package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.AndroidUtils
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostEventListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(
            object : PostEventListener {
                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    binding.cancelGroup.visibility = View.VISIBLE
                    binding.cancelButton.setOnClickListener {
                        binding.contentInput.clearFocus()
                        AndroidUtils.hideKeyboard(binding.contentInput)
                        binding.contentInput.setText("")
                        binding.cancelGroup.visibility = View.GONE
                        return@setOnClickListener
                    }
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRepost(post: Post) {
                    viewModel.repostById(post.id)
                }
            }
        )

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts) {
                binding.list.smoothScrollToPosition(0) // функция скролинга наверх после добавления нового поста
            }
        }

        viewModel.edited.observe(this){ post ->
            if (post.id == 0L) {
                return@observe
            }

            binding.cancelMassagePostId.text = getString(R.string.post_editing_id) + post.id.toString()
            binding.contentInput.setText(post.content)
            binding.contentInput.requestFocus()
        }

        binding.saveButton.setOnClickListener {
            with(binding.contentInput) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_input),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                clearFocus()
                AndroidUtils.hideKeyboard(this)
                setText("")
                binding.cancelGroup.visibility = View.GONE
            }
        }
    }
}

// Function of digits to string conversions
fun digitsToText(digitsToString: Long): String {
    when (digitsToString) {
        in 0..999 -> return digitsToString.toString().take(3)
        in 1_000..9_999 -> {
            val thousands = (digitsToString / 1_000).toDouble().roundToLong()
            when (val thousandsSecond = (digitsToString % 1_000).toDouble().roundToLong()) {
                0L -> return thousands.toString().take(3) + ".0 K"
                in 1..99 -> return thousands.toString().take(3) + ".0 K"
                in 100..999 -> return thousands.toString().take(3) + ".${thousandsSecond.toString().take(1)} K"
                in 1000..9999 -> return "$thousands." + thousandsSecond.toString()
                    .take(1) + "K"
            }
        }
        in 10_000..999_999 -> {
            val thousands = (digitsToString / 1_000).toDouble().roundToLong()
            return thousands.toString().take(3) + "K"
        }
        else -> {
            val millions = (digitsToString / 1_000_000).toDouble().roundToLong()
            val millionsSecond = (digitsToString % 1_000_000).toDouble().roundToLong()
            return if (millions == 0L) {
                millions.toString().take(3) + "M"
            } else "$millions." + millionsSecond.toString()
                .take(1) + "M"
        }
    }
    return digitsToString.toString().take(3)
}