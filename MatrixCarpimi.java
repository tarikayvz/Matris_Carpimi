public class MatrixCarpimi {
    public static void main(String[] args) {
        int[][] matrixA = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
        };

        int[][] matrixB = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
        };

        int[][] resultMatrix = new int[matrixA.length][matrixB[0].length];
        int numberThreads = matrixA.length;

        Thread[] threads = new Thread[numberThreads];
        for (int i = 0; i < numberThreads; i++) {
            threads[i] = new Thread(new MatrixCarpani(matrixA, matrixB, resultMatrix, i, i + 1));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       
        for (int[] sira : resultMatrix) {
            for (int value : sira) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

class MatrixCarpani implements Runnable {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final int[][] resultMatrix;
    private final int start;
    private final int end;

    public MatrixCarpani(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int start, int end) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < resultMatrix[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    synchronized(resultMatrix) {
                        resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        }
    }
}
