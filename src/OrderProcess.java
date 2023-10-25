import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderProcess {

    Order order = new Order();
    private static final String YES_OR_NO = "^[1-2]*$";

    //전체 주문 리스트
    private Map<String, Integer> allOrderMap = new HashMap<>();

    //전체 판매 금액
    private int allTotalPrice;

    public Map<String, Integer> getAllOrderMap() {
        return allOrderMap;
    }

    public void setAllOrderMap(Map<String, Integer> allOrderMap) {
        this.allOrderMap = allOrderMap;
    }

    public int getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(int totalPrice) {
        this.allTotalPrice += totalPrice;
    }

    /**
     * 장바구니 담기
     *
     * @param product : 사용자가 선택한 상품
     */
    public void addProduct(Product product) throws Exception {
        product.print();    //선택한 메뉴 출력
        String result = printQuestion("addProduct");    // 위 메뉴를 장바구니에 추가하시겠습니까?

        if ("1".equals(result)) {
            //주문 목록에 상품 담기
            order.getOrderMap().put(product, order.getOrderMap().getOrDefault(product, 0) + 1);
            //주문 총 금액 더하기
            order.setTotalPrice(product.getPrice());
            System.out.println("🛒 " + product.getName() + "가 장바구니에 추가되었습니다. 🛒");
        } else {
            System.out.println("❗ 취소되었습니다. ❗");
        }
    }

    /**
     * 주문 취소
     */
    public void cancelOrder() throws Exception {
        String result = printQuestion("cancelOrder");   // 진행중이던 주문을 취소하시겠습니까?

        if ("1".equals(result)) {
            resetOrder();
            System.out.println("진행중이던 주문이 취소되었습니다.");
        }
    }

    /**
     * 주문 확인
     *
     * @return : (1) 확인 / (2) 취소
     */
    public String orderCheck() throws Exception {
        if (order.getOrderMap().isEmpty()) {
            System.out.println("장바구니에 담긴 상품이 없습니다.");
            return "";
        }

        return printQuestion("printOrder"); // 아래와 같이 주문하시겠습니까?
    }

    /**
     * 주문 성공
     */
    public void orderSuccess() {

        for (Product product : order.getOrderMap().keySet()) {
            // 전체 주문 목록에 사용자가 현재 주문한 목록 담기
            allOrderMap.put(product.getName(), allOrderMap.getOrDefault(product.getName(), 0) + product.getPrice() * order.getOrderMap().get(product));
        }

        // 전체 총 금액에 사용자가 현재 주문한 총 금액 담기
        setAllTotalPrice(order.getTotalPrice());
        //대기번호 생성
        int waitingNum = order.getWaitingNumber();

        System.out.println("주문이 완료되었습니다.");
        System.out.println("대기번호는 [" + waitingNum + " ]번 입니다.");
        System.out.println("(3초 후 메뉴판으로 돌아갑니다.)");

        //다음 주문을 위한 주문번호 세팅과 장바구니 초기화
        resetOrder();
    }

    /**
     * 주문 초기화
     */
    public void resetOrder() {
        order.getOrderMap().clear();    //  장바구니 비우기
        order.clearTotalPrice();        //  해당 주문 총 금액 초기화
    }

    /**
     * 선택지에 따른 질문 출력
     *
     * @param type : 주문 상황
     * @return : 확인 or 취소
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
            case "printOrder":
                System.out.println("아래와 같이 주문하시겠습니까?");
                System.out.println("[ Orders ]");

                //장바구니에 동일한 품목이 2개이상일때만 개수 표현을 하기 위해서 최대값을 구함
                int maxCount = order.getOrderMap().values().stream().max(Integer::compareTo).orElse(1);

                for (Product product : order.getOrderMap().keySet()) {
                    //장바구니에 각 품목이 한개씩만 담겼다면
                    if (maxCount == 1) {
                        product.print();    //개수 출력하지 않음
                    } else {
                        product.print(order.getOrderMap().get(product));  //1개가 아니라면 개수 출력
                    }
                }

                System.out.println("[ Total Price ]");
                System.out.println(order.getTotalPrice());

                System.out.println("1. 주문     2. 메뉴판");
                result = sc.nextLine();
        }
        Parser.parseNum(result, YES_OR_NO);
        return result;
    }
}