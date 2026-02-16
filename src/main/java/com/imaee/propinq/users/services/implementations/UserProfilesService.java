package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.notifications.Constants;
import com.imaee.propinq.notifications.data.enums.NotificationType;
import com.imaee.propinq.notifications.mappers.NotificationMapper;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
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

import static com.imaee.propinq.users.data.enums.Role.ADMIN;
import static com.imaee.propinq.users.data.enums.Role.OWNER;
import static com.imaee.propinq.users.mappers.ProfileChangeMapper.buildProfileChange;
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
        final var notification = NotificationMapper.buildNotification(
                user,
                NotificationType.PROFILE_CHANGE_REQUEST,
                Constants.PROFILE_CHANGE_REQUEST,
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
}