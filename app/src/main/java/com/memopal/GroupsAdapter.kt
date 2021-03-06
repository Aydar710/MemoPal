package com.memopal

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import com.memopal.pojo.groups.Group
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_group.*
import kotlinx.android.synthetic.main.card_group.view.*


class GroupsAdapter(val listItemClickListener : ListItemClickListener) : ListAdapter<Group, GroupsAdapter.GroupHolder>(GroupItemDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GroupHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_group, parent, false)
        return GroupHolder(view)
    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        val group = getItem(position)
        group?.let {
            holder.bind(it)
        }
    }

    interface ListItemClickListener{
        fun onClick(group : Group)
    }


    inner class GroupHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        var imgGroup = containerView.img_group
        var txtGroupName = containerView.txt_group_name


        fun bind(group: Group) {
            txt_group_name.text = group.name

            containerView.setOnClickListener {
                    listItemClickListener.onClick(group)
            }
            Picasso.get()
                    .load(group.photo100)
                    .into(imgGroup)

        }
    }

}