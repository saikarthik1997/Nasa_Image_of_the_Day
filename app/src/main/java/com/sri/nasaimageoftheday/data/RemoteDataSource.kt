package com.sri.nasaimageoftheday.data

import com.sri.nasaimageoftheday.models.NasaImageResponseData
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val imageApi:NasaImageApi
) {
    suspend fun getImages(queries:Map<String,String>):Response<NasaImageResponseData>{
        return imageApi.getImages(queries)
    }
}