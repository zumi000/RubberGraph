import java.util.Comparator;

public class PixelComparator implements Comparator<Pixel> {

    @Override
    public int compare(Pixel pixel, Pixel t1) {
        if (pixel.y == t1.y) {
            if (pixel.x < t1.x) {
                return -1;
            } else return 1;
        } else if (pixel.y < t1.y) {
            return -1;
        } else return 1;
    }

}


