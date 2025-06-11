package com.lucas.kopringjpademo.rest

import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.service.DslService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DslController(
    private val dslService: DslService
) {

    @GetMapping("/api/dsl/like/{name}")
    fun findByNameLike(@PathVariable name: String): ResponseEntity<List<DslEntity>> {
        val result = dslService.findByNameLike(name)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/api/dsl/in")
    fun findByNameIn(@RequestBody names: List<String>): ResponseEntity<List<DslEntity>> {
        val result = dslService.findByNameIn(names)
        return ResponseEntity.ok(result)
    }
}