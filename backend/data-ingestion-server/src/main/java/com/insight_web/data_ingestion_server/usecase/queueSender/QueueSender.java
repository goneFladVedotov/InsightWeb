package com.insight_web.data_ingestion_server.usecase.queueSender;

import com.insight_web.domain.TrackerEvent;

public interface QueueSender {
    void send(TrackerEvent event);
}
