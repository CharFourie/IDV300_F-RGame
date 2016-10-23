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

    private JComboBox<String> comboBox;
    private JLabel outputLabel;
    private JLabel hostLabel;
    private JTextField hostName;
    private JButton startButton;

    public NetworkedSetupFrame() {
        super();
        initGui();
    }

    private void initGui() {

        this.setLayout(new GridLayout(5,1));

        comboBox = new JComboBox<String>();
        comboBox.addItem("Host");
        comboBox.addItem("Join");

        hostLabel = new JLabel("Host IP Address");
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

        startButton = new JButton("Start");
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

        this.add(comboBox);
        this.add(hostLabel);
        this.add(hostName);
        this.add(startButton);
        this.add(outputLabel);

        this.setSize(new Dimension(700,800));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
