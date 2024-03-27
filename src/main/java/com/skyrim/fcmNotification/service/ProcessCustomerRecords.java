package com.skyrim.fcmNotification.service;

import com.skyrim.fcmNotification.entity.Customer;
import com.skyrim.fcmNotification.entity.Notification;
import com.skyrim.fcmNotification.entity.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProcessCustomerRecords {
    @Autowired
    CustomerService customerService;



    @Autowired
    NotificationService notificationService;




    @Scheduled(fixedRate = 5000)
    public void processCustomers() {
        long diffDays = 0;
        LocalDate currentDate = LocalDate.now();
        List<Customer> result = customerService.getAllCustomers();
        Iterator itr = result.iterator();
        Customer cust;
        String notificationType = "";
        while (itr.hasNext()) {
            Notification notification;
            cust = (Customer) itr.next();
            //2nd -1st postive means in future negative means in past
            diffDays = Duration.between(currentDate.atStartOfDay(), cust.getExpiryDate().atStartOfDay()).toDays();
            if (diffDays > 90) notificationType = "seventh";
            switch ((int) diffDays) {
                case -60:
                    notificationType = "first";
                    break;
                case -30:
                    notificationType = "second";
                    break;
                case 0:
                    notificationType = "third";
                    break;
                case 30:
                    notificationType = "fourth";
                    break;
                case 60:
                    notificationType = "fifth";
                    break;
                case 90:
                    notificationType = "sixth";
                    break;
            }
            if (notificationService.findNotification(cust.getId(), cust.getDocumentType(), notificationType) == null && notificationType.length()>1) {
                notification = new Notification(0, cust.getId(), notificationType, cust.getDocumentType(), "pending", "pending", currentDate, currentDate, diffDays);
                notificationService.save(notification);
            }

        }

    }
}
