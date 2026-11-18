package com.alantech.learnmicroservices.configurations;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.alantech.learnmicroservices.dtos.OutboxEvent;
import com.alantech.learnmicroservices.repositories.OutboxEventRepository;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OutboxRelay {

  private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);

  private final OutboxEventRepository outboxRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;
  @Value("${app.outbox.topic}")
  private String topic;
  @Value("${app.outbox.batch-size}")
  private int batchSize;

  public OutboxRelay(OutboxEventRepository outboxRepository,
      KafkaTemplate<String, String> kafkaTemplate) {
    this.outboxRepository = outboxRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Scheduled(fixedDelay = 1000)
  @Transactional
  public void publishPending() {
    List<OutboxEvent> events = outboxRepository.findPending(PageRequest.of(0, batchSize));

    for (OutboxEvent event : events) {
      try {
        kafkaTemplate.send(topic, event.getAggregateId(), event.getPayload()).get(5,
            TimeUnit.SECONDS);
        event.markSent();
        log.info("Published outbox event {} ({})", event.getId(), event.getEventType());
      } catch (Exception e) {
        log.error("Failed to publish outbox event {}, will retry", event.getId(), e);
        break;
      }
    }
  }
}
