package com.insightweb.auth.adapters.rest.dto

import com.insightweb.auth.domain.User

data class PuidDto(val puid: Long)

fun User.toPuidDto(): PuidDto = PuidDto(id!!)
