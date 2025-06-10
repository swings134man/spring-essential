package com.lucas.kopringjpademo.modules.board.repository

import com.lucas.kopringjpademo.modules.board.entity.BoardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository: JpaRepository<BoardEntity, Long>{
    fun findOneById(id: Long): BoardEntity?

    // Entity 로 반환 후 DTO 변환을 위해 Fetch Join 사용 (Entity 2개 조인 조회)
    @Query("SELECT b FROM BoardEntity b JOIN FETCH b.kor WHERE b.id = :id")
    fun findByBoardAndKor(id: Long): BoardEntity?
}