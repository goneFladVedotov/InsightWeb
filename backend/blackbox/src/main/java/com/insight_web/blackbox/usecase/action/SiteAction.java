package com.insight_web.blackbox.usecase.action;

import com.insight_web.blackbox.domain.Site;

public class SiteAction {
    private String siteName;
    private String url;

    public Site toDomainModel(Long userId) {
        var site = new Site();
        site.setSiteName(siteName);
        site.setId(url);
        site.setOwnerId(userId);
        return site;
    }
}
