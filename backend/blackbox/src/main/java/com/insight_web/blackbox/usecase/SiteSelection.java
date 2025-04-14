package com.insight_web.blackbox.usecase;

import com.insight_web.blackbox.domain.Site;
import com.insight_web.blackbox.usecase.storage.SiteStorage;
import com.insight_web.blackbox.usecase.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteSelection {
    private final UserStorage userStorage;
    private final SiteStorage siteStorage;
    public Site select(String siteId) {
        return siteStorage.findById(siteId);
    }

    public List<Site> selectAllByOwnerId(String ownerEmail) {
        var ownerId = userStorage.findByEmail(ownerEmail).getId();
        return siteStorage.findAllByOwnerId(ownerId);
    }
}
