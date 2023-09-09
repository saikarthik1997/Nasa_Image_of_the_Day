package com.sri.nasaimageoftheday.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.utils.Logger


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val json = intent.getStringExtra("item_json")
        val gson = Gson()
        val clickedItem: NasaImageItemData = gson.fromJson(json, NasaImageItemData::class.java)
        Logger.log("item is $clickedItem")
    }
}