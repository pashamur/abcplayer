package player;

public interface ABCmusic {
    public interface Visitor<R> {
        public R on(Music mu);
        public R on(Voice v);
        public R on(MajorSection ms);
        public R on(Section s);
        public R on(Measure m);
        public R on(Note n);
        public R on(Rest r);
        public R on(Chord c);
        public R on(Tuplet t);
    }

    public <R> R accept(Visitor<R> v);
}
