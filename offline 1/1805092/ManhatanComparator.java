
import java.util.Comparator;

public class ManhatanComparator implements Comparator<Board> {
    @Override
    public int compare(Board board1, Board board2) {
        int cost1 = board1.getcost() + board1.calculateManhattan();
        int cost2 = board2.getcost() + board2.calculateManhattan();
        return Integer.compare(cost1, cost2);
    }
}