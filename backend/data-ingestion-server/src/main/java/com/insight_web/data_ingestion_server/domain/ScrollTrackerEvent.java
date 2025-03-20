package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

@Data
public class ScrollTrackerEvent extends TrackerEvent{
    private Integer scrollPercent;
}
