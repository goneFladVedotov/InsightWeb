package com.insight_web.blackbox.adapters.postgres.site;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<SiteEntity, String> {
    List<SiteEntity> findAllByOwnerId(Long ownerId);
}
