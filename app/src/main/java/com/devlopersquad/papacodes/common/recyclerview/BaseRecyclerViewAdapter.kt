package com.devlopersquad.papacodes.common.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, VH : BaseViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    var items: List<T> = listOf()

    val isEmpty: Boolean
        get() = items.isEmpty()

    fun getItem(position: Int): T {
        return items[position]
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    protected fun inflate(
        @LayoutRes layout: Int, parent: ViewGroup,
        attachToRoot: Boolean = false
    ): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, attachToRoot)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    abstract fun submitList(newList: List<T>)
}