package com.insight_web.data_ingestion_server.usecase;

import com.insight_web.data_ingestion_server.usecase.analyticsIntegration.AnalyticsClient;
import com.insight_web.data_ingestion_server.usecase.queueSender.QueueSender;
import com.insight_web.domain.TrackerEvent;
import org.springframework.stereotype.Service;

@Service
public class TrackerEventProcessing {

    private final QueueSender queueSender;

    private final AnalyticsClient analyticsClient;

    public TrackerEventProcessing(QueueSender queueSender, AnalyticsClient analyticsClient) {
        this.queueSender = queueSender;
        this.analyticsClient = analyticsClient;
    }

    public void processEvent(TrackerEvent event) {
        if (analyticsClient.isSiteExisted(event.getSiteId())) {
            throw new RuntimeException("Site id not found");
        }

        queueSender.send(event);
    }
}

