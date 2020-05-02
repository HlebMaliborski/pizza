package com.devlopersquad.papacodes.common.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    /**
     * Bind data to the item and set listener if needed.
     *
     * @param data object, associated with the item.
     */
    abstract fun bind(data: T)
}