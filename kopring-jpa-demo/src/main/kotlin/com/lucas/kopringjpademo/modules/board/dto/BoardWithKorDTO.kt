package com.lucas.kopringjpademo.modules.board.dto

import com.lucas.kopringjpademo.modules.board.entity.BoardEntity

/**
 * BoardWithKorDTO.kt: kor Entity 의 특정 Field 만을 포함한 DTO
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 11. 오전 12:08
 * @description:
 */
data class BoardWithKorDTO(
    val id: Long? = null,
    val title: String,
    val content: String,
    val korId: Long? = null,
    val korName: String? = null
)

fun BoardEntity.toBoardWithKorDTO(): BoardWithKorDTO {
    return BoardWithKorDTO(
        id = this.id,
        title = this.title,
        content = this.content,
        korId = this.kor?.id,
        korName = this.kor?.name
    )
}
