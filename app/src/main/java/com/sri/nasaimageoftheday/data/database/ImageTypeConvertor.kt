package com.sri.nasaimageoftheday.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.models.NasaImageResponseData

class ImageTypeConvertor {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: NasaImageResponseData): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): NasaImageResponseData {
        val listType = object : TypeToken<NasaImageResponseData>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: NasaImageItemData): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): NasaImageItemData {
        val listType = object : TypeToken<NasaImageItemData>() {}.type
        return gson.fromJson(data, listType)
    }
}