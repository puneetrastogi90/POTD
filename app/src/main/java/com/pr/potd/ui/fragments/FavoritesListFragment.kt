package com.pr.potd.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pr.potd.databinding.FragmentFavoritesListBinding
import com.pr.potd.intent.FavoritesIntent
import com.pr.potd.state.FavoritesScreenUiState
import com.pr.potd.ui.data.PotdUiModel
import com.pr.potd.ui.viewmodels.FavoritesListViewmodel
import com.pr.potd.utils.displayProgressBar
import com.pr.potd.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class FavoritesListFragment : Fragment(), AdapterInteractionListener {
    private lateinit var binding: FragmentFavoritesListBinding

    private val favoritesListViewmodel by activityViewModels<FavoritesListViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritesListBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Favorites"
        observeViewModel()
        getFavorites()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            favoritesListViewmodel.state.collectLatest {
                when (it) {
                    is FavoritesScreenUiState.Loading -> {
                        displayProgressBar(binding.progressbar, true)
                    }
                    is FavoritesScreenUiState.FetchFavoritesSuccess -> {
                        initViews(it.result)
                        displayProgressBar(binding.progressbar, false)
                    }
                    is FavoritesScreenUiState.FetchFavoritesFailed -> {
                        displayProgressBar(binding.progressbar, false)
                        showToast(it.error.toString())
                    }

                    is FavoritesScreenUiState.ToggleFavoriteFailed -> {
                        displayProgressBar(binding.progressbar, false)
                        showToast(it.error.toString())
                    }

                    is FavoritesScreenUiState.ToggleFavoriteSuccess -> {
                        getFavorites()
                        displayProgressBar(binding.progressbar, false)
                        showToast("Favorite toggled Successfully")
                    }
                }
            }
        }
    }

    private fun getFavorites() {
        lifecycleScope.launch {
            favoritesListViewmodel.favoritesIntent.send(FavoritesIntent.FetchFavorites)
        }
    }

    private fun initViews(potdUiModels: List<PotdUiModel>) {
        binding.favoritesRecyclerView.adapter = FavoritesRecyclerViewAdapter(this, potdUiModels)
        binding.favoritesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesListFragment()
    }

    override fun toggleFavorite(potdUiModel: PotdUiModel) {
        lifecycleScope.launch {
            favoritesListViewmodel.favoritesIntent.send(FavoritesIntent.ToggleFavorite(potdUiModel))
        }

    }
}