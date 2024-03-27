package com.skyrim.fcmNotification.Repository;

import com.skyrim.fcmNotification.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can add custom query methods here if needed
}
