package com.example.newsnow.ui.saved

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnow.R
import com.example.newsnow.adapters.saved.SavedNewsAdapter
import com.example.newsnow.databinding.FragmentSavedBinding
import com.example.newsnow.viewmodels.SavedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SavedViewModel>()
    private lateinit var savedNewsAdapter: SavedNewsAdapter

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

        savedNewsAdapter = SavedNewsAdapter(onItemClick = { article ->
            val action = SavedFragmentDirections.actionSavedFragmentToWebFragment(article)
            findNavController().navigate(action)
        })

        binding.savedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = savedNewsAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getNewsList().collectLatest { savedNews ->
                savedNewsAdapter.submitList(savedNews)
            }
        }

        ItemTouchHelper(helper).attachToRecyclerView(binding.savedRecyclerView)

        setHasOptionsMenu(true)
    }

    private val helper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.bindingAdapterPosition
            val deletedArticle = savedNewsAdapter.currentList[position]

            viewModel.deleteArticle(deletedArticle)

            Snackbar.make(
                requireContext(),
                binding.savedRecyclerView,
                resources.getText(R.string.news_article_deleted),
                Snackbar.LENGTH_SHORT
            ).setAction(resources.getText(R.string.undo)) {
                viewModel.insertArticle(deletedArticle)
            }.show()

        }

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