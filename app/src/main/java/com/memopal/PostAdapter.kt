package com.memopal

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import com.memopal.pojo.groupWall.Item
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_post_item.view.*

class PostAdapter : android.widget.ListAdapter<Item, PostAdapter.PostHolder>(PostItemDiffCallback()) {
    lateinit var groupPhotoSource : String
    lateinit var groupName : String

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post_item, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = getItem(position)
        post?.let {
            holder.bind(it)
        }
    }

    inner class PostHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        var imgGroupPhoto = containerView.img_group_photo
        var txtGroupName = containerView.txt_group_name
        var txtPostText = containerView.txt_post_text
        var imgPostPhoto = containerView.img_post_photo
        var txtPostViews = containerView.txt_post_views
        var txtPostLikes = containerView.txt_post_likes

        fun bind(post : Item){
            txtPostText.text = post.text
            txtPostViews.text = post.views?.count.toString()
            txtPostLikes.text = post.likes?.count.toString()
            txtGroupName.text = groupName

            Picasso.get()
                    .load(groupPhotoSource)
                    .into(imgGroupPhoto)

            Picasso.get()
                    .load(post?.attachments?.get(0)?.photo?.sizes?.get(7)?.url)
                    .into(imgPostPhoto)
        }

    }

}