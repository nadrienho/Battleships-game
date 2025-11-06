package battleships;

import battleships.ship.*;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    private List<Ship> ships=  new ArrayList<>();

    public Fleet(int battleships, int destroyers, int submarines, int aeroplanes, int aircraftcarriers) {
        for (int i = 0; i < battleships; i++) {
            this.ships.add(new Battleship());
        }
        for (int i = 0; i < destroyers; i++) {
            this.ships.add(new Destroyer());
        }
        for (int i = 0; i < submarines; i++) {
            this.ships.add(new Submarine());
        }
        for (int i = 0; i < aeroplanes; i++) {
            this.ships.add(new Aeroplane());
        }
        for (int i = 0; i < aircraftcarriers; i++) {
            this.ships.add(new AircraftCarrier());
        }


    }

    public List<Ship> getShips() {
        return ships;
    }
}
