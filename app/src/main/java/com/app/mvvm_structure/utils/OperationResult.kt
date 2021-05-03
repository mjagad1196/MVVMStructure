package com.app.mvvm_structure.utils

import java.lang.Exception

sealed class OperationResult<out R> {

    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val exception: Exception, val errorCode: Int?): OperationResult<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

}