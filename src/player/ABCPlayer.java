package player;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import sound.Pitch;
import sound.SequencePlayer;

public class ABCPlayer implements ABCmusic.Visitor<SequencePlayer>{
    // Our main player for the music
	private SequencePlayer player;
	private int ticksPerQuarterNote;
	private Header h;
	private int lastTick = 0;
	
	public ABCPlayer(int ticks, int bpm, Header h){
		try {
			player = new SequencePlayer(ticks, bpm);
			ticksPerQuarterNote = ticks;
		} catch (MidiUnavailableException e) {
	        e.printStackTrace();
	    } catch (InvalidMidiDataException e) {
	        e.printStackTrace();
	    }
	}
	
	public SequencePlayer on(Music mu){
        for (int i=0;i<mu.size;i++) {
            ABCPlayer(mu.getVoice(i));
        }
        return player;
    }
    public SequencePlayer on(Voice v) {
        for (int i=0;i<v.size;i++) {
            ABCPlayer(v.getMajorSection(i));
        }
        return player;
    }
    public SequencePlayer on(MajorSection ms) {
        StringBuilder s=new StringBuilder();
        for (int i=0;i<ms.size;i++) {
            ABCPlayer(ms.getSection(i));
        }
        return player;
    }
    public SequencePlayer on(Section sc) {
        for (int i=0;i<sc.size;i++) {
            ABCPlayer(sc.getMeasure(i));
        }
        return player;
    }
    public SequencePlayer on(Measure m) {
        for (int i=0;i<m.size;i++) {
            ABCPlayer(m.getElements(i));
        }
        return player;
    }
    public SequencePlayer on(Chord c) {
    	int noteLengthInTicks = c.getLength().num*ticksPerQuarterNote/c.getLength().den;
    	
        for (int i=0;i<c.size;i++) {
        	Note note = c.getNote(i);
        	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
        	if (note.getHasAccidental()) p.transpose(note.getAccidental());
        	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
        }
        lastTick = lastTick+noteLengthInTicks;
        return player;
    }
    public SequencePlayer on(Tuplet t) {
    	int noteLengthInTicks = t.getNoteLength().num*ticksPerQuarterNote/t.getNoteLength().den;
    	
        for (int i=0;i<t.size;i++){ 
        	Note note = t.getNote(i);
        	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
        	if (note.getHasAccidental()) p.transpose(note.getAccidental());
        	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
        	lastTick = lastTick+noteLengthInTicks;
        }
        return player;
    }
    public SequencePlayer on(Note n) {
    	Pitch p = new Pitch(n.value).transpose(n.octave*Pitch.OCTAVE);
        
    	if (n.getHasAccidental()) p.transpose(n.getAccidental());
        
    	int noteLengthInTicks = (ticksPerQuarterNote*n.getLength().num)/n.getLength().den;

    	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
    	lastTick = lastTick+noteLengthInTicks;
    		
        return player;
    }
    public SequencePlayer on(Rest r) {
    	// Increment the tick counter (without adding any notes) by the length of the rest.
    	lastTick = lastTick + r.getLength().num*ticksPerQuarterNote/r.getLength().den;
        return player;
    }
    public SequencePlayer ABCPlayer(ABCmusic e){
        return e.accept(this);
    }
}
