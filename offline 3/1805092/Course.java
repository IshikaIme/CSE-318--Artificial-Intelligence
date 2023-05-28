import java.util.ArrayList;

public class Course {
    private int id;
    private int No_of_students;
    private int day;
    private int degree_of_edge;
    private int degree_of_saturation;
    private ArrayList<Integer> color_of_adjacent_nodes;


    public ArrayList<Integer> getColor_of_adjacent_nodes() {
        return color_of_adjacent_nodes;
    }

    public void setColor_of_adjacent_nodes(ArrayList<Integer> color_of_adjacent_nodes) {
        this.color_of_adjacent_nodes = color_of_adjacent_nodes;
    }

    public Course(int id, int students)
    {
        this.id = id;
        this.No_of_students=students;
        this.day=-1;
        this.degree_of_edge =0;
        this.degree_of_saturation=0;

    }

    public int getDegree_of_edge() {
        return degree_of_edge;
    }

    public void setDegree_of_edge(int degree_of_edge) {
        this.degree_of_edge = degree_of_edge;
    }

    public int getDegree_of_saturation() {
        return degree_of_saturation;
    }

    public void setDegree_of_saturation(int degree_of_saturation) {
        this.degree_of_saturation = degree_of_saturation;
    }

    public void incrementDegree() {
        this.degree_of_edge++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo_of_students() {
        return No_of_students;
    }

    public void setNo_of_students(int no_of_students) {
        No_of_students = no_of_students;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", enrollment=" + getNo_of_students()+
                ", degree=" + degree_of_edge +
                ", date= "+ getDay()+
                '}';
    }


}
