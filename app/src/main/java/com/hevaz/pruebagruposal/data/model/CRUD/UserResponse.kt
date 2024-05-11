package com.hevaz.pruebagruposal.data.model.CRUD

import com.hevaz.pruebagruposal.data.local.User

data class UserResponse(
    val data: User,
    val support: Support
)

