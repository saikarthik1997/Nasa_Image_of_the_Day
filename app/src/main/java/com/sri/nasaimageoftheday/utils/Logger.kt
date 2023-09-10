package com.sri.nasaimageoftheday.utils

import android.util.Log
import com.sri.nasaimageoftheday.BuildConfig

//logger class for logging in debug mode
class Logger {
    companion object{
        private val canLog = BuildConfig.DEBUG
        fun log(message:String){
            if(canLog)
            Log.i("MyTag",message)
        }
    }
}