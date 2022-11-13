package com.pr.potd.di

import com.pr.localdb.LocalDataSource
import com.pr.network.NetworkDataSource
import com.pr.potd.repositories.PotdRepository
import com.pr.potd.repositories.PotdRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Provides
    fun providesPotdRepository(
        remoteDataSource: NetworkDataSource,
        localDataSource: LocalDataSource
    ): PotdRepository = PotdRepositoryImpl(remoteDataSource, localDataSource)

}