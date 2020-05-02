package com.devlopersquad.papacodes.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.devlopersquad.papacodes.R
import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.view.BaseFragment
import com.devlopersquad.papacodes.presentation.extensions.observeViewState
import com.devlopersquad.papacodes.presentation.extensions.visibility
import com.devlopersquad.papacodes.presentation.model.PresentationCodeModel
import com.devlopersquad.papacodes.presentation.recyclerview.CodeAdapter
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.CITY
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.ERROR
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.PRICE
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.RESET
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.SIZE
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_code.*
import kotlinx.android.synthetic.main.view_no_internet.*
import org.koin.android.viewmodel.ext.android.viewModel


class CodeFragment : BaseFragment(R.layout.fragment_code) {
    private val funViewModel: CodeViewModel by viewModel()
    private val codeAdapter: CodeAdapter by lazy {
        CodeAdapter().apply {
            listener = {
                funViewModel.onCopyCode(it.code)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewState()
        initStartingViews()
    }

    private fun initStartingViews() {
        codeRefreshLayout.isEnabled = false
        update.setOnClickListener {
            noInternet.visibility(false)
            codeProgressBar.visibility(true)
            funViewModel.onUpdateCodes()
        }
    }

    private fun initViewState() {
        observeViewState(funViewModel.viewState) {
            renderViewState(it)
        }

        observeViewState(funViewModel.copyIsDone) {
            if (it != ERROR) {
                notify(R.string.message_copy_done, it)
            }
        }
    }

    private fun renderViewState(viewState: CodeViewModel.ViewState) {
        handleInitialization(viewState.presentationModel, viewState.initializeView)
        handleLoading(viewState.isFirstLoading)
        handleFailure(viewState.failure)
        handleResult(viewState.presentationModel)
    }

    private fun handleLoading(isLoading: Boolean) {
        codeProgressBar.visibility(isLoading)
        codeRecyclerView.visibility(!isLoading)
        codeBottomSheet.visibility(!isLoading)
        fab.visibility(!isLoading)
        codeRefreshLayout.visibility(!isLoading)
        noInternet.visibility(false)
    }

    private fun handleResult(result: PresentationCodeModel?) {
        if (result != null) {
            codeRefreshLayout.isEnabled = true
            codeRefreshLayout.isRefreshing = false
            codeAdapter.submitList(result.codes)
        }
    }

    private fun handleFailure(failure: Failure) {
        codeRefreshLayout.isRefreshing = false
        when (failure) {
            is Failure.NetworkFailure -> {
                noInternet.visibility(true)
                codeRefreshLayout.visibility(false)
                codeRecyclerView.visibility(false)
                codeBottomSheet.visibility(false)
                fab.visibility(false)
            }
            is Failure.MappingFailure -> notify(R.string.error_message_mapping)
        }
    }

    private fun handleInitialization(result: PresentationCodeModel?, initializeView: Boolean) {
        if (result != null && initializeView) {
            initRecyclerView()
            initCitySpinner()
            initSizeRadioGroup()
            initPriseSeekBar(result.maxPrice, result.minPrice)
            initBottomSheet()
            initFab()
            initSwipeLayout()
        }
    }

    private fun initRecyclerView() {
        codeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = codeAdapter
            setHasFixedSize(true)
            itemAnimator = null
            isMotionEventSplittingEnabled = false
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
        codeSeekBar.max = maxValue - minValue
        codeSeekBar.progress = maxValue - minValue
        codeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekTo.text = getString(R.string.seek_to, progress + minValue)
                funViewModel.onFilter(PRICE, (progress + minValue).toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekTo.text = getString(R.string.seek_to, maxValue)
    }

    private fun initCitySpinner() {
        ArrayAdapter.createFromResource(
            activity as Context,
            R.array.cities_array,
            R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_layout)
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

    private fun initSwipeLayout() {
        context?.let {
            ContextCompat.getColor(
                it,
                R.color.colorPrimary
            )
        }?.let {
            codeRefreshLayout.setColorSchemeColors(
                it
            )
        }
        codeRefreshLayout.setOnRefreshListener {
            funViewModel.onUpdateCodes()
        }
    }

    private fun initFab() {
        fab.setOnClickListener {
            funViewModel.onProcessCode()
        }
    }
}