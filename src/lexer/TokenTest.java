package lexer;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import player.Rational;

import abcmusic.Note;

public class TokenTest {
    @Test
    public void TokenTest_Note() {
        List<String> s= Arrays.asList("^^", "a", "''", "", "/", "");
        Token tk=new Token(Token.Type.note,s);
        Note note=tk.getNote();
        assertEquals(note.value,'A');
        assertEquals(3,note.octave);
        assertEquals(note.getAccidental(),2);
        assertTrue(note.getHasAccidental());
        assertTrue(note.getLength().equals(new Rational(1,2)));
    }
}
