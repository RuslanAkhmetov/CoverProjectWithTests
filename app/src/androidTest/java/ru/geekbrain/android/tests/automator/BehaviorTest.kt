package ru.geekbrain.android.tests.automator

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.core.app.ApplicationProvider
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import androidx.test.uiautomator.Until.findObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.geekbrain.android.tests.BuildConfig

class BehaviorTest {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val uiDevice = UiDevice.getInstance(getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setUp() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
            uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
        }
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        /*Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())*/

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))

        searchButton?.let { it.click() }

        //Ожидаем конкретного события: появления текстового поля totalCountTextView.
        //Это будет означать, что сервер вернул ответ с какими-то данными, то есть запрос  отработал.

        val changedText =
            uiDevice.wait(findObject(By.res(packageName, "totalTextView")), TIMEOUT)

        //Убеждаемся, что сервер вернул корректный результат. Обратите внимание, что количество
        //результатов может варьироваться во времени, потому что количество репозиториев   постоянно меняется.
        val expectedResult: Int =
            if (BuildConfig.BTYPE == "FAKE") {
                42
            } else {
                812
            }
        Assert.assertEquals("Number of results: $expectedResult", changedText.text.toString())
    }

    @Test
    fun search_ForTheEmptyWord(){
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton?.let { it.click() }

        val toast = uiDevice.findObject(UiSelector().className(Toast::class.java.name))
        Assert.assertNotNull(toast)

    }

    @Test
    fun test_DetailsScreenButton(){
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails?.let{it.click()}
        val changedText =
            uiDevice.wait(findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        var result = 0
        Assert.assertEquals("Number of results: $result", changedText.text.toString())
        val incrementButton =
            uiDevice.findObject(By.res(packageName, "incrementButton"))
        incrementButton.click()
        Assert.assertEquals("Number of results: ${++result}", changedText.text.toString())
        val decrementButton =
            uiDevice.findObject(By.res(packageName, "decrementButton"))
        decrementButton.click()
        Assert.assertEquals("Number of results: ${--result}", changedText.text.toString())
        uiDevice.pressBack()
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        Assert.assertNotNull(searchButton)
    }

    @Test
    fun test_OpenDetailsScreen(){
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))

        searchButton?.let { it.click() }

        val expectedResult: Int =
            if (BuildConfig.BTYPE == "FAKE") {
                42
            } else {
                812
            }

        val totalCount =
            uiDevice.wait(
                findObject(By.res(packageName, "totalTextView")),
                TIMEOUT)

        Assert.assertEquals("Number of results: $expectedResult", totalCount.text.toString())

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails?.let{it.click()}

        val changedText =
            uiDevice.wait(findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)

        Assert.assertEquals("Number of results: $expectedResult", changedText.text.toString())
    }

}