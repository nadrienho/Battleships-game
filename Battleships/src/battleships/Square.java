package battleships;

public class Square {

    private boolean tried;

    private Ship ship;

    public boolean isTried() {
        return this.tried;
    }
    public void setTried() {
        this.tried = true;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hasShip() {
        return this.ship != null;
    }

    public boolean isHit() {
        return this.tried && hasShip();
    }

    public boolean isMiss() {
        return this.tried && !hasShip();
    }



    public String getCodeCharacter(boolean showShips) {
        if (isHit()){
            return "*";
        }else if (isMiss()){
            return "~";
        } else if (showShips && ship != null) {
            return ship.getCode();
        }else {
            return "~";
        }

    }
}
