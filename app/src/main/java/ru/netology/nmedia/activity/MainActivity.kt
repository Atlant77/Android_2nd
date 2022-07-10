package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter({
            viewModel.likeById(it.id)
        }, {
            viewModel.repostById(it.id)
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this){ posts ->
            adapter.submitList(posts)
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