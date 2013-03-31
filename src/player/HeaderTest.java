package player;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

public class HeaderTest {
    @Test
    public void HeaderTest1() throws IOException{
        String file="sample_abc/invention.abc";
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
                result = new ArrayList<String>();
            }
        }
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,8)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(4,4)));
        assertEquals(header.getTempo(),140);
        assertEquals(header.getNumVoices(),2);
        assertEquals(header.getVoiceIndex("1"),1);
        assertEquals(header.getVoiceIndex("2"),2);
        int[] keySign = new int[7];
        for (char i='A';i<'G';i++ ) {
           keySign[i-'A']=header.getAccidental(i); 
        }
        int[] expected= {0,0,0,0,0,0,0};
        assertArrayEquals(expected,keySign); 
        
         }

    @Test
    public void HeaderTest2() throws IOException{
        String file="sample_abc/prelude.abc";
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
                result = new ArrayList<String>();
            }
        }
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,16)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(4,4)));
        assertEquals(header.getTempo(),280);
        assertEquals(header.getNumVoices(),3);
        assertEquals(header.getVoiceIndex("1"),1);
        assertEquals(header.getVoiceIndex("2"),2);
        assertEquals(header.getVoiceIndex("3"),3);        
        int[] keySign = new int[7];
        for (char i='A';i<'G';i++ ) {
           keySign[i-'A']=header.getAccidental(i); 
        }
        int[] expected= {0,0,0,0,0,0,0};
        assertArrayEquals(expected,keySign); 
        

               }
    
 
    @Test
    public void HeaderTest3() throws IOException{
        String file="sample_abc/little_night_music.abc";
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
                result = new ArrayList<String>();
            }
        }
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,8)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(4,4)));
        assertEquals(header.getTempo(),280);
        int[] keySign = new int[7];
        for (char i='A';i<'G';i++ ) {
           keySign[i-'A']=header.getAccidental(i); 
        }
        int[] expected= {0, 0, 0, 0, 0, 1, 0};
        assertArrayEquals(expected,keySign); 
        
         }

    
    @Test
    public void HeaderTest4() throws IOException{
        String file="sample_abc/fur_elise.abc";
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
                result = new ArrayList<String>();
            }
        }
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,16)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(3,8)));
        assertEquals(header.getTempo(),240);
        assertEquals(header.getNumVoices(),2);
        assertEquals(header.getVoiceIndex("1"),1);
        assertEquals(header.getVoiceIndex("2"),2);
        int[] keySign = new int[7];
        for (char i='A';i<'G';i++ ) {
           keySign[i-'A']=header.getAccidental(i); 
        }
        int[] expected= {0,0,0,0,0,0,0};
        assertArrayEquals(expected,keySign); 
        
         }
    
    
}

