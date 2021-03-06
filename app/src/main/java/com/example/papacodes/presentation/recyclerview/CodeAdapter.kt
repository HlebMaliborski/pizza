package com.example.papacodes.presentation.recyclerview

import android.view.View
import android.view.ViewGroup
import com.example.papacodes.R
import com.example.papacodes.common.recyclerview.BaseRecyclerAdapter
import com.example.papacodes.common.recyclerview.BaseViewHolder
import com.example.papacodes.presentation.model.PresentationCode
import kotlinx.android.synthetic.main.item_code.*

class CodeAdapter :
    BaseRecyclerAdapter<PresentationCode, BaseViewHolder<PresentationCode>>() {

    var listener: ((code: PresentationCode) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CodeViewHolder {
        return CodeViewHolder(inflate(R.layout.item_code, parent))
    }

    override fun submitList(newList: List<PresentationCode>) {
        val oldList = items
        notify(oldList, newList) { o, n -> o.code == n.code }
        items = newList
    }

    inner class CodeViewHolder(override val containerView: View) :
        BaseViewHolder<PresentationCode>(containerView) {
        override fun bind(data: PresentationCode) {
            codeStock.text = data.stock
            codeName.text = data.code
            codePrice.text = data.price
            codeCity.text = data.city

            if (listener != null) {
                containerView.setOnClickListener {
                    listener?.invoke(data)
                }
            }
        }
    }
}

