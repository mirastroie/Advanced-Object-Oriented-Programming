package L3.Ex3;

public class Product {

    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public Product(Product p)
    {
        this.name = p.name;
        this.price = p.price;
        this.quantity = p.quantity;
    }

    @Override
    public String toString()
    {
        return "Product " + name + " " + price + " " + quantity;
    }
    public double getTotalProduct()
    {
        return price * quantity;
    }
}
