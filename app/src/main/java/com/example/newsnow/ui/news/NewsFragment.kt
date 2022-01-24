package com.example.newsnow.ui.news

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnow.R
import com.example.newsnow.adapters.news.NewsPagingAdapter
import com.example.newsnow.databinding.FragmentNewsBinding
import com.example.newsnow.data.network.paging.NewsLoadStateAdapter
import com.example.newsnow.utils.ExtensionFunctions.hide
import com.example.newsnow.utils.ExtensionFunctions.show
import com.example.newsnow.viewmodels.NewsViewModel
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)

        // Paging Adapter
        val newsAdapter = NewsPagingAdapter(
            onItemClick = { article ->
                val action = NewsFragmentDirections.actionNewsFragmentToWebFragment(article)
                findNavController().navigate(action)
            },
            onBookmarkClick = { article ->
                if (article.isBookmarked) viewModel.deleteArticle(article)
                else viewModel.insertArticle(article)
            }
        )

        binding.buttonError.setOnClickListener { newsAdapter.retry() }

        newsAdapter.addLoadStateListener { loadState ->
            binding.apply {

                swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
                newsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                imageError.isVisible = loadState.source.refresh is LoadState.Error
                buttonError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && newsAdapter.itemCount < 1
                ) {
                    textViewEmpty.show()
                    newsRecyclerView.hide()
                } else textViewEmpty.hide()

            }
        }

        // Recycler View
        binding.newsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = newsAdapter
                .withLoadStateFooter(footer = NewsLoadStateAdapter { newsAdapter.retry() })
        }

        // Collect list
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsList.collectLatest {
                newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentQuery.collectLatest { query ->
                when(query) {
                    "Breaking" -> {
                        binding.chipBreakingNews.isChecked = true
                    }
                    "Education" -> {
                        binding.chipEducation.isChecked = true
                    }
                    "Politics" -> {
                        binding.chipPolitics.isChecked = true
                    }
                    "Science" -> {
                        binding.chipScience.isChecked = true
                    }
                    "Technology" -> {
                        binding.chipTechnology.isChecked = true
                    }
                }
            }
        }

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_breaking_news -> viewModel.setCurrentQuery("Breaking")
                R.id.chip_education -> viewModel.setCurrentQuery("Education")
                R.id.chip_politics -> viewModel.setCurrentQuery("Politics")
                R.id.chip_science -> viewModel.setCurrentQuery("Science")
                R.id.chip_sports -> viewModel.setCurrentQuery("Sports")
                R.id.chip_technology -> viewModel.setCurrentQuery("Technology")
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.main_menu_bookmark)
            findNavController().navigate(R.id.action_newsFragment_to_savedFragment)
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}