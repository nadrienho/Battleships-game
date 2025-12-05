package battleships;
import java.util.*;

public class Board {
    private int width;
    private int height;
    private Fleet fleet;

    private Square[][] board;

//keeping trackm of ships on the board
    private final List<Ship> ships = new ArrayList<>();

    public Board(int height, int width) {
        this.height = height;
        this.width = width;


        this.board = new Square[height][width];
        for (int i = 0; i < this.board.length; i++) {
            Square[] row = this.board[i];
            for (int j = 0; j < row.length; j++) {
                row[j] = new Square();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public Square getSquare(int x, int y) {
        return board[y][x ];
    }
    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < this.width && y < this.height;
    }

    /**
     * First attempt set up method - hard code a few ships
     * This method will need reworking later.
     */
//    public void setup() {
//        // a battleship
//        Ship s = new Ship("battleship", "B");
//        getSquare(1,1).setShip(s);
//        getSquare(2,1).setShip(s);
//        getSquare(3,1).setShip(s);
//        getSquare(4,1).setShip(s);
//        getSquare(5,1).setShip(s);
//        // a destroyer
//        s = new Ship("destroyer", "D");
//        getSquare(7,4).setShip(s);
//        getSquare(7,5).setShip(s);
//        getSquare(7,6).setShip(s);
//        getSquare(7,7).setShip(s);
//        // a submarine
//        s = new Ship("submarine", "S");
//        getSquare(3,3).setShip(s);
//        getSquare(4,3).setShip(s);
//        getSquare(5,3).setShip(s);
//    }

    public void setUp(Fleet fleet) {
        this.fleet = fleet;
        for(Ship s : fleet.getShips()) {
            placeShip(s);
        }
    }

    public Fleet getFleet() {
        return this.fleet;
    }

    public void placeShip(Ship ship) throws FailedToPlaceShipException {
        Random random = new Random();
        final int BREAK_THRESHOLD = 1000;

        // rotate the ship a random number of times
        final int rotations = random.nextInt(4);
        for(int i = 0; i < rotations; i++) {
            ship.rotate();
        }
        // try placing the ship in random locations that do not
        // clash with ships already placed on the board
        int breakCount = 0;

        boolean collision = true;
        while (collision) {
            ship.rotate();

            final int x = random.nextInt(this.width - (ship.getWidth() - 1));
            final int y = random.nextInt(this.height - (ship.getHeight() - 1));
            ship.setLocation(x, y);

            collision = false;
            for (final Ship s : this.ships) {
                if (s.overlap(ship)) {
                    collision = true;
                    break; // from the checking ship overlap loop
                }
            }
            breakCount++;
            if (breakCount > BREAK_THRESHOLD) {

                // wipe the board
                for (int i = 0; i < this.board.length; i++) {
                    Square[] row = this.board[i];
                    for (int j = 0; j < row.length; j++) {
                        row[j].setShip(null);
                    }
                }
                // and reset and discard all ships
                for(Ship s : this.ships) {
                    s.setLocation(0,0);
                }
                this.ships.clear();

                throw new FailedToPlaceShipException();
            }
        }
//        if (collision) {
//            throw new FailedToPlaceShipException();
//        }

        ship.addToBoard(this);
        this.ships.add(ship);
    }

    /**
     * Bombs the given square and returns an Outcome object that tells if any ship
     * was hit, and if so if it was sunk, and if so whether that ends the game or not.
     *
     * @param x
     * @param y
     * @return
     */
    public Outcome dropBomb(final int x, final int y) {
        Square square = getSquare(x, y);
        if (!square.isTried()) {
            square.setTried();
            Ship sunkShip = null;
            boolean gameWon = false;
            if (square.isHit()) {
                if (square.getShip().isSunk()) {
                    sunkShip = square.getShip();
                    gameWon = true;
                    for(Ship ship : this.ships) {
                        if (!ship.isSunk()) {
                            gameWon = false;
                            break;
                        }
                    }
                }
            }
            return new Outcome(x, y, square.isHit(), sunkShip, gameWon);
        } else {
            // this is a wasted turn - perhaps an exception would be a better idea?
            throw new IllegalStateException("Square already played!");
        }
    }

    public String[] toStringArray(final boolean showShips)  {
        final String[] array = new String[this.height];
        for(int y = 0; y < this.height; y++) {
            final StringBuilder builder = new StringBuilder(this.width);
            for(int x = 0; x < this.width; x++) {
                Square square = getSquare(x,y);
                builder.append(square.getCodeCharacter(showShips));
            }
            array[y] = builder.toString();
        }
        return array;
    }

    @Override
    public String toString()  {
        final String[] array = toStringArray(true);
        final StringBuilder builder = new StringBuilder();
        for(int y = 0; y < this.height; y++) {
            builder.append(array[y]);
            builder.append("\n");
        }
        return builder.toString();
    }


}
