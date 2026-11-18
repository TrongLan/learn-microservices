package com.alantech.learnmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alantech.learnmicroservices.dtos.Payment;



public interface PaymentRepository extends JpaRepository<Payment, String> {



}
