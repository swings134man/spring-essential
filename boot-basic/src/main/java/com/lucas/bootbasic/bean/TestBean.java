package com.lucas.bootbasic.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * TestBean -> TestBean.testBeanMe
 *
 * ApplicationContext: Spring boot 의 Bean 의 생성, 관리 역할 수행
 *  -> DI 관리
 *  -> Bean 의 LifeCycle 관리 (생성, 소멸, 관리)
 *  -> Application 의 환경 설정 관리 (properties, yml, profile ,,,)
 *  -> Event 발생 및 처리: ApplicationEventPublisher
 *
 * - SpringApplication.run() 호출시 ApplicationContext 초기화, 생성이 이루어짐
 *
 * ConfigurableApplicationContext: 기본적인 ApplicationContext 에 더하여 설정 변경 기능 제공
 * AnnotationConfigApplicationContext: @Configuration 기반 설정 Class 를 사용하는 Application 에 대한 Context
 */
@Component
public class TestBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public void testBeanMe() {
        System.out.println("TestBean.testBeanMe");
    }

    // Spring Boot - Get all beans loaded by Spring Boot and print All Them
    @Bean
    public void getAllBeans() {
//        if(applicationContext != null) {
//            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//            for (String beanName : beanDefinitionNames) {
//                System.out.println("Bean: "+ beanName);
//            }
//        }
    }
}
