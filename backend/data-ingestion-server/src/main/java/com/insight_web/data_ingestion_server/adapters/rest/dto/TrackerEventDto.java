package com.insight_web.data_ingestion_server.adapters.rest.dto;

import com.insight_web.data_ingestion_server.domain.TrackerEventType;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class TrackerEventDto {
    private TrackerEventType eventType;
    private String timestamp;
    private String userId;
    private String sessionId;
    private String siteId;
    private String url;
    private String ip;
    private String userAgent;
}
