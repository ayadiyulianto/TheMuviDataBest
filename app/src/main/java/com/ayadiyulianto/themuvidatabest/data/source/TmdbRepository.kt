package com.ayadiyulianto.themuvidatabest.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ayadiyulianto.themuvidatabest.data.*
import com.ayadiyulianto.themuvidatabest.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TmdbRepository private constructor(private val remoteDataSource: RemoteDataSource): TmdbDataSource {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500"

    companion object{
        @Volatile
        private var instance: TmdbRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): TmdbRepository =
            instance ?: synchronized(this){
                instance ?: TmdbRepository(remoteDataSource)
            }
    }

    override fun getDiscoverMovies(): LiveData<List<MovieEntity>> {
        _isLoading.value = true
        val listOfMovie = MutableLiveData<List<MovieEntity>>()
        CoroutineScope(IO).launch{
            remoteDataSource.getDiscoverMovie(object : RemoteDataSource.CallbackLoadDiscoverMovie{
                override fun onMoviesRecieved(movieResponse: List<ResultsItemMovie>) {
                    val movies = ArrayList<MovieEntity>()
                    for(response in movieResponse){
                        val movie = MovieEntity(
                                response.overview,
                                response.originalTitle,
                                response.title,
                                "$imageBaseUrl${response.posterPath}",
                                "$imageBaseUrl${response.backdropPath}",
                                response.releaseDate,
                                response.voteAverage,
                                response.id
                        )
                        movies.add(movie)
                    }
                    _isLoading.postValue(false)
                    listOfMovie.postValue(movies)
                }
            })
        }
        return listOfMovie
    }

    override fun getDiscoverTvShows(): LiveData<List<TvShowEntity>> {
        _isLoading.value = true
        val listOfShow = MutableLiveData<List<TvShowEntity>>()
        CoroutineScope(IO).launch{
            remoteDataSource.getDiscoverTvShow(object : RemoteDataSource.CallbackLoadDiscoverTvShow{
                override fun onTvShowRecieved(showResponse: List<ResultsItemTvShow>) {
                    val shows = ArrayList<TvShowEntity>()
                    for(response in showResponse){
                        val show = TvShowEntity(
                            response.overview,
                            response.originalName,
                            response.name,
                            "$imageBaseUrl${response.posterPath}",
                            "$imageBaseUrl${response.backdropPath}",
                            response.firstAirDate,
                            response.voteAverage,
                            response.id
                        )
                        shows.add(show)
                    }
                    _isLoading.postValue(false)
                    listOfShow.postValue(shows)
                }
            })
        }
        return listOfShow
    }

    override fun getMovieDetail(movieId: String): LiveData<MovieDetailEntity> {
        _isLoading.value = true
        val movieResult = MutableLiveData<MovieDetailEntity>()
        CoroutineScope(IO).launch{
            remoteDataSource.getMovie(movieId, object : RemoteDataSource.CallbackLoadMovieDetail{
                override fun onMovieDetailsRecieved(movieResponse: MovieDetailResponse) {
                    val listOfGenre = ArrayList<String>()

                    for (genre in (movieResponse.genres)!!){
                        listOfGenre.add(genre!!.name!!)
                    }

                    val movie = MovieDetailEntity(
                        movieResponse.id,
                        movieResponse.title,
                        "$imageBaseUrl${movieResponse.backdropPath}",
                        listOfGenre,
                        movieResponse.voteCount,
                        movieResponse.overview,
                        movieResponse.originalTitle,
                        movieResponse.runtime,
                        "$imageBaseUrl${movieResponse.posterPath}",
                        movieResponse.releaseDate,
                        movieResponse.voteAverage,
                        movieResponse.videos?.toYoutubeVideoIds()
                    )
                    _isLoading.postValue(false)
                    movieResult.postValue(movie)
                }
            })
        }
        return movieResult
    }

    override fun getTvShowDetail(showId: String): MutableLiveData<TvShowDetailEntity> {
        _isLoading.value = true
        val showResult = MutableLiveData<TvShowDetailEntity>()
        CoroutineScope(IO).launch{
            remoteDataSource.getTvShow(showId, object : RemoteDataSource.CallbackLoadTvShowDetail{
                override fun onTvShowDetailsRecieved(showResponse: TvShowDetailResponse) {
                    val listOfGenre = ArrayList<String>()
                    val listOfSeasonData = ArrayList<TvShowSeasonEntity>()

                    for (genre in (showResponse.genres)!!){
                        listOfGenre.add(genre!!.name!!)
                    }

                    for (data in (showResponse.seasons)!!){
                        val season = TvShowSeasonEntity(
                            data?.airDate,
                            data?.episodeCount,
                            data?.id,
                            data?.name,
                            data?.overview,
                            "$imageBaseUrl${data?.posterPath as String?}",
                            data?.seasonNumber
                        )
                        listOfSeasonData.add(season)
                    }

                    val show = TvShowDetailEntity(
                        showResponse.id,
                        showResponse.name,
                        "$imageBaseUrl${showResponse.backdropPath}",
                        listOfGenre,
                        showResponse.voteCount,
                        showResponse.overview,
                        listOfSeasonData,
                        showResponse.originalName,
                        showResponse.episodeRunTime,
                        "$imageBaseUrl${showResponse.posterPath}",
                        showResponse.firstAirDate,
                        showResponse.voteAverage,
                        showResponse.videos?.toYoutubeVideoIds()
                    )
                    _isLoading.postValue(false)
                    showResult.postValue(show)
                }
            })
        }
        return showResult
    }
}