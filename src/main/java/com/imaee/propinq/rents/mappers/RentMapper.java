package com.imaee.propinq.rents.mappers;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.DocumentDetail;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.data.models.Document;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.users.data.models.User;

import java.util.List;

import static com.imaee.propinq.rents.data.enums.RaiseIndex.getRaiseIndex;
import static com.imaee.propinq.rents.data.enums.RentState.ACTIVE;
import static com.imaee.propinq.rents.data.enums.RentState.DONE;
import static java.time.LocalDate.now;

public class RentMapper {

    public static Rent buildRent(
            Contact contact,
            RentRequest rentRequest,
            byte[] contractPdf
    ) {
        final var state = rentRequest.dueDate().isBefore(now()) ? ACTIVE : DONE;
        return Rent.builder()
                .contact(contact)
                .rentDate(rentRequest.date())
                .rentDueDate(rentRequest.dueDate())
                .rentState(state)
                .payday(rentRequest.payday())
                .rentPrice(rentRequest.price())
                .raiseIndex(getRaiseIndex(rentRequest.raiseIndex()))
                .raiseMonths(rentRequest.raiseMonths())
                .contract(contractPdf)
                .build();
    }

    public static Document buildDocument(RentDocumentRequest rentDocumentRequest, byte[] content, Rent rent) {
        return Document.builder()
                .rent(rent)
                .name(rentDocumentRequest.name())
                .content(content)
                .build();
    }

    public static SimpleRent buildSimpleRent(Rent rent) {
        final var contact = rent.getContact();
        final var property = contact.getProperty();
        return SimpleRent.builder()
                .rentId(rent.getRentId())
                .propertyId(property.getPropertyId())
                .rentDate(rent.getRentDate())
                .tenantFullName(contact.getIssuer().getFullName())
                .ownerFullName(property.getUser().getFullName())
                .propertyName(property.getTitle())
                .latitude(property.getLatitude())
                .longitude(property.getLongitude())
                .build();
    }

    public static RentDetail buildRentDetail(Rent rent, User retrievingUser) {
        final var contact = rent.getContact();
        final var property = contact.getProperty();
        final var isOwnerRetrieving = contact.getProperty().getUser()
                .getUserId().equals(retrievingUser.getUserId());
        return RentDetail.builder()
                .rentId(rent.getRentId())
                .propertyId(property.getPropertyId())
                .contactId(contact.getContactId())
                .rentDate(rent.getRentDate())
                .rentDueDate(rent.getRentDueDate())
                .payday(rent.getPayday())
                .rentPrice(rent.getRentPrice())
                .raiseIndex(rent.getRaiseIndex().name())
                .raiseMonths(rent.getRaiseMonths())
                .tenantFullName(contact.getIssuer().getFullName())
                .ownerFullName(property.getUser().getFullName())
                .propertyName(property.getTitle())
                .latitude(property.getLatitude())
                .longitude(property.getLongitude())
                .contract(rent.getContract())
                .extraDocuments(buildRentDocuments(rent))
                .isOwnerRetrieving(isOwnerRetrieving)
                .build();
    }

    private static List<DocumentDetail> buildRentDocuments(Rent rent) {
        return rent.getDocuments().stream()
                .map(RentMapper::buildDocumentDetail)
                .toList();
    }

    private static DocumentDetail buildDocumentDetail(Document document) {
        return DocumentDetail.builder()
                .documentId(document.getDocumentId())
                .name(document.getName())
                .content(document.getContent())
                .build();
    }
}