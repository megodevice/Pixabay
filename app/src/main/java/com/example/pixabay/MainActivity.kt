package com.example.pixabay

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabay.adapter.ImageAdapter
import com.example.pixabay.api.ImageModel
import com.example.pixabay.api.RetrofitService
import com.example.pixabay.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), PixabayViewModel {

    private val presenter = PixabayPresenter(this, RetrofitService().api)
    private val adapter = ImageAdapter()
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvImages.adapter = adapter
        initListeners()
    }

    private fun initListeners() {
        binding.apply {

            btSearch.setOnClickListener {
                presenter.getImages(binding.etSearch.text.toString())
            }

            rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = (recyclerView.layoutManager as LinearLayoutManager)

                    val lastVisibleItemPosition: Int =
                        linearLayoutManager.findLastVisibleItemPosition()

                    val totalItemCount: Int = linearLayoutManager.itemCount

                    if (lastVisibleItemPosition == totalItemCount - 1 && dy > 0 && !isLoading)
                        presenter.giveMeMore()
                }
            })
        }
    }

    override fun showImages(list: List<ImageModel>) {
        adapter.submitList(list)
    }

    override fun addImages(list: List<ImageModel>) {
        val temp = adapter.currentList.toMutableList()
        temp.addAll(list)
        adapter.submitList(temp)
    }

    override fun showInfo(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }
}