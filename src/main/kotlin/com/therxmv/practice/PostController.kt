package com.therxmv.practice

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("post")
class PostController(
    private val postService: PostService
) {
    private val logger = LoggerFactory.getLogger(PostController::class.java)

    @GetMapping
    fun getAll() = try {
        ResponseEntity(postService.getAll(), OK)
    } catch (e: Exception) {
        logger.error(e.stackTraceToString())
        ResponseEntity(e.message, INTERNAL_SERVER_ERROR)
    }

    @GetMapping("{id}")
    fun getByIndex(@PathVariable id: Int) = try {
        postService.getByIndex(id)?.let {
            ResponseEntity(it, OK)
        } ?: ResponseEntity("Item with id $id not found", NOT_FOUND)
    } catch (e: Exception) {
        logger.error(e.stackTraceToString())
        ResponseEntity(e.message, INTERNAL_SERVER_ERROR)
    }

    @PostMapping
    fun add(@RequestBody post: PostEntity) = try {
        ResponseEntity(postService.add(post), OK)
    } catch (e: Exception) {
        logger.error(e.stackTraceToString())
        ResponseEntity(e.message, INTERNAL_SERVER_ERROR)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Int, @RequestBody post: PostEntity) = try {
        ResponseEntity(postService.update(id, post), OK)
    } catch (e: Exception) {
        logger.error(e.stackTraceToString())
        ResponseEntity(e.message, INTERNAL_SERVER_ERROR)
    }

    @PatchMapping("{id}")
    fun update(@PathVariable id: Int, @RequestBody partialPost: Map<String, Any>): ResponseEntity<Any> {
        return try {
            var post = postService.getByIndex(id) ?: return ResponseEntity("Item with id $id not found", NOT_FOUND)

            partialPost.forEach { (key, value) ->
                when(key) {
                    "author" -> post = post.copy(author = value as String)
                }
            }

            ResponseEntity(postService.update(post), OK)
        } catch (e: Exception) {
            logger.error(e.stackTraceToString())
            ResponseEntity(e.message, INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("{id}")
    fun remove(@PathVariable id: Int) = try {
        if(postService.getByIndex(id) != null) {
            postService.remove(id)
            ResponseEntity("Item with id $id deleted", OK)
        } else ResponseEntity("Item with id $id not found", NOT_FOUND)
    } catch (e: Exception) {
        logger.error(e.stackTraceToString())
        ResponseEntity(e.message, INTERNAL_SERVER_ERROR)
    }
}