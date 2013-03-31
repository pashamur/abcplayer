package player;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import sound.Pitch;
import sound.SequencePlayer;

public class ABCPlayer implements ABCmusic.Visitor<SequencePlayer>{
    // Our main player for the music
	private SequencePlayer player;
	private int ticksPerQuarterNote;
	private Header header;
	private int lastTick = 0;
	
	/**
	 * Construct a new ABCPlayer with the correct number of ticks per quarter note,
	 * beats per minute, and a valid parsed header
	 * 
	 * @param ticks Number of ticks per quarter note. Must be a non-negative integer
	 * @param bpm Number of beats (quarter notes) per minute. Must be non-negative integer
	 * @param head A parsed representation of the header
	 */
	public ABCPlayer(int ticks, int bpm, Header head){
		try {
			player = new SequencePlayer(ticks, bpm);
			ticksPerQuarterNote = ticks;
			header = head;
		} catch (MidiUnavailableException e) {
	        e.printStackTrace();
	    } catch (InvalidMidiDataException e) {
	        e.printStackTrace();
	    }
	}
	
	public SequencePlayer on(Music mu){
		// Process every voice in the music, one at a time 
        for (int i=0;i<mu.size;i++) {
            abcPlayer(mu.getVoice(i));
        }
        return player;
    }
    public SequencePlayer on(Voice v) {
    	// Process all the major sections in the voice one by one.
        for (int i=0;i<v.size;i++) {
            abcPlayer(v.getMajorSection(i));
        }
        // Reset tick after every voice
        lastTick = 0;
        return player;
    }
    public SequencePlayer on(MajorSection ms) {
        for (int i=0;i<ms.size;i++) {
            abcPlayer(ms.getSection(i));
        }
        return player;
    }
    public SequencePlayer on(Section sc) {
        for (int i=0;i<sc.sizeInMeasures;i++) {
            abcPlayer(sc.getMeasure(i));
        }
        return player;
    }
    public SequencePlayer on(Measure m) {
        for (int i=0;i<m.size;i++) {
            abcPlayer(m.getElements(i));
        }
        return player;
    }
    public SequencePlayer on(Chord c) {
    	int noteLengthInTicks = getNoteLengthInTicks(c);
    	    	
        for (int i=0;i<c.size;i++) {
        	Note note = c.getNote(i);
        	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
        	if (note.getHasAccidental())
        		p=p.transpose(note.getAccidental());
        	else
        		p=p.transpose(header.getAccidental(note.value));
        	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
        }
        lastTick = lastTick+noteLengthInTicks;
        return player;
    }
    
    public SequencePlayer on(Tuplet t) {
    	int noteLengthInTicks = getNoteLengthInTicks(t);
    	
        for (int i=0;i<t.size;i++){ 
        	ABCmusic elem = t.getElement(i);
        	if(elem instanceof Note)
        		processNoteWithinTuplet((Note)elem, noteLengthInTicks);
        	else if(elem instanceof Chord)
        		processChordWithinTuplet((Chord)elem, noteLengthInTicks);
        }
        return player;
    }
    
    public SequencePlayer on(Note n) {
    	Pitch p = new Pitch(n.value).transpose(n.octave*Pitch.OCTAVE);
        
    	if (n.getHasAccidental())
    	    p=p.transpose(n.getAccidental());
    	else 
            p=p.transpose(header.getAccidental(n.value));
    
    	int noteLengthInTicks = getNoteLengthInTicks(n);
    	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
    	lastTick = lastTick+noteLengthInTicks;
    		
        return player;
    }
    
    public SequencePlayer on(Rest r) {
    	// Increment the tick counter (without adding any notes) by the length of the rest.
    	lastTick = lastTick + getNoteLengthInTicks(r);
        
    	return player;
    }
    
    public SequencePlayer abcPlayer(ABCmusic e){
        return e.accept(this);
    }
    
    /**
     * Helper method that calculates the note length for a given ABCmusic element - either
     * a note, a tuplet, a rest or a chord
     * 
     * @param elem Either a note, a tuplet, a rest or a chord to calculate note length
     * @return an integer representing the length of a single note within elem
     */
    private int getNoteLengthInTicks(ABCmusic elem){
    	// First we calculate the noteLength, which is simply the unscaled length of a note times the default note length
    	Rational noteLength;
    	
    	if(elem instanceof Note)
    		noteLength = ((Note)elem).getLength().times(header.getL());
    	else if(elem instanceof Tuplet)
    		noteLength = ((Tuplet)elem).getNoteLength().times(header.getL());
    	else if(elem instanceof Chord)
    		noteLength = ((Chord)elem).getLength().times(header.getL());
    	else if(elem instanceof Rest)
    		noteLength = ((Rest)elem).getLength().times(header.getL());
    	else
    		throw new RuntimeException("Parameter to getNoteLengthInTicks must be one of [Note, Tuplet, Chord, Rest]");
    	
    	// Now we convert our noteLength into ticks by scaling it by 4*ticksPerQuarterNote (which is ticks / full note)
    	return (4*ticksPerQuarterNote)*noteLength.num/noteLength.den;
    }
    /**
     * Helper method used to process a note inside a tuplet
     * 
     * @param note The note to be processed (from the tuplet)
     * @param noteLengthInTicks Length of the note within the tuplet
     */
    private void processNoteWithinTuplet(Note note, int noteLengthInTicks){
    	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
    	if (note.getHasAccidental())
    	    p=p.transpose(note.getAccidental());
    	else 
            p=p.transpose(header.getAccidental(note.value));
    	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
    	lastTick = lastTick+noteLengthInTicks;
    }
    
    /**
     * Helper method used to process a chord inside a tuplet
     * 
     * @param chord The chord to be processed (from the tuplet)
     * @param noteLengthInTicks Length of a single note within the chord to be processed
     */
    private void processChordWithinTuplet(Chord chord, int noteLengthInTicks){
    	for (int i=0;i<chord.size;i++) {
        	Note note = chord.getNote(i);
        	Pitch p = new Pitch(note.value).transpose(note.octave*Pitch.OCTAVE);
        	if (note.getHasAccidental())
        		p=p.transpose(note.getAccidental());
        	else
        		p=p.transpose(header.getAccidental(note.value));
        	player.addNote(p.toMidiNote(), lastTick, noteLengthInTicks);
        }
        lastTick = lastTick+noteLengthInTicks;
    }

}