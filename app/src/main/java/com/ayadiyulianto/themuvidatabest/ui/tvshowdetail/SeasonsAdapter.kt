package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.core.domain.model.Season
import com.ayadiyulianto.themuvidatabest.databinding.ItemSeasonsBinding
import com.ayadiyulianto.themuvidatabest.core.util.Utils.changeStringDateToYear
import com.ayadiyulianto.themuvidatabest.core.util.Utils.changeStringToDateFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class SeasonsAdapter : ListAdapter<Season, SeasonsAdapter.SeasonViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val itemsSeasonDetailBinding =
            ItemSeasonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonViewHolder(itemsSeasonDetailBinding)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = getItem(position)
        holder.bind(season)
    }

    class SeasonViewHolder(private val binding: ItemSeasonsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(season: Season) {
            with(binding) {
                "Season ${season.seasonNumber}".also { tvItemTitle.text = it }
                "${changeStringDateToYear(season.airDate)} | ${season.episodeCount} Eps.".also {
                    tvItemYear.text = it
                }
                "Season ${season.seasonNumber} premiered on ${
                    changeStringToDateFormat(
                        season.airDate
                    )
                }.".also { tvItemPremiere.text = it }
                season.overview.also { tvItemDescription.text = it }

                Glide.with(itemView.context)
                    .load(season.posterPath)
                    .transform(RoundedCorners(16))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Season>() {
            override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
                return oldItem.seasonId == newItem.seasonId
            }

            override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
                return oldItem == newItem
            }
        }
    }
}