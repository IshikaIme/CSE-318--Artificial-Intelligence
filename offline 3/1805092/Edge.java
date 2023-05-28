public class Edge {
    private Course from;
    private Course to;
    public Edge(Course from, Course to)
    {
        this.from=from;
        this.to=to;
    }

    public Course getFrom() {
        return from;
    }

    public void setFrom(Course from) {
        this.from = from;
    }

    public Course getTo() {
        return to;
    }

    public void setTo(Course to) {
        this.to = to;
    }
}
