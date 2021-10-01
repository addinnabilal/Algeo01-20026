// import java.util.Scanner;

public class Gauss {
    Operasi operasi = new Operasi();

    /* operasi baris elementer */
    void oBEMatriks(Matrix A){
        int i, j, k, m = A.rows;

        for (i=0; i<m-1; i++){
            for (j=i+1; j<m; j++){
                if (A.M[i][i] < A.M[j][i]){
                    operasi.swapRow(A, i, j);
                }
            }
            for (k=i+1; k<m; k++){
                double ratio = (A.M[k][i])/(A.M[i][i]);
                operasi.replaceRow(A, i, k, (-ratio));
            }
        }
    }

    /* membuat matriks eselon baris */
    Matrix eselonBaris(Matrix A) {
        int i, j, m = A.rows, n = A.cols;
        oBEMatriks(A);

        for (i=0; i<m; i++) {
            for (j=0; j<n; j++) {
                if ((A.M[i][j] != 0) && (A.M[i][j] != 1)) {
                    operasi.scaleRow(A, i, (1/(A.M[i][j])));
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

    /* membuat matriks eselon baris tereduksi */
    Matrix eselonBarisRed(Matrix A) {
        int i, j, k, m = A.rows, n = A.cols;
        eselonBaris(A);
        for (i=1; i<m; i++) {
            for (j=0; j<n; j++) {
                if (A.M[i][j] == 1) {
                    for (k=i-1; k>=0; k--) {
                        if (A.M[k][j] != 0) {
                            operasi.replaceRow(A, i, k, -(A.M[k][j]));
                        }
                    }
                    break;
                }
                else if (A.M[i][j] == 0)    continue;
            }
        }
        return A;

    }

    /* print solusi SPL */
    void printSPL(Matrix A){
        int i, m = A.rows, n = A.cols;
        double[] solusi;
        if ((A.M[m-1][n-1] != 0.0) && (A.M[m-1][n-2] == 0.0)) {
            System.out.println("\nSolusi SPL tidak ada.");
        }
        else if ((A.M[m-1][n-1] != 0.0) && (A.M[m-1][n-2] != 0.0)) {
            solusi = gaussSPL(A);
            for (i=0; i<solusi.length; i++) {
                System.out.printf("x[%d] = %f", (i+1), (solusi[i]));
                System.out.println();
                A.spl = "x["+(i+1)+"] = "+(solusi[i]+"\n"); // <- Buat output ke file
            }
        }
    }
    
    /* mencari solusi pada SPL yang memiliki solusi unik */
    double[] gaussSPL(Matrix A) {
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
}