package gui;

import game.BoardState;
import game.GameState;
import game.GameStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import static game.Cell.FOX;

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
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // When board is clicked
                cellPanel.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mouseReleased(MouseEvent e) {
                        BoardCellPanel clickedCell = (BoardCellPanel) e.getSource();
                        gamestate.doSelect(clickedCell.getRow(), clickedCell.getColumn());
                        gamestate.doMove(clickedCell.getRow(), clickedCell.getColumn());
                        refreshBoard();
                    }
                });
                boardCellPanels.put(new Point(r,c), cellPanel);
                boardPanel.add(cellPanel);
            }
        }

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
                    case EMPTY :  boardCellPanels.get(new Point(r,c)).setBackground(Color.WHITE); break;
                }
            }
        }

        // Update message label

    }

    public  void setGameState(GameState gameState){
        this.gamestate = gameState;
        refreshBoard();
    }

}
