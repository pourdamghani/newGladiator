import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TwoPersonZeroSumGame;

import java.math.BigInteger;
import java.util.Scanner;
/**
 * Created by Arash on 18/03/07.
 */

class Player{
    private Integer alive_power,alive_troops;
    private Integer id;

    Player(Integer id){
        this.id = id;
        Scanner playerScanner = new Scanner(System.in);
        System.out.println("Please enter number of troops of player " + id + ":");
        alive_troops = playerScanner.nextInt();
        System.out.println("Please power of troops of player " + id + ":");
        alive_power = playerScanner.nextInt();
    }

    public Integer getAlive_power() { return alive_power; }

    public void decrease(Integer amount){
        alive_power = alive_power-amount;
        alive_troops = alive_troops-1;
    }
    public void increase(Integer amount){
        alive_power = alive_power+amount;
        alive_troops = alive_troops+1;

    }

    public boolean empty(){ return (alive_power == 0 || alive_troops == 0); }
    public boolean isFirstPlayer(){ return id == 1; }
}

class Fraction{
    private BigInteger numerator;
    private BigInteger denominator;

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    Fraction(Integer numerator, Integer denominator) {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(denominator);
    }
    private Fraction(BigInteger numerator, BigInteger denominator){
        this.numerator =  numerator;
        this.denominator = denominator;
    }

    public Fraction multi(Fraction second){
        return new Fraction(numerator.multiply(second.numerator),denominator.multiply(second.denominator)).normalize();
    }
    public Fraction plus(Fraction other){
        BigInteger lcm = Fraction.LCM(denominator,other.denominator);
        BigInteger first_mul = lcm.divide(denominator), second_mul = lcm.divide(other.denominator);
        return new Fraction(numerator.multiply(first_mul).add(other.numerator.multiply(second_mul)),lcm).normalize();
    }
    private Fraction normalize(){
        BigInteger tempGCD = numerator.gcd(denominator);
        return new Fraction(numerator.divide(tempGCD),denominator.divide(tempGCD));
    }

    private static BigInteger LCM(BigInteger first, BigInteger second){
        return (first.multiply(second).divide(first.gcd(second)));
    }
    public static Fraction max(Fraction first, Fraction second){
        if(first.compareTo(second) > -1)
            return first;
        return second;
    }
    public static Fraction min(Fraction first, Fraction second){
        if(first.compareTo(second) < 1)
            return first;
        return second;
    }
    private Integer compareTo(Fraction other){
        BigInteger lcm = Fraction.LCM(denominator,other.denominator);
        BigInteger first_mul = lcm.divide(denominator), second_mul = lcm.divide(other.denominator);
        return numerator.multiply(first_mul).compareTo(other.numerator.multiply(second_mul));
    }
}


public class Test {
    public static void main(String[] args) {
        Player firstPlyaer = new Player(1), secondPlayer = new Player(2);
        Fraction[][] matrix = new  Fraction[firstPlyaer.getAlive_power()+10][secondPlayer.getAlive_power()+10];
        Fraction zeroSumized = new Fraction(-1,2);
        System.out.println("The Shape of Matrix is:");
        for (Integer first_power = 1; first_power <= firstPlyaer.getAlive_power(); first_power++) {
            firstPlyaer.decrease(first_power);
            for (Integer second_power = 1; second_power <= secondPlayer.getAlive_power(); second_power++) {
                secondPlayer.decrease(second_power);
                Integer whole_power = first_power+second_power;
                Fraction first_wins = solveForTwoPlayers(secondPlayer,firstPlyaer,first_power).multi(new Fraction(first_power,whole_power));
                Fraction second_wins = solveForTwoPlayers(firstPlyaer,secondPlayer,second_power).multi(new Fraction(second_power,whole_power));
                matrix[first_power][second_power] = first_wins.plus(second_wins).plus(zeroSumized);
                System.out.print(matrix[first_power][second_power].getNumerator()+ "/" + matrix[first_power][second_power].getDenominator() + "  ");
                secondPlayer.increase(second_power);
            }
            System.out.println();
            firstPlyaer.increase(first_power);
        }

        FindNash(matrix,firstPlyaer.getAlive_power(),secondPlayer.getAlive_power());

    }

    private static Fraction solveForTwoPlayers(Player looser,Player winner, Integer winnerPower){
        if(looser.empty()){
            if(looser.isFirstPlayer())
                return new Fraction(0,1);
            else
                return new Fraction(1,1);
        }
        Fraction wholeTemp;
        if( looser.isFirstPlayer())
            wholeTemp = new Fraction(0,1);
        else
            wholeTemp = new Fraction(1,1);
        for (Integer power = 1; power <= looser.getAlive_power(); power++) {
            Integer wholePower = winnerPower+power;
            looser.decrease(power);
            Fraction continued = solveForTwoPlayers(looser,winner,winnerPower).multi(new Fraction(winnerPower,wholePower));
            Fraction changed = solveForTwoPlayers(winner,looser,power).multi(new Fraction(power,wholePower));
            Fraction temp = changed.plus(continued);
            looser.increase(power);
            if (looser.isFirstPlayer())
                wholeTemp = Fraction.max(temp,wholeTemp);
            else
                wholeTemp = Fraction.min(temp,wholeTemp);
            }
            return wholeTemp;
    }

    private static void FindNash(Fraction[][] matrix, int n, int m){
        double[][] export = new double[n][m];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                export[i-1][j-1] = matrix[i][j].getNumerator().divide(matrix[i][j].getDenominator()).doubleValue();
            }
        }
        System.out.println("The Nash eq is:");
        TwoPersonZeroSumGame zerosum = new TwoPersonZeroSumGame(export);
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
    }
}

/*class Pair{
    private Fraction first,second;
    Pair(Player looser){
        if(looser.getId() == 0){
            first = new Fraction(-1,2);
            second = new Fraction(1, 2);
        }
        else {
            first = new Fraction(1, 2);
            second = new Fraction(-1,2);
        }
    }

    public Pair(Fraction first, Fraction second) {
        this.first = first;
        this.second = second;
    }

    public Fraction getFirst() {
        return first;
    }

    public Fraction getSecond() {
        return second;
    }

    public Pair sum(Pair other){
        return new Pair(first.sum(other.getFirst()), second.sum(other.getSecond()));
    }

    public Pair multi(Fraction fraction){
        return new Pair(first.multi(fraction),second.multi(fraction));
    }
}*/