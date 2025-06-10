package com.lucas.kopringjpademo.modules.board.repository

import com.lucas.kopringjpademo.modules.board.entity.BoardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<BoardEntity, Long>{
    fun findOneById(id: Long): BoardEntity?
}