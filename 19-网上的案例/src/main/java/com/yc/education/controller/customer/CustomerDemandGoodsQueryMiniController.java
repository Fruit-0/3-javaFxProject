package com.yc.education.controller.customer;

import com.yc.education.controller.sale.CustomerDemandGoodsWhereController;
import com.yc.education.controller.sale.SaleDiscountQueryWhereController;
import com.yc.education.model.DataSetting;
import com.yc.education.model.customer.*;
import com.yc.education.service.DataSettingService;
import com.yc.education.service.customer.*;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 客户需求商品查询
 * @Author: BlueSky
 * @Date: 2018/8/15 15:14
 */
@Controller
public class CustomerDemandGoodsQueryMiniController implements Initializable {

    @Autowired
    ICustomerDemandGoodsService iCustomerDemandGoodsService;
    @Autowired
    ICustomerDataMaintainService iCustomerDataMaintainService;
    @Autowired
    ICustomerDetailInfoService iCustomerDetailInfoService;
    @Autowired
    ICustomerService iCustomerService;
    @Autowired
    DataSettingService iDataSettingService;

    @FXML
    TableView customer_demand_goods_table;
    @FXML
    TableColumn id;
    @FXML
    TableColumn create_no;
    @FXML
    TableColumn create_date;
    @FXML
    TableColumn customer_no;
    @FXML
    TableColumn customer_type;
    @FXML
    TableColumn customer_name;
    @FXML
    TableColumn remark;
    // 客户需求商品id
    public static String  customerDemandGoodsId = "";
    // 客户资料维护
    public static String  maintainId = "";
    public static LocalDate maintainCreateDate ;
    public static String  maintainCustomerNo = "";
    public static String  maintainCustomerName = "";
    public static String  maintainCustomerNoRemark = "";
    public static String  maintainBuildNo = "";
    // 客户需求商品查询
    public static String createNo="" ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 客户需求商品
        CustomerDemandGoodsController demand = (CustomerDemandGoodsController) StageManager.CONTROLLER.get("CustomerDemandGoodsController");
        // 客户资料维护
        CustomerDataMaintenanceController maintain = (CustomerDataMaintenanceController) StageManager.CONTROLLER.get("CustomerDataMaintenanceController");
        // 客户资料维护
        CustomerDemandGoodsQueryController demandQuery = (CustomerDemandGoodsQueryController) StageManager.CONTROLLER.get("CustomerDemandGoodsQueryController");
        // 客户需求商品查询 开始
        CustomerDemandGoodsWhereController demandBen = (CustomerDemandGoodsWhereController) StageManager.CONTROLLER.get("CustomerDemandGoodsWhereController_discount_ben");
        // 客户需求商品查询 结束
        CustomerDemandGoodsWhereController demandEnd = (CustomerDemandGoodsWhereController) StageManager.CONTROLLER.get("CustomerDemandGoodsWhereController_discount_end");

        if(demand != null || demandQuery != null || demandBen != null || demandEnd != null){
            customerDemandMethod();
        }else if(maintain != null){
            dataMaintainMethod();
        }

        customer_demand_goods_table.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });
    }

    /**
     * 客户资料维护
     */
    private void dataMaintainMethod(){
        List<CustomerDataMaintain> list = iCustomerDataMaintainService.listCustomerDataMaintain("",1,Integer.MAX_VALUE);
        list.forEach(p->{
            Customer customer = iCustomerService.selectByKey(p.getCustomerId());
            if(customer != null){
                p.setTempCustomerNo(customer.getCustomerCode());
                p.setTempCustomerName(customer.getName());
            }
            CustomerDetailInfo info = iCustomerDetailInfoService.getCustomerDetailInfoByCustomerId(p.getCustomerId());
            if(info.getCustomerCategory() != null && info.getCustomerCategory()!= 0L){
                DataSetting dataSetting = iDataSettingService.selectByKey(info.getCustomerCategory());
                if(dataSetting.getId().toString().equals(info.getCustomerCategory().toString())){
                    p.setTempCustomerCategory(dataSetting.getTitle());
                    return;
                }
            }
            p.setTempCustomerRemark(info.getAccountRemark());
        });

        // 查询地址集合
        final ObservableList<CustomerDataMaintain> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory<Customer,Long>("id"));
        create_no.setCellValueFactory(new PropertyValueFactory("createNo"));
        customer_no.setCellValueFactory(new PropertyValueFactory("tempCustomerNo"));
        customer_type.setCellValueFactory(new PropertyValueFactory<Customer,Long>("tempCustomerCategory"));
        customer_name.setCellValueFactory(new PropertyValueFactory("tempCustomerName"));
        remark.setCellValueFactory(new PropertyValueFactory("tempCustomerRemark"));
        create_date.setCellValueFactory(new PropertyValueFactory<Customer,Date>("createDate"));
        create_date.setCellFactory(column -> {
            TableCell<Customer, Date> cell = new TableCell<Customer, Date>() {
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

        customer_demand_goods_table.setItems(data);
        // 选择行 保存数据
        customer_demand_goods_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDataMaintain>() {
            @Override
            public void changed(ObservableValue<? extends CustomerDataMaintain> observable, CustomerDataMaintain oldValue, CustomerDataMaintain newValue) {
                if(!"".equals(newValue.getCustomerId().toString())){
                    CustomerDemandGoodsQueryMiniController.maintainId = newValue.getId().toString();
                    CustomerDemandGoodsQueryMiniController.maintainCustomerName = newValue.getTempCustomerName();
                    CustomerDemandGoodsQueryMiniController.maintainCustomerNo = newValue.getTempCustomerNo();
                    CustomerDemandGoodsQueryMiniController.maintainCustomerNoRemark = newValue.getTempCustomerRemark();
                    CustomerDemandGoodsQueryMiniController.maintainBuildNo = newValue.getCreateNo();
                    ZoneId zoneId = ZoneId.systemDefault();
                    CustomerDemandGoodsQueryMiniController.maintainCreateDate = newValue.getCreateDate().toInstant().atZone(zoneId).toLocalDate();
                }
            }
        });
    }

    /**
     * 客户需求商品
     */
    private void customerDemandMethod(){
        List<CustomerDemandGoods> list = iCustomerDemandGoodsService.listCustomerDemandGoods("",1,Integer.MAX_VALUE);
        List<DataSetting> dataSettingList = iDataSettingService.findDataSetting(10L);
        list.forEach(p->{
            if(dataSettingList != null){
                p.setCustomerTypeStr(dataSettingList.get(p.getCustomerType()).getTitle());
            }
        });
        // 查询地址集合
        final ObservableList<CustomerDemandGoods> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory<Customer,Long>("id"));
        create_no.setCellValueFactory(new PropertyValueFactory("createNo"));
        customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        customer_type.setCellValueFactory(new PropertyValueFactory<Customer,Long>("customerTypeStr"));
        customer_name.setCellValueFactory(new PropertyValueFactory("customerName"));
        remark.setCellValueFactory(new PropertyValueFactory("remark"));
        create_date.setCellValueFactory(new PropertyValueFactory<Customer,Date>("createDate"));
        create_date.setCellFactory(column -> {
            TableCell<Customer, Date> cell = new TableCell<Customer, Date>() {
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

        customer_demand_goods_table.setItems(data);
        // 选择行 保存数据
        customer_demand_goods_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDemandGoods>() {
            @Override
            public void changed(ObservableValue<? extends CustomerDemandGoods> observable, CustomerDemandGoods oldValue, CustomerDemandGoods newValue) {
                if(!"".equals(newValue.getId().toString())){
                    CustomerDemandGoodsQueryMiniController.customerDemandGoodsId = newValue.getId().toString();
                }
                // 客户需求商品查询
                if(!"".equals(newValue.getCreateNo().toString())){
                    CustomerDemandGoodsQueryMiniController.createNo = newValue.getCreateNo().toString();
                }
            }
        });
    }

    //关闭当前窗体
    @FXML
    public void closeWin(){
        Stage stage=(Stage)customer_demand_goods_table.getScene().getWindow();
        StageManager.CONTROLLER.remove("CustomerDemandGoodsController");
        StageManager.CONTROLLER.remove("CustomerDataMaintenanceController");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsQueryController");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsQueryControllerState");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsWhereController_discount_ben");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsWhereController_discount_end");
        stage.close();
    }
    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){
        // 客户需求商品
        CustomerDemandGoodsController demand = (CustomerDemandGoodsController) StageManager.CONTROLLER.get("CustomerDemandGoodsController");
        // 客户资料维护
        CustomerDataMaintenanceController maintain = (CustomerDataMaintenanceController) StageManager.CONTROLLER.get("CustomerDataMaintenanceController");
        // 客户需求商品
        CustomerDemandGoodsQueryController demandQuery = (CustomerDemandGoodsQueryController) StageManager.CONTROLLER.get("CustomerDemandGoodsQueryController");
        String state = (String) StageManager.CONTROLLER.get("CustomerDemandGoodsQueryControllerState");
        // 客户需求商品查询 开始
        CustomerDemandGoodsWhereController demandBen = (CustomerDemandGoodsWhereController) StageManager.CONTROLLER.get("CustomerDemandGoodsWhereController_discount_ben");
        // 客户需求商品查询 结束
        CustomerDemandGoodsWhereController demandEnd = (CustomerDemandGoodsWhereController) StageManager.CONTROLLER.get("CustomerDemandGoodsWhereController_discount_end");
        if(demandBen != null){
            demandBen.create_no.setText(createNo);
        }
        if(demandEnd != null){
            demandEnd.create_no_end.setText(createNo);
        }
        if(demand != null){
            demand.id.setText(customerDemandGoodsId);
            demand.getCustomerDemandGoodsById();
        }
        if(demandQuery != null){
            if(state != null){
                if("ben".equals(String.valueOf(state))){
                    demandQuery.create_no.setText(createNo);
                    demandQuery.create_no_end.setText(createNo);
                }else if("end".equals(String.valueOf(state))){
                    demandQuery.create_no_end.setText(createNo);
                }
            }
        }
        if(maintain != null){
            maintain.clearAll();
            maintain.id.setText(maintainId);
            maintain.customer_name.setText(maintainCustomerName);
            maintain.customer_no.setText(maintainCustomerNo);
            maintain.customer_no_str.setText(maintainCustomerNoRemark);
            maintain.create_no.setText(maintainBuildNo);
            maintain.create_date.setValue(maintainCreateDate);
            maintain.getBasicVal();
        }
        // 关闭窗口并清除 CONTROLLER 缓存
        closeWin();
    }
}
