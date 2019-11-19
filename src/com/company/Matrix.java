package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Matrix {
    private double[][] data;

    public Matrix(double[][] data){
        this.data = data;
    }

    public double[][] getData() {
        return data;
    }

    public int getHeight() {
        return data.length;
    }

    public int getWidth() {
        return data[0].length;
    }

    public Matrix multiply(Matrix m) {
        double[] currentRow;
        double[][] out = new double[this.getHeight()][m.getWidth()];

        if(this.getWidth() != m.getHeight()) return null;

        double entry;
        for(int i = 0; i < data.length; i++) {
            currentRow = data[i];
            for(int j = 0; j < m.getWidth(); j++) {
                entry = 0;
                for(int k = 0; k < currentRow.length; k++) {
                    entry += (currentRow[k] * m.getData()[k][j]);
                }
                out[i][j] = entry;
            }
        }
        return new Matrix(out);
    }

    public Matrix getEigenvector(int iterations) {
        double[][] initInput = new double[data.length][1];
        initInput[0][0] = 1;
        for(int i = 1; i < initInput.length; i++) {
            initInput[i][0] = 0;
        }
        Matrix m = new Matrix(initInput);

        for(int i = 0; i < iterations; i++) {
            m = this.multiply(m);
        }

        return m;
    }

    public Matrix sortEigenvector() {
        if(getWidth() != 1) {
            return null;
        }

        double[][] tempData = data.clone();

        Arrays.sort(tempData, new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                return Double.compare(o2[0], o1[0]);
            }
        });

        return new Matrix(tempData);
    }

    public void printMatrix() {
        for(double[] row : data) {
            for(double d : row) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
    }
}
