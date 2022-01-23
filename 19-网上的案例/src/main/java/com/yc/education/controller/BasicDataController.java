package com.yc.education.controller;

import com.yc.education.constants.SpringFxmlLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *@Description TODO 基本资料
 *@Author QuZhangJing
 *@Date 16:04  2018-08-13
 *@Version 1.0
 */
@Controller
public class BasicDataController implements Initializable {


    @FXML
    private  TreeView treeView;

    @FXML
    private Pane contentPane;

    private String url="";

    private static SpringFxmlLoader loader = new SpringFxmlLoader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                Node node = event.getPickResult().getIntersectedNode();

                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    String name = (String) ((TreeItem)treeView.getSelectionModel().getSelectedItem()).getValue();
                    changeClick(name);
                }
            }
        });

        baselineTreeValue();
    }

    /**
     * 单击树
     * @param value
     */
    public  void  changeClick(String value){

        if(value.equals("客户基本资料")){url= "/fxml/customer/customer_basic_info.fxml";}

        if(value.equals("供应商基本资料")){url="/fxml/basic/supplier_basic.fxml";}

        if(value.equals("公司基本资料")){url="/fxml/basic/company_basic.fxml";}

        if(value.equals("员工档案")){url="/fxml/basic/employee_basic.fxml";}

        if(value.equals("公司部门设置")){url="/fxml/basic/department_basic.fxml";}

        if(value.equals("仓库库位设置")){url="/fxml/basic/depot_basic.fxml";}

        if(value.equals("产品基本资料")){url="/fxml/basic/product_basic.fxml";}

        if(value.equals("产品价格设定")){url="/fxml/basic/pro_price_basic.fxml";}

        if(value.equals("资料编码维护")){url="/fxml/basic/coding_basic.fxml";}

        if(value.equals("业务区域设定")){url="/fxml/basic/region_basic.fxml";}




       if(!"".equals(url)){
           Pane pane = new Pane();

           pane.getChildren().setAll(loader.load(url));

           contentPane.getChildren().setAll(pane);
       }


    }


    /**
     * @Author BlueSky
     * @Description //TODO 基本资料模块功能列表
     * @Date 10:57 2018/8/13
     * @Param []
     * @return void
     **/
    public  void baselineTreeValue(){
        //根节点
        TreeItem<String> rootItem = new TreeItem<String> ();
        rootItem.setExpanded(true);

        // 基本资料
        TreeItem<String> item4 = new TreeItem<>("基本资料");
        item4.setExpanded(true);
        String[] dataArray4 = {"客户基本资料","供应商基本资料","运输商基本资料","公司基本资料","员工档案","公司部门设置","仓库库位设置","产品基本资料","产品价格设定","资料编码维护","业务区域设定"};
        for (int i = 0; i < dataArray4.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray4[i]);
            item4.getChildren().add(item);
        }
        rootItem.getChildren().add(item4);

        // 业务查询
        TreeItem<String> item2 = new TreeItem<>("业务查询");
        item2.setExpanded(true);
//        String[] dataArray2 = {"销项成本明细","库存成本查询","应付账款","销项未开票"};
//        for (int i = 0; i < dataArray2.length; i++) {
//            TreeItem<String> item = new TreeItem<> (dataArray2[i]);
//            item2.getChildren().add(item);
//        }
        rootItem.getChildren().add(item2);

         //相关报表
      TreeItem<String> item3 = new TreeItem<>("相关报表");
        item3.setExpanded(true);
//        String[] dataArray3 = {"应收账款账龄分析","应收款明细","应收款统计","未充发票明细"};
//        for (int i = 0; i < dataArray3.length; i++) {
//            TreeItem<String> item = new TreeItem<> (dataArray3[i]);
//            item3.getChildren().add(item);
//        }
        rootItem.getChildren().add(item3);



        // 更节点隐藏
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }



}
