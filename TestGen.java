import edu.princeton.cs.algs4.TwoPersonZeroSumGame;

import java.math.BigInteger;
import java.util.Scanner;

public class TestGen {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the maximum number of troops:");
        Integer max_num_troop = scanner.nextInt();
        System.out.println("Enter the maximum power");
        Integer max_power = scanner.nextInt();
        for (Integer troop2 = 1; troop2 <= max_num_troop; troop2++) {
            for (Integer troop1 = 1; troop1 <= max_num_troop; troop1++) {
                for (Integer power2 = troop2; power2 <= max_power; power2++) {
                    for (Integer power1 =  troop1 ; power1 <= power2; power1++) {
                        Test test = new Test(troop1,power1,troop2,power2);
                        int myCode = print(test.solve(),power1,power2);
                        int paper = Paper.solve(troop1,troop2,power1,power2);
                       // System.out.println("heeey your code is " + myCode + " but the paper is " + paper);
                        if( Math.abs(myCode-paper) > 1){
                            System.out.println("heeey your code is " + myCode + " but the paper is " + paper);
                            System.out.println("Second Troops: " + troop2 + " Second Power:" + power2 );
                            System.out.println("First Troops: " + troop1 + " First Power: " + power1 + " percent: " + (((double)1/(double)troop1)*100) );
                        }
                    }
                }
            }
        }

        /*
        double[] x = zerosum.row();
        double[] y = zerosum.column();
        StdOut.print("A[] = [");
        for (int j = 0; j < m-1; j++)
            StdOut.printf("%8.4f, ", x[j]);
        StdOut.printf("%8.4f]\n", x[m-1]);

        StdOut.print("B[] = [");
        for (int i = 0; i < n-1; i++)
            StdOut.printf("%8.4f, ", y[i]);
        StdOut.printf("%8.4f]\n", y[n-1]);
         */
    }
    private static int print(TwoPersonZeroSumGame zeroSumGame, int n, int m) {
        double[] x = zeroSumGame.row();
        double[] y = zeroSumGame.column();
        /*for (int j = 0; j < m; j++) {
            if (x[j] > 0 && x[j] < 0.9)
                System.out.println("baaaad");
            else  if( x[j] > 0.9){
                System.out.println("j: " + (j+1) + " / " + m + " " + (((double)(j+1)/(double)m)*100) );
            }
        }*/
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if( y[i] > 0.9){
                ans = i+1;
            }
        }
        return ans;
    }
}
