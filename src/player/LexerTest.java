package player;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

public class LexerTest {
    // I also print the result of lexer of all the examples in sample_abc, which
    // looks right
    // if you want to write a new test, follow the rule in LexerTest1

    @Test
    public void LexerTest1() throws IOException {
        // do not change anything here, it specifies a header with M 4/4 and L
        // 1/4, which will be used for the lexer
        
        //test ^^A,,/4
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        // begin to change here, give the lexer a list of strings as the input,
        // stored in result
        result = new ArrayList<String>();
        result.add("^^A,,/4");
        Lexer lexer = new Lexer(result, header);
        assertTrue("^^A,,1/4".equals(lexer.getTokens(0).get(0).print()));
    }

    @Test
    public void LexerTest2() throws IOException {
        //test ^^A,,/2B5/C:|
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
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
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
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
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
        result.add("(2^A,B");
        Lexer lexer = new Lexer(result, header);
        assertTrue("(2".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("^A,".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("B".equals(lexer.getTokens(0).get(2).print()));

    }

    @Test
    public void LexerTest5() throws IOException {
        //test A]|]
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
        result.add("A]|]");
        Lexer lexer = new Lexer(result, header);
        assertTrue("A".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("]".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("|]".equals(lexer.getTokens(0).get(2).print()));
    }

    @Test
    public void LexerTest6() throws IOException {
        //test __A||
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
        result.add("__A||");
        Lexer lexer = new Lexer(result, header);
        assertTrue("__A".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("||".equals(lexer.getTokens(0).get(1).print()));
    }

    @Test
    public void LexerTest7() throws IOException {
        //test |[1A,,
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
        result.add("|[1A,,");
        Lexer lexer = new Lexer(result, header);
        assertTrue("|".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("[1".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("A,,".equals(lexer.getTokens(0).get(2).print()));

    }

    @Test
    public void LexerTest8() throws IOException {
        //test for ||:
        String file = "sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String temp;
        int head = 0;
        Header header = null;
        while ((temp = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(temp).matches()) && (!temp.equals(""))) {
                result.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(result);
            }
        }
        result = new ArrayList<String>();
        result.add("||:A,,/");
        Lexer lexer = new Lexer(result, header);
        System.out.println(lexer.getTokens(0).get(0).print());
        assertTrue("|".equals(lexer.getTokens(0).get(0).print()));
        assertTrue("|:".equals(lexer.getTokens(0).get(1).print()));
        assertTrue("A,,1/2".equals(lexer.getTokens(0).get(2).print()));

    }

    
}
