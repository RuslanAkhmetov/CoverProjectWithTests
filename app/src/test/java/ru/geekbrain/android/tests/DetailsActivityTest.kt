package ru.geekbrain.android.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.geekbrain.android.tests.view.details.DetailsActivity


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailsActivityTest {

    lateinit var scenario: ActivityScenario<DetailsActivity>

    lateinit var context:Context


    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun close() {
        scenario.close()
    }



    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalTextView)
            assertNotNull(totalCountTextView)
        }
    }

    @Test
    fun activityTextView_HasText(){
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalTextView)
            assertEquals("Number of results: 0", totalCountTextView.text)
        }
    }

    @Test
    fun activity_isVisible(){
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalTextView)
            assertEquals(View.VISIBLE, totalCountTextView.visibility)
        }
    }

    @Test
    fun activityButtons_AreVisible() {
        scenario.onActivity {
            val decrementButton = it.findViewById<Button>(R.id.decrementButton)
            assertEquals(View.VISIBLE, decrementButton.visibility)

            val incrementButton = it.findViewById<Button>(R.id.incrementButton)
            assertEquals(View.VISIBLE, incrementButton.visibility)
        }
    }

    @Test
    fun activityButtonIncrement_IsWorking() {
        scenario.onActivity {
            val incrementButton = it.findViewById<Button>(R.id.incrementButton)
            val totalCountTextView = it.findViewById<TextView>(R.id.totalTextView)
            incrementButton.performClick()
            totalCountTextView.performClick()
            assertEquals("Number of results: 1", totalCountTextView.text)
        }
    }

    @Test
    fun activityButtonDecrement_IsWorking() {
        scenario.onActivity {
            val decrementButton = it.findViewById<Button>(R.id.decrementButton)
            val totalCountTextView =
                it.findViewById<TextView>(R.id.totalTextView)
            decrementButton.performClick()
            assertEquals("Number of results: -1", totalCountTextView.text)
        }
    }

    @Test
    fun activityCreateIntent_NotNull() {

        val intent = DetailsActivity.getIntent(context, 0)
        assertNotNull(intent)
    }

    @Test
    fun activityCreateIntent_HasExtras() {

        val intent = DetailsActivity.getIntent(context, 0)
        val bundle = intent.extras
        assertNotNull(bundle)
    }
    @Test
    fun activityCreateIntent_HasCount() {
        val count = 42

        val intent = DetailsActivity.getIntent(context, count)
        val bundle = intent.extras
        assertEquals(count, bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, 0))


    }





}