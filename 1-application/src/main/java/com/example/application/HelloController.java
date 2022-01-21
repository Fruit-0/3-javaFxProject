package com.example.application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author FRUIT
 */
public class HelloController {
  @FXML private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");
  }
}
