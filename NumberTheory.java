// Josh Humpherys
// February 22, 2017
// COS 313: Introduction to Cryptography
// Lab 5: Number Theory Implementations

// Extended GCD, Gauss' Algorithm for CRT, and modular exponentiation

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumberTheory {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("This program has functions for:");
        System.out.println("\t(1) Extended GCD");
        System.out.println("\t(2) Gauss' Algorithm for CRT");
        System.out.println("\t(3) Modular exponentiation");
        int algorithm = prompt("Please enter your choice: ", 1, 3, s);
        if(algorithm == 1) {
            int a = prompt("a: ", s);
            int b = prompt("b: ", s);
            ExtendedGCD extendedGCD = extendedGCD(a, b);
            extendedGCD.printTable();
            extendedGCD.printResult();
            extendedGCD.printCheck();
        } else if(algorithm == 2) {
            int n = prompt("Number of congruences: ", 1, s);
            int[] aValues = new int[n];
            int[] nValues = new int[n];
            for(int i = 0; i < n; i++) {
                aValues[i] = prompt("a" + (i + 1) + ": ", s);
                nValues[i] = prompt("n" + (i + 1) + ": ", 0, s);
            }
            gauss(aValues, nValues).printTable();
        } else if(algorithm == 3) {
            int a = prompt("a: ", s);
            int x = prompt("x: ", 0, s);
            int n = prompt("n: ", 0, s);
            ModularExponentiation modularExponentiation = modularExponentiation(a, x, n);
            modularExponentiation.printTable();
            modularExponentiation.printResult();
        }
    }

    private static int prompt(String prompt, Scanner s) {
        while(true) {
            System.out.print(prompt);
            String input = s.nextLine();
            try {
                return Integer.parseInt(input);
            } catch(NumberFormatException e) {
                System.out.println("Invalid input. Please enter an int.");
            }
        }
    }

    private static int prompt(String prompt, int min, Scanner s) {
        while(true) {
            int response = prompt(prompt, s);
            if(response >= min) {
                return response;
            }
            System.out.println("Out of range. Int must be greater than or equal to " + min + ".");
        }
    }

    private static int prompt(String prompt, int min, int max, Scanner s) {
        while(true) {
            int response = prompt(prompt, s);
            if(response >= min && response <= max) {
                return response;
            }
            System.out.println("Out of range. Int must be between " + min + " and " + max + ", inclusive.");
        }
    }

    private static int mod(int a, int b) {
        return (a % b + b) % b;
    }

    private static ExtendedGCD extendedGCD(int a, int b) {
        ExtendedGCD extendedGCD = new ExtendedGCD(a, b);
        extendedGCD.run();
        return extendedGCD;
    }

    private static Gauss gauss(int[] aValues, int[] nValues) {
        Gauss gauss = new Gauss(aValues, nValues);
        gauss.run();
        return gauss;
    }

    private static ModularExponentiation modularExponentiation(int a, int x, int n) {
        ModularExponentiation modularExponentiation = new ModularExponentiation(a, x, n);
        modularExponentiation.run();
        return modularExponentiation;
    }

    private static class ExtendedGCD extends TabularAlgorithm {
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
            rows.add(new int[]{0, 0, 0, 0, a, b, 1, 0, 0, 1});
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
            System.out.println("(" + a + ")" + "(" + x + ") + (" + b + ")(" + y + ") = " + gcd);
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

    private static class Gauss extends TabularAlgorithm {
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
                    if(extendedGCD(nValues[i], nValues[j]).getGCD() != 1) {
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
                    ExtendedGCD extendedGCD = extendedGCD(n, nValues[i]);
                    int x = mod(extendedGCD.getX(), nValues[i]);
                    int y = mod(extendedGCD.getY(), nValues[i]);
                    int m = mod(extendedGCD.getX2() + extendedGCD.getX1(), nValues[i]);
                    sum += aValues[i] * n * m;
                    rows.add(new int[]{aValues[i], n, m});
                }
            }
            int product = 1;
            for(int i = 0; i < nValues.length; i++) {
                product *= nValues[i];
            }
            result = mod(sum, product);
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

    private static class ModularExponentiation extends TabularAlgorithm {
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

    private static abstract class TabularAlgorithm {
        protected String[] labels;
        protected List<int[]> rows;

        protected abstract String[] getLabels();
        protected abstract List<int[]> getRows();

        protected final void printTable() {
            String[] labels = getLabels();
            List<int[]> rows = getRows();
            int[] maxWidthOfColumns = new int[labels.length];
            for(int i = 0; i < labels.length; i++) {
                int maxWidth = labels[i].length();
                for(int j = 0; j < rows.size(); j++) {
                    int currentWidth = String.valueOf(rows.get(j)[i]).length();
                    if(currentWidth > maxWidth) {
                        maxWidth = currentWidth;
                    }
                }
                maxWidthOfColumns[i] = maxWidth;
            }
            String[] spaces = new String[12];
            spaces[0] = "";
            for(int i = 1; i < 12; i++) {
                spaces[i] = spaces[i - 1] + " ";
            }
            StringBuilder header = new StringBuilder();
            for(int i = 0; i < labels.length; i++) {
                header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[i] - labels[i].length(), 0)) + labels[i] + " " + (i == labels.length - 1 ? "" : "|"));
            }
            System.out.println(header);
            StringBuilder dashes = new StringBuilder();
            for(int i = 0; i < header.length(); i++) {
                dashes.append("-");
            }
            System.out.println(dashes);
            StringBuilder headerFormatBuilder = new StringBuilder();
            for(int i = 0; i < labels.length; i++) {
                headerFormatBuilder.append(" %" + maxWidthOfColumns[i] + "d " + (i != labels.length - 1 ? "|" : ""));
            }
            headerFormatBuilder.append("%n");
            String headerFormat = headerFormatBuilder.toString();
            for(int i = 0; i < rows.size(); i++) {
                int[] row = rows.get(i);
                for(int j = 0; j < row.length; j++) {
                    System.out.printf(" %" + maxWidthOfColumns[j] + "d " + (j != labels.length - 1 ? "|" : "%n"), row[j]);
                }
            }
        }

        private static String getNumSpaces(String[] spaces, int n) {
            StringBuilder space = new StringBuilder(spaces[Math.min(n, spaces.length - 1)]);
            for(int i = spaces.length; i <= n; i++) {
                space.append(" ");
            }
            return space.toString();
        }
    }
}
