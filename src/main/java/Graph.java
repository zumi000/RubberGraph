import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Graph {
    ArrayList<Pixel> pointsOfSelectedEdges = new ArrayList<>();
    int countColor = new Color(0, 0, 180).getRGB();
    ArrayList<GraphEdge> lessEdges = new ArrayList<>();

    public void addEdge(GraphEdge edge) { //bejarashoz kell
        if (!pointsOfSelectedEdges.contains(edge.from)) {
            pointsOfSelectedEdges.add(edge.from);
        }
        if (!pointsOfSelectedEdges.contains(edge.to)) {
            pointsOfSelectedEdges.add(edge.to);
        }
        lessEdges.add(edge);
    }

    public void sort() {
        lessEdges.sort(new LengthComparator());
        //for (Object o : lessEdges) { System.out.println(o.toString());
        //}
    }

    public void removeLongEdge(int howMany) {
        for (int i = 0; i < howMany; i++) {
            lessEdges.remove(lessEdges.size() - 1);
        }
    }
/*
    Set<Pixel> visitedPixels = new HashSet<>();
    public void unVirginNeighbours (Pixel pixel) {
            if (pixel.isvisited == false) {
                visitedPixels.add(pixel);


                for (int j = 0; j < lessEdges.size(); j++) {
                    if (visitedPixels.lessEdges.get(j).from.isvisited == false)

            }


            for (int i = 0; i < lessEdges.size(); i++) {
                if (lessEdges.get(i).from == point) {
                    unVirginNeighbours(lessEdges.get(i).from);
                }
            }

        }

    }
*/


    public void drowLessEdges (String filename) {
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }

            for (Object o : lessEdges) {
                Pixel P1 = ((GraphEdge) o).from;
                Pixel P2 = ((GraphEdge) o).to;
                int xIrany = 0;
                Pixel Ptest = new Pixel(P1.x, P1.y, P1.vl, P1.isvisited);
                float vx = P2.x - P1.x;
                float vy = P2.y - P1.y;
                if (vx > 0) {
                    xIrany = 1;
                }
                if (vx < 0) {
                    xIrany = -1;
                }

                if (vy == 0) {
                    while ((Ptest.x != P2.x)) {
                        image.setRGB(Ptest.x, Ptest.y, countColor);
                        Ptest.x = Ptest.x + xIrany * 1;
                    }
                } else {
                    while ((Ptest.y != P2.y)) {
                        if (Math.floor((float) (Ptest.x - P1.x) * ((float) (vy / vx))) != Math.floor((float) (Ptest.x - P1.x + xIrany) * ((float) (vy / vx)))) {
                            image.setRGB(Ptest.x, Ptest.y, countColor);
                            Ptest.y = Ptest.y + 1;
                        }
                        if (Math.floor((float) (Ptest.y - P1.y) * ((float) (vx / vy))) != Math.floor((float) (Ptest.y - P1.y + 1) * ((float) (vx / vy)))) {
                            image.setRGB(Ptest.x, Ptest.y, countColor);
                            Ptest.x = Ptest.x + xIrany * 1;
                        }
                    }
                    image.setRGB(P2.x, P2.y, countColor);
                    image.setRGB(P1.x, P1.y, countColor);

                }
            }
            File outputfile = new File(filename + ".bmp");
            ImageIO.write(image, "bmp", outputfile);
        } catch (IOException e) {
        }

    }

    public double [] solveEquation (GraphEdge edge1, GraphEdge edge2)
    {
        String [] input = edge1.equation.toString().split(" ");
        String [] input2 = edge2.equation.toString().split(" ");
        int n = 2;
        double [][]mat = new double[n][n];
        double [][]constants = new double[n][1];
        for (int i=0; i<n-1; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = Integer.parseInt(input[j]);
                mat[i+1][j] = Integer.parseInt(input2[j]);
            }
            constants[i][0] = Integer.parseInt(input[input.length-1]);
            constants[i+1][0] = Integer.parseInt(input2[input2.length-1]);
        }


        double inverted_mat[][] = invert(mat);

        double result[][] = new double[n][1];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < 1; j++)
            {
                for (int k = 0; k < n; k++)
                {
                    result[i][j] = result[i][j] + inverted_mat[i][k] * constants[k][j];
                }
            }
        }


        double [] resultCoordinates = new double[2];
        resultCoordinates[0] = result[0][0];
        resultCoordinates[1] = result[1][0];
        return resultCoordinates;
    }

    public static double[][] invert(double a[][])
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    public static void gaussian(double a[][], int index[])
    {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i=0; i<n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i)
        {
            double c1 = 0;
            for (int j=0; j<n; ++j)
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }


        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j)
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i)
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1)
                {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i)
            {
                double pj = a[index[i]][j]/a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }




}
