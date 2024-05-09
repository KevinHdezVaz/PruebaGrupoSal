package com.hevaz.pruebagruposal.data.model.CRUD

import com.hevaz.pruebagruposal.data.local.User

data class UserListResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>,
    val support: Support
)