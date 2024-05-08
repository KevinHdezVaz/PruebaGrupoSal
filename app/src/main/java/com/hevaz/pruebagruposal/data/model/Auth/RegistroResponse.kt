package com.hevaz.pruebagruposal.data.model.Auth

data class RegistroResponse(
    val id:String,
    val token:String,
    val error: String? = null  // Incluir un campo de error para manejar mensajes de error

)
