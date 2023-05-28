import java.util.*;
import java.util.HashMap;

public class Board {
    private int number;
    private cell[][] boardState;
    private int [] rowmasks;
    private int [] colmasks;
    private ArrayList<cell> EmptyCells = new ArrayList<>();
    private Performance performance;
    HashMap<cell,Integer> maintainDomains= new HashMap<>();

    public Performance getPerformance() {
        return performance;
    }

    public int getRowmasks(int index) {
        return rowmasks[index];
    }

    public int getColmasks(int index) {
        return colmasks[index];
    }

    public void setDomainOfAllEmptyCells(Board board)
    {
        for (cell cellEmpty: EmptyCells) {

            int mask = cellEmpty.getMaskOfDomain(board);
            int domainCount= board.countSetBits(mask);
            cellEmpty.setDomainCount(domainCount);
        }
    }

    public void setDegreeofAllEmptyCells(Board board) {
        for (cell empty : EmptyCells) {
            int i = empty.getPosX();
            int j = empty.getPosY();
            int countRowSetBit = board.countSetBits(rowmasks[i]);
            int countColSetBit = board.countSetBits(colmasks[j]);

            int totalSetBits = countColSetBit+countRowSetBit;
            empty.setUnassignedDegree(totalSetBits);
        }
    }

    public int getNumber() {
        return number;
    }

    public int setBit(int n, int i) {
        n |= (1<<i);
        return n;
    }

    public int resetBit(int n, int i) {
        n &= ~(1<<i);
        return n;
    }

    public int countSetBits(int mask) {
        int c=0;
        while(mask!=0) {
            mask&= (mask-1);
            c++;
        }
        return c;
    }

//    public cell[][] getBoardState() {
//        return boardState;
//    }
//
//    public cell getBoardCell(int row, int col) {
//        return boardState[row][col];
//    }

    public Board(int num)
     {
         performance = new Performance();
         this.number =num;
         boardState= new cell[number][number];
         for (int i = 0; i < number; i++) {
             for (int j = 0; j < number; j++) {
                 boardState[i][j]= new cell(i,j,0);
             }
         }

         rowmasks= new int[num];
         colmasks=new  int[num];
         for (int i = 0; i < num; i++) {
             rowmasks[i]=1<<(num+1);
             colmasks[i]=1<<(num+1);

             rowmasks[i]=rowmasks[i]-2;
             colmasks[i]=colmasks[i]-2;
         }

     }

    public void assignBoard(int n,String [] lines)
    {
        String inputLine;
        String[] vals;
        cell[][] newBoard= new cell[n][n];

        for (int i = 0; i < number; i++) {
            inputLine=removeLastChar(lines[i]);
            //System.out.println(inputLine);
            vals= inputLine.split(",");
            for (int j = 0; j < number; j++) {
                vals[j]=vals[j].replaceAll("\\s", "");
                cell newCell= new cell(i,j,Integer.parseInt(vals[j]));
                newBoard[i][j]= newCell;
            }
        }
        this.boardState=newBoard;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = newBoard[i][j].getValue();
                if (value != 0) {
                    this.rowmasks[i] = resetBit(rowmasks[i], value);
                    this.colmasks[j] = resetBit(colmasks[j], value);

                }
                else
                {
                   // cell empty = new cell(i,j,value);
                    newBoard[i][j].setListOfDomain(this);
                    EmptyCells.add(newBoard[i][j]);
                }
            }
        }
    }


    private String removeLastChar(String s)
    {
        return s.substring(0, s.length() - 1);
    }


    public void printBoard()
     {
         for(int i = 0; i< number; i++)
         {
             for (int j = 0; j < number; j++) {
                 System.out.print(boardState[i][j].getValue()+ " ");
             }
             System.out.println();
         }
     }





     public boolean IsAssignmentCompleted()
     {
         if(EmptyCells.isEmpty())
             return true;
         else return false;
     }



     public void solveBacktrackReport(String vah)
     {
         if(vah.equalsIgnoreCase("VAH1")|vah.equalsIgnoreCase("VAH3")|vah.equalsIgnoreCase("VAH4")) {
             setDomainOfAllEmptyCells(this);
         }
         if(vah.equalsIgnoreCase("VAH2")|vah.equalsIgnoreCase("VAH3")|vah.equalsIgnoreCase("VAH4"))
             setDegreeofAllEmptyCells(this);

         double startTime1 = System.currentTimeMillis();
         int ret= solveBacktracking(this,vah);
         double stopTime1= System.currentTimeMillis();
         double timeforBT= stopTime1-startTime1;
         if(ret==-1)
             System.out.println("Cant solve using backtracking.");
         else
             System.out.println("time for backtracking in milis: "+ timeforBT);
     }

    public void solveForwardCheckReport(String vah)
    {
        if(vah.equalsIgnoreCase("VAH1")|vah.equalsIgnoreCase("VAH3")|vah.equalsIgnoreCase("VAH4"))
            setDomainOfAllEmptyCells(this);
        if(vah.equalsIgnoreCase("VAH2")|vah.equalsIgnoreCase("VAH3")|vah.equalsIgnoreCase("VAH4"))
            setDegreeofAllEmptyCells(this);

        double startTime1 = System.currentTimeMillis();
        int ret= solveForwardChecking(vah);
        double stopTime1= System.currentTimeMillis();
        double timeforFC= stopTime1-startTime1;
        if(ret==-1)
            System.out.println("Can't solve using Forward Checking");
        else
            System.out.println("time for Forward Checking in milis: "+ timeforFC);
    }



    public void solveBoardUsingVAH(String vah, int choice) {

        if(choice==1) {
            System.out.println("1. Solving backtracking");
            solveBacktrackReport(vah);
        }

        else if(choice==2) {
            System.out.println("2. Solving forward checking");
            solveForwardCheckReport(vah);
        }
        else
            System.out.println("Not a valid input, choose 1 or 2");

    }


    public cell selectUnassignedCell(String vah) {
        if (vah.equalsIgnoreCase("VAH1")) {

            cell leastCell = EmptyCells.get(0);
            int leastDom = leastCell.getDomainCount();
            for (cell cell : EmptyCells) {
                if (leastDom >= cell.getDomainCount()) {
                    leastCell = cell;
                    leastDom = leastCell.getDomainCount();
                }
            }

            EmptyCells.remove(leastCell);

            return leastCell;
        }

        else if (vah.equalsIgnoreCase("VAH2")) {
            cell CellMaxUnassignedDegree = EmptyCells.get(0);
            int maxUnassignedDegree = CellMaxUnassignedDegree.getUnassignedDegree();
            for (cell cell : EmptyCells) {
                if (maxUnassignedDegree <= cell.getUnassignedDegree()) {
                    CellMaxUnassignedDegree= cell;
                    maxUnassignedDegree= cell.getUnassignedDegree();
                }
                }
            EmptyCells.remove(CellMaxUnassignedDegree);
//            this.printBoard();
//            System.out.println();
            return CellMaxUnassignedDegree;
            }

        else if (vah.equalsIgnoreCase("VAH3")) {
            cell leastCell1 =EmptyCells.get(0);
            cell leastCell2 = null;
            cell leastCell = null;

            int leastDom = leastCell1.getDomainCount();
         //   System.out.println(" dom count " + leastDom);

            for (cell cell : EmptyCells) {
                if (leastDom >= cell.getDomainCount()) {
                    leastCell1 = cell;
                    leastDom = leastCell1.getDomainCount();

                }
            }
            leastCell=leastCell1;

            //EmptyCells.remove(leastCell1);
         //   System.out.println("cell picked leastcell1= " + leastCell.getPosX()+ " "+ leastCell.getPosY());

            for (cell cell2 : EmptyCells) {
                if ((leastDom == cell2.getDomainCount())&& (cell2!=leastCell1)) {
                    leastCell2 = cell2;
                }
            }

            if(leastCell2!=null)
            {
                if(leastCell1.getUnassignedDegree()<= leastCell2.getUnassignedDegree())
                    leastCell=leastCell2;


            }

            EmptyCells.remove(leastCell);
//            this.printBoard();
//            System.out.println();
            return leastCell;

        }
        else if (vah.equalsIgnoreCase("VAH4")) {
            cell leastCell = EmptyCells.get(0);

            for (cell cell : EmptyCells) {

                int degree = leastCell.getUnassignedDegree();
//                if(degree==0)
//                    degree= Integer.MIN_VALUE;

                int cellDegree= cell.getUnassignedDegree();
//                if(cellDegree==0)
//                    cellDegree=  Integer.MIN_VALUE;

                double value1= leastCell.getDomainCount() * 1.0/ degree ;
                double value2 =cell.getDomainCount() * 1.0/ cellDegree ;





                if (value1 >=value2  ) {
                    leastCell = cell;
                    degree = leastCell.getUnassignedDegree();
                }
            }

            EmptyCells.remove(leastCell);
//            this.printBoard();
//            System.out.println();
            return leastCell;
        }
        else if (vah.equalsIgnoreCase("VAH5")) {

            if(EmptyCells.isEmpty())
                return null;

            cell random= EmptyCells.get(0);
            EmptyCells.remove(0);
            return random;


        }

            else return null;

    }

    public boolean checkConsistency(cell emptycell, int value)
    {
        int row= emptycell.getPosX();
        int col= emptycell.getPosY();
        int num= this.number;


        for (int i = 0; i < num ; i++) {
            if(boardState[i][col].getValue()==value)
                return false;
        }

        for (int j = 0; j < num ; j++) {
            if(boardState[row][j].getValue()==value)
                return false;
        }
        return true;
    }


    public int solveBacktracking(Board board, String vah)
    {

        if(IsAssignmentCompleted())
            return 0;
        cell unassignedCell= selectUnassignedCell(vah);
        ArrayList<Integer> listOfDomain = unassignedCell.getListOfDomain();

        for (int DomValue: listOfDomain)
        {
            performance.increaseNode();

            if(checkConsistency(unassignedCell,DomValue))
            {
                unassignedCell.setValue(DomValue);
                int result = solveBacktracking(board,vah);
                performance.increaseBacktrack();
                if(result!= -1)
                    return result;
                unassignedCell.setValue(0);

            }
        }


        EmptyCells.add(unassignedCell);
        return -1;
    }

    public int solveForwardChecking( String vah)
    {
//        boolean flag;

        if(IsAssignmentCompleted())
            return 0;
        cell unassignedCell= selectUnassignedCell(vah);
        ArrayList<Integer> listOfDomain = unassignedCell.getListOfDomain();

//        flag = false;
        for (int DomValue: listOfDomain)
        {
            performance.increaseNode();
            if(checkConsistency(unassignedCell,DomValue))
            {
                unassignedCell.setValue(DomValue);
                unassignedCell.setAssigned(true);
                boolean success= checkNullDomain(unassignedCell, DomValue);
                if(success)
                {
                    performance.increaseBacktrack();
//                    flag = true;
                    int result = solveForwardChecking(vah);
                    if(result!= -1)
                        return result;

                }
                unassignedCell.setValue(0);
                unassignedCell.setAssigned(false);

            }
        }
        //performance.increaseBacktrack();

        EmptyCells.add(unassignedCell);
        return -1;
    }



    public boolean checkDomsOfRowCols( cell unassignedCell,int DomValue)
    {

        int Row= unassignedCell.getPosX();
        int Col= unassignedCell.getPosY();
        int num= this.getNumber();



        for (int i = 0; i < num; i++) {
            if(!boardState[Row][i].getAssigned())
            {
                ArrayList<Integer> integerArrayList = boardState[Row][i].getListOfDomain();
                int DomSize = integerArrayList.size();
                if(integerArrayList.contains(DomValue))
                {

                    DomSize=DomSize-1;
                    if(DomSize==0)
                        return false;
                }

            }
        }
        for (int j = 0; j < num; j++) {
            if(!boardState[j][Col].getAssigned())
            {
                ArrayList<Integer> integerArrayList = boardState[j][Col].getListOfDomain();
                int DomSize = integerArrayList.size();
                if(integerArrayList.contains(DomValue))
                {

                    DomSize=DomSize-1;
                    if(DomSize==0)
                        return false;
                }

            }
        }
        for (int i = 0; i < num; i++) {
            if(!boardState[Row][i].getAssigned())
            {
                ArrayList<Integer> integerArrayList = boardState[Row][i].getListOfDomain();
                if(integerArrayList.contains(DomValue)) {
                    maintainDomains.put(boardState[Row][i], DomValue);
                    boardState[Row][i].RemoveFromDomain(DomValue);
                    boardState[Row][i].setDomainCount(integerArrayList.size()-1);
                }
            }
        }

        for (int j = 0; j < num; j++) {
            if(!boardState[j][Col].getAssigned()) {
                ArrayList<Integer> integerArrayList = boardState[j][Col].getListOfDomain();
                if (integerArrayList.contains(DomValue)) {
                    maintainDomains.put(boardState[j][Col], DomValue);
                    boardState[j][Col].RemoveFromDomain(DomValue);
                    boardState[j][Col].setDomainCount(integerArrayList.size()-1);
                }
            }
        }

        return true;

    }

    public boolean checkNullDomain ( cell unassignedCell,int DomValue)
    {
        int Row= unassignedCell.getPosX();
        int Col= unassignedCell.getPosY();
        int num= this.getNumber();



        for (int i = 0; i < num; i++) {
            if(!boardState[Row][i].getAssigned())
            {
                ArrayList<Integer> integerArrayList = boardState[Row][i].getListOfDomain();
                int DomSize = integerArrayList.size();
                if(integerArrayList.contains(DomValue))
                {

                    DomSize=DomSize-1;
                    if(DomSize==0)
                        return false;
                }

            }
        }
        for (int j = 0; j < num; j++) {
            if(!boardState[j][Col].getAssigned())
            {
                ArrayList<Integer> integerArrayList = boardState[j][Col].getListOfDomain();
                int DomSize = integerArrayList.size();
                if(integerArrayList.contains(DomValue))
                {

                    DomSize=DomSize-1;
                    if(DomSize==0)
                        return false;
                }

            }
        }
        return true;
    }

//    public int solveForwardChecking(Board board, String vah)
//    {
//
//        if(IsAssignmentCompleted())
//            return 0;
//        cell unassignedCell= selectUnassignedCell(vah);
//        ArrayList<Integer> listOfDomain = unassignedCell.getListOfDomain();
//
//        for (int DomValue: listOfDomain)
//        {
//            performance.increaseNode();
//            if(checkConsistency(unassignedCell,DomValue))
//            {
//                unassignedCell.setValue(DomValue);
//                unassignedCell.setAssigned(true);
//                boolean success= checkDomsOfRowCols(unassignedCell, DomValue);
//                if(success)
//                {
//                    performance.increaseBacktrack();
//                    int result = solveForwardChecking(board,vah);
//                    if(result!= -1)
//                        return result;
//                    restoreOldValues(unassignedCell);
//                }
//                unassignedCell.setValue(0);
//                unassignedCell.setAssigned(false);
//
//            }
//        }
//        //performance.increaseBacktrack();
//
//        EmptyCells.add(unassignedCell);
//        return -1;
//    }

    public void restoreOldValues( cell cellWorking) {
        int Row = cellWorking.getPosX();
        int Col = cellWorking.getPosY();
        int num = this.number;
        for (int i = 0; i < num; i++) {
            if (boardState[Row][i].getAssigned() | boardState[Row][i] == cellWorking) {
            } else {
                int value;
                if (maintainDomains.containsKey(boardState[Row][i])) {
                    value = maintainDomains.get(boardState[Row][i]);
                    boardState[Row][i].addToDomain(value);
                    boardState[Row][i].setDomainCount(boardState[Row][i].getDomainCount() + 1);
                }
            }
        }

        for (int j = 0; j < num; j++) {
            if (boardState[j][Col].getAssigned() | boardState[j][Col] == cellWorking) {
            } else {
                int value;
                if (maintainDomains.containsKey(boardState[j][Col])) {
                    value = maintainDomains.get(boardState[j][Col]);
                    boardState[j][Col].addToDomain(value);
                    boardState[j][Col].setDomainCount(boardState[j][Col].getDomainCount() + 1);
                }
            }
        }
    }

    public void Report()
    {
        Performance perf= this.getPerformance();
        System.out.println("Total nodes= "+ perf.getNodes());
        System.out.println("Total backtracking "+  perf.getBacktrack());

    }

    }


