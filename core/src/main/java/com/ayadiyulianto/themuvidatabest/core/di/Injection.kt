package com.ayadiyulianto.themuvidatabest.core.di

import android.content.Context
import com.ayadiyulianto.themuvidatabest.core.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.core.data.source.local.LocalDataSource
import com.ayadiyulianto.themuvidatabest.core.data.source.local.room.TmdbDatabase
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.network.ApiService
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbInteractor
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.core.util.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object Injection {
//
//    @Singleton
//    @Provides
//    @TmdbUseCaseSingletonQualifier
//    fun provideTmdbRepository(@ApplicationContext context: Context): TmdbUseCase {
//        val database = TmdbDatabase.getInstance(context)
//        val remoteDataSource = RemoteDataSource.getInstance(getApiService())
//        val localDataSource = LocalDataSource.getInstance(database.tmdbDao())
//        val appExecutors = AppExecutors()
//        val repository = TmdbRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
//        return TmdbInteractor(repository)
//    }
//
//    private fun getApiService(): ApiService {
//        val loggingInterceptor =
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        return retrofit.create(ApiService::class.java)
//    }
//}
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class TmdbUseCaseSingletonQualifier