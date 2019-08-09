package com.memopal.ui

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
import io.reactivex.android.schedulers.AndroidSchedulers

class UserGroupsFragment : Fragment() {

    lateinit var repository: ResponseRepository
    lateinit var adapter: GroupsAdapter
    private var sPref: SharedPreferences? = null

    companion object {
        private val ARG_USER_ID = "user_id"
        fun newInstance(userId: Int): UserGroupsFragment {
            val args: Bundle = Bundle()
            args.putInt(ARG_USER_ID, userId)
            val fragment: UserGroupsFragment = UserGroupsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_users_groups, container, false)
        repository = ResponseRepository(VkApiService.create())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_users_groups)
        val fragmentActivity: FragmentContainerActivity = activity as FragmentContainerActivity
        adapter = GroupsAdapter(fragmentActivity)
        val userId: String = arguments?.getInt(ARG_USER_ID).toString()
        recyclerView.adapter = adapter
        val token = getTokenFromPreferences()
        repository.getUsersGroups(userId, count = "30", token = token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            adapter.submitList(it)
                        },
                        {
                            it.printStackTrace()
                        }
                )

        return view
    }

    fun getTokenFromPreferences(): String {
        //sPref = activity?.getSharedPreferences(SHARED_PREF_FILENAME, AppCompatActivity.MODE_PRIVATE)
        sPref = Application.context.getSharedPreferences(SHARED_PREF_FILENAME, AppCompatActivity.MODE_PRIVATE)
        val token: String? = sPref?.getString(SHARED_PREF_TOKEN_KEY, "")
        return token ?: ""
    }
}