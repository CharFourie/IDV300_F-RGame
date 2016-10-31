package test;

import game.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sabeehabanubhai on 2016/10/28.
 */
public class TestGameState {

    @Test
    public void testDoMove() {
        Player foxPlayer = new Player(Cell.FOX);
//        Player activePlayer = foxPlayer;

        GameState board1 = new GameState(GameStyle.TWO_PLAYER);

        board1.doMove(new Point(3,4));

        Assert.assertEquals(board1.getActivePlayer(), foxPlayer);
    }

    @Test
    public void testFoxLegalMove(){
        Player foxPlayer = new Player(Cell.FOX);

        GameState board1 = new GameState(GameStyle.TWO_PLAYER);
        board1.getTheBoard().setCell(new Point(2,3), Cell.EMPTY);

        board1.doMove(new Point(2,3));

        Assert.assertEquals(board1.isFoxLegalMove(new Point(2,3)), false);
    }
}
