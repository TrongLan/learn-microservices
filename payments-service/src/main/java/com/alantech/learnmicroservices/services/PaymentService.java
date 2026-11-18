package com.alantech.learnmicroservices.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alantech.learnmicroservices.dtos.OutboxEvent;
import com.alantech.learnmicroservices.dtos.Payment;
import com.alantech.learnmicroservices.repositories.OutboxEventRepository;
import com.alantech.learnmicroservices.repositories.PaymentRepository;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

  public record PaymentCreatedEvent(String paymentId, String orderId, BigDecimal amount,
      String status, Instant occurredAt) {
  }

  private final PaymentRepository paymentRepository;
  private final OutboxEventRepository outboxRepository;
  private final ObjectMapper objectMapper;

  public PaymentService(PaymentRepository paymentRepository, OutboxEventRepository outboxRepository,
      ObjectMapper objectMapper) {
    this.paymentRepository = paymentRepository;
    this.outboxRepository = outboxRepository;
    this.objectMapper = objectMapper;
  }

  /** Save payment and out box event in one transaction */
  @Transactional
  public Payment createPayment(String orderId, BigDecimal amount) {
    Payment payment = new Payment(UUID.randomUUID().toString(), orderId, amount, "COMPLETED");
    paymentRepository.save(payment);

    var event = new PaymentCreatedEvent(payment.getId(), orderId, amount, payment.getStatus(),
        Instant.now());

    outboxRepository.save(new OutboxEvent(UUID.randomUUID().toString(), "PAYMENT", payment.getId(),
        "PaymentCreated", objectMapper.writeValueAsString(event)));

    return payment;
  }
}
