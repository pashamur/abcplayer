package player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main entry point of your application.
 */
public class Main {

    /**
     * Plays the input file using Java MIDI API and displays header information
     * to the standard output stream.
     * 
     * (Your code should not exit the application abnormally using
     * System.exit().)
     * 
     * @param file
     *            the name of input abc file
     * @throws IOException
     */
    public static void play(String file) throws IOException {
        List<String> result = new ArrayList<String>();
        Header header=readFile(file,result);
        Lexer lexer = new Lexer(result, header);
        writeTokens("../../dp1/1.txt",header,lexer);
    }
    public static Header readFile(String file, List<String> result) throws IOException{
        FileReader fileReader;
        List<String> headerStr=new ArrayList<String>();
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
                if (head==1) result.add(temp);
                else headerStr.add(temp);
            }
            if ((!(temp.equals(""))) && (temp.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(headerStr);
            }
        }
        return header;
    }
    public static void writeTokens(String file, Header header, Lexer lexer) throws IOException {
        //print for check
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int j = 0; j < header.getNumVoices(); j++) {
            List<Token> tk = lexer.getTokens(j);
            for (int i = 0; i < tk.size(); i++) {
                try {

                    bw.write(tk.get(i).print());
                    bw.newLine();
                } catch (RuntimeException e) {}
            }
        }
        bw.close();
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        play("sample_abc/invention.abc");
    }
}
