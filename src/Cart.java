import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cartMap;
    // 총 금액
    private int totalPrice;

    public Cart() {
        this.cartMap = new HashMap<>();
        this.totalPrice = 0;
    }

    public Map<Product, Integer> getCartMap() {
        return cartMap;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice += totalPrice;
    }

    public void clearCartMap() {
        this.cartMap.clear();
    }

    public void clearTotalPrice() {
        this.totalPrice = 0;
    }
}
