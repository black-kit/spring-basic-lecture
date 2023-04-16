package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//가짜 네트워크 클라이언트
public class NetworkClient {
    private String url;
    public NetworkClient(){
        System.out.println("생성자 호출, url =  " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void connect() {
        System.out.println("connect = " + url);
    }
    public void call(String message) {
        System.out.println("call" + url + " message = " + message);
    }
    //서비스 종료 시
    public void disconnect() {
        System.out.println("close " + url);
    }
    //의존 관계 주입이 끝나면 호출해주겠다다

    @PostConstruct
    public void init()  {
       System.out.println("NetworkClient.init");
       connect();
       call("초기화 연결 메세지");
   }

   @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
