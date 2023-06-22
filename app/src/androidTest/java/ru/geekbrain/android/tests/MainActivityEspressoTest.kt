package ru.geekbrain.android.tests

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrain.android.tests.view.details.DetailsActivity
import ru.geekbrain.android.tests.view.search.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)

    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"))
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        if (BuildConfig.BTYPE == "FAKE") {
            onView(withId(R.id.totalTextView)).check(matches(withText("Number of results: 42")))
        } else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.totalTextView)).check(matches(withText("Number of results: 3796")))
        }
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityEditSearchTextView_NotNull() {
        scenario.onActivity {
            val editSearchTextView = it.findViewById<EditText>(R.id.searchEditText)
            TestCase.assertNotNull(editSearchTextView)
        }
    }

    @Test
    fun activityTotalCountTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalTextView)
            TestCase.assertNotNull(totalCountTextView)
        }
    }

    @Test
    fun activityToDetailActivityButton_NotNull() {
        scenario.onActivity {
            val toDetailActivityButton = it.findViewById<Button>(R.id.toDetailsActivityButton)
            TestCase.assertNotNull(toDetailActivityButton)
        }
    }

    @Test
    fun activityToDetailActivityButton_Working() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"))
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(withId(R.id.toDetailsActivityButton)).perform(click())
        scenario.close()
        val scenarioDetails: ActivityScenario<DetailsActivity> = ActivityScenario.launch(DetailsActivity::class.java)
        scenarioDetails.onActivity {
            TestCase.assertNotNull(it)
        }
        scenarioDetails.close()

    }

    @Test
    fun editTextSearch() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("ios"))
        onView(withId(R.id.searchEditText)).check(matches(withText("ios")))
    }


    private fun delay(): ViewAction? = object : ViewAction {
        override fun getDescription(): String = "wait for 2 seconds"
        override fun getConstraints(): Matcher<View> = isRoot()

        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadForAtLeast(2000)
        }
    }


    @After
    fun close() {
        scenario.close()
    }
}