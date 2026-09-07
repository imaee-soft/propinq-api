package com.imaee.propinq.users.services.usecases.implementations.unit;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.implementations.ActivateUserUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.TOKEN_USER_MISMATCH_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.WELCOME_EMAIL_SUBJECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class ActivateUserUseCaseTest {

    private static final UUID USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID OTHER_USER_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final UUID TOKEN_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    @Mock
    private ITokenService tokenService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IEmailService emailService;

    @Mock
    private EmailBuilder emailBuilder;

    @InjectMocks
    private ActivateUserUseCase activateUserUseCase;

    @Nested
    class ActivateUser {

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldReturnWithoutWelcome_whenUserAlreadyActivated() {
            User user = alreadyActivatedUser();
            when(tokenService.findUserByTokenId(TOKEN_ID)).thenReturn(user);

            activateUserUseCase.activateUser(USER_ID, TOKEN_ID);

            verify(tokenService).findUserByTokenId(TOKEN_ID);
            verify(tokenService, never()).throwExceptionIfTokenIsExpired(any());
            verify(tokenService, never()).expireToken(any());
            verify(userRepository, never()).save(any());
            verifyNoInteractions(emailService, emailBuilder);
        }

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldActivateExpireTokenAndSendWelcomeOnce_whenFirstActivation() {
            User user = inactiveUser();
            when(tokenService.findUserByTokenId(TOKEN_ID)).thenReturn(user);
            when(emailBuilder.buildWelcomeEmail(user)).thenReturn("<p>welcome</p>");

            activateUserUseCase.activateUser(USER_ID, TOKEN_ID);

            verify(tokenService).throwExceptionIfTokenIsExpired(TOKEN_ID);
            verify(userRepository).save(user);
            verify(tokenService).expireToken(TOKEN_ID);
            verify(emailService).sendEmail(eq("user@propinq.com"), eq(WELCOME_EMAIL_SUBJECT), eq("<p>welcome</p>"));
        }

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Security
        // Testing Technique: Equivalent Partition
        void shouldThrowBadRequest_whenTokenBelongsToAnotherUser() {
            User user = userWithId(USER_ID);
            when(tokenService.findUserByTokenId(TOKEN_ID)).thenReturn(user);

            assertThatThrownBy(() -> activateUserUseCase.activateUser(OTHER_USER_ID, TOKEN_ID))
                    .isInstanceOf(ResponseStatusException.class)
                    .satisfies(ex -> {
                        ResponseStatusException rse = (ResponseStatusException) ex;
                        assertThat(rse.getStatusCode()).isEqualTo(BAD_REQUEST);
                        assertThat(rse.getReason()).isEqualTo(TOKEN_USER_MISMATCH_MESSAGE);
                    });

            verify(tokenService, never()).throwExceptionIfTokenIsExpired(any());
            verify(userRepository, never()).save(any());
            verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        }
    }

    private User alreadyActivatedUser() {
        User user = userWithId(USER_ID);
        when(user.isActivated()).thenReturn(true);
        return user;
    }

    private User inactiveUser() {
        User user = userWithId(USER_ID);
        when(user.isActivated()).thenReturn(false);
        when(user.getEmail()).thenReturn("user@propinq.com");
        return user;
    }

    private User userWithId(UUID userId) {
        User user = mock(User.class);
        when(user.getUserId()).thenReturn(userId);
        return user;
    }
}
