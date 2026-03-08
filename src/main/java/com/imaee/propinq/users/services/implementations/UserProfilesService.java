package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
import com.imaee.propinq.users.data.models.ProfileChange;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IProfileChangeRepository;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.mappers.ProfileChangeMapper;
import com.imaee.propinq.users.services.interfaces.IUserProfilesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.notifications.Constants.*;
import static com.imaee.propinq.notifications.data.enums.NotificationType.*;
import static com.imaee.propinq.notifications.mappers.NotificationMapper.buildNotification;
import static com.imaee.propinq.users.data.enums.ProfileChangeState.ACCEPTED;
import static com.imaee.propinq.users.data.enums.ProfileChangeState.REJECTED;
import static com.imaee.propinq.users.data.enums.Role.ADMIN;
import static com.imaee.propinq.users.data.enums.Role.OWNER;
import static com.imaee.propinq.users.mappers.ProfileChangeMapper.buildProfileChange;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@AllArgsConstructor
public class UserProfilesService implements IUserProfilesService {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IProfileChangeRepository profileChangeRepository;
    private final IUserRepository userRepository;
    private final INotificationService notificationService;

    @Override
    public void saveOwnerProfileRequest() {
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        if (loggedUser.getRole().equals(OWNER)) throw new ResponseStatusException(FORBIDDEN);
        profileChangeRepository.save(buildProfileChange(loggedUser));
        notifyAllAdministrators(loggedUser);
    }

    @Async
    public void notifyAllAdministrators(User user) {
        final var notification = buildNotification(
                user,
                PROFILE_CHANGE_REQUEST,
                PROFILE_CHANGE_REQUEST_NOTIFICATION_TITLE,
                "/profile-changes"
        );
        final var administrators = userRepository.findAllByRole(ADMIN);
        notificationService.notifyUsers(notification, administrators);
    }

    @Override
    public ProfileChangeResponse getProfileChangeByUser(User user) {
        return profileChangeRepository.findByUser(user)
                .map(ProfileChangeMapper::buildProfileChangeResponse)
                .orElse(null);
    }

    @Override
    public Page<ProfileChangeResponse> getProfileChanges(Integer pageNumber, Integer pageSize) {
        return profileChangeRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(ProfileChangeMapper::buildProfileChangeResponse);
    }

    @Override
    public void acceptProfileChange(UUID profileChangeId) {
        final var profileChange = findByIdOrThrowException(profileChangeId);
        profileChange.setState(ACCEPTED);
        profileChange.setAcceptedAt(now());
        profileChangeRepository.save(profileChange);
        updateUserProfile(profileChange);
        notifyUser(profileChange);
    }

    @Override
    public void rejectProfileChange(UUID profileChangeId) {
        final var profileChange = findByIdOrThrowException(profileChangeId);
        profileChange.setState(REJECTED);
        profileChange.setRejectedAt(now());
        profileChangeRepository.save(profileChange);
        notifyUser(profileChange);
    }

    private ProfileChange findByIdOrThrowException(UUID profileChangeId) {
        return profileChangeRepository.findById(profileChangeId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    private void updateUserProfile(ProfileChange profileChange) {
        final var user = profileChange.getUser();
        user.setRole(profileChange.getRoleRequested());
        userRepository.save(user);
    }

    private void notifyUser(ProfileChange profileChange) {
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        final var notification = buildNotification(
                loggedUser,
                profileChange.getUser(),
                ACCEPTED.equals(profileChange.getState()) ? PROFILE_CHANGE_ACCEPTED : PROFILE_CHANGE_REJECTED,
                ACCEPTED.equals(profileChange.getState())
                        ? PROFILE_CHANGE_REQUEST_ACCEPTED_NOTIFICATION_TITLE : PROFILE_CHANGE_REQUEST_REJECTED_NOTIFICATION_TITLE,
                null,
                "/users/my-account"
        );
        notificationService.saveNotification(notification);
    }
}