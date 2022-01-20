package com.ayadiyulianto.themuvidatabest.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.EspressoIdlingResource
import com.ayadiyulianto.themuvidatabest.util.Utils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyMovie = DataDummy.generateDummyMovie()
    private val dummyTvShow = DataDummy.generateDummyTvShow()

    private val dummyMovieDetail = DataDummy.generateDummyMovieDetail()
    private val dummyTvShowDetail = DataDummy.generateDummyTvShowDetail()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovie() {
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

        onView(withId(R.id.movieTitle)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieTitle)).check(matches(withText(dummyMovieDetail[0].title)))

        onView(withId(R.id.movieRating)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.movieReleaseDate)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieReleaseDate)).check(matches(withText(
            Utils.changeStringToDateFormat(
                dummyMovieDetail[0].releaseDate
            )
        )))

        onView(withId(R.id.movieDuration)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieDuration)).check(matches(withText(
            dummyMovieDetail[0].runtime?.let {
                Utils.changeMinuteToDurationFormat(
                    it
                )
            }
        )))

        onView(withId(R.id.movieSinopsis)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieSinopsis)).check(matches(withText(dummyMovieDetail[0].overview)))
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.ytPlayerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.moviePoster)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieBackdrop)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun loadTvShow() {
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

        onView(withId(R.id.tvShowTitle)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowTitle)).check(matches(withText(dummyTvShowDetail[0].name)))

        onView(withId(R.id.tvShowRating)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.tvShowReleaseDate)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowReleaseDate)).check(matches(withText(
            Utils.changeStringToDateFormat(
                dummyTvShowDetail[0].releaseDate
            )
        )))

        onView(withId(R.id.tvShowDuration)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowDuration)).check(matches(withText(
            dummyTvShowDetail[0].runtime?.get(0)?.let {
                Utils.changeMinuteToDurationFormat(
                    it
                )
            }
        )))

        onView(withId(R.id.tvShowSinopsis)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowSinopsis)).check(matches(withText(dummyTvShowDetail[0].overview)))
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.ytPlayerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowPoster)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowBackdrop)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.rv_seasons)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_seasons)).perform(dummyTvShowDetail[0].seasons?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                it.size)
        })
    }
}