package com.lucas.kopringjpademo.modules.dsl.entity

import com.lucas.kopringjpademo.common.Auditable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * DslEntity.kt: QueryDSL 을 위한 예제 Entity
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 12. 오전 1:27
 * @description:
 */
@Entity
@Table(name = "dsl")
class DslEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var address: String,
): Auditable()