package lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import abcmusic.Pair;

import player.Rational;

public class Header {
    private int[] keySignature; // accidentals derived from key signature
                                // for A-G respectively.
    private Map<String, Integer> voice;// from voice label to index.
    private int numVoices;
    private Rational defaultNoteLength;
    private Pair<Integer, Integer> meter;
    private int tempo;
    
    @SuppressWarnings("serial")
    private class HeaderException extends RuntimeException {
        public HeaderException(String message) {
            super("HeaderException: "+message);
        }
    }
    /**
     * initialize all fields of Header. Lex header and generate a list of field
     * (K, M...) and their respective strings. get rid of comments.
     * 
     * @param headerLines A list of strings to be lexed
     * @throws HeaderException if X, T, K does not appear at the right place.
     * @throws HeaderException if syntax error within any one line.
     */
    public Header(List<String> headerLines) {
        int currentLine = 0;
        tempo = 100;
        defaultNoteLength = new Rational(1, 8);
        meter = new Pair<Integer, Integer>(4, 4);
        numVoices = 0;
        voice = new HashMap<String, Integer>();

        keySignature = new int[7];
        Pattern numberPattern = Pattern.compile("\\d+");
        Pattern wordPattern = Pattern.compile("[^\\s]+");
        Pattern XPattern = Pattern.compile("X:\\s*\\d+\\s*");
        Matcher XMatcher = XPattern.matcher(headerLines.get(currentLine));
        if (!XMatcher.matches()) {
            throw new HeaderException("Invalid input.");
        }
        currentLine++;
        Pattern titlePattern = Pattern.compile("T:.+");
        Matcher titleMatcher = titlePattern.matcher(headerLines.get(currentLine));
        if (!titleMatcher.matches()) {
            throw new HeaderException("Invalid input.");
        }

        Pattern noteLengthPattern = Pattern.compile("L:\\s*\\d+\\s*/\\s*\\d+\\s*");
        Pattern meterPattern = Pattern
                .compile("M:\\s*((\\d+\\s*/\\s*\\d+)|C|(C\\|))\\s*");
        Pattern tempoPattern = Pattern.compile("Q:\\s*\\d+\\s*");
        Pattern composerPattern = Pattern.compile("C:.+");
        Pattern voicePattern = Pattern.compile("V:\\s*[^\\s]+\\s*");

        for (currentLine = 2; currentLine < headerLines.size() - 1; currentLine++) {
            if (noteLengthPattern.matcher(headerLines.get(currentLine)).matches()) {
                Matcher numberMatcher = numberPattern.matcher(headerLines.get(currentLine));
                numberMatcher.find();
                int PairX = Integer.parseInt(numberMatcher.group());
                numberMatcher.find();
                int PairY = Integer.parseInt(numberMatcher.group());
                if (PairY == 0) {
                    throw new HeaderException("Invalid Input.");
                }
                defaultNoteLength = new Rational(PairX, PairY);
            } else if (meterPattern.matcher(headerLines.get(currentLine)).matches()) {
                int PairX;
                int PairY;
                Matcher numberMatcher = numberPattern.matcher(headerLines.get(currentLine));
                if (numberMatcher.find()) {
                    PairX = Integer.parseInt(numberMatcher.group());
                    numberMatcher.find();
                    PairY = Integer.parseInt(numberMatcher.group());
                    if (PairY == 0) {
                        throw new HeaderException("Invalid Input.");
                    }

                } else {
                    Pattern C = Pattern.compile("C");
                    Pattern C1 = Pattern.compile("C\\|");
                    if (C1.matcher(headerLines.get(currentLine)).find()) {
                        PairX = 2;
                        PairY = 2;
                    } else if (C.matcher(headerLines.get(currentLine)).find()) {
                        PairX = 4;
                        PairY = 4;
                    } else {
                        throw new HeaderException("Invalid Input");
                    }
                }
                meter = new Pair<Integer, Integer>(PairX, PairY);
            } else if (tempoPattern.matcher(headerLines.get(currentLine)).matches()) {
                Matcher numberMatcher = numberPattern.matcher(headerLines.get(currentLine));
                numberMatcher.find();
                tempo = Integer.parseInt(numberMatcher.group());

            } else if (composerPattern.matcher(headerLines.get(currentLine)).matches()) {

            } else if (voicePattern.matcher(headerLines.get(currentLine)).matches()) {
                String temp = headerLines.get(currentLine).substring(2);
                Matcher wordMatcher = wordPattern.matcher(temp);
                wordMatcher.find();
                if (voice.containsKey(wordMatcher.group())) {
                    throw new HeaderException("Invalid input.");
                }
                numVoices++;
                voice.put(wordMatcher.group(), numVoices);
            } else {
                throw new HeaderException("Invalid Input.");
            }
        }

        currentLine = headerLines.size() - 1;
        Pattern KPattern = Pattern.compile("K:\\s*([A-G][#|b]?[m]?)\\s*");
        Pattern KeyPattern = Pattern.compile("[A-G][#|b]?[m]?");
        Matcher KMatcher = KPattern.matcher(headerLines.get(currentLine));
        Matcher KeyMatcher = KeyPattern.matcher(headerLines.get(currentLine));
        if (!KMatcher.matches()) {
            throw new RuntimeException("Wrong input");
        } else {
            KeyMatcher.find();

            keySignature = KeySignature.KeySignatureToInt(KeyMatcher.group());
        }
        if (numVoices == 0) {
            numVoices = 1;
        }
    }

    public int getTempo() {
        return tempo;
    }

    public Pair<Integer, Integer> getMeter() {
        return new Pair<Integer, Integer>(meter.first, meter.second);
    }

    public Rational getDefaultNoteLength() {
        return defaultNoteLength.clone();
    }

    public int getVoiceIndex(String v) {
        if (voice.containsKey(v))
            return voice.get(v);
        else
            throw new HeaderException("Voice not found.");
    }

    public int getAccidental(char c) {
        if (c < 'A' || c > 'G')
            throw new HeaderException("Basenote out of bound.");
        return keySignature[c - 'A'];
    }

    public int[] getKeySignature(){
    	return keySignature.clone();
    }
    
    public int getNumVoices() {
        return numVoices;
    }
}
