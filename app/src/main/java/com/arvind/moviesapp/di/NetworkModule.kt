package com.arvind.moviesapp.di

import com.arvind.moviesapp.data.remote.MoviesAPI
import com.arvind.moviesapp.data.repository.RemoteDataSourceImpl
import com.arvind.moviesapp.domain.repository.RemoteDataSource
import com.arvind.moviesapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(okHttpClient: OkHttpClient): MoviesAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MoviesAPI::class.java)
    }


    @Provides
    @Singleton
    fun providesRemoteDataSource(
        moviesAPI: MoviesAPI,
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            moviesAPI = moviesAPI
        )
    }
}