package com.sri.nasaimageoftheday.di

import android.content.Context
import androidx.room.Room
import com.sri.nasaimageoftheday.data.database.ImagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule { //provides database and dao instance
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ImagesDatabase::class.java,
        "images_database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: ImagesDatabase) = database.imagesDao()
}