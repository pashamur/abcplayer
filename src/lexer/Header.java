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

    /**
     * initialize all fields of Header. Lex header and generate a list of field
     * (K, M...) and their respective strings. get rid of comments.
     * 
     * @param input
     *            A list of strings to be lexed
     * @throws RuntimeException
     *             if X, T, K does not appear at the right place.
     * @throws RuntimeException
     *             if syntax error within any one line.
     */
    public Header(List<String> input) {
        int i = 0;
        tempo = 100;
        defaultNoteLength = new Rational(1, 8);
        meter = new Pair<Integer, Integer>(4, 4);
        numVoices = 0;
        voice = new HashMap<String, Integer>();
        
        keySignature = new int[7];
        Pattern numberPattern = Pattern.compile("\\d+");
        Pattern wordPattern = Pattern.compile("[^\\s]+");
        Pattern XPattern = Pattern.compile("X:\\s*\\d+\\s*");
        Matcher XMatcher = XPattern.matcher(input.get(i));
        if (!XMatcher.matches()) {
            throw new RuntimeException("Wrong input");
        }
        i++;
        Pattern TPattern = Pattern.compile("T:.+");
        Matcher TMatcher = TPattern.matcher(input.get(i));
        if (!TMatcher.matches()) {
            throw new RuntimeException("Wrong input");
        }

        Pattern LPattern = Pattern.compile("L:\\s*\\d+\\s*/\\s*\\d+\\s*");
        Pattern MPattern = Pattern
                .compile("M:\\s*((\\d+\\s*/\\s*\\d+)|C|(C\\|))\\s*");
        Pattern QPattern = Pattern.compile("Q:\\s*\\d+\\s*");
        Pattern CPattern = Pattern.compile("C:.+");
        Pattern VPattern = Pattern.compile("V:\\s*[^\\s]+\\s*");

        for (i = 2; i < input.size() - 1; i++) {
            if (LPattern.matcher(input.get(i)).matches()) {
                Matcher numberMatcher = numberPattern.matcher(input.get(i));
                numberMatcher.find();
                int PairX = Integer.parseInt(numberMatcher.group());
                numberMatcher.find();
                int PairY = Integer.parseInt(numberMatcher.group());
                defaultNoteLength = new Rational(PairX, PairY);
            } else if (MPattern.matcher(input.get(i)).matches()) {
                int PairX;
                int PairY;
                Matcher numberMatcher = numberPattern.matcher(input.get(i));
                if (numberMatcher.find()) {
                    PairX = Integer.parseInt(numberMatcher.group());
                    numberMatcher.find();
                    PairY = Integer.parseInt(numberMatcher.group());

                } else {
                    Pattern C = Pattern.compile("C");
                    Pattern C1 = Pattern.compile("C\\|");
                    if (C1.matcher(input.get(i)).find()) {
                        PairX = 2;
                        PairY = 2;
                    } else if (C.matcher(input.get(i)).find()) {
                        PairX = 4;
                        PairY = 4;
                    } else {
                        throw new RuntimeException("Wrong Input");
                    }
                }
                meter = new Pair<Integer, Integer>(PairX, PairY);
            } else if (QPattern.matcher(input.get(i)).matches()) {
                Matcher numberMatcher = numberPattern.matcher(input.get(i));
                numberMatcher.find();
                tempo = Integer.parseInt(numberMatcher.group());

            } else if (CPattern.matcher(input.get(i)).matches()) {

            } else if (VPattern.matcher(input.get(i)).matches()) {
                String temp = input.get(i).substring(2);
                Matcher wordMatcher = wordPattern.matcher(temp);
                wordMatcher.find();
                numVoices++;

                voice.put(wordMatcher.group(), numVoices);
            } else {
                throw new RuntimeException("Wrong Input!");
            }
        }

        i = input.size() - 1;
        Pattern KPattern = Pattern.compile("K:\\s*([A-G][#|b]?[m]?)\\s*");
        Pattern KeyPattern = Pattern.compile("[A-G][#|b]?[m]?");
        Matcher KMatcher = KPattern.matcher(input.get(i));
        Matcher KeyMatcher = KeyPattern.matcher(input.get(i));
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
            throw new RuntimeException("Voice not found.");
    }

    public int getAccidental(char c) {
        if (c < 'A' || c > 'G')
            throw new RuntimeException("Basenote out of bound.");
        return keySignature[c - 'A'];
    }

    public int getNumVoices() {
        return numVoices;
    }
}
