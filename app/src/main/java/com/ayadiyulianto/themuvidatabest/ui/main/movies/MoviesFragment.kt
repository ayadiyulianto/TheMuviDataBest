package com.ayadiyulianto.themuvidatabest.ui.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.databinding.FragmentMoviesBinding
import com.ayadiyulianto.themuvidatabest.ui.main.MainActivity
import com.ayadiyulianto.themuvidatabest.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MoviesAdapter()

        moviesViewModel.getDiscoverMovies().observe(viewLifecycleOwner, { res ->
            when (res.status) {
                Status.LOADING -> binding.progressCircular.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    movieAdapter.submitList(res.data)
                }
                Status.ERROR -> {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(
                        context,
                        getString(R.string.error_while_getting_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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