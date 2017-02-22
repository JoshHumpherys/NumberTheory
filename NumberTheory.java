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
            System.out.println(gauss(aValues, nValues));
        } else if(algorithm == 3) {
            int a = prompt("a: ", s);
            int x = prompt("x: ", 0, s);
            int n = prompt("n: ", 0, s);
            System.out.println(modularExponentiation(a, x, n));
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

    private static ExtendedGCD extendedGCD(int a, int b) {
        ExtendedGCD extendedGCD = new ExtendedGCD(a, b);
        extendedGCD.run();
        return extendedGCD;
    }

    private static int gauss(int[] aValues, int[] nValues) {
        return 0;
    }

    private static int modularExponentiation(int a, int x, int n) {
        return 0;
    }

    private static class ExtendedGCD {
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
                newRow[R_INDEX] = lastRow[A_INDEX] % lastRow[B_INDEX];
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
            return lastRow[Q_INDEX] != 0 && lastRow[R_INDEX] == 0;
        }

        private int[] getLastRow() {
            return rows.get(rows.size() - 1);
        }

        void printTable() {
            int[] maxWidthOfColumns = new int[10];
            for(int i = 0; i < 10; i++) {
                int maxWidth = 0;
                for(int j = 0; j < rows.size(); j++) {
                    int[] currentRow = rows.get(j);
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
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[0] - 1, 0)) + "q |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[1] - 1, 0)) + "r |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[2] - 1, 0)) + "x |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[3] - 1, 0)) + "y |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[4] - 1, 0)) + "a |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[5] - 1, 0)) + "b |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[6] - 2, 0)) + "x2 |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[7] - 2, 0)) + "x1 |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[8] - 2, 0)) + "y2 |");
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[9] - 2, 0)) + "y1");
            System.out.println(header);
            StringBuilder dashes = new StringBuilder();
            for(int i = 0; i < header.length(); i++) {
                dashes.append("-");
            }
            System.out.println(dashes);
            StringBuilder headerFormatBuilder = new StringBuilder();
            for(int i = 0; i < 10; i++) {
                headerFormatBuilder.append(" %" + maxWidthOfColumns[i] + "d " + (i != 9 ? "|" : ""));
            }
            headerFormatBuilder.append("%n");
            String headerFormat = headerFormatBuilder.toString();
            for(int i = 0; i < rows.size(); i++) {
                int[] row = rows.get(i);
                System.out.printf(headerFormat, row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]);
            }
        }

        private String getNumSpaces(String[] spaces, int n) {
            StringBuilder space = new StringBuilder(spaces[Math.min(n, spaces.length - 1)]);
            for(int i = spaces.length; i <= n; i++) {
                space.append(" ");
            }
            return space.toString();
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
    }
}
