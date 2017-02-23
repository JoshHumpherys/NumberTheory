import java.util.ArrayList;
import java.util.List;

public class ExtendedGCD extends TabularAlgorithm {
    int Q_INDEX = 0;
    int R_INDEX = 1;
    int X_INDEX = 2;
    int Y_INDEX = 3;
    int A_INDEX = 4;
    int B_INDEX = 5;
    int X2_INDEX = 6;
    int X1_INDEX = 7;
    int Y2_INDEX = 8;
    int Y1_INDEX = 9;
    List<int[]> rows;
    int a;
    int b;
    int gcd;
    int x;
    int y;
    String[] labels = new String[]{"q", "r", "x", "y", "a", "b", "x2", "x1", "y2", "y1"};
    ExtendedGCD(int a, int b) {
        rows = new ArrayList<>();
        if(Math.abs(a) < Math.abs(b)) {
            int temp = a;
            a = b;
            b = temp;
        }
        this.a = a;
        this.b = b;
        rows.add(new int[]{0, 0, 0, 0, Math.abs(a), Math.abs(b), 1, 0, 0, 1});
    }

    void run() {
        while(!finished()) {
            int[] lastRow = getLastRow();
            int[] newRow = new int[10];
            newRow[Q_INDEX] = lastRow[A_INDEX] / lastRow[B_INDEX];
            newRow[R_INDEX] = mod(lastRow[A_INDEX], lastRow[B_INDEX]);
            newRow[X_INDEX] = lastRow[X2_INDEX] - newRow[Q_INDEX] * lastRow[X1_INDEX];
            newRow[Y_INDEX] = lastRow[Y2_INDEX] - newRow[Q_INDEX] * lastRow[Y1_INDEX];
            newRow[A_INDEX] = lastRow[B_INDEX];
            newRow[B_INDEX] = newRow[R_INDEX];
            newRow[X2_INDEX] = lastRow[X1_INDEX];
            newRow[X1_INDEX] = newRow[X_INDEX];
            newRow[Y2_INDEX] = lastRow[Y1_INDEX];
            newRow[Y1_INDEX] = newRow[Y_INDEX];
            rows.add(newRow);
        }
        int[] lastRow = getLastRow();
        gcd = lastRow[A_INDEX];
        x = lastRow[X2_INDEX];
        y = lastRow[Y2_INDEX];
        if(gcd < 0) {
            gcd *= -1;
            x *= -1;
            y *= -1;
        }
    }

    private boolean finished() {
        int[] lastRow = getLastRow();
        return (lastRow[Q_INDEX] != 0 && lastRow[R_INDEX] == 0) || lastRow[B_INDEX] == 0;
    }

    private int[] getLastRow() {
        return rows.get(rows.size() - 1);
    }

    void printResult() {
        System.out.println("gcd(" + a + ", " + b + ") = " + gcd);
    }

    void printCheck() {
        System.out.println("(" + a + ")" + "(" + x * (a < 0 ? -1 : 1) + ") + (" + b + ")(" + y * (b < 0 ? -1 : 1) + ") = " + gcd);
    }

    int getGCD() {
        return gcd;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getX1() {
        int[] lastRow = getLastRow();
        return lastRow[X1_INDEX];
    }

    int getX2() {
        int[] lastRow = getLastRow();
        return lastRow[X2_INDEX];
    }

    @Override
    protected String[] getLabels() {
        return labels;
    }

    @Override
    protected List<int[]> getRows() {
        return rows;
    }
}
