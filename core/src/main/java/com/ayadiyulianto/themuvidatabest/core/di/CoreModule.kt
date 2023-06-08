package com.ayadiyulianto.themuvidatabest.core.di

import com.ayadiyulianto.themuvidatabest.core.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.core.data.source.local.LocalDataSource
import com.ayadiyulianto.themuvidatabest.core.data.source.local.room.TmdbDatabase
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.network.ApiService
import com.ayadiyulianto.themuvidatabest.core.domain.repository.ITmdbRepository
import com.ayadiyulianto.themuvidatabest.core.util.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single { TmdbDatabase.getInstance(androidContext()) }
    factory { get<TmdbDatabase>().tmdbDao() }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource.getInstance(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ITmdbRepository> {
        TmdbRepository.getInstance(
            get(),
            get(),
            get()
        )
    }
}