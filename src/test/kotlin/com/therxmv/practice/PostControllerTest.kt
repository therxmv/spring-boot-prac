package com.therxmv.practice

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PostControllerTest {

    private val mockNotFoundEntity = { id: Int -> ResponseEntity("Item with id $id not found", HttpStatus.NOT_FOUND) }
    private val mockOkEntity = { entity: Any -> ResponseEntity(entity, HttpStatus.OK) }
    private val mockInternalErrorEntity = { e: Exception -> ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR) }

    private val mockException: IllegalArgumentException = mock()
    private val mockPostEntity: PostEntity = mock()
    private val mockPostService: PostService = mock()

    private val systemUnderTest = PostController(mockPostService)

    @Test
    fun `get all items from db`() {
        systemUnderTest.getAll()

        verify(mockPostService).getAll()
    }

    @Test
    fun `getAll returns internal error when exception thrown`() {
        `when`(mockPostService.getAll()).thenThrow(mockException::class.java)

        val result = systemUnderTest.getAll()

        assertEquals(result, mockInternalErrorEntity(mockException))
    }

    @Test
    fun `getByIndex returns NOT_FOUND when item not found`() {
        val id = -1

        `when`(mockPostService.getByIndex(id)).thenReturn(null)

        val result = systemUnderTest.getByIndex(id)

        assertEquals(result, mockNotFoundEntity(id))
    }

    @Test
    fun `getByIndex returns OK when item found`() {
        val id = 1

        `when`(mockPostService.getByIndex(id)).thenReturn(mockPostEntity)

        val result = systemUnderTest.getByIndex(id)

        assertEquals(result, mockOkEntity(mockPostEntity))
    }

    @Test
    fun `add item to the db`() {
        systemUnderTest.add(mockPostEntity)

        verify(mockPostService).add(mockPostEntity)
    }

    @Test
    fun `add returns internal error when exception thrown`() {
        `when`(mockPostService.add(mockPostEntity)).thenThrow(mockException::class.java)

        val result = systemUnderTest.add(mockPostEntity)

        assertEquals(result, mockInternalErrorEntity(mockException))
    }

    @Test
    fun `put item to the db`() {
        systemUnderTest.update(1, mockPostEntity)

        verify(mockPostService).update(1, mockPostEntity)
    }

    @Test
    fun `update returns internal error when exception thrown`() {
        `when`(mockPostService.update(1, mockPostEntity)).thenThrow(mockException::class.java)

        val result = systemUnderTest.update(1, mockPostEntity)

        assertEquals(result, mockInternalErrorEntity(mockException))
    }

    @Test
    fun `update returns NOT_FOUND when post is null when patch`() {
        val id = -1

        `when`(mockPostService.getByIndex(id)).thenReturn(null)

        val result = systemUnderTest.update(id, emptyMap())

        assertEquals(result, mockNotFoundEntity(id))
    }

    @Test
    fun `patch item in the db`() {
        val id = 1

        `when`(mockPostService.getByIndex(id)).thenReturn(mockPostEntity)

        systemUnderTest.update(id, emptyMap())

        verify(mockPostService).update(mockPostEntity)
    }

    @Test
    fun `delete item if item is not null`() {
        val id = -1

        `when`(mockPostService.getByIndex(id)).thenReturn(null)

        val result = systemUnderTest.remove(id)

        assertEquals(systemUnderTest.remove(id), mockNotFoundEntity(id))
    }

    @Test
    fun `don't delete item if item is null`() {
        val id = 1

        `when`(mockPostService.getByIndex(id)).thenReturn(mockPostEntity)

        val result = systemUnderTest.remove(id)

        assertEquals(result, mockOkEntity("Item with id $id deleted"))
    }
}