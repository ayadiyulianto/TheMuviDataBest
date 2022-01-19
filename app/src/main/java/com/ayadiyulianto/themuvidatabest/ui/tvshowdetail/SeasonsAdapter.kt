package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.data.TvShowSeasonEntity
import com.ayadiyulianto.themuvidatabest.databinding.ItemSeasonsBinding
import com.ayadiyulianto.themuvidatabest.util.Utils.changeStringDateToYear
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class SeasonsAdapter: RecyclerView.Adapter<SeasonsAdapter.SeasonViewHolder>() {

    private var listSeason = ArrayList<TvShowSeasonEntity>()

    fun setSeason(seasons: List<TvShowSeasonEntity>?) {
        if (seasons == null) return
        this.listSeason.clear()
        this.listSeason.addAll(seasons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val itemsSeasonDetailBinding = ItemSeasonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonViewHolder(itemsSeasonDetailBinding)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = listSeason[position]
        holder.bind(season)
    }

    override fun getItemCount(): Int = listSeason.size

    class SeasonViewHolder(private val binding: ItemSeasonsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(season: TvShowSeasonEntity) {
            with(binding) {
                "Season ${season.seasonNumber}".also { tvItemTitle.text = it }
                "${season.airDate?.let { changeStringDateToYear(it) }} | ${season.episodeCount} Eps.".also { tvItemYear.text = it }
                "Season ${season.seasonNumber} premiered on ${season.airDate}.".also { tvItemPremiere.text = it }
                season.overview.also { tvItemDescription.text = it }

                Glide.with(itemView.context)
                    .load(season.posterPath)
                    .transform(RoundedCorners(16))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }
}