package com.devlopersquad.papacodes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devlopersquad.papacodes.common.navigation.Navigator
import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.viewmodel.BaseViewModel
import com.devlopersquad.papacodes.domain.model.DomainCodeModel
import com.devlopersquad.papacodes.domain.usecase.*
import com.devlopersquad.papacodes.presentation.mapper.DomainToPresentationMapper
import com.devlopersquad.papacodes.presentation.model.PresentationCodeModel
import kotlinx.coroutines.launch

class CodeViewModel(
    private val mapper: DomainToPresentationMapper,
    private val getAllCodeUseCase: GetAllCodeUseCase,
    private val getAllFilteredCodes: GetAllFilteredCodes,
    private val storeCopiedCodeUseCase: StoreCopiedCodeUseCase,
    private val navigator: Navigator
) : BaseViewModel() {
    private val _viewState = liveData {
        emit(ViewState())
        val state = handleResult(getAllCodeUseCase.invoke(BaseUseCase.None())).copy(
            initializeView = true
        )
        emit(state)
    } as MutableLiveData
    val viewState: LiveData<ViewState> = _viewState

    private val _copyIsDone = MutableLiveData<String>()
    val copyIsDone: LiveData<String> = _copyIsDone

    private val filterMap: MutableMap<String, String> = mutableMapOf()

    init {
        filterMap[CITY] = RESET
        filterMap[SIZE] = RESET
        filterMap[PRICE] = RESET
    }

    fun onFilter(type: String, value: String) {
        filterMap[type] = value
        viewModelScope.launch {
            _viewState.value =
                handleResult(getAllFilteredCodes.invoke(Params(filterMap)))
        }
    }

    fun onProcessCode() {
        viewModelScope.launch {
            navigator.processCode()
        }
    }

    fun onCopyCode(code: String) {
        viewModelScope.launch {
            _copyIsDone.value =
                handleCopyResult(storeCopiedCodeUseCase.invoke(ParamsOfCopied(code)))
        }
    }

    fun onUpdateCodes() {
        viewModelScope.launch {
            if (_viewState.value?.presentationModel != null) {
                _viewState.value = handleUpdate(getAllCodeUseCase.invoke(BaseUseCase.None()))
            } else {
                _viewState.value = handleResult(getAllCodeUseCase.invoke(BaseUseCase.None())).copy(
                    initializeView = true
                )
            }
        }
    }

    private fun handleCopyResult(result: Either<Failure, String>): String {
        return when (result) {
            is Either.Error -> ERROR
            is Either.Success -> result.data
        }
    }

    private fun currentViewState(): ViewState = _viewState.value!!

    private fun handleResult(result: Either<Failure, DomainCodeModel>): ViewState {
        return when (result) {
            is Either.Error -> currentViewState().copy(
                isFirstLoading = false,
                failure = result.a,
                initializeView = false
            )
            is Either.Success -> currentViewState().copy(
                isFirstLoading = false,
                failure = Failure.None,
                presentationModel = mapper.map(result.data),
                initializeView = false
            )
        }
    }

    private suspend fun handleUpdate(result: Either<Failure, DomainCodeModel>): ViewState {
        return when (result) {
            is Either.Error -> currentViewState().copy(
                isFirstLoading = false,
                failure = result.a,
                initializeView = false
            )
            is Either.Success -> handleResult(getAllFilteredCodes.invoke(Params(filterMap)))
        }
    }

    data class ViewState(
        val isFirstLoading: Boolean = true,
        val failure: Failure = Failure.None,
        val presentationModel: PresentationCodeModel? = null,
        val initializeView: Boolean = false
    )

    companion object {
        const val CITY = "city"
        const val SIZE = "size"
        const val PRICE = "price"
        const val RESET = "reset"

        const val ERROR = "error"
    }
}