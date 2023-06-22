package ru.geekbrain.android.tests.repository

import retrofit2.Response
import ru.geekbrain.android.tests.model.SearchResponse

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}