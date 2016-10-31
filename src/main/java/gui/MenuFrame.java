package gui;

import game.AIGameState;
import game.Cell;
import game.GameState;
import game.GameStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by charnefourie on 2016/10/14.
 */
public class MenuFrame extends JFrame {

    private GameState gamestate;

    private BackgroundPanel backgroundImagePanel;
    private JLabel gameLabel;
    private JLabel gameText;

    private JButton twoPlayerButton;
    private JButton networkedButton;
    private JButton aiButton;

    public MenuFrame(){
        super();
        initGui();
    }

    public void initGui(){
        // Background Panel & image
        // Welcome label
        // Game description
        // Buttons for different games

        gameLabel = new JLabel("FOXES & RABBITS");
        gameLabel.setFont(gameLabel.getFont ().deriveFont (50.0f));
        gameLabel.setForeground(Color.WHITE);
        gameLabel.setHorizontalAlignment(JLabel.CENTER);
        gameLabel.setVerticalAlignment(JLabel.CENTER);

        gameText = new JLabel("<html>" +
                "GAME RULES:<br>" +
                "1. PLAYER 1 IS THE FOX, PLAYER 2 IS THE RABBIT<br>" +
                "2. PLAYERS CAN ONLY MOVE 1 CELL ORTHOGONALLY (NOT DIAGONALLY)<br>" +
                "3. FOXES CAN ONLY MOVE INTO A SPACE OCCUPIED BY A RABBIT<br>" +
                "4. RABBITS CAN ONLY MOVE INTO AN EMPTY SPACE<br>" +
                "5. THE FOX WINS WHEN NONE OF THEIR PIECES CAN BE MOVED- THERE ARE NO MORE RABBITS ADJACENT<br>" +
                "6. THE RABBIT WINS WHEN THE FOXES ARE ALL IN THE SAME COLUMN OR ROW<br>" +
                "</html>");
        gameText.setForeground(Color.WHITE);
        gameText.setHorizontalAlignment(JLabel.CENTER);
        gameText.setVerticalAlignment(JLabel.CENTER);

        twoPlayerButton = new JButton("TWO-PLAYER GAME");
        twoPlayerButton.setFont(twoPlayerButton.getFont().deriveFont(20.0f));
        twoPlayerButton.setBackground(new Color(20, 144, 51));
        twoPlayerButton.setForeground(new Color(255, 255, 255));
        twoPlayerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        twoPlayerButton.setOpaque(true);
//        twoPlayerButton.setBorderPainted(false);
        twoPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuFrame.this.dispose();
                gamestate = new GameState(GameStyle.TWO_PLAYER);
                GameFrame gameFrame = new GameFrame();
                gameFrame.setGameState(gamestate);
            }
        });

        networkedButton = new JButton("NETWORKED GAME");
        networkedButton.setFont(networkedButton.getFont().deriveFont(20.0f));
        networkedButton.setBackground(new Color(20, 144, 51));
        networkedButton.setForeground(new Color(255, 255, 255));
        networkedButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        networkedButton.setOpaque(true);
        networkedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuFrame.this.dispose();
                new NetworkedSetupFrame();
            }
        });

        aiButton = new JButton("AI GAME");
        aiButton.setFont(aiButton.getFont().deriveFont(20.0f));
        aiButton.setBackground(new Color(20, 144, 51));
        aiButton.setForeground(new Color(255, 255, 255));
        aiButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        aiButton.setOpaque(true);
        aiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuFrame.this.dispose();
                AIGameState aiGameState = new AIGameState(GameStyle.AI, Cell.FOX);
                GameFrame gameFrame = new GameFrame();
                gamestate = aiGameState;
                gameFrame.setGameState(gamestate);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.add(twoPlayerButton);
        buttonPanel.add(networkedButton);
        buttonPanel.add(aiButton);

        JPanel backgroundPanel = new JPanel();
        backgroundImagePanel = new BackgroundPanel();
        JPanel controlsPanel = new JPanel();
        backgroundPanel.setLayout(new OverlayLayout(backgroundPanel));

        backgroundPanel.add(controlsPanel);
        backgroundPanel.add(backgroundImagePanel);

        controlsPanel.setLayout(new GridLayout(5,1));
        controlsPanel.setBackground(new Color(0,0,0,0));
        controlsPanel.add(gameLabel);
        controlsPanel.add(gameText);
        controlsPanel.add(buttonPanel);

        this.add(backgroundPanel);

        this.setSize(new Dimension(700,800));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
