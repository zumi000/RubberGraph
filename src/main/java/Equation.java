public class Equation {

    // e: x + y = const
    int x;
    int y;
    int constant;

    public Equation(int x, int y, int constant) {
        this.x = x;
        this.y = y;
        this.constant = constant;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + constant;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
