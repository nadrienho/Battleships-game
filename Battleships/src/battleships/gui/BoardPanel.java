package battleships.gui;

import battleships.AbstractGame;
import battleships.AbstractPlayer;
import battleships.Board;
import battleships.BoardFactory;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Board board;
    private BoardButton[][] boardButtons;

    private AbstractPlayer player;
    private GamePanel gamePanel;




    public BoardPanel(AbstractPlayer player, GamePanel gamePanel) {

        super(new GridLayout(player.getBoard().getHeight(), player.getBoard().getWidth()));
        setBorder(BorderFactory.createTitledBorder(player.getName()));
        this.gamePanel = gamePanel;
        this.board = player.getBoard();
        this.player = player;
        this.boardButtons = new BoardButton[board.getHeight()][board.getWidth()];
        for (int y = 0; y < this.boardButtons.length; y++) {
            final int finalY = y;
            for (int x = 0; x < this.boardButtons[y].length; x++) {
                final int finalX = x;
                final BoardButton button = new BoardButton(board.getSquare(x, y));
                button.addActionListener((ev)->handleButton(finalX,finalY));
                this.boardButtons[y][x] = button;
                add(button);
            }
        }
    }

    private void handleButton(final int x, final int y) {
        if (!this.board.getSquare(x,y).isTried()) {
            this.gamePanel.bombDropped(this.player, x,y);
        }
    }

    public void setShowShips(final boolean showShips) {
        for (int y = 0; y < this.boardButtons.length; y++) {
            for (int x = 0; x < this.boardButtons[y].length; x++) {
                this.boardButtons[y][x].setShowShips(showShips);
            }
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        for (int y = 0; y < this.boardButtons.length; y++) {
            for (int x = 0; x < this.boardButtons[y].length; x++) {
                this.boardButtons[y][x].setEnabled(enabled);
            }
        }
    }

    public BoardButton getBoardButton(int x, int y) {
        return this.boardButtons[y][x];
    }
//    public static void launch(String[] args) {
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        Board board = BoardFactory.getBigBoard();
//        BoardPanel bp = new BoardPanel(board);
//        bp.setShowShips(true);
//        f.add(bp);
//        f.pack();
//        f.setVisible(true);
//    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> launch(args));
//    }

}
