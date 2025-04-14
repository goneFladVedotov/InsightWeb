package com.insight_web.blackbox.adapters.postgres.site;


import com.insight_web.blackbox.domain.Site;
import com.insight_web.blackbox.domain.exception.ResourceNotFoundException;
import com.insight_web.blackbox.usecase.storage.SiteStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SiteStorageImpl implements SiteStorage {
    private final SiteRepository siteRepository;
    @Override
    public Site findById(String siteId) {
        return siteRepository
                .findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("site by id " + siteId + "not found"))
                .toDomainModel();
    }

    @Override
    public List<Site> findAllByOwnerId(Long ownerId) {
        return siteRepository.findAllByOwnerId(ownerId).stream().map(SiteEntity::toDomainModel).collect(Collectors.toList());
    }

    @Override
    public Site create(Site site) {
        return siteRepository.save(map(site)).toDomainModel();
    }

    private SiteEntity map(Site site) {
        SiteEntity entity = new SiteEntity();
        entity.setId(site.getId());
        entity.setSiteName(site.getSiteName());
        entity.setOwnerId(site.getOwnerId());
        return entity;
    }
}
