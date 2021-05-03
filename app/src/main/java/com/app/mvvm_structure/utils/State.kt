package com.app.mvvm_structure.utils

open class State

class LoadingState: State()

class SuccessState: State()

data class ErrorState(val error: Throwable = Throwable()): State()