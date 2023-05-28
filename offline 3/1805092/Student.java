import java.util.ArrayList;

public class Student {
    private int id;
    private ArrayList<Integer> Courses_Id_Taken;
    private ArrayList<Course> Courses_taken;


    public Student(Graph g,int id, ArrayList<Integer>courses)
    {
        this.id=id;
        this.Courses_Id_Taken= courses;
        this.Courses_taken=courses_taken_from_ID(g, courses);
    }

    public ArrayList<Course> courses_taken_from_ID(Graph g, ArrayList<Integer>coursesID)
    {
        ArrayList<Course> courses_arraylist= new ArrayList<>();
        courses_arraylist.add(null);
        for (int i = 0; i < coursesID.size(); i++) {
            Course course= g.getnodes().get(coursesID.get(i));
           courses_arraylist.add(course);
        }
        return courses_arraylist;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getCourses_Id_Taken() {
        return Courses_Id_Taken;
    }

    public ArrayList<Course> getCourses_taken() {
        return Courses_taken;
    }

    public void setCourses_taken(ArrayList<Course> courses_taken) {
        Courses_taken = courses_taken;
    }


}
