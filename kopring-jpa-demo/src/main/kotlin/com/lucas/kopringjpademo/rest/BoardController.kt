package com.lucas.kopringjpademo.rest

import com.lucas.kopringjpademo.modules.board.dto.BoardKorDTO
import com.lucas.kopringjpademo.modules.board.dto.BoardWithKorDTO
import com.lucas.kopringjpademo.modules.board.entity.BoardEntity
import com.lucas.kopringjpademo.modules.board.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping("/api/board")
    fun createBoard(@RequestBody boardEntity: BoardEntity, @RequestParam korId: Long): ResponseEntity<BoardEntity> {
        val createdBoard = boardService.save(boardEntity, korId)
        return ResponseEntity.ok(createdBoard)
    }

    @GetMapping("/api/board")
    fun findAll(): ResponseEntity<List<BoardEntity>> {
        val boards = boardService.getAllBoards()
        return ResponseEntity.ok(boards)
    }

    @GetMapping("/api/board/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<BoardEntity?> {
        val board = boardService.getBoardById(id)
        return ResponseEntity.ok(board)
    }

    // ----------------- DTO --------------------------------
    @GetMapping("/api/board/with-kor/{id}")
    fun getBoardWithKorById(@PathVariable id: Long): ResponseEntity<BoardWithKorDTO> {
        val boardWithKor = boardService.getBoardWithKorById(id)
        return ResponseEntity.ok(boardWithKor)
    }

    @GetMapping("/api/board-kor/{id}")
    fun getBoardKorById(@PathVariable id: Long): ResponseEntity<BoardKorDTO> {
        val boardWithKor = boardService.getBoardKorById(id)
        return ResponseEntity.ok(boardWithKor)
    }

    @GetMapping("/api/dto/{id}")
    fun getBoardKorDTOById(@PathVariable id: Long): ResponseEntity<BoardKorDTO> {
        val boardKorDTO = boardService.getBoardKorDTOById(id)
        return ResponseEntity.ok(boardKorDTO)
    }
}