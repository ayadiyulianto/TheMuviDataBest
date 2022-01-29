package com.ayadiyulianto.themuvidatabest.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.remote.entity.SearchEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private val dummyTrendings= DataDummy.generateDummySearch()

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<List<SearchEntity>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = SearchViewModel(tmdbRepository)
    }

    @Test
    fun getTrendings() {
        val trendings = MutableLiveData<List<SearchEntity>>()
        trendings.value = dummyTrendings
        Mockito.`when`(tmdbRepository.getTrendings()).thenReturn(trendings)

        val dataListShow = viewModel.getTrendings().value
        Mockito.verify(tmdbRepository).getTrendings()
        assertNotNull(dataListShow)
        assertEquals(2, dataListShow?.size)

        viewModel.getTrendings().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTrendings)
    }

    @Test
    fun getSearchResult() {
        val title = "dummy"
        val results = MutableLiveData<List<SearchEntity>>()
        results.value = dummyTrendings
        Mockito.`when`(tmdbRepository.getSearchResult(title)).thenReturn(results)

        val searchResult = viewModel.getSearchResult(title).value
        Mockito.verify(tmdbRepository).getSearchResult(title)
        assertNotNull(searchResult)
        assertEquals(2, searchResult?.size)

        viewModel.getSearchResult(title).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTrendings)
    }
}