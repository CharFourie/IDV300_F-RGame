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

    @Test
    public void testRabbitLegalMove(){
        Player rabbitPlayer = new Player(Cell.RABBIT);


        GameState board1 = new GameState(GameStyle.TWO_PLAYER);
        board1.getTheBoard().setCell(new Point(2,3), Cell.FOX);
        board1.getActivePlayer().equals(rabbitPlayer);

        board1.doMove(new Point(2,3));

        Assert.assertEquals(board1.isRabbitLegalMove(new Point(2,3)), false);
    }

    @Test
    public void testRabbitMoveDiagonally() {
        Player rabbitPlayer = new Player(Cell.RABBIT);

        GameState board1 = new GameState(GameStyle.TWO_PLAYER);
        board1.getTheBoard().setCell(new Point(2,3), Cell.RABBIT);

        board1.doMove(new Point(1,4));
        Assert.assertEquals(board1.isRabbitLegalMove(new Point(1,4)), false);

    }

    @Test
    public void testFoxMoveDiagonally() {
        Player rabbitPlayer = new Player(Cell.FOX);

        GameState board1 = new GameState(GameStyle.TWO_PLAYER);
        board1.getTheBoard().setCell(new Point(2,3), Cell.FOX);

        board1.doMove(new Point(1,4));
        Assert.assertEquals(board1.isFoxLegalMove(new Point(1,4)), false);

    }

    @Test
    public void testRabbitWinsFoxSameRow() {

        GameState board1 = new GameState(GameStyle.TWO_PLAYER);
        board1.getTheBoard().setCell(new Point(2,3), Cell.FOX);
        board1.getTheBoard().setCell(new Point(2,1), Cell.FOX);
        board1.getTheBoard().setCell(new Point(2,4), Cell.FOX);

        Assert.assertEquals(board1.gameIsOver(), false);

    }

    @Test
    public void testRabbitWinsFoxSameColumn() {

        GameState board1 = new GameState(GameStyle.TWO_PLAYER);
        board1.getTheBoard().setCell(new Point(2,3), Cell.FOX);
        board1.getTheBoard().setCell(new Point(3,3), Cell.FOX);
        board1.getTheBoard().setCell(new Point(4,3), Cell.FOX);

        Assert.assertEquals(board1.gameIsOver(), false);

    }
}
