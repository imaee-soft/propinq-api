package com.imaee.propinq.users.utils;

public class TokenQueries {
    public static final String ACTIVE_TOKEN_BY_USER_AND_EXPIRATION = "SELECT t FROM tokens t " +
            "WHERE t.user = ?1 AND t.tokenExpirationDate > CURRENT_TIMESTAMP";
}
