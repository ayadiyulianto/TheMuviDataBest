package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.data.MovieDetailEntity
import com.ayadiyulianto.themuvidatabest.databinding.ActivityMovieDetailBinding
import com.ayadiyulianto.themuvidatabest.di.Injection
import com.ayadiyulianto.themuvidatabest.util.Utils.changeMinuteToDurationFormat
import com.ayadiyulianto.themuvidatabest.util.Utils.changeStringToDateFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieDetailViewModel = MovieDetailViewModel(Injection.provideImdbRepository(this))

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getLong(EXTRA_MOVIE)
            if (movieId != 0L) {
                val movieDetails = movieDetailViewModel.getMovie(movieId.toString())
                movieDetails.observe(this, { movie ->
                    showDetailMovie(movie)
                })
            }
        }

        binding.fabFavorite.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun showDetailMovie(movieDetails: MovieDetailEntity) {
        binding.toolbarLayout.title = movieDetails.title ?: movieDetails.originalTitle
        binding.movieBackdrop.alpha = 0.75F
        binding.contentMovieDetail.movieTitle.text = movieDetails.title ?: movieDetails.originalTitle
        binding.contentMovieDetail.movieSinopsis.text = movieDetails.overview
        binding.contentMovieDetail.movieReleaseDate.text = changeStringToDateFormat(movieDetails.releaseDate)
        binding.contentMovieDetail.movieRating.rating = (movieDetails.voteAverage?.toFloat() ?: 0F) /20
        binding.contentMovieDetail.movieDuration.text = movieDetails.runtime?.let {
            changeMinuteToDurationFormat(
                it
            )
        }
        binding.contentMovieDetail.movieGenres.text =
            movieDetails.genres?.joinToString(separator = " • ") ?: "-"

        Glide.with(this)
            .load(movieDetails.posterPath)
            .transform(RoundedCorners(16))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.contentMovieDetail.moviePoster )

        Glide.with(this)
            .load(movieDetails.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.movieBackdrop)

//        setYTPlayer(movieDetails.youtubeVideoId)
    }

    private fun setYTPlayer(videoId: String) {
        val youTubePlayerView = binding.contentMovieDetail.ytPlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
}