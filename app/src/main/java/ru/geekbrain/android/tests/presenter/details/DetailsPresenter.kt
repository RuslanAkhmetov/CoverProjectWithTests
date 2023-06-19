package ru.geekbrain.android.tests.presenter.details

import ru.geekbrain.android.tests.view.ViewContract
import ru.geekbrain.android.tests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private var count: Int = 0
): PresenterDetailsContract{

    private var  viewContract: ViewDetailsContract? = null

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    override fun onAttach(view: ViewContract) {
        this.viewContract = view as ViewDetailsContract
    }

    override fun onDetach() {
        viewContract = null
    }
}