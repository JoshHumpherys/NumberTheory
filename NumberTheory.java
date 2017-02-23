// Josh Humpherys
// February 22, 2017
// COS 313: Introduction to Cryptography
// Lab 5: Number Theory Implementations

// Extended GCD, Gauss' Algorithm for CRT, and modular exponentiation

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
                nValues[i] = prompt("n" + (i + 1) + ": ", 1, s);
            }
            Gauss gauss = gauss(aValues, nValues);
            gauss.printTable();
            gauss.printResult();
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
}
