package com.typicode.android.features.posts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.typicode.android.R
import com.typicode.android.common.base.BaseFragment
import com.typicode.android.common.extensions.loadImage
import com.typicode.android.common.models.UserModel
import com.typicode.android.databinding.FragmentPostsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author andrii.zhumela
 * Created 14.12.2022
 */
class PostsFragment : BaseFragment(R.layout.fragment_posts) {

    private val args: PostsFragmentArgs by navArgs()
    private val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)
    private val viewModel: PostVM by sharedViewModel()
    private lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        extractArgs()
        initAdapter()
        initViews()

        observeLoading(viewModel.isLoading)
        observeErrorMessage(binding.postErrorItem, viewModel.errorMessage)
        observeConnectionError(viewModel.connectionError, binding.root) {
            viewModel.retry()
        }
        observeData()
    }

    private fun extractArgs() {
        viewModel.setupData(UserModel.mapToDomain(args.user))
    }

    private fun initAdapter() {
        PostAdapter().apply {
            postAdapter = this
            setHasStableIds(true)
        }
    }

    private fun initViews() {
        loadingContainer = binding.loadingContainer.root

        binding.postBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.postRv.apply {
            adapter = postAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.postUserNameTv.text = user.name
            binding.postImg.loadImage(user.thumbnailUrl)
        }
        viewModel.postList.observe(viewLifecycleOwner) {
            postAdapter.items = it
        }
    }
}