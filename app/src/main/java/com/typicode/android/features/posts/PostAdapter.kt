package com.typicode.android.features.posts

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.typicode.android.R
import com.typicode.android.common.extensions.loadImage
import com.typicode.android.databinding.ItemPostBinding
import com.typicode.android.databinding.ItemUserBinding
import com.typicode.domain.models.Post
import com.typicode.domain.models.User
import splitties.systemservices.layoutInflater

/**
 * @author andrii.zhumela
 * Created 14.12.2022
 */
class PostAdapter : RecyclerView.Adapter<PostAdapter.PostVH>() {

    var items: MutableList<Post>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        return PostVH(
            ItemPostBinding.inflate(
                parent.context.layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        holder.onBind(items?.get(position))
    }

    override fun getItemCount() = items?.size ?: 0

    override fun getItemId(position: Int): Long {
        return items?.get(position)?.userId?.toLong() ?: 0L
    }

    inner class PostVH(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Post?) {
            binding.itemPostTitleTv.text = item?.title
            binding.itemPostBodyTv.text = item?.body
        }
    }
}