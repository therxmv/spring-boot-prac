package com.therxmv.practice

import com.therxmv.practice.data.PostRepository
import com.therxmv.practice.data.PostService
import com.therxmv.practice.model.PostEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PostServiceTest {

    private val mockPostEntity: PostEntity = mock()
    private val mockPostRepository: PostRepository = mock()

    private val systemUnderTest = PostService(mockPostRepository)

    @Test
    fun `get all items`() {
        `when`(mockPostRepository.findAll()).thenReturn(listOf(mockPostEntity))

        val result = systemUnderTest.getAll()

        assertEquals(result, listOf(mockPostEntity))
    }

    @Test
    fun `add an item`() {
        `when`(mockPostRepository.save(mockPostEntity)).thenReturn(mockPostEntity)

        val result = systemUnderTest.add(mockPostEntity)

        assertEquals(result, mockPostEntity)
    }

    @Test
    fun `update an item`() {
        val id = 1

        `when`(mockPostRepository.save(mockPostEntity.copy(id = id))).thenReturn(mockPostEntity)

        val result = systemUnderTest.update(id, mockPostEntity)

        assertEquals(result, mockPostEntity)
    }

    @Test
    fun `test updating a post`() {
        `when`(mockPostRepository.save(mockPostEntity)).thenReturn(mockPostEntity)

        val result = systemUnderTest.update(mockPostEntity)

        assertEquals(result, mockPostEntity)
    }

    @Test
    fun `remove an item`() {
        systemUnderTest.remove(1)

        verify(mockPostRepository).deleteById(1)
    }
}