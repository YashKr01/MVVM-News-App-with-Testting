package com.example.newsnow.di

import com.example.newsnow.data.datastore.AppPreferenceStorage
import com.example.newsnow.data.datastore.PreferenceStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun provideDataStore(appPreferenceStorage: AppPreferenceStorage): PreferenceStorage

}