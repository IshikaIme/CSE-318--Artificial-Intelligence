public class Performance {
    private long nodes;
    private long backtrack;



    public Performance(long nodes, long backtrack) {
        this.nodes = nodes;
        this.backtrack = backtrack;
        //TimeDiff=0;
    }
    public Performance() {
        this.nodes =0;
        this.backtrack = 0;
       // this.TimeDiff=0;
    }

    public long increaseNode()
    {
        this.nodes++;
        return nodes;
    }

    public long increaseBacktrack()
    {
        this.backtrack++;
        return backtrack;
    }

    public long getNodes() {
        return nodes;
    }



    public long getBacktrack() {
        return backtrack;
    }






}
