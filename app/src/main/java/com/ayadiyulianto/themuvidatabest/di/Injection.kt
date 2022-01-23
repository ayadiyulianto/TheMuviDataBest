package com.ayadiyulianto.themuvidatabest.di

import android.content.Context
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.LocalDataSource
import com.ayadiyulianto.themuvidatabest.data.source.local.room.TmdbDatabase
import com.ayadiyulianto.themuvidatabest.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.util.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Injection {

    @Singleton
    @Provides
    fun provideImdbRepository(@ApplicationContext context: Context): TmdbRepository {
        val database = TmdbDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.tmdbDao())
        val appExecutors = AppExecutors()
        return TmdbRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}