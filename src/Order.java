import java.util.HashMap;
import java.util.Map;

public class Order {
    // 주문 목록
    private Map<Product, Integer> orderMap;
    // 총 금액
    private int totalPrice;
    // 요청 사항
    private String request;
    // 대기 번호
    private int waitingNumber;
    // 처리 상태
    // true : 주문 처리 완료
    // false : 주문 대기 상태
    private boolean status;

    public Order() {
        this.orderMap = new HashMap<>();
        this.totalPrice = 0;
        this.waitingNumber = 0;
    }

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Product, Integer> orderMap) {
        this.orderMap = orderMap;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice += totalPrice;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getWaitingNumber() {
        waitingNumber++;
        return waitingNumber;
    }

    /**
     * 주문 총 금액 초기화
     */
    public void clearTotalPrice() {
        this.totalPrice = 0;
    }
}
