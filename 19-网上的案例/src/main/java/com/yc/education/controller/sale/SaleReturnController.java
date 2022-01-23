package com.yc.education.controller.sale;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.ProductBasic;
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
 * 销售退货单
 */
@Controller
public class SaleReturnController extends BaseController implements Initializable {

    @Autowired
    IRemarkService iRemarkService;
    @Autowired
    IReportRemarkService iReportRemarkService;
    @Autowired
    ISaleGoodsService iSaleGoodsService;
    @Autowired
    ISaleGoodsProductService iSaleGoodsProductService;
    @Autowired
    ISaleReturnGoodsService iSaleReturnGoodsService;
    @Autowired
    ISaleReturnGoodsProductService iSaleReturnGoodsProductService;
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

    @FXML CheckBox che_early; // 前期单据
    @FXML Button btn_invalid; // 作废按钮
    @FXML DatePicker create_date;  // 制单日期
    @FXML public TextField customer_no; // 客户编号
    @FXML public TextField customer_no_str; // 客户编号描述
    @FXML ComboBox tax; // 税别
    @FXML ComboBox currency; // 币别
    @FXML TextField return_no; // 销售单号
    @FXML TextField made_people; //制单人
    @FXML TextField last_update; //最后修改人
    @FXML TextField last_update_str; //最后修改人描述
    @FXML ComboBox business_leader; //负责业务
    @FXML TextField business_leader_str; //负责业务描述
    @FXML ComboBox warehouse_in; // 入库仓库
    @FXML TextField warehouse_position_str; // 仓库位置描述
    @FXML ComboBox warehouse_position; // 仓库位置
    @FXML ComboBox return_reason; // 退货原因
    @FXML TextField apply_people; // 退货申请人
    @FXML TextField audit_people; // 审核人
    @FXML TextField audit_people_str; // 审核人描述
    @FXML TextField remark; // 备注


    @FXML TableView tab_product; // 订货产品 表格
    @FXML TableColumn col_id; // 列id
    @FXML TableColumn col_product_no; //列 产品编号
    @FXML TableColumn col_product_name; //列 产品名称
    @FXML TableColumn col_category; //列 品类
    @FXML TableColumn col_num; //列 数量
    @FXML TableColumn col_unit; // 列 单位
    @FXML TableColumn col_pricing; // 列 定价
    @FXML TableColumn col_money; //列 金额
    @FXML TableColumn col_source; //列 来源数据
    @FXML TableColumn col_order_no; //列 单号
    @FXML TableColumn col_warehouse_position; //列 库位
    @FXML TableColumn col_floor; //列 楼层
    @FXML TableColumn col_remark; //列 备注


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

    SpringFxmlLoader loader = new SpringFxmlLoader();
    Stage stage = new Stage();
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
        setComboBox(19L,return_reason,0); //退货原因
        setComboBox(29L,warehouse_in,0); // 入库仓库
        setComboBox(29L,warehouse_position,0); //仓库库位
        loadEmployee(business_leader, 0); // 业务负责人
        // 截取姓名
        business_leader.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String bus = business_leader.getSelectionModel().getSelectedItem().toString();
                    bus = bus.substring(bus.indexOf(')')+1, bus.length());
                    business_leader_str.setText(bus);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // 报价产品行双击 调出现有产品窗口
        BaseController.clickEvent(tab_product, data ->{
            tablePosition = tab_product.getSelectionModel().getSelectedIndex();
            moreProductButtonClick();
        }, 2);

        SaleGoods order = StageManager.saleGoods;
        StageManager.saleGoods = null;
        if(order == null){
            firstData();
            setControllerDisable();
        }else{
            clearControllerVal();

            if(order.getId() != null){
                orderid.setText(order.getId().toString());
            }
            if(order.getCreateDate() != null){
                create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
            }
            if(order.getSaleNo() != null){
                return_no.setText(order.getSaleNo());
            }
            if(order.getCustomerNo() != null){
                customer_no.setText(order.getCustomerNo());
            }
            if(order.getCustomerNoStr() != null){
                customer_no_str.setText(order.getCustomerNoStr());
            }
            if(order.getBusinessLeader() != null){
                business_leader.getSelectionModel().select(Integer.valueOf(order.getBusinessLeader())-1);
            }
            if(order.getBusinessLeaderStr() != null){
                business_leader_str.setText(order.getBusinessLeaderStr());
            }
            if(order.getTax() != null){
                tax.getSelectionModel().select(order.getTax()-1);
            }
            if(order.getCurrency() != null){
                setComboBox(7L,currency,order.getCurrency()-1); //币别
            }
            if(order.getWarehouseOut() != null){
                warehouse_in.getSelectionModel().select(order.getWarehouseOut()-1);
            }
            if(order.getWarehousePosition() != null){
                warehouse_position.getSelectionModel().select(order.getWarehouseOut()-1);
            }
            if(order.getWarehousePositionStr() != null){
                warehouse_position_str.setText(order.getWarehousePositionStr());
            }

            if(order.getCustomerName() != null){
                apply_people.setText(order.getCustomerName());
            }
            if(order.getMadePeople() != null){
                made_people.setText(order.getMadePeople());
            }
//            if(order.getAudit() != null){
//                audit_people.setText(order.getAudit());
//            }
//            if(order.getAuditStr() != null){
//                audit_people_str.setText(order.getAuditStr());
//            }
//            if(order.getLastUpdate() != null){
//                last_update.setText(order.getLastUpdate());
//            }
//            if(order.getLastUpdateStr() != null){
//                last_update_str.setText(order.getLastUpdateStr());
//            }
            lastUpdatePeopel(last_update, last_update_str);
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
        pane.getChildren().setAll(loader.load("/fxml/sale/product_find_retreat.fxml"));
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
        ObservableList<SaleReturnGoodsProductProperty> list = tab_product.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        for (int i = 0; i < list.size(); i++) {
            if(tablePosition == i){
                list.get(i).setProductName(productBasic.getProname());
                list.get(i).setProductNo(productBasic.getIsnum());
            }
        }
        tab_product.setItems(FXCollections.observableArrayList(list));
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
     * 删除产品行
     */
    private void deleteRowOfProduct(){
        // 取得当前行的数据
        try {

            TablePosition pos = (TablePosition) tab_product.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();

            SaleReturnGoodsProductProperty property = (SaleReturnGoodsProductProperty)tab_product.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iSaleReturnGoodsProductService.delete(property.getId());
                returnMsg(rows);
            }
            final ObservableList<SaleReturnGoodsProductProperty> dataProperty = tab_product.getItems();
            final ObservableList<SaleReturnGoodsProductProperty> newDataProperty = FXCollections.observableArrayList();
            for (int i = 0; i < dataProperty.size(); i++) {
                if(i != index){
                    newDataProperty.add(dataProperty.get(i));
                }
            }
            tab_product.setItems(newDataProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加产品行
     */
    public void addProductRow() {

        ObservableList<SaleReturnGoodsProductProperty> list = tab_product.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new SaleReturnGoodsProductProperty( "", "", "", 0, "", new BigDecimal("0.00"), new BigDecimal("0.00"), "", "", "", "", ""));
        tab_product.setItems(list);
    }

    /**
     * 新增订单
     */
    @FXML
    public void add(){
        clearControllerVal();
        create_date.setValue(localDate.toLocalDate());
        return_no.setText("XT"+new Date().getTime()+getRandomone());
        setControllerUse();

        business_leader.getItems().clear();
        loadEmployee(business_leader, 0); // 业务负责人
        // 截取姓名
        business_leader.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String bus = business_leader.getSelectionModel().getSelectedItem().toString();
                    bus = bus.substring(bus.indexOf(')')+1, bus.length());
                    business_leader_str.setText(bus);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存数据
     */
    @FXML
    public void save(){
        SaleReturnGoods order = new SaleReturnGoods();
        if(create_date.getValue() != null){
            try {
                Date date = df.parse(create_date.getValue().toString());
                order.setCreateDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(return_no.getText() != null && !"".equals(return_no.getText())){
            order.setPinbackNo(return_no.getText());
        }

        if(customer_no.getText() != null && !"".equals(customer_no.getText())){
            order.setCustomerNo(customer_no.getText());
        }
        if(customer_no_str.getText() != null && !"".equals(customer_no_str.getText())){
            order.setCustomerNoStr(customer_no_str.getText());
        }
        if(business_leader_str.getText() != null && !"".equals(business_leader_str.getText())){
            order.setBusinessLeaderStr(business_leader_str.getText());
        }
        if(business_leader.getSelectionModel().getSelectedItem() != null && !"".equals(business_leader.getSelectionModel().getSelectedItem())){
            order.setBusinessLeader(business_leader.getSelectionModel().getSelectedIndex()+"");
        }

        if(tax.getSelectionModel().getSelectedItem() != null && !"".equals(tax.getSelectionModel().getSelectedItem())){
            order.setTax(tax.getSelectionModel().getSelectedIndex()+1);
        }
        if(currency.getSelectionModel().getSelectedItem() != null && !"".equals(currency.getSelectionModel().getSelectedItem())){
            order.setCurrency(currency.getSelectionModel().getSelectedIndex()+1);
        }
        if(warehouse_in.getSelectionModel().getSelectedItem() != null && !"".equals(warehouse_in.getSelectionModel().getSelectedItem())){
            order.setWarehouseIn(warehouse_in.getSelectionModel().getSelectedIndex()+1);
        }
        if(warehouse_position.getSelectionModel().getSelectedItem() != null && !"".equals(warehouse_position.getSelectionModel().getSelectedItem())){
            order.setWarehouseLocation(warehouse_position.getSelectionModel().getSelectedIndex()+1);
        }
        if(warehouse_position_str.getText() != null && !"".equals(warehouse_position_str.getText())){
            order.setWarehouseLocationStr(warehouse_position_str.getText());
        }
        if(return_reason.getSelectionModel().getSelectedItem() != null && !"".equals(return_reason.getSelectionModel().getSelectedItem())){
            order.setReturnReason(return_reason.getSelectionModel().getSelectedIndex()+1);
        }
        if(remark.getText() != null && !"".equals(remark.getText())){
            order.setRemark(remark.getText());
        }
        if(apply_people.getText() != null && !"".equals(apply_people.getText())){
            order.setReturnReasonPeople(apply_people.getText());
        }
        if(made_people.getText() != null && !"".equals(made_people.getText())){
            order.setMadePeople(made_people.getText());
        }
        if(audit_people.getText() != null && !"".equals(audit_people.getText())){
            order.setAuditPeople(audit_people.getText());
        }
        if(audit_people_str.getText() != null && !"".equals(audit_people_str.getText())){
            order.setAuditPeopleStr(audit_people_str.getText());
        }
        if(last_update.getText() != null && !"".equals(last_update.getText())){
            order.setLastUpdate(last_update.getText());
        }
        if(last_update_str.getText() != null && !"".equals(last_update_str.getText())){
            order.setLastUpdateStr(last_update_str.getText());
        }
        if(business_leader.getSelectionModel().getSelectedItem() != null && !"".equals(business_leader.getSelectionModel().getSelectedItem())){
            order.setBusinessLeader(business_leader.getValue().toString());
        }
        if(business_leader_str.getText() != null && !"".equals(business_leader_str.getText())){
            order.setBusinessLeaderStr(business_leader_str.getText());
        }

        if(orderid.getText() != null && !"".equals(orderid.getText())){
            order.setId(Long.valueOf(orderid.getText()));
        }
        order.setEarlyDocument(che_early.selectedProperty().getValue());

        if(order.getId() != null){
            // 修改
            int rows = iSaleReturnGoodsService.updateNotNull(order);
            returnMsg(rows);
            setControllerDisable();
        }else{
            order.setAddtime(new Date());
            // 保存
            int rows = iSaleReturnGoodsService.save(order);
            orderid.setText(order.getId().toString());
            returnMsg(rows);
            setControllerDisable();
        }
        // 操作tableview中的数据
        saveTableviewProduct();

    }

    /**
     * 保存 销退产品tableview数据
     */
    private void saveTableviewProduct(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = tab_product.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                SaleReturnGoodsProductProperty property = null;
                if(tab_product.getItems().get(i) != null){
                    property = (SaleReturnGoodsProductProperty)tab_product.getItems().get(i);
                }
                SaleReturnGoodsProduct order = new SaleReturnGoodsProduct();

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
                if(property.getSource() != null && !"".equals(property.getSource())){
                    order.setSource(property.getSource());
                }
                if(property.getOrderNo() != null && !"".equals(property.getOrderNo())){
                    order.setOrderNo(property.getOrderNo());
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
                order.setReturnGoodsId(Long.valueOf(id));

                if(property.getId() == null || property.getId()==0L){
                    try {
                        order.setAddtime(new Date());
                        iSaleReturnGoodsProductService.save(order);
                        setTableviewVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        order.setId(property.getId());
                        iSaleReturnGoodsProductService.updateNotNull(order);
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
            int rows = iSaleReturnGoodsService.delete(Long.valueOf(orderid.getText()));
            returnMsg(rows);
            firstData();
        }
    }

    /**
     * 下一个客户信息
     */
    @FXML
    public void nextData() {
        int max = iSaleReturnGoodsService.getSaleReturnGoodsCount();
        if (page < max - 1) {
            page += 1;
        }
        SaleReturnGoods order = iSaleReturnGoodsService.getSaleReturnGoodsByPage(page, rows);
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
        SaleReturnGoods order = iSaleReturnGoodsService.getSaleReturnGoodsByPage(page, rows);
        setBasicVal(order);
    }


    /**
     *  最后一条客户数据
     **/
    @FXML
    public void lastData() {
        page = iSaleReturnGoodsService.getSaleReturnGoodsCount() - 1;
        SaleReturnGoods order = iSaleReturnGoodsService.getLastSaleReturnGoods();
        setBasicVal(order);
    }

    /**
     * 第一条客户数据
     */
    @FXML
    public void firstData() {
        page = 0;
        SaleReturnGoods order = iSaleReturnGoodsService.getFirstSaleReturnGoods();
        setBasicVal(order);
    }

    /**
     * 设置值
     * @param order
     */
    public void setBasicVal(SaleReturnGoods order){
        if(order == null){
            return;
        }
        clearControllerVal();

        if(order.getId() != null){
            orderid.setText(order.getId().toString());
        }
        if(order.getCreateDate() != null){
            create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(order.getPinbackNo() != null){
            return_no.setText(order.getPinbackNo());
        }
        if(order.getCustomerNo() != null){
            customer_no.setText(order.getCustomerNo());
        }
        if(order.getCustomerNoStr() != null){
            customer_no_str.setText(order.getCustomerNoStr());
        }
        if(order.getBusinessLeader() != null){
            business_leader.getSelectionModel().select(Integer.valueOf(order.getBusinessLeader())-1);
        }
        if(order.getBusinessLeaderStr() != null){
            business_leader_str.setText(order.getBusinessLeaderStr());
        }
        if(order.getTax() != null){
            tax.getSelectionModel().select(order.getTax()-1);
        }
        if(order.getCurrency() != null){
            setComboBox(7L,currency,order.getCurrency()-1); //币别
        }
        if(order.getWarehouseIn() != null){
            warehouse_in.getSelectionModel().select(order.getWarehouseIn()-1);
        }
        if(order.getWarehouseLocation() != null){
            warehouse_position.getSelectionModel().select(order.getWarehouseLocation()-1);
        }
        if(order.getWarehouseLocationStr() != null){
            warehouse_position_str.setText(order.getWarehouseLocationStr());
        }
        if(order.getReturnReason() != null){
            return_reason.getSelectionModel().select(order.getReturnReason()-1);
        }
        if(order.getRemark() != null){
            remark.setText(order.getRemark());
        }
        if(order.getReturnReasonPeople() != null){
            apply_people.setText(order.getReturnReasonPeople());
        }
        if(order.getMadePeople() != null){
            made_people.setText(order.getMadePeople());
        }
        if(order.getAuditPeople() != null){
            audit_people.setText(order.getAuditPeople());
        }
        if(order.getAuditPeopleStr() != null){
            audit_people_str.setText(order.getAuditPeopleStr());
        }
        if(order.getLastUpdate() != null){
            last_update.setText(order.getLastUpdate());
        }
        if(order.getLastUpdateStr() != null){
            last_update_str.setText(order.getLastUpdateStr());
        }
        che_early.setSelected(order.getEarlyDocument());
        if(order.getOrderAudit() != null){
            if(order.getOrderAudit()){
                setShiroControlYES();
                menu_update.setDisable(true);
            }else{
                setShiroControlNO();
            }
        }
        setTableviewVal();
    }

    /**
     * 加载销售单的数据
     * @param order
     */
    public void setBasicImportVal(SaleGoods order){
        if(order == null){
            return;
        }
        clearControllerVal();
        if(order.getCreateDate() != null){
            create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(order.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        return_no.setText("XT"+new Date().getTime()+getRandomone());
        if(order.getCustomerNo() != null){
            customer_no.setText(order.getCustomerNo());
        }
        if(order.getCustomerNoStr() != null){
            customer_no_str.setText(order.getCustomerNoStr());
        }
        if(order.getBusinessLeader() != null){
            business_leader.getSelectionModel().select(Integer.valueOf(order.getBusinessLeader())-1);
        }
        if(order.getBusinessLeaderStr() != null){
            business_leader_str.setText(order.getBusinessLeaderStr());
        }
        if(order.getTax() != null){
            tax.getSelectionModel().select(order.getTax()-1);
        }
        if(order.getCurrency() != null){
            setComboBox(7L,currency,order.getCurrency()-1); //币别
        }
        if(order.getWarehouseOut() != null){
            warehouse_in.getSelectionModel().select(order.getWarehouseOut()-1);
        }
        if(order.getWarehousePosition() != null){
            warehouse_position.getSelectionModel().select(order.getWarehousePosition()-1);
        }
        if(order.getWarehousePositionStr() != null){
            warehouse_position_str.setText(order.getWarehousePositionStr());
        }
        if(order.getMadePeople() != null){
            made_people.setText(order.getMadePeople());
        }
        lastUpdatePeopel(last_update, last_update_str);
        che_early.setSelected(true);
    }

    /**
     * 加载tableview数据
     */
    private void setTableviewVal(){
        if(orderid.getText() != null && !"".equals(orderid.getText())){
            List<SaleReturnGoodsProduct> productList = iSaleReturnGoodsProductService.listReturnGoodsProduct(orderid.getText());
            List<SaleReturnGoodsProductProperty> productPropertyList = new ArrayList<>();
            if(productList == null) return;
            productList.forEach(p->{
                productPropertyList.add(new SaleReturnGoodsProductProperty(p.getId(),p.getReturnGoodsId(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(), p.getMoney(),p.getSource(), p.getOrderNo(), p.getWarehousePosition() , p.getFloor() ,p.getRemark()));
            });
            generalProductTab(FXCollections.observableArrayList(productPropertyList));
        }

    }

    /**
     * 加载表格数据
     * @param productPropertyList
     */
    public void generalProductTab(ObservableList<SaleReturnGoodsProductProperty> productPropertyList){
        tab_product.setEditable(true);

        col_product_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_product_name.setCellFactory(column -> EditCell.createStringEditCell());
        col_category.setCellFactory(column -> EditCell.createStringEditCell());
        col_num.setCellFactory(column -> EditCell.createStringEditCell());
        col_unit.setCellFactory(column -> EditCell.createStringEditCell());
        col_pricing.setCellFactory(column -> EditCell.createStringEditCell());
        col_money.setCellFactory(column -> EditCell.createStringEditCell());
        col_source.setCellFactory(column -> EditCell.createStringEditCell());
        col_order_no.setCellFactory(column -> EditCell.createStringEditCell());
        col_warehouse_position.setCellFactory(column -> EditCell.createStringEditCell());
        col_floor.setCellFactory(column -> EditCell.createStringEditCell());
        col_remark.setCellFactory(column -> EditCell.createStringEditCell());

        final ObservableList<SaleReturnGoodsProductProperty> dataProperty = FXCollections.observableArrayList(productPropertyList);
        col_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_product_no.setCellValueFactory(new PropertyValueFactory<>("productNo"));
        col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
        col_category.setCellValueFactory(new PropertyValueFactory("category"));
        col_num.setCellValueFactory(new PropertyValueFactory("num"));
        col_unit.setCellValueFactory(new PropertyValueFactory("unit"));
        col_pricing.setCellValueFactory(new PropertyValueFactory("pricing"));
        col_source.setCellValueFactory(new PropertyValueFactory("source"));
        col_order_no.setCellValueFactory(new PropertyValueFactory("orderNo"));
        col_money.setCellValueFactory(new PropertyValueFactory("money"));
        col_warehouse_position.setCellValueFactory(new PropertyValueFactory("warehousePosition"));
        col_floor.setCellValueFactory(new PropertyValueFactory("floor"));
        col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        tab_product.setItems(dataProperty);
    }

    /**
     * 清除控件上的值
     */
    @FXML
    private void clearControllerVal(){

        LocalDateTime localDate = LocalDateTime.now();

         orderid.clear();
         che_early.setSelected(false);
         create_date.setValue(localDate.toLocalDate());
         customer_no.clear();
         customer_no_str.clear();
         tax.getSelectionModel().selectFirst();
         currency.getSelectionModel().selectFirst();
         return_no.clear();
         made_people.clear();
         last_update.clear();
         last_update_str.clear();
         business_leader.getSelectionModel().selectFirst();
         business_leader_str.clear();
         warehouse_in.getSelectionModel().selectFirst();
         warehouse_position_str.clear();
         warehouse_position.getSelectionModel().selectFirst();
         return_reason.getSelectionModel().selectFirst();
         apply_people.clear();
         audit_people.clear();
         audit_people_str.clear();
         remark.clear();


//         col_id; // 列id
//         col_product_no; //列 产品编号
//         col_product_name; //列 产品名称
//         col_category; //列 品类
//         col_num; //列 数量
//         col_unit; // 列 单位
//         col_pricing; // 列 定价
//         col_money; //列 金额
//         col_source; //列 来源数据
//         col_order_no; //列 单号
//         col_warehouse_position; //列 库位
//         col_floor; //列 楼层
//         col_remark; //列 备注

        // 合计
        total_num.clear();
        total_money.clear();
        total_loan.clear();
        total_tax.clear();

        tab_product.setItems(null);
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


        che_early.setDisable(bool);
        create_date.setDisable(bool);
        customer_no.setDisable(bool);
        customer_no_str.setDisable(bool);
        tax.setDisable(bool);
        currency.setDisable(bool);
        return_no.setDisable(bool);
        made_people.setDisable(bool);
        last_update.setDisable(bool);
        last_update_str.setDisable(bool);
        business_leader.setDisable(bool);
        business_leader_str.setDisable(bool);
        warehouse_in.setDisable(bool);
        warehouse_position_str.setDisable(bool);
        warehouse_position.setDisable(bool);
        return_reason.setDisable(bool);
        apply_people.setDisable(bool);
        audit_people.setDisable(bool);
        audit_people_str.setDisable(bool);
        remark.setDisable(bool);
        

         tab_product.setDisable(bool);

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
        menu_update.setDisable(true);
    }

    /**
     * 取消审核
     */
    private void setShiroControlNO(){
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
        // 有效单据验证
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("单据还暂未保存，无法审核！");
            return;
        }
        setShiroControlYES();
        lastUpdatePeopel(audit_people, audit_people_str);
        lastUpdatePeopel(last_update, last_update_str);
        shiroUpdateData(true);
    }

    /**
     * 审核过后的数据提交
     */
    private void shiroUpdateData(Boolean bool){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SaleReturnGoods goods = iSaleReturnGoodsService.selectByKey(Long.valueOf(id));
            goods.setOrderAudit(bool);
            goods.setLastUpdate(last_update.getText());
            goods.setLastUpdateStr(last_update_str.getText());
            goods.setAuditPeople(audit_people.getText());
            goods.setAuditPeopleStr(audit_people_str.getText());
            iSaleReturnGoodsService.updateNotNull(goods);
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
        lastUpdatePeopel(audit_people, audit_people_str);
        lastUpdatePeopel(last_update, last_update_str);
        shiroUpdateData(false);
    }

    /**
     * 导入
     */
    @FXML
    public void importIn(){
        stage.setTitle("导入-销货单");
        Pane pane = new Pane();
        StageManager.CONTROLLER.put("SaleReturnControllerImport", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/sale_goods_import.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 打开订单查询窗口
     */
    @FXML
    public void OpenMiniQuery() {
        Pane pane = new Pane();
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("SaleReturnControllerNo", this);
        pane.getChildren().setAll(loader.load("/fxml/sale/sale_return_query_mini.fxml"));
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
        StageManager.CONTROLLER.put("SaleReturnController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

}
