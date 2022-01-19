package com.ayadiyulianto.themuvidatabest.di

import android.content.Context
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.remote.RemoteDataSource

object Injection {
    fun provideImdbRepository(context: Context): TmdbRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return TmdbRepository.getInstance(remoteDataSource)
    }
}