package com.memopal.activitiesAndFragments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.memopal.GroupsAdapter
import com.memopal.R
import com.memopal.pojo.groups.Group

class FragmentContainerActivity : AppCompatActivity(), GroupsAdapter.ListItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        doGroupsTransaction()
    }

    override fun onClick(group: Group) {
        Log.i("Tag", "Clicked")
        doGroupPostsTransaction(group)
    }

    fun doGroupsTransaction() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, UserGroupsFragment.newInstance(116812347))
                .commit()
    }

    fun doGroupPostsTransaction(group: Group) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, PostsFragment.newInstance(group))
                .commit()
    }

}
