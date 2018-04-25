import java.util.Arrays;

public class GraphEdge {

    public int[] from;
    public int[] to;

    public GraphEdge(int[] from, int[] to) {
        this.from = from;
        this.to = to;
    }

    public int[] getFrom() {
        return from;
    }

    public int[] getTo() {
        return to;
    }

    public String toString(){
        return (Arrays.toString(from) + "->" + Arrays.toString(to));
    }
}



