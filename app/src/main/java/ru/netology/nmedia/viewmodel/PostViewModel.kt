package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.get()
    fun like(){
        repository.like()
    }

    fun repost() {
        repository.repost()
    }

    fun view() {
        repository.view()
    }
}