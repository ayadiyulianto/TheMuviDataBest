package com.ayadiyulianto.themuvidatabest.ui.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.databinding.FragmentMoviesBinding
import com.ayadiyulianto.themuvidatabest.di.Injection
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
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        (activity as MainActivity).setToolbarTitle(getString(R.string.movie))
        moviesViewModel = MoviesViewModel(Injection.provideImdbRepository(requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MoviesAdapter()

        val discoverMovies = moviesViewModel.getDiscoverMovies()
        discoverMovies.observe(viewLifecycleOwner, { movies ->
            movieAdapter.setMovies(movies)
        })

        moviesViewModel.isLoading().observe(viewLifecycleOwner, {
            binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
        })

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