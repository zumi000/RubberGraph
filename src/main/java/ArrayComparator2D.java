import java.util.Comparator;

public class ArrayComparator2D implements Comparator<int[]> {
        @Override
        public int compare(int[] ints, int[] t1) {
            if (ints[1] == t1[1]) {
                if (ints[0] < t1[0]) {
                    return -1;
                } else return 1;
            }

            else if (ints[1] < t1[1]) {
                return -1;
            } else return 1;
        }


    }

