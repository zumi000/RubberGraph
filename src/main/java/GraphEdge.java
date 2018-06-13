public class GraphEdge {
    Pixel from;
    Pixel to;
    public double length;
    Equation equation;

    public GraphEdge(Pixel from, Pixel to, double length) {
        this.from = from;
        this.to = to;
        this.length = length;
        this.equation = new Equation(from.y-to.y, to.x-from.x, ((from.y-to.y)*from.x + (to.x-from.x)*from.y));
    }

    public GraphEdge(int a, int b, int c) {
        this.equation = new Equation(a, b, c);
    }

    public String toString(){
        return (from.x + "; " + from.y + "<->" + to.x + "; " + to.y + " length: " + length);
    }
}



