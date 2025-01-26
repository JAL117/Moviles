package com.example.moviles.apiService

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

data class User(val id: Number, val email: String, val password: String)
data class LoginReq(val email: String, val password: String)
data class LoginRes(val message: String, val user: User)
data class ProductResponse(val id:Int,val nombre: String, val precio: String, val imagen: String?)

interface ApiService{
    @POST("users/login")
    fun login(@Body req: LoginReq): retrofit2.Call<LoginRes>

    @Multipart
    @POST("products")
    suspend fun addProduct(
        @Part("nombre") name: RequestBody,
        @Part("precio") price: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Unit>

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @DELETE("products/{nombre}")
    suspend fun deleteProduct(@Path("nombre") nombre:String): Response<Unit>

    @Multipart
    @PUT("products")
    suspend fun updateProduct(
        @Part("nombre") nombre: RequestBody,
        @Part("nuevoNombre") nuevoNombre: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part image: MultipartBody.Part?
    ):Response<Unit>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int):Response<ProductResponse>
}