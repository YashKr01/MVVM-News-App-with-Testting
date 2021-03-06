package com.example.newsnow.ui.news

import android.os.Bundle
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
import com.example.newsnow.utils.Constants.BREAKING
import com.example.newsnow.utils.Constants.EDUCATION
import com.example.newsnow.utils.Constants.POLITICS
import com.example.newsnow.utils.Constants.SCIENCE
import com.example.newsnow.utils.Constants.SPORTS
import com.example.newsnow.utils.Constants.TECHNOLOGY
import com.example.newsnow.utils.ExtensionFunctions.hide
import com.example.newsnow.utils.ExtensionFunctions.show
import com.example.newsnow.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        binding.swipeRefreshLayout.setOnRefreshListener { newsAdapter.retry() }

        newsAdapter.addLoadStateListener { loadState ->
            binding.apply {

                swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
                newsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                imageError.isVisible = loadState.source.refresh is LoadState.Error
                buttonError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                    newsAdapter.itemCount < 1
                ) {
                    textViewEmpty.show()
                    newsRecyclerView.hide()
                    swipeRefreshLayout.isRefreshing = false
                } else {
                    textViewEmpty.hide()
                    swipeRefreshLayout.isRefreshing = false
                }

            }
        }

        // Recycler View
        binding.newsRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = newsAdapter
                .withLoadStateFooter(footer = NewsLoadStateAdapter { newsAdapter.retry() })
        }

        // Collect list
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsList.observe(viewLifecycleOwner) {
                newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_breaking_news -> viewModel.changeQuery(BREAKING)

                R.id.chip_education -> viewModel.changeQuery(EDUCATION)

                R.id.chip_politics -> viewModel.changeQuery(POLITICS)

                R.id.chip_science -> viewModel.changeQuery(SCIENCE)

                R.id.chip_sports -> viewModel.changeQuery(SPORTS)

                R.id.chip_technology -> viewModel.changeQuery(TECHNOLOGY)

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