package com.yc.education.controller;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.util.DragUtil;
import com.yc.education.util.DrawUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @ClassName HomeController
 * @Description TODO home  首页
 * @Author QuZhangJing
 * @Date 2018-08-07 13:48
 * @Version 1.0
 */
@Controller
public class HomeController extends BaseController implements Initializable {

    @FXML private Pane anPane;

    @FXML private Pane buttomPane;

    @FXML private MenuBar menuBarTop;

    @FXML private Pane homePane;

    @FXML private VBox btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;

    private VBox attrVbox ;

    private  String url="/fxml/sale_data.fxml"; //默认fxml

    private final String defaultColor="-fx-background-color:#EFF3F7;";  //默认按钮颜色

    private final String checkedColor=" -fx-background-color:#169252;"; //选中颜色

    private final String checkedFontColor=" -fx-text-fill:#FFF;"; //选中字体颜色

    private final String defaultFontColor=" -fx-text-fill: #8CA0B3;"; //默认字体颜色



    private static SpringFxmlLoader loader = new SpringFxmlLoader();


    /**
     * 打开资料设定
     */
    public void openDataSetting(){
        showDataSetting();
    }



    public void removeClass(){

        /**
         * 恢复默认样式
         */
        btn1.setStyle(defaultColor);
        btn2.setStyle(defaultColor);
        btn3.setStyle(defaultColor);
        btn4.setStyle(defaultColor);
        btn5.setStyle(defaultColor);
        btn6.setStyle(defaultColor);
        btn7.setStyle(defaultColor);
        btn8.setStyle(defaultColor);

        btn1.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/xs.png');");
        btn1.getChildren().get(1).setStyle(defaultFontColor);
        btn2.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/cg.png');");
        btn2.getChildren().get(1).setStyle(defaultFontColor);
        btn3.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/kc.png');");
        btn3.getChildren().get(1).setStyle(defaultFontColor);
        btn4.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/zk.png');");
        btn4.getChildren().get(1).setStyle(defaultFontColor);
        btn5.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/zl.png');");
        btn5.getChildren().get(1).setStyle(defaultFontColor);
        btn6.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/kh.png');");
        btn6.getChildren().get(1).setStyle(defaultFontColor);
        btn7.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/kq.png');");
        btn7.getChildren().get(1).setStyle(defaultFontColor);
        btn8.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/bb.png');");
        btn8.getChildren().get(1).setStyle(defaultFontColor);
    }

    public  void checkedClass(Node node,int type){
        attrVbox = (VBox)node;

        if(type == 1){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/xsed.png');");
        }else if(type == 2){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/cged.png');");
        }else if(type == 3){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/kced.png');");
        }else if(type == 4){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/zked.png');");
        }else if(type == 5){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/zled.png');");
        }else if(type == 6){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/khed.png');");
        }else if(type == 7){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/kqed.png');");
        }else if(type == 8){
            attrVbox.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/bbed.png');");
        }

        attrVbox.getChildren().get(1).setStyle(checkedFontColor);
    }


    public void btn1Click(Event event){

        Node node =(Node)event.getSource();
        Integer type=Integer.parseInt(node.getUserData().toString());

        removeClass();

        /**
         * 添加选中样式
         */
        node.setStyle(checkedColor);

        checkedClass(node,type);

        //销售
        if(type==1){ url="/fxml/sale_data.fxml"; }
        //采购
        if(type==2){url="/fxml/purchase_data.fxml";}
        //库存
        if(type==3){url="/fxml/inventory_data.fxml";}
        //账款
        if(type==4){url="/fxml/account_data.fxml";}
        //基本资料
        if(type==5){url="/fxml/basic_data.fxml";}
        //客户关系
        if(type==6){url="/fxml/customer_relation_data.fxml";}
        //考勤管理
        if(type==7){url="/fxml/check_data.fxml";}
        //统计汇总
        if(type==8){url="/fxml/baseline_data.fxml";}

        Pane pane = new Pane();

        pane.getChildren().setAll(loader.load(url));

//        node.getScene().getStylesheets().add(getClass().getResource("/css/home.css").toExternalForm());

        homePane.getChildren().setAll(pane);

    }


    public void changeWinSize(){
        System.err.println("====");

    }



        /**
         * home 页面初始化
         * @param location
         * @param resources baseline_data
         */
        @Override
        public void initialize(URL location, ResourceBundle resources) {


            /**
             * 默认选中
             */
            btn1.setStyle(checkedColor);
            btn1.getChildren().get(0).setStyle(" -fx-background-image: url('/images/basic/xsed.png');");
            btn1.getChildren().get(1).setStyle(checkedFontColor);
            Pane pane = new Pane();
            pane.getChildren().setAll(loader.load(url));
            homePane.getChildren().setAll(pane);


    }
}
