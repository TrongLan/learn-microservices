package com.alantech.learnmicroservices.dtos;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @Column(length = 36)
  private String id;

  @Column(name = "order_id", nullable = false)
  private String orderId;

  @Column(nullable = false, precision = 15, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false)
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  protected Payment() {}

  public Payment(String id, String orderId, BigDecimal amount, String status) {
    this.id = id;
    this.orderId = orderId;
    this.amount = amount;
    this.status = status;
    this.createdAt = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
