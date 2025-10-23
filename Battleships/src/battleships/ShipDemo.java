package battleships;

public class ShipDemo {
    public static void main(String[] args) {
        Ship s = new Ship("battleship", "B", 5);

        System.out.println(s);
        s.rotate();
        System.out.println(s);
    }
}
