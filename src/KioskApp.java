import java.util.*;

public class KioskApp {

    private static final String NUMBER_REG = "^[0-6]*$";
    private Map<Integer, List<Product>> allMenuMap = new HashMap<>(); // 전체 메뉴 지도
    private List<Menu> menuList = new ArrayList<>(); // 메뉴 리스트
    private List<Product> tteokbokkiList = new ArrayList<>(); // 떡볶이 리스트
    private List<Product> sideList = new ArrayList<>(); // 사이드 메뉴 리스트
    private List<Product> drinkList = new ArrayList<>(); // 음료 리스트
    private List<Product> mealKitList = new ArrayList<>(); // 밀키트 리스트
    Order order = new Order();

    public KioskApp() {
        insertMenu();
    }

    public void insertMenu() {

        //메인 메뉴
        menuList.add(new Menu("Tteokbokki", "계속 생각나는 매운맛! 엽기떡볶이🥵"));
        menuList.add(new Menu("Side", "엽떡과 같이 먹으면 더 맛있어요🍙"));
        menuList.add(new Menu("Drinks", "매움을 달래주기 위한 음료🧃"));
        menuList.add(new Menu("Meal Kit", "어디서든 엽떡을 즐겨보세요🌳"));

        //떡볶이 메뉴
        tteokbokkiList.add(new Product("엽기떡볶이", "엽떡을 즐길줄 안다면 역시 오리지널!", 14000));
        tteokbokkiList.add(new Product("짜장떡볶이", "아이들이 먹기 좋아요", 16000));
        tteokbokkiList.add(new Product("로제떡볶이", "매운게 싫다면 부드러운 로제가 안성맞춤", 16000));
        tteokbokkiList.add(new Product("마라떡볶이", "전국품절 마라떡볶이! 재입고 되었습니다.", 16000));

        sideList.add(new Product("셀프 주먹김밥", "오물조물 만들어서 먹어요.", 2000));
        sideList.add(new Product("계란야채죽", "매운맛 소화기", 5000));
        sideList.add(new Product("순대", "떡볶이에 순대는 빠질수 없습니다.", 3000));
        sideList.add(new Product("야채튀김", "튀김도 마찬가지로 빠질수 없습니다.", 1000));

        drinkList.add(new Product("제로콜라", "살찌는게 걱정이라면 제로를 선택하세요.", 2000));
        drinkList.add(new Product("쿨피스", "매운걸 못먹는 분은 쿨피스 필수입니다.", 1000));

        mealKitList.add(new Product("오리지널맛", "엽떡을 즐길줄 안다면 역시 오리지널!", 15000));
        mealKitList.add(new Product("착한맛", "아이들이 먹기 좋아요", 15000));

        allMenuMap.put(1, tteokbokkiList);
        allMenuMap.put(2, sideList);
        allMenuMap.put(3, drinkList);
        allMenuMap.put(4, mealKitList);

    }

    public void kiosk() throws Exception {

        String menuNum = printMenu(); //메인메뉴 출력
        Parser.parseNum(menuNum, NUMBER_REG);

        switch (menuNum) {
            case "6": // 주문 취소
                order.cancelOrder();
                break;
            case "5": // 주문하기
                if ("1".equals(order.orderCheck())) {
                    int waitingNum = order.getWaitingNum();

                    System.out.println("주문이 완료되었습니다.");
                    System.out.println("대기번호는 [" + waitingNum + " ]번 입니다.");
                    System.out.println("(3초 후 메뉴판으로 돌아갑니다.)");

                    //다음 주문을 위한 주문번호 세팅과 장바구니 초기화
                    order.setWaitingNum(waitingNum + 1);
                    order.getOrderList().clear();
                    order.setTotalPrice(0);

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
                Product selectProduct = allMenuMap.get(Integer.parseInt(menuNum)).get(Integer.parseInt(productNum) - 1); //선택한 상품에 대한 정보 가져오기

                order.addProduct(selectProduct); // 카트에 담기
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
        for (Menu m : menuList) {
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
        //메뉴 리스트 출력
        for (Product p : allMenuMap.get(Integer.parseInt(selectNum))) {
            System.out.print(index++ + ". ");
            p.print();
        }
        System.out.println();

        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public void printAdmin() {
        System.out.println("[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 금액은 [ ₩ " + order.getAllTotalPrice() + " ] 입니다.");

        System.out.println("[ 총 판매상품 목록 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");

        for (String name : order.getAllOrderList().keySet()) {
            System.out.println("- " + name + " | ₩ " + order.getAllOrderList().get(name));
        }
    }
}