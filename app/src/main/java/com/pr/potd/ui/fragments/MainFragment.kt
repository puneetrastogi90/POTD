package com.pr.potd.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.pr.potd.R
import com.pr.potd.data.PotdUiModel
import com.pr.potd.databinding.FragmentMainBinding
import com.pr.potd.intent.MainIntent
import com.pr.potd.state.MainState
import com.pr.potd.utils.addFragment
import com.pr.potd.utils.convertMillisToDate
import com.pr.potd.utils.displayProgressBar
import com.pr.potd.utils.showToast
import com.pr.potd.viewmodels.MainViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val mainViewmodel by viewModels<MainViewmodel>()
    private lateinit var potdUiModel: PotdUiModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        setHasOptionsMenu(true);
        binding.dateEditText.setOnClickListener {
            showDatePicker()
        }
        binding.favoriteButton.setOnClickListener {
            toggleFavorite()
        }
        observeViewModel()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites -> openFavorites()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFavorites() {
        addFragment(R.id.container, this, FavoritesListFragment.newInstance(), false)
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewmodel.state.collectLatest {
                when (it) {
                    is MainState.Loading -> {
                        displayProgressBar(binding.progressbar, true)
                    }
                    is MainState.FetchPotdSuccess -> {
                        updateUI(it.result)
                        displayProgressBar(binding.progressbar, false)
                    }
                    is MainState.FetchPotdApiError -> {
                        displayProgressBar(binding.progressbar, false)
                        showToast(it.error.toString())
                    }
                    is MainState.FetchPotdNetworkError -> {
                        displayProgressBar(binding.progressbar, false)
                        showToast(it.error.toString())
                    }
                    is MainState.FetchPotdUnknownError -> {
                        displayProgressBar(binding.progressbar, false)
                        showToast(it.error.toString())
                    }

                    is MainState.ToggleFavoriteFailed -> {
                        displayProgressBar(binding.progressbar, false)
                        showToast(it.error.toString())
                    }

                    is MainState.ToggleFavoriteSuccess -> {
                        potdUiModel = it.potdUiModel
                        displayProgressBar(binding.progressbar, false)
                        showToast("Favorite toggled Successfully")
                    }
                }
            }
        }
    }

    private fun showDatePicker() {
        val constraintsBuilder = getCalendarConstraint()
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .setTitleText("Select date")
                .build()

        datePicker.addOnPositiveButtonClickListener { millis ->
            binding.dateEditText.text = convertMillisToDate("MM/dd/yyyy", millis)
            fetchPotd(millis)
        }
        datePicker.addOnCancelListener {
            it.dismiss()
        }
        datePicker.addOnDismissListener {
            it.dismiss()
        }
        datePicker.show(childFragmentManager, "DatePicker")
    }

    private fun fetchPotd(dateInMillis: Long) {
        lifecycleScope.launch {
            mainViewmodel.mainIntent.send(MainIntent.FetchPictureOftheDay(dateInMillis))
        }
    }

    private fun toggleFavorite() {
        lifecycleScope.launch {
            mainViewmodel.mainIntent.send(MainIntent.ToggleFavorite(potdUiModel))
        }
    }

    private fun getCalendarConstraint(): CalendarConstraints.Builder {
        return CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
    }

    private fun updateUI(result: PotdUiModel) {
        potdUiModel = result
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.image_loader_progress_animation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
        Glide.with(this)
            .load(result.url).apply(options)
            .error(R.drawable.error_ing).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    showToast("An Error Occurred while loading the image.")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .fitCenter()
            .into(binding.PotdIv)

        binding.tvTitle.text = result.title
        binding.tvExplanation.text = result.explanation
        binding.favoriteButton.visibility = View.VISIBLE
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}