package com.example.homework10.di


import com.example.homework10.data.api.AppApi
import com.example.homework10.data.api.FakeAppApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAppApi(): AppApi {
        return FakeAppApi()
    }
}
