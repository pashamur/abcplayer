package abcmusic;

import static org.junit.Assert.*;
import org.junit.Test;
import player.Rational;
import test.TestHelpers;

public class SectionTest {
	//test is the correct number of measures are generated and if length of measures are correct
    @Test
    public void SectionTest_scale() {
    	Section section = TestHelpers.getFirstSectionFromFile("sample_abc/scale.abc");
        assertEquals(4,section.size);
        Rational expMeter=new Rational(4,1);
        for (int i=0;i<4;i++)
            assertTrue(expMeter.equals(section.mList.get(i)));
    }
    @Test
    public void SectionTest_piece1() {
    	Section section = TestHelpers.getFirstSectionFromFile("sample_abc/piece1.abc");
        assertEquals(4,section.size);
        Rational expMeter=new Rational(4,1);
        for (int i=0;i<4;i++)
            assertTrue(expMeter.equals(section.mList.get(i)));
    }
    @Test
    public void SectionTest_piece2() {
        Section section = TestHelpers.getFirstSectionFromFile("sample_abc/piece2.abc");
        assertEquals(6,section.size);
        Rational expMeter=new Rational(4,1);
        for (int i=0;i<6;i++)
            assertTrue(expMeter.equals(section.mList.get(i)));
    }
    @Test (expected=RuntimeException.class)
    public void SectionTest_ExtraBar() {
        TestHelpers.getFirstSectionFromFile("sample_abc/testSection0.abc");
    }
    @Test (expected=RuntimeException.class)
    public void SectionTest_StartingWithBar() {
        TestHelpers.getFirstSectionFromFile("sample_abc/testSection1.abc");
    }
}
