package player;

import org.junit.Test;
import static org.junit.Assert.*;

public class RationalTest {
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
}
