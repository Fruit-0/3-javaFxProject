package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SaleOfferProduct;
import com.yc.education.model.sale.SaleQuotation;
import com.yc.education.property.SalePurchaseOrderProductProperty;
import com.yc.education.property.SaleQuotationImportProductProperty;
import com.yc.education.service.sale.ISaleOfferProductService;
import com.yc.education.service.sale.ISaleQuotationService;
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
 * 报价单导入--查询
 */
@Controller
public class QuotationImportController extends BaseController implements Initializable {


    @Autowired
    ISaleQuotationService iSaleQuotationService;
    @Autowired
    ISaleOfferProductService iSaleOfferProductService;

    @FXML
    Button client_sure;

    @FXML TableView tab_order;

    @FXML TableColumn col_order_id;
    @FXML TableColumn col_order_no;
    @FXML TableColumn col_order_date;
    @FXML TableColumn col_order_valid_until; //有效期至
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
    final ObservableList<SaleQuotationImportProductProperty> importData = FXCollections.observableArrayList();
    // 导入选中的产品--订货单
    final ObservableList<SalePurchaseOrderProductProperty> importPurchaseData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initQuotation();
        importData.clear();
//        importPurchaseData.clear();
    }


    /**
     * 关闭导入窗口
     */
    @FXML
    public void closeImprotWin(){
        Stage stage=(Stage)client_sure.getScene().getWindow();
        StageManager.CONTROLLER.remove("PurchaseOrderControllerImport");
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
            PurchaseOrderController orderController = (PurchaseOrderController) StageManager.CONTROLLER.get("PurchaseOrderControllerImport");
            if(orderController != null){
                SaleQuotation quotation = iSaleQuotationService.selectByKey(Long.valueOf(orderid));
                // 把选中订单数据加载到采购订单上
                orderController.setBasicImportVal(quotation);
                // 把采购订单中的选中产品加载到采购订单的采购产品中
                for (SaleQuotationImportProductProperty p : importData) {
                    if(p.isChecked() && p.getId() != null && p.getId()>0){
                        SaleOfferProduct product = iSaleOfferProductService.selectByKey(p.getId());
                        importPurchaseData.add(new SalePurchaseOrderProductProperty(null, Long.valueOf(orderid), product.getProductNo(), product.getProductName(), product.getCategory(), product.getNum(), product.getUnit(), product.getPricing(), product.getDiscount(), product.getPrice(), product.getMoney(), quotation.getOfferNo(), "报价单", product.getRemark()));
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
        List<SaleQuotation> list = iSaleQuotationService.listSaleQuotationAll(1, Integer.MAX_VALUE);
        try {
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                p.setValidUntilStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getValidUntil()));
                if(p.getOrderAudit() != null && p.getOrderAudit()){
                    p.setAuditStatus("已审核");
                }
            });
            Iterator<SaleQuotation> it = list.iterator();
            list = new ArrayList<>();
            while(it.hasNext()){
                SaleQuotation x = it.next();
                if(!x.getOrderAudit()){
                    it.remove();
                }else {
                    list.add(x);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // 查询客户集合
        final ObservableList<SaleQuotation> data = FXCollections.observableArrayList(list);
        col_order_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_order_no.setCellValueFactory(new PropertyValueFactory("offerNo"));
        col_order_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));//映射
        col_order_valid_until.setCellValueFactory(new PropertyValueFactory("validUntilStr"));
        col_order_customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        col_order_customer_name.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        col_order_status.setCellValueFactory(new PropertyValueFactory("auditStatus"));

        tab_order.setItems(data);

        // 选择行 查询数据
        tab_order.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleQuotation>() {
            @Override
            public void changed(ObservableValue<? extends SaleQuotation> observableValue, SaleQuotation oldItem, SaleQuotation newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId()))){
                    QuotationImportController.orderid = newItem.getId().toString();
                    List<SaleOfferProduct> offerProductList = iSaleOfferProductService.listSaleOfferProduct(newItem.getId());

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


                    for (SaleOfferProduct p : offerProductList) {
                        importData.add(new SaleQuotationImportProductProperty(p.getId(), p.getProductNo(), p.getProductName(), p.getNum(), p.getUnit(), p.getPrice(), p.getRemark(), false));
                    }
                    tab_product.setItems(importData);

                }
            }
        });
        // 设置选择多行
        tab_product.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
