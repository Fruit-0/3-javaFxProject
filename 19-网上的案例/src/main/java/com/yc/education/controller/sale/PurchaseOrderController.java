package com.yc.education.controller.sale;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;

import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.customer.Remark;
import com.yc.education.model.sale.*;


import com.yc.education.property.RemarkProperty;
import com.yc.education.property.ReportRemarkProperty;
import com.yc.education.property.SalePurchaseOrderProductProperty;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.customer.IRemarkService;
import com.yc.education.service.sale.IReportRemarkService;
import com.yc.education.service.sale.ISaleOfferProductService;
import com.yc.education.service.sale.ISalePurchaseOrderProductService;
import com.yc.education.service.sale.ISalePurchaseOrderService;

import com.yc.education.util.DateUtils;
import com.yc.education.util.EditCell;
import com.yc.education.util.NumberUtil;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 销售--订货单
 */
@Controller
public class PurchaseOrderController extends BaseController implements Initializable {

    private Stage stage = new Stage();
    private static SpringFxmlLoader loader = new SpringFxmlLoader();

    @Autowired
    IRemarkService iRemarkService;
    @Autowired
    IReportRemarkService iReportRemarkService;
    @Autowired
    ISalePurchaseOrderService iSalePurchaseOrderService;
    @Autowired
    ISalePurchaseOrderProductService iSalePurchaseOrderProductService;
    @Autowired
    ISaleOfferProductService iSaleOfferProductService;
    @Autowired
    private ProductBasicService productBasicService; //产品 Service

    // 菜单控件
    @FXML VBox menu_clearAll; //清除
    @FXML VBox menu_commit;  //提交
    @FXML VBox menu_insert; //新增
    @FXML VBox menu_update; //修改
    @FXML VBox menu_delete; //删除
    @FXML VBox shiro_success;
    @FXML VBox shiro_cancel;
    @FXML VBox import_out;
    @FXML VBox import_in;
    @FXML Label writestate;// 待输入

    // 订单id
    @FXML TextField orderid;

    @FXML DatePicker order_date;  // 制单日期
    @FXML public TextField customer_no; // 客户编号
    @FXML TextField order_no; // 订货单号
    @FXML public TextField customer_no_str; // 客户编号描述
    @FXML TextField customer_order_no; // 客户订单号
    @FXML ComboBox tax; // 税别
    @FXML ComboBox currency; // 币别
    @FXML TextField discount; // 折扣
    @FXML ComboBox shipping_warehouse; // 出货仓库
    // 作废按钮
    @FXML Button btn_invalid;
    // 特价单复选框
    @FXML CheckBox ch_special;
    @FXML TextField shipping_warehouse_str; //出货仓库描述

    @FXML TextField customer_category; //客户类别
    @FXML TextField customer_name; //客户名称
    @FXML TextField receivable_balance; //应收余额
    @FXML TextField made_people; //制单人
    @FXML ComboBox payment; //支付方式
    @FXML TextField reviewer; // 审核人
    @FXML TextField reviewer_str; // 审核人描述
    @FXML TextField last_udpate; //最后修改人
    @FXML TextField last_udpate_str; //最后修改人描述
    @FXML ComboBox contact; // 联系人
    @FXML ComboBox phone; // 联系电话
    @FXML TextField invoice_address; //发票地址
    @FXML ComboBox shipping_address; //送货地址
    @FXML ComboBox invoice_title; //发票抬头
    @FXML ComboBox business_leader; //负责业务
    @FXML ComboBox fax; // 传真
    @FXML TextField zip; //邮政编码
    @FXML TextField business_leader_str; //负责业务描述
    @FXML Button btn_copy; // 复制 按钮



    @FXML TableView product_table; // 订货产品 表格
    @FXML TableColumn col_id; // 列id
    @FXML TableColumn col_product_no; //列 产品编号
    @FXML TableColumn col_product_name; //列 产品名称
    @FXML TableColumn col_category; //列 品类
    @FXML TableColumn col_num; //列 数量
    @FXML TableColumn col_unit; // 列 单位
    @FXML TableColumn col_pricing; // 列 定价
    @FXML TableColumn col_discount; //列 折扣
    @FXML TableColumn col_price; //列 单价
    @FXML TableColumn col_money; //列 金额
    @FXML TableColumn col_order_no; //列 订货单号
    @FXML TableColumn col_product_source; //列 产品来源
    @FXML TableColumn col_remark; //列 备注


    @FXML TableView remark_table; //备注及说明--备注表格
    @FXML TableColumn col_remark_id; //列id
    @FXML TableColumn col_remark_content; //列 内容

    @FXML TableView report_table; //备注及说明--报表
    @FXML TableColumn col_report_id; // 列 id
    @FXML TableColumn col_report_content; //列 内容


    // 合计
    @FXML TextField total_num; // 合计数量
    @FXML TextField total_money; //合计金额
    @FXML TextField total_loan; //合计贷款
    @FXML TextField tax_total; //税款合计

    /***************************************** 弹出窗口-产品 ********************************************/
    @FXML private TableView tableViewProduct; //产品基本查询
    @FXML private TableColumn depid; //id
    @FXML private TableColumn findproductid; //产品基本编号
    @FXML private TableColumn findproductname; //产品基本名称
    @FXML private TableColumn findprotype; //产品类型
    @FXML private TableColumn findnormprice; //产品标准售价
    @FXML private TableColumn findlowprice; //产品最低售价
    @FXML private TableColumn findsafetystock; //安全存量
    @FXML private TableColumn findremarks; //备注
    @FXML private Button client_sure; // 确定按钮
    int tablePosition = 0; // 弹出窗口选中的行数

    /***************************************** 弹出窗口-产品-结束 ********************************************/

    // 日期格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINA);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime localDate = LocalDateTime.now();
    // 当前页
    private static int page = 0;
    // 页大小
    private final static int rows = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tax.getItems().addAll("未税","已税");
        tax.getSelectionModel().select(0);
        setComboBox(7L,currency,0); //币别
        setComboBox(20L,payment,0); //收款方式
        setComboBox(29L,shipping_warehouse,0); //出货仓库
        setControllerDisable();

        // 报价产品行双击 调出现有产品窗口
        BaseController.clickEvent(product_table, data ->{
            tablePosition = product_table.getSelectionModel().getSelectedIndex();
            moreProductButtonClick();
        }, 2);

        SaleQuotation saleQuotation = StageManager.saleQuotation;
        StageManager.saleQuotation = null;
        if(saleQuotation==null){
            firstData();
        }else{
            clearControllerVal();
            //添加转单数据
            if(saleQuotation.getCreateDate() != null){
                order_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(saleQuotation.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
            }
            if(saleQuotation.getCustomerNo() != null){
                customer_no.setText(saleQuotation.getCustomerNo());
            }
            if(saleQuotation.getOfferNo() != null){
                order_no.setText(saleQuotation.getOfferNo());
            }
            if(saleQuotation.getCustomerNoStr() != null){
                customer_no_str.setText(saleQuotation.getCustomerNoStr());
            }
//            if(saleQuotation.getCustomerOrderNo() != null){
//                customer_order_no.setText(saleQuotation.getCustomerOrderNo());
//            }
            if(saleQuotation.getTax() != null){
                tax.getSelectionModel().select(Integer.valueOf(saleQuotation.getTax())+0);
            }
            if(saleQuotation.getCurrency() != null){
                setComboBox(7L,currency,saleQuotation.getCurrency()-1); //币别
            }
            if(saleQuotation.getDiscount() != null){
                discount.setText(saleQuotation.getDiscount());
            }
//            if(saleQuotation.getWarehouseOut() != null){
//                setComboBox(29L,shipping_warehouse,saleQuotation.getWarehouseOut()-1); //出货仓库
//            }
//            if(saleQuotation.getWarehouseOutStr() != null){
//                shipping_warehouse_str.setText(saleQuotation.getWarehouseOutStr());
//            }
            if(saleQuotation.getSpecialOffer() != null){
                ch_special.setSelected(saleQuotation.getSpecialOffer());
            }
            if(saleQuotation.getCustomerCategory() != null){
                customer_category.setText(saleQuotation.getCustomerCategory());
            }
            if(saleQuotation.getCustomerName() != null){
                customer_name.setText(saleQuotation.getCustomerName());
            }
            if(saleQuotation.getAmountReceivable() != null){
                receivable_balance.setText(saleQuotation.getAmountReceivable().toString());
            }
            if(saleQuotation.getSinglePeople() != null){
                made_people.setText(saleQuotation.getSinglePeople());
            }
//            if(saleQuotation.getPaymentMethod() != null){
//                setComboBox(20L,payment,saleQuotation.getPaymentMethod()-1); //收款方式
//            }
            // 审核
//            if(saleQuotation.getAudit() != null){
//                reviewer.setText(saleQuotation.getAudit());
//            }
//            if(saleQuotation.getAuditStr() != null){
//                reviewer_str.setText(saleQuotation.getAuditStr());
//            }
//            if(saleQuotation.getLastModifier() != null){
//                last_udpate.setText(saleQuotation.getLastModifier());
//            }
//            if(saleQuotation.getLastModifierStr() != null){
//                last_udpate_str.setText(saleQuotation.getLastModifierStr());
//            }
            lastUpdatePeopel(last_udpate, last_udpate_str);
            if(saleQuotation.getContact() != null){
                contact.getSelectionModel().select(saleQuotation.getContact());
            }
            if(saleQuotation.getTelephone() != null){
                phone.getSelectionModel().select(saleQuotation.getTelephone());
            }
//            if(saleQuotation.getInvoiceAddress() != null){
//                invoice_address.setText(saleQuotation.getInvoiceAddress());
//            }
            if(saleQuotation.getAddress() != null){
                shipping_address.getSelectionModel().select(saleQuotation.getAddress());
            }
//            if(saleQuotation.getInvoiceAddress() != null){
//                invoice_title.getSelectionModel().select(saleQuotation.getInvoiceTitle());
//            }
            if(saleQuotation.getBusiness() != null){
                business_leader.getSelectionModel().select(saleQuotation.getBusiness());
            }
            if(saleQuotation.getFax() != null){
                fax.getSelectionModel().select(saleQuotation.getFax());
            }
//            if(saleQuotation.getZip() != null){
//                zip.setText(saleQuotation.getZip());
//            }
            if(saleQuotation.getBusinessStr() != null){
                business_leader_str.setText(saleQuotation.getBusinessStr());
            }
            shiro_cancel.setDisable(true);
            shiro_success.setDisable(false);
            setControllerUse();
        }
    }

    /**
     * 现有产品查询
     */
    public void loadMoreProduct(){

        List<ProductBasic> productBasics = productBasicService.selectProductBasic();

        ObservableList<ProductBasic> list = FXCollections.observableArrayList();

        tableViewProduct.setEditable(true);

        /*staffcode.setCellFactory((TableColumn<Object,Object> a ) -> new EditingCell<>());*/

        depid.setCellValueFactory(new PropertyValueFactory("id"));
        findproductid.setCellValueFactory(new PropertyValueFactory("isnum"));
        findproductname.setCellValueFactory(new PropertyValueFactory("proname"));
        findprotype.setCellValueFactory(new PropertyValueFactory("protype"));
        findnormprice.setCellValueFactory(new PropertyValueFactory("normprice"));
        findlowprice.setCellValueFactory(new PropertyValueFactory("lowprice"));
        findsafetystock.setCellValueFactory(new PropertyValueFactory("safetystock"));
        findremarks.setCellValueFactory(new PropertyValueFactory("remarks"));

        for (ProductBasic productBasic:productBasics) {
            list.add(productBasic);
        }

        tableViewProduct.setItems(list); //tableview添加list

        tableViewProduct.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductBasic>() {
            @Override
            public void changed(ObservableValue<? extends ProductBasic> observableValue, ProductBasic oldItem, ProductBasic newItem) {
                if(newItem.getIsnum()!= null && !("".equals(newItem.getIsnum()))){
                    client_sure.setUserData(newItem.getId());
                }
            }
        });


        tableViewProduct.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });
    }

    /**
     * 更多产品查询
     */
    public  void moreProductButtonClick(){
        stage.setTitle("现有产品基本查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/sale/product_find_purchase.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        loadMoreProduct();
    }

    public void closeSureWin(){
        long productid =(long)client_sure.getUserData();
        ProductBasic productBasic =  productBasicService.selectByKey(productid);
        // 把数据绑到当前选中的行中
        // todo...
        ObservableList<SalePurchaseOrderProductProperty> list = product_table.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        for (int i = 0; i < list.size(); i++) {
            if(tablePosition == i){
                list.get(i).setProductName(productBasic.getProname());
                list.get(i).setProductNo(productBasic.getIsnum());
            }
        }
        product_table.setItems(FXCollections.observableArrayList(list));
        coseWin();
    }

    public void coseWin(){
        stage.close();
        tablePosition = 0;
    }

    /**
     * 产品view
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfProductTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addProductRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfProduct();
            setTableviewVal();
        }
    }

    /**
     * 备注view
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfRemarkTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addRemarkRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfRemark();
            setTableviewRemarkVal();
        }
    }

    /**
     * 报表备注view
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfReportRemarkTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addReportRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfReport();
            setTableviewReportVal();
        }
    }

    /**
     * 删除产品行
     */
    private void deleteRowOfProduct(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) product_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            SalePurchaseOrderProductProperty property = (SalePurchaseOrderProductProperty)product_table.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iSalePurchaseOrderProductService.delete(property.getId());
                returnMsg(rows);
            }
            final ObservableList<SalePurchaseOrderProductProperty> dataProperty = product_table.getItems();
            final ObservableList<SalePurchaseOrderProductProperty> newDataProperty = FXCollections.observableArrayList();
            for (int i = 0; i < dataProperty.size(); i++) {
                if(i != index){
                    newDataProperty.add(dataProperty.get(i));
                }
            }
            product_table.setItems(newDataProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加产品行
     */
    public void addProductRow() {

        ObservableList<SalePurchaseOrderProductProperty> list = product_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new SalePurchaseOrderProductProperty("", "", "", 0, "", new BigDecimal("0.00"), "", new BigDecimal("0.00"), new BigDecimal("0.00"), "", "", ""));
        product_table.setItems(list);
    }

    /**
     * 删除备注行
     */
    private void deleteRowOfRemark(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) remark_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            RemarkProperty property = (RemarkProperty)remark_table.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iRemarkService.delete(property.getId());
                returnMsg(rows);
            }
        }catch (Exception e){
            alert_informationDialog("请选择需要删除的行！");
            e.printStackTrace();
        }
    }

    /**
     * 添加备注行
     */
    public void addRemarkRow() {

        ObservableList<RemarkProperty> list = remark_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new RemarkProperty(""));
        remark_table.setItems(list);
    }

    /**
     * 删除报表行
     */
    private void deleteRowOfReport(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) report_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            ReportRemarkProperty property = (ReportRemarkProperty)report_table.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iReportRemarkService.delete(property.getId());
                returnMsg(rows);
            }
        }catch (Exception e){
            alert_informationDialog("请选择需要删除的行！");
            e.printStackTrace();
        }
    }

    /**
     * 添加报表行
     */
    public void addReportRow() {

        ObservableList<ReportRemarkProperty> list = report_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new ReportRemarkProperty(""));
        report_table.setItems(list);
    }

    /**
     * 新增订单
     */
    @FXML
    public void add(){
        clearControllerVal();
        order_date.setValue(localDate.toLocalDate());
        order_no.setText("WS"+new Date().getTime()+getRandomone());
        setControllerUse();
    }

    /**
     * 保存数据
     */
    @FXML
    public void save(){
        SalePurchaseOrder order = new SalePurchaseOrder();
        if(order_date.getValue() != null){
            try {
                Date date = df.parse(order_date.getValue().toString());
                order.setCreateDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(customer_no.getText() != null && !"".equals(customer_no.getText())){
            order.setCustomerNo(customer_no.getText());
        }
        if(order_no.getText() != null && !"".equals(order_no.getText())){
            order.setOrderNo(order_no.getText());
        }
        if(customer_no_str.getText() != null && !"".equals(customer_no_str.getText())){
            order.setCustomerNoStr(customer_no_str.getText());
        }
        if(customer_order_no.getText() != null && !"".equals(customer_order_no.getText())){
            order.setCustomerOrderNo(customer_order_no.getText());
        }
        if(discount.getText() != null && !"".equals(discount.getText())){
            order.setDiscount(discount.getText());
        }
        if(shipping_warehouse_str.getText() != null && !"".equals(shipping_warehouse_str.getText())){
            order.setWarehouseOutStr(shipping_warehouse_str.getText());
        }
        if(customer_category.getText() != null && !"".equals(customer_category.getText())){
            order.setCustomerCategory(customer_category.getText());
        }
        if(customer_name.getText() != null && !"".equals(customer_name.getText())){
            order.setCustomerName(customer_name.getText());
        }
        if(receivable_balance.getText() != null && !"".equals(receivable_balance.getText())){
            order.setReceivableBalance(Long.valueOf(receivable_balance.getText()));
        }
        if(tax.getSelectionModel().getSelectedItem() != null && !"".equals(tax.getSelectionModel().getSelectedItem())){
            order.setTax(tax.getSelectionModel().getSelectedIndex()+"");
        }
        if(currency.getSelectionModel().getSelectedItem() != null && !"".equals(currency.getSelectionModel().getSelectedItem())){
            order.setCurrency(currency.getSelectionModel().getSelectedIndex()+1);
        }
        if(shipping_warehouse.getSelectionModel().getSelectedItem() != null && !"".equals(shipping_warehouse.getSelectionModel().getSelectedItem())){
            order.setWarehouseOut(shipping_warehouse.getSelectionModel().getSelectedIndex()+1);
        }

        if(made_people.getText() != null && !"".equals(made_people.getText())){
            order.setMadePeople(made_people.getText());
        }
        if(reviewer.getText() != null && !"".equals(reviewer.getText())){
            order.setAuditPeople(reviewer.getText());
        }
        if(reviewer_str.getText() != null && !"".equals(reviewer_str.getText())){
            order.setAuditPeopleStr(reviewer_str.getText());
        }
        if(last_udpate.getText() != null && !"".equals(last_udpate.getText())){
            order.setLastUpdate(last_udpate.getText());
        }
        if(last_udpate_str.getText() != null && !"".equals(last_udpate_str.getText())){
            order.setLastUpdateStr(last_udpate_str.getText());
        }
        if(payment.getSelectionModel().getSelectedItem() != null && !"".equals(payment.getSelectionModel().getSelectedItem())){
            order.setPaymentMethod(payment.getSelectionModel().getSelectedIndex()+1);
        }
        if(contact.getSelectionModel().getSelectedItem() != null && !"".equals(contact.getSelectionModel().getSelectedItem())){
            order.setContact(contact.getValue().toString());
        }
        if(phone.getSelectionModel().getSelectedItem() != null && !"".equals(phone.getSelectionModel().getSelectedItem())){
            order.setPhone(phone.getValue().toString());
        }
        if(shipping_address.getSelectionModel().getSelectedItem() != null && !"".equals(shipping_address.getSelectionModel().getSelectedItem())){
            order.setShippingAddress(shipping_address.getValue().toString());
        }
        if(business_leader.getSelectionModel().getSelectedItem() != null && !"".equals(business_leader.getSelectionModel().getSelectedItem())){
            order.setBusinessLeader(business_leader.getValue().toString());
        }
        if(business_leader_str.getText() != null && !"".equals(business_leader_str.getText())){
            order.setBusinessLeaderStr(business_leader_str.getText());
        }
        if(fax.getSelectionModel().getSelectedItem() != null && !"".equals(fax.getSelectionModel().getSelectedItem())){
            order.setFax(fax.getValue().toString());
        }
        if(zip.getText() != null && !"".equals(zip.getText())){
            order.setZip(zip.getText());
        }
        if(invoice_title.getSelectionModel().getSelectedItem() != null && !"".equals(invoice_title.getSelectionModel().getSelectedItem())){
            order.setInvoiceTitle(invoice_title.getValue().toString());
        }
        if(invoice_address.getText() != null && !"".equals(invoice_address.getText())){
            order.setInvoiceAddress(invoice_address.getText());
        }
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            order.setId(Long.valueOf(orderid.getText()));
        }
        order.setSpecialOrder(ch_special.selectedProperty().getValue());

        if(order.getId() != null){
            // 修改
            int rows = iSalePurchaseOrderService.updateNotNull(order);
            returnMsg(rows);
            setControllerDisable();
        }else{
            order.setAddtime(new Date());
            // 保存
            int rows = iSalePurchaseOrderService.save(order);
            orderid.setText(order.getId().toString());
            returnMsg(rows);
            setControllerDisable();
        }
        // 操作tableview中的数据
        saveTableviewProduct();
        saveTableviewRemark();
        saveTableviewReport();

    }

    /**
     * 保存订货产品tableview数据
     */
    private void saveTableviewProduct(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = product_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                SalePurchaseOrderProductProperty property = null;
                if(product_table.getItems().get(i) != null){
                    property = (SalePurchaseOrderProductProperty)product_table.getItems().get(i);
                }
                SalePurchaseOrderProduct product = new SalePurchaseOrderProduct();

                if(property.getProductNo() != null && !"".equals(property.getProductNo())){
                    product.setProductNo(property.getProductNo());
                }
                if(property.getProductName() != null && !"".equals(property.getProductName())){
                    product.setProductName(property.getProductName());
                }
                if(property.getCategory() != null && !"".equals(property.getCategory())){
                    product.setCategory(property.getCategory());
                }
                if(property.getNum() != null && !"".equals(property.getNum())){
                    product.setNum(Integer.valueOf(property.getNum()));
                }
                if(property.getUnit() != null && !"".equals(property.getUnit())){
                    product.setUnit(property.getUnit());
                }
                if(property.getPricing() != null && !"".equals(property.getPricing())){
                    product.setPricing(new BigDecimal(property.getPricing()));
                }
                if(property.getDiscount() != null && !"".equals(property.getDiscount())){
                    product.setDiscount(property.getDiscount());
                }
                if(property.getPrice() != null && !"".equals(property.getPrice())){
                    product.setPrice(new BigDecimal(property.getPrice()));
                }
                if(property.getMoney() != null && !"".equals(property.getMoney())){
                    product.setMoney(new BigDecimal(property.getMoney()));
                }
                if(property.getOrderNo() != null && !"".equals(property.getOrderNo())){
                    product.setOrderNo(property.getOrderNo());
                }
                if(property.getProductSource() != null && !"".equals(property.getProductSource())){
                    product.setProductSource(property.getProductSource());
                }
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                product.setPurchaseOrderId(Long.valueOf(id));

                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iSalePurchaseOrderProductService.save(product);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        product.setId(property.getId());
                        iSalePurchaseOrderProductService.updateNotNull(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 保存 备注tableview数据
     */
    private void saveTableviewRemark(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = remark_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                RemarkProperty property = null;
                if(remark_table.getItems().get(i) != null){
                    property = (RemarkProperty)remark_table.getItems().get(i);
                }
                Remark product = new Remark();
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                product.setTypeid(3);
                product.setOtherid(Long.valueOf(id));
                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iRemarkService.save(product);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        product.setId(property.getId());
                        iRemarkService.updateNotNull(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 保存 报表tableview数据
     */
    private void saveTableviewReport(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = report_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                ReportRemarkProperty property = null;
                if(report_table.getItems().get(i) != null){
                    property = (ReportRemarkProperty)report_table.getItems().get(i);
                }
                ReportRemark product = new ReportRemark();
                if(property.getContent() != null && !"".equals(property.getContent())){
                    product.setContent(property.getContent());
                }
                product.setTypeid(2);
                product.setOtherid(Long.valueOf(id));

                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iReportRemarkService.save(product);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        product.setId(property.getId());
                        iReportRemarkService.updateNotNull(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 删除数据
     */
    @FXML
    public void delete(){
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            int rows = iSalePurchaseOrderService.delete(Long.valueOf(orderid.getText()));
            returnMsg(rows);
            firstData();
        }
    }

    /**
     * 下一个客户信息
     */
    @FXML
    public void nextData() {
        int max = iSalePurchaseOrderService.getSalePurchaseOrderCount();
        if (page < max - 1) {
            page += 1;
        }
        SalePurchaseOrder purchaseOrder = iSalePurchaseOrderService.getSalePurchaseOrderByPage(page, rows);
        setBasicVal(purchaseOrder);
    }

    /**
     * 上一个客户信息
     */
    @FXML
    public void prevData() {
        if (page > 0) {
            page -= 1;
        }
        SalePurchaseOrder purchaseOrder = iSalePurchaseOrderService.getSalePurchaseOrderByPage(page, rows);
        setBasicVal(purchaseOrder);
    }


    /**
     *  最后一条客户数据
     **/
    @FXML
    public void lastData() {
        page = iSalePurchaseOrderService.getSalePurchaseOrderCount() - 1;
        SalePurchaseOrder purchaseOrder = iSalePurchaseOrderService.getLastSalePurchaseOrder();
        setBasicVal(purchaseOrder);
    }

    /**
     * 第一条客户数据
     */
    @FXML
    public void firstData() {
        page = 0;
        SalePurchaseOrder purchaseOrder = iSalePurchaseOrderService.getFirstSalePurchaseOrder();
        setBasicVal(purchaseOrder);
    }

    /**
     * 设置值
     * @param order
     */
    public void setBasicVal(SalePurchaseOrder order){
        if(order == null){
            return;
        }
        clearControllerVal();
        if(order.getId() != null){
            orderid.setText(order.getId().toString());
        }
        if(order.getCreateDate() != null){
            order_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(order.getCustomerNo() != null){
            customer_no.setText(order.getCustomerNo());
        }
        if(order.getOrderNo() != null){
            order_no.setText(order.getOrderNo());
        }
        if(order.getCustomerNoStr() != null){
            customer_no_str.setText(order.getCustomerNoStr());
        }
        if(order.getCustomerOrderNo() != null){
            customer_order_no.setText(order.getCustomerOrderNo());
        }
        if(order.getTax() != null){
            tax.getSelectionModel().select(Integer.valueOf(order.getTax())+0);
        }
        if(order.getCurrency() != null){
            setComboBox(7L,currency,order.getCurrency()-1); //币别
        }
        if(order.getDiscount() != null){
            discount.setText(order.getDiscount());
        }
        if(order.getWarehouseOut() != null){
            setComboBox(29L,shipping_warehouse,order.getWarehouseOut()-1); //出货仓库
        }
        if(order.getSpecialOrder() != null){
            ch_special.setSelected(order.getSpecialOrder());
        }
        if(order.getWarehouseOutStr() != null){
            shipping_warehouse_str.setText(order.getWarehouseOutStr());
        }
        if(order.getCustomerCategory() != null){
            customer_category.setText(order.getCustomerCategory());
        }
        if(order.getCustomerName() != null){
            customer_name.setText(order.getCustomerName());
        }
        if(order.getReceivableBalance() != null){
            receivable_balance.setText(order.getReceivableBalance().toString());
        }
        if(order.getMadePeople() != null){
            made_people.setText(order.getMadePeople());
        }
        if(order.getPaymentMethod() != null){
            setComboBox(20L,payment,order.getPaymentMethod()-1); //收款方式
        }
        if(order.getAuditPeople() != null){
            reviewer.setText(order.getAuditPeople());
        }
        if(order.getAuditPeopleStr() != null){
            reviewer_str.setText(order.getAuditPeopleStr());
        }
        if(order.getLastUpdate() != null){
            last_udpate.setText(order.getLastUpdate());
        }
        if(order.getLastUpdateStr() != null){
            last_udpate_str.setText(order.getLastUpdateStr());
        }
        if(order.getContact() != null){
            contact.getSelectionModel().select(order.getContact());
        }
        if(order.getPhone() != null){
            phone.getSelectionModel().select(order.getPhone());
        }
        if(order.getInvoiceAddress() != null){
            invoice_address.setText(order.getInvoiceAddress());
        }
        if(order.getShippingAddress() != null){
            shipping_address.getSelectionModel().select(order.getShippingAddress());
        }
        if(order.getInvoiceAddress() != null){
            invoice_title.getSelectionModel().select(order.getInvoiceTitle());
        }
        if(order.getBusinessLeader() != null){
            business_leader.getSelectionModel().select(order.getBusinessLeader());
        }
        if(order.getFax() != null){
            fax.getSelectionModel().select(order.getFax());
        }
        if(order.getZip() != null){
            zip.setText(order.getZip());
        }
        if(order.getBusinessLeaderStr() != null){
            business_leader_str.setText(order.getBusinessLeaderStr());
        }
        if(order.getOrderAudit() != null){
            if(order.getOrderAudit()){
                setShiroControlYES();
                menu_update.setDisable(true);
            }else{
                setShiroControlNO();
            }
        }
        setTableviewVal();
        setTableviewRemarkVal();
        setTableviewReportVal();
    }

    /**
     * 报价单转单赋值
     * @param order
     */
    public void setBasicImportVal(SaleQuotation order){
        if(order == null){
            return;
        }
        clearControllerVal();
        shiro_cancel.setDisable(true);
        shiro_success.setDisable(false);
        if(order.getOfferNo() != null){
            customer_order_no.setText(order.getOfferNo());
        }
        if(order.getCreateDate() != null){
            order_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(order.getCustomerNo() != null){
            customer_no.setText(order.getCustomerNo());
        }
        order_no.setText("WS"+new Date().getTime()+getRandomone());
        if(order.getCustomerNoStr() != null){
            customer_no_str.setText(order.getCustomerNoStr());
        }
        if(order.getTax() != null){
            tax.getSelectionModel().select(order.getTax()+0);
        }
        if(order.getCurrency() != null){
            setComboBox(7L,currency,order.getCurrency()-1); //币别
        }
        if(order.getDiscount() != null){
            discount.setText(order.getDiscount());
        }
        if(order.getSpecialOffer() != null){
            ch_special.setSelected(order.getSpecialOffer());
        }
        if(order.getCustomerCategory() != null){
            customer_category.setText(order.getCustomerCategory());
        }
        if(order.getCustomerName() != null){
            customer_name.setText(order.getCustomerName());
        }
        if(order.getAmountReceivable() != null){
            receivable_balance.setText(order.getAmountReceivable());
        }
        if(order.getSinglePeople() != null){
            made_people.setText(order.getSinglePeople());
        }
        lastUpdatePeopel(last_udpate, last_udpate_str);

        if(order.getContact() != null){
            contact.getSelectionModel().select(order.getContact());
        }
        if(order.getTelephone() != null){
            phone.getSelectionModel().select(order.getTelephone());
        }
        if(order.getAddress() != null){
            shipping_address.getSelectionModel().select(order.getAddress());
        }

        if(order.getBusiness() != null){
            business_leader.getSelectionModel().select(order.getBusiness());
        }
        if(order.getFax() != null){
            fax.getSelectionModel().select(order.getFax());
        }
        if(order.getBusinessStr() != null){
            business_leader_str.setText(order.getBusinessStr());
        }
    }
    
    /**
     * 加载tableview数据
     */
    private void setTableviewVal(){
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<SalePurchaseOrderProduct> productList = iSalePurchaseOrderProductService.listPurchaseOrderProduct(orderid.getText());
            List<SalePurchaseOrderProductProperty> productPropertyList = new ArrayList<>();
            productList.forEach(p->{
                productPropertyList.add(new SalePurchaseOrderProductProperty(p.getId(),p.getPurchaseOrderId(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(),p.getDiscount(), p.getPrice(), p.getMoney(), p.getOrderNo() , p.getProductSource() ,p.getRemark()));
            });
            generalProductTab(FXCollections.observableArrayList(productPropertyList));
        }
    }

    /**
     * 给产品tableview加载数据
     * @param productPropertyList
     */
    public void generalProductTab(ObservableList<SalePurchaseOrderProductProperty> productPropertyList){
        product_table.setEditable(true);

        col_product_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_product_name.setCellFactory(column -> EditCell.createStringEditCell());
        col_category.setCellFactory(column -> EditCell.createStringEditCell());
        col_num.setCellFactory(column -> EditCell.createStringEditCell());
        col_unit.setCellFactory(column -> EditCell.createStringEditCell());
        col_pricing.setCellFactory(column -> EditCell.createStringEditCell());
        col_discount.setCellFactory(column -> EditCell.createStringEditCell());
        col_price.setCellFactory(column -> EditCell.createStringEditCell());
        col_money.setCellFactory(column -> EditCell.createStringEditCell());
        col_order_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_product_source.setCellFactory(column -> EditCell.createStringEditCell());
        col_remark.setCellFactory(column -> EditCell.createStringEditCell());

        final ObservableList<SalePurchaseOrderProductProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);

        col_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_product_no.setCellValueFactory(new PropertyValueFactory<>("productNo"));
        col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
        col_category.setCellValueFactory(new PropertyValueFactory("category"));
        col_num.setCellValueFactory(new PropertyValueFactory("num"));
        col_unit.setCellValueFactory(new PropertyValueFactory("unit"));
        col_pricing.setCellValueFactory(new PropertyValueFactory("pricing"));
        col_discount.setCellValueFactory(new PropertyValueFactory("discount"));
        col_price.setCellValueFactory(new PropertyValueFactory("price"));
        col_money.setCellValueFactory(new PropertyValueFactory("money"));
        col_order_no.setCellValueFactory(new PropertyValueFactory("orderNo"));
        col_product_source.setCellValueFactory(new PropertyValueFactory("productSource"));
        col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        product_table.setItems(dataProperty);
    }

    /**
     * 加载备注tableview数据
     */
    private void setTableviewRemarkVal(){
        remark_table.setEditable(true);

        col_remark_content.setCellFactory(column -> EditCell.createStringEditCell());

        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<Remark> productList = iRemarkService.listRemark(Long.valueOf(orderid.getText()),"3");
            List<RemarkProperty> productPropertyList = new ArrayList<>();
            productList.forEach(p->{
                productPropertyList.add(new RemarkProperty(p.getId(),p.getRemark()));
            });
            final ObservableList<RemarkProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
            col_remark_id.setCellValueFactory(new PropertyValueFactory("id"));
            col_remark_content.setCellValueFactory(new PropertyValueFactory("remark"));

            remark_table.setItems(dataProperty);
        }
    }

    /**
     * 加载报表tableview数据
     */
    private void setTableviewReportVal(){
        report_table.setEditable(true);

        col_report_content.setCellFactory(column -> EditCell.createStringEditCell());

        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<ReportRemark> productList = iReportRemarkService.listReportRemark(Long.valueOf(orderid.getText()),"2");
            List<ReportRemarkProperty> productPropertyList = new ArrayList<>();
            productList.forEach(p->{
                productPropertyList.add(new ReportRemarkProperty(p.getId(),p.getContent()));
            });
            final ObservableList<ReportRemarkProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
            col_report_id.setCellValueFactory(new PropertyValueFactory("id"));
            col_report_content.setCellValueFactory(new PropertyValueFactory("content"));

            report_table.setItems(dataProperty);
        }
    }
    /**
     * 清除空间上的值
     */
    @FXML
    private void clearControllerVal(){

        LocalDateTime localDate = LocalDateTime.now();

        order_date.setValue(localDate.toLocalDate());
        orderid.clear();
        order_date.setValue(localDate.toLocalDate());
        customer_no.clear();
        order_no.clear();
        customer_no_str.clear();
        customer_order_no.clear();
//        tax.getItems().clear();
        tax.getSelectionModel().selectFirst();
//        currency.getItems().clear();
        currency.getSelectionModel().selectFirst();
        discount.clear();
//        shipping_warehouse.getItems().clear();
        shipping_warehouse.getSelectionModel().selectFirst();

        // 特价单复选框
        ch_special.setSelected(false);
        shipping_warehouse_str.clear();

        customer_category.clear();
        customer_name.clear();
        receivable_balance.clear();
        made_people.clear();
//        payment.getItems().clear();
        payment.getSelectionModel().selectFirst();
        reviewer.clear();
        reviewer_str.clear();
        last_udpate.clear();
        last_udpate_str.clear();
        contact.getItems().clear();
        contact.getSelectionModel().select("");
        phone.getItems().clear();
        phone.getSelectionModel().select("");
        invoice_address.clear();
        shipping_address.getItems().clear();
        shipping_address.getSelectionModel().select("");
        invoice_title.getItems().clear();
        invoice_title.getSelectionModel().select("");
        business_leader.getItems().clear();
        business_leader.getSelectionModel().select("");
        fax.getItems().clear();
        fax.getSelectionModel().select("");
        zip.clear();
        business_leader_str.clear();

        // 合计
        total_num.clear();
        total_money.clear();
        total_loan.clear();
        tax_total.clear();

        product_table.setItems(null);
        remark_table.setItems(null);
        report_table.setItems(null);
    }

    /**
     * 设置所有控件禁用
     */
    public void setControllerDisable(){
        setControllerState(true);

    }

    /**
     * 设置所有控件可用
     */
    @FXML
    public void setControllerUse(){
        setControllerState(false);
    }

    /**
     *  设置控件状态（可用、不可用）
     * @param bool
     */
    private void setControllerState(boolean bool){
        if(bool){
            NumberUtil.changeStatus(writestate,0);
        }else{
            NumberUtil.changeStatus(writestate,2);
        }

        order_date.setDisable(bool);
         menu_clearAll.setDisable(bool);
         menu_commit.setDisable(bool);
//         menu_insert.setDisable(bool);
         menu_delete.setDisable(bool);


         orderid.setDisable(bool);
         order_date.setDisable(bool);
         customer_no.setDisable(bool);
         order_no.setDisable(bool);
         customer_no_str.setDisable(bool);
         customer_order_no.setDisable(bool);
         tax.setDisable(bool);
         currency.setDisable(bool);
         discount.setDisable(bool);
         shipping_warehouse.setDisable(bool);
        // 作废按钮
         btn_invalid.setDisable(bool);
        // 特价单复选框
         ch_special.setDisable(bool);
         shipping_warehouse_str.setDisable(bool);

         customer_category.setDisable(bool);
         customer_name.setDisable(bool);
         receivable_balance.setDisable(bool);
         made_people.setDisable(bool);
         payment.setDisable(bool);
         reviewer.setDisable(bool);
         reviewer_str.setDisable(bool);
         last_udpate.setDisable(bool);
         last_udpate_str.setDisable(bool);
         contact.setDisable(bool);
         phone.setDisable(bool);
         invoice_address.setDisable(bool);
         shipping_address.setDisable(bool);
         invoice_title.setDisable(bool);
         business_leader.setDisable(bool);
         fax.setDisable(bool);
         zip.setDisable(bool);
         business_leader_str.setDisable(bool);
         btn_copy.setDisable(bool);

         product_table.setDisable(bool);
         remark_table.setDisable(bool);
         report_table.setDisable(bool);

        // 合计
         total_num.setDisable(bool);
         total_money.setDisable(bool);
         total_loan.setDisable(bool);
         tax_total.setDisable(bool);
    }

    /**
     * 审核通过
     */
    private void setShiroControlYES(){
        // 有效单据验证
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("单据还暂未保存，无法审核！");
            return;
        }
        shiro_cancel.setDisable(false);
        shiro_success.setDisable(true);
        import_out.setDisable(false);

        menu_update.setDisable(true);
    }

    /**
     * 取消审核
     */
    private void setShiroControlNO(){
        // 有效单据验证
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("单据还暂未保存，无法审核！");
            return;
        }
        shiro_cancel.setDisable(true);
        shiro_success.setDisable(false);
        import_out.setDisable(true);
        menu_update.setDisable(false);
    }

    /**
     * 审核通过
     */
    @FXML
    public void setShiroControlSuccess(){
        setShiroControlYES();
        lastUpdatePeopel(reviewer, reviewer_str);
        lastUpdatePeopel(last_udpate, last_udpate_str);
        shiroUpdateData(true);
    }

    /**
     * 审核过后的数据提交
     */
    private void shiroUpdateData(Boolean bool){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SalePurchaseOrder saleGoods = iSalePurchaseOrderService.selectByKey(Long.valueOf(id));
            saleGoods.setOrderAudit(bool);
            saleGoods.setLastUpdate(last_udpate.getText());
            saleGoods.setLastUpdateStr(last_udpate_str.getText());
            saleGoods.setAuditPeople(reviewer.getText());
            saleGoods.setAuditPeopleStr(reviewer_str.getText());
            iSalePurchaseOrderService.updateNotNull(saleGoods);
        }
    }

    /**
     * 取消审核
     */
    @FXML
    public void setShiroControlCancel(){
        setShiroControlNO();
        lastUpdatePeopel(reviewer, reviewer_str);
        lastUpdatePeopel(last_udpate, last_udpate_str);
        shiroUpdateData(false);
    }

    /**
     * 导出 到订货单
     */
    @FXML
    public void importOut(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SalePurchaseOrder salePurchaseOrder = iSalePurchaseOrderService.selectByKey(Long.valueOf(id));
            StageManager.salePurchaseOrder = salePurchaseOrder;
            if(salePurchaseOrder != null && salePurchaseOrder.getOrderAudit()){
                List<SalePurchaseOrderProduct> saleOfferProductList = iSalePurchaseOrderProductService.listPurchaseOrderProduct(salePurchaseOrder.getId().toString());
                if(saleOfferProductList != null){
                    StageManager.PRODUCT_LIST = saleOfferProductList;
                }
                Pane pane1 = StageManager.ORDERCONTENT.get("SalePane");

                pane1.getChildren().setAll(loader.load("/fxml/sale/sale_slip.fxml"));
            }else{
                alert_informationDialog("该单据未审核无法进行导出!");
            }
        }

    }

    /**
     * 导入
     */
    @FXML
    public void importIn(){
        stage.setTitle("导入-报价单");
        Pane pane = new Pane();
        StageManager.CONTROLLER.put("PurchaseOrderControllerImport", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/quotation_import.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 打开订单查询窗口
     */
    @FXML
    public void OpenMiniQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();

        Pane pane = new Pane();
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("PurchaseOrderControllerNo", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/purchase_order_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO  打开窗口--现有客户查询
     * @Date 20:22 2018/8/15
     * @Param []
     **/
    @FXML
    public void OpenCurrentClientQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();

        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("PurchaseOrderController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

}
