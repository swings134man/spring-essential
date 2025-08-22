package com.lucas.kopringjpademo.modules.coroutines.service

import com.lucas.kopringjpademo.common.logger
import com.lucas.kopringjpademo.modules.board.dto.BoardKorDTO
import com.lucas.kopringjpademo.modules.board.dto.toBoardKorDTO
import com.lucas.kopringjpademo.modules.board.repository.BoardRepository
import com.lucas.kopringjpademo.modules.board.service.BoardService
import com.lucas.kopringjpademo.modules.kor.repository.KorRepository
import com.lucas.kopringjpademo.modules.kor.service.KorService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StopWatch
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

    private val boardRepository: BoardRepository,
    private val korRepository: KorRepository
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
            val board = async(Dispatchers.IO) { boardRepository.findById(id).orElseThrow() }
            val kor = async(Dispatchers.IO) { korRepository.findById(id).orElseThrow() }

            result = toBoardKorDTO(board.await(), kor.await())
        }// millis

        logger.info("Coroutine findById() time: {} ms", times)

        return@coroutineScope result
    }//findById

    // 이렇게도 사용가능하지만, DB Call 같은 경우는 Blocking + 트랜잭션 경계가 깨질 수 있음
    // Spring 의 Transaction 은 같은 Coroutine Scope 내에서 관리되는게 좋음 위의 function 처럼
    @Transactional
    suspend fun findByIdInner(id: Long): BoardKorDTO {
        val times = StopWatch()
        times.start()

        val boardResult = CoroutineScope(Dispatchers.IO).async {
            boardRepository.findById(id).orElseThrow()
        }

        val korResult = CoroutineScope(Dispatchers.IO).async {
            korRepository.findById(id).orElseThrow()
        }

        val result = toBoardKorDTO(boardResult.await(), korResult.await())
        logger.info("Coroutine findByIdInner() result: {}", result)

        times.stop()
        logger.info("Coroutine findByIdInner() time: {} ms", times.totalTimeMillis)

        return result
    }



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