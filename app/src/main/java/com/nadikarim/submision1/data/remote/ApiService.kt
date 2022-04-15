package com.nadikarim.submision1.data.remote

import com.nadikarim.submision1.data.model.login.LoginRequest
import com.nadikarim.submision1.data.model.login.LoginResponse
import com.nadikarim.submision1.data.model.login.RegisterRequest
import com.nadikarim.submision1.data.model.login.RegisterResponse
import com.nadikarim.submision1.data.model.stories.AddResponse
import com.nadikarim.submision1.data.model.stories.StoriesResponse
import com.nadikarim.submision1.data.model.stories.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field ("name") name: String,
        @Field ("email") email: String,
        @Field ("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password")password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getListStory(
        @Header("Authorization") auth: String
    ): Call<StoriesResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<AddResponse>

    @GET("stories/{id}")
    fun getDetailUser(
        @Header("Authorization") auth: String,
        @Path("id")id : String
    ): Call<Story>
}