public class Operasi {
    String name;

    void AB(String a){
        name = a;
    }

    void swap(Matrix m,int i, int j) {
        double[] temp = m.M[i];
        m.M[i] = m.M[j];
        m.M[j] = temp;
    }

    float DetGauss(Matrix m){
        Matrix mtemp = new Matrix(m.M);
        float hasil=1;
        int count = 0;
        int swap = -1;
        for(int i=0;i<mtemp.rows-1;i++){
            for(int j=i+1; j<mtemp.rows; j++){
                if(m.M[i][i]<mtemp.M[j][i]){
                    count++;
                    swap(mtemp,i,j);

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

    float DetCofactor(Matrix m){
        double sum=0;
        Matrix mtemp;
        if(m.rows==2){
            sum = ((m.M[0][0])*(m.M[1][1])) - (((m.M[0][1])*(m.M[1][0])));
            
        }
        else{
            for(int i=0; i<m.cols; i++){
                mtemp = new Matrix(m.rows-1,m.cols-1);
                for(int j=1; j<m.cols; j++){
                    for(int k=0; k<m.rows; k++){
                        if(k!=i){
                            mtemp.M[j][k] = m.M[j][k];
                        }
                    }
                }
                if((i+1)%2==0){
                    sum += m.M[0][i] * DetGauss(mtemp);
                }else{
                    sum += m.M[0][i] * DetGauss(mtemp) * (-1);
                }
                
            }
        }


        return (float) sum;

    }

    Matrix inverse(Matrix m){
        Matrix miden = new Matrix(m.rows,m.cols);
        miden = miden.identity();
        


        return miden;

    }


    
}
