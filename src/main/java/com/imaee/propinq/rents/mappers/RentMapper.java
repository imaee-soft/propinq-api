package com.imaee.propinq.rents.mappers;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.data.models.Rent;

import static com.imaee.propinq.rents.data.enums.RaiseIndex.getRaiseIndex;

public class RentMapper {

    public static Rent buildRent(
            Contact contact,
            RentRequest rentRequest,
            byte[] contractPdf
    ) {
        return Rent.builder()
                .contact(contact)
                .rentDate(rentRequest.date())
                .rentDuration(rentRequest.yearsDuration())
                .payday(rentRequest.payday())
                .rentPrice(rentRequest.price())
                .raiseIndex(getRaiseIndex(rentRequest.raiseIndex()))
                .raiseMonths(rentRequest.raiseMonths())
                .contract(contractPdf)
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
}