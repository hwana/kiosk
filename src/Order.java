import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Order {

    private static final String YES_OR_NO = "^[1-2]*$";

    private Map<Product, Integer> orderList = new HashMap<>();    //주문 리스트
    private final Map<String, Integer> allOrderList = new HashMap<>(); //총 주문 리스트
    private int totalPrice;     //주문 별 총 판매금액
    private int allTotalPrice;    //전체 판매금액
    private int waitingNum;    //대기번호

    public Order() {
        this.waitingNum = 1;
    }

    public Map<Product, Integer> getOrderList() {
        return orderList;
    }

    public Map<String, Integer> getAllOrderList() {
        return allOrderList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice() {
        this.allTotalPrice += this.totalPrice;
    }

    public int getWaitingNum() {
        return waitingNum;
    }

    public void setWaitingNum(int waitingNum) {
        this.waitingNum = waitingNum;
    }


    /**
     * 장바구니 담기
     *
     * @param product : 사용자가 선택한 상품
     */
    public void addProduct(Product product) throws Exception {
        product.print();    //선택한 메뉴 출력
        String result = printQuestion("addProduct");   //선택 결과

        if ("1".equals(result)) {
            orderList.put(product, orderList.getOrDefault(product, 0) + 1);
            System.out.println("🛒 " + product.getName() + "가 장바구니에 추가되었습니다. 🛒");
        } else {
            System.out.println("❗ 취소되었습니다. ❗");
        }
    }

    /**
     * 주문 취소
     */
    public void cancelOrder() throws Exception {
        String result = printQuestion("cancelOrder");

        if ("1".equals(result)) {
            orderList.clear();
            setTotalPrice(0);
            System.out.println("진행중이던 주문이 취소되었습니다.");
        }
    }

    /**
     * 주문내역, 총 가격 출력
     *
     * @return
     */
    public String orderCheck() throws Exception {
        if (orderList.isEmpty()) {
            System.out.println("장바구니에 담긴 상품이 없습니다.");
            return "";
        }
        return printQuestion("orderCheck");
    }

    /**
     * 주문 품목 전체 리스트에 담기
     *
     * @param product
     */
    public void insertAllOrderList(Product product) {
        //전체 주문 목록에 판매한 품목과 품목별 금액 담기
        allOrderList.put(product.getName(), allOrderList.getOrDefault(product.getName(), product.getPrice()) + product.getPrice());
    }

    /**
     * 주문 건 별 총 금액 구하기
     *
     * @param product
     */
    public void setTotalPrice(Product product) {
        this.totalPrice += product.getPrice() * orderList.get(product);
    }


    /**
     * 선택지에 따른 질문 출력
     *
     * @param type
     * @return
     */
    public String printQuestion(String type) throws Exception {

        Scanner sc = new Scanner(System.in);
        String result = "";

        switch (type) {
            case "addProduct":  //장바구니 담기
                System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인      2. 취소");
                result = sc.nextLine();
                break;
            case "cancelOrder": //주문 취소
                System.out.println("진행중이던 주문을 취소하시겠습니까?");
                System.out.println("1. 확인      2. 취소");
                result = sc.nextLine();
                break;
            case "orderCheck":  //주문 확인
                System.out.println("아래와 같이 주문하시겠습니까?");
                System.out.println("[ Orders ]");

                //장바구니에 동일한 품목이 2개이상일때만 개수 표현을 하기 위해서 최대값을 구함
                int maxCount = orderList.values().stream().max(Integer::compareTo).orElse(1);

                //주문 내역 출력
                for (Product product : getOrderList().keySet()) {
                    //장바구니에 각 품목이 한개씩만 담겼다면
                    if (maxCount == 1) {
                        product.print();    //개수 출력하지 않음
                    } else {
                        product.print(orderList.get(product));  //1개가 아니라면 개수 출력
                    }

                    setTotalPrice(product);         //주문 건 별 총 금액 구하기
                    insertAllOrderList(product);    //주문 품목 전체 리스트에 담기
                }

                setAllTotalPrice(); //전체 주문 금액 구하기

                System.out.println("[ Total Price ]");
                System.out.println(totalPrice);

                System.out.println("1. 주문     2. 메뉴판");
                result = sc.nextLine();
        }
        Parser.parseNum(result, YES_OR_NO);
        return result;
    }
}
