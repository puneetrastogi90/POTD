package com.pr.potd.di

import android.content.Context
import androidx.room.Room
import com.pr.network.NetworkConnectionInterceptor
import com.pr.network.NetworkDataSource
import com.pr.network.PotdApiService
import com.pr.network.RetrofitNetworkDataSourceImpl
import com.pr.potd.database.PotdDatabase
import com.pr.potd.database.dataobjects.PictureOfTheDayDao
import com.pr.potd.database.local.LocalDataSource
import com.pr.potd.database.local.RoomLocalDataSourceImpl
import com.pr.potd.network.*
import com.pr.potd.repositories.PotdRepository
import com.pr.potd.repositories.PotdRepositoryImpl
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesLocalDataSource(pictureOfTheDayDao: PictureOfTheDayDao): LocalDataSource =
        RoomLocalDataSourceImpl(pictureOfTheDayDao)

    @Provides
    fun providePictureOftheDayDao(potdDatabase: PotdDatabase): PictureOfTheDayDao {
        return potdDatabase.getPicturOftheDayDao()
    }

    @Provides
    fun providesPotdRepository(
        remoteDataSource: NetworkDataSource,
        localDataSource: LocalDataSource
    ): PotdRepository = PotdRepositoryImpl(remoteDataSource, localDataSource)


    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): PotdDatabase {
        return Room.databaseBuilder(
            appContext,
            PotdDatabase::class.java,
            "Potd"
        ).build()
    }

}