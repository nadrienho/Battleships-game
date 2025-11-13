package battleships;


public class BoardFactory {

    public static Board getBigBoard() {
        Fleet fleet = new Fleet(1,2,3,4,1);
        Board board = new Board(15,15);
        return setUp(board, fleet);
    }

    public static Board[] getBigBoards() {
        return new Board[] { BoardFactory.getBigBoard(), BoardFactory.getBigBoard() };
    }

    public static Board getSmallBoard() {
        Fleet fleet = new Fleet(1,1,0,1,1);
        Board board = new Board(10,10);
        return setUp(board, fleet);
    }

    public static Board[] getSmallBoards() {
        return new Board[] { getSmallBoard(), getSmallBoard() };
    }

    public static Board getTinyBoard() {
        Fleet fleet = new Fleet(0,0,0,1,0);
        Board board = new Board(5,5);
        return setUp(board, fleet);
    }

    public static Board[] getTinyBoards() {
        return new Board[] { getTinyBoard(), getTinyBoard() };
    }

    private static Board setUp(Board board, Fleet fleet) {
        while(true) {
            try {
                board.setUp(fleet);
                return board;
            } catch(FailedToPlaceShipException x) {
                System.err.println("FailedToPlaceShipException");
            }
        }
    }

}
