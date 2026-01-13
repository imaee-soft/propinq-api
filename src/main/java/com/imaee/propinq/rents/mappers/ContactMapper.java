package com.imaee.propinq.rents.mappers;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.rents.controllers.responses.ContactResponse;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.users.data.models.User;

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
        return ContactDetailResponse.builder()
                .contactId(contact.getContactId())
                .propertyId(contact.getProperty().getPropertyId())
                .contactDate(contact.getIssueDate())
                .owner(contact.getProperty().getUser().getFullName())
                .propertyAddress(contact.getProperty().getAddress())
                .status(contact.getState().name())
                .latitude(contact.getProperty().getLatitude())
                .longitude(contact.getProperty().getLongitude())
                .build();
    }
}