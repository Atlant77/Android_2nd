package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.digitsToText

interface PostEventListener {
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onLike(post: Post) {}
    fun onRepost(post: Post) {}
//    fun onCancel() {}
    fun onVideoPlay(post: Post) {}
    fun onPostOpen(post: Post) {}
}

class PostsAdapter(
    private val listener: PostEventListener,
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostEventListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesIco.text = digitsToText(post.likes)
            repostsIco.text = digitsToText(post.reposts)
            viewsIco.text = digitsToText(post.views)
            likesIco.isChecked = post.likedByMe
            videoLayout.isVisible = post.videoLink.isNotBlank()

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                return@setOnMenuItemClickListener true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            likesIco.setOnClickListener {
                listener.onLike(post)
            }

            repostsIco.setOnClickListener {
                listener.onRepost(post)
            }

            imageVideo.setOnClickListener {
                listener.onVideoPlay(post)
            }

            playVideoButton.setOnClickListener {
                listener.onVideoPlay(post)
            }

            root.setOnClickListener{
                listener.onPostOpen(post)
            }

//            videoLayout.setOnClickListener {
//                listener.onVideoPlay(post)
//            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}