package ru.geekbrain.android.tests.view.search

import ru.geekbrain.android.tests.model.SearchResult
import ru.geekbrain.android.tests.view.ViewContract

internal interface ViewSearchContract : ViewContract{
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()

    fun displayError(error: String)

    fun displayLoading(show: Boolean)
}