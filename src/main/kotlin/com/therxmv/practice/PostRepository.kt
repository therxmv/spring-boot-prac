package com.therxmv.practice

import org.springframework.data.repository.CrudRepository

interface PostRepository: CrudRepository<PostEntity, Int>