package com.lucas.kopringjpademo.modules.kor.entity

import com.lucas.kopringjpademo.common.Auditable
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener

/**
 * KorEntity.kt: kor Entity
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 9. 오후 9:25
 * @description:
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "kor")
class KorEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var age: Int,
    var isActive: Boolean = true
): Auditable()