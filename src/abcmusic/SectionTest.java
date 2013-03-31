package abcmusic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Header;
import lexer.Lexer;
import lexer.Token;

import org.junit.Test;

import player.Main;
import player.Rational;
import test.TestHelpers;

public class SectionTest {
    
	//test is the correct number of measures are generated and if length of measures are correct
    @Test
    public void SectionTest_scale() {
    	Section section = TestHelpers.getFirstSectionFromFile("sample_abc/scale.abc");
        assertEquals(4,section.sizeInMeasures);
        Rational expMeter=new Rational(4,1);
        for (int i=0;i<4;i++)
            assertTrue(expMeter.equals(section.mList.get(i)));
    }
    @Test
    public void SectionTest_piece1() {
    	Section section = TestHelpers.getFirstSectionFromFile("sample_abc/piece1.abc");
        assertEquals(4,section.sizeInMeasures);
        Rational expMeter=new Rational(4,1);
        for (int i=0;i<4;i++)
            assertTrue(expMeter.equals(section.mList.get(i)));
    }
    @Test
    public void SectionTest_piece2() {
        Section section = TestHelpers.getFirstSectionFromFile("sample_abc/piece2.abc");
        assertEquals(6,section.sizeInMeasures);
        Rational expMeter=new Rational(4,1);
        for (int i=0;i<6;i++)
            assertTrue(expMeter.equals(section.mList.get(i)));
    }
    
    
 
    
}
