package com.devlopersquad.papacodes.common.view

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.devlopersquad.papacodes.R


abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    private var toast: Toast? = null
    private var layout: View? = null
    private var textView: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflater = layoutInflater
        layout = inflater.inflate(
            R.layout.toast_layout,
            activity?.findViewById(R.id.customToastContainer) as ViewGroup?
        )
        textView = layout?.findViewById(R.id.toastText)
    }

    protected fun notify(@StringRes message: Int) {
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    protected fun notify(@StringRes message: Int, vararg: String) {
        toast = if (toast == null) {
            Toast(context)
        } else {
            toast?.cancel()
            Toast(context)
        }
        textView?.text = getString(message, vararg)
        toast?.duration = Toast.LENGTH_SHORT
        toast?.view = layout
        toast?.setGravity(Gravity.CENTER_HORIZONTAL and Gravity.CENTER_VERTICAL, 0, 0)
        toast?.show()
    }
}