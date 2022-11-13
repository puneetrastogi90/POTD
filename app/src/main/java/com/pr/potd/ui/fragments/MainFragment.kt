package com.pr.potd.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.pr.potd.databinding.FragmentMainBinding
import com.pr.potd.intent.MainIntent
import com.pr.potd.state.MainState
import com.pr.potd.ui.data.PotdUiModel
import com.pr.potd.ui.viewmodels.MainViewmodel
import com.pr.potd.utils.addFragment
import com.pr.potd.utils.displayProgressBar
import com.pr.potd.utils.replaceFragment
import com.pr.potd.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
internal class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val mainViewmodel by activityViewModels<MainViewmodel>()

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

    override fun onResume() {
        super.onResume()
        mainViewmodel.potdUiModel?.let {
            updateUI(it)
        }
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
        (requireActivity() as AppCompatActivity).replaceFragment(
            R.id.container,
            parentFragmentManager,
            FavoritesListFragment.newInstance(),
            false
        )
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewmodel.state.collect {
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
                    }

                    is MainState.ToggleFavoriteSuccess -> {
                        binding.favoriteButton.isActivated =
                            mainViewmodel.potdUiModel?.isFavorite == true
                        displayProgressBar(binding.progressbar, false)
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
            mainViewmodel.potdUiModel?.let {
                mainViewmodel.mainIntent.send(
                    MainIntent.ToggleFavorite(
                        it
                    )
                )
            }
        }
    }

    private fun getCalendarConstraint(): CalendarConstraints.Builder {
        return CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
    }

    private fun updateUI(result: PotdUiModel) {
        if (result.mediaType?.contains("video") == true) {
            loadVideo(result)
        } else {
            loadImageInGlide(result)
        }
        binding.tvTitle.text = result.title
        binding.tvExplanation.text = result.explanation
        binding.favoriteButton.visibility = View.VISIBLE
        binding.favoriteButton.isActivated = result.isFavorite
        binding.dateEditText.text = result.date
    }

    private fun loadVideo(result: PotdUiModel) {
        binding.PotdIv.visibility = View.INVISIBLE
        binding.webView.visibility = View.VISIBLE
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.mediaPlaybackRequiresUserGesture = false
            settings.pluginState = WebSettings.PluginState.ON
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
            }
            loadUrl(result.url)
        }
    }

    private fun loadImageInGlide(result: PotdUiModel) {
        binding.PotdIv.visibility = View.VISIBLE
        binding.webView.visibility = View.INVISIBLE
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
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}