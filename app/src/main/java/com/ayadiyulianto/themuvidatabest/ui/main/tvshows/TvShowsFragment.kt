package com.ayadiyulianto.themuvidatabest.ui.main.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayadiyulianto.themuvidatabest.R
import com.ayadiyulianto.themuvidatabest.databinding.FragmentTvShowsBinding
import com.ayadiyulianto.themuvidatabest.di.Injection
import com.ayadiyulianto.themuvidatabest.ui.main.MainActivity

class TvShowsFragment : Fragment() {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private var _binding: FragmentTvShowsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)

        (activity as MainActivity).setToolbarTitle(getString(R.string.tv_show))
        tvShowsViewModel = TvShowsViewModel(Injection.provideImdbRepository(requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowAdapter = TvShowsAdapter()

        val discoverTvShows = tvShowsViewModel.getDiscoverTvShow()
        discoverTvShows.observe(viewLifecycleOwner, { shows ->
            tvShowAdapter.setTvShow(shows)
        })

        tvShowsViewModel.isLoading().observe(viewLifecycleOwner, {
            binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
        })

        with(binding.rvTvshows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}