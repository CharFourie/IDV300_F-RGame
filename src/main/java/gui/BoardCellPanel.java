package gui;

import game.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by charnefourie on 2016/10/13.
 */
public class BoardCellPanel extends JPanel{

    private int row;
    private int column;
    private Cell character;

    public BoardCellPanel(int row, int column) {
        super();
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Cell getCharacter() {
        return character;
    }

    public void setCharacter(Cell character) {
        this.character = character;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getImage() != null) {
            g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    protected Image getImage() {
        try {
            if (character == Cell.FOX) {
                return ImageIO.read(new File("images/FoxIcon.png"));
            } else if (character == Cell.RABBIT) {
                return ImageIO.read(new File("images/RabbitIcon.png"));
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
