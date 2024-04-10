// Timothy Kwock
// CS1B Assignment 7 - Complex Matrix
import java.util.Scanner;
class Complex implements Cloneable, Comparable<Complex>
{
   private double a;
   private double b;
   public Complex(double a, double b)
   {
      this.a = a;
      this.b = b;
   }
   public Complex(double a)
   {
      this.a = a;
      this.b = 0.0;
   }
   public Complex()
   {
      this.a = 0.0;
      this.b = 0.0;
   }
   public Complex add(Complex second)
   {
      double a = this.a + second.getRealPart();
      double b = this.b + second.getImaginaryPart();
      return new Complex(a, b);
   }
   public Complex subtract(Complex second)
   {
      double a = this.a - second.getRealPart();
      double b = this.b - second.getImaginaryPart();
      return new Complex(a, b);
   }
   public Complex multiply(Complex second)
   {
      double a = this.a * second.getRealPart() - this.b *
              second.getImaginaryPart();
      double b = this.b * second.getRealPart() + this.a *
              second.getImaginaryPart();
      return new Complex(a, b);
   }
   public Complex divide(Complex second)
   {
      double a = (this.a * second.getRealPart() + this.b *
              second.getImaginaryPart()) / (Math.pow(second.getRealPart(), 2) +
              Math.pow(second.getImaginaryPart(), 2));
      double b = (this.b *
              second.getRealPart() - this.a * second.getImaginaryPart()) /
              (Math.pow(second.getRealPart(), 2) +
                      Math.pow(second.getImaginaryPart(), 2));
      return new Complex(a, b);
   }
   public double abs()
   {
      return Math.sqrt(Math.pow(this.a, 2) + Math.pow(this.b, 2));
   }
   @Override
   public String toString()
   {
      if(this.b == 0)
      {
         return String.valueOf(a);
      }
      else
      {
         return "(" + a + " + " + b + "i)";
      }
   }
   public double getRealPart()
   {
      return a;
   }
   public double getImaginaryPart()
   {
      return b;
   }
   @Override
   public Object clone()
   {
      try
      {
         return (Complex)super.clone();
      }
      catch (CloneNotSupportedException ex)
      {
         System.out.println("Error: Matrices could not be calculated due
                 to "
                         + "mismatching matrix sizes");
         return null;
      }
   }
   @Override
   public int compareTo(Complex second)
   {
      if (this.abs() > second.abs())
      {
         return 1;
      }
      else if (this.abs() < second.abs())
      {
         return -1;
      }
      else
      {
         return 0;
      }
   }
}
abstract class GenericMatrix<E extends Object> {
   /** Abstract method for adding two elements of the matrices */
   protected abstract E add(E o1, E o2);
   /** Abstract method for multiplying two elements of the matrices */
   protected abstract E multiply(E o1, E o2);
   /** Abstract method for defining zero for the matrix element */
   protected abstract E zero();
   /** Add two matrices */
   public E[][] addMatrix(E[][] matrix1, E[][] matrix2) {
// Check bounds of the two matrices
      if ((matrix1.length != matrix2.length) ||
              (matrix1[0].length != matrix2[0].length)) {
         throw new RuntimeException(
                 "The matrices do not have the same size");
      }
      E[][] result =
              (E[][])new Object[matrix1.length][matrix1[0].length];
// Perform addition
      for (int i = 0; i < result.length; i++)
         for (int j = 0; j < result[i].length; j++) {
            result[i][j] = add(matrix1[i][j], matrix2[i][j]);
         }
      return result;
   }
   /** Multiply two matrices */
   public E[][] multiplyMatrix(E[][] matrix1, E[][] matrix2) {
// Check bounds
      if (matrix1[0].length != matrix2.length) {
         throw new RuntimeException(
                 "The matrices do not have compatible size");
      }
// Create result matrix
      E[][] result =
              (E[][])new Object[matrix1.length][matrix2[0].length];
// Perform multiplication of two matrices
      for (int i = 0; i < result.length; i++) {
         for (int j = 0; j < result[0].length; j++) {
            result[i][j] = zero();
            for (int k = 0; k < matrix1[0].length; k++) {
               result[i][j] = add(result[i][j],
                       multiply(matrix1[i][k], matrix2[k][j]));
            }
         }
      }
      return result;
   }
   /** Print matrices, the operator, and their operation result */
   public static void printResult(
           Object[][] m1, Object[][] m2, Object[][] m3, char op) {
      for (int i = 0; i < m1.length; i++) {
         for (int j = 0; j < m1[0].length; j++)
            System.out.print(" " + m1[i][j]);
         if (i == m1.length / 2)
            System.out.print(" " + op + " ");
         else
            System.out.print(" ");
         for (int j = 0; j < m2.length; j++)
            System.out.print(" " + m2[i][j]);
         if (i == m1.length / 2)
            System.out.print(" = ");
         else
            System.out.print(" ");
         for (int j = 0; j < m3.length; j++)
            System.out.print(m3[i][j] + " ");
         System.out.println();
      }
   }
}
class ComplexMatrix extends GenericMatrix<Complex>
{
   @Override
   protected Complex add(Complex o1, Complex o2)
   {
      return o1.add(o2);
   }
   @Override
   protected Complex multiply(Complex o1, Complex o2)
   {
      return o1.multiply(o2);
   }
   @Override
   protected Complex zero()
   {
      return new Complex();
   }
}
public class ComplexMatrixCalculator
{
   public static void main(String[] args)
   {
      final String NEW_LINE = "\n";
// create 2 matrix arrays of size 3x3
      ComplexMatrix complexMatrix = new ComplexMatrix();
      Complex[][] matrix1 = new Complex[3][3];
      matrix1[0][0] = complexMatrix.zero();
      matrix1[0][1] = new Complex(5.5, 1.0);
      matrix1[0][2] = new Complex(3.5, 4.0);
      matrix1[1][0] = new Complex(1.3, 7.7);
      matrix1[1][1] = new Complex(3.0, 7.5);
      matrix1[1][2] = new Complex(4.2, 1.1);
      matrix1[2][0] = new Complex(4.6, 6.4);
      matrix1[2][1] = new Complex(2.0, 1.0);
      matrix1[2][2] = new Complex(3.3, 5.5);
      Complex[][] matrix2 = new Complex[3][3];
      matrix2[0][0] = new Complex(2.0, 0.5);
      matrix2[0][1] = new Complex(2.4, 3.7);
      matrix2[0][2] = new Complex(1.0, 1.2);
      matrix2[1][0] = new Complex(5.5, 5.0);
      matrix2[1][1] = new Complex(1.2, 2.1);
      matrix2[1][2] = new Complex(6.6, 0.1);
      matrix2[2][0] = new Complex(1.0, 1.1);
      matrix2[2][1] = new Complex(0.1 , 0.1);
      matrix2[2][2] = complexMatrix.zero();
// display result of addition of matrices
      System.out.println("Matrix1 + Matrix 2:");
      complexMatrix.printResult(matrix1, matrix2,
              complexMatrix.addMatrix(matrix1, matrix2), '+');
      System.out.print(NEW_LINE);
// display result of multiplication of matrices
      System.out.println("Matrix1 * Matrix 2:");
      complexMatrix.printResult(matrix1, matrix2,
              complexMatrix.multiplyMatrix(matrix1, matrix2), '*');
      System.out.print(NEW_LINE);
// create a 2x2 matrix array
      Complex[][] matrix3 = new Complex[2][2];
      matrix3[0][0] = new Complex(1.0, 2.0);
      matrix3[0][1] = complexMatrix.zero();
      matrix3[1][0] = complexMatrix.zero();
      matrix3[1][1] = new Complex(3.0, 4.5);
// add 2x2 matrix to 3x3 and catch resulting error
      try
      {
         System.out.println("Matrix3 + Matrix 1:");
         complexMatrix.printResult(matrix3, matrix1,
                 complexMatrix.addMatrix(matrix3, matrix1), '+');
      }
      catch (RuntimeException ex)
      {
         System.out.println("ERROR: Matrix sizes are incompatible for the
                 "
                         + "calculation requested.");
      }
   }
}complexMatrix.printResult(matrix1, matrix2,
                           complexMatrix.multiplyMatrix(matrix1, matrix2), '*');
        System.out.print(NEW_LINE);
// create a 2x2 matrix array
Complex[][] matrix3 = new Complex[2][2];
matrix3[0][0] = new Complex(1.0, 2.0);
matrix3[0][1] = complexMatrix.zero();
matrix3[1][0] = complexMatrix.zero();
matrix3[1][1] = new Complex(3.0, 4.5);
// add 2x2 matrix to 3x3 and catch resulting error
try
        {
        System.out.println("Matrix3 + Matrix 1:");
complexMatrix.printResult(matrix3, matrix1,
                          complexMatrix.addMatrix(matrix3, matrix1), '+');
        }
        catch (RuntimeException ex)
        {
        System.out.println("ERROR: Matrix sizes are incompatible for the
                                   "
                                   + "calculation requested.");
}
        }
        }
