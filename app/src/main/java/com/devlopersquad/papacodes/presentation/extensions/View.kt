package com.devlopersquad.papacodes.presentation.extensions

import android.view.View

fun View.visibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
