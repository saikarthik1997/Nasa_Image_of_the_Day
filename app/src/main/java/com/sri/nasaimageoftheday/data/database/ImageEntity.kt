package com.sri.nasaimageoftheday.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sri.nasaimageoftheday.models.NasaImageItemData

@Entity(tableName = "images_table")
class ImageEntity(
    var imageItem: NasaImageItemData
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}