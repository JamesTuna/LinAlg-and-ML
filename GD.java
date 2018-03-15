import java.util.*;
public class GD{

  /**
  Gradient descent to approach the target we want

  a: column vector of shape     [n,1]
  B: feature matrix of shape    [n,m]
  x: parameters we train        [m,1]

  Error   :             (a-Bx)^T(a-Bx)
  Gradient:             2(x^TB^TB-a^TB)

  Iterate IT times until error converge
  Learning rate is Ita

  Note: you can initialize and train multiple x to make it more likely to converge at global optimum
  (the essence of SGD and the beauty of linear algebra)

  El psy congroo

  **/
  public static Scanner reader = new Scanner(System.in);
  public static double dot(double[] vector1,double[] vector2){
    int n = vector1.length;
    int sum = 0;
    for(int i=0;i<n;i++)
      sum += vector1[i]*vector2[i];
    return sum;
  }
  public static void printMatrix(double[][] m){
    int r = m.length;
    int c = m[0].length;
    for(int i=0;i<r;i++){
      for(int j=0;j<c;j++)
        System.out.print(m[i][j]+" ");
      System.out.println("");
    }
    return ;
  }
  public static double[][] times(double m1,double[][] m2){

    int m2_d1 = m2.length;
    int m2_d2 = m2[0].length;

    double [][] result = new double [m2_d1][m2_d2];
    for(int i=0;i<m2_d1;i++)
      for(int j=0;j<m2_d2;j++)
        result[i][j]=m1*m2[i][j];
    return result;
  }

  public static double[][] multiply(double[][] m1,double[][] m2){
    int m1_d1 = m1.length;
    int m1_d2 = m1[0].length;
    int m2_d1 = m2.length;
    int m2_d2 = m2[0].length;
    double [][] dft = {{0}};
    if(m1_d2!=m2_d1){
      System.out.println("invalid input");
      return dft;
    }
    double [][] result = new double [m1_d1][m2_d2];
    for(int i=0;i<m1_d1;i++)
      for(int j=0;j<m2_d2;j++){
        result[i][j]=0;
        for(int k=0;k<m1_d2;k++)
          result[i][j]+=m1[i][k]*m2[k][j];
      }
    return result;
  }
  public static double[][] minus(double[][] m1,double[][] m2){
    int m1_d1 = m1.length;
    int m1_d2 = m1[0].length;
    int m2_d1 = m2.length;
    int m2_d2 = m2[0].length;
    double [][] dft = {{0}};
    if(!(m1_d1==m2_d1&&m1_d2==m2_d2)){
      System.out.println("invalid input");
      return dft;
    }
    double [][] result = new double [m1_d1][m1_d2];
    for(int i=0;i<m1_d1;i++)
      for(int j=0;j<m2_d2;j++)
        result[i][j]=m1[i][j]-m2[i][j];
    return result;
  }

  public static double[][] add(double[][] m1,double[][] m2){
    int m1_d1 = m1.length;
    int m1_d2 = m1[0].length;
    int m2_d1 = m2.length;
    int m2_d2 = m2[0].length;
    double [][] dft = {{0}};
    if(!(m1_d1==m2_d1&&m1_d2==m2_d2)){
      System.out.println("invalid input");
      return dft;
    }
    double [][] result = new double [m1_d1][m1_d2];
    for(int i=0;i<m1_d1;i++)
      for(int j=0;j<m2_d2;j++)
        result[i][j]=m1[i][j]+m2[i][j];
    return result;
  }
  public static double[][] transpose(double[][] m){
    int m_d1 = m.length;
    int m_d2 = m[0].length;
    double [][] result = new double [m_d2][m_d1];
    for(int i=0;i<m_d1;i++)
      for(int j=0;j<m_d2;j++){
        result[j][i]=m[i][j];
    }
    return result;
  }
  public static void main(String args[]){
    int m,n,iter=0;
    System.out.print("please enter n:");
    n = reader.nextInt();
    System.out.print("please enter m:");
    m = reader.nextInt();

    double [][]a = new double[n][1];    // n,1
    double [][]B = new double[n][m];    // n,m
    double [][]x = new double[m][1];    // m,1
    // get input and initialize x
    for (int i=0;i<m;i++){
      System.out.println("please enter b"+(i+1)+": \nin format of b1 b2 b3 ... bn");
      for (int j=0;j<n;j++){
        B[j][i] =  reader.nextInt();
      }
    }
    System.out.println("please enter a: \nin format of a1 a2 a3 ... an");
    for (int i=0;i<n;i++){
      a[i][0] = reader.nextInt();
    }
    for (int i=0;i<m;i++)
      x[i][0] = 0.1;
    printMatrix(B);
    printMatrix(a);

    /*
    you can use it as a test, but please uncomment the initialization codes above this line
    double[][] a = {{3},{4}};
    double[][] B = {{1,0},{0,1}};
    double[][] x = {{0.1},{0.1}};
    */
    double ita   = 1e-3;
    double error=999,threshold = 1e-8;
    // iteration starts here, we can reuse term a^TB and B^TB so we cashe it to make it faster
    double [][] t1 = multiply(transpose(a),B);  //  1,m
    double [][] t2 = multiply(transpose(B),B);  //  m,m
    double [][] deltaX;
    // now we enter the loop, each loop push us closer to the result
    System.out.println("training starts, please wait and enjoy...");
    while(error >= threshold){
      // calculate error
      error = multiply(transpose(minus(a,multiply(B,x))),minus(a,multiply(B,x)))[0][0]; // this should be scalar
      System.out.println("Iteration: "+iter+" error is: "+error);
      // update x
      deltaX = minus(multiply(transpose(x),t2),t1); // 1,m
      deltaX = times(-2*ita,deltaX);
      x      = add(x,transpose(deltaX));
      iter++;
    }
    printMatrix(x);
    return ;
  }
}
