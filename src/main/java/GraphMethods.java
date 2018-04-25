import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class GraphMethods {
    static int[][] points = new int[46][3];
    static int goodColor = new Color(0, 180, 0).getRGB();
    static int badColor = new Color(180, 0, 0).getRGB();
    static int countColor = new Color(0, 0, 180).getRGB();
    static int anotherColor = new Color(90, 90, 90).getRGB();

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
        points[5][1] = 222;
        points[6][1] = 222;
        points[19][1] = 80;
        points[20][1] = 80;
        points[38][1] = 44;
        points[45][1] = 44;

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

    public void drawLine() throws IOException {
        try {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("./gumi.bmp"));
            } catch (IOException ex) {
                new IOException(ex);
            }

            int blackCounter = 0;
            int xIrany = 0;
            int[] P1 = {20, 20};
            int[] P2 = {24, 24};
            int[] Ptest = {P1[0], P1[1]};
            float vx = P2[0] - P1[0];
            float vy = P2[1] - P1[1];
            if (vx > 0) {
                xIrany = 1;
            }
            if (vx < 0) {
                xIrany = -1;
            }
            System.out.println(Arrays.toString(P1));
            System.out.println(Arrays.toString(P2));


            while ((Ptest[0] != P2[0]) || (Ptest[1] != P2[1]) && image.getRGB(Ptest[0], Ptest[1]) != -1) {
                System.out.println("w");
                if (Math.floor((float)(Ptest[0] - P1[0]) * ((float)(vy / vx))) != Math.floor((float)(Ptest[0] - P1[0] + xIrany) * ((float)(vy / vx)))) {
                    if (image.getRGB(Ptest[0], Ptest[1]) != -1) {
                        blackCounter++;
                    }
                    image.setRGB(Ptest[0], Ptest[1], countColor);
                    Ptest[1] = Ptest[1] + 1;
                    System.out.println("y szerint uj koordinata: " + Ptest[1]);
                }
                if (Math.floor((float)(Ptest[1] - P1[1]) * ((float)(vx / vy))) != Math.floor((float)(Ptest[1] - P1[1] + 1) * ((float)(vx / vy)))) {
                    if (image.getRGB(Ptest[0], Ptest[1]) != -1) {
                        blackCounter++;
                    }
                    image.setRGB(Ptest[0], Ptest[1], countColor);
                    Ptest[0] = Ptest[0] + xIrany * 1;
                    System.out.println("x szerint uj koordinata: " + Ptest[0]);
                }
            }


        image.setRGB(P2[0], P2[1], goodColor);
            image.setRGB(P1[0], P1[1], goodColor);

            blackCounter--;
        if (blackCounter > 0) {
            System.out.println(blackCounter + " db fekete pixelt érintett");
        } else {
            System.out.println("Ez egy gráfél");
        }


            File outputfile = new File("line.bmp");
            ImageIO.write(image, "bmp", outputfile);
        } catch (IOException e) {
        }
    }


}





