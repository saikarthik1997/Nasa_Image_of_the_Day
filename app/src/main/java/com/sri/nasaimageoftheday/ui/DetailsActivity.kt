package com.sri.nasaimageoftheday.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import coil.load
import com.google.gson.Gson
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.databinding.ActivityDetailsBinding
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.utils.DateUtils
import com.sri.nasaimageoftheday.utils.Logger


class DetailsActivity : AppCompatActivity() {
    private lateinit var myBinding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val json = intent.getStringExtra("item_json")
        val gson = Gson()
        val clickedItem: NasaImageItemData = gson.fromJson(json, NasaImageItemData::class.java)
        Logger.log("item is $clickedItem")

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        myBinding.itemData = clickedItem
        if (clickedItem.mediaType == "video") {
            Logger.log("video item url is ${clickedItem.url}")
            myBinding.mainImageView.setImageResource(R.drawable.ic_video)
            myBinding.mainImageView.setPadding(0, 40, 0, 0)
            myBinding.mainImageView.setOnClickListener {
                val youtubeAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse("${clickedItem.url}"))
                startActivity(youtubeAppIntent)
            }
        } else {
            myBinding.mainImageView.load(clickedItem.url) {
                crossfade(600)
                placeholder(R.drawable.baseline_image_24)
            }
        }
        myBinding.dateTextView.text=DateUtils.formatDate(clickedItem.date!!)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title=clickedItem.title

    }
}