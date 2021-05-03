package com.app.mvvm_structure.data.remote

import android.content.Context
import com.app.mvvm_structure.data.entities.response.ErrorResponse
import com.app.mvvm_structure.data.entities.response.JokeResponse
import com.app.mvvm_structure.utils.CommonUtils
import com.app.mvvm_structure.utils.NetworkException
import com.app.mvvm_structure.utils.OperationResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException

class AppDataSourceImpl(private val context: Context, private val apiService: ApiService, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): AppDataSource {

    override suspend fun getJoke(): OperationResult<JokeResponse?> = withContext(ioDispatcher) {
        try {
            if (!CommonUtils.hasNetwork(context)){
                throw NetworkException("No internet connection!!")
            }
            val response = apiService.getJoke()
            if (response.isSuccessful){
                return@withContext OperationResult.Success(response.body())
            } else {
                getErrorResponse(response)
            }
        } catch (exception: Exception){
            catchException(exception)
        }
    }

    private suspend fun catchException(exception: Exception): OperationResult.Error =
        withContext(ioDispatcher) {
            when(exception) {
                is NetworkException -> return@withContext  OperationResult.Error(exception, null)
                is SocketTimeoutException -> return@withContext OperationResult.Error(
                    Exception("Your internet connection seems to be slow or connection with server is not established"),
                    null
                )
                else -> return@withContext OperationResult.Error(
                    Exception("Something went wrong"), null
                )
            }
        }

    private suspend fun getErrorResponse(response: Response<*>) = withContext(ioDispatcher){
        val logResponse = response.errorBody()!!.charStream()
        val errorResponse = Gson().fromJson(logResponse, ErrorResponse::class.java)
        return@withContext OperationResult.Error(Exception(errorResponse.respText), response.code())
    }

}