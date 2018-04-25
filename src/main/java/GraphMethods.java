import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphMethods {
    int[][] points = new int[46][3];
    ArrayList <GraphEdge> graphEdges = new ArrayList<>();
    int goodColor = new Color(0, 180, 0).getRGB();
    int badColor = new Color(180, 0, 0).getRGB();
    int countColor = new Color(0, 0, 180).getRGB();
    int anotherColor = new Color(90, 90, 90).getRGB();

    public int[][] readCSVFile(String fileName) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new java.io.File(fileName)));
            String line = null;

            for (int i = 0; i < points.length; i++) {
                line = reader.readLine();
                String[] koords = line.split(";");
                points[i][0] = (int) Math.round(Double.parseDouble(koords[1]));
                points[i][1] = (int) Math.round(Double.parseDouble(koords[2]));
                points[i][2] = Integer.parseInt(koords[0]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Arrays.sort(points, new ArrayComparator2D());


        System.out.println(Arrays.deepToString(points));
        return points;
    }

    public void corrAndAddRef() {
        /*points[47][0] = 50; //feher
        points[47][1] = 50;
        points[46][0] = 100; //fekete
        points[46][1] = 100;*/

        points[10][1] = 44;
        points[11][1] = 44;
        points[21][1] = 80;
        points[22][1] = 80;
        points[44][1] = 222;
        points[45][1] = 222;
    }

    public void checkColor() throws IOException {
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }
            int errorcounter = 1;
            System.out.println(image.getRGB(100, 100));

            for (int i = 0; i < points.length; i++) {


                System.out.println(i + 1 + ": " + points[i][0] + "; " + points[i][1] + " : " + image.getRGB(points[i][0], points[i][1]));
                image.setRGB(points[i][0], points[i][1], goodColor);

                if (image.getRGB(points[i][0] + 1, points[i][1]) == -1 &&
                        image.getRGB(points[i][0] - 1, points[i][1]) == -1 &&
                        image.getRGB(points[i][0], points[i][1] + 1) == -1 &&
                        image.getRGB(points[i][0], points[i][1] - 1) == -1) {
                    image.setRGB(points[i][0], points[i][1], badColor);
                    image.setRGB(points[i][0] + 1, points[i][1], badColor);
                    image.setRGB(points[i][0] - 1, points[i][1], badColor);
                    image.setRGB(points[i][0], points[i][1] + 1, badColor);
                    image.setRGB(points[i][0], points[i][1] - 1, badColor);
                    System.out.println("error" + errorcounter + ">> " + points[i][0] + "; " + points[i][1] + " közvetlen környezetében nem található fekete pixel!");
                    errorcounter++;
                }

            }
            File outputfile = new File("checked.bmp");
            ImageIO.write(image, "bmp", outputfile);
        } catch (IOException e) {
        }
    }

    public void colorize() throws IOException {
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }
            for (int i = 0; i < points.length; i++) {
                if (points[i][2] == 0) {
                    image.setRGB(points[i][0], points[i][1], badColor);
                } else if (points[i][2] == 1) {
                    image.setRGB(points[i][0], points[i][1], goodColor);
                } else if (points[i][2] == 2) {
                    image.setRGB(points[i][0], points[i][1], countColor);
                } else {
                    image.setRGB(points[i][0], points[i][1], anotherColor);
                }
            }

            File outputfile = new File("4colors.bmp");
            ImageIO.write(image, "bmp", outputfile);
        } catch (IOException e) {
        }
    }

    public void edgesAddToArray() throws IOException {
        int summCount = 0;
        int goodCount = 0;
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }


            for (int i = 0; i < points.length; i++) {
                int[] P1 = points[i];

                for (int j = i + 1; j < points.length; j++) {


                    int blackCounter = 0;
                    int xIrany = 0;
                    int[] P2 = points[j];
                    int[] Ptest = {P1[0], P1[1]};
                    float vx = P2[0] - P1[0];
                    float vy = P2[1] - P1[1];
                    if (vx > 0) {
                        xIrany = 1;
                    }
                    if (vx < 0) {
                        xIrany = -1;
                    }

                    while ((Ptest[1] != P2[1])) { //&& image.getRGB(Ptest[0], Ptest[1]) != -1) {
                        //System.out.println("w");

                        if (Math.floor((float) (Ptest[0] - P1[0]) * ((float) (vy / vx))) != Math.floor((float) (Ptest[0] - P1[0] + xIrany) * ((float) (vy / vx)))) {
                            if (image.getRGB(Ptest[0], Ptest[1]) < -1) {
                                blackCounter++;
                            }
                            Ptest[1] = Ptest[1] + 1;
                            //System.out.println("y szerint uj koordinata: " + Ptest[1]);
                        }
                        if (Math.floor((float) (Ptest[1] - P1[1]) * ((float) (vx / vy))) != Math.floor((float) (Ptest[1] - P1[1] + 1) * ((float) (vx / vy)))) {
                            if (image.getRGB(Ptest[0], Ptest[1]) < -1) {
                                blackCounter++;
                            }
                            Ptest[0] = Ptest[0] + xIrany * 1;
                            //System.out.println("x szerint uj koordinata: " + Ptest[0]);
                        }
                    }

                    if (blackCounter > 0) {
                        System.out.println(blackCounter + " db fekete pixelt érintett");
                        summCount++;
                    } else {
                        System.out.println("Ez egy gráfél");
                        graphEdges.add(new GraphEdge(P1, P2));
                        summCount++;
                        goodCount++;
                    }
                }
            }
            System.out.println("Good: " + goodCount);
            System.out.println("Summ: " + summCount);
            for (Object o : graphEdges) {
                System.out.println(o.toString());
            }
    }

    public void drowEdges() {
        int counter = 0;
        System.out.println(graphEdges.size());
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }

            for (int i = 0; i < graphEdges.size(); i++) {
                int[] P1 = graphEdges.get(i).from;
                int[] P2 = graphEdges.get(i).to;
                int xIrany = 0;
                int[] Ptest = {P1[0], P1[1]};
                    float vx = P2[0] - P1[0];
                    float vy = P2[1] - P1[1];
                    if (vx > 0) {
                        xIrany = 1;
                    }
                    if (vx < 0) {
                        xIrany = -1;
                    }
                    while ((Ptest[1] != P2[1])) {
                        if (Math.floor((float) (Ptest[0] - P1[0]) * ((float) (vy / vx))) != Math.floor((float) (Ptest[0] - P1[0] + xIrany) * ((float) (vy / vx)))) {
                            image.setRGB(Ptest[0], Ptest[1], countColor);
                            Ptest[1] = Ptest[1] + 1;
                        }
                        if (Math.floor((float) (Ptest[1] - P1[1]) * ((float) (vx / vy))) != Math.floor((float) (Ptest[1] - P1[1] + 1) * ((float) (vx / vy)))) {
                            image.setRGB(Ptest[0], Ptest[1], countColor);
                            Ptest[0] = Ptest[0] + xIrany * 1;
                        }
                    }
                    //image.setRGB(P2[0], P2[1], countColor);
                    //image.setRGB(P1[0], P1[1], countColor);
                counter++;

            }

            System.out.println(counter);
            File outputfile = new File("realEdges.bmp");
            ImageIO.write(image, "bmp", outputfile);
        } catch (IOException e) {
        }
    }
}








