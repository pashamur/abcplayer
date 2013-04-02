package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import player.Main;
import abcmusic.Measure;
import abcmusic.Music;
import abcmusic.Section;

import lexer.Header;
import lexer.Lexer;
import lexer.Token;

public class TestHelpers {
    /**
     * Helper function that reads lines from a file and creates a valid header
     * 
     * @param filename The filename of the music
     * @return A valid header with information extracted from the file
     * @throws IOException If we have trouble finding the file
     */
	public static Header getFileHeader(String filename) throws IOException {
        List<String> headerLines = new ArrayList<String>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            throw new IOException("Cannot find the file");
        }
        
        BufferedReader reader = new BufferedReader(fileReader);
        String nextLine;
        int head = 0;
        Header header = null;
        
        while ((nextLine = reader.readLine()) != null) {
            Pattern commentPattern = Pattern.compile("%[\\w\\s]*");
            if ((!commentPattern.matcher(nextLine).matches()) && (!nextLine.equals(""))) {
                headerLines.add(nextLine);
            }
            if ((!(nextLine.equals(""))) && (nextLine.substring(0, 1).equals("K"))
                    && (head == 0)) {
                head = 1;
                header = new Header(headerLines);
            }
        }
        
        return header;
	}
	
	// Load a file and get the first section from it
    public static Section getFirstSectionFromFile(String filename){
    	Lexer lexer = getLexerFromFile(filename);
        List<Token> tk=lexer.getTokens(0);
        if (tk.get(tk.size()-1).type.equals(Token.Type.repeat_end))
            return new Section(tk.subList(0,tk.size()));
        else return new Section(tk.subList(0,tk.size()-1));
	}
    
    // Load a file and create a music instance from it
    public static Music getMusicFromFile(String filename){
    	Lexer lexer = getLexerFromFile(filename);

    	return new Music(lexer);
	}
    
    // Load a file and get the first measure from it
    public static Measure getFirstMeasureFromFile(String filename){
        Lexer lexer = getLexerFromFile(filename);
        List<Token> tk=lexer.getTokens(0);
            
        return new Measure(tk);
	}
    
    // Load a file and get the first measure from it
    public static List<Token> getLexerTokensFromFile(String filename){
        Lexer lexer = getLexerFromFile(filename);

        return lexer.getTokens(0);
	}
    
    public static Lexer getLexerFromFile(String filename){
        List<String> result = new ArrayList<String>();
        try {
        	Header header=Main.readFile(filename,result);
            Lexer lexer = new Lexer(result, header);
            return lexer;
        }
        catch (IOException e) {
            throw new RuntimeException("File error.");
        }
	}
}
