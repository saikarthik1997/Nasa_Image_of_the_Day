package com.sri.nasaimageoftheday.data

import com.sri.nasaimageoftheday.data.database.ImageDao
import com.sri.nasaimageoftheday.data.database.ImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val imageDao: ImageDao
) {

    fun readImages(): Flow<List<ImageEntity>> {
        return imageDao.readImages()
    }

    suspend fun insertImage(imageEntity: ImageEntity) {
        imageDao.insertImage(imageEntity)
    }

    suspend fun deleteAll(){
        imageDao.deleteAll()
    }


}