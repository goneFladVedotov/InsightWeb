package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

import java.util.Map;

@Data
public class CustomTrackerEvent extends TrackerEvent{
    private Map<String, String> customEvents;
}
