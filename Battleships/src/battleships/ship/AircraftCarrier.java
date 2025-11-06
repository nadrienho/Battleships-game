package battleships.ship;

public class AircraftCarrier extends TemplateShip{
    private static final int[][] pattern = new int[][] {
            new int[] { 1, 1, 1, 1, 0},
            new int[] { 0, 1, 1, 1, 1}
    };

    public AircraftCarrier(){
        super("aircraft carrier", "C", pattern.clone());
    }
}
