
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GraphMethods {
    ArrayList <Pixel> points = new ArrayList<>();
    ArrayList <GraphEdge> graphEdges = new ArrayList<>();
    ArrayList<GraphEdge> edgesWithoutCrossing = new ArrayList<>();
    Graph gr = new Graph();

    int goodColor = new Color(0, 180, 0).getRGB();
    int badColor = new Color(180, 0, 0).getRGB();
    int countColor = new Color(0, 0, 180).getRGB();
    int anotherColor = new Color(90, 90, 90).getRGB();



    public void readCSVFile(String fileName) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new java.io.File(fileName)));
            String line = null;
                while ((line = reader.readLine()) != null) {
                String[] koords = line.split(";");
                points.add(new Pixel((int) Math.floor(Double.parseDouble(koords[1])),
                        (int) Math.floor(Double.parseDouble(koords[2])), Integer.parseInt(koords[0]), false));
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
        Collections.sort(points, new PixelComparator());
    }

    public void corrAndAddRef() {
        points.get(10).y = 44;
        points.get(11).y = 44;
        points.get(21).y = 80;
        points.get(22).y = 80;
        points.get(44).y = 222;
        points.get(45).y = 222;
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

            for (int i = 0; i < points.size(); i++) {


                System.out.println(i + 1 + ": " + points.get(i).x + "; " + points.get(i).y + " : " + image.getRGB(points.get(i).x, points.get(i).y));
                image.setRGB(points.get(i).x, points.get(i).y, goodColor);

                if (image.getRGB(points.get(i).x + 1, points.get(i).y) == -1 &&
                        image.getRGB(points.get(i).x - 1, points.get(i).y) == -1 &&
                        image.getRGB(points.get(i).x, points.get(i).y + 1) == -1 &&
                        image.getRGB(points.get(i).x, points.get(i).y - 1) == -1) {
                    image.setRGB(points.get(i).x, points.get(i).y, badColor);
                    image.setRGB(points.get(i).x + 1, points.get(i).y, badColor);
                    image.setRGB(points.get(i).x - 1, points.get(i).y, badColor);
                    image.setRGB(points.get(i).x, points.get(i).y + 1, badColor);
                    image.setRGB(points.get(i).x, points.get(i).y - 1, badColor);
                    System.out.println("error" + errorcounter + ">> " + points.get(i).x + "; " + points.get(i).y + " közvetlen környezetében nem található fekete pixel!");
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
            for (int i = 0; i < points.size(); i++) {
                if (points.get(i).vl == 0) {
                    image.setRGB(points.get(i).x, points.get(i).y, badColor);
                } else if (points.get(i).vl == 1) {
                    image.setRGB(points.get(i).x, points.get(i).y, goodColor);
                } else if (points.get(i).vl == 2) {
                    image.setRGB(points.get(i).x, points.get(i).y, countColor);
                } else {
                    image.setRGB(points.get(i).x, points.get(i).y, anotherColor);
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


            for (int i = 0; i < points.size(); i++) {
                Pixel P1 = points.get(i);

                for (int j = i + 1; j < points.size(); j++) {


                    int blackCounter = 0;
                    int xIrany = 0;
                    Pixel P2 = points.get(j);
                    Pixel Ptest = new Pixel(P1.x, P1.y, P1.vl, P1.isvisited);
                    float vx = P2.x - P1.x;
                    float vy = P2.y - P1.y;
                    double length = Math.sqrt(vx*vx+vy*vy);
                    if (vx > 0) {
                        xIrany = 1;
                    }
                    if (vx < 0) {
                        xIrany = -1;
                    }

                    if (vy == 0) {
                        while ((Ptest.x != P2.x)) {


                            if (image.getRGB(Ptest.x, Ptest.y) < -1) {
                                blackCounter++;
                            }
                            Ptest.x = Ptest.x + xIrany * 1;
                        }
                    } else {

                        while ((Ptest.y != P2.y)) {
                            if (Math.floor((float) (Ptest.x - P1.x) * ((float) (vy / vx))) != Math.floor((float) (Ptest.x - P1.x + xIrany) * ((float) (vy / vx)))) {
                                if (image.getRGB(Ptest.x, Ptest.y) < -1) {
                                    blackCounter++;
                                }
                                Ptest.y = Ptest.y + 1;
                                //System.out.println("y szerint uj koordinata: " + Ptest[1]);
                            }
                            if (Math.floor((float) (Ptest.y - P1.y) * ((float) (vx / vy))) != Math.floor((float) (Ptest.y - P1.y + 1) * ((float) (vx / vy)))) {
                                if (image.getRGB(Ptest.x, Ptest.y) < -1) {
                                    blackCounter++;
                                }
                                Ptest.x = Ptest.x + xIrany * 1;
                                //System.out.println("x szerint uj koordinata: " + Ptest[0]);
                            }
                        }
                    }

                    if (blackCounter > 0) {
                        System.out.println(blackCounter + " db fekete pixelt érintett");
                        summCount++;
                    } else {
                        System.out.println("Ez egy gráfél");
                        graphEdges.add(new GraphEdge(P1, P2, length));
                        summCount++;
                        goodCount++;
                    }
                }
            }
            System.out.println("Good: " + goodCount);
            System.out.println("Summ: " + summCount);
            for (Object o : graphEdges) {
                //System.out.println(o.toString());
            }
    }

    public void drowEdges(String filename) {
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }

            for (int i = 0; i < graphEdges.size(); i++) {
                Pixel P1 = graphEdges.get(i).from;
                Pixel P2 = graphEdges.get(i).to;
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
                    }
                    image.setRGB(P2.x, P2.y, countColor);
                    image.setRGB(P1.x, P1.y, countColor);

            }
            File outputfile = new File(filename);
            ImageIO.write(image, "bmp", outputfile);
        } catch (IOException e) {
        }
    }

    public void sort() {
        graphEdges.sort(new LengthComparator());
        //for (Object o : lessEdges) { System.out.println(o.toString());
        //}
    }

    public void checkCrossing () {
        edgesWithoutCrossing.add(graphEdges.get(0));
        for (int j = 1; j < graphEdges.size(); j++) {
            GraphEdge test = graphEdges.get(j);
            int counter = 0;
            for (int i = 0; i < edgesWithoutCrossing.size(); i++) {
                GraphEdge test2 = edgesWithoutCrossing.get(i);

                System.out.println(edgesWithoutCrossing.size());

                if (test.equation.x == test2.equation.x &&
                        test.equation.y == test2.equation.y) {
                    break;
                } else {
                    double[] crossPointCoordinate = gr.solveEquation(test, test2);

                    System.out.println(Arrays.toString(crossPointCoordinate));
                    System.out.println(test.toString());
                    System.out.println(test2.toString());
                    if ((crossPointCoordinate[1] == test.from.y &&
                            crossPointCoordinate[0] == test.from.x) ||
                            (crossPointCoordinate[1] == test.to.y &&
                                    crossPointCoordinate[0] == test.to.x) ||
                            (crossPointCoordinate[1] == test2.from.y &&
                                    crossPointCoordinate[0] == test2.from.x) ||
                            (crossPointCoordinate[1] == test2.to.y &&
                                    crossPointCoordinate[0] == test2.to.x)) {
                        break;


                    }

                    else if (test.from.x < test.to.x && test2.from.x < test2.to.x) {
                        if (crossPointCoordinate[0] > test.from.x &&
                                crossPointCoordinate[0] < test.to.x &&
                                crossPointCoordinate[0] > test2.from.x &&
                                crossPointCoordinate[0] < test2.to.x) {
                            counter++;
                        }
                    } else if (test.from.x < test.to.x && test2.from.x > test2.to.x) {
                        if (crossPointCoordinate[0] > test.from.x &&
                                crossPointCoordinate[0] < test.to.x &&
                                crossPointCoordinate[0] > test2.to.x &&
                                crossPointCoordinate[0] < test2.from.x) {
                            counter++;
                        }
                    } else if (test.from.x > test.to.x && test2.from.x > test2.to.x) {
                        if (crossPointCoordinate[0] > test.to.x &&
                                crossPointCoordinate[0] < test.from.x &&
                                crossPointCoordinate[0] > test2.to.x &&
                                crossPointCoordinate[0] < test2.from.x) {
                            counter++;
                        }
                    } else if (test.from.x > test.to.x && test2.from.x < test2.to.x) {
                        if (crossPointCoordinate[0] > test.to.x &&
                                crossPointCoordinate[0] < test.from.x &&
                                crossPointCoordinate[0] > test2.from.x &&
                                crossPointCoordinate[0] < test2.to.x) {
                            counter++;
                        }
                    }


                }
            }
            if (counter == 0) {
                edgesWithoutCrossing.add(test);
            }
            System.out.println(edgesWithoutCrossing.size());
        }

    }

    public void drawedgesWithoutCrossing (String filename) {
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }

            for (Object o : edgesWithoutCrossing) {
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
}








