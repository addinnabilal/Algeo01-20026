import java.io.*;
import java.util.*;

public class Matrix {
    double[][] M;
    int rows=0, cols=0;
    Operasi operasi = new Operasi();
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
        this.M = new double[rows][cols];
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
    }

  

    public Matrix inputMatrixFile() throws IOException{ 
      String filename = input.nextLine();

      int row=0,col=0;
      Scanner inputrow = new Scanner(new File(filename));
      while (inputrow.hasNextLine()) {
        row++;
        Scanner inputcol = new Scanner(inputrow.nextLine());
        while (inputcol.hasNextInt())
        {
          col++;
        }
      }

      FileReader fileReader = new FileReader(filename);
      double[][] data = new double[row][col];
      BufferedReader br = new BufferedReader(fileReader);

      for (int i=0;i<row;i++) {
        String line=br.readLine();
        String[] linesplitted = line.split("\\s+");
        double[] convertedline= new double[col];
        for (int j=0;j<col;j++){
          convertedline[j]=Double.parseDouble(linesplitted[j]);
        }
        data[i]=convertedline;
      }

      Matrix A= new Matrix(data);
      return A;
    }

    void cramer(Matrix A){
      for (int j=0; j<A.cols-1;j++ ){
        float det= operasi.DetGauss(A);
        float x = operasi.DetGauss(replaceColumn(A, j))/det;
        System.out.printf("x[%d] = %f\n",j+1,x);
      }
    }

    public double determinant(){
      double det=1;
      return det;
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
    
    public void display() {
      for (double[] row: M){
        System.out.println(Arrays.toString(row));
      }
    }
    public static void main(String[] args) throws IOException {
        Matrix A = new Matrix(1);
        A.cramer(A);
      }
}
