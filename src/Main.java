public class Main {

    public static void main(String[] args) {
        KioskApp app = new KioskApp(); // app 객체 생성
        while (true) {
            try {
                app.kiosk(); // KioskApp의 kiosk() 메서드 실행
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}