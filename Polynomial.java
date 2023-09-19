public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[] { 0 };
    }

    public Polynomial(double[] c) {
        coefficients = new double[c.length];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = c[i];
        }

    }

    public Polynomial add(Polynomial c) {
        Polynomial res = new Polynomial();
        int len = Math.max(this.coefficients.length, c.coefficients.length);
        res.coefficients = new double[len];
        for (int i = 0; i < this.coefficients.length; i++) {
            res.coefficients[i] += this.coefficients[i];
        }
        for (int i = 0; i < c.coefficients.length; i++) {
            res.coefficients[i] += c.coefficients[i];
        }
        return res;
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

}