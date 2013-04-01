package player;

import java.util.List;

/*************************************************************************
 *  Compilation:  javac Rational.java
 *  Execution:    java Rational
 *
 *  ADT for nonnegative Rational numbers. Bare-bones implementation.
 *  Cancel common factors, but does not stave off overflow. Does not
 *  support negative fractions.
 *
 *  Invariant: all Rational objects are in reduced form (except
 *  possibly while modifying).
 *
 *  Remarks
 *  --------
 *    - See http://www.cs.princeton.edu/introcs/92symbolic/BigRational.java.html
 *      for a version that supports negative fractions and arbitrary
 *      precision numerators and denominators.
 *  
 *  More Remarks:
 *  --------
 *     - Downloaded and modified by Qian Lin (4/22/2013).
 *     
 *  % java Rational
 *  5/6
 *  1
 *  28/51
 *  17/899
 *  0
 *
 *************************************************************************/

public class Rational {
    public final int num;   // the numerator
    public final int den;   // the denominator

    // create and initialize a new Rational object
    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new RuntimeException("Denominator is zero");
        }
        int g = gcd(numerator, denominator);
        num = numerator   / g;
        den = denominator / g;
    }
    
    @Override
    public Rational clone() {
        return new Rational(num,den);
    }
    
    public boolean equals(Rational other) {
        return ((num==other.num) && (den==other.den));        
    }

    // return string representation of (this)
    public String toString() {
        if (den == 1) { return num + "";        }
        else          { return num + "/" + den; }
    }
    
    /**
     * Convert List<String> s representing a rational number to a Rational.
     * 
     * @param rationalString
     *            s.size()=0, 1 or 3. if size=0, return 1/1; 
     *            if size=1, first element represents an
     *            integer. if size=3, first element represents an valid, nonzero
     *            numerator, or an empty string which be default means 1; second
     *            element is "/"; third element is a valid, nonzero denominator,
     *            or an empty string which by default means 2;
     * 
     * @return Rational The Rational form of the passed in fraction
     * @throws RuntimeException
     *             if s.size() is not 0, 1 or 3, or does not match the above
     *             description
     */
    public static Rational stringListToRational(List<String> rationalString) {
        int len = rationalString.size();
        
        if (len == 0) 
        	return new Rational(1,1);
        else if (len == 1) {
            if (rationalString.get(0).matches("[1-9]+[0-9]*"))
                return new Rational(Integer.parseInt(rationalString.get(0)), 1);
            throw new RuntimeException("Integer length notation incorrect.");
        }
        else if (len == 3) {
            int numerator;
            int denominator;
            
            String numeratorString = rationalString.get(0);
            if (numeratorString.length() == 0)
                numerator = 1;
            else if (numeratorString.matches("[1-9]+[0-9]*"))
                numerator = Integer.parseInt(numeratorString);
            else
                throw new RuntimeException("Numerator notation incorrect.");
            
            String divisionSignString = rationalString.get(1);
            if (!divisionSignString.equals("/"))
                throw new RuntimeException("Rational / notation incorrect.");
            
            String denominatorString = rationalString.get(2);
            if (denominatorString.length() == 0)
                denominator = 2;
            else if (denominatorString.matches("[1-9]+[0-9]*"))
                denominator = Integer.parseInt(denominatorString);
            else
                throw new RuntimeException("Denominator notation incorrect.");
            
            return new Rational(numerator, denominator);
        }
        throw new RuntimeException("Length input size incorrect.");
    }

    // return (this * b)
    public Rational times(Rational b) {
        return new Rational(this.num * b.num, this.den * b.den);
    }

    // return (this + b)
    public Rational plus(Rational b) {
        int numerator   = (this.num * b.den) + (this.den * b.num);
        int denominator = this.den * b.den;
        return new Rational(numerator, denominator);
    }

    // return (1 / this)
    public Rational reciprocal() { return new Rational(den, num);  }

    // return (this / b)
    public Rational divides(Rational b) {
        return this.times(b.reciprocal());
    }
    
    // [helper] return gcd(m, n)
    private static int gcd(int m, int n) {
        if (0 == n) return m;
        else return gcd(n, m % n);
    }
}