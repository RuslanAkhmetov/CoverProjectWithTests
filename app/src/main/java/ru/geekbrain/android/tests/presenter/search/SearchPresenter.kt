package ru.geekbrain.android.tests.presenter.search

import retrofit2.Response
import ru.geekbrain.android.tests.model.SearchResponse
import ru.geekbrain.android.tests.repository.GitHubRepository
import ru.geekbrain.android.tests.view.search.ViewSearchContract

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: GitHubRepository
) : PresenterSearchContract, GitHubRepository.GitHubRepositoryCallback {

    override fun searchGitHub(searchQuery: String) {
        viewContract.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach() {
        TODO("Not yet implemented")
    }

    override fun onDetach() {
        TODO("Not yet implemented")
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCounts = searchResponse?.totalCount
            if (!searchResults.isNullOrEmpty() && totalCounts != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCounts
                )
            } else {
                viewContract.displayError("Response is null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }

    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }

}