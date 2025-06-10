package com.lucas.kopringjpademo.modules.board.dto

import com.lucas.kopringjpademo.modules.board.entity.BoardEntity
import com.lucas.kopringjpademo.modules.kor.entity.KorEntity

/**
 * BoardKorDTO.kt: kor Entity 를 Field 로 포함한 DTO
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 11. 오전 12:08
 * @description:
 */
data class BoardKorDTO (
    val id: Long? = null,
    val title: String,
    val content: String,
    val kor: KorEntity? = null
)

fun BoardEntity.toBoardKorDTO(): BoardKorDTO {
    return BoardKorDTO(
        id = this.id,
        title = this.title,
        content = this.content,
        kor = this.kor
    )
}