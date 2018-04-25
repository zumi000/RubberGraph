import java.io.IOException;

//white rgb(255,255,255)
//black rgb(0,0,0)

public class RubberGraph {
    public static void main(String[] args) throws IOException {
        GraphMethods gr = new GraphMethods();
        System.out.println(System.getProperty("user.dir"));
        gr.readCSVFile("./4gumi.csv");
        //gr.corrAndAddRef();
        //gr.colorize();


        //gr.checkColor();
        gr.drawLine();
    }
}
