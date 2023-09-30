import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        File file = new File("polynomials.txt");
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = { 1, 2 };
        int[] e1 = { 0, 1 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { 1, 2 };
        int[] e2 = { 0, 1 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        Polynomial x = p1.multiply(p2);
        try {
            Polynomial t = new Polynomial(file);
            t.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
        x.print();
        try {
            s.saveToFile("save.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
    }
}