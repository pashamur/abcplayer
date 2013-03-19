package player;

public class Rest implements ABCmusic {
    private final Rational length;
    public <R> R accept(Visitor<R> r) {
        return r.on(this);
    }
    public Rest(Rational l) {
        length=l;
    }
    @Override
    public Rest clone() {
        return new Rest(length);
    }
    public Rational getLength() {
        return length.clone();
    }
}
