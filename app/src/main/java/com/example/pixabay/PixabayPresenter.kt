package com.example.pixabay

import com.example.pixabay.api.PixabayApi
import com.example.pixabay.api.PixabayModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PixabayPresenter(
    private val viewModel: PixabayViewModel,
    private val pixabayApi: PixabayApi
) {
    private var query = String()
    private var page = 1

    fun getImages(query: String) {
        if (query.isNotEmpty()) {
            this.query = query
            page = 1
            viewModel.setLoading(true)
            pixabayApi.getImages(query = query, page = page)
                .enqueue(object : Callback<PixabayModel> {
                    override fun onResponse(
                        call: Call<PixabayModel>,
                        response: Response<PixabayModel>
                    ) {
                        response.body()?.let { pixabayModel ->
                            viewModel.showInfo("Total images: ${pixabayModel.total}")
                            viewModel.showImages(pixabayModel.hits)
                            viewModel.setLoading(false)
                        }
                    }

                    override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                        viewModel.showInfo(t.localizedMessage ?: "Error")
                        viewModel.setLoading(false)
                    }
                })
        }
    }

    fun giveMeMore() {
        page++
        viewModel.setLoading(true)
        pixabayApi.getImages(query = query, page = page)
            .enqueue(object : Callback<PixabayModel> {
                override fun onResponse(
                    call: Call<PixabayModel>,
                    response: Response<PixabayModel>
                ) {
                    response.body()?.let { pixabayModel ->
                        viewModel.addImages(pixabayModel.hits)
                        viewModel.setLoading(false)
                    }
                }
                override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                    viewModel.showInfo(t.localizedMessage ?: "Error")
                    viewModel.setLoading(false)
                }
            })
    }
}