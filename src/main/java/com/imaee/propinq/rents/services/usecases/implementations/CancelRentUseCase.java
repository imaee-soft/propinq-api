package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.notifications.mappers.NotificationMapper;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.rents.controllers.requests.CancelRentRequest;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.data.repositories.IRentRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.ICancelRentUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindRentByIdUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.notifications.Constants.RENT_CANCELLED_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.data.enums.NotificationType.RENT_CANCELLED;
import static com.imaee.propinq.rents.data.enums.RentState.CANCELLED;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@AllArgsConstructor
public class CancelRentUseCase implements ICancelRentUseCase {

    private final IFindRentByIdUseCase findRentByIdUseCase;
    private final IAuthenticatedUserService authenticatedUserService;
    private final IRentRepository rentRepository;
    private final INotificationService notificationService;

    @Override
    public void cancelRent(UUID rentId, CancelRentRequest cancelRentRequest) {
        final var rent = findRentByIdUseCase.findById(rentId);
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        throwExceptionIfIsNotRentOwner(rent, loggedUser);
        cancelRent(rent, cancelRentRequest);
        notifyUser(rent, loggedUser);
    }

    private void throwExceptionIfIsNotRentOwner(Rent rent, User user) {
        if (!rent.getContact().getProperty().getUser().getUserId().equals(user.getUserId()))
            throw new ResponseStatusException(FORBIDDEN);
    }

    private void cancelRent(Rent rent, CancelRentRequest cancelRentRequest) {
        rent.setRentState(CANCELLED);
        rent.setCancellationReason(cancelRentRequest.reason());
        rent.setCancellationDate(now());
        rentRepository.save(rent);
    }

    private void notifyUser(Rent rent, User notifier) {
        final var url = "/rent-details/" + rent.getRentId();
        final var notification = NotificationMapper.buildNotification(
                notifier,
                rent.getContact().getIssuer(),
                RENT_CANCELLED,
                RENT_CANCELLED_NOTIFICATION_TITLE,
                null,
                url
        );
        notificationService.saveNotification(notification);
    }
}
