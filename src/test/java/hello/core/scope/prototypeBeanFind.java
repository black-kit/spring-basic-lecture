package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class prototypeBeanFind {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);


        // 이렇게 명시해도 테스트에서 destroy가 뜨지 않은 것을 볼 수 있다.
        ac.close();

        // 이렇게 직접 호출해줘야 종료된 것을 확인할 수 있다.
        prototypeBean1.destroy();
        prototypeBean2.destroy();
    }

    // 컴포넌트 어노테이션이 없는 것을 의문 제기할 수 잇다.
    // 위에서 컨테이너에 configure을 지정해주면, 클래스 자체를 컴포넌트 대상자로 보기 때문에
    // 컴포넌트로 바로 스프링 빈으로 등록한다.
    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("prototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("prototypeBean.destroy");
        }
    }
}

