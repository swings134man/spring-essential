package com.lucas.kopringjpademo.rest

import com.lucas.kopringjpademo.modules.kor.entity.KorEntity
import com.lucas.kopringjpademo.modules.kor.service.KorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KorController(
    private val korService: KorService
) {

    @PostMapping("/api/kor")
    fun save(@RequestBody korEntity: KorEntity): ResponseEntity<KorEntity> {
        val result = korService.save(korEntity)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/api/kor")
    fun findAll(): ResponseEntity<List<KorEntity>?> {
        val result = korService.findAllKor()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/api/kor/{id}")
    fun findId(@PathVariable(value = "id") id: Long): ResponseEntity<KorEntity?> {
        val result = korService.findById(id)
        return ResponseEntity.ok(result)
    }
}