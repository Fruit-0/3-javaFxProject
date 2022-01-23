package com.yc.education.controller.customer;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.controller.sale.ProductFindController;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.customer.*;
import com.yc.education.service.IClickItem;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.customer.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.event.*;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.AAShapePipe;

import javax.swing.event.TableColumnModelListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 客户需求商品
 *
 * @Author: BlueSky
 * @Date: 2018/8/15 15:13
 */
@Controller
public class CustomerDemandGoodsController extends BaseController implements Initializable {

    @Autowired
    ICustomerService iCustomerService;
    @Autowired
    ICustomerBasicService iCustomerBasicService;
    @Autowired
    ICustomerDemandGoodsService iCustomerDemandGoodsService;
    @Autowired
    ICustomerContactsService iCustomerContactsService;
    @Autowired
    ICustomerDemandGoodsInfoService iCustomerDemandGoodsInfoService;
    @Autowired
    private ProductBasicService productBasicService; //产品 Service
    // id
    @FXML
    TextField id;
    // 客户id
    @FXML
    TextField customer_id;
    // 客户编号
    @FXML
    TextField customer_no;
    // 客户名称
    @FXML
    TextField customer_name;
    // 构建编号
    @FXML
    TextField build_number;
    // 客户编号后文本框显示的值
    @FXML
    TextField customer_str;
    // 客户类别
    @FXML
    ComboBox customer_type;
    // 联系人
    @FXML
    ComboBox contacts;
    // 传真
    @FXML
    ComboBox fax;
    // 地址
    @FXML
    TextField address;
    // 备注
    @FXML
    TextField remark;
    // 有编号
    @FXML
    TextField zip;
    // 创建人
    @FXML
    public TextField create_people;
    // 联系电话
    @FXML
    ComboBox telephone;
    // 创建时间
    @FXML
    DatePicker create_date;
    // id
    @FXML
    TableColumn col_id;
    // 产品编号
    @FXML
    TableColumn col_no;
    // 产品名称
    @FXML
    TableColumn col_name;
    // 厂牌
    @FXML
    TableColumn col_brand;
    // 需求数量
    @FXML
    TableColumn col_quantity_demand;
    // 单价
    @FXML
    TableColumn col_price;
    // 产品大类
    @FXML
    TableColumn col_product_categories;
    // 产品性质
    @FXML
    TableColumn col_product_nature;
    // 制造方法
    @FXML
    TableColumn col_manufacture_method;
    // 加工方法
    @FXML
    TableColumn col_process_method;
    // 连续性
    @FXML
    TableColumn col_continuity;
    // 切削性
    @FXML
    TableColumn col_cut_oil;
    // 使用量
    @FXML
    TableColumn col_use_amount;
    // 切削速度
    @FXML
    TableColumn col_cut_speed;

    @FXML Button btn_customer_no;
    @FXML Button btn_create_no;

    //============================================== 菜单 ===============================================
    @FXML
    VBox menu_clearAll;
    @FXML
    VBox menu_commit;
    @FXML
    VBox menu_insert;
    @FXML
    VBox menu_update;
    @FXML
    VBox menu_delete;

    @FXML
    TableView customer_demand_goods_table;

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
    int tablePosition = 0; // 弹出窗口选中的行数

    /***************************************** 弹出窗口-产品-结束 ********************************************/

    @FXML
    private static Button customer_number_more;
    @FXML
    private static Button client_short_more;


    private Stage stage = new Stage();



    //==================================== 全局 ================================================
    static List<CustomerContacts> customerContactsList = new ArrayList<>();
    private static SpringFxmlLoader loader = new SpringFxmlLoader();
    // 当前页
    private static int page = 0;
    // 页大小
    private final static int rows = 1;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //加载第一条数据
        firstData();
        //客户Id变更监听
        customerIdChange();
        customerCategoryCombox();
        updateControllerDisable();

        ProductFindController.clickEvent(customer_demand_goods_table, data ->{
            tablePosition = customer_demand_goods_table.getSelectionModel().getSelectedIndex();
            moreProductButtonClick();
        }, 2);
    }


    /**
     * 设置菜单按钮状态
     * @param state
     */
    private void setMenu(boolean state) {
        menu_clearAll.setDisable(state);
        menu_commit.setDisable(state);
//        menu_insert.setDisable(state);
        menu_delete.setDisable(state);
    }

    /**
     * 设置所有控件变为可编辑状态
     */
    @FXML
    public void updateController() {
        setMainController(false);
    }

    /**
     * 设置所有控件为不可编辑状态
     */
    @FXML
    public void updateControllerDisable() {
        setMainController(true);
    }

    /**
     * 设置主要信息和tableview控件编辑状态
     *
     * @param state （可编辑、不可编辑）
     */
    private void setMainController(boolean state) {
        customer_no.setDisable(state);
        customer_name.setDisable(state);
        build_number.setDisable(state);
        customer_str.setDisable(state);
        customer_type.setDisable(state);
        contacts.setDisable(state);
        fax.setDisable(state);
        address.setDisable(state);
        remark.setDisable(state);
        zip.setDisable(state);
        create_people.setDisable(state);
        telephone.setDisable(state);
        create_date.setDisable(state);
        customer_demand_goods_table.setDisable(state);
        btn_create_no.setDisable(state);
        btn_customer_no.setDisable(state);
        setMenu(state);

    }


    /**
     * 获取列表详情
     */
    public void getDetailCustomerDemanGoods() {
        if (!"".equals(id.getText())) {
            customer_demand_goods_table.setItems(null);
            customer_demand_goods_table.setEditable(true);
            List<CustomerDemandGoodsInfo> list = iCustomerDemandGoodsInfoService.listCustomerDemandGoodsInfoByCustomerDemandId(Long.valueOf(id.getText()));
            List<CustomerDemandGoodsInfoProperty> newlist = new ArrayList<>();
            for (CustomerDemandGoodsInfo p : list) {
                newlist.add(new CustomerDemandGoodsInfoProperty(p.getId(), p.getProductNo(), p.getProductName(), p.getBrand(), p.getQuantityDemand().toString(), p.getPrice().toString(), p.getProductCategories(), p.getProductNature(), p.getManufactureMethod(), p.getProcessMethod(), p.getContinuity(), p.getCutOil(), p.getUseAmount().toString(), p.getCutSpeed()));
            }

//            col_no.setCellFactory(column -> EditCell.createStringEditCell());
//            col_name.setCellFactory(column -> EditCell.createStringEditCell());
            col_brand.setCellFactory(column -> EditCell.createStringEditCell());
            col_quantity_demand.setCellFactory(column -> EditCell.createStringEditCell());
            col_price.setCellFactory(column -> EditCell.createStringEditCell());
            col_product_categories.setCellFactory(column -> EditCell.createStringEditCell());
            col_product_nature.setCellFactory(column -> EditCell.createStringEditCell());
            col_manufacture_method.setCellFactory(column -> EditCell.createStringEditCell());
            col_process_method.setCellFactory(column -> EditCell.createStringEditCell());
            col_continuity.setCellFactory(column -> EditCell.createStringEditCell());
            col_cut_oil.setCellFactory(column -> EditCell.createStringEditCell());
            col_use_amount.setCellFactory(column -> EditCell.createStringEditCell());
            col_cut_speed.setCellFactory(column -> EditCell.createStringEditCell());

            // 查询地址集合
            col_id.setCellValueFactory(new PropertyValueFactory("id"));
            col_no.setCellValueFactory(new PropertyValueFactory("productNo"));
            col_name.setCellValueFactory(new PropertyValueFactory("productName"));
            col_brand.setCellValueFactory(new PropertyValueFactory("brand"));
            col_quantity_demand.setCellValueFactory(new PropertyValueFactory("quantityDemand"));
            col_price.setCellValueFactory(new PropertyValueFactory("price"));
            col_product_categories.setCellValueFactory(new PropertyValueFactory("productCategories"));
            col_product_nature.setCellValueFactory(new PropertyValueFactory("productNature"));
            col_manufacture_method.setCellValueFactory(new PropertyValueFactory("manufactureMethod"));
            col_process_method.setCellValueFactory(new PropertyValueFactory("processMethod"));
            col_continuity.setCellValueFactory(new PropertyValueFactory("continuity"));
            col_cut_oil.setCellValueFactory(new PropertyValueFactory("cutOil"));
            col_use_amount.setCellValueFactory(new PropertyValueFactory("useAmount"));
            col_cut_speed.setCellValueFactory(new PropertyValueFactory("cutSpeed"));

            customer_demand_goods_table.setItems(FXCollections.observableArrayList(newlist));


        }
    }

    /**
     * 删除
     */
    @FXML
    public void deleteCustomerDemandGoods() {
        if (!"".equals(id.getText())) {
            iCustomerDemandGoodsInfoService.deleteCustomerDemandGoodsInfoByCustomerDemandId(Long.valueOf(id.getText()));
            int rows = iCustomerDemandGoodsService.delete(Long.valueOf(id.getText()));
            if (rows > 0) {
                firstData();
                alert_informationDialog("删除成功！");
            } else {
                alert_informationDialog("删除失败！");
            }
        }
    }

    /**
     * enter 键盘按下触发
     *
     * @param event
     */
    public void tableViewInsertKey(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addDemand();
        }
    }


    /**
     * 添加tableview行
     */
    public void addDemand() {

        ObservableList<CustomerDemandGoodsInfoProperty> list = customer_demand_goods_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }

        //====================================== 重写Cell - Ben =================================================
        // Button
        Callback<TableColumn<CustomerShippingAddress, String>, TableCell<CustomerShippingAddress, String>> buttonFactory
                = new Callback<TableColumn<CustomerShippingAddress, String>, TableCell<CustomerShippingAddress, String>>() {
            @Override
            public TableCell call(final TableColumn<CustomerShippingAddress, String> param) {
                final TableCell<CustomerShippingAddress, String> cell = new TableCell<CustomerShippingAddress, String>() {


                    final Button btn1 = new Button("测试按钮");

                    @Override
                    public void updateItem(String ite, boolean empty) {
                        super.updateItem(ite, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn1.setOnAction((ActionEvent t)
                                    -> {
                                int selectdIndex = getTableRow().getIndex();
                                System.out.println(selectdIndex);

                                CustomerDemandGoodsInfo selectedRecord = (CustomerDemandGoodsInfo) customer_demand_goods_table.getItems().get(selectdIndex);
                                ObservableList<CustomerDemandGoodsInfo> subDataList = FXCollections.observableArrayList(new CustomerDemandGoodsInfo());
                                // 参数
                            });
                            btn1.setMaxWidth(Double.MAX_VALUE);
                            setGraphic(btn1);
                            setText(null);

                        }
                    }
                };
                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        };
        // ComBobox
        Callback<TableColumn<CustomerShippingAddress, String>, TableCell<CustomerShippingAddress, String>> comBoboxFactory
                =  new Callback<TableColumn<CustomerShippingAddress, String>, TableCell<CustomerShippingAddress, String>>() {
                    @Override
                    public TableCell call(final TableColumn<CustomerShippingAddress, String> param) {
                        final TableCell<CustomerShippingAddress, String> cell = new TableCell<CustomerShippingAddress, String>() {

                            ObservableList<String> options
                                    = FXCollections.observableArrayList(
                                    "测试选项 1",
                                    "测试选项 2",
                                    "测试选项 3"
                            );

                            final ComboBox comb = new ComboBox(options);

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    //  System.out.println("This is working");
                                    comb.setMaxWidth(Double.MAX_VALUE);
                                    setGraphic(comb);
                                    setText(null);
                                }
                            }
                        };
                        cell.setStyle("-fx-alignment: CENTER;");
                        return cell;
                    }
                };
        // Checkbox
        Callback<TableColumn<CustomerShippingAddress, String>, TableCell<CustomerShippingAddress, String>> checkboxFactory
                = new Callback<TableColumn<CustomerShippingAddress, String>, TableCell<CustomerShippingAddress, String>>() {
            @Override
            public TableCell call(final TableColumn<CustomerShippingAddress, String> param) {
                final TableCell<CustomerShippingAddress, String> cell = new TableCell<CustomerShippingAddress, String>() {

                    final CheckBox checkBox = new CheckBox("测试复选框");

                    @Override
                    public void updateItem(String ite, boolean empty) {
                        super.updateItem(ite, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            checkBox.setOnAction((ActionEvent t)
                                    -> {
                                int selectdIndex = getTableRow().getIndex();
                                System.out.println(selectdIndex);
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
        //====================================== 重写Cell - End =================================================

//        CustomerDemandGoodsInfo demand = new CustomerDemandGoodsInfo();//构建值对象
//
//        customer_demand_goods_table.setEditable(true);
//
//        col_no.setCellFactory(column -> EditCell.createStringEditCell());
//        col_name.setCellFactory(column -> EditCell.createStringEditCell());
//        col_brand.setCellFactory(column -> EditCell.createStringEditCell());
//        col_quantity_demand.setCellFactory(column -> EditCell.createStringEditCell());
//        col_price.setCellFactory(column -> EditCell.createStringEditCell());
//        col_product_categories.setCellFactory(column -> EditCell.createStringEditCell());
//        col_product_nature.setCellFactory(column -> EditCell.createStringEditCell());
//        col_manufacture_method.setCellFactory(column -> EditCell.createStringEditCell());
//        col_process_method.setCellFactory(column -> EditCell.createStringEditCell());
//        col_continuity.setCellFactory(column -> EditCell.createStringEditCell());
//        col_cut_oil.setCellFactory(column -> EditCell.createStringEditCell());
//        col_use_amount.setCellFactory(column -> EditCell.createStringEditCell());
//        col_cut_speed.setCellFactory(column -> EditCell.createStringEditCell());
//
//        // 查询地址集合
//        col_id.setCellValueFactory(new PropertyValueFactory<CustomerDemandGoodsInfo, Long>("id"));
//        col_quantity_demand.setCellValueFactory(new PropertyValueFactory<CustomerDemandGoodsInfo, Integer>("quantityDemand"));
//        col_price.setCellValueFactory(new PropertyValueFactory<CustomerDemandGoodsInfo, Double>("price"));
//        col_product_categories.setCellValueFactory(new PropertyValueFactory("productCategories"));
//        col_product_nature.setCellValueFactory(new PropertyValueFactory("productNature"));
//        col_manufacture_method.setCellValueFactory(new PropertyValueFactory("manufactureMethod"));
//        col_process_method.setCellValueFactory(new PropertyValueFactory("processMethod"));
//        col_continuity.setCellValueFactory(new PropertyValueFactory("continuity"));
//        col_cut_oil.setCellValueFactory(new PropertyValueFactory("cutOil"));
//        col_use_amount.setCellValueFactory(new PropertyValueFactory<CustomerDemandGoodsInfo, Integer>("useAmount"));
//        col_cut_speed.setCellValueFactory(new PropertyValueFactory("cutSpeed"));

        list.add(new CustomerDemandGoodsInfoProperty("", "", "", "", "", "", "", "", "", "", "", "", ""));        //list添加值对象
        customer_demand_goods_table.setItems(list); //tableview添加list

    }



    /**
     * 根据id查询客户需求商品的基本信息
     */
    public void getCustomerDemandGoodsById() {
        if (!("".equals(id.getText()))) {
            CustomerDemandGoods customerDemandGoods = iCustomerDemandGoodsService.selectByKey(Long.valueOf(id.getText()));
            setCustomerDemandGoodsVal(customerDemandGoods);
        }
    }

    /**
     * 刷新数据
     *
     * @param demand
     */
    private void setCustomerDemandGoodsVal(CustomerDemandGoods demand) {
        clearCustomerDemandGoodsVal();
        if (demand.getCustomerId() != null) {
            customerInfoCombox(demand.getCustomerId().toString());
        }
        if (demand.getId() != null) {
            id.setText(demand.getId().toString());
        }
        if (demand.getCustomerNo() != null) {
            customer_no.setText(demand.getCustomerNo());
        }
        if (demand.getCustomerName() != null) {
            customer_name.setText(demand.getCustomerName());
        }
        if (demand.getCreateNo() != null) {
            build_number.setText(demand.getCreateNo());
        }
        if (demand.getCustomerType() != null) {
            setComboBox(10L, customer_type, demand.getCustomerType()+0);
        }
        if (demand.getCreateNo() != null) {
            build_number.setText(demand.getCreateNo());
        }
        if (demand.getContacts() != null) {
            customerContactsList.forEach(p -> {
                if (demand.getContacts() == p.getId()) {
                    contacts.getSelectionModel().select(p.getPrimaryContacts());
                    return;
                }
            });
        }
        if (demand.getFax() != null) {
            customerContactsList.forEach(p -> {
                if (demand.getFax() == p.getId()) {
                    fax.getSelectionModel().select(p.getFax());
                    return;
                }
            });
        }
        if (demand.getAddress() != null) {
            address.setText(demand.getAddress());
        }
        if (demand.getRemark() != null) {
            remark.setText(demand.getRemark());
        }
        if (demand.getZip() != null) {
            zip.setText(demand.getZip());
        }
        if (demand.getCreatePeople() != null) {
            create_people.setText(demand.getCreatePeople());
        }
        telephone.getSelectionModel().selectFirst();
        create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(demand.getCreateDate(), DateUtils.FORMAT_YYYY_MM_DD), formatter));
        if (demand.getCustomerId() != null) {
            customer_id.setText(demand.getCustomerId().toString());
        }
        // 查详情
        getDetailCustomerDemanGoods();
    }

    /**
     * 清空上边的值
     */
    @FXML
    public void clearCustomerDemandGoodsVal() {
        customerContactsList.clear();
        LocalDateTime localDate = LocalDateTime.now();
        id.clear();
        customer_no.clear();
        customer_name.clear();
        build_number.clear();
        customer_str.clear();
        customer_type.getSelectionModel().clearSelection();
        contacts.getSelectionModel().clearSelection();
        contacts.setItems(null);
        contacts.getSelectionModel().clearSelection();
        fax.setItems(null);
        fax.getSelectionModel().clearSelection();
        address.clear();
        remark.clear();
        zip.clear();
        create_people.clear();
        telephone.setItems(null);
        telephone.getSelectionModel().clearSelection();
        create_date.setValue(localDate.toLocalDate());
        customer_id.clear();
        customer_demand_goods_table.setItems(null);
    }

    /**
     * 保存数据
     */
    @FXML
    private void save() {
        CustomerDemandGoods demand = new CustomerDemandGoods();
        if (!("".equals(id.getText()))) {
            demand.setId(Long.valueOf(id.getText()));
        }
        if (!("".equals(customer_no.getText()))) {
            demand.setCustomerNo(customer_no.getText());
        }
        if (!("".equals(customer_name.getText()))) {
            demand.setCustomerName(customer_name.getText());
        }
        if (customer_type.getSelectionModel().getSelectedItem() != null) {
            demand.setCustomerType(customer_type.getSelectionModel().getSelectedIndex());
        }
        if (contacts.getSelectionModel().getSelectedItem() != null) {
            customerContactsList.forEach(p -> {
                if (contacts.getValue().toString().equals(p.getPrimaryContacts())) {
                    demand.setContacts(p.getId());
                    return;
                }
            });
        }
        if (fax.getSelectionModel().getSelectedItem() != null) {
            customerContactsList.forEach(p -> {
                if (fax.getValue().toString().equals(p.getFax())) {
                    demand.setFax(p.getId());
                    return;
                }
            });
        }
        if (!("".equals(address.getText()))) {
            demand.setAddress(address.getText());
        }
        if (!("".equals(remark.getText()))) {
            demand.setRemark(remark.getText());
        }
        if (!("".equals(zip.getText()))) {
            demand.setZip(zip.getText());
        }
        if (!("".equals(create_people.getText()))) {
            demand.setCreatePeople(create_people.getText());
        }
        if (telephone.getSelectionModel().getSelectedItem() != null) {
            customerContactsList.forEach(p -> {
                if (telephone.getSelectionModel().getSelectedItem().toString().equals(p.getMobilePhone())) {
                    demand.setPhone(p.getId());
                    return;
                }
            });
        }
        if (create_date.getValue() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = df.parse(create_date.getValue().toString());
                demand.setCreateDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!("".equals(customer_id.getText())) && !("".equals(build_number.getText()))) {
            demand.setCustomerId(Long.valueOf(customer_id.getText()));
            demand.setCreateNo(build_number.getText());
            // 建档编号是否已经存在
            Boolean bool = iCustomerDemandGoodsService.getCheckCustomerDemandGoodsIsStorage(Long.valueOf(build_number.getText()));
            if (!bool) {
                int rows = iCustomerDemandGoodsService.save(demand);
                if (rows > 0) {
                    // 刷新数据（带出id）
                    setCustomerDemandGoodsVal(demand);
                    updateControllerDisable();
                    alert_informationDialog("新增成功！");
                } else {
                    alert_informationDialog("新增失败！");
                }
            } else {
                int rows = iCustomerDemandGoodsService.updateNotNull(demand);
                if (rows > 0) {
                    updateControllerDisable();
                    alert_informationDialog("修改成功！");
                } else {
                    alert_informationDialog("修改失败！");
                }
            }

        } else {
            alert_informationDialog("客户id不能为空！");
        }

        //已存在
        //未存在

        saveTableviewDemandGoods(); // 保存客户需求商品
    }

    /**
     * 新增数据
     */
    @FXML
    private void add() {
        clearCustomerDemandGoodsVal();
        updateController();
        LocalDateTime localDate = LocalDateTime.now();
        create_date.setValue(localDate.toLocalDate());
        build_number.setText(new Date().getTime()+getRandomone());
        createPeople(create_people);
    }

    /**
     * 保存 客户需求产品 tableview数据
     */
    private void saveTableviewDemandGoods(){

        String orderid = id.getText();
        if(orderid != null && !"".equals(orderid)){
            int tableSize = customer_demand_goods_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                CustomerDemandGoodsInfoProperty property = null;
                try {
                    if(customer_demand_goods_table.getItems().get(i) != null){
                        property = (CustomerDemandGoodsInfoProperty)customer_demand_goods_table.getItems().get(i);
                    }
                    CustomerDemandGoodsInfo product = new CustomerDemandGoodsInfo();

                    if(property.getBrand() != null && !"".equals(property.getBrand())){
                        product.setBrand(property.getBrand());
                    }
                    if(property.getContinuity() != null && !"".equals(property.getContinuity())){
                        product.setContinuity(property.getContinuity());
                    }
                    if(property.getCutOil() != null && !"".equals(property.getCutOil())){
                        product.setCutOil(property.getCutOil());
                    }
                    if(property.getCutSpeed() != null && !"".equals(property.getCutSpeed())){
                        product.setCutSpeed(property.getCutSpeed());
                    }
                    if(property.getManufactureMethod() != null && !"".equals(property.getManufactureMethod())){
                        product.setManufactureMethod(property.getManufactureMethod());
                    }
                    if(property.getPrice() != null && !"".equals(property.getPrice())){
                        product.setPrice(Float.valueOf(property.getPrice()));
                    }
                    if(property.getProcessMethod() != null && !"".equals(property.getProcessMethod())){
                        product.setProcessMethod(property.getProcessMethod());
                    }
                    if(property.getProductCategories() != null && !"".equals(property.getProductCategories())){
                        product.setProductCategories(property.getProductCategories());
                    }
                    if(property.getProductName() != null && !"".equals(property.getProductName())){
                        product.setProductName(property.getProductName()); ;
                    }
                    if(property.getProductNature() != null && !"".equals(property.getProductNature())){
                        product.setProductNature(property.getProductNature());
                    }
                    if(property.getProductNo() != null && !"".equals(property.getProductNo())){
                        product.setProductNo(property.getProductNo());
                    }
                    if(property.getQuantityDemand() != null && !"".equals(property.getQuantityDemand())){
                        product.setQuantityDemand(Integer.valueOf(property.getQuantityDemand()));
                    }
                    if(property.getUseAmount() != null && !"".equals(property.getUseAmount())){
                        product.setUseAmount(Integer.valueOf(property.getUseAmount()));
                    }
                    product.setCustomerDemandGoodsId(Long.valueOf(orderid));

                    if(property.getId() == null){
                        product.setAddtime(new Date());
                        iCustomerDemandGoodsInfoService.save(product);
                    }else {
                        product.setId(property.getId());
                        iCustomerDemandGoodsInfoService.updateNotNull(product);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 下一条数据
     */
    @FXML
    private void nextData() {
        int max = iCustomerDemandGoodsService.getCustomerDemandGoodsCount();
        if (page < max-1) {
            page += 1;
        }
        CustomerDemandGoods demandGoods = iCustomerDemandGoodsService.getCustomerDemandGoodsByPage(page, rows);
        setCustomerDemandGoodsVal(demandGoods);
    }

    /**
     * 上一条数据
     */
    @FXML
    private void prevData() {
        if (page > 0) {
            page -= 1;
        }
        CustomerDemandGoods demandGoods = iCustomerDemandGoodsService.getCustomerDemandGoodsByPage(page, rows);
        setCustomerDemandGoodsVal(demandGoods);
    }

    /**
     * 第一条数据
     */
    @FXML
    private void firstData() {
        CustomerDemandGoods demand = iCustomerDemandGoodsService.getFirstCustomerDemandGoods();
        setCustomerDemandGoodsVal(demand);
    }

    /**
     * 最后一条数据
     */
    @FXML
    private void lastData() {
        CustomerDemandGoods demand = iCustomerDemandGoodsService.getLastCustomerDemandGoods();
        setCustomerDemandGoodsVal(demand);
    }

    /**
     * 客户类型下拉框
     */
    public void customerCategoryCombox() {
        setComboBox(10L, customer_type, 0);
    }

    /**
     * 客户Id变更监听
     */
    public void customerIdChange() {
        customer_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String customerid = customer_id.getText();
                if (customerid != null && !("".equals(customerid))) {
//                    customerInfoCombox(customerid);
                    getDetailCustomerDemanGoods();
                }
            }
        });
    }

    /**
     * 查询客户基本信息（联系电话、传真、联系人）
     *
     * @param customerid
     */
    public void customerInfoCombox(String customerid) {
        customerContactsList = iCustomerContactsService.listCustomerContactsByCustomerId(Long.valueOf(customerid));
        List<String> phoneList = new ArrayList<>();
        List<String> faxList = new ArrayList<>();
        List<String> contactsList = new ArrayList<>();
        customerContactsList.forEach(p -> {
            phoneList.add(p.getMobilePhone());
            faxList.add(p.getFax());
            contactsList.add(p.getPrimaryContacts());
        });
        telephone.setItems(FXCollections.observableArrayList(phoneList));
        telephone.getSelectionModel().selectFirst();
        telephone.setTooltip(new Tooltip("请选择联系电话"));

        fax.setItems(FXCollections.observableArrayList(faxList));
        fax.getSelectionModel().selectFirst();
        fax.setTooltip(new Tooltip("请选择传真"));

        contacts.setItems(FXCollections.observableArrayList(contactsList));
        contacts.getSelectionModel().selectFirst();
        contacts.setTooltip(new Tooltip("请选择联系人"));

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
                    id.setUserData(newItem.getId());
                }
            }
        });


        tableViewProduct.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });


    }

    public void moreProductButtonClick(){
        stage.setTitle("现有产品基本查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/customer/product_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        loadMoreProduct();
    }

    public void closeSureWin(){
        long productid =(long)id.getUserData();
        ProductBasic productBasic =  productBasicService.selectByKey(productid);
        // 把数据绑到当前选中的行中
        // todo...
        ObservableList<CustomerDemandGoodsInfoProperty> list = customer_demand_goods_table.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        for (int i = 0; i < list.size(); i++) {
            if(tablePosition == i){
                list.get(i).setProductName(productBasic.getProname());
                list.get(i).setProductNo(productBasic.getIsnum());
            }
        }
        customer_demand_goods_table.setItems(FXCollections.observableArrayList(list));
        coseWin();
    }

    public void coseWin(){
        stage.close();
        tablePosition = 0;
    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO  现有客户查询
     * @Date 20:22 2018/8/15
     * @Param []
     **/
    @FXML
    public void CurrentClientQuery() {
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将第二个窗口保存到map中
        StageManager.STAGE.put("CustomerDemandGoodsControllerStage", stage);
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //建档编号（客户需求商品查询）
     * @Date 20:22 2018/8/15
     * @Param []
     **/
    @FXML
    public void CustomerDemandGoodsQuery() {
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将第二个窗口保存到map中
        StageManager.STAGE.put("CustomerDemandGoodsQuery", stage);
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/customer_demand_goods_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }


}
