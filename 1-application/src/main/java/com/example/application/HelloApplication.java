package com.example.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author FRUIT
 */
public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("初始化的时候执行");
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("打开窗口的时候执行");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("关闭庄口之后执行");
    }


}
