import java.io.*;
import java.util.*;


public class Operasi {
    
    Scanner input = new Scanner(System.in);

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

    /* tambahkan baris i2 dengan hasil kali i1 dengan suatu konstanta k */
    void replaceRow(Matrix m, int i1, int i2, double k) {
        int j, n = m.cols;
        for (j=0; j<n; j++) {
            m.M[i2][j] = m.M[i2][j] + (k * m.M[i1][j]);
        }
    }

    boolean isSPLRowZero(Matrix m, int i) {
        boolean rowZero = true;
        int j=0;
        while (rowZero && (j < m.cols-1)) {
            if (Double.compare(m.M[i][j], 0.0) != 0) {
                rowZero = false;
            }
            j++;
        }
        return rowZero;
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


    Matrix InvAdj(Matrix M){
        int a,b;
        Matrix temp = new Matrix(M.rows, M.cols);
        float detInv = 1/DetGauss(M);
        for(int i=0; i<M.rows; i++){
            for(int j=0; j<M.cols; j++){
                Matrix temp2 = new Matrix(M.rows-1, M.cols-1);
                a = 0;
                for(int k=0; k<M.rows; k++){
                    b = 0;
                    for(int l=0; l<M.cols; l++){
                        if(k!=i && l!=j){
                            temp2.M[a][b] = M.M[k][l];
                            b++;
                        }
                    }
                    if(k!=i){
                        a++;
                    }
                }
                temp.M[i][j] = (DetGauss(temp2))*detInv;
                if(((i+1)%2==0) && (j+1)%2!=0){
                    temp.M[i][j] *= (-1);
                }else if((i==0||(i+1)%2!=0) && (j+1)%2==0){
                    temp.M[i][j] *= (-1);
                }

            }
        }
        Matrix mtemp = new Matrix(M.rows, M.cols);
        for(int i=0; i<mtemp.rows;i++){
            for(int j=0; j<mtemp.cols;j++){
                mtemp.M[j][i] = temp.M[i][j];
            }
        }


        return mtemp;

    }

    void SaveFile(Matrix M){
        System.out.println("Masukkan nama file untuk di save");
        String fileName = input.nextLine();
        String temp = "";
        for(int i=0;i<M.rows;i++){
            for(int j=0;j<M.cols;j++){
                if(j==M.cols-1){
                    temp += (M.M[i][j]);
                }else{
                    temp += (M.M[i][j]) + " ";
                }
            }
            temp += "\n";
        }
        
        try {
            FileWriter writer = new FileWriter(fileName+".txt");
            writer.write(temp);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    void SaveFile(float M){
        System.out.println("Masukkan nama file untuk di save");
        String fileName = input.nextLine();
        String temp = "Determinan dari matriks adalah : ";
        temp += (M);
        
        try {
            FileWriter writer = new FileWriter(fileName+".txt");
            writer.write(temp);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    void SaveFile(String name){
        System.out.println("Masukkan nama file untuk di save");
        String fileName = input.nextLine();
        String temp;
        temp = name;
        
        try {
            FileWriter writer = new FileWriter(fileName+".txt");
            writer.write(temp);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    
}
