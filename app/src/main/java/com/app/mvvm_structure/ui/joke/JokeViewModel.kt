package com.app.mvvm_structure.ui.joke

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mvvm_structure.data.entities.response.JokeResponse
import com.app.mvvm_structure.domain.repository.AppRepository
import com.app.mvvm_structure.utils.*
import kotlinx.coroutines.launch

class JokeViewModel @ViewModelInject constructor(private val appRepository: AppRepository): ViewModel() {

    private var _joke = MutableLiveData<Event<JokeResponse>>()
    var joke:LiveData<Event<JokeResponse>> = _joke

    private var _state = MutableLiveData<Event<State>>()
    var state: LiveData<Event<State>> = _state

    fun getJoke(){
        viewModelScope.launch {
            _state.postValue(Event(LoadingState()))

            appRepository.getJoke().let { operationResult ->
                when(operationResult) {
                    is OperationResult.Success -> {
                        _state.postValue(Event(SuccessState()))
                        _joke.postValue(Event(operationResult.data!!))
                    }

                    is OperationResult.Error -> {
                        _state.postValue(Event(ErrorState(operationResult.exception)))
                    }
                }
            }
        }
    }


}