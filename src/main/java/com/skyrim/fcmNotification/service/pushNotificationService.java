package com.skyrim.fcmNotification.service;

import com.skyrim.fcmNotification.entity.Customer;
import com.skyrim.fcmNotification.entity.Notification;
import com.skyrim.fcmNotification.entity.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
@Service
public class pushNotificationService {
    Logger logger = LoggerFactory.getLogger(emailService.class);
    String notificationBeforeMessage = "Dear Customer, we've noticed that your documents will soon expire. " +
            "To keep your records up to date and continue enjoying uninterrupted services," +
            " please check your registered email address with EDB for details." +
            " For further assistance, you can contact us at Business.Banking@edb.gov.ae or " +
            "call 600-55-1216. If you've already updated your documents with us, " +
            "kindly disregard this message.";
    String freezeMessage="Dear Customer, please note that your documents have expired. Consequently, all outward transactions and mobile app access has been disabled temporarily. \n" +
            "To reactivate the services, kindly refer to the email sent to your registered email address with us for. \n" +
            "For further assistance contact us on Business.Banking@edb.gov.ae and 600-55-1216. \n" +
            "If you've already updated your documents with us, kindly disregard this message. ";

    @Autowired
    FCMService fcmService;

    @Autowired
    NotificationService notificationService;
    @Autowired
    CustomerService customerService;

    @Scheduled(fixedRate = 8000)
    public void sendPushNotifications() throws ExecutionException, InterruptedException {

        List<Notification> result = notificationService.getPendingPushNotifications("pending");
        String notificationMessage="";
        String notificationTitle="";
        ListIterator itr = result.listIterator();
        if (result.size() == 0) {
            logger.info("No Record to Process For PushNotifications");
        }
        Optional<Customer> cust;
        Notification notification;
        while (itr.hasNext()) {
            notification = (Notification) itr.next();
            cust = customerService.findById(notification.getCustomerId());
            if (cust != null) {
                logger.info("Push Notification sent to " + cust.get().getName() + " for the notification type " + notification.getNotificationType());
                if (notification.getNotificationType().equals("first") || notification.getNotificationType().equals("second") || notification.getNotificationType().equals("third")) {
                    notificationMessage=notificationBeforeMessage;
                    notificationTitle="Document Expiry";

                } else {
                    if (!notification.getNotificationType().equalsIgnoreCase("seventh")) {
                        notificationMessage = getAfterMessage(notification);
                        notificationTitle="Grace Period";
                    }else {
                        notificationMessage = freezeMessage;
                        notificationTitle="Debit Freeze";
                    }
                }

                if(notification.getNotificationType()!=null && notification.getNotificationType().length()>1)
                {   NotificationRequest request=new NotificationRequest();
                    request.setBody(notificationMessage);
                    request.setTitle(notificationTitle);
                    request.setToken("cLTMdZV-SDmYQojgaMCRr1:APA91bEUl6NmWsioBwOEKu2qkN8w7Akdgd-ebQfrWo-BBls2VNGNA2g60B2nj6uUAA4CLp88wy6YAgZgAG5UfFggMCb-C2VSWUwtBQF1tss-W8Y24UV66zsXmW2u6cXptmZxphv2gy4g");
                    fcmService.sendMessageToToken(request);
                    notification.setPushNotificationStatus("Completed");
                    notificationService.save(notification);
                }
            }
        }


    }
    String getAfterMessage(Notification notification)
    {
        return "Dear Customer, according to our records, some of your documents have expired. \n" +
                "To continue with uninterrupted services, please check your registered EDB email for document details.\n" +
                " Failure to submit documents may result in disabling outward transactions within " +
                notification.getDaysRemaining() +"days. \n" +
                " For assistance, contact us at Business.Banking@edb.gov.ae or 600-55-1216. \n" +
                " I If you’ve already updated your documents with us, kindly disregard this message. ";
    }
}
