import java.util.Comparator;

public class LengthComparator implements Comparator<GraphEdge> {

    @Override
    public int compare(GraphEdge v, GraphEdge t1) {
        if (v.length == t1.length) {
            return 0;
        } else if (v.length > t1.length) {
            return 1;
        } else return -1;
    }
}


