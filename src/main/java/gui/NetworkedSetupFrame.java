package gui;

import game.Cell;
import game.CommunicationLink;
import game.GameStyle;
import game.NetworkedGameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by sabeehabanubhai on 2016/10/23.
 */
public class NetworkedSetupFrame extends JFrame {

    public static final int PORTNUM = 9933;

    private BackgroundPanel backgroundImagePanel;
    private JComboBox<String> comboBox;
    private JLabel outputLabel;
    private JLabel hostLabel;
    private JTextField hostName;
    private JButton startButton;
    private JButton menuButton;

    public NetworkedSetupFrame() {
        super();
        initGui();
    }

    private void initGui() {

        comboBox = new JComboBox<String>();
        comboBox.addItem("Host");
        comboBox.addItem("Join");

        hostLabel = new JLabel("Host IP Address");
        hostLabel.setForeground(Color.WHITE);
        hostName = new JTextField();

        hostLabel.setVisible(false);
        hostName.setVisible(false);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedItem().equals("Join")) {
                    hostLabel.setVisible(true);
                    hostName.setVisible(true);
                } else {
                    hostLabel.setVisible(false);
                    hostName.setVisible(false);
                }
            }
        });

        outputLabel = new JLabel("");

        startButton = new JButton("START");
        startButton.setBackground(new Color(20, 144, 51));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        startButton.setOpaque(true);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NetworkedGameState gameState = null;
                if (comboBox.getSelectedItem().equals("Host")) {
                    outputLabel.setText("Hosting on port " + PORTNUM);
                    // The host gets to play the FOX
                    gameState = new NetworkedGameState(GameStyle.NETWORKED, Cell.FOX);
                } else {
                    outputLabel.setText("Connecting to " + hostName.getText() + " " + PORTNUM);
                    // The client gets to play the rabbit
                    gameState = new NetworkedGameState(GameStyle.NETWORKED, Cell.RABBIT);
                }
                try {
                    CommunicationLink communicationLink = new CommunicationLink(comboBox.getSelectedItem().toString(), hostName.getText(), PORTNUM);
                    communicationLink.setNetworkedGameState(gameState);
                    gameState.setCommunicationLink(communicationLink);
                    communicationLink.start();
                    NetworkedSetupFrame.this.dispose();
                    GameFrame gameFrame = new GameFrame();
                    gameFrame.setGameState(gameState);
                    gameState.setGameFrame(gameFrame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuButton = new JButton("CANCEL");
        menuButton.setBackground(new Color(20, 144, 51));
        menuButton.setForeground(new Color(255, 255, 255));
        menuButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        menuButton.setOpaque(true);
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NetworkedSetupFrame.this.dispose();
                new MenuFrame();
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4,1));
        infoPanel.setBackground(new Color(0,0,0,0));
        infoPanel.add(comboBox);
        infoPanel.add(hostLabel);
        infoPanel.add(hostName);
        infoPanel.add(outputLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.add(startButton);
        buttonPanel.add(menuButton);

        JPanel backgroundPanel = new JPanel();
        backgroundImagePanel = new BackgroundPanel();
        JPanel controlsPanel = new JPanel();
        backgroundPanel.setLayout(new OverlayLayout(backgroundPanel));

        backgroundPanel.add(controlsPanel);
        backgroundPanel.add(backgroundImagePanel);

        controlsPanel.setLayout(new GridLayout(5,1));
        controlsPanel.setBackground(new Color(0,0,0,0));
        controlsPanel.add(infoPanel);
        controlsPanel.add(buttonPanel);

        this.add(backgroundPanel);

        this.setSize(new Dimension(700,800));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
