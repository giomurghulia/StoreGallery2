package com.example.storegallery2.networking.di

import com.example.storegallery2.networking.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(retrofitClient: RetrofitClient): Retrofit {
        return retrofitClient.retrofit
    }

}