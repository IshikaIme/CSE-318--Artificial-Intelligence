import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LatinSolve {

    public static void main (String[] args)
    {

        try {
            File myObj = new File("in.txt");
            Scanner myReader = new Scanner(myObj);
            int num= Integer.parseInt(myReader.nextLine());
            Board newBoard= new Board(num);
            int line=0;
            String[] totalInput = new String[num];
            while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 totalInput[line]=data;
                 line++;
            }
            myReader.close();
            newBoard.assignBoard(num,totalInput);
            newBoard.printBoard();

          //  cell slot = newBoard.getBoardCell(0,1);
            //int mask = slot.getMaskOfDomain(newBoard,slot);
         //   System.out.println(mask);

          //  System.out.println(slot.getValue());
//            ArrayList<Integer> slotList= slot.getListOfDomain(newBoard,slot);
//            for (int i = 0; i < slotList.size(); i++) {
//                System.out.print(slotList.get(i) + " ");
//            }
//            System.out.println();

           // System.out.println("dom count "+ slot.getDomainCount(newBoard,slot));

            System.out.println("Choose one: ");
            System.out.println("1. VAH1");
            System.out.println("2. VAH2");
            System.out.println("3. VAH3");
            System.out.println("4. VAH4");
            System.out.println("5. VAH5");
            Scanner scanner= new Scanner(System.in);
            int choice= scanner.nextInt();
            if(choice==1) {
                System.out.println("Solving in VAH1");

                System.out.println("Choose any: 1. Backtracking  2.Forward checking");
                int way= scanner.nextInt();
                newBoard.solveBoardUsingVAH("VAH1" ,way);
                newBoard.printBoard();
                newBoard.Report();
            }

            else if(choice==2) {
                System.out.println("Solving in VAH2");
                System.out.println("Choose any: 1. Backtracking  2.Forward checking");
                int way= scanner.nextInt();
                newBoard.solveBoardUsingVAH("VAH2" ,way);
                newBoard.printBoard();
                newBoard.Report();

            }

            else if(choice==3) {
                System.out.println("Solving in VAH3");
                System.out.println("Choose any: 1. Backtracking  2.Forward checking");
                int way= scanner.nextInt();
                newBoard.solveBoardUsingVAH("VAH3" ,way);
                newBoard.printBoard();
                newBoard.Report();
            }

            else if(choice==4) {
                System.out.println("Solving in VAH4");
                System.out.println("Choose any: 1. Backtracking  2.Forward checking");
                int way= scanner.nextInt();
                newBoard.solveBoardUsingVAH("VAH4" ,way);
                newBoard.printBoard();
                newBoard.Report();
            }

            else if(choice==5) {
                System.out.println("Solving in VAH5");
                System.out.println("Choose any: 1. Backtracking  2.Forward checking");
                int way= scanner.nextInt();
                newBoard.solveBoardUsingVAH("VAH5" ,way);
                newBoard.printBoard();
                newBoard.Report();
            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





    }
}


