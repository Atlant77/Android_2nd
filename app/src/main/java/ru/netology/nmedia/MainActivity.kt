package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n\nНо самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 5_999,
            reposts = 35_999,
            views = 999,
            likedByMe = false,
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = digitsToText(post.likes)
            reposts.text = digitsToText(post.reposts)
            views.text = digitsToText(post.views)

            if (post.likedByMe) {
                likesIco?.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            likesIco?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (!post.likedByMe) {
                    likesIco.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    post.likes = post.likes - 1
                } else {
                    likesIco.setImageResource(R.drawable.ic_baseline_favorite_24)
                    post.likes = post.likes + 1
                }
                likes.text = digitsToText(post.likes)
            }
            repostsIco?.setOnClickListener {
                post.reposts = post.reposts + 1
                reposts.text = digitsToText(post.reposts)
            }
            viewsIco?.setOnClickListener {
                post.views = post.views + 1
                views.text = digitsToText(post.views)
            }
        }
    }
}

// Function of digits to string conversions
private fun digitsToText(digitsToString: Long): String {
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