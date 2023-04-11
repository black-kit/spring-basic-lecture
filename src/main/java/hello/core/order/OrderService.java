package hello.core.order;

public interface OrderService {

    // 주문 결과를 반환하는 인터페이스
    Order createOrder(Long memberId, String itemName, int itemPice);
}
