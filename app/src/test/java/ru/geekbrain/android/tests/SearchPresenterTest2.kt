package ru.geekbrain.android.tests

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrain.android.tests.presenter.search.SearchPresenter
import ru.geekbrain.android.tests.view.search.MainActivity


@RunWith(AndroidJUnit4::class)
class SearchPresenterTest2 {
    lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun activityOnAttach_ViewIsNotNull(){
        scenario.onActivity {

        }
        scenario.moveToState(Lifecycle.State.CREATED)
        Assert.assertNotNull(presenter.getView())
    }
}