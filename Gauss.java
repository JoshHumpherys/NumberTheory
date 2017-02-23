import java.util.ArrayList;
import java.util.List;

public class Gauss extends TabularAlgorithm {
    int[] aValues;
    int[] nValues;
    List<int[]> rows;
    int result;
    String[] labels = new String[]{"a_i", "N_i", "M_i"};
    Gauss(int[] aValues, int[] nValues) {
        rows = new ArrayList<>();
        this.aValues = new int[aValues.length];
        this.nValues = new int[nValues.length];
        System.arraycopy(aValues, 0, this.aValues, 0, aValues.length);
        System.arraycopy(nValues, 0, this.nValues, 0, nValues.length);
    }

    void run() {
        for(int i = 0; i < nValues.length; i++) {
            for(int j = i + 1; j < nValues.length; j++) {
                ExtendedGCD extendedGCD = new ExtendedGCD(nValues[i], nValues[j]);
                extendedGCD.run();
                if(extendedGCD.getGCD() != 1) {
                    System.out.println("Not all moduli are coprime.");
                    result = -1;
                }
            }
        }
        int sum = 0;
        for(int i = 0; i < aValues.length; i++) {
            if(aValues[i] != 0) {
                int n = 1;
                for(int j = 0; j < nValues.length; j++) {
                    if(i != j) {
                        n *= nValues[j];
                    }
                }
                ExtendedGCD extendedGCD = new ExtendedGCD(n, nValues[i]);
                extendedGCD.run();
                int x = mod(extendedGCD.getX(), nValues[i]);
                int y = mod(extendedGCD.getY(), nValues[i]);
                int m = mod(extendedGCD.getX2() + extendedGCD.getX1(), nValues[i]);
                sum += aValues[i] * n * m;
                rows.add(new int[]{aValues[i], n, m});
            }
        }
        int product = calculateProduct(nValues);
        result = mod(sum, product);
    }

    private int calculateProduct(int[] array) {
        int product = 1;
        for(int i = 0; i < array.length; i++) {
            product *= array[i];
        }
        return product;
    }

    void printResult() {
        System.out.println("x\u2261 " + result + "(mod " + calculateProduct(nValues) + ")");
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
