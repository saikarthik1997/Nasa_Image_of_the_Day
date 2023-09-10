package com.sri.nasaimageoftheday.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.models.NasaImageResponseData

class ImageTypeConvertor {

    var gson = Gson()

    @TypeConverter
    fun imageResponseToString(imageResponse: NasaImageResponseData): String {
        return gson.toJson(imageResponse)
    }

    @TypeConverter
    fun stringToImageResponse(data: String): NasaImageResponseData {
        val listType = object : TypeToken<NasaImageResponseData>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun itemToString(result: NasaImageItemData): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToItem(data: String): NasaImageItemData {
        val listType = object : TypeToken<NasaImageItemData>() {}.type
        return gson.fromJson(data, listType)
    }
}