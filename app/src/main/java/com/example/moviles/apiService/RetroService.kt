package com.example.moviles.apiService

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


data class User(val id: Number, val email: String, val password: String)
data class LoginReq(val email: String, val password: String)
data class LoginRes(val message: String, val user: User)

interface ApiService{
    @POST("users/login")
    fun login(@Body req: LoginReq): Call<LoginRes>

    @Multipart
    @POST("products")
    suspend fun addProduct(
        @Part("nombre") name: RequestBody,
        @Part("precio") price: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Unit>
}