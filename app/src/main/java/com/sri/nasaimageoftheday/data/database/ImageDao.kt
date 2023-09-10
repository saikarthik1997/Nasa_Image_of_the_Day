package com.sri.nasaimageoftheday.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertImage(image:ImageEntity)

    @Query("SELECT * FROM images_table ")
    fun readImages(): Flow<List<ImageEntity>>
}