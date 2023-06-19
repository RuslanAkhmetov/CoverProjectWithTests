package ru.geekbrain.android.tests.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrain.android.tests.R
import ru.geekbrain.android.tests.databinding.ActivityDetailsBinding
import ru.geekbrain.android.tests.presenter.details.DetailsPresenter
import java.util.Locale

class DetailsActivity : AppCompatActivity(), ViewDetailsContract {

    companion object {
        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int): Intent =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }

    }

    lateinit var binding: ActivityDetailsBinding

    private val presenterDetailsContract = DetailsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUi()
    }

    private fun setUi() {
        val count = intent.getIntExtra(TOTAL_COUNT_EXTRA, 0)
        presenterDetailsContract.setCounter(count)
        binding.decrementButton.setOnClickListener {
            presenterDetailsContract.onDecrement()
        }

        binding.incrementButton.setOnClickListener {
            presenterDetailsContract.onIncrement()
        }

        setCountText(count)
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    private fun setCountText(count: Int) {
        binding.totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }
}

