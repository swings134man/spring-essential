package com.lucas.bootbasic.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class TestController {

    @GetMapping
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>("boot-basic", HttpStatus.OK);
    }

    @GetMapping("/error")
    public void errorResponse() {
        throw new RuntimeException("Error Test");
    }
}
