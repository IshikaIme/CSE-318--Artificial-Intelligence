import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph= new Graph();
        Scanner Course_scanner = new Scanner(new File("course.txt"));
        while (Course_scanner.hasNextLine())
        {
            String line = Course_scanner.nextLine();
            String[] crs= line.split(" ");
            Course course= new Course(Integer.parseInt(crs[0]), Integer.parseInt(crs[1]));
            graph.addCourse(course);


        }

        Scanner student_scanner = new Scanner(new File("student.txt"));
        int countID=1;
        while (student_scanner.hasNextLine())
        {
            String line = student_scanner.nextLine();
            String [] courses= line.split(" ");
            ArrayList<Integer> courseList= new ArrayList<>();

            for (int i = 0; i < courses.length; i++) {
                courseList.add(Integer.parseInt(courses[i]));
            }
            Student student= new Student(graph,countID,courseList);
            countID++;
            graph.addStudent(student);

        }

        System.out.println("Enter choice: ");
        System.out.println("1: Largest Degree");
        System.out.println("2: Largest Enrollment");
        System.out.println("3: Random");
        System.out.println("4. Largest Saturation Degree");
        Scanner console_scanner= new Scanner(System.in);
        int choice= console_scanner.nextInt();

        graph.make_edge();
        graph.course_scheduling(choice);

        System.out.println("total days " + graph.getTotaldays());
        System.out.println("penalty "+ graph.get_penalty());

        if (graph.isConflictFree())
            System.out.println("Conflict free");
        else
            System.out.println("Conflict detected");

        for (int i = 0; i <1000; i++) graph.minimize_penalty_using_kempe_chain();
        System.out.println("After 1000 iteration of kempe chain,  penalty "+ graph.get_penalty());
        for (int i = 0; i <1000; i++) graph.minimize_penalty_using_pair_swap();
        System.out.println("After 1000 iteration of pair swap, penalty "+ graph.get_penalty());

        if (graph.isConflictFree())
            System.out.println("Conflict free");
        else
            System.out.println("Conflict detected");





    }
}
