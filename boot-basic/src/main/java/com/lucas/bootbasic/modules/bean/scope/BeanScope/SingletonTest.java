package com.lucas.bootbasic.modules.bean.scope.BeanScope;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SingletonTest {

    public SingletonTest() {
        System.out.println("SingletonTest Bean Constructor!!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("SingletonTest Bean Destroy!!");
    }
}
