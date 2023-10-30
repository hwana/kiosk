import java.util.*;

public class KioskApp {

    private static final String NUMBER_REG = "^[0-7]*$";
    OrderProcess orderProcess = new OrderProcess();
    ProductEdit productEdit = new ProductEdit();

    public KioskApp() {
        productEdit.initProduct();  // 메뉴 초기화
    }

    public void kiosk() throws Exception {

        String menuNum = printMenu(); //메인메뉴 출력
        Parser.parseNum(menuNum, NUMBER_REG);

        switch (menuNum) {
            case "7": // 주문 확인
                System.out.println("💸 주문 내역 확인 💸");
                System.out.println();
                System.out.println("[ 주문 완료 상품 목록 ]");
                doneCheck(orderProcess.getDoneList());
                System.out.println("[ 대기 중인 주문 목록 ]");
                orderCheck(orderProcess.getWaitingList());
                System.out.println();
                break;
            case "6": // 주문 취소
                orderProcess.cancelOrder();
                break;
            case "5": // 주문하기
                String result = orderProcess.orderCheck();  //주문을 확인 후 주문(1)하거나 취소(2)한 결과
                if ("1".equals(result)) {
                    orderProcess.orderSuccess();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    System.out.println("주문하지 않고 메뉴판으로 돌아갑니다.");
                }
                break;
            case "0":
                String adminNum = printAdmin();// 관리자 모드
                switch (adminNum) {
                    case "1":
                        printOrderList(orderProcess.getWaitingList());
                        break;
                    case "2":
                        printOrderList(orderProcess.getDoneList());
                        break;
                    case "3":
                        printAddProduct();
                        break;
                    case "4":
                        break;
                    case "5":
                        printTotal();
                        break;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                break;
            default: // 메뉴 선택
                String productNum = printMenu(menuNum); // 입력받은 숫자에 따른 상세 메뉴 출력
                Parser.parseNum(productNum, NUMBER_REG);
                Product selectProduct = productEdit.getProductList().get(menuNum + "#" + productNum); //선택한 상품에 대한 정보 가져오기

                if (menuNum.equals("1")) {
                    orderProcess.toppingOrder(selectProduct, productEdit);
                } else {
                    orderProcess.addProduct(selectProduct); // 카트에 담기
                }
        }
    }

    /**
     * 주문 완료 처리 출력
     *
     * @param waitingList
     */
    private void printOrderDoneProcess(List<Order> waitingList) {
        System.out.println("완료 처리 할 대기 주문을 선택하세요.");
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        orderProcess.completeProcess(result);

        System.out.println("완료 처리 되었습니다. 3초 뒤 메뉴로 이동합니다.");
    }

    /**
     * 메인 메뉴 출력
     */
    public String printMenu() {

        System.out.println("🧡 엽기떡볶이에 오신걸 환영합니다. 🧡");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println();

        System.out.println("[ 🔥 YUPDDUCK MENU 🔥 ]");
        int index = 1;
        for (Menu m : productEdit.getMenuList().values()) {
            System.out.print(index++ + ". ");
            m.print();
        }

        System.out.println("[ 💛 ORDER MENU 💛 ]");
        System.out.print(index++ + ". ");
        System.out.printf("%-15s | %s%n", "Order", "장바구니를 확인 후 주문합니다.⭕");
        System.out.print(index++ + ". ");
        System.out.printf("%-15s | %s%n", "Cancel", "진행중인 주문을 취소합니다.❌");
        System.out.print(index + ". ");
        System.out.printf("%-15s | %s%n", "Check", "주문 내역을 확인합니다.✔");
        System.out.println();

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * 상세 메뉴 출력
     */
    public String printMenu(String selectNum) {
        int index = 1;
        String menu = productEdit.getMenuList().get(selectNum).getName();    // 선택한 번호를 키값으로 메뉴리스트에서 가져옴

        System.out.println("엽기떡볶이에 오신걸 환영합니다.");
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");
        System.out.println();

        System.out.println("[ 🔥 " + menu + " MENU 🔥 ]");
//        메뉴 리스트 출력
//         해당 메뉴ID가 일치하는 상품만 출력
        List<String> keyset = new ArrayList<>(productEdit.getProductList().keySet());
        Collections.sort(keyset);                                                              //keyset 오름차순 정렬
        for (String key : keyset) {
            if (Objects.equals(key.substring(0, key.indexOf("#")), selectNum)) {

                System.out.print(index++ + ". ");
                productEdit.getProductList().get(key).print();
            }
        }
        System.out.println();

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * 관리자 메인 메뉴 출력
     *
     * @return
     */
    public String printAdmin() {
        System.out.println("메뉴를 선택하세요.");
        System.out.println("1. 대기 주문 목록");
        System.out.println("2. 완료 주문 목록");
        System.out.println("3. 상품 생성");
        System.out.println("4. 상품 삭제");
        System.out.println("5. 총 주문 조회");

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * 총 주문 목록, 주문 금액 출력
     */
    public void printTotal() {
        System.out.println("[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 금액은 [ ₩ " + orderProcess.getAllTotalPrice() + " ] 입니다.");

        System.out.println("[ 총 판매상품 목록 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");

        for (String name : orderProcess.getAllOrderMap().keySet()) {
            System.out.println("- " + name + " | ₩ " + orderProcess.getAllOrderMap().get(name));
        }
    }

    /**
     * 대기 목록 / 완료 목록 출력
     *
     * @param orderList
     */
    public void printOrderList(List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println("[       " + (i + 1) + " 번째 주문      ]");
            System.out.println("1. 대기 번호 : " + orderList.get(i).getWaitingNumber());
            System.out.println("2. 주문 상품 목록");

            Map<Product, Integer> orderMap = orderList.get(i).getOrderMap();

            for (Product product : orderMap.keySet()) {
                System.out.println("    - 상품명 : " + product.getName());
                System.out.println("    - 가격 : " + product.getPrice());
                System.out.println("    - 개수 : " + orderMap.get(product) + "개");
            }

            System.out.println("3. 주문 총 가격 : " + orderList.get(i).getTotalPrice());
            System.out.println("4. 요청 사항 : " + orderList.get(i).getRequest());
            System.out.println("5. 주문 일시 : " + orderList.get(i).getOrderTime());

            String doneTime = orderList.get(i).getDoneTime();

            if (doneTime != null) {
                System.out.println("6. 완료 주문 일시 : " + doneTime);
            } else {
                printOrderDoneProcess(orderList);
            }
        }
    }

    public void orderCheck(List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            Map<Product, Integer> orderMap = orderList.get(i).getOrderMap();
            for (Product product : orderMap.keySet()) {
                System.out.printf("%-15s | ₩ %s | %s%n", product.getName(), product.getPrice(), orderMap.get(product) + "개");
            }
        }
    }

    public void doneCheck(List<Order> doneList) {
        // doneList.get(doneList.size()-1); // 가장 최신 데이터
        if (doneList.size() < 4) {
            for (int i = 0; i < doneList.size(); i++) {
                Map<Product, Integer> orderMap = doneList.get(i).getOrderMap();
                for (Product product : orderMap.keySet()) {
                    System.out.printf("%-15s | ₩ %s | %s%n", product.getName(), product.getPrice(), orderMap.get(product) + "개");
                }
            }
        } else {
            for (int i = doneList.size() - 3; i < doneList.size(); i++) {
                Map<Product, Integer> orderMap = doneList.get(i).getOrderMap();
                for (Product product : orderMap.keySet()) {
                    System.out.printf("%-15s | ₩ %s | %s%n", product.getName(), product.getPrice(), orderMap.get(product) + "개");
                }
            }
        }

    }

    /**
     * 상품 생성 출력
     */
    public void printAddProduct() {
        // 상품생성 window
        System.out.println("[상품 생성]");
        System.out.println("추가하실려는 상품의 메뉴, 이름, 설명, 가격을 입력해주세요");
        System.out.println("예시 :     피자, 하와이안피자, 파인애플이 들어간 피자, 7000  ");
        System.out.println();

        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(", ");
        String addmenu = sc.nextLine();
        String name = sc.nextLine();
        String description = sc.nextLine();
        int price = sc.nextInt();

        productEdit.addProduct(addmenu, name, description, price);

    }
}
