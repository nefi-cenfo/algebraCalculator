package calculator;

import java.util.*;

public class Main {

    // Function to print Matrix
    static void printMatrix(double matrix[][]) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%10.2f", value);
            }
            System.out.println();
        }
    }

    // Funcion para multiplicacion
    static void multiplyMatrix(int row1, int col1,
                               double A[][], int row2,
                               int col2, double B[][])
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
        double C[][] = new double[row1][col2];

        // Multiply the two matrices
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }

        // Print the result
        System.out.println("Resultado de la multiplicacion:");
        printMatrix(C);
    }

    //Funcion para suma
    static void sumMatrix(int filas,  int columnas, double matrizA[][], double matrizB[][]){

        // Creamos la matriz para almacenar la suma
        double[][] matrizSuma; // Declaración sin inicializar tamaño

        matrizSuma = new double[filas][columnas];

        // Recorremos las matrices y sumamos elemento a elemento
        for (int i = 0; i < matrizA.length; i++) { // Filas
            for (int j = 0; j < matrizA[i].length; j++) { // Columnas
                matrizSuma[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        // Imprimimos la matriz resultante
        System.out.println("Matriz Resultado de Suma:");
        printMatrix(matrizSuma);

    }

    //Funcion para suma
    static void subMatrix(int filas, int columnas, double matrizA[][], double matrizB[][]){

        // Creamos la matriz para almacenar la resta
        double[][] matrizResta; // Declaración sin inicializar tamaño

        // Inicializar la matriz con un tamaño específico en tiempo de ejecución
        matrizResta = new double[filas][columnas];

        // Recorremos las matrices y restamos elemento a elemento
        for (int i = 0; i < matrizA.length; i++) { // Filas
            for (int j = 0; j < matrizA[i].length; j++) { // Columnas
                matrizResta[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }

        // Imprimimos la matriz resultante
        System.out.println("Matriz Resultado de Resta:");
        printMatrix(matrizResta);

    }

    static double[][] scalate(double[][] matrix, double factor) {
        double[][] resultMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resultMatrix[i][j] = matrix[i][j] * factor;
            }
        }
        return resultMatrix;
    }

    static double getDiscriminante(double[][] matrix) {
        int matrixSize = matrix[0].length;

        if (matrixSize == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else if (matrixSize == 3) {
            return matrix[0][0] * matrix[1][1] * matrix[2][2] +
                    matrix[0][1] * matrix[1][2] * matrix[2][0] +
                    matrix[1][0] * matrix[2][1] * matrix[0][2] -
                    matrix[0][2] * matrix[1][1] * matrix[2][0] -
                    matrix[0][1] * matrix[1][0] * matrix[2][2] -
                    matrix[1][2] * matrix[2][1] * matrix[0][0];
        } else if (matrixSize > 3){
            double[][] upperMatrix = toUpperTriangular(matrix);
            double result = upperMatrix[0][0];
            for (int i = 1; i < upperMatrix.length; i++) {
                for (int k = 0; k < upperMatrix.length; k++) {
                    if (i == k) {
                        result *= upperMatrix[i][k];
                    }
                }
            }
            return result;
        }
        return 0;
    }

    public static double[][] toUpperTriangular(double[][] matrix) {
        int n = matrix.length;       // número de filas
        int m = matrix[0].length;    // número de columnas (incluye la columna de términos independientes)

        // Recorremos cada fila como pivote
        for (int i = 0; i < n; i++) {
            // Pivoteo parcial: si el elemento de la diagonal es casi cero, intercambiar con una fila inferior
            if (Math.abs(matrix[i][i]) < 1e-8) {
                for (int k = i + 1; k < n; k++) {
                    if (Math.abs(matrix[k][i]) > Math.abs(matrix[i][i])) {
                        double[] temp = matrix[i];
                        matrix[i] = matrix[k];
                        matrix[k] = temp;
                        break;
                    }
                }
            }

            // Evita dividir entre cero si el pivote es cero (la matriz podría ser singular)
            if (Math.abs(matrix[i][i]) < 1e-8) {
                continue; // O lanzar una excepción dependiendo de cómo quieras manejar el caso
            }

            // Eliminar la variable en las filas inferiores
            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                // Actualizar cada elemento de la fila j
                for (int k = i; k < m; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }
        return matrix;
    }

    public static double[][] invertMatrix(double[][] matrix) {
        int n = matrix.length;
        // Crear la matriz aumentada de tamaño n x 2n: la izquierda es la matriz original, la derecha la identidad.
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
            }
            for (int j = n; j < 2 * n; j++) {
                augmented[i][j] = (i == (j - n)) ? 1.0 : 0.0;
            }
        }

        // Aplicar eliminación de Gauss-Jordan para transformar la parte izquierda en la identidad.
        for (int i = 0; i < n; i++) {
            // Pivoteo parcial: encontrar la fila con el mayor valor absoluto en la columna i
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmented[k][i]) > Math.abs(augmented[maxRow][i])) {
                    maxRow = k;
                }
            }
            // Intercambiar la fila actual con la fila del pivote mayor
            double[] temp = augmented[i];
            augmented[i] = augmented[maxRow];
            augmented[maxRow] = temp;

            // Verificar que el pivote no sea cero (matriz singular)
            if (Math.abs(augmented[i][i]) < 1e-8) {
                throw new ArithmeticException("La matriz es singular y no se puede invertir.");
            }

            // Normalizar la fila i dividiendo por el pivote
            double pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }

            // Eliminar el elemento de la columna i en las otras filas
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }
        return augmented;
    }

    public static double[] calculateCramer(double[][] augmentedMatrix) {
        double mainDiscriminant = getDiscriminante(augmentedMatrix);
        int matrixSize = augmentedMatrix.length;
        double[] result = new double[matrixSize];

        // Se copia la matriz original sin terminos independientes
        for (int i = 0; i < matrixSize; i++) {
            double[][] tempMatrix = new double[matrixSize][matrixSize];

            for (int j = 0; j < matrixSize; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    tempMatrix[j][k] = augmentedMatrix[j][k];
                }
            }

            // Se cambia cada columna y se obtiene el discriminante
            for (int m = 0; m < matrixSize; m++) {
                tempMatrix[m][i] = augmentedMatrix[m][matrixSize];
            }
            result[i] = getDiscriminante(tempMatrix);
        }

        for (int n = 0; n < result.length; n++) {
            result[n] /= mainDiscriminant;
        }
        return result;
    }

    public static double[] calculateGaussElimination(double[][] augmentedMatrix) {
        int n = augmentedMatrix.length;

        // Aplicar eliminación de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            // Pivoteo parcial: intercambiar la fila actual con la fila con el mayor valor absoluto en la columna i
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                    maxRow = k;
                }
            }
            // Intercambiar filas
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;

            // Verificar que el pivote no sea cero
            if (Math.abs(augmentedMatrix[i][i]) < 1e-8) {
                throw new ArithmeticException("La matriz es singular o el sistema no tiene solución única.");
            }

            // Normalizar la fila del pivote para que el elemento [i][i] sea 1
            double pivot = augmentedMatrix[i][i];
            for (int j = 0; j < n + 1; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Eliminar el elemento i-ésimo de las demás filas
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j < n + 1; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // Al final, la matriz aumentada tendrá la forma [I | sol] y la solución está en la última columna.
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            solution[i] = augmentedMatrix[i][n];
        }
        return solution;
    }

    static int getRow(double matriz[][]){
        return matriz.length;
    }
    static int getColumn(double matriz[][]){
        return matriz[0].length;
    }
    public static void main(String[] args) {

        // Definimos dos matrices de 3x3
        double[][] matrizA = {
                {1, -2, 3},
                {4, 5, 6},
                {4, 5, 6},

        };

        double[][] matrizB = {
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
        printMatrix(matrizA);
        System.out.println("\n");

        // Imprimir matriz B
        System.out.println("Matriz B");
        printMatrix(matrizB);
        System.out.println("\n");

        if(filasMatrizA == filasMatrizB && columnasMatrizA == columnasMatrizB) {
            sumMatrix(filasMatrizA, filasMatrizA,matrizA,matrizB);
            System.out.println("\n");
            subMatrix(filasMatrizA, filasMatrizA,matrizA,matrizB);
            System.out.println("\n");
        }else{
            System.out.println("No se puede hacer suma y resta, las matrices tienen que ser cuadraticas");
            System.out.println("\n");
        }

        //Para multiplicar
        multiplyMatrix(filasMatrizA, columnasMatrizA, matrizA, filasMatrizB,columnasMatrizB, matrizB);
        System.out.println("\n");

        //Escalar
        double[][] scalateMatrix = {
                {1, -2, 3},
                {4, 8, 9},
                {2, 5, 6},
        };

        System.out.println("Escalar por 2");
        System.out.println("Matriz original");
        printMatrix(scalateMatrix);

        System.out.println("Resultado");
        printMatrix(scalate(scalateMatrix, 2));
        System.out.println("\n");

        double[][] cramerMatrix = {
                {-1, 0, 2, 2, 1},
                {2, 0, -3, -3, 0},
                {-1, 0, -3, 2, 0},
                {-2, -1, 2, 1, 0}
        };

        System.out.println("Cramer");
        System.out.println("Matriz original");
        printMatrix(cramerMatrix);

        System.out.println("Resultado");
        double[] resultCramer = calculateCramer(cramerMatrix);
        for (int i = 0; i < resultCramer.length; i++) {
            System.out.printf("x%d = %10.2f\n", i + 1, resultCramer[i]);
        }
        System.out.println("\n");

        double[][] discriminanteM = {
                {1, -2, 3},
                {-3, 4, -5},
                {-1, -7, 8}
        };

        System.out.println("Determinante");
        System.out.println("Matriz original");
        printMatrix(discriminanteM);

        System.out.println("Resultado");
        System.out.println("discriminante: " + getDiscriminante(discriminanteM));
        System.out.println("\n");

        double[][] gaussMatrix = {
                {-7,  4,  5,  17},
                { 7, -5, -6, -27},
                {-6,  8,  9,  61}
        };

        System.out.println("Eliminacion Gauss");
        System.out.println("Matriz original");
        printMatrix(gaussMatrix);
        double[] resultGauss = calculateGaussElimination(gaussMatrix);

        System.out.println("Resultado");
        for (int i = 0; i < resultGauss.length; i++) {
            System.out.printf("x%d = %10.2f\n", i + 1, resultGauss[i]);
        }
        System.out.println("\n");
//        System.out.println(result2[2][0]);
        double[][] _invertMatrix = {
                {-7,  4,  5},
                { 7, -5, -6},
                {-6,  8,  9}
        };
        System.out.println("Matriz inversa");
        System.out.println("Matriz original");
        printMatrix(_invertMatrix);

        System.out.println("Resultado");
        printMatrix(invertMatrix(_invertMatrix));
        System.out.println("\n");

        double[][] upperMatrix = {
                {-7,  4,  5,  17},
                { 7, -5, -6, -27},
                {-6,  8,  9,  61}
        };

        System.out.println("Matriz triangular");
        System.out.println("Matriz original");
        printMatrix(upperMatrix);

        System.out.println("Resultado");
        printMatrix(toUpperTriangular(upperMatrix));
        System.out.println("\n");
    }
}
