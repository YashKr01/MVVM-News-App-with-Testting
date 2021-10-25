package com.example.newsnow.ui.news

import android.content.Intent
import android.net.Uri
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
import com.example.newsnow.adapters.NewsPagingAdapter
import com.example.newsnow.databinding.FragmentNewsBinding
import com.example.newsnow.paging.NewsLoadStateAdapter
import com.example.newsnow.utils.ExtensionFunctions.hide
import com.example.newsnow.utils.ExtensionFunctions.show
import com.example.newsnow.viewmodels.NewsViewModel
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
        val adapter = NewsPagingAdapter(
            onItemClick = { article ->
                val uri = Uri.parse(article.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                requireActivity().startActivity(intent)
            },
            onBookmarkClick = { article ->
                if (article.isBookmarked) viewModel.deleteArticle(article)
                else viewModel.insertArticle(article)
            }
        )

        binding.buttonError.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
            binding.apply {

                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                newsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                imageError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    textViewEmpty.show()
                    newsRecyclerView.hide()
                } else {
                    textViewEmpty.hide()
                }
            }
        }

        // Recycler View
        binding.newsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            this.adapter =
                adapter.withLoadStateFooter(footer = NewsLoadStateAdapter { adapter.retry() })
        }

        // Collect list
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsList.collectLatest {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saved_news) findNavController().navigate(R.id.action_newsFragment_to_savedFragment)
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}