import edu.princeton.cs.algs4.TwoPersonZeroSumGame;

/**
 * Created by Arash on 18/03/07.
 */

public class Test {
    private static Player firstPlayer,secondPlayer;
    public static TwoPersonZeroSumGame solve(Integer firstTroops, Integer firstPower, Integer secondTroops,  Integer secondPower){
        firstPlayer = new Player(1, firstTroops,firstPower);
        secondPlayer = new Player(2,secondTroops,secondPower);
        Fraction[][] matrix = new  Fraction[firstPlayer.getAlive_power()+10][secondPlayer.getAlive_power()+10];
        Fraction zeroSumized = new Fraction(-1,2);
        for (Integer first_power = 1; first_power <= firstPlayer.getAlive_power(); first_power++) {
            firstPlayer.decrease(first_power);
            for (Integer second_power = 1; second_power <= secondPlayer.getAlive_power(); second_power++) {
                secondPlayer.decrease(second_power);
                Integer whole_power = first_power+second_power;
                Fraction first_wins = solveForTwoPlayers(secondPlayer, firstPlayer,first_power).getFraction().multi(new Fraction(first_power,whole_power));
                Fraction second_wins = solveForTwoPlayers(firstPlayer,secondPlayer,second_power).getFraction().multi(new Fraction(second_power,whole_power));
                matrix[first_power][second_power] = first_wins.plus(second_wins).plus(zeroSumized);
                secondPlayer.increase(second_power);
            }
            firstPlayer.increase(first_power);
        }
        return FindNash(matrix, firstPlayer.getAlive_power(),secondPlayer.getAlive_power());
    }
    private static Node solveForTwoPlayers(Player looser,Player winner, Integer winnerPower){
        Fraction wholeTemp;
        if( looser.isFirstPlayer())
            wholeTemp = new Fraction(0,1);
        else
            wholeTemp = new Fraction(1,1);
        Node node = new Node(looser,null),tempCont = null,tempCahng = null;
        Integer tempPower = 0;
        for (Integer power = 1; !looser.empty() && power <= looser.getAlive_power(); power++) {
            looser.decrease(power);
            Node cont = solveForTwoPlayers(looser,winner,winnerPower);
            Fraction continued = cont.getFraction().multi(new Fraction(winnerPower,winnerPower+power));
            Node chang = solveForTwoPlayers(winner,looser,power);
            Fraction changed = chang.getFraction().multi(new Fraction(power,winnerPower+power));
            looser.increase(power);
            if (looser.isFirstPlayer()) {
                wholeTemp = Fraction.max( wholeTemp,changed.plus(continued));
                tempPower = power;
                tempCont = cont;
                tempCahng = chang;
            }
            else {
                wholeTemp = Fraction.min(wholeTemp,changed.plus(continued));
                tempPower = power;
            }
        }
        node.setChild(tempCont,tempCahng);
        node.setFraction(wholeTemp);
        node.setValue(tempPower);
        return node;
    }

    private static TwoPersonZeroSumGame FindNash(Fraction[][] matrix, int n, int m){
        double[][] export = new double[n][m];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                export[i-1][j-1] = matrix[i][j].getDouble();
            }
        }
        TwoPersonZeroSumGame zeroSumGame = new TwoPersonZeroSumGame(export);
        find_Best(zeroSumGame,n,m);
        return zeroSumGame;
    }
    private static void find_Best(TwoPersonZeroSumGame zeroSumGame, int n, int m){
        double[] x = zeroSumGame.row();
        double[] y = zeroSumGame.column();
        int bestDovom = m+1, bestAval = n+1;
        for (int j = 0; j < m; j++) {
            if( x[j] > 0.9){
                 bestDovom = j+1;
            }
        }
        for (int i = 0; i < n; i++) {
            if( y[i] > 0.9){
                bestAval = i+1;
            }
        }
        Node root = new Node(null,null);
        firstPlayer.decrease(bestAval);
        secondPlayer.decrease(bestDovom);
        Node temp1 = solveForTwoPlayers(firstPlayer,secondPlayer,bestDovom);
        Node temp2 = solveForTwoPlayers(secondPlayer,firstPlayer,bestAval);
        secondPlayer.increase(bestDovom);
        firstPlayer.increase(bestAval);
        root.setChild(temp1,temp2);
        root.setValue(bestAval);
        print_Best_tree(root);
        System.out.println();
    }
    private static void print_Best_tree(Node node){
        if(node!=null){
            if(node.getValue()!= null && node.getValue()!=0)
                System.out.print(node.getValue() + " ");
           else
                System.out.println();
            for (Node child : node.getChilds()) {
                print_Best_tree(child);
            }
        }
    }
}