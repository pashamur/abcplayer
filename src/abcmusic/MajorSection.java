package abcmusic;

import java.util.ArrayList;
import java.util.List;

import lexer.Token;

import player.Rational;


public class MajorSection implements ABCmusic {
    private List<Section> sections;
    private int state = 0;
    private boolean repeat = false;
    public final int size;
    public List<Rational> mList=new ArrayList<Rational>();
    
    public <R> R accept(Visitor<R> m) {
        return m.on(this);
    }

    // tk is non-empty; end with ':|' or no bar
    public MajorSection(List<Token> tk) {
        sections = new ArrayList<Section>();
        int ni = 0;
        int nf = 0;
        int nm = 0;
        int len = tk.size();
        if (len < 1)
            throw new RuntimeException("Empty major section.");
        Token current;
        while (ni < len) {
            while (nf < len) {
                current = tk.get(nf);
                if (current.type.equals(Token.Type.repeat_start) || current.type.equals(Token.Type.repeat_end)) break;
                if (current.type.equals(Token.Type.nth_repeat)) {
                    if (current.getValue() == 1) {
                        if (repeat) throw new RuntimeException("Duplicated 1st-repeat.");
                        if ((ni != nf) && !tk.get(nf - 1).type.equals(Token.Type.simple_bar))
                            throw new RuntimeException("Missing simple_bar before 1st_repeat");
                        repeat = true;
                        nm = nf;
                    } else throw new RuntimeException("Extra 2nd-repeat.");
                }
                nf++;
            }
            if (state == 0) {
                if (nf == len)
                    sections.add(new Section(tk.subList(ni, nf)));
                else if (tk.get(nf).type.equals(Token.Type.repeat_start)) {
                    if (ni != nf) sections.add(new Section(tk.subList(ni, nf)));
                    state = 1;
                } 
                else if (ni == nf) throw new RuntimeException("Unmatched repeat-start.");
                else nf = handleRepeat(tk, ni, nm, nf);
            } 
            else if (state == 1) {
                if (nf == len)
                    throw new RuntimeException("Unmatched repeat-start.");
                if (tk.get(nf).type.equals(Token.Type.repeat_start))
                    throw new RuntimeException("Nested repeat-start.");
                if (ni == nf)
                    throw new RuntimeException("Empty repeat section.");
                nf = handleRepeat(tk, ni, nm, nf);
            } 
            else if (state == 2) {
                if (nf == len)
                    sections.add(new Section(tk.subList(ni, nf)));
                else if (tk.get(nf).type.equals(Token.Type.repeat_end))
                    throw new RuntimeException("Nested repeat_end.");
                else {
                    if (ni==nf) ;
                    else if (!tk.get(nf-1).type.equals(Token.Type.simple_bar))
                        sections.add(new Section(tk.subList(ni, nf)));
                    else if (ni!=(nf-1))
                        sections.add(new Section(tk.subList(ni, nf-1)));
                    state = 1;
                }
            }
            ni = ++nf;
        }
        size=sections.size();
        for (int i=0;i<size;i++) mList.addAll(sections.get(i).mList);
    }

    /*
     * [Helper] Add the correct number of repeated sections, and modifiy the
     * state, repeat. * Return the new nf.
     * 
     * ni is the start of a repeat section. tk.get(ni).type is not repeat nf is
     * 
     * the first repeat-end following tk[ni]. tk.get(ni) is “[1” if repeat is
     * true.
     */
    private int handleRepeat(List<Token> tk, int ni, int nm, int nf) {
        int len = tk.size();
        if (!repeat) {
            Section temp = new Section(tk.subList(ni, nf));
            sections.add(temp);
            sections.add(temp);
            state = 2;
            return nf;
        } else {
            if (nf > (len - 2))
                throw new RuntimeException("Missing 2nd-repeat.");
            Token current = tk.get(nf + 1);
            if (current.type.equals(Token.Type.nth_repeat) && current.getValue() == 2) {
                if (!tk.get(nm-1).type.equals(Token.Type.simple_bar))
                    throw new RuntimeException("Missing simple-bar before 1st-repeat [1.");
                Section temp = new Section(tk.subList(ni, nm-1));
                sections.add(temp);
                sections.add(new Section(tk.subList(nm + 1, nf)));
                sections.add(temp);
                repeat = false;
                state = 2;
                return nf + 1;
            } else
                throw new RuntimeException("Missing 2nd-repeat.");
        }
    }
    public Section getSection(int i) {
        return sections.get(i);
    }
}
