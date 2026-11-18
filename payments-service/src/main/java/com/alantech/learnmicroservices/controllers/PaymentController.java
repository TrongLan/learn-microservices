package com.alantech.learnmicroservices.controllers;


import org.springframework.web.bind.annotation.*;
import com.alantech.learnmicroservices.dtos.Payment;
import com.alantech.learnmicroservices.services.PaymentService;
import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  public record CreatePaymentRequest(String orderId, BigDecimal amount) {
  }

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public Payment create(@RequestBody CreatePaymentRequest req) {
    return paymentService.createPayment(req.orderId(), req.amount());
  }
}
