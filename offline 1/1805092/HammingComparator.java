import java.util.Comparator;

class HammingComparator implements Comparator<Board> {
    @Override
    public int compare(Board board1, Board board2) {
        int cost1 = board1.getcost() + board1.calculateHamming();
        int cost2 = board2.getcost() + board2.calculateManhattan();
        return Integer.compare(cost1, cost2);
    }
}