import java.util.Scanner;

public class Main {

    private static final String NUMBER_REG = "^[1-6]*$";

    public static void main(String[] args) throws Exception {
        KioskApp app = new KioskApp();
        app.insertMenu();
        app.kiosk();
    }
}