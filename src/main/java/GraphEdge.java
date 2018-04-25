import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.Arrays;

public class GraphEdge {

    public int[] from;
    public int[] to;

    public GraphEdge(int[] from, int[] to) {
        this.from = from;
        this.to = to;
    }

    public String toString(){
        return (Arrays.toString(from) + "->" + Arrays.toString(to));
    }
}



