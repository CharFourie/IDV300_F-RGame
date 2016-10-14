package game;

/**
 * Created by sabeehabanubhai on 2016/10/14.
 */
public class Player {

    public Cell colour;
    public Cell character; //image of fox or rabbiy


    public Player(Cell colour){
        this.colour = colour;
    }

    public Cell getCharacter() {
        return character;
    }

    public void setCharacter(Cell character) {
        this.character = character;
    }

    public Cell getColour() {
        return colour;
    }

    public void setColour(Cell colour) {
        this.colour = colour;
    }
}
