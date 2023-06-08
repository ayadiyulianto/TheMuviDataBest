package com.ayadiyulianto.themuvidatabest.favorite.ui.main.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.favorite.databinding.ItemMoviesTvshowsBinding
import com.ayadiyulianto.themuvidatabest.ui.tvshowdetail.TvShowDetailActivity
import com.ayadiyulianto.themuvidatabest.core.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteTvShowsAdapter: ListAdapter<TvShow, FavoriteTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvShowBinding =
            ItemMoviesTvshowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val show = getItem(position)
        if (show != null) {
            holder.bind(show)
        }
    }

    class TvShowViewHolder(private val binding: ItemMoviesTvshowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: TvShow) {
            with(binding) {
                tvItemTitle.text = show.name
                tvItemDate.text = Utils.changeStringToDateFormat(show.firstAirDate)
                tvItemRating.rating = show.voteAverage.toFloat() / 2
                tvItemSynopsis.text = show.overview
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, TvShowDetailActivity::class.java)
                    intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, show.tvShowId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(show.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }

}