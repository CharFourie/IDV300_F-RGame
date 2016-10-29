package gui;

import game.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by charnefourie on 2016/10/28.
 */
public class BackgroundPanel extends JPanel {

    public BackgroundPanel(){
        super();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getImage() != null) {
            g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    protected Image getImage(){
        try {
            return ImageIO.read(new File("images/GrassBg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
