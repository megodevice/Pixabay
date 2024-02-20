package com.example.pixabay

import com.example.pixabay.api.ImageModel

interface PixabayViewModel {
    fun showImages(list: List<ImageModel>)
    fun addImages(list: List<ImageModel>)
    fun showInfo(text: String)
    fun setLoading(isLoading: Boolean)
}