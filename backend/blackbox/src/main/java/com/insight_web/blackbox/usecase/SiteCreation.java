package com.insight_web.blackbox.usecase;

import com.insight_web.blackbox.domain.Site;
import com.insight_web.blackbox.domain.User;
import com.insight_web.blackbox.usecase.action.SiteAction;
import com.insight_web.blackbox.usecase.storage.SiteStorage;
import com.insight_web.blackbox.usecase.storage.UserStorage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteCreation {
    private final UserStorage userStorage;
    private final SiteStorage siteStorage;

    @Transactional
    public Site create(SiteAction siteAction, String userEmail) {
        var userId = userStorage.findByEmail(userEmail).getId();
        var site = siteAction.toDomainModel(userId);

        return siteStorage.create(site);
    }
}
