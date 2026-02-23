package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.services.usecases.interfaces.IFindContactByIdUseCase;
import com.imaee.propinq.contacts.services.usecases.interfaces.IRentContactUseCase;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.data.repositories.IRentRepository;
import com.imaee.propinq.rents.mappers.RentMapper;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveRentUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static com.imaee.propinq.notifications.Constants.RENT_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.data.enums.NotificationType.CONTACT_RENTED;
import static com.imaee.propinq.notifications.mappers.NotificationMapper.buildNotification;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@AllArgsConstructor
public class SaveRentUseCase implements ISaveRentUseCase {

    private static final String MISSING_DATES = "La fecha de fin del alquiler es inválida";

    private final IFindContactByIdUseCase findContactByIdUseCase;
    private final IAuthenticatedUserService authenticatedUserService;
    private final IRentRepository rentRepository;
    private final IRentContactUseCase rentContactUseCase;
    private final INotificationService notificationService;

    @Override
    public SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract) {
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        final var contact = findContactByIdUseCase.findContactById(rentRequest.contactId());
        throwExceptionIfLoggedUserIsNotOwner(loggedUser, contact);
        throwExceptionIfDueDateIsLessThanRentDate(rentRequest);
        return saveRentAndNotifyUser(loggedUser, rentRequest, contract, contact);
    }

    private void throwExceptionIfLoggedUserIsNotOwner(User loggedUser, Contact contact) {
        if (!contact.getProperty().getUser().getUserId().equals(loggedUser.getUserId()))
            throw new ResponseStatusException(FORBIDDEN);
    }

    private void throwExceptionIfDueDateIsLessThanRentDate(RentRequest rentRequest) {
        if (rentRequest.date().plusMonths(1)
                .isAfter(rentRequest.dueDate()))
            throw new ResponseStatusException(BAD_REQUEST, MISSING_DATES);
    }

    private SaveRentResponse saveRentAndNotifyUser(User user, RentRequest rentRequest, MultipartFile contract, Contact contact) {
        final var rent = saveRent(rentRequest, contract, contact);
        rentContactUseCase.markAsRented(contact);
        notifyIssuer(rent, user);
        return new SaveRentResponse(rent.getRentId());
    }

    private Rent saveRent(RentRequest rentRequest, MultipartFile contract, Contact contact) {
        final var contractPdf = getBytes(contract);
        final var rent = RentMapper.buildRent(
                contact,
                rentRequest,
                contractPdf
        );
        rentRepository.save(rent);
        return rent;
    }


    private byte[] getBytes(MultipartFile contract) {
        try { return contract.getBytes(); }
        catch (IOException ex) { throw new ResponseStatusException(BAD_REQUEST, ex.getMessage()); }
    }

    private void notifyIssuer(Rent rent, User user) {
        final var contactUrl = "/rent-details/" + rent.getRentId();
        notificationService.saveNotification(
                buildNotification(
                        user,
                        rent.getContact().getIssuer(),
                        CONTACT_RENTED,
                        RENT_NOTIFICATION_TITLE,
                        rent.getContact().getProperty().getTitle(),
                        contactUrl
                )
        );
    }
}