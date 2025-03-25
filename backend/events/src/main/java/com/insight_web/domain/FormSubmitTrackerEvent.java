package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("FORM_SUBMIT")
public class FormSubmitTrackerEvent extends TrackerEvent {
    private String formId;
}
