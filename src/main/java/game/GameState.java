package game;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sabeehabanubhai on 2016/10/14.
 */
public class GameState {

    private GameStyle gameStyle;
    private BoardState theBoard;
    protected Player rabbitPlayer;
    protected Player foxPlayer;
    private Player activePlayer;
    private Point selectedPoint;

    public GameState(GameStyle gameStyle) {
        // This class models the state of the game
        // Including the board, active player, selected cell etc.

        this.gameStyle = gameStyle;

        theBoard = new BoardState();
//        theBoard.setCell(new Point(BoardState.MIN, BoardState.MIN), Cell.FOX);
//        theBoard.setCell(new Point(BoardState.MAX, BoardState.MAX), Cell.RABBIT);

        // 3 fox cell r1,c5 ; r3,c3 ; r5,c1
        theBoard.setCell(new Point(BoardState.MIN, BoardState.MAX), Cell.FOX);
        theBoard.setCell(new Point(BoardState.MIN + 2, BoardState.MIN + 2), Cell.FOX);
        theBoard.setCell(new Point(BoardState.MAX, BoardState.MIN), Cell.FOX);
        // 22 rabbit cells rest
        // ROW 1
        theBoard.setCell(new Point(BoardState.MIN, BoardState.MIN), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN, BoardState.MIN + 1), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN, BoardState.MIN + 2), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN, BoardState.MIN + 3), Cell.RABBIT);
        // ROW 2
        theBoard.setCell(new Point(BoardState.MIN + 1, BoardState.MIN), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 1, BoardState.MIN + 1), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 1, BoardState.MIN + 2), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 1, BoardState.MIN + 3), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 1, BoardState.MAX), Cell.RABBIT);
        // ROW 3
        theBoard.setCell(new Point(BoardState.MIN + 2, BoardState.MIN), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 2, BoardState.MIN + 1), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 2, BoardState.MIN + 3), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 2, BoardState.MAX), Cell.RABBIT);
        // ROW 4
        theBoard.setCell(new Point(BoardState.MIN + 3, BoardState.MIN), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 3, BoardState.MIN + 1), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 3, BoardState.MIN + 2), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 3, BoardState.MIN + 3), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MIN + 3, BoardState.MAX), Cell.RABBIT);
        // ROW 5
        theBoard.setCell(new Point(BoardState.MAX, BoardState.MIN + 1), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MAX, BoardState.MIN + 2), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MAX, BoardState.MIN + 3), Cell.RABBIT);
        theBoard.setCell(new Point(BoardState.MAX, BoardState.MAX), Cell.RABBIT);


        // Fox starts
        foxPlayer = new Player(Cell.FOX);
        rabbitPlayer = new Player(Cell.RABBIT);
        activePlayer = foxPlayer;

        selectedPoint = null;
    }

    // selecting and deselecting cells
    public void doSelect(Point point) {
        if (hasActiveSelection() && point.equals(selectedPoint)) {
            selectedPoint = null;
        } else if (isValidSelection(point)) {
            selectedPoint = point;
        }
    }

    public void doSelect(int row, int column) {
        doSelect(new Point(row,column));
    }

    public void doMove(Point point) {
        //if fox:
        if (activePlayer.equals(foxPlayer) && isFoxLegalMove(point)){
            theBoard.setCell(point, activePlayer.getColour());
            // set previous to empty
            theBoard.setCell(selectedPoint, Cell.EMPTY);
            switchPlayers();
        }

        if (activePlayer.equals(rabbitPlayer) && isRabbitLegalMove(point)) {
            theBoard.setCell(point, activePlayer.getColour());
            // set previous to empty
            theBoard.setCell(selectedPoint, Cell.EMPTY);
            switchPlayers();
        }

        selectedPoint = null;


    }

    public void doMove(int row, int column) {
        doMove(new Point(row,column));
    }

    public void switchPlayers() {
        if (activePlayer == foxPlayer) {
            activePlayer = rabbitPlayer;
        } else {
            activePlayer = foxPlayer;
        }
    }

    public boolean hasActiveSelection() {
        return selectedPoint != null;
    }

    public boolean isValidSelection(Point point) {
        //different rules
        return !hasActiveSelection() && theBoard.getCell(point).equals(activePlayer.getColour()) && !gameIsOver();
    }

    public boolean isFoxLegalMove(Point point) {

        return hasActiveSelection() &&
                (Point.getColumnDistance(selectedPoint, point) == 1 || Point.getRowDistance(selectedPoint, point) == 1)
                && (selectedPoint.getColumn() == point.getColumn()|| selectedPoint.getRow() == point.getRow())
                && theBoard.getCell(point).equals(Cell.RABBIT) && !gameIsOver();
    }

    public boolean isRabbitLegalMove(Point point) {

        return hasActiveSelection() &&
                (Point.getColumnDistance(selectedPoint, point) == 1 || Point.getRowDistance(selectedPoint, point) == 1)
                && (selectedPoint.getColumn() == point.getColumn()|| selectedPoint.getRow() == point.getRow())
                && theBoard.getCell(point).equals(Cell.EMPTY) && !gameIsOver();
    }

    public boolean gameIsOver() {
        //if fox cells all equal same column: rabbit wins
        //if fox cells all equal same row: rabbit wins
        //if fox cells have no more legal moves: fox wins

        return foxCellsInSameColumn() || foxCellsInSameRow() || foxHasNoLegalMoves() ;
    }


    public boolean foxCellsInSameColumn() {

        List<Point> foxLocations = getFoxLocations();

        boolean sameColumn = foxLocations.get(0).getColumn() == foxLocations.get(1).getColumn();
        boolean sameColumn2 = foxLocations.get(1).getColumn() == foxLocations.get(2).getColumn();

        if (sameColumn && sameColumn2) {
            return true;
        }
        return false;
    }

    public boolean foxCellsInSameRow() {

        List<Point> foxLocations = getFoxLocations();

        boolean sameRow = foxLocations.get(0).getRow() == foxLocations.get(1).getRow();
        boolean sameRow2 = foxLocations.get(1).getRow() == foxLocations.get(2).getRow();

        if (sameRow && sameRow2 ){
            return true;
        }
        return false;
    }


    // helper for get fox locations
    public List<Point> getFoxLocations() {

        List<Point> foxLocations = new LinkedList<Point>();

        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                //if fox cells all equal same column
                if (theBoard.getCell(r,c).equals(Cell.FOX)) {
                    foxLocations.add(new Point(r,c));
                }

            }
        }
        return foxLocations;
    }


    public List<Point> getFoxLegalMoves() {

        List<Point> foxLocations = getFoxLocations();

        List<Point> foxLegalMoves = new LinkedList<Point>();

        for (Point location : foxLocations) {
            int r = location.getRow();
            int c = location.getColumn();

            if (theBoard.getCell(r,c -1).equals(Cell.RABBIT)){
                foxLegalMoves.add(new Point(r,c -1));
            }
            if (theBoard.getCell(r,c +1).equals(Cell.RABBIT)){
                foxLegalMoves.add(new Point(r,c +1));
            }
            if (theBoard.getCell(r -1,c).equals(Cell.RABBIT)){
                foxLegalMoves.add(new Point(r -1,c));
            }
            if (theBoard.getCell(r +1,c).equals(Cell.RABBIT)){
                foxLegalMoves.add(new Point(r +1,c));
            }
        }
        return foxLegalMoves;
    }

    public boolean foxHasNoLegalMoves() {
        if (getFoxLegalMoves().size() == 0) {
            return true;
        }
        return false;
    }

    public BoardState getTheBoard() {
        return theBoard;
    }

    public Point getSelectedPoint() {
        return selectedPoint;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

}
