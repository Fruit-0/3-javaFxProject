package com.yc.education.controller;

import com.yc.education.application.NetworkConfiguration;
import com.yc.education.service.UsersService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *@Description TODO
 *@Author QuZhangJing
 *@Date 13:02  2018-08-07
 *@Version 1.0
 */
@Controller
public class LoginController implements Initializable {


    @Autowired
    private UsersService usersService;


    @FXML private TextField name;

    @FXML private PasswordField pwd;

    @FXML private Button closeButton;

    @FXML private ComboBox networkComboBox;


    /**
     * 登录按钮
     */
    public void login(){


        closeWin();

        NetworkConfiguration.homeShow();
        System.err.println("*************************************************************************************************");
        System.err.println("*********************************************欢迎登录三禄ERP*************************************");
        System.err.println("*************************************************************************************************");
        System.err.println("*********************************************三禄人民欢迎您**************************************");
        System.err.println("***********************************************Jzdsh欢迎您****************************************");
        System.err.println("*************************************************************************************************");
    }

    //退出程序
    public void closeButton(){
        Stage stage=(Stage)closeButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }


    //关闭当前窗体
    public void closeWin(){
        System.err.println("关闭当前窗体");
        Stage stage=(Stage)closeButton.getScene().getWindow();
        stage.close();
    }


    //网络配置
    public void networkConfiguration(){

        //加载配置窗体
        NetworkConfiguration.display();

     /*   ObservableList<String> options = FXCollections.observableArrayList("Option 1","Option 2","Option 3");

        networkComboBox.setItems(options);*/

        //初始化 协议下拉默认值
        networkComboBox.getItems().addAll(
                "https",
                "http",
                "ftp"
        );
    }


    /**
     * 网络配置 ---确定
     */
    public void relyConfig(){

        System.err.println("网络配置 ---确定");

        closeWin();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}
