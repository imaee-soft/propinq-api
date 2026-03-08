package com.imaee.propinq.contacts.mappers;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.contacts.controllers.requests.ContactRequest;
import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.controllers.responses.ContactResponse;
import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.users.data.models.User;

import static com.imaee.propinq.properties.data.enums.PropertyType.APARTAMENTO;

public class ContactMapper {

    public static Contact buildContact(ContactRequest request, User issuer, Property property) {
        return Contact.builder()
                .issuer(issuer)
                .property(property)
                .contactMessage(request.message())
                .build();
    }

    public static ContactResponse buildContactResponse(Contact contact) {
        return ContactResponse.builder()
                .contactId(contact.getContactId())
                .issuerFullName(contact.getIssuer().getFullName())
                .message(contact.getContactMessage())
                .contactState(contact.getState().name())
                .build();
    }

    public static ContactDetailResponse buildContactDetailResponse(Contact contact) {
        return buildContactDetailResponse(contact, false, false);
    }

    public static ContactDetailResponse buildContactDetailResponse(
            Contact contact,
            boolean showMessages,
            boolean isOwnerRetrieving
    ) {
        final var property = contact.getProperty();
        final var latitude = property.getPropertyType().equals(APARTAMENTO)
                ? property.getBuilding().getLatitude()
                : property.getLatitude();
        final var longitude = property.getPropertyType().equals(APARTAMENTO)
                ? property.getBuilding().getLongitude()
                : property.getLongitude();
        return ContactDetailResponse.builder()
                .contactId(contact.getContactId())
                .propertyId(property.getPropertyId())
                .contactDate(contact.getIssueDate())
                .owner(contact.getProperty().getUser().getFullName())
                .issuer(contact.getIssuer().getFullName())
                .message(showMessages ? contact.getContactMessage() : null)
                .answerDate(showMessages ? contact.getAnswerDate() : null)
                .answer(showMessages ? contact.getContactAnswer() : null)
                .ownerPhoneNumber(showMessages ? property.getUser().getPhoneNumber() : null)
                .propertyAddress(contact.getProperty().getTitle())
                .status(contact.getState().name())
                .latitude(latitude)
                .longitude(longitude)
                .isOwnerRetrieving(isOwnerRetrieving)
                .issuerPhoneNumber(contact.getIssuer().getPhoneNumber())
                .cancellationReason(contact.getCancellationReason())
                .cancellationDate(contact.getCancellationDate())
                .build();
    }
}