import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Arash on 18/03/07.
 */

class Player{
    private Integer number_of_troops, power_of_troops;

    public Player(){
        Scanner scanner = new Scanner(System.in);
        number_of_troops = scanner.nextInt();
        power_of_troops = scanner.nextInt();
    }

    public Integer getNumber() {
        return number_of_troops;
    }

    public Integer getPower() {
        return power_of_troops;
    }
}

class Fraction{
    private Integer numerator,denominator;

    public Fraction(Integer numerator, Integer denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Integer getNumerator() {
        return numerator;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public Fraction multi(Fraction first, Fraction second){
        return new Fraction(first.getNumerator()*second.getNumerator(),first.getDenominator()*second.getDenominator());
    }
}

public class Test {
    public static void main(String[] args) {
        Player first = new Player(), second = new Player();
        ArrayList<ArrayList<Fraction>> matrix = new ArrayList<ArrayList<Fraction>>();
        for (Integer first_num = 0; first_num < first.getNumber(); first_num++) {
            ArrayList<Fraction> temp = new ArrayList<Fraction>();
            for (Integer second_num = 0; second_num < second.getNumber(); second_num++) {
                temp.add(solve(first_num,second_num));
            }
            matrix.add(temp);
        }
        LP(matrix);
    }

    private static Fraction solve(Integer first, Integer second){

    }

    private static void LP( ArrayList<ArrayList<Fraction>> matrix){

    }
}
