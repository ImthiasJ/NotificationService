package com.skyrim.fcmNotification.Repository;

import com.skyrim.fcmNotification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification findByCustomerIdAndDocumentTypeAndNotificationType(Long customer_Id, String document_Type,String notification_Type);
    List<Notification> findByNotificationstatus(String notificationStatus);
    List<Notification> findBypushNotificationStatus(String pushNotificationStatus);
}
