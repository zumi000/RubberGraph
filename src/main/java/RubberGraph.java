import com.sun.org.apache.xalan.internal.xsltc.dom.ExtendedSAX;
import sun.security.util.Cache;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//white rgb(255,255,255)
//black rgb(0,0,0)

public class RubberGraph {
    public static void main(String[] args) throws IOException {
        GraphMethods gr = new GraphMethods();
        Graph graph = new Graph();
        System.out.println(System.getProperty("user.dir"));
        gr.readCSVFile("./gumi.csv");
        //gr.corrAndAddRef();
        gr.colorize();
        //gr.checkColor();
        gr.edgesAddToArray();
        gr.drowEdges("allEdges");

        for (Object o : gr.graphEdges) {
            graph.addEdge((GraphEdge) o);
        }
        gr.sort();
        gr.checkCrossing();


        //graph.removeLongEdge(0);
        //graph.drowLessEdges("all");
        //graph.unVirginNeighbours(graph.lessEdges.get(0).from);
        int picnum = gr.edgesWithoutCrossing.size();
        int filename = gr.edgesWithoutCrossing.size();
        for (int i = 0; i < picnum; i++) {
            gr.drawedgesWithoutCrossing("./RUBBER14/" + String.valueOf(filename));
            gr.edgesWithoutCrossing.remove(gr.edgesWithoutCrossing.size() - 1);
            filename--;
        }


    }


}

