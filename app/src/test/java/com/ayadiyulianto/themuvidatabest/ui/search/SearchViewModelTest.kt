package com.ayadiyulianto.themuvidatabest.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.SearchItem
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.core.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.util.getOrAwaitValue
import kotlinx.coroutines.flow.flowOf
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
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<SearchItem>>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = SearchViewModel(tmdbUseCase)
    }

    @Test
    fun getTrendings() {
        val dummy = Resource.Success(dummyTrendings)
        val trendings = flowOf(dummy)
        Mockito.`when`(tmdbUseCase.getTrendings()).thenReturn(trendings)

        val dataListShow = viewModel.getTrendings().getOrAwaitValue()
        Mockito.verify(tmdbUseCase).getTrendings()
        assertNotNull(dataListShow)
        assertEquals(2, dataListShow.data?.size ?: 0)

        viewModel.getTrendings().observeForever(observer)
        Mockito.verify(observer).onChanged(dummy)
    }

    @Test
    fun getSearchResult() {
        val title = "dummy"
        val dummy = Resource.Success(dummyTrendings)
        val results = flowOf(dummy)
        Mockito.`when`(tmdbUseCase.getSearchResult(title)).thenReturn(results)

        val searchResult = viewModel.getSearchResult(title).getOrAwaitValue()
        Mockito.verify(tmdbUseCase).getSearchResult(title)
        assertNotNull(searchResult)
        assertEquals(2, searchResult.data?.size ?: 0)

        viewModel.getSearchResult(title).observeForever(observer)
        Mockito.verify(observer).onChanged(dummy)
    }
}