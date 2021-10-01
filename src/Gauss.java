public class Gauss {
    Operasi operasi = new Operasi();

    /* membuat matriks eselon baris */
    Matrix eselonBaris(Matrix A) {
        int i, rowsPivot=0, colsPivot=0, m = A.rows, n = A.cols;

        while ((rowsPivot < m) && (colsPivot < n)) {
            // cari pivot bukan 0
            if (Double.compare(A.M[rowsPivot][colsPivot], 0.0) == 0) {
                for (i=rowsPivot+1; i<m; i++) {
                    if (Double.compare(A.M[i][colsPivot], 0.0) != 0) {
                        operasi.swapRow(A, rowsPivot, i);
                        break;
                    }
                }
                // kalau di kolom isinya 0 semua, lanjut ke kolom sebelahnya
                if (Double.compare(A.M[rowsPivot][colsPivot], 0.0) == 0) {
                    colsPivot++;
                }
            }
            else {
                for (i=rowsPivot+1; i<m; i++) {
                    if (Double.compare(A.M[i][colsPivot], 0.0) != 0) {
                        double ratio = (A.M[i][colsPivot]) / (A.M[rowsPivot][colsPivot]);
                        operasi.replaceRow(A, rowsPivot, i, -(ratio));
                    }
                    else    continue;
                }
                rowsPivot++;
                colsPivot++;
            }
        }
        
        return A;
    }

    /* membuat matriks eselon baris tereduksi */
    Matrix eselonBarisRed(Matrix A) {
        int i, j, k, m = A.rows, n = A.cols;
        
        A = eselonBaris(A);

        for (i=0; i<m; i++) {
            for (j=0; j<n; j++) {
                if ((Double.compare(A.M[i][j], 0.0) != 0) && (Double.compare(A.M[i][j], 1.0) != 0)) {
                    operasi.scaleRow(A, i, (1/(A.M[i][j])));
                    break;
                }
                else {
                    if (Double.compare(A.M[i][j], 0.0) == 0)         continue;
                    else if (Double.compare(A.M[i][j], 1.0) == 0)    break;
                }
            }
        }

        for (i=1; i<m; i++) {
            for (j=0; j<n; j++) {
                if (Double.compare(A.M[i][j], 1.0) == 0) {
                    for (k=i-1; k>=0; k--) {
                        if (Double.compare(A.M[k][j], 0.0) != 0) {
                            operasi.replaceRow(A, i, k, -(A.M[k][j]));
                        }
                    }
                    break;
                }
                else if (Double.compare(A.M[i][j], 0.0) == 0)    continue;
            }
        }
        
        return A;

    }

    /* print solusi SPL */
    void printSPL(Matrix A){
        int i, m = A.rows, n = A.cols;
        double[] solusi;
        if ((Double.compare((A.M[m-1][n-1]), 0.0) != 0) && (Double.compare((A.M[m-1][n-2]), 0.0) == 0)) {
            System.out.println("\nSolusi SPL tidak ada.");
        }
        else if (Double.compare((A.M[m-1][n-2]), 0.0) != 0) {
            solusi = gaussSPL(A);
            for (i=0; i<solusi.length; i++) {
                System.out.printf("x[%d] = %f", (i+1), (solusi[i]));
                System.out.println();
                A.spl += "x["+(i+1)+"] = "+(solusi[i]+"\n"); // <- Buat output ke file
            }
        }
    }
    
    /* mencari solusi pada SPL yang memiliki solusi unik */
    double[] gaussSPL(Matrix A) {
        int i, j, m = A.rows, n = A.cols;
        double[] x = new double[m];     // solusi SPL
        for (i=m-1; i>=0; i--) {
            double temp = 0.0;
            for (j=i+1; j<m; j++) {
                temp += (A.M[i][j] * x[j]);
            }
            x[i] = ((A.M[i][n-1])-temp)/(A.M[i][i]);
        }
        return x;
    }
}