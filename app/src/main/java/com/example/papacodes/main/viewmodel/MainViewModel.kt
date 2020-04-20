package com.example.papacodes.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.papacodes.common.response.Either
import com.example.papacodes.common.response.Failure
import com.example.papacodes.common.viewmodel.BaseViewModel
import com.example.papacodes.domain.usecase.BaseUseCase
import com.example.papacodes.domain.usecase.GetInfoTutorialUseCase

class MainViewModel(
    private val getInfoTutorialUseCase: GetInfoTutorialUseCase
) : BaseViewModel() {
    private val _isTutorialPassed = liveData {
        emit(handleResult(getInfoTutorialUseCase.invoke(BaseUseCase.None())))
    } as MutableLiveData
    val isTutorialPassed: LiveData<Boolean> = _isTutorialPassed

    private fun handleResult(result: Either<Failure, Boolean>): Boolean {
        return when (result) {
            is Either.Error -> false
            is Either.Success -> true
        }
    }
}