package com.sri.nasaimageoftheday.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sri.nasaimageoftheday.data.Repository
import com.sri.nasaimageoftheday.models.NasaImageResponseData
import com.sri.nasaimageoftheday.utils.Constants
import com.sri.nasaimageoftheday.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application){
    var imagesResponse: MutableLiveData<NetworkResult<NasaImageResponseData>> = MutableLiveData()

    fun fetchImages(){
        imagesResponse.value=NetworkResult.Loading()
       val queries: HashMap<String, String> = HashMap()
       queries["api_key"]= Constants.API_KEY
       queries["count"]="20"
        viewModelScope.launch {
            val response =   repository.remote.getImages(queries)
            Log.i("MyTag","response is ${response.body()}")
            if(response.isSuccessful){
                imagesResponse.value=NetworkResult.Success(response.body()!!)
            }else{
                imagesResponse.value=NetworkResult.Error("Error Occurred")
            }
   }
    }
}