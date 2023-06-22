package ru.geekbrain.android.tests.repository.fake

import retrofit2.Response
import ru.geekbrain.android.tests.model.SearchResponse
import ru.geekbrain.android.tests.model.SearchResult
import ru.geekbrain.android.tests.repository.RepositoryCallback
import ru.geekbrain.android.tests.repository.RepositoryContract

internal class FakeGitHubRepository : RepositoryContract {
    override fun searchGithub(query: String, callback: RepositoryCallback) {
        callback.handleGitHubResponse(
            Response.success(
                SearchResponse(
                    42,
                    listOf(SearchResult(
                        0,
                        "Fake",
                        "FakeRepo",
                        true,
                        "Repo for tests",
                        "01.01.2000",
                        0,
                        0,
                        "NO",
                        false,
                        false,
                        0.0)
                )
                )
            )
        )
    }
}