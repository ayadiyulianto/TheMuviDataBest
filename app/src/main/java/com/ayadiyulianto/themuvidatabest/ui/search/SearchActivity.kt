package com.ayadiyulianto.themuvidatabest.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.ayadiyulianto.themuvidatabest.databinding.ActivitySearchBinding
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchSuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearch()
    }

    private fun initSearch() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        searchAdapter = SearchSuggestionsAdapter(inflater)
        binding.searchBar.setCustomSuggestionAdapter(searchAdapter)

        binding.searchBar.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence) {
                //startSearch(text.toString(), true, null, true);
            }

            override fun onButtonClicked(buttonCode: Int) {
                when (buttonCode) {
                    MaterialSearchBar.BUTTON_NAVIGATION -> {
                        finish()
                    }
                    MaterialSearchBar.BUTTON_BACK -> {
                        binding.searchBar.clearSuggestions()
                        binding.searchBar.closeSearch()
                    }
                }
            }
        })

        binding.searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.searchBar.setCustomSuggestionAdapter(searchAdapter)

                if (binding.searchBar.text.isNotEmpty()) {
                    val results = searchViewModel.getSearchResult(binding.searchBar.text)

                    results.observe(this@SearchActivity, { detail ->
                        searchAdapter.suggestions = detail
                        searchAdapter.notifyDataSetChanged()
                        binding.searchBar.showSuggestionsList()
                    })

                    searchViewModel.getLoading().observe(this@SearchActivity, {
                        if (it) {
                            binding.progressCircular.visibility = View.VISIBLE
                        } else {
                            binding.progressCircular.visibility = View.GONE
                        }
                    })
                } else {
                    binding.searchBar.clearSuggestions()
                    binding.searchBar.hideSuggestionsList()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.searchBar.setSuggestionsClickListener(object :
            SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemClickListener(position: Int, v: View) {}
            override fun OnItemDeleteListener(position: Int, v: View) {}
        })
    }
}