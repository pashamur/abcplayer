package player;

import org.junit.Test;
import static org.junit.Assert.*;

public class RationalTest {
	/**
	 * Representation invariant: gcd(numerator, denominator) = 1 except when numerator = 0.
	 * 
	 * Testing strategy:
	 * 1) Check that fractions are reduced to lowest terms
	 * 2) Test that 0/5 and 0/1 both have equivalent representations (0/1)
	 * 3) Check that the Rational constructor simplifies fractions to lowest terms
	 * 4) Check that Rational addition preserves the representation invariant
	 * 5) Check that Rational multiplication preserves the representation invariant
	 */
	
    // test integer
    @Test
    public void RationalTest_Integer() {
        Rational result=new Rational(10,2);
        Rational expected=new Rational(5,1);
        assertEquals(result.num,5);
        assertEquals(result.den,1);
        assertTrue(result.equals(expected));
    }
    // test 0 is stored as 0/1
    @Test
    public void RatinoalTest_Zero() {
        Rational result=new Rational(0,5);
        Rational expected=new Rational(0,1);
        assertTrue(result.equals(expected));
    }
    // test rep. invariant lcm(den,num)=1
    @Test
    public void RationalTest_lcm(){
        Rational result=new Rational(12,27);
        assertEquals(result.den,9);
        assertEquals(result.num,4);
    }
    // test plus do not modify the adding terms
    @Test
    public void RationalPlusTest_Basic() {
        Rational r1=new Rational(5,1);
        Rational r2=new Rational(3,7);
        Rational result=r1.plus(r2);
        Rational expected=new Rational(38,7);
        assertTrue(expected.equals(result));
        assertTrue(r1.equals(new Rational(5,1)));
        assertTrue(r2.equals(new Rational(3,7)));
    }
    
    // Test basic rational multiplication (and that the result is reduced to lowest terms)
    @Test
    public void RationalMultiplyTest(){
    	Rational r1 = new Rational(3,4);
    	Rational r2 = new Rational(5,3);
    	Rational result = r1.times(r2);
    	Rational expectedResult = new Rational(5,4);
    	assertTrue(result.equals(expectedResult));
    }
}
