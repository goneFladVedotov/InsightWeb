package com.insight_web.data_ingestion_server.model;

import lombok.Data;

@Data
public class InsightEvent {
    private String eventType;
    private Long timestamp;
    private String userId;
    private String sessionId;
    private String siteId;
    private String url;
    private String ip;
    private String userAgent;
}

