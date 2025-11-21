package battleships.gui;

import battleships.Ship;
import battleships.Square;
import battleships.ship.Submarine;

import javax.swing.*;
import java.awt.*;

public class BoardButton extends JButton {

    private Square square;
    private boolean showShips;

    public BoardButton(Square square) {
        this. square = square;

        setPreferredSize(new Dimension(16,16));

    }

    public void setShowShips(boolean showships) {
        this.showShips = showships;
        repaint();
    }

    protected void paintComponent(Graphics g){
        g = g.create();

        if (this.square.isTried()) {
            if (this.square.isHit()) {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0,0,getWidth(),getHeight());
                if (this.square.getShip().isSunk()) {
                    g.setColor(new Color(192,0,0));
                } else {
                    g.setColor(Color.ORANGE);
                }
                g.fillOval(4,4,getWidth() - 8, getHeight() - 8);
                // hit ship

            } else if (this.square.isMiss()) {
                // sea miss
                g.setColor(Color.BLUE);
                g.fillRect(0,0,getWidth(),getHeight());
                g.setColor(Color.WHITE);
                g.drawOval(4,4,getWidth() - 8, getHeight() - 8);

            }
        } else {
            if (this.showShips && (this.square.getShip() != null)) {
                // ship
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0,0,getWidth(),getHeight());
            } else {
                // sea
                g.setColor(Color.BLUE);
                g.fillRect(0,0,getWidth(),getHeight());
            }

        }



    }

    public static void launch(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Square s = new Square();

        Ship sub = new Submarine();
        s.setShip(sub);
        sub.incrementHitCount();
        sub.incrementHitCount();
        s.setTried();

        BoardButton boardButton = new BoardButton(s);

        JPanel p = new JPanel();
        p.add(boardButton);

        f.add(p);
        f.pack();
        f.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> launch(args));
    }
}
