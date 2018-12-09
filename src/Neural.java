import static java.lang.Math.exp;
import static java.lang.Math.max;

public class Neural {

    double w1, w2, w3, w4, w5, w6, w7, w8, w9;
    double uA, uB, uC, vA, vB, vC;
    double E;
    double fDeriveUC;
    double partialDeriveUA, partialDeriveVA;
    double partialDeriveUB, partialDeriveVB;
    double partialDeriveUC, partialDeriveVC;

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

    public void computeUV(double x1, double x2) {
        uA = w1 + x1 * w2 + x2 * w3;
        vA = max(uA, 0);
        uB = w4 + x1 * w5 + x2 * w6;
        vB = max(uB, 0);
        uC = w7 + vA * w8 + vB * w9;
        vC = sigmoid(uC);
    }

    public void computePartialDeriveUCVC(double x1, double x2, double y) {
        computeUV(x1, x2);
        E = (vC - y) * (vC - y) / (double)2;
        partialDeriveVC = vC - y;
        fDeriveUC = vC * (1 - vC);
        partialDeriveUC = partialDeriveVC * fDeriveUC;
    }

    public void computePartialDeriveUABVAB(double x1, double x2, double y) {
        computePartialDeriveUCVC(x1, x2, y);
        partialDeriveVA = w8 * partialDeriveUC;
        partialDeriveUA = partialDeriveVA * deriveReLU(uA);

        partialDeriveVB = w9 * partialDeriveUC;
        partialDeriveUB = partialDeriveVB * deriveReLU(uB);

    }

    public double sigmoid(double z) {
        return  1 / (1 + exp(-z));
    }

    public double deriveReLU(double uj) {
        if (uj >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {

        int flag = Integer.valueOf(args[0]);

        Neural neural = new Neural(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]),
                Double.parseDouble(args[7]), Double.parseDouble(args[8]), Double.parseDouble(args[9]));

        if (flag == 100) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            neural.computeUV(x1, x2);
            System.out.println(String.format("%.5f", neural.uA) + " " + String.format("%.5f", neural.vA) + " "
                    + String.format("%.5f", neural.uB) + " " + String.format("%.5f", neural.vB) + " "
                    + String.format("%.5f", neural.uC) + " " + String.format("%.5f", neural.vC));

        } else if (flag == 200) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            double y = Double.parseDouble(args[12]);
            neural.computePartialDeriveUCVC(x1, x2, y);
            System.out.println(String.format("%.5f", neural.E) + " "
                    + String.format("%.5f", neural.partialDeriveVC) + " "
                    + String.format("%.5f", neural.partialDeriveUC));
        } else if (flag == 300) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            double y = Double.parseDouble(args[12]);
            neural.computePartialDeriveUABVAB(x1, x2, y);
            System.out.println(String.format("%.5f", neural.partialDeriveVA) + " "
                    + String.format("%.5f", neural.partialDeriveUA) + " "
                    + String.format("%.5f", neural.partialDeriveVB) + " "
                    + String.format("%.5f", neural.partialDeriveUB));
        } else if (flag == 400) {

        } else if (flag == 500) {

        } else if (flag == 600) {

        } else if (flag == 700) {

        } else if (flag == 800) {

        }
    }
}
