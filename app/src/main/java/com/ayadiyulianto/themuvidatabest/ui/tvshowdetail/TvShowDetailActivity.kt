package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.util.Utils
import com.ayadiyulianto.themuvidatabest.core.util.Utils.changeStringToDateFormat
import com.ayadiyulianto.themuvidatabest.databinding.ActivityTvShowDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

//@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowDetailBinding
    private val tvShowDetailViewModel: TvShowDetailViewModel by viewModel()
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
                getTvShow(showId)
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

    private fun getTvShow(showId: Int) {
        val tvShow = tvShowDetailViewModel.getTvShowDetail(showId)
        tvShow.observe(this) { show ->
            when (show) {
                is Resource.Loading -> binding.contentTvShowDetail.progressCircular.visibility =
                    View.VISIBLE

                is Resource.Success -> {
                    Log.i("result", show.data.toString())
                    binding.contentTvShowDetail.progressCircular.visibility = View.GONE
                    show.data?.let {
                        tvShowDetailViewModel.setSelectedTvShow(it)
                        showDetailTvShow(it)
                        getSeasons(it)
                    }
                }

                is Resource.Error -> {
                    binding.contentTvShowDetail.progressCircular.visibility = View.GONE
                    Toast.makeText(
                        this,
                        getString(R.string.error_while_getting_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getSeasons(tvShow: TvShow) {
        val tvShowWithSeason = tvShowDetailViewModel.getTvShowSeasons(tvShow.tvShowId)
        tvShowWithSeason.observe(this) { seasons ->
            when (seasons) {
                is Resource.Loading -> binding.contentTvShowDetail.progressCircular.visibility =
                    View.VISIBLE

                is Resource.Success -> {
                    Log.i("result", seasons.data.toString())
                    binding.contentTvShowDetail.progressCircular.visibility = View.GONE
                    seasons.data?.seasons?.let {
                        seasonAdapter.submitList(it)
                    }
                }

                is Resource.Error -> {
                    binding.contentTvShowDetail.progressCircular.visibility = View.GONE
                    Toast.makeText(
                        this,
                        getString(R.string.error_while_getting_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showDetailTvShow(showDetails: TvShow) {
        with(binding) {
            setFabIcon(showDetails.favorited)
            toolbarLayout.title = showDetails.name
            tvShowBackdrop.alpha = 0.75F
            contentTvShowDetail.tvShowTitle.text = showDetails.name
            contentTvShowDetail.tvShowSinopsis.text = showDetails.overview
            contentTvShowDetail.tvShowReleaseDate.text =
                changeStringToDateFormat(showDetails.firstAirDate)
            contentTvShowDetail.tvShowRating.rating =
                showDetails.voteAverage.toFloat() / 2
            contentTvShowDetail.tvShowDuration.text =
                showDetails.runtime.let { Utils.changeMinuteToDurationFormat(it) }
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

        setYTPlayer(showDetails.youtubeTrailerId)
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