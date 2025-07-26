package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IRecoverPasswordUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.users.utils.Constants.PASSWORDS_DO_NOT_MATCH_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.RECOVER_PASSWORD_EMAIL_SUBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class RecoverPasswordUseCase implements IRecoverPasswordUseCase {

    private final IFindUserUseCase findUserUseCase;
    private final ITokenService tokenService;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendEmailToRecoverPassword(String email) {
        final var user = findUserUseCase.findUserByEmail(email);
        final var recoverPasswordToken = tokenService.saveToken(user);
        final var emailContent = emailBuilder.buildRecoverPasswordEmail(user.getFirstName(), recoverPasswordToken.getTokenId());
        emailService.sendEmail(email, RECOVER_PASSWORD_EMAIL_SUBJECT, emailContent);
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        throwExceptionIfPasswordDontMatch(recoverPasswordRequest.password(), recoverPasswordRequest.confirmPassword());
        tokenService.throwExceptionIfTokenIsExpired(recoverPasswordRequest.recoverPasswordToken());
        recoverUserPassword(recoverPasswordRequest);
    }

    private void throwExceptionIfPasswordDontMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new ResponseStatusException(BAD_REQUEST, PASSWORDS_DO_NOT_MATCH_MESSAGE);
    }

    private void recoverUserPassword(RecoverPasswordRequest recoverPasswordRequest) {
        final var user = tokenService.findUserByTokenId(recoverPasswordRequest.recoverPasswordToken());
        user.setPassword(passwordEncoder.encode(recoverPasswordRequest.password()));
        userRepository.save(user);
    }
}