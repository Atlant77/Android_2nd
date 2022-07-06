package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likes: Long,
    var reposts: Long,
    var views: Long,
    var likedByMe: Boolean = false
)