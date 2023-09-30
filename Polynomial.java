import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(double[] c, int[] e) {
        coefficients = new double[c.length];
        exponents = new int[e.length];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = c[i];
            exponents[i] = e[i];
        }
    }

    public Polynomial(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        scanner.close();

        String[] terms = line.split("(?=[+-])");
        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            double coefficient;
            int exponent;

            if (term.contains("x")) {
                String[] parts = term.split("x");

                if (parts[0].isEmpty()) {
                    coefficient = 1.0;
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(parts[0]);
                }

                if (parts.length > 1) {
                    if (parts[1].isEmpty()) {
                        exponent = 1;
                    } else {
                        exponent = Integer.parseInt(parts[1]);
                    }
                } else {
                    exponent = 1;
                }
            } else {
                coefficient = Double.parseDouble(term);
                exponent = 0;
            }

            coefficients[i] = coefficient;
            exponents[i] = exponent;
        }
    }

    public Polynomial add(Polynomial c) {
        Polynomial res = new Polynomial();
        int len = Math.max(this.coefficients.length, c.coefficients.length);
        res.coefficients = new double[len];
        res.exponents = new int[len];
        for (int i = 0; i < this.coefficients.length; i++) {
            res.coefficients[i] += this.coefficients[i];
            res.exponents[i] = this.exponents[i];
        }
        for (int i = 0; i < c.coefficients.length; i++) {
            res.coefficients[i] += c.coefficients[i];
            res.exponents[i] = this.exponents[i];
        }
        return res;
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return sum;
    }

    public Polynomial multiply(Polynomial c) {
        int thisDegree = this.coefficients.length;
        int callDegree = c.coefficients.length;
        int resultDegree = thisDegree + callDegree - 1;

        double[] resultCoefficients = new double[resultDegree];
        int[] resultExponents = new int[resultDegree];

        for (int i = 0; i < thisDegree; i++) {
            for (int j = 0; j < callDegree; j++) {
                int newDegree = this.exponents[i] + c.exponents[j];
                double newCoefficient = this.coefficients[i] * c.coefficients[j];
                addTerm(resultCoefficients, resultExponents, newCoefficient, newDegree);
            }
        }

        Polynomial result = new Polynomial(resultCoefficients, resultExponents);
        return result;
    }

    public void addTerm(double[] coefficients, int[] exponents, double newCoefficient, int newDegree) {
        for (int i = 0; i < coefficients.length; i++) {
            if (exponents[i] == newDegree) {
                coefficients[i] += newCoefficient;
                return;
            }
        }
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] == 0.0) {
                coefficients[i] = newCoefficient;
                exponents[i] = newDegree;
                return;
            }
        }
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            for (int i = 0; i < coefficients.length; i++) {
                double coefficient = coefficients[i];
                int exponent = exponents[i];
                if (i > 0 && coefficient >= 0) {
                    writer.print("+");
                }
                if (coefficient != 0) {
                    writer.print(coefficient);
                    if (exponent > 0) {
                        writer.print("x");
                        if (exponent > 1) {
                            writer.print(exponent);
                        }
                    }
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < this.exponents.length; i++) {
            if (i + 1 >= this.exponents.length) {
                System.out.println(this.coefficients[i] + "x^" + this.exponents[i]);
            } else {
                System.out.println(this.coefficients[i] + "x^" + this.exponents[i] + " + ");
            }
        }
        System.out.println();
    }
}