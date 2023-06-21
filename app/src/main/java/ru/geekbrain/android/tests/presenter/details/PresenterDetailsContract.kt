package ru.geekbrain.android.tests.presenter.details

import ru.geekbrain.android.tests.presenter.PresenterContract

internal interface PresenterDetailsContract: PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}