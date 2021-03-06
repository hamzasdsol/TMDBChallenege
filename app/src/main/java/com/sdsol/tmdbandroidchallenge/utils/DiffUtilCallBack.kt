package com.sdsol.tmdbandroidchallenge.utils

import androidx.recyclerview.widget.DiffUtil
import com.sdsol.tmdbandroidchallenge.models.movieslistresponse.Result

open class DiffUtilCallBack(
    private val oldData: List<Result>,
    private val newData: List<Result>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}