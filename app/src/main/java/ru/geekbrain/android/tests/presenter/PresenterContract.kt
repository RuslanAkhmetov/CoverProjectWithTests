package ru.geekbrain.android.tests.presenter

import ru.geekbrain.android.tests.view.ViewContract

internal interface PresenterContract {

    fun onAttach(view: ViewContract)

    fun onDetach()
}