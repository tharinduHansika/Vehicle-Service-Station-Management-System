package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TestFormController {
    public VBox vbox;
    public AnchorPane mainPane;
    public JFXTextField txt;
    public Label iblTest;

    public void btnCallOnAction(MouseEvent mouseEvent) {
        System.out.println("click");
        vbox.setStyle("-fx-border-color: red;");
    }

    public void testOnAction(KeyEvent keyEvent) {
        txt.setStyle("-jfx-focus-color: red ");
        iblTest.setStyle("-fx-text-fill: red");
        //txt.setStyle("-fx-prompt-text-fill: red");
    }
}
