package com.imaee.propinq.rents.data.enums;

public enum ContactState {
    CREATED,
    REJECTED,
    ACCEPTED;

    public static ContactState getContactState(String contactState) {
        if ("CREATED".equals(contactState)) return CREATED;
        if ("REJECTED".equals(contactState)) return REJECTED;
        if ("ACCEPTED".equals(contactState)) return ACCEPTED;
        throw new RuntimeException();
    }
}