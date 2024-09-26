package com.moriawe.myandroiddiary.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

// DI in here lives as long as the view-model lives
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

//    // USE CASE
//    @Provides
//    @ViewModelScoped
//    fun provideDeleteTimeItemFromDatabase(repository: TimeRepository): DeleteTimeItemFromDatabase {
//        return DeleteTimeItemFromDatabase(repository)
    }
