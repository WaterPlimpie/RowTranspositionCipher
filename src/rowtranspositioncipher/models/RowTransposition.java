/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rowtranspositioncipher.models;

import java.util.Arrays;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author WaterPlimpie
 */
public class RowTransposition {
    
    private final StringProperty cipherTxt = new SimpleStringProperty();
    private final StringProperty plainTxt = new SimpleStringProperty();
    private final StringProperty originalMatrix = new SimpleStringProperty();
    private final StringProperty transposedMatrix = new SimpleStringProperty();
    private final StringProperty key = new SimpleStringProperty();
    private final IntegerProperty columns = new SimpleIntegerProperty();

    public String getCipherTxt() {
        return cipherTxt.get();
    }

    public void setCipherTxt(String value) {
        cipherTxt.set(value);
    }

    public StringProperty cipherTxtProperty() {
        return cipherTxt;
    }

    public String getPlainTxt() {
        return plainTxt.get();
    }

    public void setPlainTxt(String value) {
        plainTxt.set(value);
    }

    public StringProperty plainTxtProperty() {
        return plainTxt;
    }

    public String getOriginalMatrix() {
        return originalMatrix.get();
    }

    public void setOriginalMatrix(String value) {
        originalMatrix.set(value);
    }

    public StringProperty originalMatrixProperty() {
        return originalMatrix;
    }

    public String getTransposedMatrix() {
        return transposedMatrix.get();
    }

    public void setTransposedMatrix(String value) {
        transposedMatrix.set(value);
    }

    public StringProperty transposedMatrixProperty() {
        return transposedMatrix;
    }

    public String getKey() {
        return key.get();
    }

    public void setKey(String value) {
        key.set(value);
    }

    public StringProperty keyProperty() {
        return key;
    }

    public int getColumns() {
        return columns.get();
    }

    public void setColumns(int value) {
        columns.set(value);
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }
    
    
    
    public  String encrypt() {
        
        int cols = columns.get();
        
        String temp = "";
        String message = plainTxt.get();
         //get rid of all whitespaces to put in matrix
        message = message.replaceAll("\\s+", "");
        int len = message.length();
        
        int rows = len / cols; //determine the number of rows in the matrix
        rows += ((len % cols == 0) ? 0 : 1);
        //System.out.println("Rows = " + rows);
        
        //original matrix before transposition
        char matrix[][] = new char[rows][cols]; 
        
        int index = 0; //index for the message
        
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (index < len)
                    matrix[i][j] = message.charAt(index++);
                else
                    matrix[i][j] = '-';
            }
        }
        String orig = "";
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
               orig += matrix[i][j];
               orig += '\t';
            }
            orig += '\n';
        }
        originalMatrix.set(orig);
        
//        for (int i = 0; i < rows; ++i) {
//            for (int j = 0; j < cols; ++j) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println("");
//        }
        
        char newMatrix[][] = new char[rows][cols]; //transposed matrix
        
        //copy columns from original matrix to new matrix according to key index
        
        String key = this.key.get();
        
        for (int i = 0; i < cols; ++i) {
            int k = (int) (key.charAt(i) - '0' - 1); 
            for (int r = 0; r < rows; ++r) {
                newMatrix[r][i] = matrix[r][k];
            }
        }
        
        String trans = "";
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
               trans += newMatrix[i][j];
               trans += '\t';
            }
            trans += '\n';
        }
        transposedMatrix.set(trans);
//        for (int i = 0; i < rows; ++i) {
//            for (int j = 0; j < cols; ++j) {
//                System.out.print(newMatrix[i][j] + " ");
//            }
//            System.out.println("");
//        }
        
        //construct the encrypted text
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                temp += newMatrix[i][j];
            }
        }
        
        return temp;
    }
    
    public String decrypt() {
        
        int cols = columns.get();
        String key = this.key.get();
        String message = cipherTxt.get();
        String temp = "";
        
        int len = message.length();
        int rows = len / cols; //determine the number of rows in the matrix
        rows += ((len % cols == 0) ? 0 : 1);
        
        //original matrix before transposition
        char matrix[][] = new char[rows][cols]; 
        
        int index = 0; //index for the message
        
        //construct the transposed matrix
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                matrix[i][j] = message.charAt(index++);
            }
        }
        
        String trans = "";
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
               trans += matrix[i][j];
               trans += '\t';
            }
            trans += '\n';
        }
        transposedMatrix.set(trans);
        
        //now use the key to get the original matrix
        char newMatrix[][] = new char[rows][cols];
        
        for (int i = 0; i < cols; ++i) {
            int k = (int) (key.charAt(i) - '0' - 1); 
            for (int r = 0; r < rows; ++r) {
                newMatrix[r][k] = matrix[r][i];
            }
        }
        
        String orig = "";
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
               orig += newMatrix[i][j];
               orig += '\t';
            }
            orig += '\n';
        }
        originalMatrix.set(orig);
        
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                temp += newMatrix[i][j];
            }
        }
        
        return temp;
        
    }
    
    public static boolean isValidKey(String key, int cols) {
        if (key.length() != cols)
            return false;
        char[] sorted = key.toCharArray();
        Arrays.sort(sorted);
        String temp = new String(sorted);
        for (int i = 1; i <= cols; ++i) {
            if (i != (int)(temp.charAt(i - 1) - '0'))
                return false;
        }
        
        return true;
    }
    
}
