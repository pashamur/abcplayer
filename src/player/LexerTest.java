package player;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

public class LexerTest {
    
    
@Test
public void LexerTest1() throws IOException{
String file="sample_abc/piece1.abc";
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
result.add("^^A,,/4");
Lexer lexer = new Lexer(result, header);
assertTrue("^^A,,1/4".equals(lexer.getTokens(0).get(0).print()));



}
}
