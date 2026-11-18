package com.alantech.learnmicroservices.dtos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {

  @Id
  @Column(length = 36)
  private String id;

  @Column(name = "aggregate_type", nullable = false)
  private String aggregateType;

  @Column(name = "aggregate_id", nullable = false)
  private String aggregateId;

  @Column(name = "event_type", nullable = false)
  private String eventType;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String payload;

  @Column(nullable = false)
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

  protected OutboxEvent() {}

  public OutboxEvent(String id, String aggregateType, String aggregateId, String eventType,
      String payload) {
    this.id = id;
    this.aggregateType = aggregateType;
    this.aggregateId = aggregateId;
    this.eventType = eventType;
    this.payload = payload;
    this.status = "PENDING";
    this.createdAt = LocalDateTime.now();
  }

  public void markSent() {
    this.status = "SENT";
    this.sentAt = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public String getEventType() {
    return eventType;
  }

  public String getPayload() {
    return payload;
  }

  public String getStatus() {
    return status;
  }
}
