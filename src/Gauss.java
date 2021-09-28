import java.util.Scanner;

public class Gauss {

    public static void scaleRow(Matrix m, int i, double k) {
        int j, n = m.cols;
        for (j=0; j<n; j++) {
            m.M[i][j] = k * m.M[i][j];
        }
    }

    public static void swapRow(Matrix m, int i1, int i2) {
        double[] temp = m.M[i1];
        m.M[i1] = m.M[i2];
        m.M[i2] = temp;
    }

    public static void replaceRow(Matrix m, int i1, int i2, double k) {
        int j, n = m.cols;
        for (j=0; j<n; j++) {
            m.M[i2][j] = m.M[i2][j] + (k * m.M[i1][j]);
        }
    }

    public static void oBEMatriks(Matrix A){
        int i, j, k, m = A.rows;

        for (i=0; i<m-1; i++){
            for (j=i+1; j<m; j++){
                if (A.M[i][i] < A.M[j][i]){
                    swapRow(A, i, j);
                }
            }
            for (k=i+1; k<m; k++){
                double ratio = (A.M[k][i])/(A.M[i][i]);
                replaceRow(A, i, k, (-ratio));
            }
        }
    }

    public static Matrix eselonBaris(Matrix A) {
        int i, j, m = A.rows, n = A.cols;

        oBEMatriks(A);

        for (i=0; i<m; i++) {
            for (j=0; j<n; j++) {
                if ((A.M[i][j] != 0) && (A.M[i][j] != 1)) {
                    scaleRow(A, i, (1/(A.M[i][j])));
                    break;
                }
                else {
                    if (A.M[i][j] == 0)         continue;
                    else if (A.M[i][j] == 1)    break;
                }
            }
        }

        return A;
    }

    public static double[] gaussSPL(Matrix A) {
        int i, j, m = A.rows, n = A.cols;
        double[] x = new double[m];     // solusi SPL
        for (i=m-1; i>=0; i--) {
            double temp = 0;
            for (j=i+1; j<m; j++) {
                temp += (A.M[i][j] * x[j]);
            }
            x[i] = ((A.M[i][n-1])-temp)/(A.M[i][i]);
        }
        return x;
    }

    public static Matrix augmented(Matrix A, double[] B) {
        int i, j, m = A.rows, n = A.cols;
        Matrix newA = new Matrix(m, n+1);
        for (i=0; i<m; i++) {
            for (j=0; j<n+1; j++) {
                if (j == n) {
                    newA.M[i][j] = B[i];
                }
                else {
                    newA.M[i][j] = A.M[i][j];
                }
            }
        }
        return newA;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Masukkan banyak baris (m) dan kolom (n)");
        int m = input.nextInt();
        int n = input.nextInt();
        Matrix A = new Matrix(m, n);
        double[] B = new double[m];
        int i, j;

        System.out.println("Masukkan koefisien a[i][j]");
        for (i=0; i<m; i++) {
            for (j=0; j<n; j++) {
                A.M[i][j] = input.nextDouble();
            }
        }

        System.out.println("Masukkan koefisien b[i]");
        for (i=0; i<m; i++) {
            B[i] = input.nextDouble();
        }

        A = augmented(A, B);
        A = eselonBaris(A);
        double[] solusi;

        if ((A.M[m-1][n] != 0) && (A.M[m-1][n-1] == 0)) {
            System.out.println("\nSolusi SPL tidak ada.");
        }
        else if ((A.M[m-1][n] != 0) && (A.M[m-1][n-1] != 0)) {
            solusi = gaussSPL(A);
            for (i=0; i<solusi.length; i++) {
                System.out.printf("x[%d] = %.3f", (i+1), (solusi[i]));
                System.out.println();
            }
        }
        /* else solusi parametrik belum */
    }

}