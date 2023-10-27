
import java.util.*;

public class KioskApp {

    private static final String NUMBER_REG = "^[0-6]*$";
    OrderProcess orderProcess = new OrderProcess();
    ProductEdit productEdit = new ProductEdit();


    public KioskApp() {
        insertMenu();
    }

    public void insertMenu() {
        productEdit.initProduct();
    }

    public void kiosk() throws Exception {

        String menuNum = printMenu(); //메인메뉴 출력
        Parser.parseNum(menuNum, NUMBER_REG);

        switch (menuNum) {
            case "6": // 주문 취소
                orderProcess.cancelOrder();
                break;
            case "5": // 주문하기
                String result = orderProcess.orderCheck();  //주문을 확인 후 주문(1)하거나 취소(2)한 결과
                if ("1".equals(result)) {
                    orderProcess.orderSuccess();    //

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    System.out.println("주문하지 않고 메뉴판으로 돌아갑니다.");
                }
                break;
            case "0": // 관리자 모드
                printAdmin();
                break;
            default: // 메뉴 선택
                String productNum = printMenu(menuNum); // 입력받은 숫자에 따른 상세 메뉴 출력
                Parser.parseNum(productNum, NUMBER_REG);
                Product selectProduct = productEdit.getProductList().get(menuNum + "#" + productNum); //선택한 상품에 대한 정보 가져오기

                if (menuNum.equals("1")) {
                   orderProcess.toppingOrder(selectProduct, productEdit);
                }
                else {
                    orderProcess.addProduct(selectProduct); // 카트에 담기
                }
        }
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
        for (Menu m : productEdit.getmenuList().values()) {
            System.out.print(index++ + ". ");
            m.print();
        }

        System.out.println("[ 💛 ORDER MENU 💛 ]");
        System.out.print(index++ + ". ");
        System.out.printf("%-15s | %s%n", "Order", "장바구니를 확인 후 주문합니다.⭕");
        System.out.print(index + ". ");
        System.out.printf("%-15s | %s%n", "Cancel", "진행중인 주문을 취소합니다.❌");
        System.out.println();

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * 상세 메뉴 출력
     */
    public String printMenu(String selectNum) {
        int index = 1;
        String menu = productEdit.getmenuList().get(selectNum).getName();    // 선택한 번호를 키값으로 메뉴리스트에서 가져옴

        System.out.println("엽기떡볶이에 오신걸 환영합니다.");
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");
        System.out.println();

        System.out.println("[ 🔥 " + menu + " MENU 🔥 ]");
//        메뉴 리스트 출력
//         해당 메뉴ID가 일치하는 상품만 출력
        List<String> keyset = new ArrayList<>(productEdit.getProductList().keySet());
        Collections.sort(keyset);                                                              //keyset 오름차순 정렬
        for (String key :  keyset) {
            if (Objects.equals(key.substring(0, key.indexOf("#")), selectNum)) {

                System.out.print(index++ + ". ");
                productEdit.getProductList().get(key).print();
            }
        }
        System.out.println();

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public void printAdmin() {
        System.out.println("[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 금액은 [ ₩ " + orderProcess.getAllTotalPrice() + " ] 입니다.");

        System.out.println("[ 총 판매상품 목록 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");

        for (String name : orderProcess.getAllOrderMap().keySet()) {
            System.out.println("- " + name + " | ₩ " + orderProcess.getAllOrderMap().get(name));
        }
    }


    public void adminWaiting() {
        //if(order.getStatus()) 이 false인 경우
        System.out.println("[ 대기주문 목록 ]");
        for (String name : orderProcess.getAllOrderMap().keySet()) {
            System.out.println("- " + name + " | ₩ " + orderProcess.getAllOrderMap().get(name));
        }

    }

    public void adminFinish() {
        //if(order.getStatus()) 이 true인 경우
        System.out.println("[ 완료주문 목록 ]");

    }

    public void adminCreateItem() {
        System.out.println("[ 상품생성 ]");

    }

    public void adminDeleteItem() {
        System.out.println("[ 상품삭제 ]");

    }

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
