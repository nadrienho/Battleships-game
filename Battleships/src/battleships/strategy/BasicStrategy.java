package battleships.strategy;

import battleships.Board;
import battleships.ComputerPlayerStrategy;
import battleships.Outcome;

import java.util.ArrayList;
import java.util.List;

public class BasicStrategy implements ComputerPlayerStrategy {
    private final List<int[]> lastHits = new ArrayList<>();
    @Override
    public int[] chooseTarget(Board opponentBoard, Board ownBoard) {
        System.out.println( "\nChoosing target....");
        if (!this.lastHits.isEmpty()) {
            // look for a square adjacent to one of the hits in lastHits
            // that has not been bombed yet and return it as the target
            for(int index = 0; index < this.lastHits.size(); index++) {
                final int[] lastHit = this.lastHits.get(index);
                Adjacent candidate = Adjacent.random();
                int c = 4;
                while(c > 0) {
                    int x = lastHit[0] + candidate.dx;
                    int y = lastHit[1] + candidate.dy;
                    if (inBoundsAndUntried(opponentBoard, x, y) && squareMightBeShip(opponentBoard, x, y)) {
                        return new int[] { x, y };
                    }
                    candidate = candidate.next();
                    c--;
                }
            }
        }
        // else choose a random unbombed square and return that
        return chooseSquare(opponentBoard);
    }
    @Override
    public void resultOfMove(Outcome outcome) {
        if (outcome.hit()) {
            this.lastHits.add(new int[] { outcome.getX(), outcome.getY() });
            if (outcome.sunk() != null) {
                this.lastHits.clear();
            }
        }
    }
    private int[] chooseSquare(final Board board) {
        while(true) {
            final int x = (int)(Math.random() * board.getWidth());
            final int y = (int)(Math.random() * board.getHeight());
            if (!board.getSquare(x, y).isTried()) {
                return new int[] { x, y };
            }
        }
    }
    private boolean inBoundsAndUntried(Board board, int x, int y) {
        return board.inBounds(x, y) && !board.getSquare(x, y).isTried();
    }

    private boolean squareMightBeShip(final Board board, final int x, final int y) {
        if (!board.inBounds(x, y)) {
            return false;
        }
        if (board.getSquare(x, y).isMiss()) {
            return false;
        }
        // examine squares around the target being considered
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    final int tx = x + i;
                    final int ty = y + j;
                    if (board.inBounds(tx, ty) && board.getSquare(tx, ty).isHit()) {
                        // if the square is a hit in lastHits that is OK as its part of the ship we are part
                        // way through destroying (this method is also used when picking adjacent squares in
                        // that process).
                        if (!isInLastHits( tx, ty)) {
                            System.out.println( "BOOST!");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    private boolean isInLastHits(int x, int y) {
        for(int[] hit : this.lastHits) {
            if (hit[0] == x && hit[1] == y) {
                return true;
            }
        }
        return false;
    }
}