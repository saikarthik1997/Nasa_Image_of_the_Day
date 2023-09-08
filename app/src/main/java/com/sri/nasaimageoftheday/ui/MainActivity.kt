package com.sri.nasaimageoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.i("MyTag",mainViewModel.toString())
        setContentView(R.layout.activity_main)
        mainViewModel.fetchImages()
    }
}