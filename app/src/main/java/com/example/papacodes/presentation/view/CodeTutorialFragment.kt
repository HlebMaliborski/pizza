package com.example.papacodes.presentation.view

import android.os.Bundle
import android.view.View
import com.example.papacodes.R
import com.example.papacodes.common.view.BaseFragment
import com.example.papacodes.presentation.viewmodel.CodeTutorialViewModel
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