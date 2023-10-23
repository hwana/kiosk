public class Product extends Menu {

    public Product(String name, String description, int price) {
        super(name, description);
        this.price = price;
    }

    private int price;

    public int getPrice() {
        return price;
    }

    @Override
    public void print() {
        System.out.printf("%-15s | ₩ %s | %s%n", super.getName(), getPrice(), super.getDescription());
    }

    public void print(int count) {
        System.out.printf("%-15s | ₩ %s | %s개 | %s%n", super.getName(), getPrice() * count, count, super.getDescription());
    }
}
