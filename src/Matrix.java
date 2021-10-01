import java.io.*;
import java.util.*;

public class Matrix {
    double[][] M;
    int rows=0, cols=0;
    String spl="";
    Operasi operasi = new Operasi();
    Gauss gauss = new Gauss();

    Scanner input = new Scanner(System.in);
    //konstruktor
    public Matrix(int rows, int cols) {
        M = new double[rows][cols];
        this.rows = rows;
        this.cols =cols;
    }

    public Matrix(double[][] M){
      rows=M.length;
      cols=M[0].length;
      this.M =new double[rows][cols];
      for(int i=0;i<rows;i++) {
        for(int j=0;j<cols;j++) {
            this.M[i][j]= M[i][j];
          }
      }
  }
    public Matrix(int a){
      if (a==1) {
        System.out.println("Masukkan jumlah baris");
        int rows = input.nextInt();
        System.out.println("Masukkan jumlah kolom");
        int cols = input.nextInt();
        this.M = new double[rows][cols];
        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                this.M[i][j]= input.nextDouble();
              }
            }
      this.rows=rows;
      this.cols=cols;
      }
      else if (a==2) {
        System.out.println("Masukkan jumlah baris dan kolom");
        int n = input.nextInt();
        this.M = new double[n][n];
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                this.M[i][j]= input.nextDouble();
              }
            }
      this.rows=n;
      this.cols=n;
      }
      else if (a==3) {
        System.out.println("Masukkan jumlah baris dan kolom");
        int n = input.nextInt();
        this.M = new double[n][n+1];
        for (int i=0;i<n;i++){
          double x = input.nextDouble();
          for (int j=0;j<n;j++){
            this.M[i][j]= Math.pow(x,j);
          }
          this.M[i][n]=input.nextDouble();
        }
      this.rows=n;
      this.cols=n+1;
      }
      else if(a==4){
        System.out.println("Masukkan nama file ");
        String filename = input.nextLine();
        String text = "";
        try {
            FileReader reader = new FileReader(filename);
            int data = reader.read();
            while(data!=-1){
                text += (char) data;
                data = reader.read();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] ab = text.split("\\r?\\n");
        this.rows = ab.length;
        this.cols = ab[0].split(" ").length;
        this.M = new double[rows][cols];
        for(int i = 0; i < rows; i++){
            String[] temp = ab[i].split(" ");
            for(int j = 0; j <cols;j++){
                M[i][j] = Double.parseDouble(temp[j]);
            }
        }
    }
    }

    void cramer(Matrix A) throws IOException{
      Matrix B = new Matrix(A.rows, A.cols-1);
      for (int i=0;i<B.rows;i++){
        for (int j=0;j<B.cols;j++){
          B.M[i][j]=A.M[i][j];
        }
      }
      if (B.cols != B.rows){
        System.out.println("Jumlah equation tidak sama dengan jumlah variabel (jumlah baris dan kolom pada matriks koefisien tidak sama). Metode cramer tidak dapat diterapkan pada SPL ini.");
        A.spl += "Jumlah equation tidak sama dengan jumlah variabel (jumlah baris dan kolom pada matriks koefisien tidak sama). Metode cramer tidak dapat diterapkan pada SPL ini.";
      }
      else if ((int)operasi.DetGauss(B)!=0){
        double det = operasi.DetGauss(B);
        for (int j=0; j<A.cols-1;j++ ){
          double x = operasi.DetGauss(replaceColumn(A, j))/det;
          System.out.printf("x[%d] = %f\n",j+1,x);
          A.spl += ("x["+(j+1)+"] = "+x+"\n");
        }
      }
      else {
        System.out.println("Determinan matriks koefisien = 0. Metode cramer tidak dapat diterapkan pada SPL ini.");
        A.spl += "Determinan matriks koefisien = 0. Metode cramer tidak dapat diterapkan pada SPL ini.";
      }
    }


   Matrix replaceColumn(Matrix A, int col){
      Matrix AT = new Matrix(A.rows, A.cols-1);
      for (int i=0; i< AT.rows;i++){
        for (int j=0;j<AT.cols;j++){
          if (j==col){
            AT.M[i][j]=A.M[i][A.cols-1];
          }
          else{
            AT.M[i][j]=A.M[i][j];
          }
        }
      }
      return AT;
    }

    Matrix identity(){
      Matrix mtemp = new Matrix(rows, cols);
      for(int i = 0; i < rows; i++){
          for(int j = 0; j < cols; j++){
              if(i==j){
                  mtemp.M[i][j] = 1;
              }
              else{
                  mtemp.M[i][j] = 0;
              }
          }
      }
      return mtemp;
     }

     void splBalikan(Matrix A) {
      int i,j,k;
      double sum;
      Matrix aug = new Matrix(rows, cols-1);
      Matrix res = new Matrix(rows, 1);
      for (i=0; i< aug.rows; i++){
        for (j=0; j<aug.cols;j++){
          aug.M[i][j]= M[i][j];
        }
      }
      if (aug.cols !=aug.rows){
        System.out.println("Jumlah equation tidak sama dengan jumlah variabel (jumlah baris dan kolom pada matriks koefisien tidak sama). Metode matriks balikan tidak dapat diterapkan pada SPL ini.");
        A.spl += "Jumlah equation tidak sama dengan jumlah variabel (jumlah baris dan kolom pada matriks koefisien tidak sama). Metode matriks balikan tidak dapat diterapkan pada SPL ini.";

      }
      else if ((int)operasi.DetGauss(aug)!=0){
        for (i=0; i< aug.rows; i++){
          res.M[i][0]= M[i][cols-1];
        }
        aug = aug.operasi.inverse(aug);
        for (i=0; i< aug.rows; i++){
          sum=0;
          for (k=0; k<res.rows;k++) {
              sum = sum + aug.M[i][k] * res.M[k][0];
          }
            System.out.printf("x[%d] = %f\n",i+1,sum);
            A.spl += ("x["+(i+1)+"] = "+sum+"\n");
        }
      }
      else {
        System.out.println("Determinan matriks koefisien = 0, matriks koefisien tidak memiliki balikan. Metode matriks balikan tidak dapat diterapkan pada SPL ini.");
        A.spl += "Determinan matriks koefisien = 0, matriks koefisien tidak memiliki balikan. Metode matriks balikan tidak dapat diterapkan pada SPL ini.";
      } 
    }

    void regresi(){
      int i,j,k;
      double sum;
      Matrix aug = new Matrix(rows, cols-1);
      Matrix res = new Matrix(rows, 1);
      double xk[] = new double[aug.cols];
      for (k=0; k<aug.cols;k++){
        System.out.printf("Masukkan nilai xk[%d] yang akan ditaksir nilainya: ", k+1);
        xk[k]= input.nextDouble();
      }

      for (i=0; i< aug.rows; i++){
        for (j=0; j<aug.cols;j++){
          aug.M[i][j]= M[i][j];
        }
      }
      for (i=0; i< aug.rows; i++){
        res.M[i][0]= M[i][cols-1];
      }
      Matrix augT = new Matrix(aug.cols,aug.rows);

      for (i=0;i<augT.rows;i++){
        for (j=0;j<augT.cols;j++){
          augT.M[i][j]=aug.M[j][i];
        }
      }
      
      // bikin matriks perkalian aug dan augT
      Matrix hasilkali = new Matrix (augT.rows, aug.cols);
      for (i=0; i< augT.rows; i++){
        for (j=0;j<aug.cols;j++){
            sum=0;
            for (k=0; k<aug.rows;k++) {
                sum = sum + augT.M[i][k] * aug.M[k][j];
            }
            hasilkali.M[i][j]=sum;
        }
    }

      //bikin matriks normal estimation equation        
        Matrix est = new Matrix(aug.cols+1,aug.cols+2);
        //baris 1 kolom 1
        est.M[0][0]=rows;
        //isi kolom 0
        for (i=1;i<est.rows;i++){
          sum=0;
          for (j=0;j<rows;j++){
            sum += M[j][i-1];
          }
          est.M[i][0]=sum;
        }
        //isi baris 0
        for (i=1;i<est.cols;i++){
          sum=0;
          for (j=0;j<rows;j++){
            sum += M[j][i-1];
          }
          est.M[0][i]=sum;
        }

        for (i=1; i<est.rows;i++){
          for (j=1;j<est.cols-1;j++){
            est.M[i][j]=hasilkali.M[i-1][j-1];
          }
        }
        //isi y
        for (i=1;i<est.rows;i++){
          sum=0;
          for (j=0;j<rows;j++){
            sum += aug.M[j][i-1] * res.M[j][0];
          }
          est.M[i][est.cols-1]=sum;
        }
        est.gauss.eselonBaris(est);

        int m = est.rows, n = est.cols;
        double[] solusi;
        String temp = "";
        if ((est.M[m-1][n-1] != 0) && (est.M[m-1][n-2] == 0)) {
            System.out.println("\nSolusi SPL tidak ada.");
            temp += "\nSolusi SPL tidak ada.";
            
        }
        else if ((est.M[m-1][n-1] != 0) && (est.M[m-1][n-2] != 0)) {
            solusi = gauss.gaussSPL(est);
            System.out.printf("y(x) =  %f +", (solusi[0]));
            temp += "y(x) = " + (solusi[0]) + " + ";
            for (i=1; i<solusi.length-1; i++) {
                System.out.printf(" %fx^%d +", (solusi[i]),i);
                temp += (solusi[i]+"x^" + i + " + \n");
                System.out.println();
            }
            System.out.printf(" %fx^%d\n", (solusi[solusi.length-1]), solusi.length-1);
            temp += solusi[solusi.length-1]+"x^" + (solusi.length-1) + "\n";
            double result=solusi[0];
            // taksir fungsi pada x
            for (i=1;i<solusi.length;i++){
              result += solusi[i]* xk[i-1];
            }
            System.out.printf("y = %f\n",result);
            temp += "y = " + result + "\n";
        }
        System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2.Tidak");
				  int a = input.nextInt();
				  if(a==1){
          operasi.SaveFile(temp);
				}
        
      }

      void interpolasi(Matrix A){
        System.out.println("Masukkan nilai x yang akan ditaksir nilainya: ");
        double x= input.nextDouble();
        A=gauss.eselonBaris(A);
        int i, m = A.rows, n = A.cols;
        double[] solusi;
        String temp="";
        if ((A.M[m-1][n-1] != 0) && (A.M[m-1][n-2] == 0)) {
            System.out.println("\nSolusi SPL tidak ada.");
            temp += "Solusi SPL tidak ada.\n";
        }
        else if ((A.M[m-1][n-1] != 0) && (A.M[m-1][n-2] != 0)) {
            solusi = gauss.gaussSPL(A);
            System.out.printf("p(x) =  %f +", (solusi[0]));
            temp += "p(x) = " + solusi[0] + " +";
            for (i=1; i<solusi.length-1; i++) {
                System.out.printf(" %fx^%d +", (solusi[i]),i);
                temp += " " + solusi[i] + "x^" + i + " +\n";
                System.out.println();
            }
            System.out.printf(" %fx^%d\n", (solusi[solusi.length-1]), solusi.length-1);
            temp += " " + solusi[i] + "x^" + i + "\n";
            double sum=0;
            // taksir fungsi pada x
            for (i=0;i<solusi.length;i++){
              sum += solusi[i]* Math.pow(x,i);
            }
            System.out.printf("p(%f) = %f\n",x,sum);
            temp += "p(" + x+") = " + sum + "\n";
        }
        System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2.Tidak");
				  int a = input.nextInt();
				  if(a==1){
          operasi.SaveFile(temp);
				}
      }

      public void display() {
        for (double[] row: M){
          System.out.println(Arrays.toString(row));
        }
      }
  


}
