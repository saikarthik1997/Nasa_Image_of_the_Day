package com.sri.nasaimageoftheday.data

import com.sri.nasaimageoftheday.models.NasaImageResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NasaImageApi {

    @GET("planetary/apod")
    suspend fun getImages(
        @QueryMap queries: Map<String, String>
    ): Response<NasaImageResponseData>

}