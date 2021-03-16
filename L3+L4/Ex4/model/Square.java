package L3.Ex4.model;

public class Square extends Rectangle {

    public Square(double l)
    {
        super(l,l);
    }
    @Override
    public double Area()
    {
        /// System.out.print("Square ");
        return b*b;
    }
}
