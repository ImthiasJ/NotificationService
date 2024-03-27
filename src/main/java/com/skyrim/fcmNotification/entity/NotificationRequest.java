package com.skyrim.fcmNotification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class NotificationRequest {
    private String title;
    private String body;
    private String topic;
    private String token;
}
