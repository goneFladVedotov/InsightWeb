package com.insight_web.blackbox.usecase.storage;

import com.insight_web.blackbox.domain.Site;

import java.util.List;

public interface SiteStorage {
    Site findById(String siteId);
    List<Site> findAllByOwnerId(Long ownerId);
    Site create(Site site);
}
