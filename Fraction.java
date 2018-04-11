import java.math.BigInteger;

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
    public double getDouble(){
        return numerator.doubleValue()/denominator.doubleValue();
    }
}