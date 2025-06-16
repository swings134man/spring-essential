package com.lucas.kopringjpademo.rest

import com.lucas.kopringjpademo.common.PageResponse
import com.lucas.kopringjpademo.modules.dsl.dto.DslResponseDTO
import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.service.DslService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
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

    // DTO Response
    @GetMapping("/api/dsl/address/like")
    fun findByAddressLike(@RequestParam address: String): ResponseEntity<List<DslResponseDTO>?> {
        val result = dslService.findByAddressLike(address)
        return ResponseEntity.ok(result)
    }

    // ---------------------------------------------- Paging ----------------------------------------------

    // Entity 가 아닌 Pageable 을 포함한 DTO 를 Response 하는것이 JSON Serialize 에 유리함.
    @GetMapping("/api/dsl/paging")
    fun findByNamePaging(@RequestParam name: String?, pageable: Pageable): ResponseEntity<Page<DslEntity>> {
        val result = dslService.findByNamePaging(name ?: "", pageable)
        return ResponseEntity.ok(result)
    }

    // 공통 Page DTO 를 사용하는 Paging Function
    @GetMapping("/api/dsl/paging/dto")
    fun findByNamePagingToDTO(@RequestParam name: String?, pageable: Pageable): ResponseEntity<PageResponse<DslEntity>> {
        val result = dslService.findByNamePagingToDTO(name ?: "", pageable)
        return ResponseEntity.ok(result)
    }

    // 공통 Page Util 을 사용한 function
    @GetMapping("/api/dsl/paging/util")
    fun findByNamePagingUtil(@RequestParam name: String?, pageable: Pageable): ResponseEntity<PageResponse<DslEntity>> {
        val result = dslService.findByNamePagingUtil(name ?: "", pageable)
        return ResponseEntity.ok(result)
    }

    // QuerydslRepositorySupport 를 사용한 Paging function sample
    @GetMapping("/api/dsl/paging/support")
    fun findByNamePagingQuerydslSupport(@RequestParam name: String?, pageable: Pageable): ResponseEntity<List<DslEntity?>?> {
        val result = dslService.findByNamePagingQuerydslSupport(name ?: "", pageable)
        return ResponseEntity.ok(result)
    }
}