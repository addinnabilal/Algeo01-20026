public class Operasi {

    void swap(Matrix m,int i, int j) {
        double[] temp = m.M[i];
        m.M[i] = m.M[j];
        m.M[j] = temp;
    }

    float DetGauss(Matrix m){
        float hasil=1;
        int count = 0;
        int swap = -1;
        for(int i=0;i<m.rows-1;i++){
            for(int j=i+1; j<m.rows; j++){
                if(m.M[i][i]<m.M[j][i]){
                    count++;
                    swap(m,i,j);

                }
            }
            for(int k=i+1; k<m.rows; k++){
                double ratio = m.M[k][i]/m.M[i][i];
                for(int j=0;j<m.cols;j++){
                    m.M[k][j] -= ratio*m.M[i][j];
                }

            }

        }
        for(int i=0; i<m.rows; i++){
            hasil *= m.M[i][i];
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

    double DetCofactor(Matrix m){
        double sum = 0;
        if(m.rows==2){
            sum = ((m.M[0][0])*(m.M[1][1])) - ((m.M[0][1])*(m.M[1][0]));
        }
        /*
        else{
            for(int i=1; i<m.cols; i++){

            }
        }
        */


        return sum;

    }

    Matrix inverse(Matrix m){
        Matrix mtemp = new Matrix(m.rows,m.cols);


        return mtemp;

    }


    
}
