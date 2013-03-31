package lexer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import test.TestHelpers;

@SuppressWarnings("unused")

public class LexerTest {
    // I also print the result of lexer of all the examples in sample_abc, which
    // looks right. If you want to write a new test, follow the rule in LexerTest1

	
    @Test
    public void LexerTest1() throws IOException {
        // do not change anything here, it specifies a header with M 4/4 and L
        // 1/4, which will be used for the lexer
        
        //test ^^A,,/4
        Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
        // begin to change here, give the lexer a list of strings as the input,
        // stored in result
        ArrayList<String> result = new ArrayList<String>();
        result.add("^^A,,/4");
        Lexer lexer = new Lexer(result, header);
        assertTrue("^^A,,1/4".equals(lexer.getTokens(0).get(0).print()));
    }

    @Test
    public void LexerTest2() throws IOException {
        //test ^^A,,/2B5/C:|
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	
    	ArrayList<String> result = new ArrayList<String>();
        result.add("^^A,,/2B5/C:|");
        Lexer lexer = new Lexer(result, header);
        assertTrue("^^A,,1/2".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("B5/2".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("C".equals(lexer.getTokens(0).get(2).print()));
        assertTrue(":|".equals(lexer.getTokens(0).get(3).print()));
    }

    @Test
    public void LexerTest3() throws IOException {
        //test [A,B/]
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("[A,B/]");
        Lexer lexer = new Lexer(result, header);
        assertTrue("[".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("A,".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("B1/2".equals(lexer.getTokens(0).get(2).print()));
        assertTrue("]".equals(lexer.getTokens(0).get(3).print()));

    }

    @Test
    public void LexerTest4() throws IOException {
       //test (2^A,B
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("(2^A,B");
        Lexer lexer = new Lexer(result, header);
        assertTrue("(2".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("^A,".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("B".equals(lexer.getTokens(0).get(2).print()));

    }

    @Test
    public void LexerTest5() throws IOException {
        //test A]|]
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("A]|]");
        Lexer lexer = new Lexer(result, header);
        assertTrue("A".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("]".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("|]".equals(lexer.getTokens(0).get(2).print()));
    }

    @Test
    public void LexerTest6() throws IOException {
        //test __A||
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("__A||");
        Lexer lexer = new Lexer(result, header);
        assertTrue("__A".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("||".equals(lexer.getTokens(0).get(1).print()));
    }

    @Test
    public void LexerTest7() throws IOException {
        //test |[1A,,
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("|[1A,,");
        Lexer lexer = new Lexer(result, header);
        assertTrue("|".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("[1".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("A,,".equals(lexer.getTokens(0).get(2).print()));

    }

    @Test
    public void LexerTest8() throws IOException {
        //test for ||:
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("||:A,,/");
        Lexer lexer = new Lexer(result, header);
        assertTrue("|".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("|:".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("A,,1/2".equals(lexer.getTokens(0).get(2).print()));

    }
    
    @Test(expected = RuntimeException.class)
    public void LexerTest9() throws IOException {
        //test for (5
		Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
		ArrayList<String> result = new ArrayList<String>();
	
        result.add("(5abcde");
		Lexer lexer = new Lexer(result, header);

    }

    @Test(expected = RuntimeException.class)
    public void LexerTest10() throws IOException {
        //test for wrong basicnote
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();

        result.add("habc");
        Lexer lexer = new Lexer(result, header);
    }

    @Test(expected = RuntimeException.class)
    public void LexerTest11() throws IOException {
        //test for strange length
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("bc ^A,,1/4/ ");
        Lexer lexer = new Lexer(result, header);
    }

    @Test(expected = RuntimeException.class)
    public void LexerTest12() throws IOException {
        //test for wrong accidental
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("==A,,2");
        Lexer lexer = new Lexer(result, header);
    }
    
    @Test(expected = RuntimeException.class)
    public void LexerTest13() throws IOException {
        //test for wrong nth repeat
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("[3ABC");
        Lexer lexer = new Lexer(result, header);
    }
    
    @Test(expected = RuntimeException.class)
    public void LexerTest14() throws IOException {
        //test for wrong order for note
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("A^,2");
        Lexer lexer = new Lexer(result, header);
    }
    
    @Test(expected = RuntimeException.class)
    public void LexerTest15() throws IOException {
        //test for wrong spaces
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("( 3abc");
        Lexer lexer = new Lexer(result, header);
    }
    @Test
    public void LexerTest16() throws IOException {
        //test a compicated one
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();
    	
        result.add("||:^^A,,,/4:|");
        Lexer lexer = new Lexer(result, header);
        assertTrue("|".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("|:".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("^^A,,,1/4".equals(lexer.getTokens(0).get(2).print()));
        assertTrue(":|".equals(lexer.getTokens(0).get(3).print()));
        
    }
    
    @Test
    public void LexerTest17() throws IOException {
        //test another complicated one
    	Header header = TestHelpers.getFileHeader("sample_abc/piece1.abc");
    	ArrayList<String> result = new ArrayList<String>();

        result.add("||:[^^A,,,/4^^B , 1 /]:|");
        Lexer lexer = new Lexer(result, header);
        assertTrue("|".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("|:".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("[".equals(lexer.getTokens(0).get(2).print()));       
        assertTrue("^^A,,,1/4".equals(lexer.getTokens(0).get(3).print()));
        assertTrue("^^B,1/2".equals(lexer.getTokens(0).get(4).print()));     
        assertTrue("]".equals(lexer.getTokens(0).get(5).print()));            
        assertTrue(":|".equals(lexer.getTokens(0).get(6).print()));
    }

    
}
