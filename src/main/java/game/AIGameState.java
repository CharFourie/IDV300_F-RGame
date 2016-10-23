package game;

import gui.GameFrame;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sabeehabanubhai on 2016/10/23.
 */
public class AIGameState extends GameState {

    private GameFrame gameFrame;
    private Player localPlayer;
    private Player aiPlayer;
    private Point minimaxSelect;
    private Point minimaxMove;

    public AIGameState(GameStyle gameStyle, Cell localColour) {
        super(gameStyle);
        if(localColour.equals(Cell.FOX)) {
            localPlayer = foxPlayer;
            aiPlayer = rabbitPlayer;
        } else {
            localPlayer = rabbitPlayer;
            aiPlayer = foxPlayer;
        }
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void doAiSelect(Point point) {
        super.doSelect(point);
    }

    public void doAiMove(Point point) {
        super.doMove(point);
        gameFrame.refreshBoard();
    }

    @Override
    public void doSelect(Point point) {
        // the player can only select if its their turn
        if (isLocalPlayerTurn()) {
            super.doSelect(point);
        }
    }

    public int calculateHeuristicForBoard(BoardState boardState, Player player){
        // do we need two? one for fox and one for rabbit?
        return boardState.countCells(player.getColour())
                - boardState.countCells(otherPlayer(player).getColour());
    }

    public List<Point> getPossibleSelections(Player player) {
        List<Point> possibleSelections = new LinkedList<Point>();
        // do we need two? one for fox and one for rabbit?
        return possibleSelections;
    }

    public List<Point> getPossibleMovesForSelection(Point selection) {
        List<Point> possibleMoves = new LinkedList<Point>();
        // do we need two? one for fox and one for rabbit?
        return possibleMoves;
    }

    public void doMoveOnCloneBoard(BoardState clonedBoard, Point selection, Point move) {

    }

    public Player otherPlayer(Player player) {
        if (player.getColour().equals(foxPlayer.getColour())) {
            return rabbitPlayer;
        } else {
            return foxPlayer;
        }
    }

    public MoveAndHeuristic runMiniMax(BoardState currentBoard, Player playerToMove, int depth) {
        List<MoveAndHeuristic> heuristicList = new LinkedList<MoveAndHeuristic>();

        // Create a new board for each possible move
        for (Point selection : getPossibleSelections(playerToMove)) {
            for (Point move : getPossibleMovesForSelection(selection)) {
                BoardState clonedBoard = currentBoard.copy();
                doMoveOnCloneBoard(clonedBoard, selection, move);
                if (depth == 0) {
                    int cloneHeuristic = calculateHeuristicForBoard(clonedBoard, playerToMove);
                    heuristicList.add(new MoveAndHeuristic(selection, move, cloneHeuristic));
                } else {
                    int cloneHeuristic = runMiniMax(clonedBoard, otherPlayer(playerToMove), depth -1).getHeuristic();
                    heuristicList.add(new MoveAndHeuristic(selection, move, cloneHeuristic));
                }
            }
        }

        //return here
        MoveAndHeuristic bestMove = null;
        for (MoveAndHeuristic cloneHeuristic : heuristicList) {
            if (bestMove == null || cloneHeuristic.getHeuristic() > bestMove.getHeuristic()) {
                bestMove = cloneHeuristic;
            }
        }
        return bestMove;
    }

    @Override
    public void doMove(Point point) {
        // the player can only move something n the board if is their turn
        if (isLocalPlayerTurn()){
            super.doMove(point);
            // move must have been a success, so now it is the ai turn
            if (!isLocalPlayerTurn()) {
                gameFrame.refreshBoard();
                MoveAndHeuristic bestMove = runMiniMax(getTheBoard(), aiPlayer, 2);
                if (bestMove != null) {
                    this.minimaxSelect = bestMove.getSelection();
                    this.minimaxMove = bestMove.getMove();
                    doAiSelect(minimaxSelect);
                    try{
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                    doAiMove(minimaxMove);
                }
            }
        }
    }

    public boolean isLocalPlayerTurn() {
        return localPlayer.equals(getActivePlayer());
    }

}

