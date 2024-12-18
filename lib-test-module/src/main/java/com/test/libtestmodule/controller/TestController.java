package com.test.libtestmodule.controller;

import com.lucas.samplelib.utils.Calculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final Calculator calculator;

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/calc/{a}/{b}")
    public ResponseEntity<Integer> testCalc(@PathVariable("a") int a, @PathVariable("b") int b) {
        log.info("Result: {}", calculator.add(a, b));
        int result = calculator.add(a, b);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
