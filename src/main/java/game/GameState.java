package game;

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
        //is cell rabbit && adjacent (not diagonal): then move is legal
        //change colour to fox, and previous cell to empty
        //f rabbit
        //is cell empty && adjacent (not diagonal): then move is legal
        //change empty to rabbit, and previous cell to empty

        //switchPlayers();
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
        return !hasActiveSelection() && theBoard.getCell(point).equals(activePlayer.getColour());
    }

    public boolean isLegalMove(Point point) {
        // foxplayer = is cell top,bottom,left,right == rabbit
        // rabbitplayer = is cell t,b,l,r == empty
        return hasActiveSelection();
    }

    public boolean gameIsOver() {
        //if fox cells all equal same column: rabbit wins
        //if fox cells all equal same row: rabbit wins
        //if fox cells have no more legal moves: fox wins

        return !foxCellsInSameColumn() || !foxCellsInSameRow() || !boardHasEmptySpace();
    }

    public boolean foxCellsInSameColumn() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                //if fox cells all equal same column
                return true;
            }
        }
        return false;
    }

    public boolean foxCellsInSameRow() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                //if fox cells all equal same row
                return true;
            }
        }
        return false;
    }

    public boolean boardHasEmptySpace() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
               if (theBoard.getCell(r,c).equals(Cell.EMPTY)) {
                   return true;
               }
            }
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
