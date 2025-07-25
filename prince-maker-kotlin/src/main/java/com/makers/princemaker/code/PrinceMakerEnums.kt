package com.makers.princemaker.code

/**
 * PrinceMakerEnums.kt: Enums Class -> By Java
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 5. 오전 2:06
 * @description: Java Enum 을 Kotlin Enum 으로 변환및 병합한 클래스
 */
enum class PrinceMakerErrorCode(val message: String) {
    NO_SUCH_PRINCE("해당되는 왕자님이 안계십니다."),
    DUPLICATED_PRINCE_ID("고유 왕자번호가 중복됩니다."),
    LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH("왕자 레벨과 연차가 맞지 않습니다."),

    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.");
}

enum class StatusCode (
    val description: String
){
    HEALTHY("건강한"),
    WOUNDED("부상당한");
}
