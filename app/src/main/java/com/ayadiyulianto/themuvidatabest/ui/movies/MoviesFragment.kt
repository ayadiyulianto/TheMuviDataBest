package com.ayadiyulianto.themuvidatabest.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.databinding.FragmentMoviesBinding
import com.ayadiyulianto.themuvidatabest.ui.main.MainActivity

class MoviesFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel
    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moviesViewModel =
            ViewModelProvider(this)[MoviesViewModel::class.java]

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        (activity as MainActivity).setToolbarTitle(getString(R.string.movie))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movies = moviesViewModel.getMovies()

        val movieAdapter = MoviesAdapter()
        movieAdapter.setMovies(movies)

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}