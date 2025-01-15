package com.lucas.bootbasic.dip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @package : com.lucas.bootbasic.dip
 * @name : SampleRepositoryImpl_second.java
 * @date : 2025. 1. 15. 오후 6:13
 * @author : lucaskang(swings134man)
 * @Description: 기존의 구현체인 one 말고 또하나의 구현체인 second Class
 * - 하지만 Spring 에서는 어떤 구현체를 주입받을지 명시적으로 지정하지 않으면 에러가 발생함
 * --> 이떄 @Primary, 또는 @Qualifier 를 사용하여 명시적으로 지정해주어야 함
 * --> Qualifier 는 주입받을 Bean 의 이름을 지정해주는 방법 @Qualifier("sampleRepositoryImpl_second")
 * --> @Primary 의 경우는 기본적으로 선택될 Bean 을 지정해주는 방법
 *
 * _one Class 에 @Primary 어노테이션을 붙여주었기 때문에 _one Class 가 주입됨
 * -> 해당 어노테이션 제거 후 Controller 에서 Qualifier 를 통해 주입받는 방법을 사용하면 _second Class 가 주입됨
**/
@Slf4j
@Service
public class SampleRepositoryImpl_second implements SampleRepository{
    @Override
    public void save(String msg) {
        log.info("SECOND #### Save message : {}", msg);
    }

    @Override
    public String get(String msg) {
        log.info("SECOND #### Get message : {}", msg);
        return msg;
    }
}
