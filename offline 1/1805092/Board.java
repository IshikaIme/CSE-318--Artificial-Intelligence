import java.util.ArrayList;
import java.util.List;

public class Board {
    String [][] boardArray;
    int num;
    private int level;
    private int cost;
    Board boardPrevious;

    public void setcost(int cost) {
        this.cost = cost;
    }
    public int getcost() {
        return this.cost ;
    }

    Board(int k, String [][] boardArr, Board boardPrev )
    {
        num=k;
        boardArray=boardArr;
        boardPrevious= boardPrev;
        if (boardPrev==null)
            cost= 0;
        else
            cost= boardPrev.getcost()+ 1;

    }

    public String[][] getBoardArray() {
        return boardArray;
    }

    public void setBoardArray(String[][] boardArray) {
        this.boardArray = boardArray;
    }

    public int [] getBlank()
    {
        int [] coOrdinate = new int[2];

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (boardArray[i][j].equals("*")) {
                    coOrdinate[0] = i;
                    coOrdinate[1] = j;

                }
            }
        }
        return coOrdinate;
    }


    public List<Board> getNeighBours()
    {

        List<Board> BoardNeighbour = new ArrayList<>();
        String [] ways = this.getWays();
        int [] blankPos= this.getBlank();
        int [] tobeSwapped = new int [2];

        for (int i = 0; i < ways.length; i++) {
            if(ways[i].equalsIgnoreCase("up"))
            {
             //   System.out.println(" up : ");
                if(blankPos[0]+1<num)
                    tobeSwapped[0]= blankPos[0]+1;
                tobeSwapped[1]= blankPos[1];
                Board boardNew= swapEntries(blankPos,tobeSwapped);
                BoardNeighbour.add(boardNew);
            //    boardNew.printBoard();

            }

            else if(ways[i].equalsIgnoreCase("down"))
            {
               // System.out.println(" down : ");
                if(blankPos[0]-1>=0)
                    tobeSwapped[0]= blankPos[0]-1;
                tobeSwapped[1]= blankPos[1];
                Board boardNew= swapEntries(blankPos,tobeSwapped);
                BoardNeighbour.add(boardNew);
             //   boardNew.printBoard();

            }
            else if(ways[i].equalsIgnoreCase("left"))
            {
               // System.out.println(" left : ");

                tobeSwapped[0]= blankPos[0];
                if(blankPos[1]+1<num)
                    tobeSwapped[1]= blankPos[1]+1;
                Board boardNew= swapEntries(blankPos,tobeSwapped);
                BoardNeighbour.add(boardNew);
             //   boardNew.printBoard();

            }
            else if(ways[i].equalsIgnoreCase("right"))
            {
              //  System.out.println(" right : ");

                tobeSwapped[0]= blankPos[0];
                if(blankPos[1]-1>=0)
                    tobeSwapped[1]= blankPos[1]-1;
                Board boardNew= swapEntries(blankPos,tobeSwapped);
                BoardNeighbour.add(boardNew);
           //     boardNew.printBoard();

            }
            else
                continue;
        }
        return BoardNeighbour;
    }

    public Board swapEntries(int[] src, int[] dest)
    {
        String [][] boardNew = new String[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                boardNew[i][j]= boardArray[i][j];
            }
        }
        String value1 = boardNew[src[0]][src[1]];
        String value2 = boardNew[dest[0]][dest[1]];

        String  temp = new String();

        temp= value1;
        value1= value2;
        value2=temp;

        boardNew[src[0]][src[1]]= value1;
        boardNew[dest[0]][dest[1]]= value2;

        Board boardCreated= new Board(num, boardNew, this);
        return boardCreated;

    }


    public String[] getWays() {

        String [] ways = null;

        int[] position  = this.getBlank();


        if ((position[0] == 0) && (position[1] == 0) ){

            ways= new String[]{"up", "left"};
        }

        else if ((position[0] == 0) && (position[1] == num - 1)) {

            ways= new String[]{"up", "right"};
        }

        else if ((position[0] == num-1) && (position[1] == 0)) {

            ways= new String[]{"down", "left"};
        }

        else if ((position[0] == num-1) && (position[1] == num-1)) {

            ways= new String[]{"down", "right"};
        }

        else if(position[0]==0)
        {
            ways= new String[]{"up", "left", "right"};
        }

        else if(position[0]==num-1)
        {
            ways= new String[]{"down", "left", "right"};
        }

        else if(position[1]==0)
        {
            ways= new String[]{"up", "left", "down"};
        }
        else if(position[0]==num-1)
        {
            ways= new String[]{"up", "down", "right"};
        }

        else
        {
            ways= new String[]{"up", "down", "left", "right"};
        }


        return ways;

    }

    public int calculateHamming()
    {
        int entry;
        entry=0;
        int count=1;
        String[][] boardFinal = new String[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if(count==num*num)
                    boardFinal[i][j]="*";
                else
                    boardFinal[i][j]= Integer.toString(count);
                count++;
            }
        }


        int totalEntry= (num * num);
        int[] flag= new int[totalEntry];

        for (int i = 0; i < num ; i++) {
            for (int j = 0; j < num; j++) {
                if (this.boardArray[i][j].equals("*"))
                    flag[entry] = 0;
                else{
                    if (this.boardArray[i][j].equals(boardFinal[i][j]))
                        flag[entry] = 0;
                    else
                        flag[entry] = 1;
                    entry++;
                }
            }
        }
        int countA=0;
        //System.out.println("Hamming: ");
        for (int i=0 ; i<totalEntry; i++)
        {

            //System.out.print(flag[i]+ " ");
                countA+=flag[i];
        }
        return countA;
    }


    public int calculateManhattan()
    {
        int totalEntry= (num * num);
        int[]TotalDistance= new int[totalEntry];

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                String x = this.boardArray[i][j];
                if (!x.equals("*")) {
                    int rowFinal = (Integer.parseInt(x) - 1) / num;
                    int colFinal = (Integer.parseInt(x) - 1) % num;
                    TotalDistance[Integer.parseInt(x)]= Math.abs(rowFinal-i) + Math.abs(colFinal-j);

                }
            }
        }
        int sum=0;
        for (int i = 1; i < totalEntry; i++) {
         //   System.out.print(TotalDistance[i]+ " ");
            sum+=TotalDistance[i];
        }
        return sum;
    }


    public boolean checkSolvability()
    {
        boolean isSolvable=false;
        String [] array = new String[num*num-1];
        int index=0;
        int inversionCount=0;
        while(index<num*num-1) {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    if (!(this.boardArray[i][j]).equals("*")) {
                        array[index] = this.boardArray[i][j];
                        index++;
                    }
                }
            }
        }
//        System.out.println("Array ");
        for (int i = 0; i < num*num-1; i++) {
            for (int j = i; j < num*num-1; j++) {
                if(Integer.parseInt(array[i])>Integer.parseInt(array[j]))
                    inversionCount++;
            }
        }

       // System.out.println("Inversion Count "+ inversionCount);

        if(num%2==1)
        {
            if(inversionCount%2==0)
            {
                isSolvable=true;
            }
        }
        else
        {
            int blank[]= this.getBlank();
            if((blank[0]%2==0)&&(inversionCount%2==1))
                isSolvable=true;
            else if ((blank[0]%2==1)&&(inversionCount%2==0))
                isSolvable=true;

        }
        return isSolvable;
    }

    public void printAllRecursiveBoards(Board curBoard)
    {
        if (curBoard==null)
            return;
        printAllRecursiveBoards(curBoard.boardPrevious);
        curBoard.printBoard();
    }

    public void printBoard()
    {
        System.out.println("The board state now:");
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                System.out.print(this.boardArray[i][j] + " ");
            }
            System.out.println();
        }
    }

}
