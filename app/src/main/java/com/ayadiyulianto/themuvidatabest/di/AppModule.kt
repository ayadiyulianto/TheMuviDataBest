package com.ayadiyulianto.themuvidatabest.di

import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbInteractor
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.ui.movies.MoviesViewModel
import com.ayadiyulianto.themuvidatabest.ui.tvshows.TvShowsViewModel
import com.ayadiyulianto.themuvidatabest.ui.moviedetail.MovieDetailViewModel
import com.ayadiyulianto.themuvidatabest.ui.search.SearchViewModel
import com.ayadiyulianto.themuvidatabest.ui.tvshowdetail.TvShowDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TmdbUseCase> { TmdbInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { TvShowsViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { TvShowDetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}