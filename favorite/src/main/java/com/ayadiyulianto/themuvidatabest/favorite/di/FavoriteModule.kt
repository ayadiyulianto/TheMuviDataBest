package com.ayadiyulianto.themuvidatabest.favorite.di

import com.ayadiyulianto.themuvidatabest.favorite.ui.main.movies.FavoriteMoviesViewModel
import com.ayadiyulianto.themuvidatabest.favorite.ui.main.tvshows.FavoriteTvShowsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteMoviesViewModel(get()) }
    viewModel { FavoriteTvShowsViewModel(get()) }
}