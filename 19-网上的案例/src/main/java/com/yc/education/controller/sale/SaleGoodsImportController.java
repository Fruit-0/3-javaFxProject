package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SaleGoods;
import com.yc.education.model.sale.SaleGoodsProduct;
import com.yc.education.model.sale.SalePurchaseOrder;
import com.yc.education.model.sale.SalePurchaseOrderProduct;
import com.yc.education.property.SaleGoodsProductProperty;
import com.yc.education.property.SalePurchaseOrderProductProperty;
import com.yc.education.property.SaleReturnGoodsProductProperty;
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
 * 销货单导入--查询
 */
@Controller
public class SaleGoodsImportController extends BaseController implements Initializable {

    @Autowired
    ISaleGoodsService iSaleGoodsService;
    @Autowired
    ISaleGoodsProductService iSaleGoodsProductService;

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
     ObservableList<SaleGoodsProductProperty> importData = FXCollections.observableArrayList();
    // 导入选中的产品--销货单
     ObservableList<SaleReturnGoodsProductProperty> importPurchaseData = FXCollections.observableArrayList();


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
        StageManager.CONTROLLER.remove("SaleReturnControllerImport");
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
            SaleReturnController orderController = (SaleReturnController) StageManager.CONTROLLER.get("SaleReturnControllerImport");
            if(orderController != null){
                SaleGoods quotation = iSaleGoodsService.selectByKey(Long.valueOf(orderid));
                // 把选中订单数据加载到退货订单上
                orderController.setBasicImportVal(quotation);
                // 把采购订单中的选中产品加载到采购订单的采购产品中
                for (SaleGoodsProductProperty k : importData) {
                    if(k.isChecked() && k.getId() != null && k.getId()>0){
                        SaleGoodsProduct p = iSaleGoodsProductService.selectByKey(k.getId());
                        importPurchaseData.add(new SaleReturnGoodsProductProperty(0L, quotation.getId(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(), p.getMoney(),"采购订单" , quotation.getSaleNo(),p.getWarehousePosition() , p.getFloor(),p.getRemark() ));
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
     * 初始化销货单信息
     */
    private void initQuotation(){
        List<SaleGoods> list = iSaleGoodsService.listSaleGoodsAll();
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                if(p.getOrderAudit() != null && p.getOrderAudit()){
                    p.setAuditStatus("已审核");
                }
            });
            Iterator<SaleGoods> it = list.iterator();
            list = new ArrayList<>();
            while(it.hasNext()){
                SaleGoods x = it.next();
                if(x.getOrderAudit() != null && !x.getOrderAudit()){
                    it.remove();
                }else {
                    list.add(x);
                }
            }
        }

        // 查询客户集合
        final ObservableList<SaleGoods> data = FXCollections.observableArrayList(list);
        col_order_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_order_no.setCellValueFactory(new PropertyValueFactory("saleNo"));
        col_order_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        col_order_category.setCellValueFactory(new PropertyValueFactory("customerCategory"));
        col_order_customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        col_order_customer_name.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        col_order_status.setCellValueFactory(new PropertyValueFactory("auditStatus"));

        tab_order.setItems(data);

        // 选择行 查询数据
        tab_order.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleGoods>() {
            @Override
            public void changed(ObservableValue<? extends SaleGoods> observableValue, SaleGoods oldItem, SaleGoods newItem) {
                if(newItem.getId() != null && !"".equals(newItem.getId()) && newItem.getSaleNo() != null && !"".equals(newItem.getSaleNo())){
                    SaleGoodsImportController.orderid = newItem.getId().toString();
                    List<SaleGoodsProduct> productList = iSaleGoodsProductService.listSaleGoodsProduct(newItem.getId().toString());
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

                    try {
                        for (SaleGoodsProduct p : productList) {
                            importData.add(new SaleGoodsProductProperty(p.getId(), 0L, p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(),p.getDiscount().toString() ,p.getPrice() ,p.getMoney() , newItem.getSaleNo() , "采购单",p.getRemark(),false));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    tab_product.setItems(importData);

                }
            }
        });
        // 设置选择多行
        tab_product.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
