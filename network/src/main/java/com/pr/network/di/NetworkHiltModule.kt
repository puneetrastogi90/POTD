package com.pr.network.di

import android.content.Context
import com.google.gson.Gson
import com.pr.network.*
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkHiltModule {

    companion object {
        private const val CONNECT_TIMEOUT = 30L
        private const val READ_WRITE_TIMEOUT = 60L

    }

    @Provides
    fun provideGson(): Gson = Gson()


    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context) =
        NetworkConnectionInterceptor(context)

    @Provides
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        queryParamsInterceptor: QueryParamsInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(queryParamsInterceptor).addInterceptor(networkConnectionInterceptor)
            .addInterceptor(httpLoggingInterceptor).build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        } else {
            level = HttpLoggingInterceptor.Level.NONE
        }

    }

    @Provides
    fun providesNetworkDataSource(potdApiService: PotdApiService): NetworkDataSource =
        RetrofitNetworkDataSourceImpl(potdApiService)


    @Provides
    fun provideNetworkService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): PotdApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .callFactory(client.get())
            .build()
            .create(PotdApiService::class.java)

}