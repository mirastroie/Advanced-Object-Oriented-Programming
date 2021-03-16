package L3.Ex4.model;

public class Parallelogram extends Quadrilateral{
    protected double b;
    protected double h;
    public Parallelogram(double b, double h)
    {
        this.b = b;
        this.h = h;
    }
    @Override
    public double Area()
    {
        //System.out.print("Parallelogram ");
        return b*h;
    }

}
