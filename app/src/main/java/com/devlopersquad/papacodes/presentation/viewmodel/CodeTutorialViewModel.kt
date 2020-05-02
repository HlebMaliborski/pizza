package com.devlopersquad.papacodes.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.devlopersquad.papacodes.common.navigation.Navigator
import com.devlopersquad.papacodes.common.viewmodel.BaseViewModel
import com.devlopersquad.papacodes.domain.usecase.BaseUseCase
import com.devlopersquad.papacodes.domain.usecase.SetTutorialInfoUseCase
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