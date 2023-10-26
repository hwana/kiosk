
import java.util.*;


public class ProductEdit {
    private String name;
    private String description;
    private int price;
    private String addmenu;
    private String id;

    private Menu setValue = null;
    private String setKey;

    private List<Integer> keys = new ArrayList<>();
//    private Map<Integer, List<Product>> allMenuMap = new HashMap<>(); // 전체 메뉴 지도


    private Map<String, Menu> menuList = new HashMap<>();

    private Map<String, Product> productList = new HashMap<>();

    //    private List<Menu> menuList = new ArrayList<>(); // 메뉴 리스트
//    private List<Product> tteokbokkiList = new ArrayList<>(); // 떡볶이 리스트
//    private List<Product> sideList = new ArrayList<>(); // 사이드 메뉴 리스트
//    private List<Product> drinkList = new ArrayList<>(); // 음료 리스트
//    private List<Product> mealKitList = new ArrayList<>(); // 밀키트 리스트
    // 메뉴id#상품id
    public void initProduct() {
        ///  ID범례: 메뉴는 문자열 1,2,3,.... 순으로 ID부여,  상품은 메뉴ID#1,2,3.....순으로 부여
        menuList.put("1", new Menu("떡볶이", "계속 생각나는 매운맛! 엽기떡볶이🥵"));
        menuList.put("2", new Menu("사이드", "엽떡과 같이 먹으면 더 맛있어요🍙"));                                 //이름을 키값으로
        menuList.put("3", new Menu("드링크", "매움을 달래주기 위한 음료🧃"));
        menuList.put("4", new Menu("밀키트", "어디서든 엽떡을 즐겨보세요🌳"));

        productList.put("1#1", new Product("엽기떡볶이", "엽떡을 즐길줄 안다면 역시 오리지널!", 14000));
        productList.put("1#2", new Product("짜장떡볶이", "아이들이 먹기 좋아요", 16000));
        productList.put("1#3", new Product("로제떡볶이", "매운게 싫다면 부드러운 로제가 안성맞춤", 16000));
        productList.put("1#4", new Product("마라떡볶이", "전국품절 마라떡볶이! 재입고 되었습니다.", 16000));

        productList.put("2#1", new Product("셀프 주먹김밥", "오물조물 만들어서 먹어요.", 2000));
        productList.put("2#2", new Product("계란야채죽", "매운맛 소화기", 5000));
        productList.put("2#3", new Product("순대", "떡볶이에 순대는 빠질수 없습니다.", 3000));
        productList.put("2#4", new Product("야채튀김", "튀김도 마찬가지로 빠질수 없습니다.", 1000));

        productList.put("3#1", new Product("제로콜라", "살찌는게 걱정이라면 제로를 선택하세요.", 2000));
        productList.put("3#2", new Product("쿨피스", "매운걸 못먹는 분은 쿨피스 필수입니다.", 1000));

        productList.put("4#1", new Product("오리지널맛", "엽떡을 즐길줄 안다면 역시 오리지널!", 15000));
        productList.put("4#2", new Product("착한맛", "아이들이 먹기 좋아요", 15000));

//        menuList.add(new Menu("00010000", "떡볶이", "계속 생각나는 매운맛! 엽기떡볶이🥵"));
//        menuList.add(new Menu("00020000", "사이드", "엽떡과 같이 먹으면 더 맛있어요🍙"));
//        menuList.add(new Menu("00030000", "드링크", "매움을 달래주기 위한 음료🧃"));
//        menuList.add(new Menu("00040000", "밀키트", "어디서든 엽떡을 즐겨보세요🌳"));
//        //떡볶이 메뉴
//
//        tteokbokkiList.add(new Product("00010001", "엽기떡볶이", "엽떡을 즐길줄 안다면 역시 오리지널!", 14000));
//        tteokbokkiList.add(new Product("00010002", "짜장떡볶이", "아이들이 먹기 좋아요", 16000));
//        tteokbokkiList.add(new Product("00010003", "로제떡볶이", "매운게 싫다면 부드러운 로제가 안성맞춤", 16000));
//        tteokbokkiList.add(new Product("00010004", "마라떡볶이", "전국품절 마라떡볶이! 재입고 되었습니다.", 16000));
//
//        sideList.add(new Product("00020001", "셀프 주먹김밥", "오물조물 만들어서 먹어요.", 2000));
//        sideList.add(new Product("00020002", "계란야채죽", "매운맛 소화기", 5000));
//        sideList.add(new Product("00020003", "순대", "떡볶이에 순대는 빠질수 없습니다.", 3000));
//        sideList.add(new Product("00020004", "야채튀김", "튀김도 마찬가지로 빠질수 없습니다.", 1000));
//
//        drinkList.add(new Product("00030001", "제로콜라", "살찌는게 걱정이라면 제로를 선택하세요.", 2000));
//        drinkList.add(new Product("00030001", "쿨피스", "매운걸 못먹는 분은 쿨피스 필수입니다.", 1000));
//
//        mealKitList.add(new Product("00040001", "오리지널맛", "엽떡을 즐길줄 안다면 역시 오리지널!", 15000));
//        mealKitList.add(new Product("00040002", "착한맛", "아이들이 먹기 좋아요", 15000));

//            allMenuMap.put(1, tteokbokkiList);
//            allMenuMap.put(2, sideList);
//            allMenuMap.put(3, drinkList);
//            allMenuMap.put(4, mealKitList);

    }



    public Map<String, Product> getProductList() {
        return productList;
    }

    public Map<String, Menu> getmenuList() {
        return menuList;
    }



    public void addProduct(String addmenu, String name, String description, int price) {


        for (Menu value : menuList.values()) {
            if (Objects.equals(addmenu, value.getName())) {     ///추가 메뉴의 이름이 기존 메뉴목록의 메뉴와 일치된 값 저장
                setValue = value;
            }
        }
        if (setValue!=null) {
            for (String key : menuList.keySet()) {
                if (Objects.equals(menuList.get(key), setValue)) {   /// 메뉴의 value로 key값(ID) 조회 (1,2,3,4....)
                    setKey = key;
                }
            }

            for (String K : productList.keySet()) {                              /// 메뉴에 포함되는 상품들의 ID를 찾아 그 최대값을 구함
                String menuKey = K.substring(0, K.indexOf("#"));
                String productKey = K.substring(K.indexOf("#") + 1);
                if (Objects.equals(menuKey, setKey)) {
                    keys.add(Integer.parseInt(productKey));
                }
            }

            Integer maxKeys = Collections.max(keys);  // 정수화된 상품ID중 최대값 구하기
            productList.put(setKey + "#" + (maxKeys + 1), new Product(name, description, price));

        } else {   //새로운 메뉴 추가시 메뉴리스트와 상품생성
            menuList.put(Integer.toString(menuList.size() + 1), new Menu(addmenu, ""));        //새로운 상품 추가시 메뉴설명을 추가하지않았기때문에  공란처리함
            productList.put((menuList.size() + 1) + "#" + "1", new Product(name, description, price));
        }



    }

}
