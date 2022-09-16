package com.example.storegallery2.di

import com.example.storegallery2.networking.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductApiModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}