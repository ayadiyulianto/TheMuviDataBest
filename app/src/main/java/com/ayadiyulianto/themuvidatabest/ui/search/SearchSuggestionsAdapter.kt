package com.ayadiyulianto.themuvidatabest.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.core.domain.model.SearchItem
import com.ayadiyulianto.themuvidatabest.ui.moviedetail.MovieDetailActivity
import com.ayadiyulianto.themuvidatabest.ui.tvshowdetail.TvShowDetailActivity
import com.ayadiyulianto.themuvidatabest.core.util.Utils
import com.ayadiyulianto.themuvidatabest.databinding.ItemSuggestionsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter

class SearchSuggestionsAdapter(inflater: LayoutInflater?) :
    SuggestionsAdapter<SearchItem, SearchSuggestionsAdapter.SuggestionHolder>(inflater) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
        val itemSearchBinding =
            ItemSuggestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionHolder(itemSearchBinding)
    }

    override fun onBindSuggestionHolder(
        suggestion: SearchItem,
        holder: SuggestionHolder,
        position: Int
    ) {
        holder.bind(suggestion)
    }

    override fun getSingleViewHeight(): Int {
        return 140
    }

    class SuggestionHolder(private val binding: ItemSuggestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestion: SearchItem) {
            with(binding) {
                tvItemTitle.text = suggestion.name
                tvItemDate.text = Utils.changeStringToDateFormat(suggestion.releaseOrAirDate)
                tvItemSynopsis.text = suggestion.overview
                Glide.with(itemView.context)
                    .load(suggestion.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    //show detail page
                    if (suggestion.mediaType == "tv") {
                        val intent = Intent(itemView.context, TvShowDetailActivity::class.java)
                        intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, suggestion.id)
                        itemView.context.startActivity(intent)
                    } else if (suggestion.mediaType == "movie") {
                        val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, suggestion.id)
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }
}