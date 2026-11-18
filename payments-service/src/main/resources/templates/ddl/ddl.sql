-- Table for PAYMENTS service --
CREATE TABLE payments (
    id CHAR(36) PRIMARY KEY,
    order_id VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
) ENGINE=InnoDB;

CREATE TABLE outbox_events (
    id             CHAR(36) PRIMARY KEY,
    aggregate_type VARCHAR(50)  NOT NULL,
    aggregate_id   VARCHAR(50)  NOT NULL,
    event_type     VARCHAR(50)  NOT NULL,
    payload        TEXT         NOT NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    sent_at        TIMESTAMP    NULL,
    INDEX idx_outbox_status_created (status, created_at)
) ENGINE=InnoDB;

