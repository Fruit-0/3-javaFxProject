package com.yc.education.controller.sale;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.customer.Remark;
import com.yc.education.model.sale.*;
import com.yc.education.property.*;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.customer.IRemarkService;
import com.yc.education.service.sale.*;
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
 * 销售-销货单
 */
@Controller
public class SaleGoodsController extends BaseController implements Initializable {

    private static SpringFxmlLoader loader = new SpringFxmlLoader();
    Stage stage = new Stage();

    @Autowired
    IRemarkService iRemarkService;
    @Autowired
    IReportRemarkService iReportRemarkService;
    @Autowired
    ISaleGoodsService iSaleGoodsService;
    @Autowired
    ISaleGoodsProductService iSaleGoodsProductService;
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
    // 客户id
    @FXML public TextField customerid;

    @FXML DatePicker create_date;  // 制单日期
    @FXML public TextField customer_no; // 客户编号
    @FXML public TextField customer_no_str; // 客户编号描述
    @FXML TextField customer_order_no; // 客户订单号
    @FXML ComboBox tax; // 税别
    @FXML ComboBox currency; // 币别
    @FXML TextField discount; // 折扣
    @FXML TextField sale_no; // 销售单号


    @FXML Button btn_invalid; // 作废按钮
    @FXML CheckBox che_special_price;// 特价单复选框
    @FXML CheckBox che_special; // 特批单


    @FXML ComboBox transport_method; //运输方式
    @FXML ComboBox delivery_status; //发货状态
    @FXML TextField category; //客户类别
    @FXML TextField customer_name; //客户名称
    @FXML TextField receivable; //销售应收
    @FXML TextField made_people; //制单人
    @FXML ComboBox payment; //支付方式
    @FXML TextField transport_method_str; //快递公司
    @FXML TextField last_update; //最后修改人
    @FXML TextField last_update_str; //最后修改人描述
    @FXML TextField audit; // 审核人
    @FXML TextField audit_str; // 审核人描述
    @FXML ComboBox contact; // 联系人
    @FXML ComboBox phone; // 联系电话
    @FXML TextField invoice_address; //发票地址
    @FXML ComboBox shipping_address; //送货地址
    @FXML ComboBox invoice_title; //发票抬头
    @FXML ComboBox business_leader; //负责业务
    @FXML ComboBox fax; // 传真
    @FXML TextField zip; //邮政编码
    @FXML TextField business_leader_str; //负责业务描述
    @FXML ComboBox warehouse_out; // 出货仓库
    @FXML TextField warehouse_position_str; // 仓库位置描述
    @FXML TextField warehouse_out_str; // 出货仓库描述
    @FXML ComboBox warehouse_position; // 仓库位置
    @FXML TextField weight; // 重量




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
    @FXML TableColumn col_inventory; //列 仓库
    @FXML TableColumn col_floor; //列 楼层
    @FXML TableColumn col_remark; //列 备注



    @FXML TableView tab_remark; //备注及说明--备注表格
    @FXML TableColumn col_remark_id; //列id
    @FXML TableColumn col_remark_content; //列 内容

    @FXML TableView tab_report; //备注及说明--报表
    @FXML TableColumn col_report_id; // 列 id
    @FXML TableColumn col_report_content; //列 内容

    // 合计
    @FXML TextField total_num; // 合计数量
    @FXML TextField total_money; //合计金额
    @FXML TextField total_loan; //合计贷款
    @FXML TextField total_tax; //税款合计

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
        setComboBox(1L,delivery_status,0); //发货状态
        setComboBox(7L,currency,0); //币别
        setComboBox(20L,payment,0); //收款方式
        setComboBox(29L,warehouse_out,0); //出货仓库
        setComboBox(29L,warehouse_position,0); //仓库库位
        setComboBox(30L,transport_method,0); //运输方式

        // 报价产品行双击 调出现有产品窗口
        BaseController.clickEvent(product_table, data ->{
            tablePosition = product_table.getSelectionModel().getSelectedIndex();
            moreProductButtonClick();
        }, 2);

        SalePurchaseOrder order = StageManager.salePurchaseOrder;
        StageManager.salePurchaseOrder = null;
        if(order == null){
            firstData();
            setControllerDisable();
        }else{
            clearControllerVal();
            tax.getSelectionModel().select(0);
            if(order.getId() != null){
                orderid.setText(order.getId().toString());
            }
            if(order.getCreateDate() != null){
                create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
            }
            if(order.getCustomerNo() != null){
                customer_no.setText(order.getCustomerNo());
            }
            if(order.getOrderNo() != null){
                sale_no.setText(order.getOrderNo());
            }
            if(order.getCustomerNoStr() != null){
                customer_no_str.setText(order.getCustomerNoStr());
            }
            if(order.getCustomerOrderNo() != null){
                customer_order_no.setText(order.getCustomerOrderNo());
            }
            if(order.getTax() != null){
                try {
                    tax.getSelectionModel().select(Integer.valueOf(order.getTax())-1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(order.getCurrency() != null){
                setComboBox(7L,currency,order.getCurrency()-1); //币别
            }
            if(order.getDiscount() != null){
                discount.setText(order.getDiscount());
            }
            if(order.getWarehouseOut() != null){
                setComboBox(29L,warehouse_out,order.getWarehouseOut()-1); //出货仓库
            }
            if(order.getSpecialOrder() != null){
                che_special_price.setSelected(order.getSpecialOrder());
            }
            if(order.getWarehouseOutStr() != null){
                warehouse_out_str.setText(order.getWarehouseOutStr());
            }
            if(order.getCustomerCategory() != null){
                category.setText(order.getCustomerCategory());
            }
            if(order.getCustomerName() != null){
                customer_name.setText(order.getCustomerName());
            }
            if(order.getReceivableBalance() != null){
                receivable.setText(order.getReceivableBalance().toString());
            }
            if(order.getMadePeople() != null){
                made_people.setText(order.getMadePeople());
            }
            if(order.getPaymentMethod() != null){
                setComboBox(20L,payment,order.getPaymentMethod()-1); //收款方式
            }
//            if(order.getAuditPeople() != null){
//                audit.setText(order.getAuditPeople());
//            }
//            if(order.getAuditPeopleStr() != null){
//                audit_str.setText(order.getAuditPeopleStr());
//            }
//            if(order.getLastUpdate() != null){
//                last_update.setText(order.getLastUpdate());
//            }
//            if(order.getLastUpdateStr() != null){
//                last_update_str.setText(order.getLastUpdateStr());
//            }
            lastUpdatePeopel(last_update, last_update_str);
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
        pane.getChildren().setAll(loader.load("/fxml/sale/product_find_sales.fxml"));
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
        ObservableList<SaleGoodsProductProperty> list = product_table.getItems();
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
            SaleGoodsProductProperty property = (SaleGoodsProductProperty)product_table.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iSaleGoodsProductService.delete(property.getId());
                returnMsg(rows);
            }
            final ObservableList<SaleGoodsProductProperty> dataProperty = product_table.getItems();
            final ObservableList<SaleGoodsProductProperty> newDataProperty = FXCollections.observableArrayList();
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

        ObservableList<SaleGoodsProductProperty> list = product_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new SaleGoodsProductProperty("", "", "", 0, "", new BigDecimal("0.00"), "0", new BigDecimal("0.00"), new BigDecimal("0.00"), "", "", ""));
        product_table.setItems(list);
    }

    /**
     * 删除备注行
     */
    private void deleteRowOfRemark(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) tab_remark.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            RemarkProperty property = (RemarkProperty)tab_remark.getItems().get(index);
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

        ObservableList<RemarkProperty> list = tab_remark.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new RemarkProperty(""));
        tab_remark.setItems(list);
    }

    /**
     * 删除报表行
     */
    private void deleteRowOfReport(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) tab_report.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            ReportRemarkProperty property = (ReportRemarkProperty)tab_report.getItems().get(index);
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

        ObservableList<ReportRemarkProperty> list = tab_report.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new ReportRemarkProperty(""));
        tab_report.setItems(list);
    }

    /**
     * 新增订单
     */
    @FXML
    public void add(){
        clearControllerVal();
        create_date.setValue(localDate.toLocalDate());
        sale_no.setText("X"+new Date().getTime()+getRandomone());
    }

    /**
     * 保存数据
     */
    @FXML
    public void save(){
        SaleGoods order = new SaleGoods();
        if(create_date.getValue() != null){
            try {
                Date date = df.parse(create_date.getValue().toString());
                order.setCreateDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(warehouse_position.getSelectionModel().getSelectedItem() != null && !"".equals(warehouse_position.getSelectionModel().getSelectedItem())){
            order.setWarehousePosition(warehouse_position.getSelectionModel().getSelectedIndex());
        }
        if(warehouse_position_str.getText() != null && !"".equals(warehouse_position_str.getText())){
            order.setWarehousePositionStr(warehouse_position_str.getText());
        }
        if(customer_no.getText() != null && !"".equals(customer_no.getText())){
            order.setCustomerNo(customer_no.getText());
        }
        if(sale_no.getText() != null && !"".equals(sale_no.getText())){
            order.setSaleNo(sale_no.getText());
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
        if(warehouse_out_str.getText() != null && !"".equals(warehouse_out_str.getText())){
            order.setWarehouseOutStr(warehouse_out_str.getText());
        }
        if(category.getText() != null && !"".equals(category.getText())){
            order.setCustomerCategory(category.getText());
        }
        if(customer_name.getText() != null && !"".equals(customer_name.getText())){
            order.setCustomerName(customer_name.getText());
        }
        if(receivable.getText() != null && !"".equals(receivable.getText())){
            order.setSaleReceivable(new BigDecimal(receivable.getText()));
        }
        if(tax.getSelectionModel().getSelectedItem() != null && !"".equals(tax.getSelectionModel().getSelectedItem())){
            order.setTax(tax.getSelectionModel().getSelectedIndex()+1);
        }
        if(currency.getSelectionModel().getSelectedItem() != null && !"".equals(currency.getSelectionModel().getSelectedItem())){
            order.setCurrency(currency.getSelectionModel().getSelectedIndex()+1);
        }
        if(warehouse_out.getSelectionModel().getSelectedItem() != null && !"".equals(warehouse_out.getSelectionModel().getSelectedItem())){
            order.setWarehouseOut(warehouse_out.getSelectionModel().getSelectedIndex()+1);
        }
        if(warehouse_position.getSelectionModel().getSelectedItem() != null && !"".equals(warehouse_position.getSelectionModel().getSelectedItem())){
            order.setWarehousePosition(warehouse_position.getSelectionModel().getSelectedIndex()+1);
        }
        if(made_people.getText() != null && !"".equals(made_people.getText())){
            order.setMadePeople(made_people.getText());
        }
        if(audit.getText() != null && !"".equals(audit.getText())){
            order.setAudit(audit.getText());
        }
        if(audit_str.getText() != null && !"".equals(audit_str.getText())){
            order.setAuditStr(audit_str.getText());
        }
        if(last_update.getText() != null && !"".equals(last_update.getText())){
            order.setLastUpdate(last_update.getText());
        }
        if(last_update_str.getText() != null && !"".equals(last_update_str.getText())){
            order.setLastUpdateStr(last_update_str.getText());
        }
        if(payment.getSelectionModel().getSelectedItem() != null && !"".equals(payment.getSelectionModel().getSelectedItem())){
            order.setPayment(payment.getSelectionModel().getSelectedIndex()+1);
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
        if(transport_method.getSelectionModel().getSelectedItem() != null && !"".equals(transport_method.getSelectionModel().getSelectedItem())){
            order.setCarryMethod(transport_method.getSelectionModel().getSelectedIndex()-1);
        }
        if(transport_method_str.getText() != null && !"".equals(transport_method_str.getText())){
            order.setCarryMethodStr(transport_method_str.getText());
        }
        if(weight.getText() != null && !"".equals(weight.getText())){
            order.setWeight(Double.valueOf(weight.getText()));
        }
        if(delivery_status.getSelectionModel().getSelectedItem() != null && !"".equals(delivery_status.getSelectionModel().getSelectedItem())){
            order.setDeliveryStatus(delivery_status.getSelectionModel().getSelectedIndex()-1);
        }
        order.setSpecialPriceOrder(che_special_price.selectedProperty().getValue());
        order.setSpecialOrder(che_special.selectedProperty().getValue());

        if(order.getId() != null){
            // 修改
            int rows = iSaleGoodsService.updateNotNull(order);
            returnMsg(rows);

            setControllerDisable();
        }else{
            order.setAddtime(new Date());
            // 保存
            int rows = iSaleGoodsService.save(order);
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
                SaleGoodsProductProperty property = null;
                if(product_table.getItems().get(i) != null){
                    property = (SaleGoodsProductProperty)product_table.getItems().get(i);
                }
                SaleGoodsProduct order = new SaleGoodsProduct();

                if(property.getProductNo() != null && !"".equals(property.getProductNo())){
                    order.setProductNo(property.getProductNo());
                }
                if(property.getProductName() != null && !"".equals(property.getProductName())){
                    order.setProductName(property.getProductName());
                }
                if(property.getCategory() != null && !"".equals(property.getCategory())){
                    order.setCategory(property.getCategory());
                }
                if(property.getNum() != null && !"".equals(property.getNum())){
                    order.setNum(Integer.valueOf(property.getNum()));
                }
                if(property.getUnit() != null && !"".equals(property.getUnit())){
                    order.setUnit(property.getUnit());
                }
                if(property.getPricing() != null && !"".equals(property.getPricing())){
                    order.setPricing(new BigDecimal(property.getPricing()));
                }
                if(property.getDiscount() != null && !"".equals(property.getDiscount())){
                    order.setDiscount(Double.valueOf(property.getDiscount()));
                }
                if(property.getPrice() != null && !"".equals(property.getPrice())){
                    order.setPrice(new BigDecimal(property.getPrice()));
                }
                if(property.getMoney() != null && !"".equals(property.getMoney())){
                    order.setMoney(new BigDecimal(property.getMoney()));
                }
                if(property.getWarehousePosition() != null && !"".equals(property.getWarehousePosition())){
                    order.setWarehousePosition(property.getWarehousePosition());
                }
                if(property.getFloor() != null && !"".equals(property.getFloor())){
                    order.setFloor(property.getFloor());
                }
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    order.setRemark(property.getRemark());
                }
                order.setSaleGoodsId(Long.valueOf(id));

                if(property.getId() == null){
                    try {
                        order.setAddtime(new Date());
                        iSaleGoodsProductService.save(order);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        order.setId(property.getId());
                        iSaleGoodsProductService.updateNotNull(order);
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
            int tableSize = tab_remark.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                RemarkProperty property = null;
                if(tab_remark.getItems().get(i) != null){
                    property = (RemarkProperty)tab_remark.getItems().get(i);
                }
                Remark product = new Remark();
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                // 4：销货单
                product.setTypeid(4);
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
            int tableSize = tab_report.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                ReportRemarkProperty property = null;
                if(tab_report.getItems().get(i) != null){
                    property = (ReportRemarkProperty)tab_report.getItems().get(i);
                }
                ReportRemark product = new ReportRemark();
                if(property.getContent() != null && !"".equals(property.getContent())){
                    product.setContent(property.getContent());
                }
                // 3：销货单
                product.setTypeid(3);
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
            int rows = iSaleGoodsService.delete(Long.valueOf(orderid.getText()));
            returnMsg(rows);
            firstData();
        }
    }

    /**
     * 下一个客户信息
     */
    @FXML
    public void nextData() {
        int max = iSaleGoodsService.getSaleGoodsCount();
        if (page < max - 1) {
            page += 1;
        }
        SaleGoods saleGoods = iSaleGoodsService.getSaleGoodsByPage(page, rows);
        setBasicVal(saleGoods);
    }

    /**
     * 上一个客户信息
     */
    @FXML
    public void prevData() {
        if (page > 0) {
            page -= 1;
        }
        SaleGoods saleGoods = iSaleGoodsService.getSaleGoodsByPage(page, rows);
        setBasicVal(saleGoods);
    }


    /**
     *  最后一条客户数据
     **/
    @FXML
    public void lastData() {
        page = iSaleGoodsService.getSaleGoodsCount() - 1;
        SaleGoods saleGoods = iSaleGoodsService.getLastSaleGoods();
        setBasicVal(saleGoods);
    }

    /**
     * 第一条客户数据
     */
    @FXML
    public void firstData() {
        page = 0;
        SaleGoods saleGoods = iSaleGoodsService.getFirstSaleGoods();
        setBasicVal(saleGoods);
    }

    /**
     * 加载采购单中的数据
     * @param order
     */
    public void setBasicImportVal(SalePurchaseOrder order){
        if(order == null){
            return;
        }
        clearControllerVal();
        shiro_cancel.setDisable(true);
        shiro_success.setDisable(false);
        menu_update.setDisable(false);
        if(order.getCreateDate() != null){
            create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(order.getCustomerNo() != null){
            customer_no.setText(order.getCustomerNo());
        }
        sale_no.setText("WS"+new Date().getTime()+getRandomone());
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
            setComboBox(29L,warehouse_out,order.getWarehouseOut()-1); //出货仓库
        }
        if(order.getWarehouseOut() != null){
            setComboBox(29L,warehouse_position,order.getWarehouseOut()-1); //仓库库位
        }
        if(order.getSpecialOrder() != null){
            che_special.setSelected(order.getSpecialOrder());
        }
        if(order.getSpecialOrder() != null){
            che_special_price.setSelected(order.getSpecialOrder());
        }
        if(order.getWarehouseOutStr() != null){
            warehouse_out_str.setText(order.getWarehouseOutStr());
        }
        if(order.getCustomerCategory() != null){
            category.setText(order.getCustomerCategory());
        }
        if(order.getCustomerName() != null){
            customer_name.setText(order.getCustomerName());
        }
        if(order.getReceivableBalance() != null){
            receivable.setText(order.getReceivableBalance().toString());
        }
        if(order.getMadePeople() != null){
            made_people.setText(order.getMadePeople());
        }
        if(order.getPaymentMethod() != null){
            setComboBox(20L,payment,order.getPaymentMethod()-1); //收款方式
        }
        lastUpdatePeopel(last_update, last_update_str);

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
    }

    /**
     * 设置值
     * @param order
     */
    public void setBasicVal(SaleGoods order){
        if(order == null){
            return;
        }
        clearControllerVal();
        if(order.getWarehousePosition() != null){
            warehouse_position.getSelectionModel().select(order.getWarehousePosition()-1);
        }
        if(order.getWarehousePositionStr() != null){
            warehouse_position_str.setText(order.getWarehousePositionStr());
        }
        if(order.getId() != null){
            orderid.setText(order.getId().toString());
        }
        if(order.getCreateDate() != null){
            create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(order.getCustomerNo() != null){
            customer_no.setText(order.getCustomerNo());
        }
        if(order.getSaleNo() != null){
            sale_no.setText(order.getSaleNo());
        }
        if(order.getCustomerNoStr() != null){
            customer_no_str.setText(order.getCustomerNoStr());
        }
        if(order.getCustomerOrderNo() != null){
            customer_order_no.setText(order.getCustomerOrderNo());
        }
        if(order.getTax() != null){
            tax.getSelectionModel().select(order.getTax()-1);
        }
        if(order.getCurrency() != null){
            setComboBox(7L,currency,order.getCurrency()-1); //币别
        }
        if(order.getDiscount() != null){
            discount.setText(order.getDiscount());
        }
        if(order.getWarehouseOut() != null){
            setComboBox(29L,warehouse_out,order.getWarehouseOut()-1); //出货仓库
        }
        if(order.getSpecialOrder() != null){
            che_special.setSelected(order.getSpecialOrder());
        }
        if(order.getSpecialPriceOrder() != null){
            che_special_price.setSelected(order.getSpecialPriceOrder());
        }
        if(order.getWarehouseOutStr() != null){
            warehouse_out_str.setText(order.getWarehouseOutStr());
        }
        if(order.getCustomerCategory() != null){
            category.setText(order.getCustomerCategory());
        }
        if(order.getCustomerName() != null){
            customer_name.setText(order.getCustomerName());
        }
        if(order.getSaleReceivable() != null){
            receivable.setText(order.getSaleReceivable().toString());
        }
        if(order.getMadePeople() != null){
            made_people.setText(order.getMadePeople());
        }
        if(order.getPayment() != null){
            setComboBox(20L,payment,order.getPayment()-1); //收款方式
        }
        if(order.getAudit() != null){
            audit.setText(order.getAudit());
        }
        if(order.getAuditStr() != null){
            audit_str.setText(order.getAuditStr());
        }
        if(order.getLastUpdate() != null){
            last_update.setText(order.getLastUpdate());
        }
        if(order.getLastUpdateStr() != null){
            last_update_str.setText(order.getLastUpdateStr());
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
        if(order.getWeight() != null){
            weight.setText(order.getWeight().toString());
        }
        if(order.getCarryMethod() != null){
            transport_method.getSelectionModel().select(order.getCarryMethod()-1);
        }
        if(order.getCarryMethodStr() != null){
            transport_method_str.setText(order.getCarryMethodStr());
        }
        if(order.getOrderAudit() != null){
            if(order.getOrderAudit()){
                setShiroControlYES();
                menu_update.setDisable(true);
            }else{
                setShiroControlNO();
                menu_update.setDisable(false);
            }
        }else{
            setShiroControlNO();
            menu_update.setDisable(false);
        }
        setTableviewVal();
        setTableviewRemarkVal();
        setTableviewReportVal();
    }

    /**
     * 加载tableview数据
     */
    private void setTableviewVal(){
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<SaleGoodsProduct> productList = iSaleGoodsProductService.listSaleGoodsProduct(orderid.getText());
            List<SaleGoodsProductProperty> productPropertyList = new ArrayList<>();
            if(productList == null) return;
            productList.forEach(p->{
                productPropertyList.add(new SaleGoodsProductProperty(p.getId(),p.getSaleGoodsId(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(),p.getDiscount().toString(), p.getPrice(), p.getMoney(), p.getWarehousePosition() , p.getFloor() ,p.getRemark()));
            });
            generalProductTab(FXCollections.observableArrayList(productPropertyList));
        }
    }

    /**
     * 给产品表格加载数据
     * @param productPropertyList
     */
    public void generalProductTab(ObservableList<SaleGoodsProductProperty> productPropertyList){
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
        col_inventory.setCellFactory(column -> EditCell.createStringEditCell());
        col_floor.setCellFactory(column -> EditCell.createStringEditCell());
        col_remark.setCellFactory(column -> EditCell.createStringEditCell());
        final ObservableList<SaleGoodsProductProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
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
        col_inventory.setCellValueFactory(new PropertyValueFactory("warehousePosition"));
        col_floor.setCellValueFactory(new PropertyValueFactory("floor"));
        col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        product_table.setItems(dataProperty);
    }

    /**
     * 加载备注tableview数据
     */
    private void setTableviewRemarkVal(){
        tab_remark.setEditable(true);

        col_remark_content.setCellFactory(column -> EditCell.createStringEditCell());

        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<Remark> productList = iRemarkService.listRemark(Long.valueOf(orderid.getText()),"4");
            List<RemarkProperty> productPropertyList = new ArrayList<>();
            productList.forEach(p->{
                productPropertyList.add(new RemarkProperty(p.getId(),p.getRemark()));
            });
            final ObservableList<RemarkProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
            col_remark_id.setCellValueFactory(new PropertyValueFactory("id"));
            col_remark_content.setCellValueFactory(new PropertyValueFactory("remark"));

            tab_remark.setItems(dataProperty);
        }
    }

    /**
     * 加载报表tableview数据
     */
    private void setTableviewReportVal(){
        tab_report.setEditable(true);

        col_report_content.setCellFactory(column -> EditCell.createStringEditCell());

        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<ReportRemark> productList = iReportRemarkService.listReportRemark(Long.valueOf(orderid.getText()),"3");
            List<ReportRemarkProperty> productPropertyList = new ArrayList<>();
            productList.forEach(p->{
                productPropertyList.add(new ReportRemarkProperty(p.getId(),p.getContent()));
            });
            final ObservableList<ReportRemarkProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
            col_report_id.setCellValueFactory(new PropertyValueFactory("id"));
            col_report_content.setCellValueFactory(new PropertyValueFactory("content"));

            tab_report.setItems(dataProperty);
        }
    }
    /**
     * 清除空间上的值
     */
    @FXML
    private void clearControllerVal(){

        LocalDateTime localDate = LocalDateTime.now();

        create_date.setValue(localDate.toLocalDate());
        orderid.clear();
        customer_no.clear();
        sale_no.clear();
        customer_no_str.clear();
        customer_order_no.clear();
        tax.getSelectionModel().selectFirst();
        currency.getSelectionModel().selectFirst();
        discount.clear();
        warehouse_out.getSelectionModel().selectFirst();
        warehouse_out_str.clear();
        warehouse_position.getSelectionModel().selectFirst();
        warehouse_position_str.clear();

        // 特批单复选框
        che_special.setSelected(false);
        // 特价单复选框
        che_special_price.setSelected(false);

        category.clear();
        customer_name.clear();
        receivable.clear();
        made_people.clear();
        payment.getSelectionModel().selectFirst();
        audit.clear();
        audit_str.clear();
        last_update.clear();
        last_update_str.clear();
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
        transport_method.getSelectionModel().selectFirst();
        transport_method_str.clear();
        weight.clear();
        delivery_status.getSelectionModel().selectFirst();

        // 合计
        total_num.clear();
        total_money.clear();
        total_loan.clear();
        total_tax.clear();

        product_table.setItems(null);
        tab_remark.setItems(null);
        tab_report.setItems(null);
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
         create_date.setDisable(bool);
         menu_clearAll.setDisable(bool);
         menu_commit.setDisable(bool);
//         menu_insert.setDisable(bool);
         menu_delete.setDisable(bool);

         orderid.setDisable(bool);
         customer_no.setDisable(bool);
        sale_no.setDisable(bool);
         customer_no_str.setDisable(bool);
         customer_order_no.setDisable(bool);
         tax.setDisable(bool);
         currency.setDisable(bool);
         discount.setDisable(bool);
         warehouse_out.setDisable(bool);
        // 作废按钮
         btn_invalid.setDisable(bool);
        // 特价单复选框
        che_special_price.setDisable(bool);
        che_special.setDisable(bool);
         warehouse_out_str.setDisable(bool);
         warehouse_position.setDisable(bool);
         warehouse_position_str.setDisable(bool);

         delivery_status.setDisable(bool);
         weight.setDisable(bool);
         transport_method.setDisable(bool);
         transport_method_str.setDisable(bool);
         category.setDisable(bool);
         customer_name.setDisable(bool);
         receivable.setDisable(bool);
         made_people.setDisable(bool);
         payment.setDisable(bool);
         audit.setDisable(bool);
         audit_str.setDisable(bool);
         last_update.setDisable(bool);
         last_update_str.setDisable(bool);
         contact.setDisable(bool);
         phone.setDisable(bool);
         invoice_address.setDisable(bool);
         shipping_address.setDisable(bool);
         invoice_title.setDisable(bool);
         business_leader.setDisable(bool);
         fax.setDisable(bool);
         zip.setDisable(bool);
         business_leader_str.setDisable(bool);
         btn_invalid.setDisable(bool);


         product_table.setDisable(bool);
         tab_remark.setDisable(bool);
         tab_report.setDisable(bool);

        // 合计
         total_num.setDisable(bool);
         total_money.setDisable(bool);
         total_loan.setDisable(bool);
         total_tax.setDisable(bool);
    }

    /**
     * 审核通过
     */
    private void setShiroControlYES(){
        shiro_cancel.setDisable(false);
        shiro_success.setDisable(true);
        import_out.setDisable(false);
    }

    /**
     * 取消审核
     */
    private void setShiroControlNO(){
        shiro_cancel.setDisable(true);
        shiro_success.setDisable(false);
        import_out.setDisable(true);
    }

    /**
     * 审核通过
     */
    @FXML
    public void setShiroControlSuccess(){
        // 有效单据验证
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("单据还暂未保存，无法审核！");
            return;
        }
        setShiroControlYES();
        lastUpdatePeopel(audit, audit_str);
        lastUpdatePeopel(last_update, last_update_str);
        shiroUpdateData(true);

        menu_update.setDisable(true);
    }

    /**
     * 审核过后的数据提交
     */
    private void shiroUpdateData(Boolean bool){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SaleGoods saleGoods = iSaleGoodsService.selectByKey(Long.valueOf(id));
            saleGoods.setOrderAudit(bool);
            saleGoods.setLastUpdate(last_update.getText());
            saleGoods.setLastUpdateStr(last_update_str.getText());
            saleGoods.setAudit(audit.getText());
            saleGoods.setAuditStr(audit_str.getText());
            iSaleGoodsService.updateNotNull(saleGoods);
        }
    }

    /**
     * 取消审核
     */
    @FXML
    public void setShiroControlCancel(){
        // 有效单据验证
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("单据还暂未保存，无法审核！");
            return;
        }
        setShiroControlNO();
        lastUpdatePeopel(audit, audit_str);
        lastUpdatePeopel(last_update, last_update_str);
        shiroUpdateData(false);

        menu_update.setDisable(false);
    }

    /**
     * 导出 到订货单
     */
    @FXML
    public void importOut(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SaleGoods saleGoods = iSaleGoodsService.selectByKey(Long.valueOf(id));
            StageManager.saleGoods = saleGoods;
            if(saleGoods != null && saleGoods.getOrderAudit()){
                List<SaleGoodsProduct> saleGoodsProductList = iSaleGoodsProductService.listSaleGoodsProduct(saleGoods.getId().toString());
                if(saleGoodsProductList != null){
                    StageManager.PRODUCT_LIST = saleGoodsProductList;
                }
                Pane pane1 = StageManager.ORDERCONTENT.get("SalePane");

                pane1.getChildren().setAll(loader.load("/fxml/sale/sale_return.fxml"));
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

         SpringFxmlLoader loader = new SpringFxmlLoader();
        stage.setTitle("导入-订货单");
        Pane pane = new Pane();
        StageManager.CONTROLLER.put("SaleGoodsControllerImport", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/purchase_order_import.fxml"));
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
        StageManager.CONTROLLER.put("SaleGoodsControllerNo", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/sale_goods_query_mini.fxml"));
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
        StageManager.CONTROLLER.put("SaleGoodsController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

}
