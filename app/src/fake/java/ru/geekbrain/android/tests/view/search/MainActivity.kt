package ru.geekbrain.android.tests.view.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import org.koin.android.ext.android.inject
import ru.geekbrain.android.tests.R

import ru.geekbrain.android.tests.databinding.ActivityMainBinding
import ru.geekbrain.android.tests.model.SearchResult
import ru.geekbrain.android.tests.presenter.search.PresenterSearchContract
import ru.geekbrain.android.tests.presenter.search.SearchPresenter
import ru.geekbrain.android.tests.repository.RepositoryContract
import ru.geekbrain.android.tests.repository.fake.FakeGitHubRepository
import ru.geekbrain.android.tests.view.SearchResultAdapter
import ru.geekbrain.android.tests.view.details.DetailsActivity
import java.util.*

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private var totalCount = 0

    private val adapter = SearchResultAdapter()

    private val repository: RepositoryContract by inject()
    private val presenter: PresenterSearchContract = SearchPresenter(repository)

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

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        this.totalCount = totalCount

        with(binding.totalTextView){
            visibility = View.VISIBLE
            text = String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
        }

        adapter.updateResults(searchResults)
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

}