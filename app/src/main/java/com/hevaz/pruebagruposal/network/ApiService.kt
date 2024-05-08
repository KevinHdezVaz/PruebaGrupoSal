package com.hevaz.pruebagruposal.network

import com.hevaz.pruebagruposal.data.model.Auth.LoginResponse
import com.hevaz.pruebagruposal.data.model.Auth.RegistroResponse
import com.hevaz.pruebagruposal.data.model.Auth.UserRegistrationRequest
import com.hevaz.pruebagruposal.data.model.CRUD.UserListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserListResponse>

    @POST("register")
    suspend fun registerUser(@Body request: UserRegistrationRequest): Response<RegistroResponse>

    @POST("login")
    suspend fun loginUser(@Body request: UserRegistrationRequest): Response<LoginResponse>

}
