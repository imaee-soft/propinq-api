package com.imaee.propinq.notifications.data.repositories;

import com.imaee.propinq.notifications.data.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByNotifiedUserIdAndSeenFalse(UUID notifiedUserId);
    void deleteByContact_ContactId(UUID contactContactId);
}