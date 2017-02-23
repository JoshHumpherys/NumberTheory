import java.util.ArrayList;
import java.util.List;

public class ModularExponentiation extends TabularAlgorithm {
    int a;
    int x;
    int n;
    boolean[] binaryArray;
    List<int[]> rows;
    int result;
    String[] labels = new String[]{"i", "a^i(mod n)", "x"};
    ModularExponentiation(int a, int x, int n) {
        rows = new ArrayList<>();
        this.a = a;
        this.x = x;
        this.n = n;
    }

    void run() {
        boolean[] binaryArray = convertDecimalToBinary(x);
        this.binaryArray = binaryArray;
        int powerOfTwo = 1;
        int aToPowerOfTwoModN = mod(a, n);
        int productModN = binaryArray[0] ? aToPowerOfTwoModN : 1;
        rows.add(new int[]{powerOfTwo, aToPowerOfTwoModN, binaryArray[0] ? 1 : 0});
        for(int i = 1; i < binaryArray.length; i++) {
            powerOfTwo = powerOfTwo << 1;
            aToPowerOfTwoModN = mod((int)Math.pow(aToPowerOfTwoModN, 2), n);
            if(binaryArray[i]) {
                productModN = mod(productModN * aToPowerOfTwoModN, n);
            }
            rows.add(new int[]{powerOfTwo, aToPowerOfTwoModN, binaryArray[i] ? 1 : 0});
        }
        result = productModN;
    }

    private static boolean[] convertDecimalToBinary(int x) {
        String binaryString = Integer.toBinaryString(x);
        boolean[] binaryArray = new boolean[binaryString.length()];
        for(int i = 0; i < binaryString.length(); i++) {
            binaryArray[binaryArray.length - 1 - i] = binaryString.charAt(i) == '1';
        }
        return binaryArray;
    }

    void printResult() {
        System.out.println(addParensWhenNegative(a) + "^" + addParensWhenNegative(x) + "(mod " + n + ")\u2261 " + result);
    }

    private String addParensWhenNegative(int i) {
        return i < 0 ? "(" + i + ")" : String.valueOf(i);
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
