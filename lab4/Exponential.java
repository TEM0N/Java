package lab4;

public class Exponential extends Series {
    public Exponential(int n, double firstElement, double d) {
        super(n, firstElement, d);
    }

    @Override
    public double getElement(int j) {
        return Math.pow(delta, j) * firstElement;
    }

    @Override
    public double getSum() {
        return firstElement * (Math.pow(delta, n) - 1) / (delta - 1);
    }
}
