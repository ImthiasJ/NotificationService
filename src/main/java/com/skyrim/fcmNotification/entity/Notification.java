package com.skyrim.fcmNotification.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="notifications")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int notification_id;
    @Column(name="customer_id")
    Long customerId;
    @Column(name="notification_type")
    String notificationType;
    @Column(name="document_type")
    String documentType;
    @Column(name="notification_status")
    String notificationstatus;
    @Column(name="push_notification_status")
    String pushNotificationStatus;
    @Column(name="creation_date")
    private LocalDate creationDate;
    @Column(name="last_modification_date")
    private LocalDate lastModificationDate;



    Long daysRemaining;


}
