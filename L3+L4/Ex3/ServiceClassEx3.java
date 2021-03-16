package L3.Ex3;

public class ServiceClassEx3 {

    private static ServiceClassEx3 serviceClassEx3;
    public static ServiceClassEx3 getServiceClassEx3()
    {
        if(serviceClassEx3 == null)
            serviceClassEx3 = new ServiceClassEx3();
        return serviceClassEx3;
    }
    public void addProducts(Store store, Product product)
    {
        Store.increaseNo();
        store.products[Store.getNo()] = new Product(product);
    }
}
