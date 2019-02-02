package com.memopal

import android.support.v7.util.DiffUtil
import com.memopal.pojo.groups.Group

class GroupItemDiffCallback : DiffUtil.ItemCallback<Group>() {
    override fun areItemsTheSame(p0: Group, p1: Group): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: Group, p1: Group): Boolean {
        return p0 == p1
    }
}