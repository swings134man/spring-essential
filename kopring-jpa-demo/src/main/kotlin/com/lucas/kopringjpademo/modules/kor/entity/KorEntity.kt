package com.lucas.kopringjpademo.modules.kor.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.lucas.kopringjpademo.common.Auditable
import com.lucas.kopringjpademo.modules.board.entity.BoardEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.OneToMany
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * KorEntity.kt: kor Entity
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 9. 오후 9:25
 * @description:
 */
@Entity
@Table(name = "kor")
class KorEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var age: Int,
    var isActive: Boolean = true,


    @OneToMany(mappedBy = "kor", fetch = FetchType.LAZY ,cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    var boards: MutableList<BoardEntity> = mutableListOf()

): Auditable() {

    fun addBoard(board: BoardEntity) {
        boards.add(board)
        board.kor = this
    }
}
