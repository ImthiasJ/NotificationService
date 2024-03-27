package com.skyrim.fcmNotification.service;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.skyrim.fcmNotification.Repository.NotificationRepository;
import com.skyrim.fcmNotification.entity.Notification;
import com.skyrim.fcmNotification.entity.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    @Autowired
    FCMService fcmService;
    @Autowired
    public NotificationService(NotificationRepository notificationRepository)
    {
        this.notificationRepository=notificationRepository;
    }
    public Notification findNotification(Long customer_id,String document_type,String notification_type)
    {
        return notificationRepository.findByCustomerIdAndDocumentTypeAndNotificationType(customer_id,document_type,notification_type);
    }
    public void save(Notification notification)
    {
         notificationRepository.save(notification);
    }
    public List<Notification> getPendingNotifications(String notificationStatus)
    {
    return notificationRepository.findByNotificationstatus(notificationStatus);
    }
    public List<Notification> getPendingPushNotifications(String pushNotificationStatus)
    {
    return notificationRepository.findBypushNotificationStatus(pushNotificationStatus);
    }


}
