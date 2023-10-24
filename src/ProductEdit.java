import com.sun.org.apache.xpath.internal.operations.Equals;

import java.util.*;

public class ProductEdit {
    private String name;
    private String description;
    private int price;
    private String addmenu;

    private Map<Integer, List<Product>> allMenuMap = new HashMap<>(); // 전체 메뉴 지도
    private List<Menu> menuList = new ArrayList<>(); // 메뉴 리스트
    private List<Product> tteokbokkiList = new ArrayList<>(); // 떡볶이 리스트
    private List<Product> sideList = new ArrayList<>(); // 사이드 메뉴 리스트
    private List<Product> drinkList = new ArrayList<>(); // 음료 리스트
    private List<Product> mealKitList = new ArrayList<>(); // 밀키트 리스트

    public void initProduct(){
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

    public String getAddmenu() {
        return addmenu;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public void addProduct(String addmenu,Product product ){
        for (Menu i : menuList){
            if (Objects.equals(addmenu, i)){

            }
        }

    }



}
