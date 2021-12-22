package com.aispeech.framework.paging

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by spq on 2021/12/7
 */
/**
 * 基于databing的viewHolder
 * @param T 数据类型
 * @property binding ViewDataBinding binding
 * @property variableId Int? T对应的BR_id
 */
open class BaseViewHolder<T>(val binding: ViewDataBinding, val variableId: Int?) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(t: T) {
        variableId?.let {
            binding.setVariable(variableId, t)
            binding.executePendingBindings()
        }
    }

    /**
     * 占位显示
     */
    fun clear() {

    }

}