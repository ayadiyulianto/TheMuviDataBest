package com.ayadiyulianto.themuvidatabest.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.NetworkBoundResource
import com.ayadiyulianto.themuvidatabest.data.TmdbDataSource
import com.ayadiyulianto.themuvidatabest.data.source.local.LocalDataSource
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.*
import com.ayadiyulianto.themuvidatabest.data.source.remote.ApiResponse
import com.ayadiyulianto.themuvidatabest.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.data.source.remote.entity.SearchEntity
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.*
import com.ayadiyulianto.themuvidatabest.util.AppExecutors
import com.ayadiyulianto.themuvidatabest.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONArray

class FakeTmdbRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): TmdbDataSource {

    private val _isLoading = MutableLiveData<Boolean>()

    override fun getDiscoverMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object: NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItemMovie>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ResultsItemMovie>>> =
                remoteDataSource.getDiscoverMovie()

            public override fun saveCallResult(data: List<ResultsItemMovie>) {
                val movies = ArrayList<MovieEntity>()
                for(response in data){
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.overview,
                        response.posterPath,
                        response.backdropPath,
                        response.releaseDate,
                        response.voteAverage,
                        null,
                        null
                    )
                    movies.add(movie)
                }
                localDataSource.insertMovies(movies)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: String): LiveData<Resource<MovieEntity>> {
        return object: NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data?.runtime == null

            public override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovie(movieId)

            public override fun saveCallResult(data: MovieDetailResponse) {
                val listOfGenre = ArrayList<String>()

                for (genre in (data.genres)!!){
                    listOfGenre.add(genre!!.name!!)
                }

                val movie = MovieEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.posterPath,
                    data.backdropPath,
                    data.releaseDate,
                    data.voteAverage!!,
                    data.runtime,
                    JSONArray(listOfGenre).toString()
                )
                localDataSource.updateMovie(movie)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun getDiscoverTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object: NetworkBoundResource<PagedList<TvShowEntity>, List<ResultsItemTvShow>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(10)
                    setPageSize(10)
                }.build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ResultsItemTvShow>>> =
                remoteDataSource.getDiscoverTvShow()

            public override fun saveCallResult(data: List<ResultsItemTvShow>) {
                val shows = ArrayList<TvShowEntity>()
                for(response in data) {
                    val show = TvShowEntity(
                        response.id,
                        response.name,
                        response.overview,
                        response.posterPath,
                        response.backdropPath,
                        response.voteAverage,
                        response.firstAirDate,
                        null,
                        null
                    )
                    shows.add(show)
                }
                localDataSource.insertTvShow(shows)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(showId: String): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowDetailResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvShowById(showId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data?.runtime == null

            public override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShow(showId)

            public override fun saveCallResult(data:TvShowDetailResponse) {
                val listOfGenre = ArrayList<String>()
                val listOfSeason = ArrayList<SeasonEntity>()

                for (genre in (data.genres)!!){
                    listOfGenre.add(genre!!.name!!)
                }

                for(season in(data.seasons)!!){
                    if (season != null) {
                        val s = SeasonEntity(
                            season.id,
                            data.id,
                            season.name,
                            season.overview,
                            season.airDate,
                            season.seasonNumber,
                            season.episodeCount,
                            season.posterPath.toString()
                        )
                        listOfSeason.add(s)
                    }
                }

                val show = TvShowEntity(
                    data.id,
                    data.name,
                    data.overview,
                    data.posterPath,
                    data.backdropPath,
                    data.voteAverage,
                    data.firstAirDate,
                    JSONArray(listOfGenre).toString(),
                    data.episodeRunTime!![0]
                )
                localDataSource.updateTvShow(show)
                localDataSource.insertSeason(listOfSeason)
            }
        }.asLiveData()
    }

    override fun getTvShowWithSeason(showId: String): LiveData<Resource<TvShowWithSeason>> {
        return object : NetworkBoundResource<TvShowWithSeason, TvShowDetailResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<TvShowWithSeason> =
                localDataSource.getTvShowWithSeason(showId)

            override fun shouldFetch(data: TvShowWithSeason?): Boolean =
                data?.mSeason == null || data.mSeason.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShow(showId)

            public override fun saveCallResult(data:TvShowDetailResponse) {
                val listOfGenre = ArrayList<String>()
                val listOfSeason = ArrayList<SeasonEntity>()

                for (genre in (data.genres)!!){
                    listOfGenre.add(genre!!.name!!)
                }

                for(season in(data.seasons)!!){
                    if (season != null) {
                        val s = SeasonEntity(
                            season.id,
                            data.id,
                            season.name,
                            season.overview,
                            season.airDate,
                            season.seasonNumber,
                            season.episodeCount,
                            season.posterPath.toString()
                        )
                        listOfSeason.add(s)
                    }
                }

                val show = TvShowEntity(
                    data.id,
                    data.name,
                    data.overview,
                    data.posterPath,
                    data.backdropPath,
                    data.voteAverage,
                    data.firstAirDate,
                    JSONArray(listOfGenre).toString(),
                    data.episodeRunTime!![0]
                )
                localDataSource.updateTvShow(show)
                localDataSource.insertSeason(listOfSeason)
            }
        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        CoroutineScope(IO).launch {
            localDataSource.setMovieFavorite(movie, newState)
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean){
        CoroutineScope(IO).launch {
            localDataSource.setTvShowFavorite(tvShow, newState)
        }
    }

    override fun getSearchResult(title: String): LiveData<List<SearchEntity>> {
        _isLoading.value = true
        val listOfResult = MutableLiveData<List<SearchEntity>>()
        CoroutineScope(IO).launch{
            remoteDataSource.getSearchResult(title, object : RemoteDataSource.CallbackLoadSearchResult{
                override fun onSearchResultRecieved(showResponse: List<SearchResultsItem?>?) {
                    val res = ArrayList<SearchEntity>()
                    if (showResponse != null) {
                        for(responseSearch in showResponse){
                            if(responseSearch!!.mediaType == "tv" || responseSearch.mediaType == "movie"){
                                val resSearch = SearchEntity(
                                    responseSearch.id,
                                    if(responseSearch.mediaType == "tv") responseSearch.name else responseSearch.title,
                                    responseSearch.posterPath,
                                    responseSearch.backdropPath,
                                    responseSearch.mediaType,
                                    responseSearch.overview,
                                    responseSearch.voteAverage,
                                    if(responseSearch.mediaType == "tv") responseSearch.firstAirDate else responseSearch.releaseDate
                                )

                                if(!res.contains(resSearch)){
                                    res.add(resSearch)
                                }
                            }
                        }
                    }
                    _isLoading.postValue(false)
                    listOfResult.postValue(res)
                }
            })
        }
        return listOfResult
    }

    override fun getTrendings(): LiveData<List<SearchEntity>> {
        _isLoading.value = true
        val listOfResult = MutableLiveData<List<SearchEntity>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getTrendings(
                object : RemoteDataSource.CallbackLoadTrendings {
                    override fun onTrendingsRecieved(showResponse: List<SearchResultsItem?>?) {
                        val res = ArrayList<SearchEntity>()
                        if (showResponse != null) {
                            for (responseSearch in showResponse) {
                                if (responseSearch != null) {
                                    if (responseSearch.mediaType == "tv" || responseSearch.mediaType == "movie") {
                                        //for return result
                                        val resSearch = SearchEntity(
                                            responseSearch.id,
                                            if (responseSearch.mediaType == "tv") responseSearch.name else responseSearch.title,
                                            responseSearch.posterPath,
                                            responseSearch.backdropPath,
                                            responseSearch.mediaType,
                                            responseSearch.overview,
                                            responseSearch.voteAverage,
                                            if (responseSearch.mediaType == "tv") responseSearch.firstAirDate else responseSearch.releaseDate
                                        )

                                        if (!res.contains(resSearch)) {
                                            res.add(resSearch)
                                        }
                                    }
                                }
                            }
                        }
                        _isLoading.postValue(false)
                        listOfResult.postValue(res)
                    }
                })
        }
        return listOfResult
    }
}