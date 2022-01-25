package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.util.Utils.changeStringToDateFormat
import com.ayadiyulianto.themuvidatabest.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowDetailBinding
    private val tvShowDetailViewModel: TvShowDetailViewModel by viewModels()
    private lateinit var seasonAdapter: SeasonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        seasonAdapter = SeasonsAdapter()

        with(binding.contentTvShowDetail.rvSeasons) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@TvShowDetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = seasonAdapter
        }

        val extras = intent.extras
        if (extras != null) {
            val showId = extras.getInt(EXTRA_TV_SHOW)
            if (showId != 0) {
                val showDetails = tvShowDetailViewModel.getTvShowDetail(showId)
                showDetails.observe(this, { res ->
                    when (res.status) {
                        Status.LOADING -> binding.contentTvShowDetail.progressCircular.visibility =
                            View.VISIBLE
                        Status.SUCCESS -> {
                            Log.i("result", res.data.toString())
                            binding.contentTvShowDetail.progressCircular.visibility = View.GONE
                            res.data?.let { showDetailTvShow(it) }

                            val showSeasons = tvShowDetailViewModel.getTvShowWithSeason(showId)
                            showSeasons.observe(this, { tvShowSeason ->
                                if (tvShowSeason != null) {
                                    showDetailTvShow(tvShowSeason.mTvShow)
                                    seasonAdapter.submitList(tvShowSeason.mSeason)
                                    // binding.contentTvShowDetail.cvSeasons.requestLayout()
                                }
                            })
                        }
                        Status.ERROR -> {
                            binding.contentTvShowDetail.progressCircular.visibility = View.GONE
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
            val newState = tvShowDetailViewModel.setFavorite()
            if (newState) {
                Toast.makeText(this, R.string.addedToFavorite, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.removedFromFavorite, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDetailTvShow(showDetails: TvShowEntity) {
        with(binding) {
            setFabIcon(showDetails.favorited)
            toolbarLayout.title = showDetails.name
            tvShowBackdrop.alpha = 0.75F
            contentTvShowDetail.tvShowTitle.text = showDetails.name
            contentTvShowDetail.tvShowSinopsis.text = showDetails.overview
            contentTvShowDetail.tvShowReleaseDate.text =
                changeStringToDateFormat(showDetails.firstAirDate)
            contentTvShowDetail.tvShowRating.rating =
                (showDetails.voteAverage?.toFloat() ?: 0F) / 2
            contentTvShowDetail.tvShowDuration.text =
                showDetails.runtime?.let { Utils.changeMinuteToDurationFormat(it) }
            contentTvShowDetail.tvShowGenres.text = showDetails.genres
        }

        Glide.with(this)
            .load(showDetails.posterPath)
            .transform(RoundedCorners(16))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.contentTvShowDetail.tvShowPoster)

        Glide.with(this)
            .load(showDetails.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.tvShowBackdrop)

        showDetails.youtubeTrailerId?.let { setYTPlayer(it) }
    }

    private fun setFabIcon(isFavorited: Boolean) {
        if (isFavorited) {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
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