import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order {
    private List<Product> orderList = new ArrayList<>();

    private int waitingNum;

    public Order() {
        this.waitingNum = 1;
    }

    public List<Product> getOrderList() {
        return orderList;
    }

    public void setWaitingNum(int waitingNum) {
        this.waitingNum = waitingNum;
    }

    public int getWaitingNum() {
        return waitingNum;
    }


    /**
     * 장바구니 담기
     * @param product
     */
    public void addProduct(Product product) {
        product.print();
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        Scanner sc = new Scanner(System.in);
        int cartNum = sc.nextInt();

        if (cartNum == 1) {
            orderList.add(product);
            System.out.println("🛒 " + product.getName() + "가 장바구니에 추가되었습니다. 🛒");
        }else{
            System.out.println("❗ 취소되었습니다. ❗");
        }
    }

    /**
     * 주문내역, 총 가격 출력
     * @return
     */
    public int printOrderList(){
        System.out.println("아래와 같이 주문하시겠습니까?");
        System.out.println("[ Orders ]");

        int totalPrice = 0;

        for (Product product : getOrderList()) {
            totalPrice += product.getPrice();
            product.print();
        }

        System.out.println("[ Total Price ]");
        System.out.println(totalPrice);

        System.out.println("1. 주문     2. 메뉴판");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public void cancelOrder(){
        System.out.println("진행중이던 주문을 취소하시겠습니까?");
        System.out.println("1. 확인      2. 취소");

        Scanner sc = new Scanner(System.in);

        if(sc.nextInt() == 1){
            orderList.clear();
            System.out.println("진행중이던 주문이 취소되었습니다.");
        }
    }
}
