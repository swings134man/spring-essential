package com.lucas.kopringjpademo.modules.board.service

import com.lucas.kopringjpademo.modules.board.dto.BoardKorDTO
import com.lucas.kopringjpademo.modules.board.dto.BoardWithKorDTO
import com.lucas.kopringjpademo.modules.board.dto.toBoardKorDTO
import com.lucas.kopringjpademo.modules.board.dto.toBoardWithKorDTO
import com.lucas.kopringjpademo.modules.board.entity.BoardEntity
import com.lucas.kopringjpademo.modules.board.repository.BoardRepository
import com.lucas.kopringjpademo.modules.kor.service.KorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
  private val boardRepository: BoardRepository,
  private val korService: KorService
) {

    @Transactional
    fun save(boardEntity: BoardEntity, korId: Long?): BoardEntity {
        return if (boardEntity.id == null) {
            createBoard(boardEntity, korId)
        } else {
            updateBoard(boardEntity.id!!, boardEntity)
        }
    }

    private fun createBoard(board: BoardEntity, korId: Long?): BoardEntity {
        val korResult = korService.findById(korId!!)
        board.kor = korResult
        return boardRepository.save(board)
    }

    private fun updateBoard(id: Long, updatedBoard: BoardEntity): BoardEntity {
        return if (boardRepository.existsById(id)) {
            updatedBoard.id = id
            boardRepository.save(updatedBoard)
        } else {
            throw IllegalArgumentException("Board with id $id not found")
        }
    }

    @Transactional(readOnly = true)
    fun getAllBoards(): List<BoardEntity> {
        return boardRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getBoardById(id: Long): BoardEntity? {
        return boardRepository.findById(id)
            .orElseThrow({ IllegalArgumentException("Board with id $id not found") })
    }

    @Transactional
    fun deleteBoard(id: Long) {
        boardRepository.deleteById(id)
    }

    // -------------------------------- DTO --------------------------------
    // kor 의 특정 필드만을 포함한 DTO 반환 (BoardWithKorDTO)
    @Transactional(readOnly = true)
    fun getBoardWithKorById(id: Long): BoardWithKorDTO {
        return boardRepository.findById(id).orElseThrow(
            { IllegalArgumentException("Board with id $id not found") }
        ).toBoardWithKorDTO()
    }

    // kor Entity 를 포함한 DTO 반환 (BoardKorDTO)
    @Transactional(readOnly = true)
    fun getBoardKorById(id: Long): BoardKorDTO {
        return boardRepository.findByBoardAndKor(id)?.toBoardKorDTO()
            ?: throw IllegalArgumentException("Board with id $id not found")
    }
}