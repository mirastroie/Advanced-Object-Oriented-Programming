package L3;
import java.util.Scanner;
import java.util.Random;
public class Ex1 {

    public static int computeSum(int [][]a)
    {
        int sum = 0;
        for(int []arr : a)
            for(int elem : arr)
                sum += elem;

        return sum;
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Please give the dimensions of the two dimensional array: ");
        System.out.print("n = ");
        int n = scanner.nextInt();
        System.out.print("m = ");
        int m = scanner.nextInt();

        int [][]a = new int[n][m];
        for(int i = 0;  i < n; i ++) {
            for (int j = 0; j < m; j++)
                a[i][j] = rand.nextInt(20);
        }
        System.out.println("The generated two dimensional array is: ");
        for(int []arr : a) {
            for (int elem : arr)
                System.out.print(elem + " ");
            System.out.println();
        }
        System.out.println("The sum of the elements is = " + computeSum(a));
    }

}
