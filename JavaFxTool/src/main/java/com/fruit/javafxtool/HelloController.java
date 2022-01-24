package com.fruit.javafxtool;

import com.fruit.utils.FruitJavaFxUtils;
import javafx.scene.control.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class HelloController {
    private static int excelNumber = 0;

    @FXML
    private VBox rootVBox;//这是界面A的根布局的id

    @FXML
    private Label welcomeText;

    @FXML
    private Button excelChoose_button;

    @FXML
    private TextField excel_text;

    @FXML
    private Button txtChoose_button;

    @FXML
    private TextField txt_text;








    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }



    @FXML
    void onExcelButtonClick(MouseEvent MouseEvent) throws Exception {
        try {
            //todo 具体的操作-使用javaFx的方式选择文件
            int a = 1/0;
        } catch (Exception ex) {
            FruitJavaFxUtils.alertFunction(Alert.AlertType.ERROR,"普通标题","异常发生了","异常原因",true,null,null);
        }


    }

    @FXML
    void onTxtButtonClick(MouseEvent event) {

    }


    @FXML
    void onDealExcelButtonClick(MouseEvent MouseEvent) {
        System.out.println("111");
    }







}