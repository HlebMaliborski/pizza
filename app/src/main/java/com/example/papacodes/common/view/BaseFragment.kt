package com.example.papacodes.common.view

import android.view.Gravity
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    private var toast: Toast? = null

    protected fun notify(@StringRes message: Int) {
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    protected fun notify(@StringRes message: Int, vararg: String) {
        toast = if (toast == null) {
            Toast.makeText(context, getString(message, vararg), Toast.LENGTH_SHORT)
        } else {
            toast?.cancel()
            Toast.makeText(context, getString(message, vararg), Toast.LENGTH_SHORT)
        }
        toast?.setGravity(Gravity.CENTER_HORIZONTAL and Gravity.CENTER_VERTICAL, 0, 0)
        toast?.show()
    }
}