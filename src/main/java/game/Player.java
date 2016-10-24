package game;

/**
 * Created by sabeehabanubhai on 2016/10/14.
 */
public class Player {

    public Cell colour;
    public Cell character; //image of fox or rabbit


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (colour != player.colour) return false;
        return character == player.character;

    }

    @Override
    public int hashCode() {
        int result = colour != null ? colour.hashCode() : 0;
        result = 31 * result + (character != null ? character.hashCode() : 0);
        return result;
    }
}
