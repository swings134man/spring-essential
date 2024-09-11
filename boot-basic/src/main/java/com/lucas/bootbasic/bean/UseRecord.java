package com.lucas.bootbasic.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java Record keywords
 *
 * record: Java 16 부터 추가된 기능
 *
 */
@Configuration
public class UseRecord {

    @Bean
    public Person personBean() {
        var person = new Person("Lucas", 30);
        System.out.println("record: " + person.name());
        System.out.println("record: " + person);
        return person;
    }

    // 이후 annotationConfigApplicationContext 를 통해 Bean 을 호출하면, record 를 통해 생성된 객체가 출력됨
    // sample
//    @Bean
//    public void getBeanNamePerson() {
//        var context = new AnnotationConfigApplicationContext(BootBasicApplication.class);
//        System.out.println("Bean Get Person :" + context.getBean("personBean"));
//    }
}

// record: getter, setter, equals, hashCode, toString 메소드를 자동 생성
record Person(String name, int age) {}
