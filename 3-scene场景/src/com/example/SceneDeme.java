package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @version 1.0.0
 * @项目名称：
 * @类名称：
 * @类描述：
 * @功能描述：
 * @author：FRUIT
 * @创建时间：2022/1/22 20:18
 * @修改备注
 * @修改记录： 修改时间 修改人员 修改原因
 * --------------------------------------------------
 */
public class SceneDeme extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label label1 = new Label();
        label1.setText("标签1，跟scane1绑定");
        label1.setLayoutX(200);
        label1.setLayoutY(200);

        Button button1 = new Button();
        button1.setText("切换scene1");
        button1.setLayoutX(200);
        button1.setLayoutY(200);

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.getChildren().addAll(button1,label1);
        Scene scene1 = new Scene(anchorPane1,500,500);


        Label label2 = new Label();
        label2.setText("标签2，跟scane2绑定");
        label2.setLayoutX(200);
        label2.setLayoutY(200);

        Button button2 = new Button();
        button2.setText("切换scene1");
        button2.setLayoutX(200);
        button2.setLayoutY(200);

        AnchorPane anchorPane2 = new AnchorPane();
        //关键
        anchorPane2.getChildren().addAll(button2,label2);
        Scene scene2 = new Scene(anchorPane2,500,500);




        button2.setOnAction(event -> {
            stage.setScene(scene1);
        });
        button1.setOnAction(event -> {
            stage.setScene(scene2);
        });

        stage.getIcons().add(new Image("image/logo.png"));
        stage.setScene(scene1);
        stage.show();




    }
}
