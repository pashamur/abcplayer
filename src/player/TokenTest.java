package player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TokenTest {
    @Test
    public void TokenTest_Note() {
        List<String> s=new ArrayList<String>();
        s.add("^^");
        s.add("a");
        s.add("''");
        s.add("");
        s.add("/");
        s.add("");
        Token tk=new Token(Token.Type.note,s);
        Note note=tk.getNote();
        assertEquals(note.value,'A');
        assertEquals(3,note.octave);
        assertEquals(note.getAccidental(),2);
        assertTrue(note.getHasAccidental());
        assertTrue(note.getLength().equals(new Rational(1,2)));
    }
}
