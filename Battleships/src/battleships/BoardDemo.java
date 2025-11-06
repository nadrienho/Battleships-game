package battleships;



public class BoardDemo {
    public static void main() throws FailedToPlaceShipException {
        Board b1 = new Board(20,20);

        Fleet fleet = new Fleet(1,2,3, 4,1);
        b1.setUp(fleet);



        
        b1.getSquare(0,0).setTried();
        b1.getSquare(0,1).setTried();

        System.out.println(b1);

        // drop some bombs

        System.out.println("Bombing square x=2, y=0");
        b1.dropBomb(2, 0);
        System.out.println(b1);
        System.out.println();

        System.out.println("Bombing square x=2, y=1");
        b1.dropBomb(2, 1);
        System.out.println(b1);
        System.out.println();

        System.out.println("Bombing square x=2, y=2");
        b1.dropBomb(2, 2);
        System.out.println(b1);
        System.out.println();

        // we also need to be able to print the board without showing
        // the ships - this is one way to do that:
        System.out.println("Showing the board with the ships hidden:");
        String[] b = b1.toStringArray(false);
        for(String r : b) {
            System.out.println(r);
        }

        System.out.println("NOW BOMBING TWO WHOLE ROWS TO TRY AND SINK SOMETHING:");
        for(int y = 3; y < 5; y++) {
            for(int x = 0; x < b1.getWidth(); x++) {
                System.out.println("Bombing square x=" + x + ", y=" + y);
                b1.dropBomb(x, y);
                System.out.println(b1);
                System.out.println();
            }
        }

    }
}
