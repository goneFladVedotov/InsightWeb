package com.insight_web.blackbox.adapters.rest.controller;

import com.insight_web.blackbox.adapters.rest.auth.AuthProvider;
import com.insight_web.blackbox.adapters.rest.dto.SiteDto;
import com.insight_web.blackbox.adapters.rest.dto.SiteRequest;
import com.insight_web.blackbox.adapters.rest.dto.UserDto;
import com.insight_web.blackbox.domain.AuthToken;
import com.insight_web.blackbox.domain.Site;
import com.insight_web.blackbox.usecase.SessionInitializing;
import com.insight_web.blackbox.usecase.SiteCreation;
import com.insight_web.blackbox.usecase.SiteSelection;
import com.insight_web.blackbox.usecase.UserRegistration;
import com.insight_web.blackbox.usecase.action.LoginAction;
import com.insight_web.blackbox.usecase.action.RegisterAction;
import com.insight_web.blackbox.usecase.action.SiteAction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/insight-web/blackbox")
@RequiredArgsConstructor
public class BlackboxController {
    private final SessionInitializing sessionInitializing;
    private final UserRegistration userRegistration;
    private final SiteCreation siteCreation;
    private final SiteSelection siteSelection;
    private final AuthProvider authProvider;

    @PostMapping("/v1/login")
    AuthToken login(@RequestBody LoginAction loginAction) {
        return sessionInitializing.initialize(loginAction);
    }

    @PostMapping("/v1/register")
    void register(@RequestBody RegisterAction registerAction) {
        userRegistration.register(registerAction);
    }

    @PostMapping("/v1/site")
    void addSite(@RequestBody SiteAction siteAction) {
        var email = authProvider.getEmail();
        siteCreation.create(siteAction, email);
    }

    @GetMapping("/v1/site")
    List<SiteDto> getAllUserSites() {
        var email = authProvider.getEmail();
        return siteSelection.selectAllByOwnerId(email).stream().map(this::map).collect(Collectors.toList());
    }

    @GetMapping("/v1/site/one")
    SiteDto getById(@RequestBody SiteRequest siteRequest) {
        return map(siteSelection.select(siteRequest.getSiteId()));
    }

    private SiteDto map(Site site) {
        var dto = new SiteDto();
        dto.setName(site.getSiteName());
        dto.setUrl(site.getId());

        return dto;
    }
}
