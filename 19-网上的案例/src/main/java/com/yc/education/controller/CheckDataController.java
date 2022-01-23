package com.yc.education.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *@Description TODO 考勤
 *@Author QuZhangJing
 *@Date 16:04  2018-08-13
 *@Version 1.0
 */
@Controller
public class CheckDataController implements Initializable {


    @FXML
    private  TreeView treeView;

    @FXML
    private Pane contentPane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        contentPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        checkTreeValue();
    }

    /**
     * @Author BlueSky
     * @Description //TODO 考勤管理模块功能列表
     * @Date 11:05 2018/8/13
     * @Param []
     * @return void
     **/
    public  void checkTreeValue(){
        //根节点
        TreeItem<String> rootItem = new TreeItem<String> ();
        rootItem.setExpanded(true);

        // 考勤管理
        TreeItem<String> item4 = new TreeItem<>("考勤管理");
        item4.setExpanded(true);
        String[] dataArray4 = {"请假/加班/出差","原始考勤资料","考勤报告","考勤资料转结","薪资计算"};
        for (int i = 0; i < dataArray4.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray4[i]);
            item4.getChildren().add(item);
        }
        rootItem.getChildren().add(item4);



        // 参数设定
        TreeItem<String> item3 = new TreeItem<>("参数设定");
        item3.setExpanded(true);
        String[] dataArray3 = {"法定节假日设定","考勤班次设定","薪资设定"};
        for (int i = 0; i < dataArray3.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray3[i]);
            item3.getChildren().add(item);
        }
        rootItem.getChildren().add(item3);

        // 更节点隐藏
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }


}
