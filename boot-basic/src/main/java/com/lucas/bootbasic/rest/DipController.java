package com.lucas.bootbasic.rest;

import com.lucas.bootbasic.modules.dip.SampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @package : com.lucas.bootbasic.rest
 * @name : DipController.java
 * @date : 2025. 1. 15. 오후 6:06
 * @author : lucaskang(swings134man)
 * @Description: Dip Sample Repository Controller
 * - Interface 인 SampleRepository 를 의존성 주입 받음, 그렇게 되면 interface 의 구현체인 SampleRepositoryImpl_one 을 주입받음 -> 수정후 SECOND 로 변경
 * - Lombok 의 @RequiredArgsConstructor 사용시 {@code @Qualifier} 가 무시됨. 때문에 별도의 설정이 없다면 생성자 주입 방식을 사용하거나 롬복설정을 추가해야함 -> /src/main/java/lombok.config 추가
**/
@RestController
//@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/dip")
public class DipController {

    @Qualifier("sampleRepositoryImpl_second") // Qualifier 를 통해 특정 Bean 주입 가능
    private final SampleRepository sampleRepository;

    // Qualifier 를 통해 특정 Bean 주입 가능 -> @RequiredArgsConstructor 사용시 @Qualifier 가 무시됨
    public DipController(@Qualifier("sampleRepositoryImpl_second") SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @PostMapping("/{msg}")
    public ResponseEntity<Object> save(@PathVariable("msg") String msg) {
        log.info("Save message : {}", msg);
        sampleRepository.save(msg);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/{msg}")
    public ResponseEntity<String> get(@PathVariable("msg") String msg) {
        log.info("Get message : {}", msg);
        return new ResponseEntity<>(sampleRepository.get(msg), HttpStatus.OK);
    }
}
