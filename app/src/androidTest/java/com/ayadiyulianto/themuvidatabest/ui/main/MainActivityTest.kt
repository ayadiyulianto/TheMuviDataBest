package com.ayadiyulianto.themuvidatabest.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
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
import com.ayadiyulianto.themuvidatabest.core.util.EspressoIdlingResource
import com.ayadiyulianto.themuvidatabest.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
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
        onView(withId(R.id.movieRating)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieReleaseDate)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieDuration)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieGenres)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movieSinopsis)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.ytPlayerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_tvshows)).perform(click())
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_tvshows)).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvShowBackdrop)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowPoster)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowTitle)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowRating)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowReleaseDate)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowDuration)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowGenres)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tvShowSinopsis)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.rv_seasons)).check(matches(isDisplayed()))
        onView(withId(R.id.ytPlayerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testAddAndRemoveFavoriteMovie(){
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Update Current Status favorite
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).perform(click())

        Espresso.pressBack()
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Re-update the status again so now the status back to first condition before update
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).perform(click())

        Espresso.pressBack()
    }

    @Test
    fun testAddAndRemoveFavoriteTvShow(){
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_tvshows)).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Update Current Status favorite
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).perform(click())

        Espresso.pressBack()
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Re-update the status again so now the status back to first condition before update
        onView(withId(R.id.fabFavorite)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabFavorite)).perform(click())

        Espresso.pressBack()
    }

    @Test
    fun loadMovieFavoritePage() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_favorites)).perform(click())
        val matcher: Matcher<View> = allOf(
            withText(R.string.movie),
            isDescendantOfA(withId(R.id.tab_favorite))
        )
        onView(matcher).perform(click())
        onView(withId(R.id.rv_favorite_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun loadTvShowFavoritePage() {
        onView(withId(R.id.container)).perform(swipeUp())

        onView(withId(R.id.navigation_favorites)).perform(click())
        val matcher: Matcher<View> = allOf(
            withText(R.string.tv_show),
            isDescendantOfA(withId(R.id.tab_favorite))
        )
        onView(matcher).perform(click())
        onView(withId(R.id.rv_favorite_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }
}