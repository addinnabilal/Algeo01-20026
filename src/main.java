import java.io.*;
import java.util.*;
public class main {

	  static void printmenu(){
		System.out.println("MENU");
		System.out.println("\n1. Sistem persamaan linier\n2. determinan\n3. Matriks balikan\n4. Interpolasi polinom\n5. Regresi Linier berganda\n6. Keluar");
	  }
	  static void printmenuspl(){
		System.out.println("\nPilih Metode\n1. Metode eliminasi Gauss\n2. Metode eliminasi Gauss-Jordan\n3. Metode matriks balikan\n4. Kaidah Cramer");
	  }
  
	  static void printmenudet(){
		System.out.println("\nPilih Metode\n1. Metode eliminasi Gauss\n2. Metode kofaktor");
	  }
  
	  static void printmenuinverse(){
		System.out.println("\nPilih Metode\n1. Metode eliminasi Gauss\n2. Metode adjoin");
	  }
  
	  public static void main(String[] args) throws IOException {
		
		Operasi operasi = new Operasi();
		Gauss gauss = new Gauss();
		Scanner input = new Scanner(System.in);
		  printmenu();
		  int userinput = input.nextInt();
		  while (userinput !=6) {
			Matrix A;
			if (userinput == 1) {
			  printmenuspl();
			  int userinputspl = input.nextInt();
			  System.out.println("\nPILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
			  int userinputtipe = input.nextInt();
			  if (userinputspl == 1){
				  if(userinputtipe==1){
					A = new Matrix(1);
				  }else{
					A = new Matrix(4);
				  }
				A = gauss.eselonBaris(A);
				gauss.printSPL(A);
				System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				int a = input.nextInt();
				if(a==1){
					operasi.SaveFile(A.spl);
				}
				
			  }
			  else if (userinputspl == 2){
				if(userinputtipe==1){
					A = new Matrix(1);
				  }else{
					A = new Matrix(4);
				  }
				A = gauss.eselonBarisRed(A);
				gauss.printSPL(A);
				System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				int a = input.nextInt();
				if(a==1){
					operasi.SaveFile(A.spl);
				}
				
			  }              
			  else if (userinputspl == 3){
				if(userinputtipe==1){
					A = new Matrix(1);
				  }else{
					A = new Matrix(4);
				  }

				A.splBalikan(A);
				System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				int a = input.nextInt();
				if(a==1){
					operasi.SaveFile(A.spl);
				}
				
			  }
			  else if (userinputspl == 4){
				if(userinputtipe==1){
					A = new Matrix(1);
				  }else{
					A = new Matrix(4);
				  }
				A.cramer(A);
				System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				int a = input.nextInt();
				if(a==1){
					operasi.SaveFile(A.spl);
				}
				
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
				System.out.println(operasi.DetGauss(A));
				System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				int a = input.nextInt();
				if(a==1){
					operasi.SaveFile(operasi.DetGauss(A));
				}
			  }
			  else if (userinputspl == 2){
				if (userinputtipe==1){
					A = new Matrix(2);
				  }
				  else {
					A= new Matrix(4);
				  } 
				  System.out.println(operasi.DetCofactor(A));
				  System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				  int a = input.nextInt();
				  if(a==1){
					operasi.SaveFile(operasi.DetCofactor(A));
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
				A = operasi.inverse(A);
				operasi.DisplayMatrix(A);

				System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				int a = input.nextInt();
				if(a==1){
					operasi.SaveFile(A);
				}

			  }
			  else if (userinputspl == 2){
				if (userinputtipe==1){
					A = new Matrix(2);
				  }
				  else {
					A= new Matrix(4);
				  }
				  A = operasi.InvAdj(A);
				  operasi.DisplayMatrix(A);
				  System.out.println("\nSimpan hasil ke file baru ?\n1. Ya\n2. Tidak");
				  int a = input.nextInt();
				  if(a==1){
					operasi.SaveFile(A);
				}

			  }      
			}
			else if (userinput == 4) {
				System.out.println("PILIH TIPE MASUKAN\n1.Dari keyboard\n2.Dari file");
				int userinputtipe = input.nextInt();
				if (userinputtipe==1){
				  A = new Matrix(3);
				}
				else {
				  A= new Matrix(4);
				  A=A.toInterpolasi(A);
				}
				A.interpolasi(A); 
			  
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
		  
		  input.close();
		}
}
