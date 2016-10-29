package game;

import com.sun.media.jfxmedia.events.PlayerTimeListener;
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


//    public  void doFullAiMove(){
//        runMiniMax();
//        doAiSelect(minimaxSelect);
//        gameFrame.refreshBoard();
//        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            Thread.interrupted();
//        }
//        doAiMove(minimaxMove);
//        gameFrame.refreshBoard();
//    }

    @Override
    public void doMove(Point point) {
        if (getActivePlayer().getColour().equals(localPlayer.getColour())) {
            super.doMove(point);

            if (getActivePlayer().getColour().equals(aiPlayer.getColour())) {
                MoveAndHeuristic bestMove = runMiniMax(getTheBoard(), aiPlayer, 0);
                if (bestMove != null){
                    this.minimaxSelect = bestMove.getSelection();
                    this.minimaxMove = bestMove.getMove();
                    doAiSelect(minimaxSelect);
                    gameFrame.refreshBoard();
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                    doAiMove(minimaxMove);
                    gameFrame.refreshBoard();
                }
            }
        }
    }

    public int calculateHeuristicForBoard(BoardState boardState, Player player){
        // Large heuristic good for rabbits
        // Small is good for foxes
        int heuristic = getFoxLegalMoves().size();

        if(foxCellsInSameColumn() || foxCellsInSameRow()) {
            heuristic = 1000;
        }

        return heuristic;

    }

    public List<Point> getPossibleSelections(Player player) {
        List<Point> possibleSelections = new LinkedList<Point>();
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if(player.getColour().equals(getTheBoard().getCell(r,c))) {
                    possibleSelections.add(new Point(r,c));
                }
            }
        }
        return possibleSelections;
    }

    public List<Point> getPossibleMovesForSelection(Point selection) {
        List<Point> possibleMoves = new LinkedList<Point>();
        if (getTheBoard().getCell(selection) == Cell.FOX){
            if (getTheBoard().getCell(selection.getRow(), selection.getColumn() -1).equals(Cell.RABBIT)) {
                possibleMoves.add(new Point(selection.getRow(), selection.getColumn() -1));
            }
            if (getTheBoard().getCell(selection.getRow(), selection.getColumn() +1).equals(Cell.RABBIT)) {
                possibleMoves.add(new Point(selection.getRow(), selection.getColumn() +1));
            }
            if (getTheBoard().getCell(selection.getRow() -1, selection.getColumn() ).equals(Cell.RABBIT)) {
                possibleMoves.add(new Point(selection.getRow() -1, selection.getColumn() ));
            }
            if (getTheBoard().getCell(selection.getRow() + 1, selection.getColumn()).equals(Cell.RABBIT)) {
                possibleMoves.add(new Point(selection.getRow() + 1, selection.getColumn()));
            }
        } else if (getTheBoard().getCell(selection) == Cell.RABBIT) {
            if (getTheBoard().getCell(selection.getRow(), selection.getColumn() -1).equals(Cell.EMPTY)) {
                possibleMoves.add(new Point(selection.getRow(), selection.getColumn() -1));
            }
            if (getTheBoard().getCell(selection.getRow(), selection.getColumn() +1).equals(Cell.EMPTY)) {
                possibleMoves.add(new Point(selection.getRow(), selection.getColumn() +1));
            }
            if (getTheBoard().getCell(selection.getRow() -1, selection.getColumn() ).equals(Cell.EMPTY)) {
                possibleMoves.add(new Point(selection.getRow() -1, selection.getColumn() ));
            }
            if (getTheBoard().getCell(selection.getRow() + 1, selection.getColumn()).equals(Cell.EMPTY)) {
                possibleMoves.add(new Point(selection.getRow() + 1, selection.getColumn()));
            }
        }
        return possibleMoves;
    }

    public void doMoveOnCloneBoard(BoardState clonedBoard, Point selection, Point move) {

        clonedBoard.setCell(move, aiPlayer.getColour());

        if (Point.getColumnDistance(selection, move) == 1 || Point.getRowDistance(selection, move) == 1) {
            clonedBoard.setCell(selection, Cell.EMPTY);
        }
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

//        int heuristic = Integer.MIN_VALUE;

        // create a new board for each possible move
        for (Point selection : getPossibleSelections(playerToMove)) {
            for (Point move : getPossibleMovesForSelection(selection)) {
                BoardState clonedBoard = currentBoard.copy();
                doMoveOnCloneBoard(clonedBoard, selection, move);
                if (depth == 2){
                    int cloneHeuristic = calculateHeuristicForBoard(clonedBoard, playerToMove);
                    heuristicList.add(new MoveAndHeuristic(selection, move, cloneHeuristic));
                } else {
                    int cloneHeuristic = runMiniMax(clonedBoard, otherPlayer(playerToMove), depth -1).getHeuristic();
                    heuristicList.add(new MoveAndHeuristic(selection, move, cloneHeuristic));
                }

//                if (cloneHeuristic > heuristic && aiPlayer == rabbitPlayer) {
//                    minimaxSelect = selection;
//                    minimaxMove = move;
//                    heuristic = cloneHeuristic;
//                } else if (cloneHeuristic < heuristic && aiPlayer == foxPlayer) {
//                    minimaxSelect = selection;
//                    minimaxMove = move;
//                    heuristic = cloneHeuristic;
//                }
            }
        }

        MoveAndHeuristic bestMove = null;
        for (MoveAndHeuristic cloneHeuristic : heuristicList) {
            if (bestMove == null || cloneHeuristic.getHeuristic() > bestMove.getHeuristic()) {
                bestMove = cloneHeuristic;
            }
        }
        return bestMove;
    }



    public boolean isLocalPlayerTurn() {
        return localPlayer.equals(getActivePlayer());
    }

}

