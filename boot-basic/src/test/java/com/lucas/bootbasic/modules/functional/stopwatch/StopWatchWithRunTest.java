package com.lucas.bootbasic.modules.functional.stopwatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @package : com.lucas.bootbasic.modules.functional.stopwatch
 * @name : StopWatchWithRunTest.java
 * @date : 2025. 4. 14. 오후 3:50
 * @author : lucaskang(swings134man)
 * @Description: StopWatch Util Test
**/
@SpringBootTest
class StopWatchWithRunTest {

    @Autowired
    private StopWatchWithRun runner;

    public void testMethod() throws InterruptedException {
        System.out.println("testMethod Start");
        Thread.sleep(3000);
        System.out.println("testMethod End");
    }

    public boolean testMethodWithReturn() throws InterruptedException {
        System.out.println("testMethod Start");
        Thread.sleep(3000);
        System.out.println("testMethod End");

        return true;
    }

    @Test
    @DisplayName("1. void Method Test")
    void startWithStopWatch() {
        runner.startWithStopWatch("testMethod", () -> {
            try {
                testMethod();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    @DisplayName("2. Return <T> Method Test")
    void startWithReturn() {
        boolean result = runner.startWithStopWatch("testMethod", () -> {
            try {
                return testMethodWithReturn();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertTrue(result);
    }
}