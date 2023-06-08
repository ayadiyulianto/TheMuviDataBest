package com.ayadiyulianto.themuvidatabest.favorite

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.favorite.databinding.ActivityFavoriteBinding
import com.ayadiyulianto.themuvidatabest.favorite.di.favoriteModule
import com.ayadiyulianto.themuvidatabest.favorite.ui.main.FavoriteTabsAdapter
import com.ayadiyulianto.themuvidatabest.ui.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.context.loadKoinModules

//@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.title = getString(R.string.title_favorite)
        binding.toolbar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        loadKoinModules(favoriteModule)

        with(binding) {
            val tabsAdapter = FavoriteTabsAdapter(this@FavoriteActivity)
            viewPagerFavorite.adapter = tabsAdapter
            TabLayoutMediator(tabs, viewPagerFavorite) { tab, pos ->
                tab.text = resources.getString(TAB_TITLES[pos])
            }.attach()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movie,
            R.string.tv_show
        )
    }
}