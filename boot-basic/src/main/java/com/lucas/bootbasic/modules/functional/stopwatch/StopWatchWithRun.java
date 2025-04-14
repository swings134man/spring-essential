package com.lucas.bootbasic.modules.functional.stopwatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @package : com.lucas.bootbasic.modules.functional.stopwatch
 * @name : StopWatchWithRun.java
 * @date : 2025. 4. 14. 오후 3:43
 * @author : lucaskang(swings134man)
 * @Description: 특정 Method 의 실행시간을 측정하기 위한 StopWatch Util
 * - 공통화를 통해 모든 Class 에서 StopWatch 의존성을 사용하지 않고 해당 Util 만으로 사용 가능
 * - Work 자체에 void run() 메서드를 정의하여 해당 메서드에 대한 실행시간을 측정할 수 있도록 함
**/
@Slf4j
@RequiredArgsConstructor
@Service
public class StopWatchWithRun {

    private StopWatch stopWatch;

    public void startWithStopWatch(String methodName, Work work) {
        stopWatch = new StopWatch(methodName);
        stopWatch.start();
        log.info("Start method: {} -- Check Time", methodName);

        // Method Work Run
        work.run();

        stopWatch.stop();

        log.info("End method: {} -- working time: {}", methodName, stopWatch.getLastTaskInfo().getTimeSeconds());
    }

    public <T> T startWithStopWatch(String methodName, WorkWithReturn<T> work) {
        stopWatch = new StopWatch(methodName);
        stopWatch.start();
        log.info("Start method: {} -- Check Time", methodName);

        // Method Work Run
        T run = work.run();

        stopWatch.stop();
        log.info("End method: {} -- working time: {}", methodName, stopWatch.getLastTaskInfo().getTimeSeconds());

        return run;
    }

    @FunctionalInterface
    public interface Work {
        void run();
    }

    @FunctionalInterface
    public interface WorkWithReturn<T> {
        T run();
    }
}
