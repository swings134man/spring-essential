package com.lucas.kopringjpademo.modules.coroutines.service

import com.lucas.kopringjpademo.common.logger
import com.lucas.kopringjpademo.modules.board.dto.BoardKorDTO
import com.lucas.kopringjpademo.modules.board.dto.toBoardKorDTO
import com.lucas.kopringjpademo.modules.board.service.BoardService
import com.lucas.kopringjpademo.modules.kor.service.KorService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.system.measureTimeMillis

/**
 * CoroutineService.kt: Coroutine Service
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 7. 11. 오후 5:56
 * @description: 테스트를 위해 이미 존재하는 도메인을 사용(Board, Kor)
 */
@Service
class CoroutineService (
    private val boardService: BoardService,
    private val korService: KorService,
){

    val logger = logger()


    /**
     * @author: lucaskang(swings134man)
     * @since: 2025. 7. 11. 오후 6:05
     * @description: 코루틴을 사용: 여러 엔티티 동시 조회 후 반환
     * - measureTime 블록을 사용하여 실행 시간을 측정
     * - `병렬처리`
     */
    @Transactional
    suspend fun findById(id: Long): BoardKorDTO? = coroutineScope{

        // 결과를 할당할 변수
        var result: BoardKorDTO? = null

        val times = measureTimeMillis {
            // 2개의 작업 task
            val board = async { boardService.getBoardById(id) }
            val kor = async { korService.findById(id) }

            // Task Run
            val boardResult = board.await()
            val korResult = kor.await()

            result = toBoardKorDTO(boardResult, korResult)
        }// millis

        logger.info("Coroutine findById() time: {} ms", times)

        return@coroutineScope result
    }//findById



    // -------------------------------------------- Coroutine 사용 X --------------------------------------------
    /**
     * @author: lucaskang(swings134man)
     * @since: 2025. 7. 11. 오후 6:31
     * @description: findById() 와 동일한 기능수행 -> 코루틴 사용 X
     */
    @Transactional
    fun notCoFindById(id: Long): BoardKorDTO? {

        var result : BoardKorDTO? = null

        val times = measureTimeMillis {
            val board = boardService.getBoardById(id)
            val kor = korService.findById(id)

            result = toBoardKorDTO(board, kor)
        }

        logger.info("notCoFindById() time: {} ms", times)

        return result
    }

}