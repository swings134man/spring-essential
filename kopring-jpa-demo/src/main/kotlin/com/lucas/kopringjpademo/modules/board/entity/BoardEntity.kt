package com.lucas.kopringjpademo.modules.board.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.lucas.kopringjpademo.common.Auditable
import com.lucas.kopringjpademo.modules.kor.entity.KorEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "board")
class BoardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kor_id")
    @JsonBackReference
    var kor: KorEntity? = null
): Auditable()