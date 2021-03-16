package L3.Ex4.main;
import L3.Ex4.model.Parallelogram;
import L3.Ex4.model.Quadrilateral;
import L3.Ex4.model.Rhombus;
import L3.Ex4.model.Rectangle;
import L3.Ex4.model.Square;

public class Ex4 {

    public static void main(String[] args) {

        Quadrilateral []a = new Quadrilateral[4];
        a[0] = new Parallelogram(3,4);
        a[1] = new Rhombus(3,5);
        a[2] = new Rectangle(4,5);
        a[3] = new Square(4);

        for(Quadrilateral item: a)
        {
            System.out.println(item.Area());
        }
    }
}
