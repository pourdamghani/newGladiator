import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import static sun.audio.AudioPlayer.player;

/**
 * Created by Arash on 18/03/07.
 */

class Player{
    private Integer number_of_troops, power_of_troops;
    private Integer alive_power,alive_troops;
    private Integer id;

    public Player(Integer id){
        Scanner playerScanner = new Scanner(System.in);
        number_of_troops = playerScanner.nextInt();
        power_of_troops = playerScanner.nextInt();
        this.id = id;
    }

    public Integer getNumber() {
        return number_of_troops;
    }

    public Integer getPower() {
        return power_of_troops;
    }

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
    public Integer getAlive_troops() {
        return alive_troops;
    }

    public Integer getId() {
        return id;
    }

    public static boolean empty(Player player){
        return (player.getAlive_power() == 0 || player.getAlive_troops() == 0);
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
    public Fraction sum(Fraction second){
        BigInteger lcm = Fraction.LCM(denominator,second.denominator);
        BigInteger first_mul = lcm.divide(denominator), second_mul = lcm.divide(second.denominator);
        return new Fraction(numerator.multiply(first_mul).add(second.numerator.multiply(second_mul)),lcm);
    }
    private static BigInteger LCM(BigInteger first, BigInteger second){
        return (first.multiply(second).divide(first.gcd(second)));
    }
}

class Pair{
    private Fraction first,second;
    Pair(Player one, Integer i, Integer j){
        if(one.getId() == 0){
            first = new Fraction(i,1);
            second = new Fraction(j, 1);
        }
        else {
            first = new Fraction(j, 1);
            second = new Fraction(i,1);
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

    public Pair Sum(Pair other){
        return new Pair(first.sum(other.getFirst()), second.sum(other.getSecond()));
    }

    public Pair multi(Fraction fraction){
        return new Pair(first.multi(fraction),second.multi(fraction));
    }
}

public class Test {
    private static ArrayList<ArrayList<Fraction>> matrix = new ArrayList<ArrayList<Fraction>>();
    private static ArrayList<Player> players= new ArrayList<Player>();
    public static void main(String[] args) {
        Scanner mainScanner = new Scanner(System.in);
        Integer number_of_players = mainScanner.nextInt();
        Integer number_of_battle_fields = mainScanner.nextInt();
        for (Integer i = 0; i < number_of_players; i++) {
            players.add(new Player(i));
        }
        solveForTwoPlayers(players,null);
    }

    private static Pair solveForTwoPlayers(ArrayList<Player> players,Player winner){
        if (winner == null) {

        }
        Pair tempSum = new Pair(winner,0,0);
        for (Player player : players) {
            if (!player.equals(winner)){
                if(Player.empty(player)){
                    return new Pair(player , 0  , 1);
                }
                else{
                    for (Integer power = 0; power < player.getAlive_power(); power++) {
                        player.decrese(power);

                        player.increase(power);
                    }
                }
            }
        }
    }

    private static void LP( ArrayList<ArrayList<Fraction>> matrix){

    }
}
