package com.sri.nasaimageoftheday.data.database

import android.media.Image
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ImageEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ImageTypeConvertor::class)
abstract class ImagesDatabase: RoomDatabase() {
    abstract fun imagesDao(): ImageDao
}