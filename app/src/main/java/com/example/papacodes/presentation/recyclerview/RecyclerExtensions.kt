package com.example.papacodes.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

fun <T, VH> RecyclerView.Adapter<VH>.notify(
    old: List<T>,
    new: List<T>,
    compare: (T, T) -> Boolean
) where VH : RecyclerView.ViewHolder {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        /**
         * By default you should compare item's id to be sure that items are identical
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(old[oldItemPosition], new[newItemPosition])
        }

        /**
         * To use this comparison properly override [equals] method or use data class
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }

        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size
    })

    diff.dispatchUpdatesTo(this)
}