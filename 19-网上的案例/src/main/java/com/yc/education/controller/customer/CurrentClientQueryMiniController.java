package com.yc.education.controller.customer;

import com.yc.education.controller.BaseController;
import com.yc.education.controller.sale.*;
import com.yc.education.controller.stock.SaleOutboundOrderController;
import com.yc.education.model.DataSetting;
import com.yc.education.model.customer.*;
import com.yc.education.property.CustomerProperty;
import com.yc.education.service.DataSettingService;
import com.yc.education.service.customer.ICustomerDetailInfoService;
import com.yc.education.service.customer.ICustomerService;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 现有客户查询
 * @Author: BlueSky
 * @Date: 2018/8/15 15:06
 */
@Controller
public class CurrentClientQueryMiniController extends BaseController implements Initializable {

    @Autowired
    ICustomerService iCustomerService;
    @Autowired
    ICustomerDetailInfoService iCustomerDetailInfoService;
    @Autowired
    DataSettingService iDataSettingService;

    @FXML
    TableView tableView;

    @FXML
    TableColumn id;
    @FXML
    TableColumn customer_id;
    @FXML
    TableColumn customer_type;
    @FXML
    TableColumn customer_call;
    @FXML
    TableColumn customer_general;
    @FXML
    TableColumn customer_level;
    @FXML
    TableColumn customer_note;
    @FXML
    Button client_sure;
    //======================= 客户基本资料 =============================
    // 客户编号
    private static String  code = "";
    // 客户姓名
    private static String  customer_name = "";
    // 注册地址
    private static String  register_address = "";
    // 客户简称
    private static String  customer_initials = "";
    // 客户简称
    private static String  customer_remark = "";
    // 客户id
    private static String  customerid = "";
    //======================= 客户需求商品 =============================

    //======================= 客户需求商品查询 =============================
    private static String  customerNo = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Customer> list = iCustomerService.listExistCustomer(1, Integer.MAX_VALUE);
        List<CustomerProperty> customerPropertyList = new ArrayList<>();
        List<DataSetting> dataSettingList = iDataSettingService.findDataSetting(10L);
        // 客户等级
        List<DataSetting> levelList = iDataSettingService.findDataSetting(14L);
        try {
            list.forEach(p->{
                if( p.getId() != null && p.getId() != 0L){
                    if(p.getCustomerLevel() != null && !"".equals(p.getCustomerLevel())){
                        p.setCustomerLevel(levelList.get(Integer.valueOf(p.getCustomerLevel())).getTitle());
                    }
                    if(p.getCustomerType() != null && !"".equals(p.getCustomerType())){
                        if(dataSettingList != null){
                            p.setCustomerType(dataSettingList.get(Integer.valueOf(p.getCustomerType())).getTitle());
                        }
                    }
                    CustomerProperty cust = new CustomerProperty(p.getId(), p.getCustomerType(), p.getCustomerLevel(), p.getCustomerCode(), p.getShortName(), p.getAddtime(),p.getGeneralCustomer(),p.getRemark());
                    customerPropertyList.add(cust);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        // checkbox
        Callback<TableColumn<CustomerProperty, Boolean>, TableCell<CustomerProperty, Boolean>> generalCheckboxFactory
                = new Callback<TableColumn<CustomerProperty, Boolean>, TableCell<CustomerProperty, Boolean>>() {
            @Override
            public TableCell call(final TableColumn<CustomerProperty, Boolean> param) {
                final TableCell<Customer, Boolean> cell = new TableCell<Customer, Boolean>() {

                    final CheckBox checkBox = new CheckBox("一般客户");

                    @Override
                    public void updateItem(Boolean ite, boolean empty) {
                        if(ite != null){
                            checkBox.setSelected(ite);
                        }
                        super.updateItem(ite, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            checkBox.setOnAction((ActionEvent t)
                                    -> {
                                int selectdIndex = getTableRow().getIndex();
                                CustomerProperty selectedRecord = (CustomerProperty) tableView.getItems().get(selectdIndex);
                                if(selectedRecord!=null){
                                    selectedRecord.setGeneralCustomer(!selectedRecord.isGeneralCustomer());
                                    iCustomerService.updateNotNull(new Customer(selectedRecord.getId(),selectedRecord.getCustomerType(), selectedRecord.getCustomerLevel(), selectedRecord.getRemark(), selectedRecord.getCustomerCode(), selectedRecord.getShortName(),  selectedRecord.isGeneralCustomer()));
                                }
                            });
                            checkBox.setMaxWidth(Double.MAX_VALUE);
                            setGraphic(checkBox);
                            setText(null);
                        }
                    }
                };
                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        };

        // 查询客户集合
        final ObservableList<CustomerProperty> data = FXCollections.observableArrayList(customerPropertyList);
        customer_general.setCellFactory(generalCheckboxFactory);

        id.setCellValueFactory(new PropertyValueFactory("id"));
        customer_id.setCellValueFactory(new PropertyValueFactory("customerCode"));
        customer_type.setCellValueFactory(new PropertyValueFactory("customerType"));//映射
        customer_call.setCellValueFactory(new PropertyValueFactory("shortName"));
        customer_general.setCellValueFactory(new PropertyValueFactory("generalCustomer"));
        customer_level.setCellValueFactory(new PropertyValueFactory("customerLevel"));
        customer_note.setCellValueFactory(new PropertyValueFactory("remark"));

        tableView.setItems(data);

        // 选择行 保存数据
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerProperty>() {
            @Override
            public void changed(ObservableValue<? extends CustomerProperty> observableValue, CustomerProperty oldItem, CustomerProperty newItem) {
                if(newItem.getCustomerCode() != null && !("".equals(newItem.getCustomerCode()))){
                    CurrentClientQueryMiniController.customerid = newItem.getId()+"";
                    CurrentClientQueryMiniController.customer_name = newItem.getShortName();
                    CurrentClientQueryMiniController.code = newItem.getCustomerCode();
                    CurrentClientQueryMiniController.register_address = "";

                    // 客户需求商品查询
                    CurrentClientQueryMiniController.customerNo = newItem.getCustomerCode();
                }
                if (newItem.getRemark() != null && !"".equals(newItem.getRemark())){
                    CurrentClientQueryMiniController.customer_remark = newItem.getRemark();
                }
            }
        });

        tableView.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });
    }


    //关闭当前窗体
    @FXML
    public void closeWin(){
        Stage stage=(Stage)client_sure.getScene().getWindow();
        StageManager.CONTROLLER.remove("CustomerBasicInfoController");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsController");
        StageManager.CONTROLLER.remove("CustomerDataMaintenanceController");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsQueryController");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsQueryControllerCustomerNo");
        StageManager.CONTROLLER.remove("OnlineOrderController");
        StageManager.CONTROLLER.remove("PurchaseOrderController");
        StageManager.CONTROLLER.remove("SaleGoodsController");
        StageManager.CONTROLLER.remove("SaleReturnController");
        StageManager.CONTROLLER.remove("SaleReturnStatusController");
        StageManager.CONTROLLER.remove("SaleDiscountQueryWhereController");
        StageManager.CONTROLLER.remove("SaleDiscountQueryWhereControllerEnd");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsWhereController");
        StageManager.CONTROLLER.remove("CustomerDemandGoodsWhereControllerEnd");
        StageManager.CONTROLLER.remove("SaleOutboundOrderController");

        stage.close();
    }

    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){

        // 基本资料
        CustomerBasicInfoController customer = (CustomerBasicInfoController) StageManager.CONTROLLER.get("CustomerBasicInfoController");
        if(customer != null){
            customer.customer_id.setText(customerid);
            customer.code.setText(code);
            customer.customer_initials.setText(customer_name);
            customer.customer_name.setText(customer_name);
            customer.register_address.setText(register_address);
            Customer cu = iCustomerService.selectByKey(Long.valueOf(customerid));
            if(cu != null){
                customer.customer_initials.setText(cu.getShortName());
                customer.customer_name.setText(cu.getName());
                customer.register_address.setText(cu.getRegisterAddress());
            }

        }
        // 客户需求商品
        CustomerDemandGoodsController demand = (CustomerDemandGoodsController) StageManager.CONTROLLER.get("CustomerDemandGoodsController");
        if(demand != null){
            demand.clearCustomerDemandGoodsVal();
            if(!"".equals(code)){
                demand.customer_no.setText(code);
            }
            if(!"".equals(customerid)){
                demand.customer_id.setText(customerid);
            }
            if(!"".equals(customer_name)){
                demand.customer_name.setText(customer_name);
                demand.create_people.setText(customer_name);
            }
            demand.build_number.setText(new Date().getTime()+getRandomone());
            if(!"".equals(customer_remark)){
                demand.remark.setText(customer_remark);
            }

        }
        // 客户资料维护
        CustomerDataMaintenanceController maintain = (CustomerDataMaintenanceController) StageManager.CONTROLLER.get("CustomerDataMaintenanceController");
        if(maintain != null && customerid != null && !"".equals(customerid)){
            maintain.customer_id.setText(customerid);
            Customer cu = iCustomerService.selectByKey(Long.valueOf(customerid));
            maintain.customer_name.setText(cu.getName());
            maintain.customer_no.setText(cu.getCustomerCode());
            maintain.customer_no_str.setText(cu.getRegisterAddress());
            maintain.create_no.setText(new Date().getTime()+getRandomone());
            maintain.create_date.setValue(LocalDateTime.now().toLocalDate());
            maintain.getMaintainMainInfoById();
        }
        // 客户需求商品查询
        CustomerDemandGoodsQueryController demandQuery = (CustomerDemandGoodsQueryController) StageManager.CONTROLLER.get("CustomerDemandGoodsQueryController");
        String demandQueryCustomer = (String) StageManager.CONTROLLER.get("CustomerDemandGoodsQueryControllerCustomerNo");
        if(demandQuery != null){
            if(demandQueryCustomer != null){
                if("ben".equals(String.valueOf(demandQueryCustomer))){
                    demandQuery.customer_no.setText(customerNo);
                    demandQuery.customer_no_end.setText(customerNo);
                }else if("end".equals(String.valueOf(demandQueryCustomer))){
                    demandQuery.customer_no_end.setText(customerNo);
                }
            }
        }
        // 销售--报价单
        QuotationController quotationController = (QuotationController) StageManager.CONTROLLER.get("QuotationController");
        if(quotationController != null){
//             清空值
//            quotationController.clearValue();
//            quotationController.add();
            if(!"".equals(code)){
                quotationController.customer_no.setText(code);
            }
            if(!"".equals(customerid)){
                quotationController.customer_id.setText(customerid);
            }
            if(!"".equals(customer_name)){
                quotationController.customer_no_str.setText(customer_name);
            }
        }
        // 网上订单
        OnlineOrderController onlineOrderController = (OnlineOrderController)StageManager.CONTROLLER.get("OnlineOrderController");
        if(onlineOrderController != null){
            onlineOrderController.customer_no.setText(code);
            onlineOrderController.customer_no_str.setText(customer_name);
            onlineOrderController.order_people.setText(customer_name);
        }
        // 订货单
        PurchaseOrderController purchaseOrderController = (PurchaseOrderController)StageManager.CONTROLLER.get("PurchaseOrderController");
        if(purchaseOrderController != null){
            purchaseOrderController.customer_no.setText(code);
            purchaseOrderController.customer_no_str.setText(customer_name);
        }
        // 订货单
        SaleGoodsController saleGoodsController = (SaleGoodsController)StageManager.CONTROLLER.get("SaleGoodsController");
        if(saleGoodsController != null){
            saleGoodsController.customerid.setText(customerid);
            saleGoodsController.customer_no.setText(code);
            saleGoodsController.customer_no_str.setText(customer_name);
        }
        // 销售退货单
        SaleReturnController saleReturnController = (SaleReturnController)StageManager.CONTROLLER.get("SaleReturnController");
        if(saleReturnController != null){
            saleReturnController.customer_no.setText(code);
            saleReturnController.customer_no_str.setText(customer_name);
        }
        // 销货单状态更新
        SaleReturnStatusController saleReturnStatusController = (SaleReturnStatusController)StageManager.CONTROLLER.get("SaleReturnStatusController");
        if(saleReturnStatusController != null){
            saleReturnStatusController.customer_no.setText(code);
            saleReturnStatusController.customer_short.setText(customer_name);
        }
        // 销售产品折扣查询 -客户编号开始
        SaleDiscountQueryWhereController saleDiscountQueryWhereController = (SaleDiscountQueryWhereController)StageManager.CONTROLLER.get("SaleDiscountQueryWhereController");
        if(saleDiscountQueryWhereController != null){
            saleDiscountQueryWhereController.customer_no.setText(code);
        }
        // 销售产品折扣查询 -客户编号结束
        SaleDiscountQueryWhereController saleDiscountQueryWhereControllerEnd = (SaleDiscountQueryWhereController)StageManager.CONTROLLER.get("SaleDiscountQueryWhereControllerEnd");
        if(saleDiscountQueryWhereControllerEnd != null){
            saleDiscountQueryWhereControllerEnd.customer_no_end.setText(code);
        }
        // 客户需求商品查询 -客户编号开始
        CustomerDemandGoodsWhereController customerDemandGoodsWhereController = (CustomerDemandGoodsWhereController)StageManager.CONTROLLER.get("CustomerDemandGoodsWhereController");
        if(customerDemandGoodsWhereController != null){
            customerDemandGoodsWhereController.cusotmer_no.setText(code);
        }
        // 客户需求商品查询 -客户编号结束
        CustomerDemandGoodsWhereController customerDemandGoodsWhereControllerEnd = (CustomerDemandGoodsWhereController)StageManager.CONTROLLER.get("CustomerDemandGoodsWhereControllerEnd");
        if(customerDemandGoodsWhereControllerEnd != null){
            customerDemandGoodsWhereControllerEnd.cusotmer_no_end.setText(code);
        }
        // 库存 - 销货出库单
        SaleOutboundOrderController SaleOutboundOrderController = (SaleOutboundOrderController)StageManager.CONTROLLER.get("SaleOutboundOrderController");
        if(SaleOutboundOrderController != null){
            SaleOutboundOrderController.customer_no.setText(code);
            SaleOutboundOrderController.customer_no_str.setText(customer_name);
        }

        //关闭窗口
        closeWin();
    }

}
