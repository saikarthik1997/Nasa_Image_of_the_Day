package com.sri.nasaimageoftheday.utils

import android.util.Log

class Logger {
    companion object{
        fun log(message:String){
            Log.i("MyTag",message)
        }
    }
}