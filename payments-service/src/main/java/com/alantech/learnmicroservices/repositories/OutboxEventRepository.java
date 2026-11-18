package com.alantech.learnmicroservices.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.alantech.learnmicroservices.dtos.OutboxEvent;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, String> {

  @Query("select e from OutboxEvent e where e.status = 'PENDING' order by e.createdAt asc")
  List<OutboxEvent> findPending(Pageable pageable);
}
