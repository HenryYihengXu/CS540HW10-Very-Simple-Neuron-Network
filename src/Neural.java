import static java.lang.Math.exp;
import static java.lang.Math.max;

public class Neural {

    double w1, w2, w3, w4, w5, w6, w7, w8, w9;
    double uA, uB, uC, vA, vB, vC;

    public Neural() {
    }

    public Neural(double w1, double w2, double w3, double w4, double w5,
                  double w6, double w7, double w8, double w9) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
        this.w5 = w5;
        this.w6 = w6;
        this.w7 = w7;
        this.w8 = w8;
        this.w9 = w9;
    }

    public void compute(double x1, double x2) {
        uA = w1 + x1 * w2 + x2 * w3;
        vA = max(uA, 0);
        uB = w4 + x1 * w5 + x2 * w6;
        vB = max(uB, 0);
        uC = w7 + vA * w8 + vB * w9;
        vC = sigmoid(uC);
    }

    public double sigmoid(double z) {
        return  1 / (1 + exp(-z));
    }

    public static void main(String[] args) {

        int flag = Integer.valueOf(args[0]);

        Neural neural = new Neural(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]),
                Double.parseDouble(args[7]), Double.parseDouble(args[8]), Double.parseDouble(args[9]));

        if (flag == 100) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            neural.compute(x1, x2);
            /*double uA, vA, uB, vB, uC, vC;
            uA = neural.w1 + x1 * neural.w2 + x2 * neural.w3;
            vA = max(uA, 0);
            uB = neural.w4 + x1 * neural.w5 + x2 * neural.w6;
            vB = max(uB, 0);
            uC = neural.w7 + vA * neural.w8 + vB * neural.w9;
            vC = 1 / (1 + exp(-uC));
            System.out.println(String.format("%.5f", uA) + " " + String.format("%.5f", vA) + " "
                    + String.format("%.5f", uB) + " " + String.format("%.5f", vB) + " "
                    + String.format("%.5f", uC) + " " + String.format("%.5f", vC));*/
            System.out.println(String.format("%.5f", neural.uA) + " " + String.format("%.5f", neural.vA) + " "
                    + String.format("%.5f", neural.uB) + " " + String.format("%.5f", neural.vB) + " "
                    + String.format("%.5f", neural.uC) + " " + String.format("%.5f", neural.vC));

        } else if (flag == 200) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            double y = Double.parseDouble(args[12]);
            neural.compute(x1, x2);
            double E = (neural.vC - y) * (neural.vC - y) / (double)2;
            double partialDeriveVC = neural.vC - y;
            double fDeriveUC = neural.vC * (1 - neural.vC);
            double partialDeriveUC = partialDeriveVC * fDeriveUC;
            System.out.println(String.format("%.5f", E) + " " + String.format("%.5f", partialDeriveVC) + " "
                    + String.format("%.5f", partialDeriveUC));
        } else if (flag == 300) {

        } else if (flag == 400) {

        } else if (flag == 500) {

        } else if (flag == 600) {

        } else if (flag == 700) {

        } else if (flag == 800) {

        }
    }
}
