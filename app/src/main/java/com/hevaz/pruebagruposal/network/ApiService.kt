package com.hevaz.pruebagruposal.network

import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.data.model.Auth.LoginResponse
import com.hevaz.pruebagruposal.data.model.Auth.RegistroResponse
import com.hevaz.pruebagruposal.data.model.Auth.UserRegistrationRequest
import com.hevaz.pruebagruposal.data.model.CRUD.UserDetailsResponse
import com.hevaz.pruebagruposal.data.model.CRUD.UserListResponse
import com.hevaz.pruebagruposal.data.model.CRUD.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserListResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Response<UserDetailsResponse>


    @POST("register")
    suspend fun registerUser(@Body request: UserRegistrationRequest): Response<RegistroResponse>

    @POST("login")
    suspend fun loginUser(@Body request: UserRegistrationRequest): Response<LoginResponse>


    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") userId: Int,
        @Body userData: User
    ): Response<UserResponse>

}
