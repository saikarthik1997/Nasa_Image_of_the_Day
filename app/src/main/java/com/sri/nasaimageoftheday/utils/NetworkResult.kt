package com.sri.nasaimageoftheday.utils


//for getting a network result that could success or failure
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null): NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()

}