package com.insight_web.blackbox.adapters.postgres.site;

import com.insight_web.blackbox.domain.Site;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "site")
public class SiteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long ownerId;
    private String siteName;

    public Site toDomainModel() {
        var site = new Site();
        site.setId(id);
        site.setSiteName(siteName);
        site.setOwnerId(ownerId);
        return site;
    }
}
