package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.databinding.ActivityTvShowDetailBinding
import com.ayadiyulianto.themuvidatabest.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import androidx.recyclerview.widget.DividerItemDecoration
import com.ayadiyulianto.themuvidatabest.data.TvShowDetailEntity
import com.ayadiyulianto.themuvidatabest.di.Injection
import com.ayadiyulianto.themuvidatabest.util.Utils.changeStringToDateFormat

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowDetailBinding
    private lateinit var tvShowDetailViewModel: TvShowDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvShowDetailViewModel = TvShowDetailViewModel(Injection.provideImdbRepository(this))

        val extras = intent.extras
        if (extras != null) {
            val showId = extras.getLong(EXTRA_TV_SHOW)
            if (showId != 0L) {
                val showDetails = tvShowDetailViewModel.getTvShow(showId.toString())
                showDetails.observe(this, { tvShow ->
                    showDetailTvShow(tvShow)
                })
            }
        }

        binding.fabFavorite.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun showDetailTvShow(showDetails: TvShowDetailEntity) {
        val listOfSeason = showDetails.seasons
        val seasonAdapter = SeasonsAdapter()
        seasonAdapter.setSeason(listOfSeason)

        with(binding.contentTvShowDetail.rvSeasons) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@TvShowDetailActivity,
                    DividerItemDecoration.VERTICAL
                ))
            adapter = seasonAdapter
        }
        
        binding.toolbarLayout.title = showDetails.name ?: showDetails.originalName
        binding.tvShowBackdrop.alpha = 0.75F
        binding.contentTvShowDetail.tvShowTitle.text = showDetails.name ?: showDetails.originalName
        binding.contentTvShowDetail.tvShowSinopsis.text = showDetails.overview
        binding.contentTvShowDetail.tvShowReleaseDate.text = changeStringToDateFormat(showDetails.releaseDate)
        binding.contentTvShowDetail.tvShowRating.rating = (showDetails.voteAverage?.toFloat() ?: 0F) /20
        binding.contentTvShowDetail.tvShowDuration.text =
            showDetails.runtime?.get(0)?.let { Utils.changeMinuteToDurationFormat(it) }
        binding.contentTvShowDetail.tvShowGenres.text =
            showDetails.genres?.joinToString(separator = " • ") ?: "-"

        Glide.with(this)
            .load(showDetails.posterPath)
            .transform(RoundedCorners(16))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.contentTvShowDetail.tvShowPoster )

        Glide.with(this)
            .load(showDetails.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.tvShowBackdrop)

//        setYTPlayer(showDetails.youtubeVideoId)
    }

    private fun setYTPlayer(videoId: String) {
        val youTubePlayerView = binding.contentTvShowDetail.ytPlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }
}