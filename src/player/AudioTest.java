package player;

import java.io.IOException;

import org.junit.Test;

/** Test ABCplay by playing music
 * @category no_didit
 */
public class AudioTest {
    // two voices, one using repeat and the other not, should coinside.
    @Test
    public void ABCPlayerTest_Repeat() {
        try {
            Main.play("sample_abc/testRepeat6_paddy.abc");
        } catch (IOException e) {
            System.out.println("File exceptions.");
        }
    }
    // two voices, one use tuplet and the other do (but note length should match)
    @Test
    public void ABCPlayerTest_Tuplet() {
        try {
            Main.play("sample_abc/testTuplet0_fur_elise.abc");
        } catch (IOException e) {
            System.out.println("File exceptions.");
        }
    }
    // chord within tuplet
    @Test
    public void ABCPlayerTest_TupletofChord() {
        try {
            Main.play("sample_abc/testTuplet1_fur_elise.abc");
        } catch (IOException e) {
            System.out.println("File exceptions.");
        }
    }
}
