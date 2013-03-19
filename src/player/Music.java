package player;

import java.util.ArrayList;
import java.util.List;

public class Music implements ABCmusic {
    private List<ABCmusic> voices;
    public <R> R accept(Visitor<R> mu) {
        return mu.on(this);
    }
    public Music(List<List<Token>> tk) {
        int len=tk.size();
        voices=new ArrayList<ABCmusic>();
        for (int i=0;i<len;i++) voices.add(new Voice(tk.get(i)));
    }
}
