package com.ayadiyulianto.themuvidatabest.ui.main.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.databinding.FragmentFavoritesBinding
import com.ayadiyulianto.themuvidatabest.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        (activity as MainActivity).setToolbarTitle(getString(R.string.title_favorite))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val tabsAdapter = FavoriteTabsAdapter(activity as MainActivity)
            viewPagerFavorite.adapter = tabsAdapter
            TabLayoutMediator(tabFavorite, viewPagerFavorite) { tab, pos ->
                tab.text = resources.getString(TAB_TITLES[pos])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movie,
            R.string.tv_show
        )
    }
}