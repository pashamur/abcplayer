package player;

public class ABCmusicTicks implements ABCmusic.Visitor<Integer>{
	// Calculate ticks per quarter note
	private int ticksPerQuarterNote = 1;
	private Rational baseNoteLength;
	
	public ABCmusicTicks(Rational baseNoteLength){
		this.baseNoteLength = baseNoteLength;
	}
	
	public Integer on(Music mu){
		for (int i=0;i<mu.size;i++) {
			ABCMusicTicks(mu.getVoice(i));
        }
        return ticksPerQuarterNote;
    }
    public Integer on(Voice v) {
        for (int i=0;i<v.size;i++) {
        	ABCMusicTicks(v.getMajorSection(i));
        }
        return ticksPerQuarterNote;
    }
    public Integer on(MajorSection ms) {
        for (int i=0;i<ms.size;i++) {
        	ABCMusicTicks(ms.getSection(i));
        }
        return ticksPerQuarterNote;
    }
    public Integer on(Section sc) {
        for (int i=0;i<sc.sizeInMeasures;i++) {
        	ABCMusicTicks(sc.getMeasure(i));
        }
        return ticksPerQuarterNote;
    }
    public Integer on(Measure m) {
        for (int i=0;i<m.size;i++) {
            ABCMusicTicks(m.getElements(i));
        }
        return ticksPerQuarterNote;
    }
    public Integer on(Chord c) {
       	ticksPerQuarterNote = lcm(ticksPerQuarterNote, c.getLength().times(baseNoteLength).den);
        return ticksPerQuarterNote;
    }
    
    public Integer on(Tuplet t) {
        ticksPerQuarterNote = lcm(ticksPerQuarterNote, t.getNoteLength().times(baseNoteLength).den);
        return ticksPerQuarterNote;
    }
    
    public Integer on(Note n) {
    	ticksPerQuarterNote = lcm(ticksPerQuarterNote, n.getLength().times(baseNoteLength).den);
        return ticksPerQuarterNote;
    }
    public Integer on(Rest r) {
    	ticksPerQuarterNote = lcm(ticksPerQuarterNote, r.getLength().times(baseNoteLength).den);
        return ticksPerQuarterNote;
    }
    public Integer ABCMusicTicks(ABCmusic e){
        return e.accept(this);
    }
    
	// find the greatest common divisor of two numbers, a and b using Euclid's algorithm
	private int gcd(int a, int b){
		if(b == 0) return a;
		if(a>b)
			return gcd(b, a%b);
		else
			return gcd(a, b%a);
	}
	
	// Find the least common multiple of a & b
	private int lcm(int a, int b){
		return a * (b / gcd(a, b));
	}
}
