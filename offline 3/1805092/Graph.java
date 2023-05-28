import java.util.*;

public class Graph {
    private int vertex;
    private ArrayList<Course> Node_Course;
    private ArrayList<Course> nodes;
    private ArrayList<Edge> Edge_between_Courses;
    private ArrayList<Student> students;
    private  int totaldays;

    private LinkedList<Course> adj[]; //Adjacency List

    public ArrayList<Course> getnodes() {
        return nodes;
    }

    public int getTotaldays() {
        int max=0;
        for (int i = 1; i < nodes.size(); i++) {
            Course crs= nodes.get(i);
            if(crs.getDay()>max)
                max= crs.getDay();
        }
        return max;
    }

    public Graph() {
        vertex=0;
        Node_Course = new ArrayList<>();
        Node_Course.add(null);
        nodes= new ArrayList<>();
        nodes.add(null);
        students = new ArrayList<>();
        students.add(null);
        Edge_between_Courses=new ArrayList<>();
        Edge_between_Courses.add(null);

    }

    public void printGraph() {
//        for (int i=1; i< Node_Course.size(); i++) {
//            Course cr= Node_Course.get(i);
////            System.out.println("Course id = " + cr.getId() + " Students = " + cr.getNo_of_students());
//            System.out.println(cr.toString());
//        }

//       // System.out.println("Student size "+ students.size());
//        for (int i=1; i< students.size(); i++) {
//            Student std= students.get(i);
//            System.out.println("Student id = " + std.getId() + " Courses = " + std.getCourses_Id_Taken());
//        }
//
//        System.out.println("Edge size "+ Edge_between_Courses.size());
//        for (Edge edge : Edge_between_Courses) {
//            System.out.println("Edge found from = " + edge.getFrom().getId()+ " and "+ edge.getTo().getId());
//        }

//        System.out.println("Adj print");
//        for (int i = 0; i < adj.length; i++) {
//            System.out.println(adj[i]);
//        }

        for (int i = 1; i < nodes.size(); i++) {
            System.out.println("Day "+ nodes.get(i).getDay());
        }
    }


    public void addCourse(Course course) {
        Node_Course.add(course);
        nodes.add(course);
    }

    public void addStudent(Student std) {
        students.add(std);
    }



    public void addEdge(Edge edge) {
        Edge_between_Courses.add(edge);

    }

//    public Course findCourse(int id)
//    {
//        for (Course crs: Node_Course) {
//            if(crs.getId()==id)
//            {
//                return crs;
//            }
//        }
//        return null;
//    }

    public void make_edge()
    {
        vertex=Node_Course.size();
        adj= new LinkedList[vertex];

        for (int i=1; i<vertex; i++) {
            adj[i]= new LinkedList<>();
        }


        for (int i=1; i<students.size(); i++) {
            Student std= students.get(i);
            ArrayList<Integer> courses_taken = std.getCourses_Id_Taken();
            int size= courses_taken.size();
            for (int x = 0; x <size-1 ; x++) {
                for (int j = x+1; j < size ; j++) {
                    Course from = Node_Course.get(courses_taken.get(x));
                    Course to = Node_Course.get(courses_taken.get(j));
                    Edge_between_Courses.add(new Edge(from, to));

                    if(!adj[from.getId()].contains(to)) {
                        adj[from.getId()].add(to);
                        from.incrementDegree();
                    }
                    if(!adj[to.getId()].contains(from)) {
                        adj[to.getId()].add(from);
                        to.incrementDegree();
                    }

                }
            }
        }
    }

//
    public Course get_largest_degree()
    {
        Course crs=null;
        int highest_degree= Integer.MIN_VALUE;
        for(int i=1; i< Node_Course.size(); i++) {
            if (Node_Course.get(i).getDegree_of_edge() >= highest_degree) {
                highest_degree = Node_Course.get(i).getDegree_of_edge();
                crs= Node_Course.get(i);

           //    System.out.println("Highest degree "+ crs);
            }
        }

        return crs;

    }

    public Course get_largest_enrollment()
    {
        Course crs=null;
        int highest_enrollment= Integer.MIN_VALUE;
        for(int i=1; i< Node_Course.size(); i++) {
            if (Node_Course.get(i).getNo_of_students() >= highest_enrollment) {
                highest_enrollment = Node_Course.get(i).getNo_of_students();
                crs= Node_Course.get(i);

            }
        }

        return crs;

    }

    public Course get_random()
    {
       if(!Node_Course.isEmpty())
           return Node_Course.get(0);
       else return null;
    }

    public void set_saturation(Course crs)
    {
        boolean [] color_track = new boolean [vertex-1];

        for (int i = 0; i < adj[crs.getId()].size(); i++) {
            color_track[adj[crs.getId()].get(i).getId()-1]=true;
        }

        int count=0;
        for (int i = 0; i < color_track.length; i++) {
            if(color_track[i])
                count++;
        }

        crs.setDegree_of_saturation(count);


    }

    public Course get_largest_saturated()
    {
        int max_satur_degree = 0;
        Course SAT_Course=null;
        for (int i=1; i< Node_Course.size(); i++) {
            Course Crs= Node_Course.get(i);
            if (Crs.getDegree_of_saturation() >= max_satur_degree
                    && Crs.getDay() == -1) {
                max_satur_degree = Crs.getDegree_of_saturation();
                SAT_Course=Crs;

            }
        }
//        System.out.println(nextNode.course.courseID + " " + nextNode.saturationDegree.size());
        return SAT_Course;
    }

    public void update_saturation_of_adjacent(int crsID)
    {
       // Course crs= Node_Course.get(crsID);
        if(adj[crsID]==null)
            return;

        for (int i = 0; i < adj[crsID].size(); i++) {
            set_saturation(adj[crsID].get(i));
        }
    }

    public Course get_from_vah(int choice)
    {
        if(choice==1)
            return get_largest_degree();
        else if(choice==2)
            return get_largest_enrollment();
        else if (choice==3)
            return get_random();
        else if (choice==4)
            return get_largest_saturated();
        else
            return null;
    }

    public void kempe_chain_interchange(Course course, int color1, int color2)
    {

        boolean visited[] = new boolean[vertex];
        LinkedList<Course> queue = new LinkedList<Course>();
        visited[0]= false;
        visited[course.getId()] = true;
        if (course.getDay() == color1)
            course.setDay(color2);

        queue.add(course);


        while (queue.size() != 0) {
            course = queue.poll();
          //  System.out.print(course + " ");

            Iterator<Course> course_iterator = adj[course.getId()].iterator();
            while (course_iterator.hasNext()) {
                Course next = course_iterator.next();
                if (!visited[next.getId()]) {
                    if(next.getDay()==color1) {
                        visited[next.getId()] = true;
                        next.setDay(color2);
                        queue.add(next);
                    }
                    else if( next.getDay()==color2){
                        visited[next.getId()] = true;
                        next.setDay(color1);
                        queue.add(next);
                    }
                }
            }
        }

    }


    public void course_scheduling(int choice)
    {
        int[] track_color = new int[vertex-1];
        Arrays.fill(track_color, -1);
        boolean [] availability_of_color = new boolean[vertex-1];
        Arrays.fill(availability_of_color, true);



        while (!Node_Course.isEmpty()) {
            Course Assigned_Course = get_from_vah(choice);
            if(Assigned_Course==null) {
                Node_Course.remove(Assigned_Course);
                continue;
            }

            track_color[Assigned_Course.getId()-1]= 0;
            Assigned_Course.setDay(0);

            Iterator<Course> adj_iterator = adj[Assigned_Course.getId()].iterator();

           while (adj_iterator.hasNext())
           {
               Course next_course= adj_iterator.next();
               int ind=next_course.getId()-1;

               if (track_color[ind] != -1)
                   availability_of_color[track_color[ind]] = false;
           }
           int color;
            for ( color = 0; color < vertex; color++) {
                if(availability_of_color[color])
                    break;;
            }

            track_color[Assigned_Course.getId()-1]= color;
            Assigned_Course.setDay(color);
            
            update_saturation_of_adjacent(Assigned_Course.getId()-1);
            Arrays.fill(availability_of_color,true);
            Node_Course.remove(Assigned_Course);

        }

        for (int i = 1; i < Node_Course.size(); i++) {
            set_adjacent_colors(Node_Course.get(i));
        }

        totaldays= track_color.length;

//        for (int i = 0; i < track_color.length ; i++) {
//            System.out.println("track color "  +track_color[i]);
//        }
    }

    public double calculate_penalty_exponential(int n)
    {
        double base = 2, exponent = 5 - n;

       double res;
       if(n<=5)
           res= Math.pow(base, exponent);
       else res=0;
       return res;
    }


    public double calculate_penalty_linear(int n)
    {
        double res;
        if(n<=5)
            res= 2* (5 - n);
        else res=0;
        return res;
    }

    public double get_penalty()
    {
        double sum_total_penalty = 0;
        for (int i = 1; i < students.size(); i++) {
            int penalty_for_a_student=0;
            ArrayList<Course> courses = students.get(i).getCourses_taken();
            for (int j = 1; j < courses.size()-1; j++) {
                for (int k = j+1; k < courses.size() ; k++) {
                    int gap= courses.get(j).getDay()-courses.get(k).getDay();
                //    System.out.println(gap);
                    if(gap<0)
                        gap= gap *-1;
                    penalty_for_a_student+=calculate_penalty_exponential(gap);
                }
               // System.out.println(penalty_for_a_student);
            }


            sum_total_penalty+= penalty_for_a_student;
        }
       // System.out.println("sum total " + sum_total_penalty);
        double avg_gap_total = sum_total_penalty/ students.size();
        return  avg_gap_total;

    }

    public ArrayList<Integer> set_adjacent_colors(Course crs)
    {
        ArrayList<Integer> adj_colors=new ArrayList<>();
        for (int i = 0; i < adj[crs.getId()].size(); i++) {
            if(!(adj[crs.getId()].contains(adj[crs.getId()].get(i).getDay())))
                  adj_colors.add(adj[crs.getId()].get(i).getDay());
        }
        crs.setColor_of_adjacent_nodes(adj_colors);
        return adj_colors;
    }

    public void minimize_penalty_using_kempe_chain() {
        double old_penalty= get_penalty();
        double new_penalty;
        Random random= new Random();
        int rand_int = random.nextInt(1, nodes.size());


        Course crs= nodes.get(rand_int);
            int color_prev= crs.getDay();
            //  System.out.println("color prev "+ color_prev + " new "+ crs.getDay());
            this.set_adjacent_colors(nodes.get(rand_int));
            ArrayList<Integer> colors= nodes.get(rand_int).getColor_of_adjacent_nodes();
            for( int color: colors)
            {
                if(crs.getDay() != color) {
                    kempe_chain_interchange(crs, crs.getDay(), color);
                    new_penalty = get_penalty();
                    // System.out.println(" new penalty =" +new_penalty);
                    if (new_penalty < old_penalty)
                        old_penalty = new_penalty;
                    else {
                        if (crs.getDay() == color_prev)
                            System.out.println("Unchanged");
                        kempe_chain_interchange(crs, color, color_prev);
                    }
                    // System.out.println("color prev "+ color+ " new "+ color_prev);
                }

            }



    }


//    public void minimize_penalty_using_kempe_chain() {
//        double old_penalty= get_penalty();
//        double new_penalty;
//        for (int i = 1; i < nodes.size(); i++) {
//            Course crs= nodes.get(i);
//            int color_prev= crs.getDay();
//          //  System.out.println("color prev "+ color_prev + " new "+ crs.getDay());
//            this.set_adjacent_colors(nodes.get(i));
//            ArrayList<Integer> colors= nodes.get(i).getColor_of_adjacent_nodes();
//            for( int color: colors)
//            {
//                if(crs.getDay() != color) {
//                    kempe_chain_interchange(crs, crs.getDay(), color);
//                    new_penalty = get_penalty();
//                    // System.out.println(" new penalty =" +new_penalty);
//                    if (new_penalty < old_penalty)
//                        old_penalty = new_penalty;
//                    else {
//                        if (crs.getDay() == color_prev)
//                            System.out.println("Unchanged");
//                        kempe_chain_interchange(crs, color, color_prev);
//                    }
//                    // System.out.println("color prev "+ color+ " new "+ color_prev);
//                }
//
//            }
//
//        }
//
//    }

    public void minimize_penalty_using_pair_swap() {
        double old_penalty= get_penalty();
        double new_penalty;
        Random random= new Random();
        int rand_int = random.nextInt(1, nodes.size());
    //    System.out.println(" rand" + rand_int);
        boolean flag= false;
        int rand_int_2 ;


        do{rand_int_2 = random.nextInt(1, nodes.size());}
        while(
                (rand_int==rand_int_2)||(adj[rand_int].contains(nodes.get(rand_int_2)))
        );

        int color1= nodes.get(rand_int).getDay();
        int color2= nodes.get(rand_int_2).getDay();

//        for (int i = 0; i < adj[nodes.get(rand_int).getId()].size(); i++) {
//            if(adj[nodes.get(rand_int).getId()].get(i).getDay()!=color2)
//                nodes.get(rand_int).setDay(color2);
//        }
//        for (int i = 0; i < adj[nodes.get(rand_int_2).getId()].size(); i++) {
//            if(adj[nodes.get(rand_int_2).getId()].get(i).getDay()!=color1)
//                nodes.get(rand_int_2).setDay(color1);
//        }


        set_adjacent_colors(nodes.get(rand_int));
        if(!nodes.get(rand_int).getColor_of_adjacent_nodes().contains(color2))
            nodes.get(rand_int).setDay(color2);

        set_adjacent_colors(nodes.get(rand_int_2));
        if(!nodes.get(rand_int_2).getColor_of_adjacent_nodes().contains(color1))
            nodes.get(rand_int_2).setDay(color1);




        new_penalty = get_penalty();
        // System.out.println(" new penalty =" +new_penalty);
        if (new_penalty < old_penalty)
            old_penalty = new_penalty;
        else
        {
            nodes.get(rand_int).setDay(color1);
            nodes.get(rand_int_2).setDay(color2);
        }


        //System.out.println(" rand" + rand_int + " rand2 "+ rand_int_2);

    }



    boolean isConflictFree() {
        for (int i = 1; i < nodes.size(); i++) {
            int color = nodes.get(i).getDay();

            for (int j=0; j< adj[nodes.get(i).getId()].size(); j++)
            {
                if (color == adj[nodes.get(i).getId()].get(j).getDay())
                    return false;
            }
        }
        return true;
    }
}
