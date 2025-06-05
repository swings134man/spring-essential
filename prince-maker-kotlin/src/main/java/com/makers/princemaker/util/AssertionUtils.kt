package com.makers.princemaker.util

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.exception.PrinceMakerException

/**
 * AssertionUtils.kt:
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 5. 오후 6:30
 * @description: Assertion Utils if-throw 문으로 method 가 true 일때 해당 Exception 을 던질 수 있도록.
 */
// Exception 을 던져야할때 -> 객체가 true 면 해당 Exception 을 던진다.
fun Boolean.shouldNotTrue(princeMakerErrorCode: PrinceMakerErrorCode){
    if(this){
        throw PrinceMakerException(princeMakerErrorCode)
    }
}