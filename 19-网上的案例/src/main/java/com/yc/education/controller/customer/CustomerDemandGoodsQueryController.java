package com.yc.education.controller.customer;


import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.customer.CustomerDemandGoods;
import com.yc.education.model.customer.CustomerDemandGoodsInfo;
import com.yc.education.service.customer.ICustomerDemandGoodsInfoService;
import com.yc.education.service.customer.ICustomerDemandGoodsService;
import com.yc.education.util.StageManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 客户需求商品查询
 * @Author: BlueSky
 * @Date: 2018/8/15 15:14
 */
@Controller
public class CustomerDemandGoodsQueryController extends BaseController implements Initializable {

    @Autowired
    ICustomerDemandGoodsService iCustomerDemandGoodsService;
    @Autowired
    ICustomerDemandGoodsInfoService iCustomerDemandGoodsInfoService;
    @FXML
    TextField create_no;
    @FXML
    DatePicker create_date;
    @FXML
    TextField product_no;
    @FXML
    TextField customer_no;
    @FXML
    ComboBox material;
    @FXML
    ComboBox production;
    @FXML
    TextField create_no_end;
    @FXML
    DatePicker create_date_end;
    @FXML
    TextField customer_no_end;
    @FXML
    TextField product_no_end;
    @FXML
    ComboBox manufacture;
    @FXML
    TableView table;
    @FXML
    TableColumn col_id;
    @FXML
    TableColumn col_product_no;
    @FXML
    TableColumn col_product_name;
    @FXML
    TableColumn col_need_num;
    @FXML
    TableColumn col_price;
    @FXML
    TableColumn col_unit;
    // 材质
    @FXML
    TableColumn col_texture;
    @FXML
    TableColumn col_manufacture;
    @FXML
    TableColumn col_material;
    @FXML
    TableColumn col_short_call;
    @FXML
    TableColumn col_create_no;
    @FXML
    TableColumn col_create_date;
    @FXML
    TableColumn col_remark;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private static SpringFxmlLoader loader = new SpringFxmlLoader();

    /**
     * 清空
     */
    @FXML
    private void clearVal(){
        create_no.clear();
        create_date.setValue(null);
        product_no.clear();
        customer_no.clear();
        material.getItems().clear();
        production.getItems().clear();
        create_no_end.clear();
        create_date_end.setValue(null);
        customer_no_end.clear();
        product_no_end.clear();
        manufacture.getItems().clear();
    }


    /**
     * 查询需求商品
     */
    @FXML
    public void queryDemandGoods(){
        String createNo = create_no.getText();
        String createNoEnd = create_no_end.getText();
        LocalDate createDate = create_date.getValue();
        LocalDate createDateEnd = create_date_end.getValue();
        String productNo = product_no.getText();
        String productNoEnd = product_no_end.getText();
        String customerNo = customer_no.getText();
        String customerNoEnd = customer_no_end.getText();
        String createDateStr = "",createDateEndStr ="";
        String materialStr,productionStr,manufactureStr;
        if(createNo==null){
            createNo = "";
        }
        if(createNo==null){
            createNo = "";
        }
        if(createDate != null){
            createDateStr = createDate.toString();
        }
        if(createDateEnd != null){
            createDateEndStr = createDateEnd.toString();
        }
        if(productNo==null){
            productNo = "";
        }
        if(productNoEnd==null){
            productNoEnd = "";
        }
        if(customerNo==null){
            customerNo = "";
        }
        if(customerNoEnd==null){
            customerNoEnd = "";
        }
        if(material.getSelectionModel().getSelectedItem() != null){
            materialStr = material.getSelectionModel().getSelectedItem().toString();
        }
        if(production.getSelectionModel().getSelectedItem() != null){
            productionStr = production.getSelectionModel().getSelectedItem().toString();
        }
        if(manufacture.getSelectionModel().getSelectedItem() != null){
            manufactureStr = manufacture.getSelectionModel().getSelectedItem().toString();
        }
        // 拼接条件
        List<CustomerDemandGoods> customerDemandGoodsList = iCustomerDemandGoodsService.listCustomerDemandGoodsByManyCondition(createNo,createNoEnd,createDateStr ,createDateEndStr ,customerNo ,customerNoEnd  );
        // 查询结果集合
        List<CustomerDemandGoodsInfo> customerDemandGoodsInfoList = new ArrayList<>();
        customerDemandGoodsList.forEach(p->{
            List<CustomerDemandGoodsInfo> info = iCustomerDemandGoodsInfoService.listCustomerDemandGoodsInfoByCustomerDemandId(p.getId());
            info.forEach(k->{
                k.setCreateNo(p.getCreateNo());
                k.setCreateDate(p.getCreateDate());
                k.setRemark(p.getRemark());
                k.setCustomerShortCall(p.getCustomerName());
                customerDemandGoodsInfoList.add(k);
            });
        });
        getCustomerDemandGoodsInfoList(customerDemandGoodsInfoList);

    }

    /**
     * 获取问题及意见
     */
    public void getCustomerDemandGoodsInfoList(List<CustomerDemandGoodsInfo> list){
        table.setItems(null);

        // 查询地址集合
        col_id.setCellValueFactory(new PropertyValueFactory<CustomerDemandGoodsInfo,Long>("id"));
        col_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));
        col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
        col_need_num.setCellValueFactory(new PropertyValueFactory("quantityDemand"));
        col_price.setCellValueFactory(new PropertyValueFactory("price"));
        col_unit.setCellValueFactory(new PropertyValueFactory(""));
        col_texture.setCellValueFactory(new PropertyValueFactory(""));
        col_manufacture.setCellValueFactory(new PropertyValueFactory("manufactureMethod"));
        col_material.setCellValueFactory(new PropertyValueFactory(""));
        col_short_call.setCellValueFactory(new PropertyValueFactory("customerShortCall"));
        col_create_no.setCellValueFactory(new PropertyValueFactory("createNo"));
        col_create_date.setCellValueFactory(new PropertyValueFactory<CustomerDemandGoodsInfo,Date>("createDate"));
        col_create_date.setCellFactory(column -> {
            TableCell<CustomerDemandGoodsInfo, Date> cell = new TableCell<CustomerDemandGoodsInfo, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        if(item != null)
                            this.setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        table.setItems(FXCollections.observableArrayList(list));

    }

    /**
     * @Author BlueSky
     * @Description //建档编号（客户需求商品查询）开始
     * @Date 20:22 2018/8/15
     * @Param []
     * @return void
     **/
    @FXML
    public void CustomerDemandGoodsQueryBen(){
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将第二个窗口保存到map中
        StageManager.STAGE.put("CustomerDemandGoodsQuery", stage);
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryController", this);
        //设置标识 查询开始的客户编号
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryControllerState", "ben");
        pane.getChildren().setAll(loader.load("/fxml/customer/customer_demand_goods_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @Author BlueSky
     * @Description //建档编号（客户需求商品查询）结束
     * @Date 20:22 2018/8/15
     * @Param []
     * @return void
     **/
    @FXML
    public void CustomerDemandGoodsQueryEnd(){
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将第二个窗口保存到map中
        StageManager.STAGE.put("CustomerDemandGoodsQuery", stage);
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryController", this);
        //设置标识 查询结束的客户编号
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryControllerState", "end");

        pane.getChildren().setAll(loader.load("/fxml/customer/customer_demand_goods_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @Author BlueSky
     * @Description //客户编号查询 开始
     * @Date 20:22 2018/8/15
     * @Param []
     * @return void
     **/
    @FXML
    public void CustomerQueryBen(){
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryController", this);
        //设置标识 查询开始的客户编号
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryControllerCustomerNo", "ben");
        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @Author BlueSky
     * @Description //客户编号查询 结束
     * @Date 20:22 2018/8/15
     * @Param []
     * @return void
     **/
    @FXML
    public void CustomerQueryEnd(){
        Stage stage = new Stage();
        Pane pane = new Pane();


        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryController", this);
        //设置标识 查询结束的客户编号
        StageManager.CONTROLLER.put("CustomerDemandGoodsQueryControllerCustomerNo", "end");

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void but_click(){
        Stage stage = new Stage();
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
