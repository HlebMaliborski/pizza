package com.example.papacodes.common.view

import android.view.View

fun View.visibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
