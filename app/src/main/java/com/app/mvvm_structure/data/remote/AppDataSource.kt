package com.app.mvvm_structure.data.remote

import com.app.mvvm_structure.data.entities.response.JokeResponse
import com.app.mvvm_structure.utils.OperationResult

interface AppDataSource {
    suspend fun getJoke(): OperationResult<JokeResponse?>
}