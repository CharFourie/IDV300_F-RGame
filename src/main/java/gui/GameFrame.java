package gui;

import game.*;
import game.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import static game.Cell.FOX;
import static game.Cell.RABBIT;

/**
 * Created by charnefourie on 2016/10/13.
 */
public class GameFrame extends JFrame {

    private GameState gamestate;
    private JLabel messageLabel;
    private Map<Point, BoardCellPanel> boardCellPanels;

    public GameFrame(){
        super();
        boardCellPanels = new HashMap<Point, BoardCellPanel>();
        initGui();
    }

    public void initGui(){

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("New Game...");

        JMenuItem twoPlayerItem = new JMenuItem("New Two-Player Game");
        twoPlayerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamestate = new GameState(GameStyle.TWO_PLAYER);
                refreshBoard();
            }
        });

        JMenuItem networkedItem = new JMenuItem("New Networked Game");
        networkedItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameFrame.this.dispose();
                new NetworkedSetupFrame();
            }
        });

        JMenuItem aiItem = new JMenuItem("New AI Game");

        menu.add(twoPlayerItem);
        menu.add(networkedItem);
        menu.add(aiItem);

        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        // Visual board setup
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BoardState.MAX+1, BoardState.MAX+1));
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++){
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++){
                BoardCellPanel cellPanel = new BoardCellPanel(r,c);
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
                // When board is clicked
                cellPanel.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mouseReleased(MouseEvent e) {
                        BoardCellPanel clickedCell = (BoardCellPanel) e.getSource();

                        if (gamestate.getSelectedPoint() == null) {
                            gamestate.doSelect(clickedCell.getRow(), clickedCell.getColumn());
                        } else {
                            gamestate.doMove(clickedCell.getRow(), clickedCell.getColumn());
                        }

                        refreshBoard();
                    }
                });
                boardCellPanels.put(new Point(r,c), cellPanel);
                boardPanel.add(cellPanel);
            }
        }
        boardPanel.setBackground(Color.GREEN);

        // Message Label
        messageLabel = new JLabel();

        // Frame Layout
        this.setLayout(new BorderLayout());
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(messageLabel, BorderLayout.SOUTH);

        this.setSize(new Dimension(700,800));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void refreshBoard(){

        // Set fox cells orange, rabbit cells grey and empty cells <<white>>
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                switch (gamestate.getTheBoard().getCell(r,c)) {
                    case FOX : boardCellPanels.get(new Point(r, c)).setBackground(Color.ORANGE); break;
                    case RABBIT :  boardCellPanels.get(new Point(r,c)).setBackground(Color.PINK); break;
                    case EMPTY :  boardCellPanels.get(new Point(r,c)).setBackground(new Color(0,0,0,64)); break;
                }
            }
        }

        // show if selected
        if (gamestate.getSelectedPoint() != null && gamestate.getActivePlayer().getColour().equals(FOX)) {
            boardCellPanels.get(gamestate.getSelectedPoint()).setBackground(Color.YELLOW);
        } else if (gamestate.getSelectedPoint() != null && gamestate.getActivePlayer().getColour().equals(RABBIT)){
            boardCellPanels.get(gamestate.getSelectedPoint()).setBackground(Color.RED);
        }

        // Update message label
        if (gamestate.gameIsOver()){
            if (gamestate.foxCellsInSameColumn()){
                messageLabel.setForeground(Color.PINK);
                messageLabel.setText("GAME OVER! RABBIT WINS!");
            } else if (gamestate.foxCellsInSameRow()){
                messageLabel.setForeground(Color.PINK);
                messageLabel.setText("GAME OVER! RABBIT WINS!");
            }
            else if (gamestate.foxHasNoLegalMoves()){
                messageLabel.setForeground(Color.ORANGE);
                messageLabel.setText("GAME OVER! FOX WINS!");
            }
            else {
                messageLabel.setForeground(Color.BLACK);
                messageLabel.setText("GAME OVER! IT IS A DRAW.");
            }
        } else {
            if (gamestate.getActivePlayer().getColour().equals(Cell.FOX)) {
                messageLabel.setForeground(Color.ORANGE);
                messageLabel.setText("FOX'S TURN.");
            } else {
                messageLabel.setForeground(Color.PINK);
                messageLabel.setText("RABBIT'S TURN.");
            }
        }

        this.repaint();
    }

    public  void setGameState(GameState gameState){
        this.gamestate = gameState;
        refreshBoard();
    }

}
