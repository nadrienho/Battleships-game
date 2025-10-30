package battleships;

import battleships.ship.Battleship;
import battleships.ship.Destroyer;
import battleships.ship.Submarine;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    private List<Ship> ships=  new ArrayList<>();

    public Fleet(int battleships, int destroyers, int submarines) {
        for (int i = 0; i < battleships; i++) {
            this.ships.add(new Battleship());
        }
        for (int i = 0; i < destroyers; i++) {
            this.ships.add(new Destroyer());
        }
        for (int i = 0; i < submarines; i++) {
            this.ships.add(new Submarine());
        }

    }

    public List<Ship> getShips() {
        return ships;
    }
}
