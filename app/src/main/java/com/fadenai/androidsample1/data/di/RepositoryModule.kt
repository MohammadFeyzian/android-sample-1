package com.fadenai.androidsample1.data.di

import com.fadenai.androidsample1.data.repository.DataRepository
import com.fadenai.androidsample1.data.repository.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun repo(repoImpl: DataRepositoryImpl): DataRepository
}