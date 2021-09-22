package com.dicoding.moviejetpacklast.ui.homestart

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.dicoding.moviejetpacklast.R
import com.dicoding.moviejetpacklast.utils.EspressoIdlingResource
import org.junit.*

import org.junit.Assert.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeActivityTest {
    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun testLoadMovieAndTvShow() {
        Espresso.onView(withId(R.id.rv_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_movie))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))

        Espresso.onView(withId(R.id.navigation_tvshow)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvshow))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_tvshow))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))

        Espresso.onView(withId(R.id.navigation_movie)).perform(ViewActions.click())
    }

    @Test
    fun testViewTest() {
        Espresso.onView(withId(R.id.rv_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.navigation_tvshow)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvshow))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.navigation_favorite)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.tab_title_movie)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.tab_title_tvshow)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.navigation_movie)).perform(ViewActions.click())
    }

    @Test
    fun testCRUDFavorite() {
        Espresso.onView(withId(R.id.rv_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3,
            ViewActions.click()
        ))
        Espresso.onView(withId(R.id.bt_favorite)).perform(ViewActions.click())
        Espresso.pressBack()

        Espresso.onView(withId(R.id.navigation_tvshow)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvshow))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3,
            ViewActions.click()
        ))
        Espresso.onView(withId(R.id.bt_favorite)).perform(ViewActions.click())
        Espresso.pressBack()

        Espresso.onView(withId(R.id.navigation_favorite)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_favorite_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))
        Espresso.onView(withId(R.id.bt_favorite)).perform(ViewActions.click())
        Espresso.pressBack()

        Espresso.onView(withId(R.id.navigation_favorite)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.tab_title_tvshow)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_favorite_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))
        Espresso.onView(withId(R.id.bt_favorite)).perform(ViewActions.click())
        Espresso.pressBack()
    }

    @Test
    fun testDetailMovie() {
        Espresso.onView(withId(R.id.rv_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_movie))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Espresso.onView(withId(R.id.rv_movie))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3,
                                ViewActions.click()
                        ))

        Espresso.onView(withId(R.id.img_huge))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.img_poster))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_name))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_desc))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.pressBack()
    }

    @Test
    fun testDetailTvShow() {
        Espresso.onView(withId(R.id.navigation_tvshow)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvshow))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_tvshow))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Espresso.onView(withId(R.id.rv_tvshow))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3,
                                ViewActions.click()
                        ))

        Espresso.onView(withId(R.id.img_huge))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.img_poster))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_name))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_desc))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.pressBack()
    }
}