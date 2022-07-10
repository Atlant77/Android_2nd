package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository
import java.net.IDN

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()
    fun likeById(id: Long){
        repository.likeById(id)
    }

    fun repostById(id: Long) {
        repository.repostById(id)
    }
}