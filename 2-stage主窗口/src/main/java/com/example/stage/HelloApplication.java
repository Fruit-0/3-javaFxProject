package com.example.stage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("设置的窗口名称");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);

        //将implicitExit 属性设置为指定值。 如果此属性为真，JavaFX 运行时将在最后一个窗口关闭时隐式关闭； JavaFX 启动器将调用Application.stop方法并终止 JavaFX 应用程序线程。 如果此属性为 false，则即使在最后一个窗口关闭后，应用程序仍将继续正常运行，直到应用程序调用exit 。 默认值是true。
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event ->{
            //消费掉窗口关闭的按钮的事件，即使点击 ×  ，任然不做任何处理
            event.consume();

            //通过弹窗来认为控制程序的关闭与否
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("消息提示的抬头");
            alert.setHeaderText(null);
            alert.setContentText("是否退出程序？");

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isPresent()){
                if (buttonType.get() == ButtonType.OK){
                    Platform.exit();
                }
            }

        });
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}