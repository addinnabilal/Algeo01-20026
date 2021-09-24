import java.io.*;
import java.util.*;

public class Matrix {
    private double[][] M =null;
    private int rows=0, cols=0;

    public Matrix(int rows, int cols) {
        M = new double[rows][cols];
        this.rows = rows;
        this.cols =cols;
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
                A.M[i][j]= input.nextInt();
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

    public static void main(String[] args) {
        Matrix A = Matrix.inputSPL();
        Matrix AT = replaceColumn(A, 0);
        cramer(A);
      }
}
