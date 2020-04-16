package com.example.papacodes.presentation.extensions

import android.view.View
import android.view.animation.AlphaAnimation

fun View.visibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
