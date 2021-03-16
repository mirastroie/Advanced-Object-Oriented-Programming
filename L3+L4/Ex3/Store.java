package L3.Ex3;

public class Store {

    private String name;
    protected static int no;
    /// a store can't have more than 100 products
    public Product [] products = new Product[100];

    public Store(String name, Product first, Product second, Product third)
    {
        this.name = name;
        this.no = 3;
        products[0] = new Product(first);
        products[1] = new Product(second);
        products[2] = new Product(third);
    }
    public String toString()
    {
        String res = "The store " + name + " has the following products: \n";
        for(Product product : products)
        {
            if(product != null)
               res += product.toString() + "\n";
        }
        return res;
    }
    public double getTotalStore()
    {
        double total = 0;
        for(Product product : products)
        {
            if(product != null)
                total += product.getTotalProduct();
        }
        return total;
    }
    public static void increaseNo(){ no += 1;}
    public static int getNo(){ return no;}

    public static void main(String[] args) {

        Store store = new Store("Barnes&Noble", new Product("American Dirt",25.99,34),
                new Product("How Grinch Stole Christmas", 20.5,120), new Product("A promised land", 34.5,68));

        System.out.println(store);
        ServiceClassEx3 service = ServiceClassEx3.getServiceClassEx3();
        service.addProducts(store,new Product("The hill we climb",12,45));
        System.out.println(store);
        System.out.println("The total price of the products is: " + store.getTotalStore());

    }
}

