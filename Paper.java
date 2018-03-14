import org.apache.commons.math3.special.Beta;
public class Paper {
    public static int solve(int aTroops,int bTroops,int  aPower,int  bPower){
        double maximum = -1.0;
        int ans = 0;
        for (int aTroop = 1; aTroop <= aTroops; aTroop++) {
            double temp = calcMathematically(aTroop,aPower,bTroops,bPower);
            if(temp > maximum){
                maximum = temp;
                ans = aTroop;
            }
        }
        return (aPower+ans-1)/ans;
    }
    private static double calcMathematically(double aTroops, double aPower, double bTroops, double bPower){
        double x = (aTroops*bPower)/(aTroops*bPower+bTroops*aPower);
        return (double)(1)/(double)(2) - Beta.regularizedBeta(x,aTroops,bTroops);
    }
    
}
