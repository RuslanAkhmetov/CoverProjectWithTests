package ru.geekbrain.android.tests.presenter.search

import android.util.Log
import retrofit2.Response
import ru.geekbrain.android.tests.model.SearchResponse
import ru.geekbrain.android.tests.repository.RepositoryCallback
import ru.geekbrain.android.tests.repository.RepositoryContract
import ru.geekbrain.android.tests.view.ViewContract
import ru.geekbrain.android.tests.view.search.ViewSearchContract

internal class SearchPresenter internal constructor(

    private val repository: RepositoryContract
) : PresenterSearchContract, RepositoryCallback {

    private var viewContract: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(view: ViewContract) {
        viewContract = view as ViewSearchContract
    }


    override fun onDetach() {
        viewContract = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCounts = searchResponse?.totalCount
            Log.i("TAG", "handleGitHubResponse: $totalCounts")
            if (!searchResults.isNullOrEmpty() && totalCounts != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCounts
                )
            } else {
                viewContract?.displayError("Response is null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }

    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }

}