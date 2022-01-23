package com.yc.education.controller;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.util.StageManager;
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
 *@Description TODO 库存
 *@Author QuZhangJing
 *@Date 16:04  2018-08-13
 *@Version 1.0
 */
@Controller
public class InventoryDataController extends BaseController implements Initializable {


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
        inventoryTreeValue();
    }



    /**
     * 单击树
     * @param value
     */
    public  void  changeClick(String value){


        if(value.equals("采购入库单")){url= "/fxml/stock/purchase_stock.fxml";}

        if(value.equals("销货出库单")){url="/fxml/stock/sale_outbound_order.fxml";}

        if(value.equals("库存异动作业")){url="/fxml/stock/stock_change.fxml";}

        if(value.equals("盘库作业")){url="/fxml/stock/inventory_operation.fxml";}

        if(value.equals("快递寄件")){url="/fxml/stock/express_mail.fxml";}

        if(value.equals("快递收件")){url="/fxml/stock/express_collect.fxml";}

        if(value.equals("包装打印")){url="/fxml/stock/printing.fxml";}

        if(value.equals("异动查询")){url="/fxml/stock/change_query.fxml";}

        if(value.equals("盘点查询")){url="/fxml/stock/inventory_query.fxml";}

        if(value.equals("销货未出库")){url="/fxml/sale/sale_storage_not_out.fxml";}

        if(value.equals("销退未入库")){url="/fxml/sale/sale_return_not_enter_storage.fxml";}

        if(value.equals("采购未入库")){url="/fxml/purchase/nostorage.fxml";}

        if(value.equals("产品库存查询")){url="/fxml/purchase/product_stock.fxml";}



        if(value.equals("库存异动汇总")){url="/fxml/stock/stock_change_total.fxml";}

        if(value.equals("库存异动查询")){url="/fxml/stock/stock_change_query.fxml";}

        if(value.equals("进销货统计")){url="/fxml/stock/purchase_sale_total.fxml";}



        if(value.equals("供应商基本资料")){url="/fxml/basic/supplier_basic.fxml";}

        if(value.equals("产品基本资料")){url="/fxml/basic/product_basic.fxml";}

        if(value.equals("仓库库位设置")){url="/fxml/basic/depot_basic.fxml";}



        if(value.equals("资料设定")){showDataSetting();}

        if(value.equals("功能参数设定")){url="/fxml/basic/region_basic.fxml";}


        if(!"".equals(url)){
            Pane pane = new Pane();

            pane.getChildren().setAll(loader.load(url));

            contentPane.getChildren().setAll(pane);

            StageManager.clear();

        }


    }





    /**
     * @Author BlueSky
     * @Description //TODO 库存模块功能列表
     * @Date 10:31 2018/8/13
     * @Param []
     * @return void
     **/
    public  void inventoryTreeValue(){
        //根节点
        TreeItem<String> rootItem = new TreeItem<String> ();
        rootItem.setExpanded(true);

        // 业务单据
        TreeItem<String> item1 = new TreeItem<String> ("业务单据");
        item1.setExpanded(true);
        String[] dataArray1 = {"采购入库单","销货出库单","库存异动作业","盘库作业","快递寄件","快递收件","包装打印"};
        for (int i = 0; i < dataArray1.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray1[i]);
            item1.getChildren().add(item);
        }
        rootItem.getChildren().add(item1);

        // 业务查询
        TreeItem<String> item2 = new TreeItem<>("业务查询");
        item2.setExpanded(true);
        String[] dataArray2 = {"异动查询","盘点查询","销货未出库","销退未入库","采购未入库","产品库存查询"};
        for (int i = 0; i < dataArray2.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray2[i]);
            item2.getChildren().add(item);
        }
        rootItem.getChildren().add(item2);

        // 相关报表
        TreeItem<String> item3 = new TreeItem<>("相关报表");
        item3.setExpanded(true);
        String[] dataArray3 = {"库存异动汇总","库存异动查询","进销货物统计"};
        for (int i = 0; i < dataArray3.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray3[i]);
            item3.getChildren().add(item);
        }
        rootItem.getChildren().add(item3);

        // 基本资料
        TreeItem<String> item4 = new TreeItem<>("基本资料");
        item4.setExpanded(true);
        String[] dataArray4 = {"产品基本资料","供应商基本资料","仓库库位设置"};
        for (int i = 0; i < dataArray4.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray4[i]);
            item4.getChildren().add(item);
        }
        rootItem.getChildren().add(item4);

        // 参数设定
        TreeItem<String> item5 = new TreeItem<>("参数设定");
        item5.setExpanded(true);
        String[] dataArray5 = {"资料设定","功能参数设定"};
        for (int i = 0; i < dataArray5.length; i++) {
            TreeItem<String> item = new TreeItem<> (dataArray5[i]);
            item5.getChildren().add(item);
        }
        rootItem.getChildren().add(item5);

        // 更节点隐藏
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }


}
