package com.lucas.bootbasic.bean.scope.BeanScope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring boot - Bean Scope Test: ProtoType
 */
@SpringBootTest
class BeanScopeTest {

    @Autowired
    private ApplicationContext context;

    // Not container Managed Object: new 로 생성된 객체는 Spring Container 에 의해 관리되지 않음
    @Test
    @DisplayName("ProtoType Bean Test")
    void protoTypeTest() {
        ProtoTypeTest p1 = new ProtoTypeTest();
        ProtoTypeTest p2 = new ProtoTypeTest();

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);

        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("ProtoType Bean Test: get Context")
    void protoTypeTest_byContext() {
        ProtoTypeTest p1 = context.getBean(ProtoTypeTest.class);
        ProtoTypeTest p2 = context.getBean(ProtoTypeTest.class);

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);

        assertNotEquals(p1, p2);

        // Predestroy Test
        p1.destroy();


    }

    @Test
    @DisplayName("Singleton Bean Test: Context")
    void singletonBeanTest_context() {
        SingletonTest singletonTest1 = context.getBean(SingletonTest.class);
        SingletonTest singletonTest2 = context.getBean(SingletonTest.class);

        System.out.println("singletonTest1: " + singletonTest1);
        System.out.println("singletonTest2: " + singletonTest2);

        assertEquals(singletonTest1, singletonTest2);
    }
}