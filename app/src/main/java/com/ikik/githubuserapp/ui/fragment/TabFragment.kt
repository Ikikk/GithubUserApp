//package com.ikik.githubuserapp.ui.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.ikik.githubuserapp.R
//import com.ikik.githubuserapp.data.remote.response.ItemsItem
//import com.ikik.githubuserapp.data.remote.retrofit.APIConfig
//import com.ikik.githubuserapp.data.remote.retrofit.APIService
//import com.ikik.githubuserapp.databinding.FragmentTabBinding
//import com.ikik.githubuserapp.ui.adapter.UserAdapter
//import com.ikik.githubuserapp.ui.UserDetailsActivity
//import com.ikik.githubuserapp.ui.viewmodel.TabFollowingViewModel
//import com.ikik.githubuserapp.ui.viewmodel.TabViewModel
//
//class TabFragment : Fragment() {
//
//    private var _binding: FragmentTabBinding? = null
//    private val binding get() = _binding
//    private lateinit var adapter: UserAdapter
//    private lateinit var apiService: APIService
//    private lateinit var progressBar: ProgressBar
//    private val followersViewModel: TabViewModel by viewModels()
//    private val followingViewModel: TabFollowingViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        _binding = FragmentTabBinding.inflate(inflater,container, false)
//
//        return binding?.root ?: inflater.inflate(R.layout.activity_user_details, container, false)
//    }
//
//    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adapter = UserAdapter(emptyList())
//
//        apiService = APIConfig.getAPIService()
//        progressBar = view.findViewById(R.id.fragmentProgressBar)
//        progressBar.visibility = View.VISIBLE
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.rv_user_follow)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = adapter
//
//        adapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: ItemsItem){
//                val intent = Intent(requireContext(), UserDetailsActivity::class.java).apply {
//                    putExtra(ARG_USERNAME, data.login)
//                }
//                startActivity(intent)
//            }
//        })
//
//        val position = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
//        val username = arguments?.getString(ARG_USERNAME) ?: "Unknown"
//
////        viewModel.isLoadingFollowers.observe(viewLifecycleOwner) { isLoading ->
////            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
////        }
//
////        viewModel.isLoadingFollowing.observe(viewLifecycleOwner) { isLoading ->
////            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
////        }
//
////        viewModel.users.observe(viewLifecycleOwner) { users ->
////            adapter.updateUsers(users)
////        }
//        if (position == 1) {
////            progressBar.visibility = View.VISIBLE
//            followersViewModel.isLoadingFollowers.observe(viewLifecycleOwner) { isLoading ->
//                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//            }
//            followersViewModel.users.observe(viewLifecycleOwner) { users ->
//                adapter.updateUsers(users)
//            }
////            followersViewModel.searchFollowers(username)
//            if (followersViewModel.users.value == null) {
//                followersViewModel.searchFollowers(username)
//            }
//        } else{
////            progressBar.visibility = View.VISIBLE
//            followingViewModel.isLoadingFollowing.observe(viewLifecycleOwner) { isLoading ->
//                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//            }
//            followingViewModel.users.observe(viewLifecycleOwner) { users ->
//                adapter.updateUsers(users)
//            }
////            followingViewModel.searchFollowing(username)
//            if (followingViewModel.users.value == null) {
//                followingViewModel.searchFollowing(username)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        const val ARG_SECTION_NUMBER = "section_number"
//        const val ARG_USERNAME = "USERNAME"
//
//        @JvmStatic
//        fun newInstance(sectionNumber: Int, username: String): TabFragment {
//            return TabFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                    putString(ARG_USERNAME, username)
//                }
//            }
//        }
//    }
//}
package com.ikik.githubuserapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikik.githubuserapp.R
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.data.remote.retrofit.APIConfig
import com.ikik.githubuserapp.data.remote.retrofit.APIService
import com.ikik.githubuserapp.databinding.FragmentTabBinding
import com.ikik.githubuserapp.ui.UserDetailsActivity
import com.ikik.githubuserapp.ui.adapter.UserAdapter
import com.ikik.githubuserapp.ui.viewmodel.TabFollowingViewModel
import com.ikik.githubuserapp.ui.viewmodel.TabViewModel

class TabFragment : Fragment() {

    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: UserAdapter
    private lateinit var apiService: APIService
    private lateinit var progressBar: ProgressBar
    private val followersViewModel: TabViewModel by viewModels()
    private val followingViewModel: TabFollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater,container, false)

        return binding?.root ?: inflater.inflate(R.layout.activity_user_details, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter(emptyList())


        apiService = APIConfig.getAPIService()
        progressBar = view.findViewById(R.id.fragmentProgressBar)
        progressBar.visibility = View.VISIBLE

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_user_follow)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem){
                val intent = Intent(requireContext(), UserDetailsActivity::class.java).apply {
                    putExtra(ARG_USERNAME, data.login)
                }
                startActivity(intent)
            }
        })

        val position = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
        val username = arguments?.getString(ARG_USERNAME) ?: "Unknown"

        if (position == 1) {
            progressBar.visibility = View.VISIBLE
            followersViewModel.users.observe(viewLifecycleOwner) { users ->
                adapter.updateUsers(users)
            }

            followersViewModel.isLoadingFollowers.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            if (followersViewModel.users.value.isNullOrEmpty()) {
                followersViewModel.searchFollowers(username)
            }

        } else {
            followingViewModel.users.observe(viewLifecycleOwner) { users ->
                adapter.updateUsers(users)
            }
            progressBar.visibility = View.VISIBLE
            followingViewModel.isLoadingFollowing.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            if (followingViewModel.users.value.isNullOrEmpty()) {
                followingViewModel.searchFollowing(username)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "USERNAME"

        @JvmStatic
        fun newInstance(sectionNumber: Int, username: String): TabFragment {
            return TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_USERNAME, username)
                }
            }
        }
    }
}