package com.memopal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import com.memopal.GroupsAdapter
import com.memopal.R
import com.memopal.ResponseRepository
import com.memopal.VkApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_groups.*

class GroupsActivity : AppCompatActivity() {

    lateinit var repository: ResponseRepository
    lateinit var adapter: GroupsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups)

        setSupportActionBar(toolbar)

        repository = ResponseRepository(VkApiService.create())

        var recyclerView = recycler_groups
        adapter = GroupsAdapter()

        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.groups_menu, menu)

        val searchViewItem = menu?.findItem(R.id.action_search)

        val searchView: SearchView = searchViewItem?.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                Log.i("Tag", query?.length.toString() + "COMPLETE")
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query?.length == 0) return true
                repository.getGroups(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    adapter.submitList(it)
                                },
                                {
                                    it.printStackTrace()
                                })

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}
