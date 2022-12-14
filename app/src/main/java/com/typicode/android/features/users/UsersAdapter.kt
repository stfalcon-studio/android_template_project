package com.typicode.android.features.users

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.typicode.android.R
import com.typicode.android.common.extensions.loadImage
import com.typicode.android.databinding.ItemUserBinding
import com.typicode.domain.models.User
import splitties.systemservices.layoutInflater

/**
 * @author andrii.zhumela
 * Created 14.12.2022
 */
class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserVH>() {

    var onUserClicked: ((User) -> Unit)? = null
    var items: MutableList<User>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        return UserVH(
            ItemUserBinding.inflate(
                parent.context.layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.onBind(items?.get(position))
    }

    override fun getItemCount() = items?.size ?: 0

    override fun getItemId(position: Int): Long {
        return items?.get(position)?.userId?.toLong() ?: 0L
    }

    inner class UserVH(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: User?) {
            binding.itemUserNameTv.text = item?.name
            binding.itemUserPhotoIv.loadImage(item?.url)
            binding.itemUserPostsCountTv.text =
                binding.root.context.getString(R.string.posts_count_formatter, item?.postCount)
            binding.itemUserContainer.setOnClickListener { _ ->
                item?.let { onUserClicked?.invoke(it) }
            }
        }
    }
}