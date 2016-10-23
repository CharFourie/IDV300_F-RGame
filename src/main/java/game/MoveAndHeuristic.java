package game;

/**
 * Created by sabeehabanubhai on 2016/10/23.
 */
public class MoveAndHeuristic {

    private Point selection;
    private Point move;
    private int heuristic;

    public MoveAndHeuristic(Point selection, Point move, int heuristic) {
        this.selection = selection;
        this.move = move;
        this.heuristic = heuristic;
    }

    public Point getSelection() {
        return selection;
    }

    public Point getMove() {
        return move;
    }

    public int getHeuristic() {
        return heuristic;
    }
}
