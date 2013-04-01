package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {
    private List<List<Token>> tk;

    /**
     * Convert noteLength to a list of strings which can be understood by the token
     * 
     * @param result
     *            , mutable list of Strings, which can be understood by Token if
     *            result.size()=1, first element represents an integer. if
     *            result.size()=3, first element represents an valid, nonzero
     *            numerator, or an empty string which be default means 1; second
     *            element is "/"; third element is a valid, nonzero denominator,
     *            or an empty string which by default means 2;
     * @param noteLength
     *            the string of notelength to be converted such as "/" "1/2" "2"
     *            "2 /4" "/ 4"
     */
    private void convertLength(List<String> result, String noteLength) {
        Pattern numberPattern = Pattern.compile("\\d+");
        Pattern dividePattern = Pattern.compile("/");
        Matcher matcher;
        Matcher matcher1;
        if ((matcher = numberPattern.matcher(noteLength)).lookingAt()) { // if starting
                                                                // with number
            result.add(matcher.group());
            noteLength = noteLength.substring(matcher.end());
            if (dividePattern.matcher(noteLength).find()) { // if contains "/"
                result.add("/");
                if ((matcher1 = numberPattern.matcher(noteLength)).find()) {
                    result.add(matcher1.group()); // if contains another number
                } else {
                    result.add("2");
                }
            }
        } else {
            result.add("1");
            result.add("/");
            if ((matcher1 = numberPattern.matcher(noteLength)).find()) {
                result.add(matcher1.group());
            } else {
                result.add("2");
            }

        }

    }

    /**
     * initialize the Lexer. Lex the music part and generate a list of list of
     * Tokens Each list of tokens represents a voice Tokens include simple_bar
     * --- {"|"} repeat_start --- {"|:"} repeat_end --- {":|"}
     * major_section_start --- {"[|"} major_section_stop --- {"||"} or {"|]"}
     * multinote_start --- {"["} multinote_end --- {"]"} tuplet_spec --- {"(2"}
     * or {"(3"} or {"(4"} nth_repeat --- {"[1"} or {"[2"} rest ---
     * {"z"(,"[0-9]*"(,"/","[0-9]*"))} () means optional note ---
     * {"^^|^|=|_|__","[a-gA-G]","[']*|[,]*"(,"[0-9]*"(,"/","[0-9]*"))}
     * 
     * @param input
     *            list of Strings to be lexed
     * @param header
     *            the header which provides useful information
     * @throws RuntimeException
     *             if syntax error within any one line.
     */
    public Lexer(List<String> input, Header header) {
        tk = new ArrayList<List<Token>>();
        Pattern VPattern = Pattern.compile("V:\\s*[^\\s]+\\s*");
        Pattern wordPattern = Pattern.compile("[^\\s]+");

        Pattern basenotePattern = Pattern.compile("[a-gA-G]");
        Pattern accidentalPattern = Pattern.compile("\\^\\^|__|=|\\^|_");
        Pattern barlinePattern = Pattern
                .compile("\\|\\|:|\\|\\||\\[\\||\\|\\]|\\|:|:\\||\\|");
        Pattern multinoteStartPattern = Pattern.compile("\\[");
        Pattern multinoteEndPattern = Pattern.compile("\\]");
        Pattern octavePattern = Pattern.compile("'+|,+");
        Pattern noteLengthPattern = Pattern
                .compile("(\\d*\\s*/\\s*\\d*)|(\\d+)");
        Pattern restPattern = Pattern
                .compile("z\\s*((\\d*\\s*/\\s*\\d*)|(\\d+))?");
        Pattern tupletSpecPattern = Pattern.compile("\\([234]");
        Pattern nthRepeatPattern = Pattern.compile("\\[[12]");
        Pattern spacePattern = Pattern.compile("\\s+");
        Pattern notePattern = Pattern
                .compile("(\\^\\^|__|=|\\^|_)?\\s*([a-gA-G])\\s*(,+|'+)?\\s*((\\d*\\s*/\\s*\\d*)|(\\d+))?");
        int i = 0; // the index of the line(input)
        for (int k = 0; k < header.getNumVoices(); k++) {
            tk.add(new ArrayList<Token>());
        }
        int k = 0; // the index of current voice
        if (header.getNumVoices() > 1) { // find the current voice, if there are
                                         // many voices
            Matcher matcher = VPattern.matcher(input.get(i));
            if (matcher.matches()) {
                String temp = input.get(i).substring(2);
                Matcher wordMatcher = wordPattern.matcher(temp);
                wordMatcher.find();
                String voice = wordMatcher.group();
                k = header.getVoiceIndex(voice) - 1;
            } else {
                throw new RuntimeException("No Voice");
            }
        }
        // lex line by line
        for (; i < input.size(); i++) {
            Matcher matcher = VPattern.matcher(input.get(i));
            Matcher matcher1;
            if (matcher.matches()) {
                // if the line is a voice
                String temp = input.get(i).substring(2);
                Matcher wordMatcher = wordPattern.matcher(temp);
                wordMatcher.find();
                String voice = wordMatcher.group();
                k = header.getVoiceIndex(voice) - 1;
            } else {
                String substring = input.get(i);
                while (!substring.equals("")) {
                    // substring is the current string to lex, after every loop,
                    // the part which has been lexed is deleted
                    if ((matcher = nthRepeatPattern.matcher(substring))
                            .lookingAt()) {
                        // if it is a [1 or [2
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k).add(new Token(Token.Type.nth_repeat, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = tupletSpecPattern.matcher(substring))
                    // if it is a (2 (3 or (4
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k).add(new Token(Token.Type.tuplet_spec, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = restPattern.matcher(substring))
                    // a rest
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add("z");
                        String rest = matcher.group();
                        if ((matcher1 = noteLengthPattern.matcher(rest)).find()) {
                            convertLength(text, matcher1.group());
                        }
                        tk.get(k).add(new Token(Token.Type.rest, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = notePattern.matcher(substring))
                            .lookingAt()) {
                        // a note
                        List<String> noteParts = new ArrayList<String>();
                        String note = matcher.group();
                        if ((matcher1 = accidentalPattern.matcher(note)).find()) {
                            noteParts.add(matcher1.group());
                            //if an accidental is found
                        } else {
                            noteParts.add("");
                        }
                        (matcher1 = basenotePattern.matcher(note)).find();
                        noteParts.add(matcher1.group());
                        if ((matcher1 = octavePattern.matcher(note)).find()) {
                            noteParts.add(matcher1.group());
                            //if an octave is found
                        } else {
                            noteParts.add("");
                        }
                        if ((matcher1 = noteLengthPattern.matcher(note)).find()) {
                            convertLength(noteParts, matcher1.group());
                            // if note length is found
                        }
                        tk.get(k).add(new Token(Token.Type.note, noteParts));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = barlinePattern.matcher(substring))
                            .lookingAt()) {
                        //bar line
                        List<String> text = new ArrayList<String>();
                        String temp = matcher.group();
                        text.add(temp);
                        if (temp.equals("[|")) {
                            tk.get(k).add(
                                    new Token(Token.Type.major_section_start,
                                            text));

                        } else if (temp.equals("|]")) {
                            tk.get(k).add(
                                    new Token(Token.Type.major_section_end,
                                            text));

                        } else if (temp.equals("|:")) {
                            tk.get(k).add(
                                    new Token(Token.Type.repeat_start, text));

                        } else if (temp.equals(":|")) {
                            tk.get(k).add(
                                    new Token(Token.Type.repeat_end, text));
                        } else if (temp.equals("||")) {
                            tk.get(k).add(
                                    new Token(Token.Type.major_section_end,
                                            text));

                        } else if (temp.equals("|")) {
                            tk.get(k).add(
                                    new Token(Token.Type.simple_bar, text));

                        } 
                        else if (temp.equals("||:")) {
                            text = new ArrayList<String>();
                            text.add("|");
                            tk.get(k).add(
                                    new Token(Token.Type.simple_bar, text));
                            text = new ArrayList<String>();
                            text.add("|:");                            
                            tk.get(k).add(
                                    new Token(Token.Type.repeat_start, text));

                        } 
                        else {
                            throw new RuntimeException("Wrong input");
                        }

                        substring = substring.substring(matcher.end());

                    } else if ((matcher = multinoteStartPattern
                            .matcher(substring)).lookingAt()) {
                        // [ is found
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k).add(
                                new Token(Token.Type.multinote_start, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = multinoteEndPattern
                            .matcher(substring)).lookingAt()) {
                        // ] is found
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k)
                                .add(new Token(Token.Type.multinote_end, text));

                        substring = substring.substring(matcher.end());

                    } else if ((matcher = spacePattern.matcher(substring))
                            .lookingAt()) {
                        //space
                        substring = substring.substring(matcher.end());
                    } else {
                        throw new RuntimeException("Wrong input");
                    }

                }
            }
        }
    }

    // number of voices
    public int getLength() {
        return tk.size();
    }

    // get a clone of ith token list. i must be in [0,getLength()-1]
    public List<Token> getTokens(int i) {
        return new ArrayList<Token>(tk.get(i));
    }
}
