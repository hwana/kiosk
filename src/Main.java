public class Main {

    public static void main(String[] args) {
        KioskApp app = new KioskApp();
        while (true) {
            try {
                app.kiosk();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("test");
            }
        }
    }
}