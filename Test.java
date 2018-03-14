import edu.princeton.cs.algs4.TwoPersonZeroSumGame;

import java.math.BigInteger;
/**
 * Created by Arash on 18/03/07.
 */

class Player{
    private Integer alive_power,alive_troops;
    private Integer id;

    Player(Integer id, Integer alive_troops, Integer alive_power){
        this.id = id;
        this.alive_troops = alive_troops;
        this.alive_power = alive_power;
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


public class Test {
    private Player firstPlyaer,secondPlayer;
    Test(Integer firstTroops, Integer firstPower, Integer secondTroops,  Integer secondPower) {
        this.firstPlyaer = new Player(1, firstTroops,firstPower);
        this.secondPlayer = new Player(2,secondTroops,secondPower);
    }

    public TwoPersonZeroSumGame solve(){
        Fraction[][] matrix = new  Fraction[firstPlyaer.getAlive_power()+10][secondPlayer.getAlive_power()+10];
        Fraction zeroSumized = new Fraction(-1,2);
        for (Integer first_power = 1; first_power <= firstPlyaer.getAlive_power(); first_power++) {
            firstPlyaer.decrease(first_power);
            for (Integer second_power = 1; second_power <= secondPlayer.getAlive_power(); second_power++) {
                secondPlayer.decrease(second_power);
                Integer whole_power = first_power+second_power;
                Fraction first_wins = solveForTwoPlayers(secondPlayer,firstPlyaer,first_power).multi(new Fraction(first_power,whole_power));
                Fraction second_wins = solveForTwoPlayers(firstPlyaer,secondPlayer,second_power).multi(new Fraction(second_power,whole_power));
                matrix[first_power][second_power] = first_wins.plus(second_wins).plus(zeroSumized);
                secondPlayer.increase(second_power);
            }
            firstPlyaer.increase(first_power);
        }
        return FindNash(matrix,firstPlyaer.getAlive_power(),secondPlayer.getAlive_power());
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
            if (looser.isFirstPlayer()) {
                wholeTemp = Fraction.max( wholeTemp,temp);
            }
            else {
                wholeTemp = Fraction.min(wholeTemp,temp);
            }
        }
            return wholeTemp;
    }

    private static TwoPersonZeroSumGame FindNash(Fraction[][] matrix, int n, int m){
        double[][] export = new double[n][m];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                export[i-1][j-1] = matrix[i][j].getDouble();
            }
        }
        return new TwoPersonZeroSumGame(export);
    }
    /*private static TwoPersonZeroSumGame FindNash(Fraction[][] matrix, int n, int m){
        double[][] export = new double[n][m];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                export[i-1][j-1] = (matrix[i][j].getNumerator().doubleValue())/(matrix[i][j].getDenominator().doubleValue());
            }
        }
        TwoPersonZeroSumGame zeroSumGame = new TwoPersonZeroSumGame(export);
        find_Best(zeroSumGame,n,m);
        return zeroSumGame;
    }
    static void find_Best(TwoPersonZeroSumGame zeroSumGame, int n, int m){
        double[] x = zeroSumGame.row();
        double[] y = zeroSumGame.column();
        int bestDovom = m+1, bestAval = n+1;
        for (int j = 0; j < m; j++) {
            if( x[j] > 0.9){
                 bestDovom = j+1;
                //System.out.println("j: " + (j+1) + " / " + m + " " + (((double)(j+1)/(double)m)*100) );

            }
        }
        for (int i = 0; i < n; i++) {
            if( y[i] > 0.9){
                bestAval = i+1;
                //System.out.println("i: " + (i+1) + " / " + n + " " + (((double)(i+1)/(double)n)*100) );
            }
        }

    }*/
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