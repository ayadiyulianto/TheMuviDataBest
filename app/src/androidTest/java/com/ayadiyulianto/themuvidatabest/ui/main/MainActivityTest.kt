package com.ayadiyulianto.themuvidatabest.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.Utils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyMovie = DataDummy.generateDummyMovie()
    private val dummyTvShow = DataDummy.generateTvShow()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.movieBackdrop)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.moviePoster)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.movieTitle)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieTitle)).check(matches(withText(dummyMovie[0].title)))

        onView(withId(R.id.movieRating)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.movieReleaseDate)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieReleaseDate)).check(matches(withText(
            Utils.changeStringToDateFormat(
                dummyMovie[0].releaseDate
            )
        )))

        onView(withId(R.id.movieDuration)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieDuration)).check(matches(withText(
            Utils.changeMinuteToDurationFormat(
                dummyMovie[0].duration
            )
        )))

        onView(withId(R.id.movieSinopsis)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieSinopsis)).check(matches(withText(dummyMovie[0].description)))
        onView(withId(R.id.ytPlayerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_tvshows)).perform(click())
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_tvshows)).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvShowBackdrop)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowPoster)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.tvShowTitle)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowTitle)).check(matches(withText(dummyTvShow[0].title)))

        onView(withId(R.id.tvShowRating)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.tvShowReleaseDate)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowReleaseDate)).check(matches(withText(
            dummyTvShow[0].seasons[dummyTvShow[0].seasons.size -1].airDate
        )))

        onView(withId(R.id.tvShowDuration)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowDuration)).check(matches(withText(
            Utils.changeMinuteToDurationFormat(
                dummyTvShow[0].duration
            )
        )))

        onView(withId(R.id.tvShowSinopsis)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowSinopsis)).check(matches(withText(dummyTvShow[0].overview)))

        onView(withId(R.id.rv_seasons)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_seasons)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow[0].seasons.size))
        
        onView(withId(R.id.ytPlayerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}