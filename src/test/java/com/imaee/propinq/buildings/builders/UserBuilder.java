package com.imaee.propinq.buildings.builders;

import com.imaee.propinq.users.data.enums.Role;
import com.imaee.propinq.users.data.models.User;

import java.time.LocalDate;

/**
 * Test Data Builder for User in building integration tests. Fixed defaults — no random values.
 */
public class UserBuilder {

    private static final String DEFAULT_DNI = "20111222333";
    private static final String DEFAULT_PASSWORD = "TestPassword1!";
    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1990, 1, 1);
    private static final String DEFAULT_FIRST_NAME = "Test";
    private static final String DEFAULT_LAST_NAME = "User";
    private static final String DEFAULT_EMAIL = "test.user@example.com";
    private static final String DEFAULT_ADDRESS = "Calle Falsa 123";
    private static final String DEFAULT_PHONE = "+5491112345678";

    private String dni = DEFAULT_DNI;
    private String password = DEFAULT_PASSWORD;
    private LocalDate birthDate = DEFAULT_BIRTH_DATE;
    private String firstName = DEFAULT_FIRST_NAME;
    private String lastName = DEFAULT_LAST_NAME;
    private String email = DEFAULT_EMAIL;
    private String address = DEFAULT_ADDRESS;
    private String phoneNumber = DEFAULT_PHONE;
    private Role role = Role.OWNER;

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withDni(String dni) {
        this.dni = dni;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        return User.builder()
                .dni(dni)
                .password(password)
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .address(address)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }
}
