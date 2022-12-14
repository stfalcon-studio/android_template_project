package com.typicode.android.features.users

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.typicode.android.R
import com.typicode.android.common.base.BaseFragment
import com.typicode.android.common.models.UserModel
import com.typicode.android.databinding.FragmentUserBinding
import com.typicode.android.features.MainVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author andrii.zhumela
 * Created 14.12.2022
 */
class UsersFragment: BaseFragment(R.layout.fragment_user) {

    private val binding: FragmentUserBinding by viewBinding(FragmentUserBinding::bind)
    private val sharedViewModel: MainVM by sharedViewModel()
    private lateinit var usersAdapter: UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViews()

        observeLoading(sharedViewModel.isLoading)
        observeErrorMessage(binding.userErrorItem, sharedViewModel.errorMessage)
        observeConnectionError(sharedViewModel.connectionError, binding.root) {
            sharedViewModel.retry()
        }
        observeData()
    }

    private fun initAdapter() {
        UsersAdapter().apply {
            usersAdapter = this
            onUserClicked = { user ->
                findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToPostsFragment(user = UserModel.mapTo(user)))
            }
            setHasStableIds(true)
        }
    }

    private fun initViews() {
        loadingContainer = binding.loadingContainer.root

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.userRv.apply {
            adapter = usersAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun observeData() {
        sharedViewModel.userList.observe(viewLifecycleOwner) {
            usersAdapter.items = it
        }
    }
}