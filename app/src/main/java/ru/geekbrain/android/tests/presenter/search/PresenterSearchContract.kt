package ru.geekbrain.android.tests.presenter.search

import ru.geekbrain.android.tests.presenter.PresenterContract

 internal interface PresenterSearchContract: PresenterContract {
     fun searchGitHub(searchQuery: String)
}