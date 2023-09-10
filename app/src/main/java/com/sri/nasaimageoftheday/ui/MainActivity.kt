package com.sri.nasaimageoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sri.nasaimageoftheday.R
import com.sri.nasaimageoftheday.adapters.ImagesAdapter
import com.sri.nasaimageoftheday.databinding.ActivityMainBinding
import com.sri.nasaimageoftheday.models.NasaImageItemData
import com.sri.nasaimageoftheday.utils.Constants
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

    //for listening to result from api calls
    private fun setupApiListeners(){
        mainViewModel.imagesResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    hideErrorView()
                    showShimmer()
                }

                is NetworkResult.Success -> {
                    Logger.log("success")
                    hideShimmer()
                    hideErrorView()
                    mAdapter.setData(it.data!!)
                }

                else -> {
                    hideShimmer()
                    showErrorView(it.message)
                    Logger.log("failure")
                }
            }
        }
    }

    //swipe to refresh listener
    private fun setOnSwipeListener(){
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = false
            mainViewModel.fetchImages()
        }
    }

    //recycler view adapter setup
    private fun setupRecyclerViewAdapter(){
        myBinding.recyclerview.adapter=mAdapter
        myBinding.recyclerview.layoutManager= LinearLayoutManager(this)
    }

    //will read the local database once
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
                    hideShimmer()
                } else {
                    mainViewModel.fetchImages()
                }

            }
        }
    }

    //for showing shimmer in the recycler view
    private fun showShimmer(){
        myBinding.recyclerview.showShimmer()
    }

    //for hiding shimmer in the recycler view
    private fun hideShimmer(){
        myBinding.recyclerview.hideShimmer()
    }

    //shows error image and text and hides the recycler view
    private fun showErrorView(errorText:String?){
        myBinding.recyclerview.visibility=View.INVISIBLE
        myBinding.errorImageView.visibility= View.VISIBLE
        myBinding.errorTextView.visibility= View.VISIBLE
        myBinding.errorTextView.text=errorText?:Constants.SOMETHING_WRONG
    }

    //hides the error image and text
    private fun hideErrorView(){
        myBinding.recyclerview.visibility=View.VISIBLE
        myBinding.errorImageView.visibility= View.INVISIBLE
        myBinding.errorTextView.visibility= View.INVISIBLE
    }
}