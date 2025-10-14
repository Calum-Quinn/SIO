import java.util.ArrayList;
import java.util.Random;

// Exercise series 3 - Exercise 1
public class Main {
    Random random = new Random();

    public static void main(String[] args) {
        // Test that the return values of the three variance methods are the same
        Main main = new Main();
        double[] d = {3.0, 6.0, 9.0};



        ArrayList data;

        for (int i = 0; i < d.length; i++) {
            System.out.println("Values for d = " + d[i]);
            data = new ArrayList<Double>();
            main.realisations(Math.pow(10, d[i]), Math.pow(10,d[i]), 1, data);

            System.out.println("Welford Variance: " + main.welford(data));
            System.out.println("Variance Method 1: " + main.variance(data));
            System.out.println("Variance Method 2: " + main.variance2(data));
        }
    }

    private double welford(ArrayList data) {
        double mean = 0.0;
        double M2 = 0.0;
        int n = 0;

        for (int i = 0; i < data.size(); i++) {
            double x = (double) data.get(i);
            n++;
            double delta = x - mean;
            mean += delta / n;
            double delta2 = x - mean;
            M2 += delta * delta2;
        }

        return M2 / (n - 1);
    }

    private double mean(ArrayList data) {
        double sum = 0.0;

        for (int i = 0; i < data.size(); i++) {
            sum += (double) data.get(i);
        }

        return sum / data.size();
    }

    private double variance(ArrayList data) {
        double sum = 0.0;
        double avg = 0.0;

        for (int i = 0; i < data.size(); i++) {
            sum += (double) data.get(i);
        }

        avg = sum / data.size();

        double intermediate = 0.0;

        for (int i = 0; i < data.size(); i++) {
            double x = (double) data.get(i);
            intermediate += (x - avg) * (x - avg);
        }

        return intermediate / (data.size() - 1);
    }

    private double variance2(ArrayList data) {
        double sum = 0.0;
        double avg = 0.0;

        for (int i = 0; i < data.size(); i++) {
            sum += (double) data.get(i);
        }

        avg = sum / data.size();

        double intermediate = 0.0;

        for (int i = 0; i < data.size(); i++) {
            double x = (double) data.get(i);
            intermediate += x * x;
        }

        return (intermediate - data.size() * avg * avg) / (data.size() - 1);
    }

    private void realisations(double a, double b, double c, ArrayList<Double> results) {
        for (int i = 0; i < 1000000; i++) {
            double x = random.nextDouble() + a - c/2;
            double y = random.nextDouble() + b - c/2;

            results.add(Math.sqrt(x*x + y*y));
        }
    }
}