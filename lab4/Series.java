package lab4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Series {
    protected final int n;
    protected final double firstElement;
    protected final double delta;

    Series(int n, double firstElement, double d) {
        if (n < 0) {
            throw new IllegalArgumentException("wrong number of elements");
        }
        this.n = n;
        this.firstElement = firstElement;
        this.delta = d;
    }

    public abstract double getElement(int j);

    public double getSum() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += getElement(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < n; i++) {
            string.append(getElement(i)).append(" ");
            //if (i % 20 == 0 && i > 0) {
            //    string.append("\n");
            //}
        }
        return string.toString();
    }

    public void saveToFile(Path path) throws IOException {
        Files.write(path, toString().getBytes());
    }
}
