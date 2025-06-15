package com.lucas.kopringjpademo.modules.dsl.dto

import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity

data class DslResponseDTO (
    val id: Long? = null,
    val name: String,
    val address: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

fun DslEntity.toResponseDTO(): DslResponseDTO {
    return DslResponseDTO(
        id = this.id,
        name = this.name,
        address = this.address,
        createdAt = this.createdAt?.toString(),
        updatedAt = this.updatedAt?.toString()
    )
}