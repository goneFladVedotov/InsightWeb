package com.insight_web.blackbox.domain;


import lombok.Data;

@Data
public class Site {
    private String id;
    private Long ownerId;
    private String siteName;
}
