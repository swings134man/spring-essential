package com.lucas.bootbasic.modules.tx;

import com.lucas.bootbasic.modules.models.test.TestEntity;
import com.lucas.bootbasic.modules.models.test.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @package : com.lucas.bootbasic.modules.tx
 * @name : CheckedExceptionService.java
 * @date : 2025. 5. 22. 오후 5:57
 * @author : lucaskang(swings134man)
 * @Description: Checked Exception, Unchecked Exception 에 대한 Service Class
 *
 * - Unchecked Exception 은 RuntimeException 을 상속받는 모든 Exception => 예외처리를 강제하지 않음(RuntimeException) 실행중에 일어난 예외를 모두 잡기 힘들다 -> @Transactional 에서는 기본적으로 롤백하도록 되어있음.
 *
 * - Checked Exception 은 RuntimeException 을 상속받지 않는 모든 Exception
 *      - 예외처리를 필수로 해주어야 한다.(throws xxException, try/catch)
 *      - 처리 방법
 *          - Unchecked Exception 으로의 변환, @Transactional(rollbackFor = Exception.class) 를 통해 처리가능
 *              - 다만, 어노테이션을 사용하는 경우 상위 메서드에도 Exception 처리를 해주어야 한다.
 *              - 혹은 try/catch 문을 사용하여 핸들링 하는 방법도 존재한다.
 *                  - throw e 를 통해 예외를 다시 던져서 롤백을 유도함(메서드를 호출한곳으로)
**/
@Service
@RequiredArgsConstructor
@Slf4j
public class CheckedExceptionService {

    private final TestRepository testRepository;


    // ----------------------------------- Test Transactional -----------------------------------

    // Rollback
    @Transactional
    public TestEntity saveUncheckedEx(TestEntity entity, boolean isEx) {
        testRepository.save(entity);

        // Unchecked Exception
        if(isEx) {
            throw new RuntimeException("Unchecked Exception");
        }

        return entity;
    }

    // Not Rollback
    @Transactional
    public TestEntity saveCheckedEx(TestEntity entity, boolean isEx) throws IOException {
        testRepository.save(entity);

        // Checked Exception
        if(isEx) {
            throw new IOException("Checked IOException");
        }

        return entity;
    }

    // Not Rollback
    @Transactional
    public TestEntity saveEx(TestEntity entity, boolean isEx) throws Exception {
        testRepository.save(entity);

        // Checked Exception
        if(isEx) {
            throw new Exception("Checked Exception");
        }

        return entity;
    }


    // ----------------------------------- After Transactional Fix -----------------------------------
    // Rollback - IOException -> 호출한곳에서 처리하는 방식
    @Transactional(rollbackFor = IOException.class)
    public TestEntity saveRollbackIOE(TestEntity entity, boolean isEx) throws IOException {
        testRepository.save(entity);

        // Checked Exception
        if(isEx) {
            throw new IOException("Checked IOException");
        }

        return entity;
    }

    // Rollback - Unchecked Exception 으로 변환 처리하여
    @Transactional
    public TestEntity saveRollbackEx(TestEntity entity, boolean isEx) {
        try {
            testRepository.save(entity);

            // Checked Exception
            if(isEx) {
                throw new Exception("Checked Exception");
            }
        } catch (Exception e) {
            // checked to unchecked
            log.info("Checked Exception To Unchecked Exception");
            throw new RuntimeException(e);
        }

        return entity;
    }
}
