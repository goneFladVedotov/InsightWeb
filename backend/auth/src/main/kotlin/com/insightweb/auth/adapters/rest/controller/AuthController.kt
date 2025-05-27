package com.insightweb.auth.adapters.rest.controller

import com.insightweb.auth.adapters.rest.auth.AuthProvider
import com.insightweb.auth.adapters.rest.dto.PuidDto
import com.insightweb.auth.adapters.rest.dto.SiteDto
import com.insightweb.auth.adapters.rest.dto.SiteRequest
import com.insightweb.auth.adapters.rest.dto.toPuidDto
import com.insightweb.auth.adapters.rest.dto.toSiteDto
import com.insightweb.auth.domain.AuthToken
import com.insightweb.auth.usecase.SessionInitializing
import com.insightweb.auth.usecase.SiteCreation
import com.insightweb.auth.usecase.SiteRemoving
import com.insightweb.auth.usecase.SiteSelection
import com.insightweb.auth.usecase.UserRegistration
import com.insightweb.auth.usecase.UserSelection
import com.insightweb.auth.usecase.action.LoginAction
import com.insightweb.auth.usecase.action.RegisterAction
import com.insightweb.auth.usecase.action.SiteAction
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/v1")
class AuthController(
    private val sessionInitializing: SessionInitializing,
    private val siteCreation: SiteCreation,
    private val siteSelection: SiteSelection,
    private val userRegistration: UserRegistration,
    private val userSelection: UserSelection,
    private val siteRemoving: SiteRemoving,
    private val authProvider: AuthProvider,
) {

    @PostMapping("/login")
    fun login(@RequestBody loginAction: LoginAction): AuthToken {
        return sessionInitializing.initialize(loginAction)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerAction: RegisterAction) {
        userRegistration.register(registerAction)
    }

    @GetMapping
    fun getPuid(): PuidDto {
        val email = authProvider.getEmail()
        return userSelection.select(email).toPuidDto()
    }

    @PostMapping("/site")
    fun addSite(@RequestBody siteAction: SiteAction) {
        val email = authProvider.getEmail()
        siteCreation.create(siteAction, email)
    }

    @GetMapping("/site")
    fun getAllUserSites(): List<SiteDto> {
        val email = authProvider.getEmail()
        return siteSelection.selectAllNyOwner(email).map { it.toSiteDto() }
    }

    @GetMapping("/site/one")
    fun getById(@RequestBody siteRequest: SiteRequest): SiteDto {
        return siteSelection.select(siteRequest.siteId).toSiteDto()
    }

    @DeleteMapping("/site")
    fun deleteById(@RequestBody request: SiteRequest) {
        siteRemoving.remove(request.siteId)
    }
}