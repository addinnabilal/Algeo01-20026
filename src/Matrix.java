import java.io.*;
import java.util.*;

public class Matrix {
    double[][] M;
    int rows=0, cols=0;
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
      float det= operasi.DetGauss(B);
      for (int j=0; j<A.cols-1;j++ ){
        float x = operasi.DetGauss(replaceColumn(A, j))/det;
        System.out.printf("x[%d] = %f\n",j+1,x);
      }
      File fileOutput = createFile();
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
    void splBalikan() {
      int i,j,k;
      double sum;
      Matrix aug = new Matrix(rows, cols-1);
      Matrix res = new Matrix(rows, 1);
      for (i=0; i< aug.rows; i++){
        for (j=0; j<aug.cols;j++){
          aug.M[i][j]= M[i][j];
        }
      }
      for (i=0; i< aug.rows; i++){
        res.M[i][0]= M[i][cols-1];
      }
      aug = operasi.inverse(aug);
      for (i=0; i< aug.rows; i++){
        sum=0;
        for (k=0; k<res.rows;k++) {
            sum = sum + aug.M[i][k] * res.M[k][0];
        }
          System.out.printf("x[%d] = %f\n",i+1,sum);
      }
    }

    void regresi(){
      int i,j,k;
      double sum;
      Matrix aug = new Matrix(rows, cols-1);
      Matrix res = new Matrix(rows, 1);
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
      }

    void interpolasi(Matrix A){
      A.gauss.eselonBaris(A);
    }

    public void display() {
      for (double[] row: M){
        System.out.println(Arrays.toString(row));
      }
    }

    File createFile() throws IOException{
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine().trim();
        File file = new File(fileName);
        while (!file.createNewFile()) {
          fileName = input.nextLine().trim();
          file = new File(fileName);
        }
        return file;
    }

    static void printmenu(){
      System.out.println("MENU");
      System.out.println("1. Sistem persamaan linier\n2. determinan\n3. Matriks balikan\n4. Interpolasi polinom\n5. Regresi Linier berganda\n6.Keluar");
    }
    static void printmenuspl(){
      System.out.println("1. Metode eliminasi Gauss\n2. Metode eliminasi Gauss-Jordan\n3. Metode matriks balikan\n4. Kaidah Cramer");
    }

    static void printmenudet(){
      System.out.println("1. Metode eliminasi Gauss\n2. Metode kofaktor");
    }

    static void printmenuinverse(){
      System.out.println("1. Metode eliminasi Gauss\n2. Metode adjoin");
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Matrix A;
        printmenu();
        int userinput = input.nextInt();
        while (userinput !=6) {
          printmenu();
          if (userinput == 1) {
            printmenuspl();
            int userinputspl = input.nextInt();
            System.out.println("PILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
            int userinputtipe = input.nextInt();
            if (userinputspl == 1){
              if (userinputtipe==1){
                A = new Matrix(1);
              }
              else {
                A= new Matrix(4);
              }
              A=A.gauss.eselonBaris(A);
              A.gauss.printSPL(A);
            }
            else if (userinputspl == 2){
              if (userinputtipe==1){
                A = new Matrix(1);
              }
              else {
                A= new Matrix(4);
              }              
              A=A.gauss.eselonBarisRed(A);
              A.gauss.printSPL(A);
            }              
            else if (userinputspl == 3){
              if (userinputtipe==1){
                A = new Matrix(1);
              }
              else {
                A= new Matrix(4);
              }              
              A.splBalikan();
            }
            else if (userinputspl == 4){
              if (userinputtipe==1){
                A = new Matrix(1);
              }
              else {
                A= new Matrix(4);
              }
              A.cramer(A);
            }
          }
          else if (userinput == 2) {
            printmenudet();
            int userinputspl = input.nextInt();
            System.out.println("PILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
            int userinputtipe = input.nextInt();
            if (userinputspl == 1){
              if (userinputtipe==1){
                A = new Matrix(2);
              }
              else {
                A= new Matrix(4);
              }     
              System.out.println(A.operasi.DetGauss(A));
            }
            else if (userinputspl == 2){
              if (userinputtipe==1){
                A = new Matrix(2);
              }
              else {
                A= new Matrix(4);
              }                 
            }              
          }
          else if (userinput == 3) {
            printmenuinverse();
            int userinputspl = input.nextInt();
            System.out.println("PILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
            int userinputtipe = input.nextInt();
            if (userinputspl == 1){
              if (userinputtipe==1){
                A = new Matrix(2);
              }
              else {
                A= new Matrix(4);
              }                   
              A.operasi.inverse(A).display();;
            }
            else if (userinputspl == 2){
              if (userinputtipe==1){
                A = new Matrix(2);
              }
              else {
                A= new Matrix(4);
              }                 }      
          }
          else if (userinput == 4) {
            System.out.println("PILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
            int userinputtipe = input.nextInt();
            if (userinputtipe==1){
              A = new Matrix(3);
            }
            else {
              A= new Matrix(4);
            }               
          }
          else if (userinput == 5) {
            System.out.println("PILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
            int userinputtipe = input.nextInt();
            if (userinputtipe==1){
              A = new Matrix(1);
            }
            else {
              A= new Matrix(4);
            }     
            A.regresi();
          }
          printmenu();
          userinput=input.nextInt();
        }
      }
}
