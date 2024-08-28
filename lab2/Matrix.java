package lab2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Matrix {
    private final double[][] matrix;
    private int rows;
    private int cols;

    public Matrix(Path path) throws IOException, NonSquareMatrixException {
        readMatrixSizeFromFile(path);
        matrix = new double[rows][cols];
        readMatrixFromFile(path);
    }

    Matrix(Matrix a) {
        this.matrix = new double[a.rows][a.cols];
        this.rows = a.rows;
        this.cols = a.cols;
        for (int i = 0; i < a.rows; i++) {
            if (a.cols >= 0) {
                System.arraycopy(a.matrix[i], 0, this.matrix[i], 0, a.cols);
            }
        }
    }

    private void readMatrixSizeFromFile(Path path) throws NotEnoughElementsException, TooMuchElementsException, NumberFormatException, IOException {
        Scanner scanner = new Scanner(path);
        String[] size = scanner.nextLine().split(" ");
        rows = Integer.parseInt(size[0]);
        cols = Integer.parseInt(size[1]);
    }

    private void readMatrixFromFile(Path path) throws IOException, IllegalArgumentException {
        Scanner scanner = new Scanner(path);
        int n = 0;
        scanner.nextLine();
        while (scanner.hasNextLine() && n < rows) {
            String[] arrayLine = scanner.nextLine().split(" ");
            if (arrayLine.length == 0) {
                break;
            }

//            //double[] row = Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
            if (arrayLine.length < cols) {
                throw new NotEnoughElementsException("Need more elements");
            } else if (arrayLine.length > cols) {
                throw new TooMuchElementsException("Too many elements");
            }

            for (int i = 0; i < cols; i++) {
                matrix[n][i] = Double.parseDouble(arrayLine[i]);
            }

            n++;
            if (n < rows && !scanner.hasNextLine()) {
                throw new NotEnoughElementsException("Need more elements");
            }
            if (n == rows && scanner.hasNextLine()) {
                throw new TooMuchElementsException("Too many elements");
            }
        }
    }

    private static Pair<Matrix, double[]> getTriangle(Matrix a, double[] b) throws IndexOutOfBoundsException {
        double ratio;
        Matrix triangularMatrix = new Matrix(a);
        double[] newB = new double[b.length];
        System.arraycopy(b, 0, newB, 0, b.length);
        int n = a.rows;

        for (int i = 0; i < n - 1; i++) {
            if (a.matrix[i][i] == 0) {
                int index = findNonZeroRow(triangularMatrix, i);
                if (index == -1) {
                    throw new IndexOutOfBoundsException("Unsolvable system");
                }
                changeRows(triangularMatrix, newB, i, index);
            }

            for (int j = i + 1; j <= n - 1; j++) {
                ratio = triangularMatrix.matrix[j][i] / triangularMatrix.matrix[i][i];
                for (int k = i; k < n; k++) {
                    triangularMatrix.matrix[j][k] -= ratio * triangularMatrix.matrix[i][k];
                }
                newB[j] -= newB[i] * ratio;
            }
        }
        return new Pair<>(triangularMatrix, newB);
    }

    public static double[] solveLinearSystem(Matrix a, double[] b) throws IllegalArgumentException {
        if (a.cols != a.rows) {
            throw new IllegalArgumentException("Matrix should be squared");
        }
        Pair<Matrix, double[]> pair = null;
        double[] solution = new double[a.rows];
        try {
            pair = getTriangle(a, b);
           
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(2);
        }
        int n = a.rows;
        if (pair.getFirst().matrix[n-1][n-1] == 0) {
            throw new IndexOutOfBoundsException("unsolvable system");
        }
        solution[n - 1] = pair.getSecond()[n - 1] / pair.getFirst().matrix[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            solution[i] = pair.getSecond()[i];
            for (int j = n - 1; j > i; j--) {
                solution[i] -= solution[j] * pair.getFirst().matrix[i][j];
            }

            solution[i] /= pair.getFirst().matrix[i][i];
        }
        return solution;
    }

    public void printExtendedMatrix(double[] b) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("| " + b[i]);
            System.out.println();
        }
    }

    private static int findNonZeroRow(Matrix a, int col) {
        for (int i = col; i < a.rows; i++) {
            if (a.matrix[i][col] != 0) {
                return i;
            }
        }
        return -1;
    }

    private static void changeRows(Matrix a, double[] b, int r1, int r2) throws IndexOutOfBoundsException {
        if (r1 >= a.rows || r1 < 0 || r2 < 0 || r2 >= a.rows) {
            throw new IndexOutOfBoundsException("Illegal index of rows");
        }
        double[] tempRow = new double[a.cols];
        System.arraycopy(a.matrix[r1], 0, tempRow, 0, a.cols);
        System.arraycopy(a.matrix[r2], 0, a.matrix[r1], 0, a.cols);
        a.matrix[r2] = tempRow;

        double temp = b[r1];
        b[r1] = b[r2];
        b[r2] = temp;
    }

    public static void run(Path path, Path pathB) {

        Matrix m = null;
        try {
            m = new Matrix(path);
        } catch (TooMuchElementsException | NotEnoughElementsException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(2);
        } catch (NonSquareMatrixException | NumberFormatException | IOException e) {
            System.err.println("wrong input:" + e.getLocalizedMessage());
            System.exit(2);
        }
        try {
            double[] B = readB(pathB, m.rows);
            if (B.length != m.matrix.length) {
                System.err.println("unequal matrix and vector size");
                System.exit(2);
            }
            m.printExtendedMatrix(B);
            System.out.println();

            double[] result = solveLinearSystem(m, B);
            System.out.println(Arrays.toString(result));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(2);
        } catch (InputMismatchException | IOException e) {
            System.err.println("wrong B: " + e.getLocalizedMessage());
            System.exit(2);
        }
    }

    public static double[] readB(Path path, int size) throws IOException {
        Scanner scanner = new Scanner(path);
        double[] B = new double[size];
        for (int i = 0; i < size; i++) {
            if (!scanner.hasNextLine()) {
                throw new NotEnoughElementsException("Need more elements for B");
            }
            B[i] = scanner.nextDouble();
        }
        if (scanner.hasNextLine()) {
            throw new TooMuchElementsException("Too many arguments for B");
        }
        return B;
    }
}
