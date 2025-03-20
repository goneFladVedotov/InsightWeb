package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

@Data
public class FormSubmitTrackerEvent extends TrackerEvent{
    private String formId;
}
