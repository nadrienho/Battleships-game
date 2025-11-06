package battleships;

import battleships.ship.Aeroplane;
import battleships.ship.Battleship;
import battleships.ship.Destroyer;
import battleships.ship.SimpleShip;

public class ShipDemo {
    public static void main(String[] args) {
        Ship s = new Aeroplane();

        System.out.println(s);
        s.rotate();
        System.out.println(s);
        s.rotate();
        System.out.println(s);
        s.rotate();
        System.out.println(s);

    }
}
