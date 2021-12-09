package com.example.newsnow.ui.saved

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnow.R
import com.example.newsnow.adapters.saved.SavedNewsAdapter
import com.example.newsnow.databinding.FragmentSavedBinding
import com.example.newsnow.viewmodels.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SavedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)

        val adapter = SavedNewsAdapter(onItemClick = { article ->
            val uri = Uri.parse(article.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireActivity().startActivity(intent)
        }, onDeleteClick = { article ->
            viewModel.deleteArticle(article)
        })

        binding.savedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getNewsList().collectLatest { savedNews ->
                adapter.submitList(savedNews)
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.saved_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saved_news_delete) viewModel.deleteAllNews()
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}