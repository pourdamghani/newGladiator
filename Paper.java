import org.apache.commons.math3.special.Beta;
public class Paper {
    public static int solve(int aTroops,int bTroops,int  aPower,int  bPower){
        /*double maximum = -1.0;
        int ans = 0;*/
        double maximumBycode = -1.0;
        int ansBycode = 0;
        for (int aTroop = 1; aTroop <= aTroops; aTroop++) {
            /*double temp = calcMathematically(aTroop,aPower,bTroops,bPower);
            if(temp > maximum){
                maximum = temp;
                ans = aTroop;
            }*/
            Fraction fraction = calcByCode(aTroop,aPower,bTroops,bPower);
            double tempByCode = fraction.getDouble()-(double)(1)/(double)(2);
            if(tempByCode > maximumBycode){
                maximumBycode = tempByCode;
                ansBycode = aTroop;
            }
        }
        return (aPower+ansBycode-1)/ansBycode;
    }
    /*private static double calcMathematically(double aTroops, double aPower, double bTroops, double bPower){
        double x = (aTroops*bPower)/(aTroops*bPower+bTroops*aPower);
        return (double)(1)/(double)(2) - Beta.regularizedBeta(x,aTroops,bTroops);
    }*/
    private static Fraction calcByCode(Integer aTroops, Integer aPower, Integer bTroops, Integer bPower){
        if(aTroops == 0)
            return new Fraction(0,1);
        if(bTroops == 0)
            return new Fraction(1,1);
        int aNow = (aPower+aTroops-1)/aTroops;
        int bNow = (bPower+bTroops-1)/bTroops;
        return (new Fraction(aNow,aNow+bNow).multi(calcByCode(aTroops,aPower,bTroops-1,bPower-bNow))).plus(
                new Fraction(bNow,aNow+bNow).multi(calcByCode(aTroops-1,aPower-aNow,bTroops,bPower)));
    }
}
