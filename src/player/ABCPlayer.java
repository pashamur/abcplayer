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
	private int[] localAccidental = {0,0,0,0,0,0,0,0};
	private int[] zeroAccidental = {0,0,0,0,0,0,0,0};
	
	public ABCPlayer(int ticks, int bpm, Header h){
		try {
			player = new SequencePlayer(ticks, bpm);
			ticksPerQuarterNote = ticks;
			this.h = h;
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
        // Reset tick after every voice
        lastTick = 0;
        return player;
    }
    public SequencePlayer on(MajorSection ms) {
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
        localAccidental = zeroAccidental.clone();
        return player;
    }
    public SequencePlayer on(Chord c) {
    	Rational noteLength = c.getLength().times(h.getL());
    	int noteLengthInTicks = 4*noteLength.num*ticksPerQuarterNote/noteLength.den;
    	    	
        for (int i=0;i<c.size;i++) {
        	Note note = c.getNote(i);
        	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
        	if (note.getHasAccidental()){
        		p.transpose(note.getAccidental());
        		localAccidental[note.value-'A'] = note.getAccidental();
        	}
        	else if(localAccidental[note.value-'A'] != 0){
        		p.transpose(localAccidental[note.value-'A']);
        	}
        	else{
        		p.transpose(h.getAccidental(note.value));
        	}
        	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
        }
        lastTick = lastTick+noteLengthInTicks;
        return player;
    }
    public SequencePlayer on(Tuplet t) {
    	Rational noteLength = t.getNoteLength().times(h.getL());
    	int noteLengthInTicks = 4*noteLength.num*ticksPerQuarterNote/noteLength.den;
    	
        for (int i=0;i<t.size;i++){ 
        	Note note = t.getNote(i);
        	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
        	if (note.getHasAccidental()){
        		p.transpose(note.getAccidental());
        		localAccidental[note.value-'A'] = note.getAccidental();
        	}
        	else if(localAccidental[note.value-'A'] != 0){
        		p.transpose(localAccidental[note.value-'A']);
        	}
        	else{
        		p.transpose(h.getAccidental(note.value));
        	}
        	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
        	lastTick = lastTick+noteLengthInTicks;
        }
        return player;
    }
    public SequencePlayer on(Note n) {
    	Pitch p = new Pitch(n.value).transpose(n.octave*Pitch.OCTAVE);
        
    	if (n.getHasAccidental()){
    		p.transpose(n.getAccidental());
    		localAccidental[n.value-'A'] = n.getAccidental();
    	}
    	else if(localAccidental[n.value-'A'] != 0){
    		p.transpose(localAccidental[n.value-'A']);
    	}
    	else{
    		p.transpose(h.getAccidental(n.value));
    	}
    	Rational noteLength = n.getLength().times(h.getL());
    	int noteLengthInTicks = 4*noteLength.num*ticksPerQuarterNote/noteLength.den;
    	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
    	lastTick = lastTick+noteLengthInTicks;
    		
        return player;
    }
    public SequencePlayer on(Rest r) {
    	// Increment the tick counter (without adding any notes) by the length of the rest.
    	Rational restLength = r.getLength().times(h.getL());
    	int restLengthInTicks = 4*restLength.num*ticksPerQuarterNote/restLength.den;
    	lastTick = lastTick + restLengthInTicks;
        return player;
    }
    public SequencePlayer ABCPlayer(ABCmusic e){
        return e.accept(this);
    }
}
