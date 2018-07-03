/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rowtranspositioncipher.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import rowtranspositioncipher.models.RowTransposition;
import static rowtranspositioncipher.models.RowTransposition.isValidKey;

/**
 * FXML Controller class
 *
 * @author WaterPlimpie
 */
public class MainViewController implements Initializable {
    @FXML
    private TextArea plainTxt;
    @FXML
    private Button encryptBtn;
    @FXML
    private TextArea transposedMatrix;
    @FXML
    private TextArea cipherTxt;
    @FXML
    private Button decryptBtn;
    @FXML
    private TextArea originalMatrix;
    @FXML
    private TextField keyTxt;
    @FXML
    private Spinner<Integer> colsSpnr;
    @FXML
    private Button validateKeyBtn;
    @FXML
    private Label validityLbl;
    
    
    private final BooleanProperty valid = new SimpleBooleanProperty(true);

    public boolean isValid() {
        return valid.get();
    }

    public void setValid(boolean value) {
        valid.set(value);
    }

    public BooleanProperty validProperty() {
        return valid;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        RowTransposition rt = new RowTransposition();
        //encryptBtn.setDisable(true);
        //decryptBtn.setDisable(true);
        
        decryptBtn.disableProperty().bindBidirectional(valid);
        encryptBtn.disableProperty().bindBidirectional(valid);
        
        validateKeyBtn.setOnAction(e -> {
            if (isValidKey(keyTxt.getText(), colsSpnr.getValue())) {
                rt.setKey(keyTxt.getText());
                rt.setColumns(colsSpnr.getValue());
                valid.set(false);
                //encryptBtn.setDisable(false);
                //decryptBtn.setDisable(false);
            } else {
                rt.setKey(null);
                rt.setColumns(0);
                valid.set(true);
            }
        });
        encryptBtn.setOnAction(e -> {
            rt.setPlainTxt(plainTxt.getText());
            cipherTxt.setText("");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            cipherTxt.setText(rt.encrypt());
            transposedMatrix.setText(rt.getTransposedMatrix());     
        });
        
        decryptBtn.setOnAction(e -> {
            rt.setCipherTxt(cipherTxt.getText());
            plainTxt.setText("");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            plainTxt.setText(rt.decrypt());
            originalMatrix.setText(rt.getOriginalMatrix());
        });
        
    }    
    
}
