package calculator;

import java.util.*;

public class Main {

    // Function to print Matrix
    static void printMatrix(int M[][], int rowSize,
                            int colSize)
    {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++)
                System.out.print(M[i][j] + " ");

            System.out.println();
        }
    }

    // Funcion para multiplicacion
    static void multiplyMatrix(int row1, int col1,
                               int A[][], int row2,
                               int col2, int B[][])
    {
        int i, j, k;

        // Check if multiplication is Possible
        if (row2 != col1) {

            System.out.println("No se puede realizar la multiplicacion");
            return;
        }

        // Matrix to store the result
        // The product matrix will
        // be of size row1 x col2
        int C[][] = new int[row1][col2];

        // Multiply the two matrices
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }

        // Print the result
        System.out.println("Resultado de la multiplicacion:");
        printMatrix(C, row1, col2);
    }

    //Funcion para suma
    static void sumMatrix(int filas,  int columnas, int matrizA[][], int matrizB[][]){

        // Creamos la matriz para almacenar la suma
        int[][] matrizSuma; // Declaración sin inicializar tamaño

        matrizSuma = new int[filas][columnas];

        // Recorremos las matrices y sumamos elemento a elemento
        for (int i = 0; i < matrizA.length; i++) { // Filas
            for (int j = 0; j < matrizA[i].length; j++) { // Columnas
                matrizSuma[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        // Imprimimos la matriz resultante
        System.out.println("Matriz Resultado de Suma:");
        printMatrix(matrizSuma,filas, columnas);

    }

    //Funcion para suma
    static void subMatrix(int filas, int columnas, int matrizA[][], int matrizB[][]){

        // Creamos la matriz para almacenar la resta
        int[][] matrizResta; // Declaración sin inicializar tamaño

        // Inicializar la matriz con un tamaño específico en tiempo de ejecución
        matrizResta = new int[filas][columnas];

        // Recorremos las matrices y restamos elemento a elemento
        for (int i = 0; i < matrizA.length; i++) { // Filas
            for (int j = 0; j < matrizA[i].length; j++) { // Columnas
                matrizResta[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }

        // Imprimimos la matriz resultante
        System.out.println("Matriz Resultado de Resta:");
        printMatrix(matrizResta,filas, columnas);

    }

    static int getRow(int matriz[][]){
        return matriz.length;
    }
    static int getColumn(int matriz[][]){
        return matriz[0].length;
    }
    public static void main(String[] args) {

        // Definimos dos matrices de 3x3
        int[][] matrizA = {
                {1, -2, 3},
                {4, 5, 6},
                {4, 5, 6},

        };

        int[][] matrizB = {
                {9, 8, 7},
                {6, 5, 4},
                {6, 5, 4}
        };

        //Obtenemos las dimensiones de las matrices
        int filasMatrizA = getRow(matrizA);
        int columnasMatrizA = getColumn(matrizA);

        //Obtenemos las dimensiones de las matrices
        int filasMatrizB = getRow(matrizB);
        int columnasMatrizB = getColumn(matrizB);

        // Imprimir matriz A
        System.out.println("Matriz A");
        printMatrix(matrizA, filasMatrizA, columnasMatrizA);

        // Imprimir matriz B
        System.out.println("Matriz B");
        printMatrix(matrizB, filasMatrizB, columnasMatrizB);

        if(filasMatrizA == filasMatrizB && columnasMatrizA == columnasMatrizB) {
            sumMatrix(filasMatrizA, filasMatrizA,matrizA,matrizB);
            subMatrix(filasMatrizA, filasMatrizA,matrizA,matrizB);
        }else{
            System.out.println("No se puede hacer suma y resta, las matrices tienen que ser cuadraticas");
        }

        //Para multiplicar
        multiplyMatrix(filasMatrizA, columnasMatrizA, matrizA, filasMatrizB,columnasMatrizB, matrizB);

    }
}
