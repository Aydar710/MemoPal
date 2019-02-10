package com.memopal.activitiesAndFragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memopal.*
import com.memopal.constants.SHARED_PREF_FILENAME
import com.memopal.constants.SHARED_PREF_TOKEN_KEY
import com.memopal.pojo.groups.Group
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostsFragment : Fragment() {

    lateinit var adapter: PostAdapter
    private var sPref: SharedPreferences? = null

    companion object {
        private var ARG_GROUP_ID = "group_id"
        private var ARG_GROUP_NAME = "group_name"
        private var ARG_GROUP_PHOTO_SRC = "group_photo_src"
        fun newInstance(group : Group): PostsFragment {
            val args: Bundle = Bundle()
            args.putInt(ARG_GROUP_ID, group.id ?: -1)
            args.putString(ARG_GROUP_NAME, group.name)
            args.putString(ARG_GROUP_PHOTO_SRC, group.photo100)
            val fragment = PostsFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        val repository = ResponseRepository(VkApiService.create())
        val recyclerView = view.recycler_group_posts
        adapter = PostAdapter()
        val groupId: String = "-" + arguments?.getInt(ARG_GROUP_ID).toString()

        adapter.groupName = arguments?.getString(ARG_GROUP_NAME) ?: ""
        adapter.groupPhotoSource = arguments?.getString(ARG_GROUP_PHOTO_SRC) ?: ""
        recyclerView.adapter = adapter
        val token = getTokenFromPreferences()

        repository.getGroupPosts(groupId, token)
                .subscribe({
                    adapter.submitList(it)
                }, {
                    it.printStackTrace()
                })

        return view
    }

    fun getTokenFromPreferences(): String {
        //sPref = activity?.getSharedPreferences(SHARED_PREF_FILENAME, AppCompatActivity.MODE_PRIVATE)
        sPref = Application.context.getSharedPreferences(SHARED_PREF_FILENAME, AppCompatActivity.MODE_PRIVATE)
        val token: String? = sPref?.getString(SHARED_PREF_TOKEN_KEY, "")
        return token ?: ""
    }
}