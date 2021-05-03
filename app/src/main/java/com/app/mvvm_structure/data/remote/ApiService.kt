package com.app.mvvm_structure.data.remote

import com.app.mvvm_structure.data.entities.response.JokeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("random")
    suspend fun getJoke(): Response<JokeResponse>

}