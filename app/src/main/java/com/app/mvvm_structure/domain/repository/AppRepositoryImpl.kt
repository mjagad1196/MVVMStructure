package com.app.mvvm_structure.domain.repository

import android.content.Context
import com.app.mvvm_structure.data.entities.response.JokeResponse
import com.app.mvvm_structure.data.remote.AppDataSource
import com.app.mvvm_structure.utils.OperationResult

class AppRepositoryImpl(private val context: Context, private val appDataSource: AppDataSource): AppRepository {
    override suspend fun getJoke(): OperationResult<JokeResponse?> {
        return appDataSource.getJoke()
    }
}