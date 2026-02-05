package com.imaee.propinq.contacts.data.enums;

public enum ContactState {
    CREATED,
    REJECTED,
    ACCEPTED,
    UNSETTLED,
    RENTED;

    public static ContactState getContactState(String contactState) {
        if ("CREATED".equalsIgnoreCase(contactState)) return CREATED;
        if ("REJECTED".equalsIgnoreCase(contactState)) return REJECTED;
        if ("ACCEPTED".equalsIgnoreCase(contactState)) return ACCEPTED;
        if ("UNSETTLED".equalsIgnoreCase(contactState)) return UNSETTLED;
        if ("RENTED".equalsIgnoreCase(contactState)) return RENTED;
        throw new RuntimeException();
    }
}