package com.pr.localdb.di

import android.content.Context
import androidx.room.Room
import com.pr.localdb.LocalDataSource
import com.pr.localdb.RoomLocalDataSourceImpl
import com.pr.localdb.dao.PictureOfTheDayDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DatabaseHiltModule {

    @Provides
    fun providesLocalDataSource(pictureOfTheDayDao: PictureOfTheDayDao): LocalDataSource =
        RoomLocalDataSourceImpl(pictureOfTheDayDao)

    @Provides
    fun providePictureOftheDayDao(potdDatabase: com.pr.localdb.PotdDatabase): PictureOfTheDayDao {
        return potdDatabase.getPicturOftheDayDao()
    }


    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): com.pr.localdb.PotdDatabase {
        return Room.databaseBuilder(
            appContext,
            com.pr.localdb.PotdDatabase::class.java,
            "Potd"
        ).build()
    }

}