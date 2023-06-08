package com.ayadiyulianto.themuvidatabest.favorite.ui.main.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.ui.moviedetail.MovieDetailActivity
import com.ayadiyulianto.themuvidatabest.core.util.Utils
import com.ayadiyulianto.themuvidatabest.favorite.databinding.ItemMoviesTvshowsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteMoviesAdapter: ListAdapter<Movie, FavoriteMoviesAdapter.MoviesViewHolder>(DIFF_CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsSeasonDetailBinding = ItemMoviesTvshowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsSeasonDetailBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MoviesViewHolder(private val binding: ItemMoviesTvshowsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemDate.text = Utils.changeStringToDateFormat(movie.releaseDate)
                tvItemRating.rating = movie.voteAverage.toFloat() / 2
                tvItemSynopsis.text = movie.overview
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                    intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie.movieId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}