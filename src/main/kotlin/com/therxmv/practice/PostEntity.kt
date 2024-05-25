package com.therxmv.practice

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "post")
data class PostEntity(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Int = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val topic: String = "",
)