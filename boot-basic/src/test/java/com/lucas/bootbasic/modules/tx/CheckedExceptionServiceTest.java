package com.lucas.bootbasic.modules.tx;

import com.lucas.bootbasic.modules.models.test.TestEntity;
import com.lucas.bootbasic.modules.models.test.TestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @package : com.lucas.bootbasic.modules.tx
 * @name : CheckedExceptionServiceTest.java
 * @date : 2025. 5. 22. 오후 10:13
 * @author : lucaskang(swings134man)
 * @Description: Checked, Unchecked Exception 테스트
 *
 * Rollback Test 를 위해 @Transactional 어노테이션을 사용하지 않음. -> 테스트 전체가 Transactional 안에 존재함으로, 롤백 테스트가 무력화됨
**/
@SpringBootTest
class CheckedExceptionServiceTest {

    @Autowired
    private TestRepository repository;

    @Autowired
    private CheckedExceptionService service;


    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    private static TestEntity getTestEntity(String value) {
        TestEntity entity = new TestEntity();
        entity.setValue(value);
        return entity;
    }

    @Test
    @DisplayName("1. Unchecked Exception: Rollback : True")
    public void ueNotRollback() {
        // given
        String value = "1";

        TestEntity entity = getTestEntity(value);

        // when
        try {
            service.saveUncheckedEx(entity, true); // true면 예외 발생
            fail("예외가 발생해야 합니다");
        } catch (RuntimeException e) {
            // expected
        }

        // then
        assertTrue(repository.findByValue(value).isEmpty(), "롤백되어 저장되지 않아야 함");
    }

    @Test
    @DisplayName("2. Checked Exception(IOException: Rollback: False")
    void ceRollback() {
        // given
        String value = "IO";

        TestEntity entity = getTestEntity(value);

        // when
        try {
            service.saveCheckedEx(entity, true);
            fail("IOException 예외가 발생해야 합니다");
        } catch (IOException e) {
            // expected
        }

        // then
        assertNotNull(repository.findByValue(value), "롤백되지 않아야 함"); // 저장됨.
    }

    @Test
    @DisplayName("3. Checked Exception(Exception: Rollback: False")
    void ceEx() {
        // given
        String value = "Ex";

        TestEntity entity = getTestEntity(value);

        // when
        try {
            service.saveEx(entity, true);
            fail("Exception 예외가 발생해야 합니다");
        } catch (Exception e) {
            // expected
        }

        // then
        assertNotNull(repository.findByValue(value), "롤백되지 않아야 함"); // 저장됨.
    }

    // -------------------------------- After Transactional Fix(Rollback 처리 되도록) -----------------------------------

    /**
     * rollBackFor 를 사용하였기에 호출한곳에서 처리해줘야 한다
     * -> 해당 코드에서는 try/catch 로 핸들링, 호출한곳 즉 해당 테스트 코드(외부로)로 예외가 전파되었고,
     * rollbackFor 로 설정한 IOException 이 발생하였기에 Rollback 처리가 됨
     */
    @Test
    @DisplayName("4. Checked Exception(IOException: Rollback: True")
    void ceIOExRollback() {
        // given
        String value = "IO Rollback";

        TestEntity entity = getTestEntity(value);

        // when
        try {
            service.saveRollbackIOE(entity, true);
            fail("Exception 예외가 발생해야 합니다");
        } catch (Exception e) {
            // expected
        }

        // then
        assertTrue(repository.findByValue(value).isEmpty(), "롤백 되어야 함");
    }

    /**
     * 롤백처리 됨 -> throws IOException 으로 호출한곳에서 처리됨 -> Exception 이 발생함으로 Rollback 됨
     */
    @Test
    @DisplayName("4-2. Checked Exception(IOException: Rollback: True - throws IOException")
    void ceIOExRollback2() throws IOException {
        // given
        String value = "IO Rollback";

        TestEntity entity = getTestEntity(value);

        // when
        service.saveRollbackIOE(entity, true);

        // then
//        assertTrue(repository.findByValue(value).isEmpty(), "롤백 되어야 함");
    }


    @Test
    @DisplayName("5. Checked Exception(Exception: Rollback: TRUE")
    void ceExRollback() {
        // given
        String value = "Ex Rollback";

        TestEntity entity = getTestEntity(value);

        // when
        try {
            service.saveRollbackEx(entity, true);
            fail("Exception 예외가 발생해야 합니다");
        } catch (Exception e) {
            // expected
        }

        // then
        assertTrue(repository.findByValue(value).isEmpty(), "롤백 되어야 함");
    }

}