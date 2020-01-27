public class Product {
    private String productId;
    private int price;
    private int quantity = 1;

    public Product(String id, int p) {
        productId = id;
        price = p;
    }

    public Product(String id, int p, int q) {
        productId = id;
        price = p;
        quantity = q;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }
}
