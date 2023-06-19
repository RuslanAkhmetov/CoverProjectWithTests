package ru.geekbrain.android.tests.presenter.details

import ru.geekbrain.android.tests.presenter.PresenterContract
import ru.geekbrain.android.tests.view.ViewContract
import ru.geekbrain.android.tests.view.details.ViewDetailsContract

internal interface PresenterDetailsContract: PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}