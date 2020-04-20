package com.example.papacodes.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.papacodes.common.navigation.Navigator
import com.example.papacodes.common.viewmodel.BaseViewModel
import com.example.papacodes.domain.usecase.BaseUseCase
import com.example.papacodes.domain.usecase.SetTutorialInfoUseCase
import kotlinx.coroutines.launch

class CodeTutorialViewModel(
    private val navigator: Navigator,
    private val setTutorialInfoUseCase: SetTutorialInfoUseCase
) : BaseViewModel() {
    fun onTutorialPassed() {
        viewModelScope.launch {
            setTutorialInfoUseCase.invoke(BaseUseCase.None())
            navigator.openCodeScreen()
        }
    }
}