package ru.geekbrain.android.tests.view.details

import ru.geekbrain.android.tests.view.ViewContract

internal interface ViewDetailsContract: ViewContract {
    fun setCount(count: Int)
}