package com.example.pixabay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.pixabay.api.ImageModel
import com.example.pixabay.databinding.ItemImageBinding

class ImageAdapter : ListAdapter<ImageModel, ImageAdapter.ImageViewHolder>(DIFF_UTIL_CALL_BACK) {

    private companion object {
        val DIFF_UTIL_CALL_BACK: DiffUtil.ItemCallback<ImageModel> =
            object : DiffUtil.ItemCallback<ImageModel>() {
                override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                    return oldItem.largeImageURL == newItem.largeImageURL
                }

                override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) : ViewHolder(binding.root) {
        fun onBind(imageModel: ImageModel) {
            binding.apply {
                tvDownloads.text = imageModel.downloads.toString()
                tvLikes.text = imageModel.likes.toString()
                tvUser.text = imageModel.user
                ivImage.load(imageModel.largeImageURL)
            }
        }
    }
}