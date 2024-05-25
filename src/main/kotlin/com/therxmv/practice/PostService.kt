package com.therxmv.practice

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getAll(): Iterable<PostEntity> = postRepository.findAll()

    fun getByIndex(id: Int): PostEntity? = postRepository.findByIdOrNull(id)

    fun add(post: PostEntity): PostEntity = postRepository.save(post)

    fun update(id: Int, post: PostEntity): PostEntity = postRepository.save(post.copy(id = id))

    fun update(post: PostEntity): PostEntity = postRepository.save(post)

    fun remove(id: Int) = postRepository.deleteById(id)
}