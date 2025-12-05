package battleships.strategy;

public enum Adjacent {
    ABOVE(0,-1), RIGHT(1,0), BELOW(0,1), LEFT(-1,0);

    public final int dx;
    public final int dy;

    Adjacent(final int dx, final int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Adjacent opposite() {
        switch(this) {
            case ABOVE:
                return BELOW;
            case BELOW:
                return ABOVE;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        throw new Error();
    }

    public Adjacent next() {
        switch(this) {
            case ABOVE:
                return RIGHT;
            case BELOW:
                return LEFT;
            case LEFT:
                return ABOVE;
            case RIGHT:
                return BELOW;
        }
        throw new Error();
    }

    public static Adjacent random() {
        return values()[(int) (Math.random() * 4)];
    }
}
