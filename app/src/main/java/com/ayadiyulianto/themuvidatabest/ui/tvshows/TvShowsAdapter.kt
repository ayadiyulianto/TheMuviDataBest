package com.ayadiyulianto.themuvidatabest.ui.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.data.TvShowEntity
import com.ayadiyulianto.themuvidatabest.databinding.ItemMoviesTvshowsBinding
import com.ayadiyulianto.themuvidatabest.ui.tvshowdetail.TvShowDetailActivity
import com.ayadiyulianto.themuvidatabest.util.Utils.changeStringToDateFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowsAdapter: RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder>() {

    private var listTvShows = ArrayList<TvShowEntity>()

    fun setTvShow(shows: List<TvShowEntity>?) {
        if (shows == null) return
        this.listTvShows.clear()
        this.listTvShows.addAll(shows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvShowBinding = ItemMoviesTvshowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val show = listTvShows[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int = listTvShows.size


    class TvShowViewHolder(private val binding: ItemMoviesTvshowsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: TvShowEntity) {
            with(binding) {
                tvItemTitle.text = show.name ?: show.originalName

                tvItemDate.text = changeStringToDateFormat(show.firstAirDate)

                tvItemRating.rating = (show.voteAverage?.toFloat() ?: 0F) / 20

                tvItemSynopsis.text = show.overview

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, TvShowDetailActivity::class.java)
                    intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, show.id)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(show.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

}