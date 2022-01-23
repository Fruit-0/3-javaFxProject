package com.yc.education.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 *@Description TODO
 *@Author QuZhangJing
 *@Date 13:02  2018-08-07
 *@Version 1.0
 */
public class Main extends Application {

    private static Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Starting application");
        Platform.setImplicitExit(true);
        ScreenManager screens = new ScreenManager();
        screens.setPrimaryStage(stage);
        screens.showStage();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
