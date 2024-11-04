package com.lucas.bootbasic.bean.scope.BeanScope;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ProtoType Bean Test Class
 */
@Component
@Scope("prototype")
public class ProtoTypeTest {

    public ProtoTypeTest() {
        System.out.println("ProtoTypeTest Bean Constructor!!");
    }


    /**
     * 프로토타입 Bean 은 Spring Container 가 생명주기 관리를 하지않기에
     * 해당 @PreDestroy 는 호출되지 않음
     * 즉 종료 호출은 직접 해줘야 한다.
     * 다만, 공식 docs 상, 더이상 해당 Bean 이 사용되지 않는다면
     * 해당 Bean 은 GC 에 의해 소멸된다고 한다.
     *
     * 수동으로 소멸시켜야 할떄는: DB, Network Connection 과 같이 리소스를 사용할 경우이며
     * 이런 경우에는 @PreDestroy 를 사용하여 수동으로 소멸시킨다.
     */
    @PreDestroy
    public void destroy() {
        System.out.println("ProtoTypeTest Bean Destroy!!");
    }
}
