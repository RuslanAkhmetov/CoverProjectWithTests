package ru.geekbrain.android.tests.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.geekbrain.android.tests.model.SearchResponse

internal interface GitHubApi {
    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("search/repositories")
    fun searchGithub(@Query("q") term: String?): Call<SearchResponse?>?

}