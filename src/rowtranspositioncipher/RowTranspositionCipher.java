/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rowtranspositioncipher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author WaterPlimpie
 */
public class RowTranspositionCipher extends Application {
    
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().
                    getResource("/rowtranspositioncipher/views/MainView.fxml"));
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getCause());
        }
        
        Scene scene = new Scene(root);
        stage.setTitle("Row Transposition Cipher");
        stage.setScene(scene);
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
