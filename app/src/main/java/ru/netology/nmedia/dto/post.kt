package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Long,
    val reposts: Long,
    val views: Long,
    val likedByMe: Boolean = false
)