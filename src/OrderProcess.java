

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class OrderProcess {

    private static final String YES_OR_NO = "^[1-2]*$";
    Cart cart = new Cart();

    //전체 주문 리스트
    private Map<String, Integer> allOrderMap = new HashMap<>();

    //대기 주문 리스트
    private List<Order> waitingList = new ArrayList<>();

    //완료 주문 리스트
    private List<Order> doneList = new ArrayList<>();

    //전체 판매 금액
    private int allTotalPrice;

    private int waitingNumber = 1;

    public Map<String, Integer> getAllOrderMap() {
        return allOrderMap;
    }

    public int getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(int totalPrice) {
        this.allTotalPrice += totalPrice;
    }

    public List<Order> getWaitingList() {
        return waitingList;
    }

    public List<Order> getDoneList() {
        return doneList;
    }

    /**
     * 장바구니 담기
     *
     * @param product : 사용자가 선택한 상품
     */
    public void addProduct(Product product) throws Exception {
        product.print();    //선택한 메뉴 출력
        String addProduct = printQuestion("addProduct");    // 위 메뉴를 장바구니에 추가하시겠습니까?

        if ("1".equals(addProduct)) {
            cart.getCartMap().put(product, cart.getCartMap().getOrDefault(product, 0) + 1);
            cart.setTotalPrice(product.getPrice());
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
            resetCart();
            System.out.println("진행중이던 주문이 취소되었습니다.");
        }
    }

    /**
     * 주문 확인
     *
     * @return : (1) 확인 / (2) 취소
     */
    public String orderCheck() throws Exception {
        if (cart.getCartMap().isEmpty()) {
            System.out.println("장바구니에 담긴 상품이 없습니다.");
            return "";
        }

        return printQuestion("printOrder"); // 아래와 같이 주문하시겠습니까?
    }

    /**
     * 요청사항 검증
     *
     * @return
     */
    public String verifiedRequest() {
        String request;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("요청 사항을 20자 이하로 입력해 주세요.");
            request = sc.nextLine();
        } while (request.length() > 20);

        return request;
    }

    /**
     * 주문 성공
     */
    public void orderSuccess() throws Exception {

        String writeRequest = printQuestion("writeRequest");
        String request = "";
        //요청 사항 입력 여부 확인
        if ("1".equals(writeRequest)) {
            request = verifiedRequest();
        }

        for (Product product : cart.getCartMap().keySet()) {
            // 전체 주문 목록에 사용자가 현재 주문한 목록 담기
            allOrderMap.put(product.getName(), allOrderMap.getOrDefault(product.getName(), 0) + product.getPrice() * cart.getCartMap().get(product));
        }

        // 전체 총 금액에 사용자가 현재 주문한 총 금액 담기
        setAllTotalPrice(cart.getTotalPrice());

        Order order = new Order(new HashMap<>(cart.getCartMap()), cart.getTotalPrice(), request, waitingNumber, makeISO8601Date());
        waitingList.add(order);

        System.out.println("주문이 완료되었습니다.");
        System.out.println("대기번호는 [ " + waitingNumber++ + " ]번 입니다.");
        System.out.println("(3초 후 메뉴판으로 돌아갑니다.)");

        //다음 주문을 위한 장바구니 초기화
        resetCart();
    }

    /**
     * 장바구니 초기화
     */
    public void resetCart() {
        cart.clearCartMap();
        cart.clearTotalPrice();
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
            case "writeRequest": //요청사항 작성
                System.out.println("요청 사항을 입력하시겠습니까?");
                System.out.println("1. 확인      2. 취소");
                result = sc.nextLine();
                break;
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
                int maxCount = cart.getCartMap().values().stream().max(Integer::compareTo).orElse(1);

                for (Product product : cart.getCartMap().keySet()) {
                    //장바구니에 각 품목이 한개씩만 담겼다면
                    if (maxCount == 1) {
                        product.print();    //개수 출력하지 않음
                    } else {
                        product.print(cart.getCartMap().get(product));  //1개가 아니라면 개수 출력
                    }
                }
                System.out.println("[ Total Price ]");
                System.out.println(cart.getTotalPrice());

                System.out.println("1. 주문     2. 메뉴판");
                result = sc.nextLine();
        }
        Parser.parseNum(result, YES_OR_NO);
        return result;
    }


    /**
     * ISO형식으로 일자, 시간 형식 변경
     *
     * @return
     */
    public String makeISO8601Date() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return currentDateTime.format(formatter);
    }

    /**
     * 주문 완료 처리
     *
     * @param result
     */
    public void completeProcess(String result) {
        int index = Integer.parseInt(result) - 1;
        waitingList.get(index).setDoneTime(makeISO8601Date());
        doneList.add(waitingList.get(index));
        waitingList.remove(index);
    }








        public void toppingOrder (Product selectProduct, ProductEdit productEdit) throws Exception {

            selectProduct.print();
            System.out.println("토핑을 추가 하시겠습니까?");
            System.out.println("1. 확인      2. 취소");

            Scanner sc = new Scanner(System.in);
            String check = sc.nextLine();
            Parser.parseNum(check, YES_OR_NO);

            Product addToppingProdcut = selectProduct;

            if (Objects.equals(check,"1")) {
                int index = 1;
                System.out.println("[ Topping Menu ]");
                System.out.println("아래 메뉴판을 보시고 토핑을 골라 입력해주세요.");
                for (Product topping : productEdit.getToppingList()) {             //토핑 목록 출력
                    System.out.print(index++ + ". ");
                    topping.print();
                }

                int selectTopping = sc.nextInt();
                System.out.println();

                addToppingProdcut = new Product(productEdit.addToppingProductname(addToppingProdcut, productEdit.getToppingList().get(selectTopping - 1)),
                        selectProduct.getDescription(),
                        productEdit.addToppingProductprice(addToppingProdcut, productEdit.getToppingList().get(selectTopping - 1)));


                productEdit.getToppingList().get(selectTopping - 1).print();
                System.out.println("토핑이 추가되었습니다.");
                System.out.println();

            }

            addProduct(addToppingProdcut);
        }
}
