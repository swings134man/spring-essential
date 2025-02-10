package com.lucas.bootbasic.modules.dip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @package : com.lucas.bootbasic.dip
 * @name : SampleRepositoryImpl_one.java
 * @date : 2025. 1. 15. 오후 6:05
 * @author : lucaskang(swings134man)
 * @Description: Sample Repository Implementation Class FIRST -> 자세한 설명은 _second Class 참조
**/
@Slf4j
@Service
@Primary
public class SampleRepositoryImpl_one implements SampleRepository{
    @Override
    public void save(String msg) {
        log.info("FIRST #### Save message : {}", msg);
    }

    @Override
    public String get(String msg) {
        log.info("FIRST #### Get message : {}", msg);
        return msg;
    }
}
