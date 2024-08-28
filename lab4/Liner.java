package lab4;

public class Liner extends Series {
    public Liner(int n, double firstElement, double d) {
        super(n, firstElement, d);
    }

    @Override
    public double getElement(int j) {
        if (j == 0) {
            return firstElement;
        }
        return firstElement + j * delta;
    }

    @Override
    public double getSum() {
        return (2 * firstElement + (n - 1) * delta) * n / 2;
    }
}