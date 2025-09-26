package com.lucas.hexagonalkotlin.adapter.out.persistence.post.events

import com.lucas.hexagonalkotlin.adapter.out.persistence.post.PostJpaRepository
import com.lucas.hexagonalkotlin.domain.post.PostEventDto
import com.lucas.hexagonalkotlin.infrastructure.common.logger
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

/**
 * PostEventHandler.kt: post 도메인에 대한 이벤트 핸들러(Event Handler)
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 26. 오후 5:31
 * @description:
 */
@Component
class PostEventHandler(
    private val postJpaRepository: PostJpaRepository
) {

    val logger = logger()

    // View Count 증가 이벤트 처리 - Caller 트랜잭션 커밋 후에 별도의 쓰레드에서 실행 (비동기)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("multiExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleViewCountIncrementEvent(incrementEvent: PostEventDto.PostViewIncrementEventDto) {
        val result = postJpaRepository.increaseViewCount(incrementEvent.id, incrementEvent.viewCount)

        if(result == 0) logger.error("Failed to increase view count for Post ID: ${incrementEvent.id}")
    }

}