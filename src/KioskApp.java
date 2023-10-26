import java.util.*;

public class KioskApp {

    private static final String NUMBER_REG = "^[0-7]*$"; // private 같은 클래스 내에서만 접근 가능, static final: 상수
    private Map<Integer, List<Product>> allMenuMap = new HashMap<>(); // Map< 키값, 제품리스트 > 전체 메뉴리스트 : 43줄 키,값
    private List<Menu> menuList = new ArrayList<>(); // 메뉴 리스트 선언
    private List<Product> tteokbokkiList = new ArrayList<>(); // 떡볶이 리스트 선언
    private List<Product> sideList = new ArrayList<>(); // 사이드 메뉴 리스트 선언
    private List<Product> drinkList = new ArrayList<>(); // 음료 리스트 선언
    private List<Product> mealKitList = new ArrayList<>(); // 밀키트 리스트 선언
    OrderProcess orderProcess = new OrderProcess(); // orderProcess 객체 생성 : OrderProcess 내 메서드 뽑아옴
    Order order = new Order();
    public KioskApp() {
        insertMenu();
    } // 생성자 { 메뉴넣기 메서드 }

    public void insertMenu() { // 메뉴 넣기

        // 메인 메뉴 : 메뉴리스트.추가(new Menu( name, description ))
        // Menu.java의 this.name, this.description
        menuList.add(new Menu("Tteokbokki", "계속 생각나는 매운맛! 엽기떡볶이🥵"));
        menuList.add(new Menu("Side", "엽떡과 같이 먹으면 더 맛있어요🍙"));
        menuList.add(new Menu("Drinks", "매움을 달래주기 위한 음료🧃"));
        menuList.add(new Menu("Meal Kit", "어디서든 엽떡을 즐겨보세요🌳"));

        // 떡볶이 메뉴 : 떡볶이리스트.추가(new Product( name, description, price ))
        // Menu.java를 상속받은 Product.java의 super(name, description), this.price
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

        allMenuMap.put(1, tteokbokkiList); //키, 값 : (1, 떡볶이 리스트)
        allMenuMap.put(2, sideList);
        allMenuMap.put(3, drinkList);
        allMenuMap.put(4, mealKitList);

    }

    public void kiosk() throws Exception { // kiosk 메서드

        String menuNum = printMenu(); // 숫자를 입력하면 메인 메뉴 출력
        Parser.parseNum(menuNum, NUMBER_REG); // ??

        switch (menuNum) {
            case "7": // 주문 확인
                System.out.println("💸 주문 내역 확인 💸");
                System.out.println();
                orderProcess.finishCheck();  // 완료 된 주문 전체 출력
                orderProcess.waitingCheck(); // 대기 중인 주문 전체 출력
                System.out.println();

                break;

            case "6": // 주문 취소
                orderProcess.cancelOrder(); // OrderProcess의 cancelOrder 메서드
                break;

            case "5": // 주문하기
                String result = orderProcess.orderCheck();  //주문을 확인 후 주문(1)하거나 취소(2)한 결과
                if ("1".equals(result)) { // (1) 주문
                    orderProcess.orderSuccess();    // orderSuccess 메서드 실행

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

            default: // 메뉴 선택 : 무조건 출력
                String productNum = printMenu(menuNum); // 입력받은 숫자(메인 메뉴)에 따른 상세 메뉴 출력
                Parser.parseNum(productNum, NUMBER_REG);
                Product selectProduct = allMenuMap.get(Integer.parseInt(menuNum)).get(Integer.parseInt(productNum) - 1); //선택한 상품에 대한 정보 가져오기

                orderProcess.addProduct(selectProduct); // 장바구니 추가
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
        for (Menu m : menuList) { // menuList(메인 메뉴) 전체 출력
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
        for (Product p : allMenuMap.get(Integer.parseInt(selectNum))) { // 상세 메뉴 출력
            System.out.print(index++ + ". ");
            p.print();
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
   /* public void orderFinish() { // 완료 주문 (최근 3건) 출력
        System.out.println("[ 최근 주문 완료 목록 ]");
        for (String name : orderProcess.getAllOrderMap().keySet()) {
            System.out.println(name + "           | ₩ " + orderProcess.getAllOrderMap().get(name));
        }
    }*/

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
}
