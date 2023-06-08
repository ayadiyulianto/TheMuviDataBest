package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.util.Utils.changeMinuteToDurationFormat
import com.ayadiyulianto.themuvidatabest.core.util.Utils.changeStringToDateFormat
import com.ayadiyulianto.themuvidatabest.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

//@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModel()

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
                movieDetails.observe(this) { movie ->
                    when (movie) {
                        is Resource.Loading -> binding.contentMovieDetail.progressCircular.visibility =
                            View.VISIBLE

                        is Resource.Success -> {
                            Log.i("result", movie.data.toString())
                            binding.contentMovieDetail.progressCircular.visibility = View.GONE
                            movie.data?.let {
                                movieDetailViewModel.setMovie(it)
                                showDetailMovie(it)
                            }
                        }

                        is Resource.Error -> {
                            binding.contentMovieDetail.progressCircular.visibility = View.GONE
                            Toast.makeText(
                                this,
                                getString(R.string.error_while_getting_data),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
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

    private fun showDetailMovie(movieDetails: Movie) {
        with(binding) {
            setFabIcon(movieDetails.favorited)
            toolbarLayout.title = movieDetails.title
            movieBackdrop.alpha = 0.75F
            contentMovieDetail.movieTitle.text = movieDetails.title
            contentMovieDetail.movieSinopsis.text = movieDetails.overview
            contentMovieDetail.movieReleaseDate.text =
                changeStringToDateFormat(movieDetails.releaseDate)
            contentMovieDetail.movieRating.rating =
                movieDetails.voteAverage.toFloat() / 2
            contentMovieDetail.movieDuration.text = changeMinuteToDurationFormat(
                movieDetails.runtime
            )
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

        setYTPlayer(movieDetails.youtubeTrailerId)
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