import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    double partialDeriveW1, partialDeriveW2, partialDeriveW3;
    double partialDeriveW4, partialDeriveW5, partialDeriveW6;
    double partialDeriveW7, partialDeriveW8, partialDeriveW9;

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

    public void computeE(double y){
        E = (vC - y) * (vC - y) / (double)2;
    }

    public void computePartialDeriveUCVC(double x1, double x2, double y) {
        partialDeriveVC = vC - y;
        fDeriveUC = vC * (1 - vC);
        partialDeriveUC = partialDeriveVC * fDeriveUC;
    }

    public void computePartialDeriveUABVAB(double x1, double x2, double y) {
        partialDeriveVA = w8 * partialDeriveUC;
        partialDeriveUA = partialDeriveVA * deriveReLU(uA);

        partialDeriveVB = w9 * partialDeriveUC;
        partialDeriveUB = partialDeriveVB * deriveReLU(uB);
    }

    public void computePartialDeriveW(double x1, double x2, double y) {
        partialDeriveW1 = 1 * partialDeriveUA;
        partialDeriveW2 = x1 * partialDeriveUA;
        partialDeriveW3 = x2 * partialDeriveUA;
        partialDeriveW4 = 1 * partialDeriveUB;
        partialDeriveW5 = x1 * partialDeriveUB;
        partialDeriveW6 = x2 * partialDeriveUB;
        partialDeriveW7 = 1 * partialDeriveUC;
        partialDeriveW8 = vA * partialDeriveUC;
        partialDeriveW9 = vB * partialDeriveUC;
    }

    public void updateW(double eta) {
        w1 -= eta * partialDeriveW1;
        w2 -= eta * partialDeriveW2;
        w3 -= eta * partialDeriveW3;
        w4 -= eta * partialDeriveW4;
        w5 -= eta * partialDeriveW5;
        w6 -= eta * partialDeriveW6;
        w7 -= eta * partialDeriveW7;
        w8 -= eta * partialDeriveW8;
        w9 -= eta * partialDeriveW9;
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

    public void printW() {
        System.out.println(String.format("%.5f", w1) + " "
                + String.format("%.5f", w2) + " "
                + String.format("%.5f", w3) + " "
                + String.format("%.5f", w4) + " "
                + String.format("%.5f", w5) + " "
                + String.format("%.5f", w6) + " "
                + String.format("%.5f", w7) + " "
                + String.format("%.5f", w8) + " "
                + String.format("%.5f", w9));
    }

    public static void main(String[] args) throws IOException {

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
            neural.computeUV(x1, x2);
            neural.computePartialDeriveUCVC(x1, x2, y);
            neural.computeE(y);
            System.out.println(String.format("%.5f", neural.E) + " "
                    + String.format("%.5f", neural.partialDeriveVC) + " "
                    + String.format("%.5f", neural.partialDeriveUC));
        } else if (flag == 300) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            double y = Double.parseDouble(args[12]);
            neural.computeUV(x1, x2);
            neural.computePartialDeriveUCVC(x1, x2, y);
            neural.computePartialDeriveUABVAB(x1, x2, y);
            System.out.println(String.format("%.5f", neural.partialDeriveVA) + " "
                    + String.format("%.5f", neural.partialDeriveUA) + " "
                    + String.format("%.5f", neural.partialDeriveVB) + " "
                    + String.format("%.5f", neural.partialDeriveUB));
        } else if (flag == 400) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            double y = Double.parseDouble(args[12]);
            neural.computeUV(x1, x2);
            neural.computePartialDeriveUCVC(x1, x2, y);
            neural.computePartialDeriveUABVAB(x1, x2, y);
            neural.computePartialDeriveW(x1, x2, y);
            System.out.println(String.format("%.5f", neural.partialDeriveW1) + " "
                    + String.format("%.5f", neural.partialDeriveW2) + " "
                    + String.format("%.5f", neural.partialDeriveW3) + " "
                    + String.format("%.5f", neural.partialDeriveW4) + " "
                    + String.format("%.5f", neural.partialDeriveW5) + " "
                    + String.format("%.5f", neural.partialDeriveW6) + " "
                    + String.format("%.5f", neural.partialDeriveW7) + " "
                    + String.format("%.5f", neural.partialDeriveW8) + " "
                    + String.format("%.5f", neural.partialDeriveW9));
        } else if (flag == 500) {
            double x1 = Double.parseDouble(args[10]);
            double x2 = Double.parseDouble(args[11]);
            double y = Double.parseDouble(args[12]);
            double eta = Double.parseDouble(args[13]);

            neural.printW();

            neural.computeUV(x1, x2);
            neural.computeE(y);
            System.out.println(String.format("%.5f", neural.E));

            neural.computePartialDeriveUCVC(x1, x2, y);
            neural.computePartialDeriveUABVAB(x1, x2, y);
            neural.computePartialDeriveW(x1, x2, y);
            neural.updateW(eta);
            neural.printW();

            neural.computeUV(x1, x2);
            neural.computeE(y);
            System.out.println(String.format("%.5f", neural.E));

        } else if (flag == 600) {
            neural.readTraining();
            neural.readEvaluation();
            double eta = Double.parseDouble(args[10]);
            /*for (Item i : neural.trainingSet) {
                System.out.println(i.x1 + " " + i.x2 + " " + i.y);
            }*/
            //Item[] data = neural.trainingSet;
            for (int i = 0; i < neural.trainingSet.length; i++) {
                double x1 = neural.trainingSet[i].x1;
                double x2 = neural.trainingSet[i].x2;
                double y = neural.trainingSet[i].y;
                System.out.println(String.format("%.5f", x1) + " "
                        + String.format("%.5f", x2) + " "
                        + String.format("%.5f", y));
                neural.computeUV(x1, x2);
                neural.computePartialDeriveUCVC(x1, x2, y);
                neural.computePartialDeriveUABVAB(x1, x2, y);
                neural.computePartialDeriveW(x1, x2, y);
                neural.updateW(eta);
                neural.printW();
                double E = 0;
                for (Item item : neural.evaluationSet) {
                    neural.computeUV(item.x1, item.x2);
                    neural.computeE(item.y);
                    E += neural.E;
                }
                System.out.println(String.format("%.5f", E));
            }
        } else if (flag == 700) {

        } else if (flag == 800) {

        }
    }

    Item[] trainingSet = null;
    Item[] evaluationSet = null;

    public void readTraining() throws IOException {
        String pathname = "hw2_midterm_A_train.txt";
        FileReader reader = new FileReader(pathname);
        BufferedReader br = new BufferedReader(reader);
        ArrayList<String[]> data = new ArrayList<String[]>();
        String line = br.readLine();
        while (line != null) {
            String[] fields = line.split(" ");
            data.add(fields);
            line = br.readLine();
        }
        trainingSet = new Item[data.size()];
        br.close();
        for (int i = 0; i < data.size(); i++) {
            trainingSet[i] = new Item();
            trainingSet[i].x1 = Double.parseDouble(data.get(i)[0]);
            trainingSet[i].x2 = Double.parseDouble(data.get(i)[1]);
            trainingSet[i].y = Integer.valueOf(data.get(i)[2]);
        }
    }

    public void readEvaluation() throws IOException {
        String pathname = "hw2_midterm_A_eval.txt";
        FileReader reader = new FileReader(pathname);
        BufferedReader br = new BufferedReader(reader);
        ArrayList<String[]> data = new ArrayList<String[]>();
        String line = br.readLine();
        while (line != null) {
            String[] fields = line.split(" ");
            data.add(fields);
            line = br.readLine();
        }
        evaluationSet = new Item[data.size()];
        br.close();
        for (int i = 0; i < data.size(); i++) {
            evaluationSet[i] = new Item();
            evaluationSet[i].x1 = Double.parseDouble(data.get(i)[0]);
            evaluationSet[i].x2 = Double.parseDouble(data.get(i)[1]);
            evaluationSet[i].y = Integer.valueOf(data.get(i)[2]);
        }
    }

    class Item {
        double x1, x2;
        int y;
    }
}