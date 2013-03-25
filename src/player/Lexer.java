package player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private List<List<Token>> tk;

    // Due to generic type, can only use list of list instead of array of list.
    // generate array of token list from music strings. each list representing a
    // voice.
    private void convertLength(List<String> result, String s) {
        Pattern numberPattern = Pattern.compile("\\d+");
        Pattern dividePattern = Pattern.compile("/");
        Matcher matcher;
        Matcher matcher1;
        if ((matcher=numberPattern.matcher(s)).lookingAt()) {
            result.add(matcher.group());
            s = s.substring(matcher.end());
            if (dividePattern.matcher(s).find()) {
                result.add("/");
                if ((matcher1=numberPattern.matcher(s)).find()) {
                    result.add(matcher1.group());    
                }
                 else {
                     result.add("2");
                 }
            }
        } else {
            result.add("1");
            result.add("/");
            if ((matcher1=numberPattern.matcher(s)).find()) {
                result.add(matcher1.group());    
            }
             else {
                 result.add("2");
             }

        }

    }

    public Lexer(List<String> input, Header header) {
        tk = new ArrayList<List<Token>>();
        Pattern VPattern = Pattern.compile("V:\\s*[^\\s]+\\s*");
        Pattern wordPattern = Pattern.compile("[^\\s]+");

        Pattern basenotePattern = Pattern.compile("[a-gA-G]");
        Pattern numberPattern = Pattern.compile("\\d+");
        Pattern accidentalPattern = Pattern.compile("\\^\\^|__|=|\\^|_");
        Pattern barlinePattern = Pattern
                .compile("\\|\\||\\[\\||\\|\\]|\\|:|:\\||\\|");
        Pattern multinoteStartPattern = Pattern.compile("\\[");
        Pattern multinoteEndPattern = Pattern.compile("\\]");
        Pattern octavePattern = Pattern.compile("'+|,+");
        Pattern noteLengthPattern = Pattern.compile("(\\d*\\s*/\\s*\\d*)|(\\d+)");
        Pattern restPattern = Pattern.compile("z\\s*((\\d*\\s*/\\s*\\d*)|(\\d+))?");
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
        if (header.getNumVoices() > 1) {
            Matcher matcher = VPattern.matcher(input.get(i));
            if (matcher.matches()) {
                String temp=input.get(i).substring(2);
                Matcher wordMatcher = wordPattern.matcher(temp);
                wordMatcher.find();
                String voice = wordMatcher.group();
                k = header.getVoiceIndex(voice)-1;
            } else {
                throw new RuntimeException("No Voice");
            }
        }
        for (; i < input.size(); i++) {
            Matcher matcher = VPattern.matcher(input.get(i));
            Matcher matcher1;
            Matcher matcher2;
            if (matcher.matches()) {
                String temp=input.get(i).substring(2);
                Matcher wordMatcher = wordPattern.matcher(temp);
                wordMatcher.find();
                String voice = wordMatcher.group();
                k = header.getVoiceIndex(voice)-1;            
            } else {
               String substring = input.get(i);
                while (!substring.equals("")) {
                    if ((matcher = nthRepeatPattern.matcher(substring))
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k).add(new Token(Token.Type.nth_repeat, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = tupletSpecPattern.matcher(substring))
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k).add(new Token(Token.Type.tuplet_spec, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = restPattern.matcher(substring))
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add("z");
                        String rest= matcher.group();
                        if ((matcher1 = noteLengthPattern.matcher(rest))
                                .find()) {
                            convertLength(text, matcher1.group());
                        }
                        tk.get(k).add(new Token(Token.Type.rest, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = notePattern.matcher(substring))
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        String note = matcher.group();
                        if ((matcher1 = accidentalPattern.matcher(note)).find()) {
                            text.add(matcher1.group());
                            
                        } else {
                            text.add("");
                        }
                        (matcher1 = basenotePattern.matcher(note)).find();
                        text.add(matcher1.group());
                        if ((matcher1 = octavePattern.matcher(note)).find()) {
                            text.add(matcher1.group());
                           
                        }else {
                            text.add("");
                        }
                        if ((matcher1 = noteLengthPattern.matcher(note)).find()) {
                            convertLength(text, matcher1.group());
                        }                        
                        tk.get(k).add(new Token(Token.Type.note, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher = barlinePattern.matcher(substring))
                            .lookingAt()) {
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

                        } else {
                            throw new RuntimeException("Wrong input");
                        }

                        substring = substring.substring(matcher.end());

                    } else if ((matcher=multinoteStartPattern.matcher(substring))
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k).add(
                                new Token(Token.Type.multinote_start, text));
                        substring = substring.substring(matcher.end());

                    } else if ((matcher=multinoteEndPattern.matcher(substring))
                            .lookingAt()) {
                        List<String> text = new ArrayList<String>();
                        text.add(matcher.group());
                        tk.get(k)
                                .add(new Token(Token.Type.multinote_end, text));

                        substring = substring.substring(matcher.end());

                    } else if ((matcher=spacePattern.matcher(substring)).lookingAt()) {
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
