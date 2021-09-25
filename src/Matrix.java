import java.io.*;
import java.util.*;

public class Matrix {
    private double[][] M =null;
    private int rows=0, cols=0;

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

    public static Matrix inputSPL(){
        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan jumlah baris");
        int n = input.nextInt();
        System.out.println("Masukkan jumlah kolom");
        int m = input.nextInt();
        Matrix A = new Matrix(n,m);
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                A.M[i][j]= input.nextDouble();
              }
            }
        return A;
      }
    
    public static Matrix inputSquare(){
        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan jumlah baris dan kolom ");
        int n = input.nextInt();
        Matrix A = new Matrix(n,n);
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                A.M[i][j]= input.nextInt();
              }
            }
        return A;
      }
    public static Matrix inputInterpolasi(){
      Scanner input = new Scanner(System.in);
      System.out.println("Masukkan jumlah baris dan kolom ");
      int n = input.nextInt();
      Matrix A = new Matrix(n,n+1);
      for (int i=0;i<A.rows;i++){
        double x = input.nextDouble();
        for (int j=0;j<A.cols-1;j++){
          A.M[i][j]= Math.pow(x,j);
        }
        A.M[i][A.cols-1]=input.nextDouble();
      }
      return A;
    }

    public static Matrix inputMatrixFile() throws IOException{ 
      Scanner input = new Scanner(System.in);
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
        String[] linesplitted = new line.split("\\s+");
        double[] convertedline= new double[col];
        for (int j=0;j<col;j++){
          convertedline[j]=Double.parseDouble(linesplitted[j]);
        }
        data[i]=convertedline;
      }

      Matrix A= new Matrix(data);
      return A;
    }

    static void cramer(Matrix A){
      double det = A.determinant();
      for (int j=0; j<A.cols-1;j++ ){
        double x = replaceColumn(A, j).determinant()/det;
        System.out.printf("x[%d] = %f",j+1,x);
      }
    }

    public double determinant(){
      double det=1;
      return det;
    }

    static Matrix replaceColumn(Matrix A, int col){
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
        Matrix A = Matrix.inputMatrixFile();
        A.display();
      }
}
