package hello.core.scope;

import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sound.sampled.Port;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    // 싱글톤이 기본값이기 때문에 명시를 안해줘도 된다
    @Scope("singleton")
    static class ClientBean {

        // ObjectFactory로 바꿔도 됨
        // ObjectProvider가 조금 더 많은 기능이 있다.
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeansProvider;

       /* @Autowired
        private Provider<PrototypeBean> prototypeBeansProvider;*/

        public int logic() {

            // getObject를 하면 그때 프로토타입 빈을 찾아서 호출해준다.
            // 다른 기능없이 딱 찾아주는 기능
            PrototypeBean prototypeBean = prototypeBeansProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }
        public int getCount() {
            return count;
        }

        @PostConstruct
        public void inti() {
            System.out.println("prototypeBean.inti" + this); //현재 나의 참조 값 보기
        }
        @PreDestroy
        public void destroy(){
            System.out.println("prototypeBean.destroy");
        }
    }
}
