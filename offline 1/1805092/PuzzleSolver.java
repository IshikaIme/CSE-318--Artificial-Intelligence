import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class PuzzleSolver {

    public static void main(String[] args) {


        try {
            File ff = new File("input.txt");
            Scanner scanner = new Scanner(ff);
            int k;
            k = scanner.nextInt();
            String[][] boardInput = new String[k][k];


            while (scanner.hasNext()) {
                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < k; j++) {
                        String entries = scanner.next();
                        boardInput[i][j] = entries;
                    }
                }
            }


            Board boardInitial = new Board(k, boardInput, null);
            boolean isSolvable = boardInitial.checkSolvability();


            if (isSolvable == false)
                System.out.println("Not solvable");
            else {


                System.out.println("Solving using hamming distance: ");
                PriorityQueue<Board> pq = new PriorityQueue<>(new HammingComparator());
                ArrayList<Board> visited = new ArrayList<>();
                pq.add(boardInitial);

                int count = 1;

                String[][] boardFinal = new String[k][k];

                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < k; j++) {
                        if (count == k * k)
                            boardFinal[i][j] = "*";
                        else
                            boardFinal[i][j] = Integer.toString(count);
                        count++;
                    }
                }


                int expandedNodeCount = 1;
                int exploredNodeCount = 0;
                while (!pq.isEmpty()) {
                    Board poppedBoard = pq.poll();
                    exploredNodeCount++;

                    String[][] popArray = poppedBoard.boardArray;
                    int match = 1;
                    for (int i = 0; i < k; i++) {
                        for (int j = 0; j < k; j++) {

                            if (!(popArray[i][j].equals(boardFinal[i][j])))
                                match = 0;

                        }
                    }
                    if (match == 1) {
                        System.out.println("Minimum number of moves : " + poppedBoard.getcost());
                        poppedBoard.printAllRecursiveBoards(poppedBoard);
                        break;
                    }
                    visited.add(poppedBoard);
                    List<Board> neighbours = poppedBoard.getNeighBours();

                    for (int i = 0; i < neighbours.size(); i++) {
                        if (!visited.contains(neighbours.get(i))) {
                            pq.add(neighbours.get(i));
                            expandedNodeCount++;


                        }

                    }
                }

                System.out.println("Expanded Node Count : " + expandedNodeCount);
                System.out.println("Explored Node Count : " + exploredNodeCount);


                System.out.println();
                System.out.println("Solving using manhattan distance: ");
                PriorityQueue<Board> pq2 = new PriorityQueue<>(new ManhatanComparator());
                ArrayList<Board> visited2 = new ArrayList<>();
                pq2.add(boardInitial);




                int expandedNodeCount2 = 1;
                int exploredNodeCount2 = 0;
                while (!pq2.isEmpty()) {
                    Board poppedBoard2 = pq2.poll();
                    exploredNodeCount2++;

                    String[][] popArray2 = poppedBoard2.boardArray;
                    int match2 = 1;
                    for (int i = 0; i < k; i++) {
                        for (int j = 0; j < k; j++) {

                            if (!(popArray2[i][j].equals(boardFinal[i][j])))
                                match2 = 0;

                        }
                    }
                    if (match2 == 1) {
                        System.out.println("Minimum number of moves : " + poppedBoard2.getcost());
                        poppedBoard2.printAllRecursiveBoards(poppedBoard2);
                        break;
                    }
                    visited2.add(poppedBoard2);
                    List<Board> neighbours2 = poppedBoard2.getNeighBours();

                    for (int i = 0; i < neighbours2.size(); i++) {
                        if (!visited2.contains(neighbours2.get(i))) {
                            pq2.add(neighbours2.get(i));
                            expandedNodeCount2++;


                        }

                    }
                }

                System.out.println("Expanded Node Count : " + expandedNodeCount2);
                System.out.println("Explored Node Count : " + exploredNodeCount2);




            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
