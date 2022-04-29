import java.io.IOException;
import java.util.Stack;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.util.Random;

public class tester {
    public static void main(String[] args) throws IOException {
        Sudoku x = new Easy();
        System.out.println("Easy:");
        System.out.println(x);
        System.out.println(x.printCorrectBoard());
        System.out.println();

        Sudoku y = new Normal();
        System.out.println("Normal:");
        System.out.println(y);
        System.out.println(y.printCorrectBoard());
        System.out.println();

        Sudoku z = new Hard();
        System.out.println("Hard:");
        System.out.println(z);
        System.out.println(z.printCorrectBoard());


    }
}
