package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.*;
import com.yc.education.property.SaleGoodsProductProperty;
import com.yc.education.property.SaleOfferProductProperty;
import com.yc.education.property.SalePurchaseOrderProductProperty;
import com.yc.education.property.SaleQuotationImportProductProperty;
import com.yc.education.service.sale.*;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 采购订单导入--查询
 */
@Controller
public class PurchaseOrderImportController extends BaseController implements Initializable {


    @Autowired
    ISalePurchaseOrderService iSalePurchaseOrderService;
    @Autowired
    ISalePurchaseOrderProductService iSalePurchaseOrderProductService;

    @FXML
    Button client_sure;

    @FXML TableView tab_order;

    @FXML TableColumn col_order_id;
    @FXML TableColumn col_order_no;
    @FXML TableColumn col_order_date;
    @FXML TableColumn col_order_category; //客户类型
    @FXML TableColumn col_order_customer_no;
    @FXML TableColumn col_order_customer_name;
    @FXML TableColumn col_order_status;

    @FXML TableView tab_product;

    @FXML TableColumn tab_product_che;
    @FXML TableColumn tab_product_id;
    @FXML TableColumn tab_product_no;
    @FXML TableColumn tab_product_name;
    @FXML TableColumn tab_product_num;
    @FXML TableColumn tab_product_unit;
    @FXML TableColumn tab_product_price;
    @FXML TableColumn tab_product_remark;


    // 订单编号
    private static String  orderid = "";
    // 查询订单中产品
    final ObservableList<SalePurchaseOrderProductProperty> importData = FXCollections.observableArrayList();
    // 导入选中的产品--销货单
    final ObservableList<SaleGoodsProductProperty> importPurchaseData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initQuotation();
        importData.clear();
    }


    /**
     * 关闭导入窗口
     */
    @FXML
    public void closeImprotWin(){
        Stage stage=(Stage)client_sure.getScene().getWindow();
        StageManager.CONTROLLER.remove("SaleGoodsControllerImport");
        importData.clear();
        importPurchaseData.clear();
        stage.close();
    }

    /**
     * 确认按钮-关闭窗口
     */
    @FXML
    public void sureCloseImportWin(){
        if(orderid != null && !"".equals(orderid)){
            SaleGoodsController orderController = (SaleGoodsController) StageManager.CONTROLLER.get("SaleGoodsControllerImport");
            if(orderController != null){
                SalePurchaseOrder quotation = iSalePurchaseOrderService.selectByKey(Long.valueOf(orderid));
                // 把选中订单数据加载到采购订单上
                orderController.setBasicImportVal(quotation);
                // 把采购订单中的选中产品加载到采购订单的采购产品中
                for (SalePurchaseOrderProductProperty p : importData) {
                    if(p.isChecked() && p.getId() != null && p.getId()>0){
                        SalePurchaseOrderProduct product = iSalePurchaseOrderProductService.selectByKey(p.getId());
                        importPurchaseData.add(new SaleGoodsProductProperty(null, Long.valueOf(orderid), product.getProductNo(), product.getProductName(), product.getCategory(), product.getNum(), product.getUnit(), product.getPricing(), product.getDiscount(), product.getPrice(), product.getMoney(), "", "", product.getRemark()));
                    }
                }
                if(importPurchaseData != null){
                    orderController.generalProductTab(importPurchaseData);
                }
                orderController.setControllerUse();
            }
        }
        closeImprotWin();
    }

    /**
     * 初始化报价单信息
     */
    private void initQuotation(){
        List<SalePurchaseOrder> list = iSalePurchaseOrderService.listSalePurchaseOrderAll();
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                if(p.getOrderAudit() != null && p.getOrderAudit()){
                    p.setAuditStatus("已审核");
                }
            });
            Iterator<SalePurchaseOrder> it = list.iterator();
            list = new ArrayList<>();
            while(it.hasNext()){
                SalePurchaseOrder x = it.next();
                if(x.getOrderAudit() != null && !x.getOrderAudit()){
                    it.remove();
                }else {
                    list.add(x);
                }
            }
        }

        // 查询客户集合
        final ObservableList<SalePurchaseOrder> data = FXCollections.observableArrayList(list);
        col_order_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_order_no.setCellValueFactory(new PropertyValueFactory("orderNo"));
        col_order_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        col_order_category.setCellValueFactory(new PropertyValueFactory("customerCategory"));
        col_order_customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        col_order_customer_name.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        col_order_status.setCellValueFactory(new PropertyValueFactory("auditStatus"));

        tab_order.setItems(data);

        // 选择行 查询数据
        tab_order.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalePurchaseOrder>() {
            @Override
            public void changed(ObservableValue<? extends SalePurchaseOrder> observableValue, SalePurchaseOrder oldItem, SalePurchaseOrder newItem) {
                if(newItem.getId() != null && !"".equals(newItem.getId()) && newItem.getOrderNo() != null && !"".equals(newItem.getOrderNo())){
                    PurchaseOrderImportController.orderid = newItem.getId().toString();
                    List<SalePurchaseOrderProduct> productList = iSalePurchaseOrderProductService.listPurchaseOrderProduct(newItem.getId().toString());
                    importData.clear();
                    tab_product.setEditable(true);
                    tab_product_che.setCellFactory(CheckBoxTableCell.forTableColumn(tab_product_che));
                    tab_product_che.setCellValueFactory(new PropertyValueFactory("checked"));
                    tab_product_id.setCellValueFactory(new PropertyValueFactory("id"));
                    tab_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));
                    tab_product_name.setCellValueFactory(new PropertyValueFactory("productName"));//映射
                    tab_product_num.setCellValueFactory(new PropertyValueFactory("num"));
                    tab_product_unit.setCellValueFactory(new PropertyValueFactory("unit"));
                    tab_product_price.setCellValueFactory(new PropertyValueFactory("price"));
                    tab_product_remark.setCellValueFactory(new PropertyValueFactory("remark"));


                    for (SalePurchaseOrderProduct p : productList) {
                        importData.add(new SalePurchaseOrderProductProperty(p.getId(), 0L, p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(),p.getDiscount() ,p.getPrice() ,p.getMoney() , newItem.getOrderNo() , "采购单",p.getRemark(),false));
                    }
                    tab_product.setItems(importData);

                }
            }
        });
        // 设置选择多行
        tab_product.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
