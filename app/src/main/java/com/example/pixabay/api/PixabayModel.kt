package com.example.pixabay.api

data class PixabayModel(
    val total: Int,
    val hits: List<ImageModel>
)

data class ImageModel(
    val largeImageURL: String,
    val downloads: Int,
    val likes: Int,
    val user: String
)