package com.app.mvvm_structure.domain.repository

import com.app.mvvm_structure.data.entities.response.JokeResponse
import com.app.mvvm_structure.utils.OperationResult

interface AppRepository {
    suspend fun getJoke(): OperationResult<JokeResponse?>
}