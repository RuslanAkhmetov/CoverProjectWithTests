package ru.geekbrain.android.tests.view.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import ru.geekbrain.android.tests.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrain.android.tests.databinding.ActivityMainBinding
import ru.geekbrain.android.tests.model.SearchResult
import ru.geekbrain.android.tests.presenter.search.PresenterSearchContract
import ru.geekbrain.android.tests.presenter.search.SearchPresenter
import ru.geekbrain.android.tests.repository.*
import ru.geekbrain.android.tests.view.SearchResultAdapter
import ru.geekbrain.android.tests.view.details.DetailsActivity

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private var totalCount = 0

    private val adapter = SearchResultAdapter()

    private val presenter: PresenterSearchContract = SearchPresenter(createRepository())

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter.onAttach(this)
        setContentView(binding.root)
        setUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    private fun setUI() {
        binding.toDetailsActivityButton.setOnClickListener{
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                    return@OnEditorActionListener true
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnEditorActionListener false
                }
            }
            false
        })
    }

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        adapter.updateResults(searchResults)
        this.totalCount = totalCount
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

}