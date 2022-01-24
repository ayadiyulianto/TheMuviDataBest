package com.ayadiyulianto.themuvidatabest.ui.main.favorites.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.databinding.FragmentFavoriteTvShowsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvShowsFragment : Fragment() {

    private var _binding: FragmentFavoriteTvShowsBinding? = null
    private val viewModel: FavoriteTvShowsViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteAdapter = FavoriteTvShowsAdapter()
        with(binding) {
            progressCircular.visibility = View.VISIBLE
            viewModel.getTvShowFav().observe(viewLifecycleOwner, { shows ->
                progressCircular.visibility = View.GONE
                imgNotFound.visibility = if (shows.isEmpty()) View.VISIBLE else View.GONE
                favoriteAdapter.submitList(shows)
            })
        }

        with(binding.rvFavoriteShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}