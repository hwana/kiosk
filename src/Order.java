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
    //주문 시간
    private String orderTime;
    //주문 완료 처리 시간
    private String doneTime;

    public Order(Map<Product, Integer> orderMap, int totalPrice, String request, int waitingNumber, String orderTime) {
        this.orderMap = orderMap;
        this.totalPrice = totalPrice;
        this.request = request;
        this.waitingNumber = waitingNumber;
        this.orderTime = orderTime;
    }

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getRequest() {
        return request;
    }

    public int getWaitingNumber() {
        return waitingNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }
}
