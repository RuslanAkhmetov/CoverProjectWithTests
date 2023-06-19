package ru.geekbrain.android.tests

import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrain.android.tests.model.SearchResponse
import ru.geekbrain.android.tests.repository.GitHubApi
import ru.geekbrain.android.tests.repository.GitHubRepository

class GitHubRepositoryTest {

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchRepos() {
        val searchQuery = "some Query"
        val call = mock(Call::class.java) as Call<SearchResponse?>

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)

        repository.searchGithub(
            searchQuery,
            mock(GitHubRepository.GitHubRepositoryCallback::class.java)
        )

        verify(gitHubApi, times(1))
            .searchGithub(searchQuery)

    }

    @Test
    fun searchGitHub_TestCallBack() {
        val searchQuery = "some Query"
        val response = mock(Response::class.java) as Response<SearchResponse?>
        val gitHubRepositoryCallback =
            mock(GitHubRepository.GitHubRepositoryCallback::class.java)

        val call = object : Call<SearchResponse?> {
            override fun clone(): Call<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

            override fun enqueue(callback: Callback<SearchResponse?>) {
                callback.onResponse(this, response)
                callback.onFailure(this, Throwable())
            }

        }

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)

        repository.searchGithub(searchQuery, gitHubRepositoryCallback)

        verify(gitHubRepositoryCallback, times(1))
            .handleGitHubResponse(response)
        verify(gitHubRepositoryCallback, times(1))
            .handleGitHubError()

    }

    @Test
    fun searchGitHub_TestCallBack_WithMock() {
        val searchQuery = "some Query"
        val callMocked = mock(Call::class.java) as Call<SearchResponse?>

        val callbackMocked = mock(Callback::class.java) as Callback<SearchResponse?>

        val gitHubRepositoryCallbackMocked =
            mock(GitHubRepository.GitHubRepositoryCallback::class.java)
        val responseMocked = mock(Response::class.java) as Response<SearchResponse?>

        `when`(gitHubApi.searchGithub(searchQuery))
            .thenReturn(callMocked)

        `when`(callMocked.enqueue(callbackMocked)).then {
            callbackMocked.onResponse(any(), any())
        }

        `when`(callbackMocked.onResponse(any(), any())).then {
            gitHubRepositoryCallbackMocked.handleGitHubResponse(responseMocked)
        }

        repository.searchGithub(searchQuery, gitHubRepositoryCallbackMocked)

        verify(gitHubRepositoryCallbackMocked, times(1))
            .handleGitHubResponse(responseMocked)

    }


}