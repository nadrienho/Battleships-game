package battleships;

import java.awt.geom.Rectangle2D;

public abstract class Ship {
    protected String name;
    protected String code;

    private int hits;

    protected int squareCount;
    protected int x;
    protected int y;

    public Ship(String name, String code, int squareCount) {
        this.name = name;
        this.code = code;
        this.squareCount = squareCount;
    }

    public String getName() {

        return name;
    }

    public String getCode() {

        return code;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public abstract void rotate();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void addToBoard(final Board board);

    public boolean overlap(final Ship other) {
        final Rectangle2D rectThis = new Rectangle2D.Double(this.x, this.y, this.getWidth(), this.getHeight());
        final Rectangle2D rectOther = new Rectangle2D.Double(other.x, other.y, other.getWidth(), other.getHeight());
        final Rectangle2D intersection = rectThis.createIntersection(rectOther);
        return (intersection.getWidth() >= 0) && (intersection.getHeight() >= 0);
    }

    public boolean incrementHitCount() {
        if (this.hits < this.squareCount) {
            this.hits++;
        }
        return (this.hits == this.squareCount);
    }

    public boolean isSunk() {
        return (this.hits == this.squareCount);
    }

}


