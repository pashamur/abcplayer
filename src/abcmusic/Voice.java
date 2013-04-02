package abcmusic;

import java.util.ArrayList;
import java.util.List;
import lexer.Token;
import player.Rational;


public class Voice implements ABCmusic {
    private List<MajorSection> majorSections;
    public final int size;
    public List<Rational> mList=new ArrayList<Rational>();
    
    @SuppressWarnings("serial")
    private static class VoiceException extends RuntimeException {
        public VoiceException (String message) {
            super("TupletException: "+message);
        }
    }
    
    public <R> R accept(Visitor<R> v) {
        return v.on(this);
    }
    /**
     * Construct a Voice from tk
     * @param tk token list representing a voice
     * @throw VoiceException if tk is empty
     * @throw VoiceException if duplcate major-section bars or the last element in tk is not end-bar
     */
    public Voice(List<Token> tk) {
        int len=tk.size();
        if (len==0) throw new VoiceException("Empty voice.");
        Token last=tk.get(len-1);
        if ((!last.type.equals(Token.Type.simple_bar)) && (!last.type.equals(Token.Type.repeat_end)) && (!last.type.equals(Token.Type.major_section_end))) 
            throw new VoiceException("Missing bar at the end.");
        int ni=0;
        int nf=0;
        if (tk.get(ni).type.equals(Token.Type.major_section_start)) {
            ni++;
            nf++;
        }//ignore first major section start.
        majorSections=new ArrayList<MajorSection>();
        while (ni<len) {
            while (nf<len && !tk.get(nf).type.equals(Token.Type.major_section_end) && 
                    !tk.get(nf).type.equals(Token.Type.major_section_start)) ++nf;
            if (ni==nf) throw new VoiceException("Extra major-section bar.");
            else if ((nf==len) && !tk.get(nf-1).type.equals(Token.Type.repeat_end)) 
                majorSections.add(new MajorSection(tk.subList(ni,nf-1)));
                //if the last bar is repeat, keep it; otherwise get rid of the simple-bar at the end or all major-section-bar
            else majorSections.add(new MajorSection(tk.subList(ni,nf)));
            ni=++nf;
        }
        size=majorSections.size();
        for (int i=0;i<size;i++) mList.addAll(majorSections.get(i).mList);
    }
    
    public MajorSection getMajorSection(int i) {
        return majorSections.get(i);
    }
}
