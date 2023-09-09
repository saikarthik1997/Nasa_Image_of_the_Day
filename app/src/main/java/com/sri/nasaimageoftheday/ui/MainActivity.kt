package com.sri.nasaimageoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.adapters.ImagesAdapter
import com.sri.nasaimageoftheday.databinding.ActivityMainBinding
import com.sri.nasaimageoftheday.utils.Logger
import com.sri.nasaimageoftheday.utils.NetworkResult
import com.sri.nasaimageoftheday.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var myBinding:ActivityMainBinding
    lateinit var swipeContainer: SwipeRefreshLayout
    private val mAdapter by lazy { ImagesAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        myBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        myBinding.recyclerview.adapter=mAdapter
        myBinding.recyclerview.layoutManager= LinearLayoutManager(this)
        swipeContainer = myBinding.swipeContainer
        mainViewModel.fetchImages()
        mainViewModel.imagesResponse.observe(this) {
            if(it is NetworkResult.Loading){
                myBinding.recyclerview.showShimmer()
            }else if (it is NetworkResult.Success) {
                Logger.log("success")
                myBinding.recyclerview.hideShimmer()
                mAdapter.setData(it.data!!)
            } else {
                myBinding.recyclerview.hideShimmer()
                Logger.log("failure")
            }
        }
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            mainViewModel.fetchImages()
        }
    }
}