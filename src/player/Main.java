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

import javax.sound.midi.MidiUnavailableException;

import sound.SequencePlayer;

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
        for (char c='A';c<'H'; c=(char)(c+1))
            System.out.println(header.getAccidental(c));        
        Lexer lexer = new Lexer(result, header);
        //writeTokens("../../dp1/lexer.txt",header,lexer);
        Music music=new Music(lexer);
        if (!music.checkRep()) throw new RuntimeException("Voices in music do not match.");
        writeMusic("../../dp1/music_origin.txt",music);
        ABCmusicTicks ticks = new ABCmusicTicks(header.getL());
        int ticksPerQuarterNote = ticks.ABCMusicTicks(music);
        // Number of quarter notes (!) per minute: Tempo * default note length divided by 4 (to scale according to quarter notes)
        System.out.println(header.getQ() + " " + header.getL());
        int beatsPerMinute = (header.getQ() * header.getL().num * 4) / header.getL().den;
        System.out.println(beatsPerMinute);
        ABCPlayer player = new ABCPlayer(ticksPerQuarterNote, beatsPerMinute, header);
        SequencePlayer p = player.on(music);
        //System.out.print(ticksPerQuarterNote);
        //System.out.println(p.toString());
        try {
			p.play();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static Header readFile(String file, List<String> result) throws IOException {
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
    public static void writeMusic(String file, Music music) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        try{
            bw.write(ABCmusicToString.abcmusicToString(music));
            } catch (RuntimeException e) {}
        bw.close();
        fw.close();
    }
    public static void main(String[] args) throws IOException {
        play("sample_abc/fur_elise_test.abc");
    }
}