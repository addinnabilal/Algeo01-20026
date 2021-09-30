public class Operasi {
    String name;

    void DisplayMatrix(Matrix M){
        for(int i=0;i<M.rows;i++){
            for(int j=0;j<M.cols;j++){
                System.out.print((float) M.M[i][j]+" ");
            }
            System.out.println("");
        }
    }

    /* kali baris i dengan suatu konstanta k */
    void scaleRow(Matrix m, int i, double k) {
        int j, n = m.cols;
        for (j=0; j<n; j++) {
            m.M[i][j] = k * m.M[i][j];
        }
    }

    /* tukar baris i1 dengan baris i2 */
    void swapRow(Matrix m, int i1, int i2) {
        double[] temp = m.M[i1];
        m.M[i1] = m.M[i2];
        m.M[i2] = temp;
    }

    /* tambahkan baris i2 dengan hasil kali i1 dan suatu konstanta k */
    void replaceRow(Matrix m, int i1, int i2, double k) {
        int j, n = m.cols;
        for (j=0; j<n; j++) {
            m.M[i2][j] = m.M[i2][j] + (k * m.M[i1][j]);
        }
    }

    /* mencari determinan dengan reduksi baris */
    float DetGauss(Matrix m){
        Matrix mtemp = new Matrix(m.M);
        float hasil=1;
        int count = 0;
        int swap = -1;
        for(int i=0;i<mtemp.rows-1;i++){
            for(int j=i+1; j<mtemp.rows; j++){
                if(mtemp.M[i][i]<mtemp.M[j][i]){
                    count++;
                    swapRow(mtemp,i,j);

                }
            }
            for(int k=i+1; k<mtemp.rows; k++){
                double ratio = mtemp.M[k][i]/mtemp.M[i][i];
                for(int j=0;j<mtemp.cols;j++){
                    mtemp.M[k][j] -= ratio*mtemp.M[i][j];
                }

            }

        }
        for(int i=0; i<mtemp.rows; i++){
            hasil *= mtemp.M[i][i];
        }
        if(count==0){
            swap = 1;
        }else{
            for(int i=1; i<count; i++){
                swap = swap * (-1);
            }
        }
        return hasil*swap;
    }

    /* mencari determinan dengan ekspansi kofaktor */
    float DetCofactor(Matrix m){
        int a,b;
        double sum=0;
        Matrix mtemp;
        if(m.rows==2){
            sum = ((m.M[0][0])*(m.M[1][1])) - (((m.M[0][1])*(m.M[1][0])));
            
        }
        else{
            for(int i=0; i<m.cols; i++){
                mtemp = new Matrix(m.rows-1,m.cols-1);
                a = 0;
                for(int j=1; j<m.cols; j++){
                    b = 0;
                    for(int k=0; k<m.rows; k++){
                        if(k!=i){
                            mtemp.M[a][b] = m.M[j][k];
                            b++;
                        }
                    }
                    a++;
                }
                if((i+1)%2==0){
                    sum += m.M[0][i] * DetGauss(mtemp)* (-1);
                }else{
                    sum += m.M[0][i] * DetGauss(mtemp);
                }
                
            }
        }

        return (float) sum;

    }

    /* mencari matriks balikan menggunakan reduksi baris dan matriks identitas */
    Matrix inverse(Matrix m){
        Matrix mtemp = new Matrix(m.M);
        Matrix miden = new Matrix(m.rows,m.cols);
        miden = miden.identity();
        for(int i=0;i<mtemp.rows-1;i++){
            for(int j=i+1; j<mtemp.rows; j++){
                if(mtemp.M[i][i]<mtemp.M[j][i]){
                    swapRow(mtemp,i,j);
                    swapRow(miden,i,j);
                }
            }
            for(int k=i+1; k<mtemp.rows; k++){
                double ratio = mtemp.M[k][i]/mtemp.M[i][i];
                for(int j=0;j<mtemp.cols;j++){
                    mtemp.M[k][j] -= ratio*mtemp.M[i][j];
                    miden.M[k][j] -= ratio*miden.M[i][j];
                }

            }

        }
        for(int i=mtemp.rows-1;i>0;i--){
            for(int k=i-1; k>-1; k--){
                double ratio = mtemp.M[k][i]/mtemp.M[i][i];
                for(int j=mtemp.cols-1;j>-1;j--){
                    mtemp.M[k][j] -= ratio*mtemp.M[i][j];
                    miden.M[k][j] -= ratio*miden.M[i][j];
                }

            }

        }
        

        for(int i=0; i<mtemp.rows; i++){
            for(int j=0; j<mtemp.cols; j++){
                miden.M[i][j] /= mtemp.M[i][i];
            }
        }
        return miden;

    }

    /* mencari matriks balikan menggunakan determinan dan adjoin */
    Matrix invAdjoin (Matrix A) {
        int i, j, i2, j2, k1, k2, n = A.rows;
        double det = DetCofactor(A);
        Matrix temp = new Matrix(n-1, n-1);
        Matrix cofactor = new Matrix(n, n);
        Matrix result = new Matrix(n, n);
        
        for (i=0; i<n; i++) {   // [i, j] indeks traversal matriks awal
            for (j=0; j<n; j++) {
                i2 = 0;     // [i2, j2] indeks traversal matriks temp
                for (k1=0; k1<n; k1++) {    // [k1, k2] indeks traversal pengecekan
                    j2 = 0;
                    for (k2=0; k2<n; k2++) {
                        if ((k1 == i) || (k2 == j))    continue;
                        else {
                            temp.M[i2][j2] = A.M[k1][k2];
                        }
                        j2++;
                    }
                    i2++;
                }
                if ((i-j)%2 == 0) {
                    cofactor.M[i][j] = DetCofactor(temp);
                }
                else {
                    cofactor.M[i][j] = (-1) * DetCofactor(temp);
                }
            }
        }

        for (i=0; i<n; i++) {   // transpose cofactor
            for (j=0; j<n; j++) {
                result.M[i][j] = cofactor.M[j][i];
            }
        }

        for (i=0; i<n; i++) {   // kali tiap baris dengan 1/det
            scaleRow(result, i, (1/det));
        }

        return result;
    }
    
}
