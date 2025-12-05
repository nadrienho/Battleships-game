package battleships.strategy;

import battleships.*;

import java.util.*;

public class BestStrategy implements ComputerPlayerStrategy {

    private int boardWidth;
    private int boardHeight;
    /** A map of ship names to number of them e.g aeroplane: 4 */
    private TreeMap<String,Integer> fleetMap = new TreeMap<>();
    /**
     * Initially, every possible location of all ships in all orientations.
     * These are removed when miss overlaps them, or they are adjacent to a
     * hit and so are no longer valid, and when the named involved ship is sunk.
     */
    private List<ShipLocation> allLocations;

    public BestStrategy(Board board) {
        this.boardWidth = board.getWidth();
        this.boardHeight = board.getHeight();
        Fleet fleet = board.getFleet().clone();
        for(Ship s : fleet.getShips()) {
            this.fleetMap.put(s.getName(), this.fleetMap.getOrDefault(s.getName(), 0) + 1);
        }
        List<ShipOrientation> allOrientations = allPossible(fleet, this.fleetMap);
        this.allLocations = new ArrayList<ShipLocation>();
        for(ShipOrientation so : allOrientations) {
            for(int x = 0; x < boardWidth; x++) {
                for(int y = 0; y < boardHeight; y++) {
                    if (so.getWidth() + x <= boardWidth && so.getHeight() + y <= boardHeight) {
                        this.allLocations.add(new ShipLocation(x, y, so));
                    }
                }
            }
        }
    }
    public int[] chooseTarget(Board opponentBoard, Board ownBoard){
        int[][] counts = new int[opponentBoard.getHeight()][opponentBoard.getWidth()];
        int best = 0;
        // scan every square on the board and compute a score for it in counts[][]
        for (int y = 0; y < counts.length; y++) {
            int[] row = counts[y];
            for (int x = 0; x < row.length; x++) {
                // for each square look at every possible remaining ship location
                for(ShipLocation sl : this.allLocations) {
                    // if the square has been tried already ignore it (it will score zero),
                    // otherwise if the square would be a hit on this ship location increase its score
                    if (!opponentBoard.getSquare(x, y).isTried() && sl.shipCovers(x, y)) {
                        int increment = 1;
                        // the basic score increment is 1, but if this ship location already
                        // has other hits in it, add an extra 100 for each hit; in the end
                        // each square will have a score that reflects how many possible ship
                        // locations it hits, but with much greater weight to all ship locations
                        // that are in the set of possible locations in the process of being destroyed
                        for(int w = sl.x; w < sl.x + sl.orientation.getWidth(); w++) {
                            for(int z = sl.y; z < sl.y + sl.orientation.getHeight(); z++) {
                                if (sl.shipCovers(w, z) && opponentBoard.getSquare(w, z).isHit()) {
                                    increment += 100;
                                }
                            }
                        }

                        counts[y][x] += increment;
                        if (counts[y][x] > best) {
                            best = counts[y][x];
                        }
                    }
                }
            }
        }
        System.out.println("Best: " + best);
        // choose a square with the best chance of being a hit
        List<int[]> squares = new ArrayList<>();
        for (int y = 0; y < counts.length; y++) {
            int[] row = counts[y];
            for (int x = 0; x < row.length; x++) {
                System.out.print(row[x] + "\t");
                if (row[x] == best) {
                    squares.add(new int[] { x, y });
                }
            }
            System.out.println();
        }
        return squares.get((int)(Math.random() * squares.size()));
    }

    public void resultOfMove( Outcome outcome) {
        if (!outcome.hit()) {
            // eliminate all positions that would have had a hit
            this.allLocations.removeIf(sl -> sl.shipCovers(outcome.getX(), outcome.getY()));
        } else {
            // eliminate all positions that would have been a miss,
            // but are adjacent to this hit - a ship would not be allowed there
            this.allLocations.removeIf(sl ->
                    !sl.shipCovers(outcome.getX(), outcome.getY()) &&
                            sl.isAdjacent(outcome.getX(), outcome.getY())
            );
            Ship s = outcome.sunk();
            if (s != null) {
                ShipLocation sunkLocation = new ShipLocation(s.getX(), s.getY(), createShipOrientation(s, 0));
                // eliminate all ShipLocations that overlap with the sunk ship
                // these will be locations which have also received hits during the sinking
                this.allLocations.removeIf(sl -> sl.overlaps(sunkLocation));
                this.fleetMap.put(s.getName(), this.fleetMap.get(s.getName()) - 1);
                // remove from all locations every occurrence of the sunk ship (e.g submarine1)
                ShipOrientation toRemove = createShipOrientation(s, this.fleetMap.get(s.getName()));
                this.allLocations.removeIf(sl -> sl.orientation.getName().equals(toRemove.getName()));
            }
        }
    }


    private ShipOrientation createShipOrientation(Ship ship, int shipNumber) {
        int[][] pattern = new int[ship.getHeight()][ship.getWidth()];
        Board board = new Board(this.boardHeight, this.boardWidth);
        ship.addToBoard(board);
        for(int x = 0; x < ship.getWidth(); x++) {
            for(int y = 0; y < ship.getHeight(); y++) {
                pattern[y][x] = board.getSquare(ship.getX() + x, ship.getY() + y).hasShip() ? 1 : 0;
            }
        }
        return new ShipOrientation(ship.getName() + shipNumber, pattern);
    }

    private Set<ShipOrientation> createAllOrientations(Ship ship, int shipNumber) {
        HashSet<ShipOrientation> set = new HashSet<>();
        for(int i = 0; i < 4; i++) {
            set.add(createShipOrientation(ship, shipNumber));
            ship.rotate();
        }
        return set;
    }

    private List<ShipOrientation> allPossible(Fleet fleet, TreeMap<String,Integer> fleetMap) {
        List<ShipOrientation> list = new ArrayList<>();
        TreeMap<String,Integer> copyOfFleetMap = new TreeMap<>(fleetMap);
        for(Ship s : fleet.getShips()) {
            int id = copyOfFleetMap.get(s.getName());
            copyOfFleetMap.put(s.getName(), id - 1);
            list.addAll(createAllOrientations(s, id));
        }
        return list;
    }



    /**
     * A board location for a ship in a given orientation.
     */
    private static class ShipLocation {
        private int x;
        private int y;
        private ShipOrientation orientation;

        public ShipLocation(int x, int y, ShipOrientation orientation) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
        }

        public boolean shipCovers(int boardX, int boardY) {
            return this.orientation.shipCovers(boardX - this.x, boardY - this.y);
        }

        public boolean isAdjacent(int boardX, int boardY) {
            for(int x = -1; x <= 1; x++){
                for(int y = -1; y <= 1; y++) {
                    if (shipCovers(boardX + x, boardY + y)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean overlaps(ShipLocation other) {
            for(int y = 0; y < orientation.pattern.length; y++) {
                for(int x = 0; x < orientation.pattern[0].length; x++) {
                    if (orientation.pattern[y][x] == 1 && other.shipCovers(this.x + x, this.y + y)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class ShipOrientation {
        private String name;
        private int[][] pattern;

        public ShipOrientation(String name, int[][] pattern) {
            this.name = name;
            this.pattern = pattern;
        }
        private String getName() {
            return this.name;
        }

        public int getWidth() {
            return this.pattern[0].length;
        }

        public int getHeight() {
            return this.pattern.length;
        }

        /**
         * Returns true if the ship pattern has a ship square in the
         * specified coordinate. If the coordinate is outside the
         * bounds of the ship altogether, returns false.
         */
        public boolean shipCovers(int x, int y) {
            if (x >= 0 && y >= 0 && y < this.pattern.length && x < this.pattern[0].length) {
                return this.pattern[y][x] == 1;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            ShipOrientation that = (ShipOrientation) o;
            return Objects.equals(name, that.name) && Objects.deepEquals(pattern, that.pattern);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, Arrays.deepHashCode(pattern));
        }
    }
}
