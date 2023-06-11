package com.ayadiyulianto.themuvidatabest.core.di

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