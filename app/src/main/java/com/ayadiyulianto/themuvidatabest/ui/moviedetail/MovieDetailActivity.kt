package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.databinding.ActivityMovieDetailBinding
import com.ayadiyulianto.themuvidatabest.util.Utils.changeMinuteToDurationFormat
import com.ayadiyulianto.themuvidatabest.util.Utils.changeStringToDateFormat
import com.ayadiyulianto.themuvidatabest.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIE)
            if (movieId != 0) {
                val movieDetails = movieDetailViewModel.getMovieDetail(movieId)
                movieDetails.observe(this, { res ->
                    when (res.status) {
                        Status.LOADING -> binding.contentMovieDetail.progressCircular.visibility =
                            View.VISIBLE
                        Status.SUCCESS -> {
                            Log.i("result", res.data.toString())
                            binding.contentMovieDetail.progressCircular.visibility = View.GONE
                            res.data?.let { showDetailMovie(it) }
                        }
                        Status.ERROR -> {
                            binding.contentMovieDetail.progressCircular.visibility = View.GONE
                            Toast.makeText(
                                this,
                                getString(R.string.error_while_getting_data),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }

        binding.fabFavorite.setOnClickListener {
            val newState = movieDetailViewModel.setFavorite()
            if (newState) {
                Toast.makeText(this, R.string.addedToFavorite, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.removedFromFavorite, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDetailMovie(movieDetails: MovieEntity) {
        with(binding) {
            setFabIcon(movieDetails.favorited ?: false)
            toolbarLayout.title = movieDetails.title
            movieBackdrop.alpha = 0.75F
            contentMovieDetail.movieTitle.text = movieDetails.title
            contentMovieDetail.movieSinopsis.text = movieDetails.overview
            contentMovieDetail.movieReleaseDate.text =
                changeStringToDateFormat(movieDetails.releaseDate)
            contentMovieDetail.movieRating.rating =
                (movieDetails.voteAverage?.toFloat() ?: 0F) / 2
            contentMovieDetail.movieDuration.text = movieDetails.runtime?.let {
                changeMinuteToDurationFormat(
                    it
                )
            }
            contentMovieDetail.movieGenres.text = movieDetails.genres
        }

        Glide.with(this)
            .load(movieDetails.posterPath)
            .transform(RoundedCorners(16))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.contentMovieDetail.moviePoster)

        Glide.with(this)
            .load(movieDetails.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.movieBackdrop)

        movieDetails.youtubeTrailerId?.let { setYTPlayer(it) }
    }

    private fun setFabIcon(isFavorited: Boolean) {
        if (isFavorited) {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
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