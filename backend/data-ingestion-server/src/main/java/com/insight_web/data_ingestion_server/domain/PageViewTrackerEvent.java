package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

@Data
public class PageViewTrackerEvent extends TrackerEvent{
    private String title;
    private String referrer;
}
