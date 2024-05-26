package com.therxmv.practice.data

import com.therxmv.practice.model.PostEntity
import org.springframework.data.repository.CrudRepository

interface PostRepository: CrudRepository<PostEntity, Int>