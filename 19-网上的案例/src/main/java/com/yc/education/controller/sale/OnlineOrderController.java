package com.yc.education.controller.sale;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.customer.Remark;
import com.yc.education.model.sale.SaleOnlineOrder;
import com.yc.education.model.sale.SaleOnlineOrderProduct;
import com.yc.education.property.SaleOnlineOrderProductProperty;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.sale.ISaleOnlineOrderProductService;
import com.yc.education.service.sale.ISaleOnlineOrderService;
import com.yc.education.util.DateUtils;
import com.yc.education.util.EditCell;
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
 * 销售-网上订单
 */
@Controller
public class OnlineOrderController extends BaseController implements Initializable {

    @Autowired
    ISaleOnlineOrderService iSaleOnlineOrderService;
    @Autowired
    ISaleOnlineOrderProductService iSaleOnlineOrderProductService;

    // 菜单控件
    @FXML VBox menu_clearAll;
    @FXML VBox menu_commit;
    @FXML VBox menu_insert;
    @FXML VBox menu_update;
    @FXML VBox menu_delete;

    // 客户id
    @FXML TextField customerid;
    // 订单id
    @FXML public TextField orderid;

    @FXML DatePicker order_date;
    @FXML public TextField customer_no;
    @FXML TextField order_remark;
    @FXML TextField order_no;
    @FXML public TextField customer_no_str;
    // 作废按钮
    @FXML Button btn_invalid;
    @FXML public TextField order_people;
    @FXML TextField contact;
    @FXML TextField zip;
    @FXML TextField invoice_title;
    @FXML TextField invoice_address;
    @FXML TextField audit;
    @FXML TextField update_last;
    @FXML TextField invalid_people;
    @FXML TextField base_remark;
    @FXML TextField phone;
    @FXML TextField fax;
    @FXML ComboBox delivery_address;
    @FXML TextField audit_str;
    @FXML TextField update_last_str;
    @FXML TextField invalid_people_str;

    @FXML TableView tableview;
    @FXML TableColumn col_id;
    @FXML TableColumn col_product_no;
    @FXML TableColumn col_product_name;
    @FXML TableColumn col_category;
    @FXML TableColumn col_number;
    @FXML TableColumn col_unit;
    @FXML TableColumn col_price;
    @FXML TableColumn col_money;
    @FXML TableColumn col_usable_num;
    @FXML TableColumn col_stock;
    @FXML TableColumn col_remark;

    /***************************************** 弹出窗口-产品 ********************************************/
    @Autowired
    private ProductBasicService productBasicService; //产品 Service
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

    SpringFxmlLoader loader = new SpringFxmlLoader();
    private Stage stage = new Stage();
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
        firstData();
        setControllerDisable();

        // 报价产品行双击 调出现有产品窗口
        BaseController.clickEvent(tableview, data ->{
            tablePosition = tableview.getSelectionModel().getSelectedIndex();
            moreProductButtonClick();
        }, 2);
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
        pane.getChildren().setAll(loader.load("/fxml/sale/product_find_online.fxml"));
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
        ObservableList<SaleOnlineOrderProductProperty> list = tableview.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        for (int i = 0; i < list.size(); i++) {
            if(tablePosition == i){
                list.get(i).setProductName(productBasic.getProname());
                list.get(i).setProductNo(productBasic.getIsnum());
            }
        }
        tableview.setItems(FXCollections.observableArrayList(list));
        coseWin();
    }

    public void coseWin(){
        stage.close();
        tablePosition = 0;
    }

    /**
     * 报表备注view
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfReportRemarkTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addProductRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfProduct();
            setTableviewVal();
        }
    }

    /**
     * 删除产品行
     */
    private void deleteRowOfProduct(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) tableview.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            SaleOnlineOrderProductProperty property = (SaleOnlineOrderProductProperty)tableview.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iSaleOnlineOrderProductService.delete(property.getId());
                returnMsg(rows);
            }
        }catch (Exception e){
            alert_informationDialog("请选择需要删除的行！");
            e.printStackTrace();
        }
    }

    /**
     * 添加产品行
     */
    public void addProductRow() {

        ObservableList<SaleOnlineOrderProductProperty> list = tableview.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new SaleOnlineOrderProductProperty( "", "", "", "", 0, "", new BigDecimal("0.00"), new BigDecimal("0.00"), 0, ""));
        tableview.setItems(list);
    }

    /**
     * 新增订单
     */
    @FXML
    public void add(){
        clearControllerVal();
        order_date.setValue(localDate.toLocalDate());
        order_no.setText("WS"+new Date().getTime()+getRandomone());
    }

    /**
     * 保存数据
     */
    @FXML
    public void save(){
        SaleOnlineOrder onlineOrder = new SaleOnlineOrder();
        if(order_date.getValue() != null){
            try {
                Date date = df.parse(order_date.getValue().toString());
                onlineOrder.setOrderDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(customer_no.getText() != null && !"".equals(customer_no.getText())){
            onlineOrder.setCustomerNo(customer_no.getText());
        }
        if(order_remark.getText() != null && !"".equals(order_remark.getText())){
            onlineOrder.setOrderRemark(order_remark.getText());
        }
        if(order_no.getText() != null && !"".equals(order_no.getText())){
            onlineOrder.setOrderNo(order_no.getText());
        }
        if(customer_no_str.getText() != null && !"".equals(customer_no_str.getText())){
            onlineOrder.setCustomerNoStr(customer_no_str.getText());
        }
        if(order_people.getText() != null && !"".equals(order_people.getText())){
            onlineOrder.setOrderPeople(order_people.getText());
        }
        if(contact.getText() != null && !"".equals(contact.getText())){
            onlineOrder.setContact(contact.getText());
        }
        if(zip.getText() != null && !"".equals(zip.getText())){
            onlineOrder.setZip(zip.getText());
        }
        if(invoice_title.getText() != null && !"".equals(invoice_title.getText())){
            onlineOrder.setInvoiceTitle(invoice_title.getText());
        }
        if(invoice_address.getText() != null && !"".equals(invoice_address.getText())){
            onlineOrder.setInvoiceAddress(invoice_address.getText());
        }
        if(audit.getText() != null && !"".equals(audit.getText())){
            onlineOrder.setAudit(audit.getText());
        }
        if(update_last.getText() != null && !"".equals(update_last.getText())){
            onlineOrder.setUpdateLast(update_last.getText());
        }
        if(invalid_people.getText() != null && !"".equals(invalid_people.getText())){
            onlineOrder.setInvalidPeople(invalid_people.getText());
        }
        if(base_remark.getText() != null && !"".equals(base_remark.getText())){
            onlineOrder.setBaseRemark(base_remark.getText());
        }
        if(phone.getText() != null && !"".equals(phone.getText())){
            onlineOrder.setPhone(phone.getText());
        }
        if(fax.getText() != null && !"".equals(fax.getText())){
            onlineOrder.setFax(fax.getText());
        }
        if(delivery_address.getSelectionModel().getSelectedItem() != null && !"".equals(delivery_address.getSelectionModel().getSelectedItem())){
            onlineOrder.setDeliveryAddress(delivery_address.getValue().toString());
        }
        if(audit_str.getText() != null && !"".equals(audit_str.getText())){
            onlineOrder.setAuditStr(audit_str.getText());
        }
        if(update_last_str.getText() != null && !"".equals(update_last_str.getText())){
            onlineOrder.setUpdateLastStr(update_last_str.getText());
        }
        if(invalid_people_str.getText() != null && !"".equals(invalid_people_str.getText())){
            onlineOrder.setInvalidPeopleStr(invalid_people_str.getText());
        }
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            onlineOrder.setId(Long.valueOf(orderid.getText()));
        }
        // 操作tableview中的数据
        saveTableviewProduct();
        if(onlineOrder.getId() != null){
            // 修改
            int rows = iSaleOnlineOrderService.updateNotNull(onlineOrder);
            returnMsg(rows);

            setControllerDisable();
        }else{
            // 保存
            int rows = iSaleOnlineOrderService.save(onlineOrder);
            returnMsg(rows);
            setBasicVal(onlineOrder);
            setControllerDisable();
        }

    }

    /**
     * 保存订货产品tableview数据
     */
    private void saveTableviewProduct(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = tableview.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                SaleOnlineOrderProductProperty property = null;
                if(tableview.getItems().get(i) != null){
                    property = (SaleOnlineOrderProductProperty)tableview.getItems().get(i);
                }
                SaleOnlineOrderProduct product = new SaleOnlineOrderProduct();

                if(property.getProductNo() != null){
                    product.setProductNo(property.getProductNo());
                }
                if(property.getProductName() != null){
                    product.setProductName(property.getProductName());
                }
                if(property.getCategory() != null){
                    product.setCategory(property.getCategory());
                }
                if(property.getNum() != null){
                    product.setNum(Integer.valueOf(property.getNum()));
                }
                if(property.getUnit() != null){
                    product.setUnit(property.getUnit());
                }
                if(property.getPrice() != null){
                    product.setPrice(new BigDecimal(property.getPrice()));
                }
                if(property.getMoney() != null){
                    product.setMoney(new BigDecimal(property.getMoney()));
                }
                if(property.getUsableNum() != null){
                    product.setUsableNum(Integer.valueOf(property.getUsableNum()));
                }
                if(property.getIfstock() != null && !"".equals(property.getIfstock())){
                    if("是".equals(property.getIfstock())){
                        product.setIfstock(true);
                    }else{
                        product.setIfstock(false);
                    }
                }
                if(property.getRemark() != null){
                    product.setRemark(property.getRemark());
                }
                product.setOnlineOrderId(Long.valueOf(id));
                product.setAddtime(new Date());
                if(property.getId() == null){
                    try {
                        iSaleOnlineOrderProductService.save(product);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    product.setId(property.getId());
                    try {
                        iSaleOnlineOrderProductService.updateNotNull(product);
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
            int rows = iSaleOnlineOrderService.delete(Long.valueOf(orderid.getText()));
            returnMsg(rows);
            firstData();
        }
    }

    /**
     * 下一个客户信息
     */
    @FXML
    public void nextData() {
        int max = iSaleOnlineOrderService.getSaleOnlineOrderCount();
        if (page < max - 1) {
            page += 1;
        }
        SaleOnlineOrder onlineOrder = iSaleOnlineOrderService.getSaleOnlineOrderByPage(page, rows);
        setBasicVal(onlineOrder);
    }

    /**
     * 上一个客户信息
     */
    @FXML
    public void prevData() {
        if (page > 0) {
            page -= 1;
        }
        SaleOnlineOrder onlineOrder = iSaleOnlineOrderService.getSaleOnlineOrderByPage(page, rows);
        setBasicVal(onlineOrder);
    }


    /**
     *  最后一条客户数据
     **/
    @FXML
    public void lastData() {
        page = iSaleOnlineOrderService.getSaleOnlineOrderCount() - 1;
        SaleOnlineOrder onlineOrder = iSaleOnlineOrderService.getLastSaleOnlineOrder();
        setBasicVal(onlineOrder);
    }

    /**
     * 第一条客户数据
     */
    @FXML
    public void firstData() {
        page = 0;
        SaleOnlineOrder onlineOrder = iSaleOnlineOrderService.getFirstSaleOnlineOrder();
        setBasicVal(onlineOrder);
    }
    
    /**
     * 设置值
     * @param onlineOrder
     */
    public void setBasicVal(SaleOnlineOrder onlineOrder){
        if(onlineOrder == null){
            return;
        }
        clearControllerVal();
        if(onlineOrder.getId() != null){
            orderid.setText(onlineOrder.getId().toString());
        }
        if(onlineOrder.getOrderDate() != null){
            order_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(onlineOrder.getOrderDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(onlineOrder.getCustomerNo() != null){
            customer_no.setText(onlineOrder.getCustomerNo());
        }
        if(onlineOrder.getOrderRemark() != null){
            order_remark.setText(onlineOrder.getOrderRemark());
        }
        if(onlineOrder.getOrderNo() != null){
            order_no.setText(onlineOrder.getOrderNo());
        }
        if(onlineOrder.getCustomerNoStr() != null){
            customer_no_str.setText(onlineOrder.getCustomerNoStr());
        }
        if(onlineOrder.getOrderPeople() != null){
            order_people.setText(onlineOrder.getOrderPeople());
        }
        if(onlineOrder.getContact() != null){
            contact.setText(onlineOrder.getContact());
        }
        if(onlineOrder.getZip() != null){
            zip.setText(onlineOrder.getZip());
        }
        if(onlineOrder.getInvoiceTitle() != null){
            invoice_title.setText(onlineOrder.getInvoiceTitle());
        }
        if(onlineOrder.getInvoiceAddress() != null){
            invoice_address.setText(onlineOrder.getInvoiceAddress());
        }
        if(onlineOrder.getAudit() != null){
            audit.setText(onlineOrder.getAudit());
        }
        if(onlineOrder.getUpdateLast() != null){
            update_last.setText(onlineOrder.getUpdateLast());
        }
        if(onlineOrder.getInvalidPeople() != null){
            invalid_people.setText(onlineOrder.getInvalidPeople());
        }
        if(onlineOrder.getBaseRemark() != null){
            base_remark.setText(onlineOrder.getBaseRemark());
        }
        if(onlineOrder.getPhone() != null){
            phone.setText(onlineOrder.getPhone());
        }
        if(onlineOrder.getFax() != null){
            fax.setText(onlineOrder.getFax());
        }
        if(onlineOrder.getDeliveryAddress() != null){
            delivery_address.getSelectionModel().select(onlineOrder.getDeliveryAddress());
        }
        if(onlineOrder.getAuditStr() != null){
            audit_str.setText(onlineOrder.getAuditStr());
        }
        if(onlineOrder.getUpdateLastStr() != null){
            update_last_str.setText(onlineOrder.getUpdateLastStr());
        }
        if(onlineOrder.getInvalidPeopleStr() != null){
            invalid_people_str.setText(onlineOrder.getInvalidPeopleStr());
        }
        setTableviewVal();
    }

    /**
     * 加载tableview数据
     */
    private void setTableviewVal(){
        tableview.setEditable(true);

        col_product_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_product_name.setCellFactory(column -> EditCell.createStringEditCell());
        col_category.setCellFactory(column -> EditCell.createStringEditCell());
        col_number.setCellFactory(column -> EditCell.createStringEditCell());
        col_unit.setCellFactory(column -> EditCell.createStringEditCell());
        col_price.setCellFactory(column -> EditCell.createStringEditCell());
        col_money.setCellFactory(column -> EditCell.createStringEditCell());
        col_usable_num.setCellFactory(column -> EditCell.createStringEditCell());
        col_stock.setCellFactory(column -> EditCell.createStringEditCell());
        col_remark.setCellFactory(column -> EditCell.createStringEditCell());


        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<SaleOnlineOrderProduct> productList = iSaleOnlineOrderProductService.listByOnlineOrderAll(orderid.getText());
            List<SaleOnlineOrderProductProperty> productPropertyList = new ArrayList<>();
            productList.forEach(p->{
                productPropertyList.add(new SaleOnlineOrderProductProperty(p.getId(), p.getOnlineOrderId(), p.getCustomerNo(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPrice(), p.getMoney(), p.getUsableNum() ,p.getRemark()));
            });
            final ObservableList<SaleOnlineOrderProductProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
            col_id.setCellValueFactory(new PropertyValueFactory<Remark,Long>("id"));
            col_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));
            col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
            col_category.setCellValueFactory(new PropertyValueFactory("category"));
            col_number.setCellValueFactory(new PropertyValueFactory("num"));
            col_unit.setCellValueFactory(new PropertyValueFactory("unit"));
            col_price.setCellValueFactory(new PropertyValueFactory("price"));
            col_money.setCellValueFactory(new PropertyValueFactory("money"));
            col_usable_num.setCellValueFactory(new PropertyValueFactory("usableNum"));
            col_stock.setCellValueFactory(new PropertyValueFactory("ifstock"));
            col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

            tableview.setItems(dataProperty);
        }

    }

    /**
     * 清除空间上的值
     */
    @FXML
    private void clearControllerVal(){
        customerid.clear();
        orderid.clear();
        LocalDateTime localDate = LocalDateTime.now();
        order_date.setValue(localDate.toLocalDate());
        customer_no.clear();
        order_remark.clear();
        order_no.clear();
        customer_no_str.clear();
        order_people.clear();
        contact.clear();
        zip.clear();
        invoice_title.clear();
        invoice_address.clear();
        audit.clear();
        update_last.clear();
        invalid_people.clear();
        base_remark.clear();
        phone.clear();
        fax.clear();
        delivery_address.getItems().clear();
        delivery_address.getSelectionModel().select("");
        audit_str.clear();
        update_last_str.clear();
        invalid_people_str.clear();

        tableview.setItems(null);
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
    public void setControllerUse(){
        setControllerState(false);
    }

    /**
     *  设置控件状态（可用、不可用）
     * @param bool
     */
    private void setControllerState(boolean bool){
        order_date.setDisable(bool);
        customer_no.setDisable(bool);
        order_remark.setDisable(bool);
        order_no.setDisable(bool);
        customer_no_str.setDisable(bool);
        // 作废按钮
        btn_invalid.setDisable(bool);
        order_people.setDisable(bool);
        contact.setDisable(bool);
        zip.setDisable(bool);
        invoice_title.setDisable(bool);
        invoice_address.setDisable(bool);
        audit.setDisable(bool);
        update_last.setDisable(bool);
        invalid_people.setDisable(bool);
        base_remark.setDisable(bool);
        phone.setDisable(bool);
        fax.setDisable(bool);
        delivery_address.setDisable(bool);
        audit_str.setDisable(bool);
        update_last_str.setDisable(bool);
        invalid_people_str.setDisable(bool);

        tableview.setDisable(bool);
        menu_clearAll.setDisable(bool);
        menu_commit.setDisable(bool);
        menu_insert.setDisable(bool);
        menu_delete.setDisable(bool);

        tableview.setDisable(bool);
    }


    /**
     * 打开订单查询窗口
     */
    @FXML
    public void OpenMiniQuery() {

        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("OnlineOrderControllerNo", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/online_order_query_mini.fxml"));
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
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("OnlineOrderController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

}
