package battleships;

import java.awt.geom.Rectangle2D;

public class Ship {
    private String name;
    private String code;

    private int hits;

    private int squareCount;
    private int x;
    private int y;
    private Boolean horizontal=Boolean.FALSE;

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

    public void rotate() {
        this.horizontal = !this.horizontal;
    }

    public void addToBoard(final Board board) {
        if (this.horizontal) {
            for(int x = 0; x < this.squareCount; x++) {
                board.getSquare(x + this.x, this.y).setShip(this);
            }
        } else {
            for(int y = 0; y < this.squareCount; y++) {
                board.getSquare(this.x, y + this.y).setShip(this);
            }
        }
    }
    public int getWidth() {
        if (this.horizontal) {
            return this.squareCount;
        } else {
            return 1;
        }
    }

    public int getHeight() {
        if (this.horizontal) {
            return 1;
        } else {
            return this.squareCount;
        }
    }

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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (this.horizontal) {
            for (int x = 0; x < this.squareCount; x++) {
                builder.append("O");
            }
            builder.append("\n");
        } else {
            for (int y = 0; y < this.squareCount; y++) {
                builder.append("O\n");
            }
        }
        return builder.toString();
    }




}


