package com.memopal

import android.support.v7.util.DiffUtil
import com.memopal.pojo.groupWall.Item

class PostItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(p0: Item, p1: Item): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: Item, p1: Item): Boolean {
        return p0.id == p1.id
    }
}