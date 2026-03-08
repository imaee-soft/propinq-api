package com.imaee.propinq.notifications.data.repositories;

import com.imaee.propinq.notifications.data.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findByNotifiedUserId(UUID notifiedUserId, Pageable pageable);
    List<Notification> findByNotifiedUserIdAndSeenFalse(UUID notifiedUserId);
}