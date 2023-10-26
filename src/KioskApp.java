
import java.util.*;

public class KioskApp {

    private static final String NUMBER_REG = "^[0-6]*$";
    //    private Map<Integer, List<Product>> allMenuMap = new HashMap<>(); // 전체 메뉴 지도        //초기데이터 및 추가 삭제 관련데이터 ProductEdit으로 이관
//    private List<Menu> menuList = new ArrayList<>(); // 메뉴 리스트
//    private List<Product> tteokbokkiList = new ArrayList<>(); // 떡볶이 리스트
//    private List<Product> sideList = new ArrayList<>(); // 사이드 메뉴 리스트
//    private List<Product> drinkList = new ArrayList<>(); // 음료 리스트
//    private List<Product> mealKitList = new ArrayList<>(); // 밀키트 리스트
    OrderProcess orderProcess = new OrderProcess();
    ProductEdit productEdit = new ProductEdit();


    public KioskApp() {
        insertMenu();
    }

    public void insertMenu() {
        productEdit.initProduct();
//        //메인 메뉴
//        menuList.add(new Menu("Tteokbokki", "계속 생각나는 매운맛! 엽기떡볶이🥵"));
//        menuList.add(new Menu("Side", "엽떡과 같이 먹으면 더 맛있어요🍙"));
//        menuList.add(new Menu("Drinks", "매움을 달래주기 위한 음료🧃"));
//        menuList.add(new Menu("Meal Kit", "어디서든 엽떡을 즐겨보세요🌳"));
//
//        //떡볶이 메뉴
//        tteokbokkiList.add(new Product("엽기떡볶이", "엽떡을 즐길줄 안다면 역시 오리지널!", 14000));
//        tteokbokkiList.add(new Product("짜장떡볶이", "아이들이 먹기 좋아요", 16000));
//        tteokbokkiList.add(new Product("로제떡볶이", "매운게 싫다면 부드러운 로제가 안성맞춤", 16000));
//        tteokbokkiList.add(new Product("마라떡볶이", "전국품절 마라떡볶이! 재입고 되었습니다.", 16000));
//
//        sideList.add(new Product("셀프 주먹김밥", "오물조물 만들어서 먹어요.", 2000));
//        sideList.add(new Product("계란야채죽", "매운맛 소화기", 5000));
//        sideList.add(new Product("순대", "떡볶이에 순대는 빠질수 없습니다.", 3000));
//        sideList.add(new Product("야채튀김", "튀김도 마찬가지로 빠질수 없습니다.", 1000));
//
//        drinkList.add(new Product("제로콜라", "살찌는게 걱정이라면 제로를 선택하세요.", 2000));
//        drinkList.add(new Product("쿨피스", "매운걸 못먹는 분은 쿨피스 필수입니다.", 1000));
//
//        mealKitList.add(new Product("오리지널맛", "엽떡을 즐길줄 안다면 역시 오리지널!", 15000));
//        mealKitList.add(new Product("착한맛", "아이들이 먹기 좋아요", 15000));
//
//        allMenuMap.put(1, tteokbokkiList);
//        allMenuMap.put(2, sideList);
//        allMenuMap.put(3, drinkList);
//        allMenuMap.put(4, mealKitList);
//        productEdit.addProduct("떡볶이", "김치떡볶이", "김치로만든 떡볶이", 10000);
//        productEdit.addProduct("햄버거", "불고기햄버거", "햄버거임", 8000);

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
                Product selectProduct = productEdit.getProductList().get(menuNum + "#" +productNum); //선택한 상품에 대한 정보 가져오기

                orderProcess.addProduct(selectProduct); // 카트에 담기
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
        String menu = "TTEOKBOKKI";
        int index = 1;
        if ("2".equals(selectNum)) {
            menu = "SIDE";
        } else if ("3".equals(selectNum)) {
            menu = "DRINK";
        } else if ("4".equals(selectNum)) {
            menu = "MEAL KIT";
        }

        System.out.println("엽기떡볶이에 오신걸 환영합니다.");
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");
        System.out.println();

        System.out.println("[ 🔥 " + menu + " MENU 🔥 ]");
//        메뉴 리스트 출력
//         해당 메뉴ID가 일치하는 상품만 출력
        for (String key : productEdit.getProductList().keySet()) {
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
