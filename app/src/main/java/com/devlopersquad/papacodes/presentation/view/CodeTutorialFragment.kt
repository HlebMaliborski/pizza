package com.devlopersquad.papacodes.presentation.view

import android.os.Bundle
import android.view.View
import com.devlopersquad.papacodes.R
import com.devlopersquad.papacodes.common.view.BaseFragment
import com.devlopersquad.papacodes.presentation.viewmodel.CodeTutorialViewModel
import kotlinx.android.synthetic.main.fragment_code_tutorial.*
import org.koin.android.viewmodel.ext.android.viewModel

class CodeTutorialFragment : BaseFragment(R.layout.fragment_code_tutorial) {
    private val codeTutorialViewModel: CodeTutorialViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openCodeFragment.setOnClickListener {
            codeTutorialViewModel.onTutorialPassed()
        }
    }
}