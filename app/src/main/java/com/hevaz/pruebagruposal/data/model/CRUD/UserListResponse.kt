package com.hevaz.pruebagruposal.data.model.CRUD

data class UserListResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>,
    val support: Support
)