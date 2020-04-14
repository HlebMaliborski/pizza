package com.example.papacodes.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observeViewState(
    liveData: L,
    body: (T) -> Unit
) = liveData.observe(this, Observer { stateView ->
    stateView?.let {
        body(it)
    }
})