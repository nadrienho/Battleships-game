package battleships;

import battleships.ship.*;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    private List<Ship> ships=  new ArrayList<>();

    private int battleships;
    private int destroyers;
    private int submarines;
    private int aeroplanes;
    private int aircraftcarriers;



    public Fleet(int battleships, int destroyers, int submarines, int aeroplanes, int aircraftcarriers) {
        this.battleships = battleships;
        this.destroyers = destroyers;
        this.submarines = submarines;
        this.aeroplanes = aeroplanes;
        this.aircraftcarriers = aircraftcarriers;

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
    @Override
    public Fleet clone(){
        return new Fleet(
         this.battleships,
         this.destroyers,
         this.submarines,
         this.aeroplanes,
         this.aircraftcarriers
        );
    }
}
