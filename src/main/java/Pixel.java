public class Pixel {
    int x;
    int y;
    int vl;
    boolean isvisited;

    public Pixel(int x, int y, int vl, boolean isvisited) {
        this.x = x;
        this.y = y;
        this.vl = vl;
        this.isvisited = isvisited;
    }

    @Override
    public String toString() {
        return x + ";" + y + " ";
    }
}
