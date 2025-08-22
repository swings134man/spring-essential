package com.lucas.kopringjpademo.rest

import com.lucas.kopringjpademo.modules.board.dto.BoardKorDTO
import com.lucas.kopringjpademo.modules.coroutines.service.CoroutineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * CoroutineController.kt: MVC + Coroutine Rest Controller
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 7. 11. 오후 5:55
 * @description:
 */
@RestController
@RequestMapping("/routine")
class CoroutineController(
    private val service: CoroutineService
) {


    @GetMapping("/id/{id}")
    suspend fun findById(@PathVariable id: Long): ResponseEntity<BoardKorDTO?> {
        val result = service.findById(id)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/in/{id}")
    suspend fun findByIdIn(@PathVariable id: Long): ResponseEntity<BoardKorDTO?> {
        val result = service.findByIdInner(id)
        return ResponseEntity.ok(result)
    }



    // -------------------------------------------- Coroutine 사용 X --------------------------------------------

    @GetMapping("/not/{id}")
    suspend fun notCoFindById(@PathVariable id: Long): ResponseEntity<BoardKorDTO?> {
        val result = service.notCoFindById(id)
        return ResponseEntity.ok(result)
    }
}