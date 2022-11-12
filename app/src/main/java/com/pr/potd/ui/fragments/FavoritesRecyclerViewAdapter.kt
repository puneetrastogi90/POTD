package com.pr.potd.ui.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pr.potd.R
import com.pr.potd.data.PotdUiModel
import com.pr.potd.databinding.FragmentItemBinding


internal class FavoritesRecyclerViewAdapter(
    private val adapterInteractionListener: AdapterInteractionListener,
    private val potdUiModels: List<PotdUiModel>
) : RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(potdUiModels[position])
    }

    override fun getItemCount(): Int = potdUiModels.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val headingTv = binding.favoritesTitleTv
        val descTv = binding.favoritesDescTv
        val dateTv = binding.favoritesDateTv
        val bgImgView = binding.backgroundImg

        fun bind(potdUiModel: PotdUiModel) {
            Glide.with(itemView.context)
                .load(potdUiModel.url).error(R.drawable.error_ing)
                .fitCenter()
                .into(bgImgView)

            headingTv.text = potdUiModel.title
            descTv.text = potdUiModel.explanation
            dateTv.text = potdUiModel.date

        }
    }

}

internal interface AdapterInteractionListener {
    fun toggleFavorite(potdUiModel: PotdUiModel)
}