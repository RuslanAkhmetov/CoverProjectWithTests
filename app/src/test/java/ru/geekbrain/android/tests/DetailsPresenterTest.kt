package ru.geekbrain.android.tests

import android.os.Build
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.geekbrain.android.tests.presenter.details.DetailsPresenter
import ru.geekbrain.android.tests.view.details.DetailsActivity

const val COUNT = 42

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    lateinit var scenario: ActivityScenario<DetailsActivity>

    @Before
    fun setUp(){
        presenter = DetailsPresenter()
        presenter.setCounter(COUNT)
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
        scenario.onActivity {
            presenter.onAttach(it)
        }

    }

    @Test
    fun onIncrement_Successful(){
        presenter.onIncrement()
        val result = COUNT + 1
        scenario.onActivity {
            val totalCounterTextView = it.findViewById<TextView>(R.id.totalTextView)
            assertEquals("Number of results: $result", totalCounterTextView.text )
        }
    }

    @Test
    fun onDecrement_Successful(){
        presenter.onDecrement()
        val result = COUNT - 1
        scenario.onActivity {
            val totalCounterTextView = it.findViewById<TextView>(R.id.totalTextView)
            assertEquals("Number of results: $result", totalCounterTextView.text )
        }

    }

    @Test
    fun onDetach_ViewContractIsNotEqualsToScenarioOnActivity(){
        presenter.onDetach()
        //Так поле viewContract Презентера приватное и мы не можем проверить его на null
        // Проверяем что астивити отвалилось и не получает сообщений от презентера
        presenter.onIncrement()
        val result = COUNT + 1
        scenario.onActivity {
            val totalCounterTextView = it.findViewById<TextView>(R.id.totalTextView)
            assertNotEquals("Number of results: $result", totalCounterTextView.text )
        }
    }

    @After
    fun close() {
        scenario.close()
    }


}