package com.example.papacodes.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.appyvet.materialrangebar.RangeBar
import com.example.core_common.result.Failure
import com.example.papacodes.R
import com.example.papacodes.presentation.extensions.observeViewState
import com.example.papacodes.presentation.extensions.visibility
import com.example.papacodes.presentation.model.PresentationCodeModel
import com.example.papacodes.presentation.recyclerview.CodeAdapter
import com.example.papacodes.presentation.viewmodel.CodeViewModel
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.CITY
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.PRICE
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.RESET
import com.example.papacodes.presentation.viewmodel.CodeViewModel.Companion.SIZE
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_code.*
import org.koin.android.viewmodel.ext.android.viewModel


class CodeFragment : BaseFragment(R.layout.fragment_code) {
    private val funViewModel: CodeViewModel by viewModel()
    private val codeAdapter: CodeAdapter by lazy {
        CodeAdapter().apply {
            listener = {
                //copy code
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewState()
    }

    private fun initRecyclerView() {
        codeRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = codeAdapter
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(codeBottomSheet)
        codeBottomSheet.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun initPriseSeekBar(maxValue: Int, minValue: Int) {
        codeSeekBar.tickEnd = maxValue.toFloat()
        codeSeekBar.tickStart = minValue.toFloat()
        codeSeekBar.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
            override fun onTouchEnded(rangeBar: RangeBar?) {}

            override fun onRangeChangeListener(
                rangeBar: RangeBar?,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String?,
                rightPinValue: String?
            ) {
                funViewModel.onFilter(PRICE, "$leftPinValue-$rightPinValue")
            }

            override fun onTouchStarted(rangeBar: RangeBar?) {}
        })


        seekTo.text = getString(R.string.seek_to, minValue)
    }

    private fun initCitySpinner() {
        ArrayAdapter.createFromResource(
            activity as Context,
            R.array.cities_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            codeCitySpinner.adapter = adapter
            codeCitySpinner.setSelection(0, false)
            codeCitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        funViewModel.onFilter(CITY, parent?.getItemAtPosition(position) as String)
                    } else {
                        funViewModel.onFilter(CITY, RESET)
                    }
                }
            }
        }
    }

    private fun initSizeRadioGroup() {
        allSizes.setOnClickListener { funViewModel.onFilter(SIZE, RESET) }
        size23.setOnClickListener { funViewModel.onFilter(SIZE, getString(R.string.filter_23)) }
        size30.setOnClickListener { funViewModel.onFilter(SIZE, getString(R.string.filter_30)) }
        size35.setOnClickListener { funViewModel.onFilter(SIZE, getString(R.string.filter_35)) }
        size40.setOnClickListener { funViewModel.onFilter(SIZE, getString(R.string.filter_40)) }
    }

    private fun initViewState() {
        observeViewState(funViewModel.viewState) {
            renderViewState(it)
        }
    }

    private fun renderViewState(viewState: CodeViewModel.ViewState) {
        handleInitialization(viewState.presentationModel, viewState.initializeView)
        handleFailure(viewState.failure)
        handleLoading(viewState.isLoading)
        handleResult(viewState.presentationModel)
    }

    private fun handleLoading(isLoading: Boolean) {
        codeProgressBar.visibility(isLoading)
        codeRecyclerView.visibility(!isLoading)
        codeBottomSheet.visibility(!isLoading)
    }

    private fun handleResult(result: PresentationCodeModel?) {
        if (result != null) {
            codeAdapter.submitList(result.codes)
        }
    }

    private fun handleInitialization(result: PresentationCodeModel?, initializeView: Boolean) {
        if (result != null && initializeView) {
            initRecyclerView()
            initCitySpinner()
            initSizeRadioGroup()
            initPriseSeekBar(result.maxPrice, result.minPrice)
            initBottomSheet()
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> notify(R.string.error_message_network)
        }
    }
}