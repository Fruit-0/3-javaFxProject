package com.yc.education.controller.stock;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.customer.Remark;
import com.yc.education.model.sale.ReportRemark;

import com.yc.education.model.stock.StockOutSale;
import com.yc.education.model.stock.StockOutSaleProduct;
import com.yc.education.model.stock.StockOutSaleProductProperty;
import com.yc.education.property.RemarkProperty;
import com.yc.education.property.ReportRemarkProperty;
import com.yc.education.service.customer.IRemarkService;
import com.yc.education.service.sale.IReportRemarkService;
import com.yc.education.service.stock.IStockOutSaleProductService;
import com.yc.education.service.stock.IStockOutSaleService;
import com.yc.education.util.DateUtils;
import com.yc.education.util.EditCell;
import com.yc.education.util.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
 * 库存 - 销货出库单
 */
@Controller
public class SaleOutboundOrderController extends BaseController implements Initializable {

    @Autowired
    IRemarkService iRemarkService;
    @Autowired
    IReportRemarkService iReportRemarkService;
    @Autowired
    IStockOutSaleService iStockOutSaleService;
    @Autowired
    IStockOutSaleProductService iStockOutSaleProductService;

    // 菜单控件
    @FXML VBox menu_clearAll;
    @FXML VBox menu_commit;
    @FXML VBox menu_insert;
    @FXML VBox menu_update;
    @FXML VBox menu_delete;

    // 订单id
    @FXML public TextField orderid;

    // 作废按钮
    @FXML Button btn_invalid;
    @FXML DatePicker order_date;
    @FXML public TextField customer_no;
    @FXML public TextField customer_no_str;

    @FXML TextField made_people; // 制单人
    @FXML TextField order_no;
    @FXML TextField last_update_str;
    @FXML TextField last_update;
    @FXML TextField warehouse_in;
    @FXML TextField warehouse_in_str;
    @FXML TextField audit;
    @FXML TextField audit_str;

 // 产品tableview
    @FXML TableView tableview;
    @FXML TableColumn col_id;
    @FXML TableColumn col_source_order;
    @FXML TableColumn col_order_no;
    @FXML TableColumn col_product_no;
    @FXML TableColumn col_product_name;
    @FXML TableColumn col_category;
    @FXML TableColumn col_unit;
    @FXML TableColumn col_price;
    @FXML TableColumn col_num;
    @FXML TableColumn col_location_name;
    @FXML TableColumn col_floor;
    @FXML TableColumn col_remark;

    // 备注tableview
    @FXML TableView remark_table; //备注及说明--备注表格
    @FXML TableColumn col_remark_id; //列id
    @FXML TableColumn col_remark_content; //列 内容
    // 报表tableview
    @FXML TableView report_table; //备注及说明--报表
    @FXML TableColumn col_report_id; // 列 id
    @FXML TableColumn col_report_content; //列 内容



    private Stage stage = new Stage();
    private static SpringFxmlLoader loader = new SpringFxmlLoader();
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
     * 删除产品行
     */
    private void deleteRowOfProduct(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) tableview.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            StockOutSaleProductProperty property = (StockOutSaleProductProperty)tableview.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iStockOutSaleProductService.delete(property.getId());
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

        ObservableList<StockOutSaleProductProperty> list = tableview.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new StockOutSaleProductProperty(0L, 0L, "", "", "", "", "", 0, 0L, new BigDecimal("0.00"), "", "", ""));
        tableview.setItems(list);
    }



    /**
     * 新增订单
     */
    @FXML
    public void add(){
        clearControllerVal();
        order_date.setValue(localDate.toLocalDate());
        order_no.setText("S"+new Date().getTime()+getRandomone());
    }

    /**
     * 保存数据
     */
    @FXML
    public void save(){
        StockOutSale order = new StockOutSale();
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
            order.setOutboundNo(order_no.getText());
        }
        if(customer_no_str.getText() != null && !"".equals(customer_no_str.getText())){
            order.setCustomerNoStr(customer_no_str.getText());
        }

        if(audit.getText() != null && !"".equals(audit.getText())){
            order.setAudit(audit.getText());
        }
        if(audit.getText() != null && !"".equals(audit.getText())){
            order.setAudit(audit.getText());
        }
        if(audit_str.getText() != null && !"".equals(audit_str.getText())){
            order.setAuditStr(audit_str.getText());
        }
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            order.setId(Long.valueOf(orderid.getText()));
        }
        if(made_people.getText() != null && !"".equals(made_people.getText())){
            order.setMadePeople(made_people.getText());
        }
        if(last_update_str.getText() != null && !"".equals(last_update_str.getText())){
            order.setLastUpdateStr(last_update_str.getText());
        }
        if(last_update.getText() != null && !"".equals(last_update.getText())){
            order.setLastUpdate(last_update.getText());
        }
        if(warehouse_in.getText() != null && !"".equals(warehouse_in.getText())){
            try {
                order.setWarehouseIn(Long.valueOf(warehouse_in.getText()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(warehouse_in_str.getText() != null && !"".equals(warehouse_in_str.getText())){
            order.setWarehouseInStr(warehouse_in_str.getText());
        }
        int rows = 0;
        if(order.getId() != null){
            // 修改
            rows = iStockOutSaleService.updateNotNull(order);
        }else{
            // 保存
            rows = iStockOutSaleService.save(order);
            orderid.setText(order.getId().toString());
        }
        // 操作tableview中的数据
        saveTableviewProduct();
        saveTableviewRemark();
        saveTableviewReport();
        setBasicVal(order);
        returnMsg(rows);
        setControllerDisable();
    }

    /**
     * 保存订货产品tableview数据
     */
    private void saveTableviewProduct(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = tableview.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                StockOutSaleProductProperty property = null;
                if(tableview.getItems().get(i) != null){
                    property = (StockOutSaleProductProperty)tableview.getItems().get(i);
                }
                StockOutSaleProduct product = new StockOutSaleProduct();
                if(property.getOrderNo() != null){
                    product.setOrderNo(property.getOrderNo());
                }
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
                    product.setUnit(Long.valueOf(property.getUnit()));
                }
                if(property.getPrice() != null){
                    product.setPrice(new BigDecimal(property.getPrice()));
                }
                if(property.getWarehouseName() != null){
                    product.setWarehouseName(property.getWarehouseName());
                }
                if(property.getFloor() != null){
                    product.setFloor(property.getFloor());
                }
                if(property.getRemark() != null){
                    product.setRemark(property.getRemark());
                }
                product.setStockOutSaleId(Long.valueOf(id));
                product.setAddtime(new Date());
                if(property.getId() == null || property.getId() == 0L){
                    try {
                        iStockOutSaleProductService.save(product);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    product.setId(property.getId());
                    try {
                        iStockOutSaleProductService.updateNotNull(product);
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
            int rows = iStockOutSaleService.delete(Long.valueOf(orderid.getText()));
            returnMsg(rows);
            firstData();
        }
    }

    /**
     * 下一个客户信息
     */
    @FXML
    public void nextData() {
        int max = iStockOutSaleService.getStockOutSaleCount();
        if (page < max - 1) {
            page += 1;
        }
        StockOutSale order = iStockOutSaleService.getStockOutSaleByPage(page, rows);
        setBasicVal(order);
    }

    /**
     * 上一个客户信息
     */
    @FXML
    public void prevData() {
        if (page > 0) {
            page -= 1;
        }
        StockOutSale order = iStockOutSaleService.getStockOutSaleByPage(page, rows);
        setBasicVal(order);
    }


    /**
     *  最后一条客户数据
     **/
    @FXML
    public void lastData() {
        page = iStockOutSaleService.getStockOutSaleCount() - 1;
        StockOutSale order = iStockOutSaleService.getLastStockOutSale();
        setBasicVal(order);
    }

    /**
     * 第一条客户数据
     */
    @FXML
    public void firstData() {
        page = 0;
        StockOutSale order = iStockOutSaleService.getFirstStockOutSale();
        setBasicVal(order);
    }
    
    /**
     * 设置值
     * @param order
     */
    public void setBasicVal(StockOutSale order){
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
        if(order.getCustomerNoStr() != null){
            customer_no_str.setText(order.getCustomerNoStr());
        }
        if(order.getLastUpdate() != null){
            last_update.setText(order.getLastUpdate());
        }
        if(order.getLastUpdateStr() != null){
            last_update_str.setText(order.getLastUpdateStr());
        }
        if(order.getMadePeople() != null){
            made_people.setText(order.getMadePeople());
        }
        if(order.getAudit() != null){
            audit.setText(order.getAudit());
        }
        if(order.getAuditStr() != null){
            audit_str.setText(order.getAuditStr());
        }
        if(order.getOutboundNo() != null){
            order_no.setText(order.getOutboundNo());
        }
        if(order.getWarehouseIn() != null){
            warehouse_in.setText(order.getWarehouseIn().toString());
        }
        if(order.getWarehouseInStr() != null){
            warehouse_in_str.setText(order.getWarehouseInStr());
        }

        setTableviewVal();
        setTableviewRemarkVal();
        setTableviewReportVal();
    }

    /**
     * 加载tableview数据
     */
    private void setTableviewVal(){
        tableview.setEditable(true);

        col_source_order.setCellFactory(column -> EditCell.createStringEditCell());
        col_order_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_product_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_product_name.setCellFactory(column -> EditCell.createStringEditCell());
        col_category.setCellFactory(column -> EditCell.createStringEditCell());
        col_unit.setCellFactory(column -> EditCell.createStringEditCell());
        col_price.setCellFactory(column -> EditCell.createStringEditCell());
        col_num.setCellFactory(column -> EditCell.createStringEditCell());
        col_location_name.setCellFactory(column -> EditCell.createStringEditCell());
        col_floor.setCellFactory(column -> EditCell.createStringEditCell());
        col_remark.setCellFactory(column -> EditCell.createStringEditCell());


        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<StockOutSaleProduct> productList = iStockOutSaleProductService.listStockOutSaleProduct(orderid.getText());
            List<StockOutSaleProductProperty> productPropertyList = new ArrayList<>();
            if(productList != null){
                productList.forEach(p->{
                    productPropertyList.add(new StockOutSaleProductProperty(p.getId(), p.getStockOutSaleId(),p.getOrderSource() ,p.getOrderNo(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPrice(), p.getWarehouseName(), p.getFloor() ,p.getRemark()));
                });
            }
            final ObservableList<StockOutSaleProductProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
            col_id.setCellValueFactory(new PropertyValueFactory<Remark,Long>("id"));
            col_source_order.setCellValueFactory(new PropertyValueFactory("orderSource"));
            col_order_no.setCellValueFactory(new PropertyValueFactory("orderNo"));
            col_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));
            col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
            col_category.setCellValueFactory(new PropertyValueFactory("category"));
            col_unit.setCellValueFactory(new PropertyValueFactory("unit"));
            col_price.setCellValueFactory(new PropertyValueFactory("price"));
            col_num.setCellValueFactory(new PropertyValueFactory("num"));
            col_location_name.setCellValueFactory(new PropertyValueFactory("warehouseName"));
            col_floor.setCellValueFactory(new PropertyValueFactory("floor"));
            col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

            tableview.setItems(dataProperty);
        }

    }

    /**
     * 清除空间上的值
     */
    @FXML
    private void clearControllerVal(){

        orderid.clear();
        LocalDateTime localDate = LocalDateTime.now();
        order_date.setValue(localDate.toLocalDate());
        customer_no.clear();
        customer_no_str.clear();
        made_people.clear();
        order_no.clear();
        last_update_str.clear();
        last_update.clear();
        warehouse_in.clear();
        warehouse_in_str.clear();
        audit.clear();
        audit_str.clear();

        tableview.setItems(null);
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
    public void setControllerUse(){
        setControllerState(false);
    }

    /**
     *  设置控件状态（可用、不可用）
     * @param bool
     */
    private void setControllerState(boolean bool){
        // 作废按钮
        btn_invalid.setDisable(bool);

        order_date.setDisable(bool);
        customer_no.setDisable(bool);
        customer_no_str.setDisable(bool);
        order_no.setDisable(bool);
        made_people.setDisable(bool);
        audit.setDisable(bool);
        audit_str.setDisable(bool);
        last_update.setDisable(bool);
        last_update_str.setDisable(bool);
        warehouse_in.setDisable(bool);
        warehouse_in_str.setDisable(bool);

        menu_clearAll.setDisable(bool);
        menu_commit.setDisable(bool);
        menu_insert.setDisable(bool);
        menu_delete.setDisable(bool);

        tableview.setDisable(bool);
        report_table.setDisable(bool);
        remark_table.setDisable(bool);
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
     * 加载备注tableview数据
     */
    private void setTableviewRemarkVal(){
        remark_table.setEditable(true);

        col_remark_content.setCellFactory(column -> EditCell.createStringEditCell());

        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<Remark> productList = iRemarkService.listRemark(Long.valueOf(orderid.getText()),"5");
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
            List<ReportRemark> productList = iReportRemarkService.listReportRemark(Long.valueOf(orderid.getText()),"4");
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
                product.setTypeid(5);
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
                product.setTypeid(4);
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
     * 打开订单查询窗口
     */
    @FXML
    public void OpenMiniQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("SaleOutboundOrderControllerMini", this);
        pane.getChildren().setAll(loader.load("/fxml/stock/sale_outbound_query_mini.fxml"));
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
        StageManager.CONTROLLER.put("SaleOutboundOrderController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

}
