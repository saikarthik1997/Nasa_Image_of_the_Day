package com.sri.nasaimageoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.adapters.ImagesAdapter
import com.sri.nasaimageoftheday.databinding.ActivityMainBinding
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.utils.Logger
import com.sri.nasaimageoftheday.utils.NetworkResult
import com.sri.nasaimageoftheday.utils.observeOnce
import com.sri.nasaimageoftheday.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var myBinding:ActivityMainBinding
    private lateinit var swipeContainer: SwipeRefreshLayout
    private val mAdapter by lazy { ImagesAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        myBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        swipeContainer = myBinding.swipeContainer
        setupRecyclerViewAdapter()
        readDatabase()
        setOnSwipeListener()
        setupApiListeners()
    }

    private fun setupApiListeners(){
        mainViewModel.imagesResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    myBinding.recyclerview.showShimmer()
                }

                is NetworkResult.Success -> {
                    Logger.log("success")
                    myBinding.recyclerview.hideShimmer()
                    mAdapter.setData(it.data!!)
                }

                else -> {
                    myBinding.recyclerview.hideShimmer()
                    Logger.log("failure")
                }
            }
        }
    }

    private fun setOnSwipeListener(){
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            mainViewModel.fetchImages()
        }
    }

    private fun setupRecyclerViewAdapter(){
        myBinding.recyclerview.adapter=mAdapter
        myBinding.recyclerview.layoutManager= LinearLayoutManager(this)
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readImagesFromDB.observeOnce(this@MainActivity) { database ->
                Logger.log("database observer called")
                if (database.isNotEmpty()
                ) {
                   Logger.log("database not empty")
                    val dataList:ArrayList<NasaImageItemData> = ArrayList<NasaImageItemData>()
                    database.forEach{
                        dataList.add(it.imageItem)
                    }
                    mAdapter.setData(dataList)
                    myBinding.recyclerview.hideShimmer()
                } else {
                    mainViewModel.fetchImages()

                }

            }
        }
    }
}