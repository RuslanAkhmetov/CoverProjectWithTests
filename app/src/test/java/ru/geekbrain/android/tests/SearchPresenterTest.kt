package ru.geekbrain.android.tests

import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Response
import ru.geekbrain.android.tests.model.SearchResponse
import ru.geekbrain.android.tests.model.SearchResult
import ru.geekbrain.android.tests.presenter.search.SearchPresenter
import ru.geekbrain.android.tests.repository.GitHubRepository
import ru.geekbrain.android.tests.view.search.ViewSearchContract

class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Before
    fun setUp() {
        //initMocks(this)
        initMocks(this)
        presenter = SearchPresenter(viewContract, repository)
    }


    @Test
    fun searchGitHub_Test() {
        val searchQuery = "some query"

        presenter.searchGitHub(searchQuery)

        Mockito.verify(repository, times(1)).searchGithub(searchQuery, presenter)
    }

    @Test //Проверяем работу метода handleGitHubError()
    fun handleGitHubError_Test() {
        //Вызываем у Презентера метод handleGitHubError()
        presenter.handleGitHubError()
        //Проверяем, что у viewContract вызывается метод displayError()
        Mockito.verify(viewContract, times(1)).displayError()
    }

    @Test
    fun handleGitHubResponseUnsuccessful() {
        val response =
            Mockito.mock(/* classToMock = */ Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        assertFalse(response.isSuccessful)
    }

    @Test
    fun handleGitHubResponse_Failure() {
        //Создаем мок ответа сервера с типом Response<SearchResponse?>?

        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        //Описываем правило, что при вызове метода isSuccessful должен возвращаться false
        //В таком случае должен вызываться метод viewContract.displayError(...)
        Mockito.`when`(response.isSuccessful).thenReturn(false)

        //Вызывваем у Презентера метод handleGitHubResponse()
        presenter.handleGitHubResponse(response)

        //Убеждаемся, что вызывается верный метод:
        //viewContract.displayError("Response is null or unsuccessful"), и что он
        //вызывается единожды

        Mockito.verify(viewContract, times(1))
            .displayError("Response is null or unsuccessful")

    }

    @Test
    fun handleGitHubResponse_ResponseIsEmpty() {
        //Создаем мок ответа сервера с типом Response<SearchResponse?>?
        val response =
            Mockito.mock(/* classToMock = */ Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response != null && response.isSuccessful).thenReturn(true)

        Mockito.`when`(response.body()).thenReturn(null)

        presenter.handleGitHubResponse(response)

        Mockito.verify(viewContract, times(1))
            .displayError("Response is null")
    }

    @Test
    fun handleGitHubResponse_ResponseIsMotEmpty() {
        //Создаем мок ответа сервера с типом Response<SearchResponse?>?
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        val searchResults =
            listOf(
                SearchResult(
                    0, "Name",
                    "FullName", false,
                    "Description", "Updated",
                    1, 0, "EN", false, false, 0.0
                )
            )
        Mockito.`when`(response != null && response.isSuccessful).thenReturn(true)

        Mockito.`when`(response.body()).thenReturn(SearchResponse(1, searchResults))
        //или
        //Mockito.`when`(response.body()).thenReturn(Mockito.mock(SearchResponse::class.java))

        presenter.handleGitHubResponse(response)

        Mockito.verify(viewContract, times(1)).displaySearchResults(
            searchResults,
            1
        )
    }

    @Test
    fun handleGitHubResponse_Success() {
        //Создаем мок ответа сервера с типом Response<SearchResponse?>?
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        val searchResponse = Mockito.mock(SearchResponse::class.java)
        val searchResults = listOf(Mockito.mock(SearchResult::class.java))
        Mockito.`when`(response != null && response.isSuccessful).thenReturn(true)

        Mockito.`when`(response.body()).thenReturn(searchResponse)
        Mockito.`when`(searchResponse.searchResults).thenReturn(searchResults)
        Mockito.`when`(searchResponse.totalCount).thenReturn(101)
        presenter.handleGitHubResponse(response)

        Mockito.verify(viewContract, times(1))
            .displaySearchResults(searchResults, 101)
    }

    @Test
    fun handleGitHubResponse_ResponseFailure_ViewContractMethodOrder(){
        val mockedResponse = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(mockedResponse.isSuccessful).thenReturn(false)

        presenter.handleGitHubResponse(mockedResponse)

        //Определяем порядок вызова методов какого класса мы хотим проверить
        val inOrder = inOrder(viewContract)
        //Прописываем порядок вызова методов
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError("Response is null or unsuccessful")
    }
}