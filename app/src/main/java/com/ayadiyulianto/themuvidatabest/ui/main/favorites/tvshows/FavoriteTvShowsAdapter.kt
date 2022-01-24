package com.ayadiyulianto.themuvidatabest.ui.main.favorites.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.databinding.ItemMoviesTvshowsBinding
import com.ayadiyulianto.themuvidatabest.ui.tvshowdetail.TvShowDetailActivity
import com.ayadiyulianto.themuvidatabest.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteTvShowsAdapter: PagedListAdapter<TvShowEntity, FavoriteTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

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
        fun bind(show: TvShowEntity) {
            with(binding) {
                tvItemTitle.text = show.name
                tvItemDate.text = Utils.changeStringToDateFormat(show.firstAirDate)
                tvItemRating.rating = (show.voteAverage?.toFloat() ?: 0F) / 2
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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}