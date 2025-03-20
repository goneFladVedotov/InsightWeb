package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class TrackerEvent {
    protected TrackerEventType eventType;
    protected Instant timestamp;
    protected String siteId;

    public String getSiteId() {
        return siteId;
    }
}

