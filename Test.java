import java.math.BigInteger;
import java.util.Scanner;
/**
 * Created by Arash on 18/03/07.
 */

class Player{
    private Integer number_of_troops, power_of_troops;
    private Integer alive_power,alive_troops;
    private Integer id;

    Player(Integer id){
        Scanner playerScanner = new Scanner(System.in);
        number_of_troops = playerScanner.nextInt();
        power_of_troops = playerScanner.nextInt();
        this.id = id;
    }

    /*public Integer getNumber() {
        return number_of_troops;
    }

    public Integer getPower() {
        return power_of_troops;
    }*/

    public Integer getAlive_power() {
        return alive_power;
    }
    public void decrese(Integer amount){
        alive_power = alive_power-amount;
        alive_troops = alive_troops-1;
    }
    public void increase(Integer amount){
        alive_power = alive_power+amount;
        alive_troops = alive_troops+1;

    }
    public boolean empty(){
        return (alive_power == 0 || alive_troops == 0);
    }
    public boolean isFirstPlayer(){
        return id == 1;
    }
}

class Fraction{
    private BigInteger numerator;
    private BigInteger denominator;

    Fraction(Integer numerator, Integer denominator) {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(denominator);
    }
    private Fraction(BigInteger numerator, BigInteger denominator){
        this.numerator =  numerator;
        this.denominator = denominator;
    }

    private BigInteger getNumerator() {
        return numerator;
    }

    private BigInteger getDenominator() {
        return denominator;
    }

    public Fraction multi(Fraction second){
        return new Fraction(getNumerator().multiply(second.getNumerator()),getDenominator().multiply(second.getDenominator()));
    }
    public Fraction plus(Fraction other){
        BigInteger lcm = Fraction.LCM(denominator,other.denominator);
        BigInteger first_mul = lcm.divide(denominator), second_mul = lcm.divide(other.denominator);
        return new Fraction(numerator.multiply(first_mul).add(other.numerator.multiply(second_mul)),lcm);
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
        solveForTwoPlayers(new Player(1), new Player(2), null);
    }

    private static Fraction solveForTwoPlayers(Player looser,Player winner, Integer winnerPower){
        if (winnerPower == null) {
            return null;
        }
        else{
        if(looser.empty()){
            if(looser.isFirstPlayer())
                return new Fraction(-1,2);
            else
                return new Fraction(1,2);
        }
        Fraction wholeTemp;
        if( looser.isFirstPlayer())
            wholeTemp = new Fraction(0,1);
        else
            wholeTemp = new Fraction(1,1);
        for (Integer power = 0; power < looser.getAlive_power(); power++) {
            Integer wholePower = winnerPower+power;
            looser.decrese(power);
            Fraction countinue = solveForTwoPlayers(looser,winner,winnerPower).multi(new Fraction(winnerPower,wholePower));
            Fraction changed = solveForTwoPlayers(winner,looser,power).multi(new Fraction(power,wholePower));
            Fraction temp = changed.plus(countinue);
            looser.increase(power);
            if (looser.isFirstPlayer())
                wholeTemp = Fraction.max(temp,wholeTemp);
            else
                wholeTemp = Fraction.min(temp,wholeTemp);
            }
            return wholeTemp;
        }
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