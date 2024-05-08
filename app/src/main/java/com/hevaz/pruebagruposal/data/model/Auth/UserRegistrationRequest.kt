package com.hevaz.pruebagruposal.data.model.Auth


data class UserRegistrationRequest(
    val email:String,
     val password: String? = null  // Hacer password opcional si es posible registrar sin contraseña

)
