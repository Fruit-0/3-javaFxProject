package com.yc.education.controller.customer;


import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.DataSetting;
import com.yc.education.model.customer.*;
import com.yc.education.property.RemarkProperty;
import com.yc.education.service.DataSettingService;
import com.yc.education.service.UsersService;
import com.yc.education.service.customer.*;
import com.yc.education.util.DateUtils;
import com.yc.education.util.EditCell;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.collections.*;

/**
 * 客户基本资料
 *
 * @Author: BlueSky
 * @Date: 2018/8/15 15:08
 */
@Controller
public class CustomerBasicInfoController extends BaseController implements Initializable {
    // 送货地址
    @Autowired
    ICustomerShippingAddressService addressService;
    // 用户
    @Autowired
    UsersService usersService;
    // 客户基本信息
    @Autowired
    ICustomerService iCustomerService;
    // 客户基本资料
    @Autowired
    ICustomerBasicService iCustomerBasicService;

    // 客户来源信息
    @Autowired
    ICustomerSourceService iCustomerSourceService;
    // 客户来源信息
    @Autowired
    IIndustryService iIndustryService;
    // 客户来源信息
    @Autowired
    IAreaService iAreaService;
    // 客户详情
    @Autowired
    ICustomerDetailInfoService iCustomerDetailInfoService;
    // 业务负责人
    @Autowired
    ICustomerBusinessLeaderService iCustomerBusinessLeaderService;
    // 联系人
    @Autowired
    ICustomerContactsService iCustomerContactsService;
    // 资料维护
    @Autowired
    ICustomerDataMaintenanceService iCustomerDataMaintenanceService;
    // 备注及留言
    @Autowired
    IRemarkService iRemarkService;
    // 送货地址
    @Autowired
    ICustomerShippingAddressService iCustomerShippingAddressService;
    // 基本数据
    @Autowired
    DataSettingService iDataSettingService;
    // 发票详情
    @Autowired
    IInvoiceService iInvoiceService;
    //======================================== 送货地址=============================================
    @FXML
    TableView shipping_address_table;
    @FXML
    private TableColumn address_col_id;
    @FXML
    private TableColumn address_col_contact;
    @FXML
    private TableColumn address_col_address;
    @FXML
    private TableColumn address_col_phone;
    @FXML
    private TableColumn address_col_setting;
    @FXML
    private TableColumn address_col_type;
    @FXML
    private TableColumn address_col_title;
    @FXML
    private ComboBox address_combox_type;
    @FXML
    private TextField address_txt_contact;
    @FXML
    private TextField address_txt_info;
    @FXML
    private TextField address_txt_phone;
    @FXML
    private TextField address_txt_name; // 名称
    @FXML
    Button address_btn_sure;
    /*================================================== 上边模块 =====================================================================*/
    // 客户编号
    @FXML
    public TextField code;
    // 客户姓名
    @FXML
    public TextField customer_name;
    // 注册地址
    @FXML
    public TextField register_address;
    // 客户简称
    @FXML
    public TextField customer_initials;
    // 客户隐藏id
    @FXML
    public TextField customer_id;

    @FXML Button more;

    // 当前页
    private static int page = 0;
    // 页大小
    private final static int rows = 1;
    //=========================================== 基本信息 =====================================================
    // 国家id下拉框
    @FXML
    private ComboBox base_combox_country_id;
    //国家文本框
    @FXML
    private TextField base_country;
    @FXML
    private TextField base_phone;
    @FXML
    private ComboBox base_payment_method; // 付款方式
    @FXML
    private TextField base_initial_quota;
    @FXML
    private ComboBox base_initial_quota_money_type;
    @FXML
    private DatePicker  base_rush_money_date;
    @FXML
    private TextField base_shipping_address;
    @FXML
    private TextField base_shipping_address_remark;
    @FXML
    private TextField base_primary_contact;
    @FXML
    private TextField base_archivist;
    @FXML
    private TextField base_last_modifier;
    @FXML
    private DatePicker base_establish_date;
    @FXML
    private TextField base_zip_code;
    @FXML
    private TextField base_fax;
    @FXML
    private TextField base_credit_line;
    @FXML
    private ComboBox base_credit_line_money_type;
    @FXML
    private TextField base_credit_line_remark;
    @FXML
    private TextField base_minimum_discount;
    @FXML
    private TextField base_contact_number;
    @FXML
    private DatePicker base_document_date;
    @FXML
    private DatePicker base_last_modified_date;
    @FXML
    private TextField base_stop_use;
    // 国家名称
    List<DataSetting> countryList = new ArrayList<>();
    //========================================== 详细资料 =============================================
    //客户类别
    @FXML
    ComboBox info_customer_category;
    // 行业
    @FXML
    ComboBox info_industry;
    // 客户来源
    @FXML
    ComboBox info_customer_source;
    // 开户银行
    @FXML
    ComboBox info_bank;
    // 银行账户
    @FXML
    TextField info_bank_acount;
    // 税务登记号
    @FXML
    TextField info_tax_no;
    // 所属区域
    @FXML
    ComboBox info_area_com;
    // 所属区域
    @FXML
    TextField info_area_str;
    // 账款备注
    @FXML
    TextField info_acount_remark;
    // 所属地区
    @FXML
    ComboBox info_region;
    // 客户等级
    @FXML
    ComboBox info_customer_level;
    // 所属公司
    @FXML
    ComboBox info_company;
    // 销售单需回传
    @FXML
    CheckBox info_comes_back;
    // 发票详情tableview
    @FXML
    TableView customer_invoice_tab;
    @FXML
    TableColumn invoice_id_col;
    @FXML
    TableColumn invoice_usual_col;
    @FXML
    TableColumn invoice_title_col;
    @FXML
    TableColumn invoice_address_col;
    @FXML
    TableColumn invoice_phone_col;
    @FXML
    TableColumn invoice_contact_col;
    @FXML
    TableColumn invoice_remark_col;

    // 下拉框数据集合
    static List<CustomerSource> customerSourceList = new ArrayList<>();
    static List<Area> areaList = new ArrayList<>();
    //================================================== 业务负责人 =====================================================
    // id
    @FXML
    TableColumn leader_id;
    // 员工编号
    @FXML
    TableColumn leader_no;
    // 姓名
    @FXML
    TableColumn leader_name;
    // 主要负责人
    @FXML
    TableColumn leader_primary;
    // 备注
    @FXML
    TableColumn leader_remark;
    // 业务负责人tableview
    @FXML
    TableView customer_business_leader_tab;

    //================================================== 联系人 =====================================================
    // id
    @FXML
    TableColumn contact_id;
    // 主要负责人
    @FXML
    TableColumn contact_primary;
    // 姓名
    @FXML
    TableColumn contact_name;
    // 部门
    @FXML
    TableColumn contact_department;
    // 职务
    @FXML
    TableColumn contact_position;
    // 电话
    @FXML
    TableColumn contact_telephone;
    // 传真
    @FXML
    TableColumn contact_fax;
    // 手机
    @FXML
    TableColumn contact_phone;
    // 邮箱
    @FXML
    TableColumn contact_mail;
    // 备注
    @FXML
    TableColumn contact_remark;
    // 联系人tableview
    @FXML
    TableView customer_contact_tab;
    //===================================== 资料维护 ============================================
    // ID号
    @FXML
    TableColumn data_id;
    // 建档编号
    @FXML
    TableColumn data_no;
    // 建档日期
    @FXML
    TableColumn data_create_date;
    // 负责人
    @FXML
    TableColumn data_leader_people;
    // 采购人
    @FXML
    TableColumn data_purcharse_people;
    // 联络人
    @FXML
    TableColumn data_contact;
    // 电话
    @FXML
    TableColumn data_telephone;
    // 传真
    @FXML
    TableColumn data_fax;
    // 创业年度
    @FXML
    TableColumn data_startup_year;
    // 员工数量
    @FXML
    TableColumn data_employee_number;
    // 去年营业
    @FXML
    TableColumn data_last_year_business;
    // 今年预计
    @FXML
    TableColumn date_year_plan;
    // 工业形态
    @FXML
    TableColumn date_industry;
    // 资料维护tableview
    @FXML
    TableView customer_data_maintenance_tab;
    //===================================== 备注及说明 ============================================
    // ID号
    @FXML
    TableColumn remark_id;
    // 备注及说明
    @FXML
    TableColumn remark_remark;
    // 备注及说明tableview
    @FXML
    TableView customer_remark_tab;

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

    //===================================== 全局 ============================================
    // TAB 大面板
    @FXML
    TabPane tab;
    // 存储当前面板选项卡位置
    private String current_pane_str = "基本资料";

    // 日期格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINA);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime localDate = LocalDateTime.now();

    /**
     * 初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // 国家-复选框值
        base_combox_countrySetVal();
        // 地址类型-复选框值
        address_combox_typeSetVal();
        // 付款方式
        setComboBox(20L,base_payment_method,0);
        // 加载第一条客户数据
        firstCustomer();
        // 基本资料-复选框-金钱类型
        setMoneyComboxVal();

        // 客户编号变更监听
        customerCodeChange();

        //查询面板中的所有内容
        selectAllPane();

        // tabpane 面板切换监听
//        tab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
//                String customerid = customer_id.getText();
//                current_pane_str = tab.getSelectionModel().getSelectedItem().getText().toString();
//                switchCase(current_pane_str,customerid);
//            }
//        });

        // 设置所有控件不可编辑
        updateControllerDisable();

    }

    /*********************************************** 控件禁用 ***************************************************/

    /**
     * 设置菜单按钮状态
     * @param state
     */
    private void setMenu(boolean state){
        menu_clearAll.setDisable(state);
        menu_commit.setDisable(state);
//        menu_insert.setDisable(state);
        menu_delete.setDisable(state);
    }

    /**
     * 设置控件可编辑状态
     */
    @FXML
    public void updateController(){
        allController(false);
    }

    /**
     * 设置控件不可编辑
     */
    private void updateControllerDisable(){
        allController(true);
    }

    /**
     *  所有控件状态
     * @param state
     */
    private void allController(boolean state){
        setMainController(state);
        setBasicController(state);
        setAddressController(state);
        setBusinessController(state);
        setContactsController(state);
        setDateMaintainController(state);
        setInfoController(state);
        setNoteController(state);
        setMenu(state);
    }

    /**
     * 设置备注及说明信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setNoteController(boolean state){
        customer_remark_tab.setDisable(state);
    }


    /**
     * 设置资料维护信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setDateMaintainController(boolean state){
        customer_data_maintenance_tab.setDisable(state);
    }

    /**
     * 设置送货地址信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setAddressController(boolean state){
        shipping_address_table.setDisable(state);
        address_combox_type.setDisable(state);
        address_txt_contact.setDisable(state);
        address_txt_info.setDisable(state);
        address_txt_phone.setDisable(state);
        address_btn_sure.setDisable(state);
    }

    /**
     * 设置业务负责人信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setBusinessController(boolean state){
        customer_business_leader_tab.setDisable(state);
    }

    /**
     * 设置联系人信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setContactsController(boolean state){
        customer_contact_tab.setDisable(state);
    }

    /**
     * 设置详细你信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setInfoController(boolean state){
        info_customer_category.setDisable(state);
        info_industry.setDisable(state);
        info_customer_source.setDisable(state);
        info_bank.setDisable(state);
        info_bank_acount.setDisable(state);
        info_tax_no.setDisable(state);
        info_area_com.setDisable(state);
        info_area_str.setDisable(state);
        info_acount_remark.setDisable(state);
        info_region.setDisable(state);
        info_customer_level.setDisable(state);
        info_company.setDisable(state);
        info_comes_back.setDisable(state);
    }

    /**
     * 设置基本信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setBasicController(boolean state){
        base_combox_country_id.setDisable(state);
        base_country.setDisable(state);
        base_phone.setDisable(state);
        base_payment_method.setDisable(state);
        base_initial_quota.setDisable(state);
        base_initial_quota_money_type.setDisable(state);
        base_rush_money_date.setDisable(state);
//        base_shipping_address.setDisable(state);
        base_shipping_address_remark.setDisable(state);
        base_primary_contact.setDisable(state);
//        base_archivist.setDisable(state);
//        base_last_modifier.setDisable(state);
//        base_establish_date.setDisable(state);
        base_zip_code.setDisable(state);
        base_fax.setDisable(state);
        base_credit_line.setDisable(state);
        base_credit_line_money_type.setDisable(state);
        base_credit_line_remark.setDisable(state);
        base_minimum_discount.setDisable(state);
        base_contact_number.setDisable(state);
//        base_document_date.setDisable(state);
//        base_last_modified_date.setDisable(state);
        base_stop_use.setDisable(state);
    }

    /**
     * 设置主要信息控件状态（可编辑、不可编辑）
     * @param state
     */
    private void setMainController(boolean state){
        code.setDisable(state);
        customer_name.setDisable(state);
        register_address.setDisable(state);
//        customer_initials.setDisable(state);

        more.setDisable(state);
    }

    /*************************************************** 备注及说明-开始 ************************************/

    /**
     *  刷新备注及说明数据
     */
    public void setCustomerRemarkVal(){
        String customerid = customer_id.getText();
        if(customerid != null && !"".equals(customerid)){
            customer_remark_tab.setEditable(true);

            remark_remark.setCellFactory(column -> EditCell.createStringEditCell());

            // 查询地址集合
            List<Remark> list = iRemarkService.listRemark(Long.valueOf(customerid), "1");
            List<RemarkProperty> listProperty = new ArrayList<>();
            if(list != null){
                list.forEach(p->{
                    listProperty.add(new RemarkProperty(p.getId(), p.getRemark()));
                });
            }
            final ObservableList<RemarkProperty> data = FXCollections.observableArrayList(listProperty);
            remark_id.setCellValueFactory(new PropertyValueFactory("id"));
            remark_remark.setCellValueFactory(new PropertyValueFactory("remark"));//映射

            customer_remark_tab.setItems(data);
        }

    }

    /**
     * 清空备注及说明数据
     */
    public void clearCustomerRemarkVal(){
        customer_remark_tab.setItems(null);
        customer_remark_tab.refresh();
    }
    /*************************************************** 备注及说明-结束 ************************************/

    /*************************************************** 资料维护-开始 ************************************/

    /**
     *  刷新资料 维护数据
     */
    public void setCustomerDataMaintenanceVal(String customerid){
        customer_data_maintenance_tab.setEditable(true);

        data_no.setCellFactory(column -> EditCell.createStringEditCell());
        data_create_date.setCellFactory(column -> EditCell.createStringEditCell());
        data_leader_people.setCellFactory(column -> EditCell.createStringEditCell());
        data_purcharse_people.setCellFactory(column -> EditCell.createStringEditCell());
        data_contact.setCellFactory(column -> EditCell.createStringEditCell());
        data_telephone.setCellFactory(column -> EditCell.createStringEditCell());
        data_fax.setCellFactory(column -> EditCell.createStringEditCell());
        data_startup_year.setCellFactory(column -> EditCell.createStringEditCell());
        data_employee_number.setCellFactory(column -> EditCell.createStringEditCell());
        data_last_year_business.setCellFactory(column -> EditCell.createStringEditCell());
        date_year_plan.setCellFactory(column -> EditCell.createStringEditCell());
        date_industry.setCellFactory(column -> EditCell.createStringEditCell());

        // 查询地址集合
        List<CustomerDataMaintenance> list = iCustomerDataMaintenanceService.listCustomerDataMaintenanceByCustomerId(Long.valueOf(customerid));
        List<CustomerDataMaintenanceProperty> listProperty = new ArrayList<>();
        if(list != null){
            list.forEach(p->{
                CustomerDataMaintenanceProperty dt = new CustomerDataMaintenanceProperty(p.getId(), p.getCustomerId().toString(), p.getDocumentNo(), p.getCreateDate().toString(), p.getLeaderPeople(), p.getPurchasePeople(), p.getContact(), p.getTelephone(), p.getFax(), p.getStartupYear().toString(), p.getEmployeeNumber().toString(),p.getLastYearBusiness() , p.getYearPlan(),p.getIndustry());
                dt.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                dt.setStartupYear(new SimpleDateFormat("yyyy-MM-dd").format(p.getStartupYear()));
                listProperty.add(dt);
            });
        }
        final ObservableList<CustomerDataMaintenanceProperty> data = FXCollections.observableArrayList(listProperty);
        data_id.setCellValueFactory(new PropertyValueFactory("id"));
        data_no.setCellValueFactory(new PropertyValueFactory("documentNo"));//映射
        data_create_date.setCellValueFactory(new PropertyValueFactory("createDate"));
        data_leader_people.setCellValueFactory(new PropertyValueFactory("leaderPeople"));
        data_purcharse_people.setCellValueFactory(new PropertyValueFactory("purchasePeople"));
        data_contact.setCellValueFactory(new PropertyValueFactory("contact"));
        data_telephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        data_fax.setCellValueFactory(new PropertyValueFactory("fax"));
        data_startup_year.setCellValueFactory(new PropertyValueFactory("startupYear"));
        data_employee_number.setCellValueFactory(new PropertyValueFactory("employeeNumber"));
        data_last_year_business.setCellValueFactory(new PropertyValueFactory("lastYearBusiness"));
        date_year_plan.setCellValueFactory(new PropertyValueFactory("yearPlan"));
        date_industry.setCellValueFactory(new PropertyValueFactory("industry"));

        customer_data_maintenance_tab.setItems(data);
        customer_data_maintenance_tab.refresh();

    }

    /**
     * 清空资料维护数据
     */
    public void clearCustomerDataMaintenanceVal(){
        customer_data_maintenance_tab.setItems(null);
        customer_data_maintenance_tab.refresh();
    }
    /*************************************************** 资料维护-结束 ************************************/

    /*************************************************** 联系人-开始 ************************************/

    /**
     *  刷新联系人数据
     */
    public void setCustomerContactsVal(String customerid){
        customer_contact_tab.setEditable(true);

        contact_primary.setCellFactory(column -> EditCell.createStringEditCell());
        contact_name.setCellFactory(column -> EditCell.createStringEditCell());
        contact_department.setCellFactory(column -> EditCell.createStringEditCell());
        contact_position.setCellFactory(column -> EditCell.createStringEditCell());
        contact_telephone.setCellFactory(column -> EditCell.createStringEditCell());
        contact_fax.setCellFactory(column -> EditCell.createStringEditCell());
        contact_phone.setCellFactory(column -> EditCell.createStringEditCell());
        contact_mail.setCellFactory(column -> EditCell.createStringEditCell());
        contact_remark.setCellFactory(column -> EditCell.createStringEditCell());

        // 查询地址集合
        List<CustomerContacts> list = iCustomerContactsService.listCustomerContactsByCustomerId(Long.valueOf(customerid));
        List<CustomerContactsProperty> listProperty = new ArrayList<>();
        if(list != null){
            list.forEach(p->{
                try {
                    listProperty.add(new CustomerContactsProperty(p.getId(), p.getCustomerId().toString(), p.getPrimaryContacts(), p.getName(), p.getDepartment(), p.getPosition(), p.getTelephone(), p.getFax(), p.getMobilePhone(), p.getEmail(), p.getRemark()));
                }catch (NullPointerException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        final ObservableList<CustomerContactsProperty> data = FXCollections.observableArrayList(listProperty);
        contact_id.setCellValueFactory(new PropertyValueFactory("id"));
        contact_primary.setCellValueFactory(new PropertyValueFactory("primaryContacts"));//映射
        contact_name.setCellValueFactory(new PropertyValueFactory("name"));
        contact_department.setCellValueFactory(new PropertyValueFactory("department"));
        contact_position.setCellValueFactory(new PropertyValueFactory("position"));
        contact_telephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        contact_fax.setCellValueFactory(new PropertyValueFactory("fax"));
        contact_phone.setCellValueFactory(new PropertyValueFactory("mobilePhone"));
        contact_mail.setCellValueFactory(new PropertyValueFactory("email"));
        contact_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        customer_contact_tab.setItems(data);
        customer_business_leader_tab.refresh();
    }

    /**
     * 清空联系人数据
     */
    public void clearCustomerContactsVal(){
        customer_contact_tab.setItems(null);
        customer_contact_tab.refresh();
    }
    /*************************************************** 联系人-结束 ************************************/

    /*************************************************** 业务负责人-开始 ************************************/

    /**
     *  刷新客户业务负责人数据
     */
    public void setCustomerBusinessLeaderVal(String customerid){
        customer_business_leader_tab.setEditable(true);

        leader_no.setCellFactory(column -> EditCell.createStringEditCell());
        leader_name.setCellFactory(column -> EditCell.createStringEditCell());
        leader_primary.setCellFactory(column -> EditCell.createStringEditCell());
        leader_remark.setCellFactory(column -> EditCell.createStringEditCell());

        // 查询地址集合
        List<CustomerBusinessLeader> list = iCustomerBusinessLeaderService.listCustomerBusinessLeaderByCustomerId(Long.valueOf(customerid));
        List<CustomerBusinessLeaderProperty> listProperty = new ArrayList<>();
        if(list != null){
            list.forEach(p->{
                try {
                    listProperty.add(new CustomerBusinessLeaderProperty(p.getId(), p.getCustomerId().toString(), p.getEmployeeCode(), p.getName(), p.getPrimaryPeople(), p.getRemark()));
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
            });
        }
        final ObservableList<CustomerBusinessLeaderProperty> data = FXCollections.observableArrayList(listProperty);
        leader_id.setCellValueFactory(new PropertyValueFactory("id"));
        leader_no.setCellValueFactory(new PropertyValueFactory("employeeCode"));//映射
        leader_name.setCellValueFactory(new PropertyValueFactory("name"));
        leader_primary.setCellValueFactory(new PropertyValueFactory("primaryPeople"));
        leader_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        customer_business_leader_tab.setItems(data);
    }

    /**
     * 清空业务负责人数据
     */
    public void clearCustomerBusinessLeaderVal(){
        customer_business_leader_tab.setItems(null);
        customer_business_leader_tab.refresh();
    }
    /*************************************************** 业务负责人-结束 ************************************/


    /********************************************* 详细信息-开始 ********************************************/

    /**
     * 显示发票详情
     */
    public void showInvoice(){
        String customerid = customer_id.getText();
        if(!"".equals(customerid) && customerid != null){

            customer_invoice_tab.setEditable(true);

            invoice_usual_col.setCellFactory(column -> EditCell.createStringEditCell());
            invoice_title_col.setCellFactory(column -> EditCell.createStringEditCell());
            invoice_address_col.setCellFactory(column -> EditCell.createStringEditCell());
            invoice_phone_col.setCellFactory(column -> EditCell.createStringEditCell());
            invoice_contact_col.setCellFactory(column -> EditCell.createStringEditCell());
            invoice_remark_col.setCellFactory(column -> EditCell.createStringEditCell());

            List<InvoiceProperty> propertyList = new ArrayList<>();
            List<Invoice> invoiceList = iInvoiceService.listInvoiceByCustomerId(Long.valueOf(customerid));
            if(invoiceList != null){
                invoiceList.forEach(p-> propertyList.add(new InvoiceProperty(p.getId(), p.getUsual(), p.getTitle(), p.getAddress(), p.getPhone(), p.getContact(), p.getRemark())));
            }
            ObservableList<InvoiceProperty> data = FXCollections.observableArrayList(propertyList);

            invoice_id_col.setCellValueFactory(new PropertyValueFactory("id"));
            invoice_usual_col.setCellValueFactory(new PropertyValueFactory("usual"));
            invoice_title_col.setCellValueFactory(new PropertyValueFactory("title"));//映射
            invoice_address_col.setCellValueFactory(new PropertyValueFactory("address"));
            invoice_phone_col.setCellValueFactory(new PropertyValueFactory("phone"));
            invoice_contact_col.setCellValueFactory(new PropertyValueFactory("contact"));
            invoice_remark_col.setCellValueFactory(new PropertyValueFactory<Customer,Date>("remark"));

            customer_invoice_tab.setItems(data);
        }

    }

    /**
     * 客户类型下拉框
     */
    public void customerCategoryCombox(){
        info_customer_category.getSelectionModel().selectFirst();
        setComboBox(10,info_customer_category,0);
        info_customer_category.setTooltip(new Tooltip("请选择客户类型"));
    }

    /**
     * 行业下拉框
     */
    public void industryCombox(){
        setComboBox(11, info_industry, 0);
        info_industry.setTooltip(new Tooltip("请选择行业类型"));
    }
    /**
     *  客户来源下拉框
     */
    public void customerSourceCombox(){
        customerSourceList  = iCustomerSourceService.listCustomerSourceAll();
        List<String> ids = new ArrayList<>();
        customerSourceList.forEach(p->{
            ids.add(p.getTitle());
        });
        info_customer_source.setItems(FXCollections.observableArrayList(ids.toArray())
        );
        info_customer_source.getSelectionModel().selectFirst();
        info_customer_source.setTooltip(new Tooltip("请选择客户来源"));
    }
    /**
     *  银行下拉框
     */
    public void bankCombox(){
        setComboBox(4, info_bank, 0);
        info_bank.setTooltip(new Tooltip("请选择银行"));
    }
    /**
     *  客户等级下拉框
     */
    public void customerLevelCombox(){
        setComboBox(14, info_customer_level, 0);
        info_customer_level.setTooltip(new Tooltip("请选择客户等级"));
    }
    /**
     *  地区下拉框-省份
     */
    public void areaCombox(){
        areaList = iAreaService.listProvice();
        List<String> ids = new ArrayList<>();
        areaList.forEach(p->{
            ids.add(p.getAreaname());
        });
        info_area_com.setItems(FXCollections.observableArrayList(ids.toArray()));
        info_area_com.getSelectionModel().selectFirst();
        info_area_com.setTooltip(new Tooltip("请选择区域"));


        info_region.setItems(FXCollections.observableArrayList(ids.toArray()));
        info_region.getSelectionModel().selectFirst();
        info_region.setTooltip(new Tooltip("请选择地区"));

        // 下拉框值改变事件监听
        info_area_com.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    info_area_str.setText(info_area_com.getValue().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 清空客户详细信息值
     */
    public void clearCustomerDetailInfoVal(){
        info_customer_category.getSelectionModel().selectFirst();
        info_industry.getSelectionModel().selectFirst();
        info_customer_source.getSelectionModel().selectFirst();
        info_bank.getSelectionModel().selectFirst();
        info_bank_acount.clear();
        info_tax_no.clear();
        info_area_com.getSelectionModel().selectFirst();
        info_area_str.clear();
        info_acount_remark.clear();
        info_region.getSelectionModel().selectFirst();
        info_customer_level.getSelectionModel().selectFirst();
        // 所属公司
//      info_company;
        info_comes_back.setSelected(false);
    }

    /**
     * 给客户详情赋值
     * @param info
     */
    public void setCustomerDetailInfoVal(CustomerDetailInfo info){
        if(info != null){
            if(info.getCustomerCategory() != null){
                try {
                    setComboBox(10, info_customer_category,info.getCustomerCategory());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(info.getIndustry() != null){
                setComboBox(11, info_industry, info.getIndustry());
            }
            if(info.getCustomerSource() != null){
                setComboBox(13, info_customer_source, info.getCustomerSource());
            }
            if(info.getBank() != null){
                setComboBox(4, info_bank, info.getBank());
            }
            if(!("".equals(info.getBankAccount())) && info.getBankAccount() != null){
                info_bank_acount.setText(info.getBankAccount());
            }
            if(!("".equals(info.getTaxRegister())) && info.getTaxRegister() != null){
                info_tax_no.setText(info.getTaxRegister());
            }
            if(info.getArea() != null){
                info_area_com.getSelectionModel().select(info.getArea());
            }
            if(!("".equals(info.getAreaInfo())) && info.getAreaInfo() != null){
                info_area_str.setText(info.getAreaInfo());
            }
            if(!("".equals(info.getAccountRemark())) && info.getAccountRemark() != null){
                info_acount_remark.setText(info.getAccountRemark());
            }
            if(info.getRegion() != null){
                info_region.getSelectionModel().select(info.getRegion());
            }
            if(info.getCustomerLevel() != null){
                setComboBox(14, info_customer_level, info.getCustomerLevel());
            }
            // 所属公司
//          info_company;
            info_comes_back.setSelected(info.getSentBack());
        }
    }

    /********************************************* 详细信息-结束 ********************************************/

    /********************************************* 基本信息-开始 ********************************************/


    /**
     * @Author BlueSky
     * @Description //TODO 基本信息-国家下拉框
     * @Date 11:40 2018/8/17
     **/
    public void base_combox_countrySetVal() {
        List<String> ids = new ArrayList<>();
        countryList = iDataSettingService.findDataSetting(28L);
        countryList.forEach(p->{
            ids.add(p.getTitle());
        });
        base_combox_country_id.setItems(FXCollections.observableArrayList(ids.toArray()));
        base_combox_country_id.setTooltip(new Tooltip("Select the country"));
        if(countryList.size() > 0){
            base_country.setText(countryList.get(0).getTitle());
        }
        // 下拉框值改变事件监听
        base_combox_country_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    base_country.setText(base_combox_country_id.getValue().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 清除基本信息
     */
    private void clearBasicVal(){
        base_combox_country_id.getSelectionModel().selectFirst();
        base_country.setText(null);
        base_phone.setText(null);
        base_payment_method.getSelectionModel().selectFirst();
        base_initial_quota.setText(null);
        base_initial_quota_money_type.getSelectionModel().selectFirst();

        /*
        Date 类型转换 LocalDate
        Instant instant = new Date().toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        */
        LocalDateTime localDate = LocalDateTime.now();

        base_rush_money_date.setValue(localDate.toLocalDate());
        base_shipping_address.setText(null);
        base_shipping_address_remark.setText(null);
        base_primary_contact.setText(null);
        base_archivist.setText(null);
        base_last_modifier.setText(null);
        base_establish_date.setValue(localDate.toLocalDate());
        base_zip_code.setText(null);
        base_fax.setText(null);
        base_credit_line.setText(null);
        base_credit_line_money_type.getSelectionModel().selectFirst();
        base_credit_line_remark.setText(null);
        base_minimum_discount.setText(null);
        base_contact_number.setText(null);
        base_document_date.setValue(localDate.toLocalDate());
        base_last_modified_date.setValue(localDate.toLocalDate());
        base_stop_use.setText(null);
    }

    /**
     * 查询基本资料信息
     * @param basic
     */
    public void setBaseInfoVal(CustomerBasic basic) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINA);
        // 先清空所有值
        clearBasicVal();
        // 赋值
        if (basic != null) {
            try {
                if (!("".equals(basic.getCountryId())) && basic.getCountryId() != null) {
                    base_combox_country_id.getSelectionModel().select(Integer.valueOf(basic.getCountryId().toString())+0);
                    base_country.setText(countryList.get(Integer.valueOf(basic.getCountryId().toString())).getTitle());
                }
                if (!("".equals(basic.getPhone())) && basic.getPhone() != null) {
                    base_phone.setText(basic.getPhone());
                }
                if (!("".equals(basic.getPaymentMethod())) && basic.getPaymentMethod() != null) {
                    base_payment_method.getSelectionModel().select(Integer.valueOf(basic.getPaymentMethod())+0);
                }
                if (!("".equals(basic.getInitialQuota())) && basic.getInitialQuota() != null) {
                    base_initial_quota.setText(basic.getInitialQuota().toString());
                }
                if (!("".equals(basic.getInitialQuotaMoneyType())) && basic.getInitialQuotaMoneyType() != null) {
                    setComboBox(7, base_initial_quota_money_type, basic.getInitialQuotaMoneyType());
                }
                if (!("".equals(basic.getRushMoneyDate())) && basic.getRushMoneyDate() != null) {
                    base_rush_money_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(basic.getRushMoneyDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
                }
                if (!("".equals(basic.getShippingAddress())) && basic.getShippingAddress() != null) {
                    base_shipping_address.setText(basic.getShippingAddress());
                }
                if (!("".equals(basic.getShippingAddressRemark())) && basic.getShippingAddressRemark() != null) {
                    base_shipping_address_remark.setText(basic.getShippingAddressRemark());
                }
                if (!("".equals(basic.getPrimaryContact())) && basic.getPrimaryContact() != null) {
                    base_primary_contact.setText(basic.getPrimaryContact());
                }
                if (!("".equals(basic.getArchivist())) && basic.getArchivist() != null) {
                    base_archivist.setText(basic.getArchivist());
                }
                if (!("".equals(basic.getLastModifier())) && basic.getLastModifier() != null) {
                    base_last_modifier.setText(basic.getLastModifier());
                }
                if (!("".equals(basic.getEstablishDate())) && basic.getEstablishDate() != null) {
                    base_establish_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(basic.getEstablishDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
                }
                if (!("".equals(basic.getZipCode())) && basic.getZipCode() != null) {
                    base_zip_code.setText(basic.getZipCode());
                }
                if (!("".equals(basic.getFax())) && basic.getFax() != null) {
                    base_fax.setText(basic.getFax());
                }
                if (!("".equals(basic.getCreditLine())) && basic.getCreditLine() != null) {
                    base_credit_line.setText(basic.getCreditLine().toString());
                }
                if ( basic.getCreditLineMoneyType() != null) {
                    setComboBox(7, base_credit_line_money_type, basic.getCreditLineMoneyType());
                }
                if (!("".equals(basic.getCreditLineRemark())) && basic.getCreditLineRemark() != null) {
                    base_credit_line_remark.setText(basic.getCreditLineRemark());
                }
                if (!("".equals(basic.getMinimumDiscount())) && basic.getMinimumDiscount() != null) {
                    base_minimum_discount.setText(basic.getMinimumDiscount().toString());
                }
                if (!("".equals(basic.getContactNumber())) && basic.getContactNumber() != null) {
                    base_contact_number.setText(basic.getContactNumber());
                }
                if (!("".equals(basic.getDocumentDate())) && basic.getDocumentDate() != null) {
                    base_document_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(basic.getDocumentDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
                }
                if (!("".equals(basic.getLastModifiedDate())) && basic.getLastModifiedDate() != null) {
                    base_last_modified_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(basic.getLastModifiedDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
                }
                if (!("".equals(basic.getStopUse())) && basic.getStopUse() != null) {
                    base_stop_use.setText(basic.getStopUse().toString());
                }
                showInvoice();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 货币类型下拉框
     */
    public void setMoneyComboxVal() {
        setComboBox(7, base_initial_quota_money_type, 0);
        setComboBox(7, base_credit_line_money_type, 0);
    }

    /**
     * 客户编号变更监听
     */
    public  void customerCodeChange(){
        code.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String customerid = customer_id.getText();
                if(customerid != null && !("".equals(customerid))){
//                    switchCase(current_pane_str,customerid);
                    selectAllPane();
                }
            }
        });
    }


    /********************************************* 基本信息-结束 ********************************************/


    /********************************************* 客户公共部分结束 ********************************************/

    /**
     * 查询所欲面板中的数据
     */
    private void selectAllPane(){
        String customerid = customer_id.getText();
        // 基本资料
        CustomerBasic basic = iCustomerBasicService.getCustomerBasicByCustomerId(Long.valueOf(customerid));
        setBaseInfoVal(basic);
        // 详细信息
        customerCategoryCombox();
        industryCombox();
        customerSourceCombox();
        bankCombox();
        customerLevelCombox();
        areaCombox();
        // 发票详情
        showInvoice();
        // 清除客户详细信息
        clearCustomerDetailInfoVal();
        CustomerDetailInfo info = iCustomerDetailInfoService.getCustomerDetailInfoByCustomerId(Long.valueOf(customerid));
        setCustomerDetailInfoVal(info);
        // 业务负责人
        clearCustomerBusinessLeaderVal();
        setCustomerBusinessLeaderVal(customerid);
        // 联系人
        clearCustomerContactsVal();
        setCustomerContactsVal(customerid);
        // 送货地址
        clearAddressVal();
        showAddressList();
        // 交易单据

        // 资料维护
        clearCustomerDataMaintenanceVal();
        setCustomerDataMaintenanceVal(customerid);
        // 备注及说明
        clearCustomerRemarkVal();
        setCustomerRemarkVal();
    }

    /**
     * 面板切换刷新数据
     * @param current_pane_str 当前面板
     * @param customerid 客户id
     */
//    public void switchCase(String current_pane_str,String customerid){
//        switch (current_pane_str){
//            case "基本资料":{
//                CustomerBasic basic = iCustomerBasicService.getCustomerBasicByCustomerId(Long.valueOf(customerid));
//                setBaseInfoVal(basic);
//                break;
//            }
//            case "详细信息": {
//                // 客户详细信息
//                customerCategoryCombox();
//                industryCombox();
//                customerSourceCombox();
//                bankCombox();
//                customerLevelCombox();
//                areaCombox();
//                // 清除客户详细信息
//                clearCustomerDetailInfoVal();
//                CustomerDetailInfo info = iCustomerDetailInfoService.getCustomerDetailInfoByCustomerId(Long.valueOf(customerid));
//                setCustomerDetailInfoVal(info);
//                break;
//            }
//            case "业务负责人": {
//                // 查询业务负责人
//                clearCustomerBusinessLeaderVal();
//                setCustomerBusinessLeaderVal(customerid);
//                break;
//            }
//            case "联系人": {
//                clearCustomerContactsVal();
//                setCustomerContactsVal(customerid);
//                break;
//            }
//            case "送货地址": {
//                //显示地址信息
//                clearAddressVal();
//                showAddressList();
//                shipping_address_table.refresh();
//                break;
//            }
//            case "交易单据": {
//                System.out.println("交易单据");
//                break;
//            }
//            case "资料维护": {
//                clearCustomerDataMaintenanceVal();
//                setCustomerDataMaintenanceVal(customerid);
//                break;
//            }
//            case "备注及说明": {
//                clearCustomerRemarkVal();
//                setCustomerRemarkVal();
//                break;
//            }
//        }
//    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 删除客户
     * @Date 18:44 2018/8/20
     * @Param []
     **/
    @FXML
    public void deleteCustomer() {
        String strid = customer_id.getText();
        int rows = iCustomerService.delete(Long.valueOf(strid));
        if (rows > 0) {
            // 加载第一条客户数据
            firstCustomer();
            alert_informationDialog("删除成功！");
        } else {
            alert_informationDialog("删除失败！");
        }
    }

    /**
     * 清空所有数据
     */
    @FXML
    public void clearAll(){
        clearBasicVal();
        clearCustomerData();
        clearAddressVal();
        clearCustomerBusinessLeaderVal();
        clearCustomerContactsVal();
        clearCustomerDataMaintenanceVal();
        clearCustomerDetailInfoVal();
        clearCustomerRemarkVal();

    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 清空文本框
     * @Date 17:12 2018/8/20
     * @Param []
     **/
    public void clearCustomerData() {
        customer_id.clear();
        code.clear();
        customer_name.clear();
        register_address.clear();
        customer_initials.clear();
        customer_invoice_tab.setItems(null);
    }

    /**
     * 下一个客户信息
     */
    @FXML
    public void nextCustomer() {
        int max = iCustomerService.getCustomerCount();
        if (page < max - 1) {
            page += 1;
        }
        Customer customer = iCustomerService.getCustomerByPage(page, rows);
        setCustomerTextFieldVal(customer);
    }

    /**
     * 上一个客户信息
     */
    @FXML
    public void prevCustomer() {
        if (page > 0) {
            page -= 1;
        }
        Customer customer = iCustomerService.getCustomerByPage(page, rows);
        setCustomerTextFieldVal(customer);
//        CustomerBasic basic = iCustomerBasicService.getCustomerBasicByCustomerId(customer.getId());
//        setBaseInfoVal(basic);
    }


    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 最后一条客户数据
     * @Date 15:11 2018/8/20
     * @Param []
     **/
    @FXML
    public void lastCustomer() {
        page = iCustomerService.getCustomerCount() - 1;
        Customer customer = iCustomerService.getLastCustomer();
        setCustomerTextFieldVal(customer);
//        CustomerBasic basic = iCustomerBasicService.getCustomerBasicByCustomerId(customer.getId());
//        setBaseInfoVal(basic);
    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 第一条客户数据
     * @Date 15:11 2018/8/20
     * @Param []
     **/
    @FXML
    public void firstCustomer() {
        page = 0;
        Customer customer = iCustomerService.getFirstCustomer();
        setCustomerTextFieldVal(customer);
        CustomerBasic basic = iCustomerBasicService.getCustomerBasicByCustomerId(customer.getId());
        setBaseInfoVal(basic);
    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 给客户基本信息文本框赋值
     * @Date 15:13 2018/8/20
     * @Param [customer]
     **/
    private void setCustomerTextFieldVal(Customer customer) {
        if (customer != null) {
            if (customer.getId() != null ) {
                customer_id.setText(customer.getId().toString());
            }
            if (customer.getName() != null ) {
                customer_name.setText(customer.getName());
            }
            if (customer.getShortName() != null ) {
                customer_initials.setText(customer.getShortName());
            }
            if (customer.getCustomerCode() != null ) {
                code.setText(customer.getCustomerCode());
            }
            if (customer.getRegisterAddress() != null) {
                register_address.setText(customer.getRegisterAddress());
            }
        }
    }


    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 新建客户
     * @Date 10:40 2018/8/20
     * @Param []
     **/
    @FXML
    public void add_customer() {
        // 清空所有
        clearAll();
        // 设置控件可编辑
        updateController();
        String str_code = DateUtils.getDateFormat(DateUtils.FORMAT_YYYYMMDDHHMMSS).format(new Date()) + "" + getRandomone();
        code.setText(str_code);
        // 建档人 建档日期
        createPeople(base_archivist,base_document_date);
        customer_initials.setText(null);
        customer_name.setText(null);
        register_address.setText(null);
        // 选中下拉框中的第一个值
        try {
            info_area_str.setText(info_area_com.getValue().toString());
            base_country.setText(base_combox_country_id.getValue().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 保存
     * @return void
     * @Author BlueSky
     * @Description //TODO 客户数据提交
     * @Date 15:17 2018/8/20
     * @Param []
     **/
    @FXML
    public void commitCustomer() {
        String old_code = code.getText().trim();
        Customer tempCustomer = iCustomerService.getCustomer(old_code);
        String name = customer_name.getText().trim();
        String address = register_address.getText().trim();
        String initials = customer_initials.getText().trim();
        // 最后修改人
        lastUpdatePeopel(base_last_modifier, base_last_modified_date);
        if (old_code == null || "".equals(old_code) || name == null || "".equals(name) || address == null || "".equals(address)) {
            alert_informationDialog("内容不能为空");
            return;
        } else {
            if (tempCustomer == null) {
                // 实例化
                tempCustomer = new Customer();
            }
            tempCustomer.setName(name);
            tempCustomer.setRegisterAddress(address);
            tempCustomer.setShortName(initials);
            tempCustomer.setCustomerCode(old_code);
            tempCustomer.setAddtime(new Date());
            String new_code = code.getText().trim();
            tempCustomer.setCustomerCode(new_code);
            if(tempCustomer.getId() == null || tempCustomer.getId() == 0L){
                int rows = iCustomerService.save(tempCustomer);
                if (rows > 0) {
                    // 把新增的id值赋予隐藏框
                    customer_id.setText(tempCustomer.getId().toString());
                    updateControllerDisable();
                    alert_informationDialog("客户基本信息新增成功");
                } else {
                    alert_informationDialog("客户基本信息新增失败");
                }
            }else{
                int rows = iCustomerService.updateNotNull(tempCustomer);
                if (rows > 0) {
                    // 把id值赋予隐藏框
                    customer_id.setText(tempCustomer.getId().toString());
                    updateControllerDisable();
                    alert_informationDialog("客户基本信息修改成功");
                } else {
                    alert_informationDialog("客户基本信息修改失败");
                }
            }
        }

        // 保存基本资料
        saveCustomerBasic();
        // 保存客户详细信息
        saveCustomerInfo();

        // tableview 数据保存
        saveTableviewBusiness();
        saveTableviewRemark();
        saveTableviewMaintenance();
        saveTableviewContact();
        saveTableviewInvice();
        saveAddress();

//        switch (current_pane_str) {
//            case "基本资料": {
//                // 保存基本资料
//                saveCustomerBasic();
//                break;
//            }
//            case "详细信息": {
//                saveCustomerInfo();
//                break;
//            }
//            case "业务负责人": {
//                System.out.println("业务负责人");
//                break;
//            }
//            case "联系人": {
//                System.out.println("联系人");
//                break;
//            }
//            case "交易单据": {
//                System.out.println("交易单据");
//                break;
//            }
//            case "资料维护": {
//                System.out.println("资料维护");
//                break;
//            }
//            case "备注及说明": {
//                System.out.println("备注及说明");
//                break;
//            }
//        }
    }

    /**
     * 保存客户基本信息
     */
    private void saveCustomerBasic(){
        String customerid = customer_id.getText();
        if (!("".equals(customerid)) && customerid != null) {
            try {
                CustomerBasic basic = new CustomerBasic();
                basic.setCustomerId(Long.valueOf(customerid));
                if (base_combox_country_id.getSelectionModel().getSelectedItem() != null && !"".equals(base_combox_country_id.getSelectionModel().getSelectedItem())) {
                    basic.setCountryId(Long.valueOf(base_combox_country_id.getSelectionModel().getSelectedIndex()));
                }
                if (!("".equals(base_phone.getText())) && base_phone.getText() != null) {
                    basic.setPhone(base_phone.getText());
                }
                if (  base_payment_method.getSelectionModel().getSelectedItem() != null) {
                    basic.setPaymentMethod(base_payment_method.getSelectionModel().getSelectedIndex()+"");
                }
                if (!("".equals(base_initial_quota.getText())) && base_initial_quota.getText() != null) {
                    basic.setInitialQuota(Long.valueOf(base_initial_quota.getText()));
                }
                if (base_initial_quota_money_type.getSelectionModel().getSelectedItem() != null ) {
                    basic.setInitialQuotaMoneyType(base_initial_quota_money_type.getSelectionModel().getSelectedIndex());
                }
                if (!("".equals(base_rush_money_date.getValue())) && base_rush_money_date.getValue() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = df.parse(base_rush_money_date.getValue().toString());
                        basic.setRushMoneyDate(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!("".equals(base_shipping_address.getText())) && base_shipping_address.getText() != null) {
                    basic.setShippingAddress(base_shipping_address.getText());
                }
                if (!("".equals(base_shipping_address_remark.getText())) && base_shipping_address_remark.getText() != null) {
                    basic.setShippingAddressRemark(base_shipping_address_remark.getText());
                }
                if (!("".equals(base_primary_contact.getText())) && base_primary_contact.getText() != null) {
                    basic.setPrimaryContact(base_primary_contact.getText());
                }
                if (!("".equals(base_archivist.getText())) && base_archivist.getText() != null) {
                    basic.setArchivist(base_archivist.getText());
                }
                if (!("".equals(base_last_modifier.getText())) && base_last_modifier.getText() != null) {
                    basic.setLastModifier(base_last_modifier.getText());
                }
                if (!("".equals(base_establish_date.getValue())) && base_establish_date.getValue() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = df.parse(base_establish_date.getValue().toString());
                        basic.setEstablishDate(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!("".equals(base_zip_code.getText())) && base_zip_code.getText() != null) {
                    basic.setZipCode(base_zip_code.getText());
                }
                if (!("".equals(base_fax.getText())) && base_fax.getText() != null) {
                    basic.setFax(base_fax.getText());
                }
                if (!("".equals(base_credit_line.getText())) && base_credit_line.getText() != null) {
                    basic.setCreditLine(Long.valueOf(base_credit_line.getText()));
                }
                if (base_credit_line_money_type.getSelectionModel().getSelectedItem() != null && !"".equals(base_credit_line_money_type.getSelectionModel().getSelectedItem())) {
                    basic.setCreditLineMoneyType(base_credit_line_money_type.getSelectionModel().getSelectedIndex());
                }
                if (!("".equals(base_credit_line_remark.getText())) && base_credit_line_remark.getText() != null) {
                    basic.setCreditLineRemark(base_credit_line_remark.getText());
                }
                if (!("".equals(base_minimum_discount.getText())) && base_minimum_discount.getText() != null) {
                    basic.setMinimumDiscount(Double.valueOf(base_minimum_discount.getText()));
                }
                if (!("".equals(base_contact_number.getText())) && base_contact_number.getText() != null) {
                    basic.setContactNumber(base_contact_number.getText());
                }
                if (!("".equals(base_document_date.getValue())) && base_document_date.getValue() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = df.parse(base_document_date.getValue().toString());
                        basic.setDocumentDate(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!("".equals(base_last_modified_date.getValue())) && base_last_modified_date.getValue() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = df.parse(base_last_modified_date.getValue().toString());
                        basic.setLastModifiedDate(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!("".equals(base_stop_use.getText())) && base_stop_use.getText() != null) {
                    basic.setStopUse(Integer.parseInt(base_stop_use.getText()));
                }

                if (basic.getCustomerId() != null && basic.getCustomerId() != 0L) {
                    CustomerBasic cu = iCustomerBasicService.getCustomerBasicByCustomerId(basic.getCustomerId());
                    if (cu == null) {
                        // 新增客户基本资料
                        basic.setAddtime(new Date());
                        int rows = iCustomerBasicService.save(basic);
                        if (rows > 0) {
                            updateControllerDisable();
                            alert_informationDialog("客户基本资料保存成功！");
                        } else {
                            alert_informationDialog("客户基本资料保存失败！");
                        }
                    }else{
                        // 修改客户基本资料
                        basic.setId(cu.getId());
                        int rows = iCustomerBasicService.updateNotNull(basic);
                        if (rows > 0) {
                            updateControllerDisable();
                            alert_informationDialog("客户基本资料修改成功！");
                        } else {
                            alert_informationDialog("客户基本资料修改失败！");
                        }
                    }
                } else {
                    alert_informationDialog("客户基本信息不完整");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 客户详细信息保存
     */
    private void saveCustomerInfo(){
        CustomerDetailInfo info = new CustomerDetailInfo();
        String customerid = customer_id.getText().toString();
        info.setCustomerId(Long.valueOf(customerid));
        //客户类别
        if(info_customer_category.getSelectionModel().getSelectedItem() != null){
            info.setCustomerCategory(info_customer_category.getSelectionModel().getSelectedIndex());
        }
        // 行业
        if(info_industry.getSelectionModel().getSelectedItem() != null){
            info.setIndustry(info_industry.getSelectionModel().getSelectedIndex());
        }
        // 客户来源
        if(!("".equals(info_customer_source.getValue().toString())) && info_customer_source.getValue().toString() != null){
            info.setCustomerSource(info_customer_source.getSelectionModel().getSelectedIndex());
        }
        // 开户银行
        if(!("".equals(info_bank.getValue().toString())) && info_bank.getValue().toString() != null){
            info.setBank(info_bank.getSelectionModel().getSelectedIndex());
        }
        // 银行账户
        if(!("".equals(info_bank_acount.getText())) && info_bank_acount.getText() != null){
            info.setBankAccount(info_bank_acount.getText());
        }
        // 税务登记号
        if(!("".equals(info_tax_no.getText())) && info_tax_no.getText() != null){
            info.setTaxRegister(info_tax_no.getText());
        }
        // 所属区域
        if(!("".equals(info_area_com.getValue().toString())) && info_area_com.getValue().toString() != null){
            areaList.forEach(p->{
                if(info_area_com.getValue().toString().equals(p.getAreaname())){
                    info.setArea(p.getAreaid());
                    return;
                }
            });
        }
        // 所属区域详情
        if(!("".equals(info_area_str.getText())) && info_area_str.getText() != null){
            info.setAreaInfo(info_area_str.getText());
        }
        // 账款备注
        if(!("".equals(info_acount_remark.getText())) && info_acount_remark.getText() != null){
            info.setAccountRemark(info_acount_remark.getText());
        }
        // 所属地区
        if(!("".equals(info_region.getValue().toString())) && info_region.getValue().toString() != null){
            areaList.forEach(p->{
                if(info_region.getValue().toString().equals(p.getAreaname())){
                    info.setRegion(p.getAreaid());
                    return;
                }
            });
        }
        // 客户等级
        if( info_customer_level.getSelectionModel().getSelectedItem() != null){
            try {
                info.setCustomerLevel(info_customer_level.getSelectionModel().getSelectedIndex());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // 所属公司
        if(!("".equals(info_company.getValue()) && info_company.getValue() != null)){
            info.setCompanyAffiliation(info_company.getValue()+"");
        }
        // 销售单需回传
        info.setSentBack(info_comes_back.selectedProperty().getValue());
        CustomerDetailInfo detailInfo = iCustomerDetailInfoService.getCustomerDetailInfoByCustomerId(Long.valueOf(customerid));
        if(detailInfo == null){
            info.setAddtime(new Date());
            int rows = iCustomerDetailInfoService.save(info);
            if(rows>0){
                updateControllerDisable();
                alert_informationDialog("客户详情新增成功！");
            }else{
                alert_informationDialog("客户详情新增失败！");
            }
        }else{
            info.setId(detailInfo.getId());
            int rows = iCustomerDetailInfoService.updateNotNull(info);
            if(rows>0){
                updateControllerDisable();
                alert_informationDialog("客户详情修改成功！");
            }else{
                alert_informationDialog("客户详情修改失败！");
            }
        }
    }

    /********************************************* 客户公共部分结束 ********************************************/

    /********************************************* 送货地址开始 ********************************************/

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 添加联系地址
     * @Date 10:35 2018/8/20
     * @Param []
     **/
    @FXML
    public void address_add() {
        String type = address_combox_type.getValue().toString();
        if ("住宅".equals(type)) {
            type = "1";
        } else if ("公司".equals(type)) {
            type = "2";
        } else if ("公寓".equals(type)) {
            type = "3";
        } else if ("酒店".equals(type)) {
            type = "4";
        } else {
            type = "0";
        }
        String contact = address_txt_contact.getText();
        String info = address_txt_info.getText();
        String phone = address_txt_phone.getText();
        String title = address_txt_name.getText();
        CustomerShippingAddress address = new CustomerShippingAddress();
        address.setType(type);
        address.setTitle(title);
        address.setContact(contact);
        address.setPhone(phone);
        address.setAddress(info);
        if(!"".equals(customer_id.getText()) && customer_id.getText() != null){
            address.setCustomerid(Long.valueOf(customer_id.getText()));
            int rows = addressService.save(address);
            if(rows>0){
                clearAddressVal();
                // 刷新数据
                showAddressList();
            }else{
                alert_informationDialog("保存失败，系统出错了！");
            }
        }
    }

    /**
     *  送货地址的地址类型
     */
    final String[] greeting = {"住宅", "公司", "公寓", "酒店",};

    /**
     * @Author BlueSky
     * @Description //TODO 送货地址-下拉框类型
     * @Date 11:40 2018/8/17
     **/
    public void address_combox_typeSetVal() {
        address_combox_type.setItems(FXCollections.observableArrayList(greeting)
        );
        address_combox_type.getSelectionModel().selectFirst();
    }


    public static enum Participation {
        设为默认;
    }


    /**
     * 表格中下拉框的值
     */
    static ObservableList<String> levelChoice = null;

    public static ObservableList<String> getLevelChoice() {
        return levelChoice;
    }

    public static void setLevelChoice(ObservableList<String> levelChoice) {
        CustomerBasicInfoController.levelChoice = levelChoice;
    }

    /**
     * @Author BlueSky
     * @Description //TODO  送货地址查询
     * @Date 11:20 2018/8/17
     **/
    public void showAddressList() {

        shipping_address_table.getItems().clear();
        String customerid = customer_id.getText();
        if(customerid != null && !("".equals(customerid))){
            List<CustomerShippingAddress> addressList = addressService.listAll(Long.valueOf(customerid));
            List<ShippingAddressProperty> addressListNew = new ArrayList<>();
                    addressList.forEach(p -> {
                if ("1".equals(p.getType())) {
                    p.setType("住宅");
                } else if ("2".equals(p.getType())) {
                    p.setType("公司");
                } else if ("3".equals(p.getType())) {
                    p.setType("公寓");
                } else if ("4".equals(p.getType())) {
                    p.setType("酒店");
                }
                Participation part = null;
                if(p.getSetting() != null && p.getSetting()){
                    part = Participation.设为默认;
                }
                addressListNew.add(new ShippingAddressProperty(p.getId(),p.getCustomerid(),p.getType(),p.getTitle(),p.getAddress(),p.getPhone(),p.getContact(),part));
            });

            shipping_address_table.setEditable(true);

            address_col_type.setCellFactory(column -> EditCell.createStringEditCell());
            address_col_title.setCellFactory(column -> EditCell.createStringEditCell());
            address_col_contact.setCellFactory(column -> EditCell.createStringEditCell());
            address_col_address.setCellFactory(column -> EditCell.createStringEditCell());
            address_col_phone.setCellFactory(column -> EditCell.createStringEditCell());
            address_col_setting.setCellFactory((param) -> new TableViewSample.RadioButtonCell<CustomerShippingAddress, Participation>(EnumSet.allOf(Participation.class)));

            // 查询地址集合
            final ObservableList<ShippingAddressProperty> data = FXCollections.observableArrayList(addressListNew);
            address_col_id.setCellValueFactory(new PropertyValueFactory<ShippingAddressProperty,Long>("id"));
            address_col_type.setCellValueFactory(new PropertyValueFactory("type"));
            address_col_title.setCellValueFactory(new PropertyValueFactory("title"));
            levelChoice = FXCollections.observableArrayList("住宅", "公司","公寓","酒店");
            address_col_type.setCellFactory(ComboBoxTableCell.forTableColumn(levelChoice));
            address_col_type.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<ShippingAddressProperty, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ShippingAddressProperty, String> t) {
                            ((ShippingAddressProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setType(t.getNewValue());
                        };
                    }
            );
            address_col_address.setCellValueFactory(new PropertyValueFactory("address"));
            address_col_phone.setCellValueFactory(new PropertyValueFactory("phone"));
            address_col_contact.setCellValueFactory(new PropertyValueFactory("contact"));
            address_col_setting.setCellValueFactory(new PropertyValueFactory<ShippingAddressProperty,Participation>("participation"));

            shipping_address_table.setItems(data);

            shipping_address_table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // 默认地址选择
            address_col_setting.setOnEditCommit(evt -> {
                boolean inputValid = false;
                if(!inputValid) {
                    int row = shipping_address_table.getEditingCell().getRow();

                    ShippingAddressProperty selected = (ShippingAddressProperty) shipping_address_table.getItems().get(row);
                    // 清除所有默认
                    iCustomerShippingAddressService.updateClearAddressDefault(selected.getCustomerId());
                    // 设置为默认
                    int count = iCustomerShippingAddressService.updateAddressDefault(selected.getId());

                    if(count > 0){
                        showAddressList();
                    }
                    shipping_address_table.requestFocus(); // get back focus
                    shipping_address_table.edit(row, address_col_setting);
                }
            });
        }
    }

    /**
     * 保存tableview中的数据
     */
    @FXML
    public void saveAddress(){

        // 取得当前行的数据
        TablePosition pos = (TablePosition) shipping_address_table.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        int col = pos.getColumn();

        String customerid = customer_id.getText();
        if(customerid != null && !"".equals(customerid)){
            int rows = shipping_address_table.getItems().size();
            for (int i = 0; i < rows; i++) {
                ShippingAddressProperty ad = null;
                if(shipping_address_table.getItems().get(i) != null){
                    ad = (ShippingAddressProperty)shipping_address_table.getItems().get(i);
                }

                CustomerShippingAddress address = new CustomerShippingAddress();
                if(ad.getAddress() != null){
                    address.setAddress(ad.getAddress());
                }
                if(ad.getTitle() != null){
                    address.setType(ad.getTitle());
                }
                if(ad.getContact() != null){
                    address.setContact(ad.getContact());
                }
                if(ad.getPhone() != null){
                    address.setPhone(ad.getPhone());
                }
                if(ad.getType() != null){
                    for (int k = 0; k < greeting.length; k++) {
                        if(greeting[k].equals(ad.getType())){
                            address.setType(""+(k+1));
                            break;
                        }
                    }
                }
                if(ad.getParticipation() != null){
                    address.setSetting(true);
                }
                address.setCustomerid(Long.valueOf(customerid));
                if(ad.getId() == null){
                    address.setAddtime(new Date());
                    try {
                        if(!(ad.getAddress() == null && ad.getContact() == null && ad.getPhone() == null && ad.getType() == null)){
                            iCustomerShippingAddressService.save(address);
                            iCustomerShippingAddressService.updateClearAddressDefault(ad.getCustomerId());
                            iCustomerShippingAddressService.updateAddressDefault(address.getId());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    address.setId(ad.getId());
                    try {
                        iCustomerShippingAddressService.updateNotNull(address);
                        if(address.getSetting() != null && address.getSetting()){
                            iCustomerShippingAddressService.updateClearAddressDefault(ad.getCustomerId());
                            iCustomerShippingAddressService.updateAddressDefault(address.getId());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            alert_informationDialog("保存成功！");
        }
        // 刷新地址信息
        showAddressList();

        // 默认地址没有值的话就从送货地址选取第一条数据
        if(base_shipping_address.getText() == null || "".equals(base_shipping_address.getText())){
            List<CustomerShippingAddress> addressList = iCustomerShippingAddressService.listAll(Long.valueOf(customerid));
            if(addressList != null){
                base_shipping_address.setText(addressList.get(0).getAddress());
            }
        }
    }

    /**
     * 清空地址信息
     */
    public void clearAddressVal(){
        address_combox_type.getSelectionModel().selectFirst();
        address_txt_contact.clear();
        address_txt_info.clear();
        address_txt_phone.clear();
        shipping_address_table.getItems().clear();
        shipping_address_table.refresh();
    }

    // 可边tableview 的初始文本框
    private TextField textField;


    /**
     * enter 键盘按下触发
     *
     * @param event
     */
    public void tableViewInsertKey(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addAddress();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteAddress();
        }
    }


    /**
     * 删除地址
     */
    private void deleteAddress(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) shipping_address_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            ShippingAddressProperty addressProperty = (ShippingAddressProperty)shipping_address_table.getItems().get(index);
            if(addressProperty.getId() != null && addressProperty.getId() != 0L){
                int rows = iCustomerShippingAddressService.delete(addressProperty.getId());
                if(rows > 0){
                    showAddressList();
                    alert_informationDialog("删除成功！");
                }else{
                    alert_informationDialog("删除失败！");
                }
            }
        }catch (Exception e){
            alert_informationDialog("请选择需要删除的行！");
            e.printStackTrace();
        }
    }

    /**
     * 添加新地址
     */
    public void addAddress() {

        ObservableList<ShippingAddressProperty> list = shipping_address_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        if(customer_id.getText() != null && !"".equals(customer_id.getText())){
            list.add(new ShippingAddressProperty(Long.valueOf(customer_id.getText()), "","", "", "", ""));        //list添加值对象
        }

        shipping_address_table.setItems(list);

    }


    /********************************************* 送货地址结束 ********************************************/






    /**
     * 业务负责人
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfBusinessTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addBusinessRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfBusiness();

        }
    }

    /**
     * 行删除 业务负责人
     */
    private void deleteRowOfBusiness(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) customer_business_leader_tab.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            CustomerBusinessLeaderProperty property = (CustomerBusinessLeaderProperty)customer_business_leader_tab.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iCustomerBusinessLeaderService.delete(property.getId());
                returnMsg(rows);
            }
            final ObservableList<CustomerBusinessLeader> dataProperty = customer_business_leader_tab.getItems();
            final ObservableList<CustomerBusinessLeader> newDataProperty = FXCollections.observableArrayList();
            for (int i = 0; i < dataProperty.size(); i++) {
                if(i != index){
                    newDataProperty.add(dataProperty.get(i));
                }
            }
            customer_business_leader_tab.setItems(newDataProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 行添加 业务负责人
     */
    public void addBusinessRow() {

        ObservableList<CustomerBusinessLeaderProperty> list = customer_business_leader_tab.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new CustomerBusinessLeaderProperty( "", "", "", "",""));
        customer_business_leader_tab.setItems(list);
    }

    /**
     * 联系人
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfContactTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addContactRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfContact();

        }
    }

    /**
     * 行删除 联系人
     */
    private void deleteRowOfContact(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) customer_contact_tab.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            CustomerContactsProperty property = (CustomerContactsProperty)customer_contact_tab.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iCustomerContactsService.delete(property.getId());
                returnMsg(rows);
            }
            final ObservableList<CustomerContactsProperty> dataProperty = customer_contact_tab.getItems();
            final ObservableList<CustomerContactsProperty> newDataProperty = FXCollections.observableArrayList();
            for (int i = 0; i < dataProperty.size(); i++) {
                if(i != index){
                    newDataProperty.add(dataProperty.get(i));
                }
            }
            customer_contact_tab.setItems(newDataProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 行添加 联系人
     */
    public void addContactRow() {

        ObservableList<CustomerContactsProperty> list = customer_contact_tab.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new CustomerContactsProperty( "", "", "", "", "", "", "", "", "", ""));
        customer_contact_tab.setItems(list);
    }

    /**
     * 资料维护
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfMaintenanceTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addMaintenanceRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfMaintenance();

        }
    }

    /**
     * 行删除 资料维护
     */
    private void deleteRowOfMaintenance(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) customer_data_maintenance_tab.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            CustomerDataMaintenanceProperty property = (CustomerDataMaintenanceProperty)customer_data_maintenance_tab.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iCustomerContactsService.delete(property.getId());
                returnMsg(rows);
            }
            final ObservableList<CustomerDataMaintenanceProperty> dataProperty = customer_data_maintenance_tab.getItems();
            final ObservableList<CustomerDataMaintenanceProperty> newDataProperty = FXCollections.observableArrayList();
            for (int i = 0; i < dataProperty.size(); i++) {
                if(i != index){
                    newDataProperty.add(dataProperty.get(i));
                }
            }
            customer_data_maintenance_tab.setItems(newDataProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 行添加 资料维护
     */
    public void addMaintenanceRow() {

        ObservableList<CustomerDataMaintenanceProperty> list = customer_data_maintenance_tab.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new CustomerDataMaintenanceProperty( "", "", "", "", "", "", "", "", "", "", "", "", ""));
        customer_data_maintenance_tab.setItems(list);
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
        }
    }

    /**
     * 行添加 备注
     */
    public void addRemarkRow() {

        ObservableList<RemarkProperty> list = customer_remark_tab.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new RemarkProperty(""));
        customer_remark_tab.setItems(list);
    }


    /**
     * 行删除 备注
     */
    private void deleteRowOfRemark(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) customer_remark_tab.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            RemarkProperty property = (RemarkProperty)customer_remark_tab.getItems().get(index);
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
     * 保存 备注 tableview数据
     */
    private void saveTableviewRemark(){
        String id = customer_id.getText();
        if(id != null && !"".equals(id)){
            int tableSize = customer_remark_tab.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                RemarkProperty property = null;
                if(customer_remark_tab.getItems().get(i) != null){
                    property = (RemarkProperty)customer_remark_tab.getItems().get(i);
                }
                Remark product = new Remark();
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                product.setTypeid(1);
                product.setOtherid(Long.valueOf(id));
                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iRemarkService.save(product);
                        setCustomerRemarkVal();
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
     * 保存 业务负责人 tableview数据
     */
    private void saveTableviewBusiness(){
        String id = customer_id.getText();
        if(id != null && !"".equals(id)){
            int tableSize = customer_business_leader_tab.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                CustomerBusinessLeaderProperty property = null;
                if(customer_business_leader_tab.getItems().get(i) != null){
                    property = (CustomerBusinessLeaderProperty)customer_business_leader_tab.getItems().get(i);
                }
                CustomerBusinessLeader product = new CustomerBusinessLeader();

                if(property.getName() != null && !"".equals(property.getName())){
                    product.setName(property.getName());
                }
                if(property.getPrimaryPeople() != null && !"".equals(property.getPrimaryPeople())){
                    product.setPrimaryPeople(property.getPrimaryPeople());
                }
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                if(property.getEmployeeCode() != null && !"".equals(property.getEmployeeCode())){
                    product.setEmployeeCode(property.getEmployeeCode());
                }
                product.setCustomerId(Long.valueOf(id));

                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iCustomerBusinessLeaderService.save(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        product.setId(property.getId());
                        iCustomerBusinessLeaderService.updateNotNull(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 保存 资料维护 tableview数据
     */
    private void saveTableviewMaintenance(){
        String id = customer_id.getText();
        if(id != null && !"".equals(id)){
            int tableSize = customer_data_maintenance_tab.getItems().size();
            try {
                for (int i = 0; i < tableSize; i++) {
                    CustomerDataMaintenanceProperty property = null;
                    if(customer_data_maintenance_tab.getItems().get(i) != null){
                        property = (CustomerDataMaintenanceProperty)customer_data_maintenance_tab.getItems().get(i);
                    }
                    CustomerDataMaintenance product = new CustomerDataMaintenance();

                    if(property.getIndustry() != null && !"".equals(property.getIndustry())){
                        product.setIndustry(property.getIndustry());
                    }
                    if(property.getLastYearBusiness() != null && !"".equals(property.getLastYearBusiness())){
                        product.setLastYearBusiness(property.getLastYearBusiness());
                    }
                    if(property.getLeaderPeople() != null && !"".equals(property.getLeaderPeople())){
                        product.setLeaderPeople(property.getLeaderPeople());
                    }
                    if(property.getPurchasePeople() != null && !"".equals(property.getPurchasePeople())){
                        product.setPurchasePeople(property.getPurchasePeople());
                    }
                    if(property.getStartupYear() != null && !"".equals(property.getStartupYear())){
                        Date date = df.parse(property.getStartupYear());
                        product.setStartupYear(date);
                    }
                    if(property.getTelephone() != null && !"".equals(property.getTelephone())){
                        product.setTelephone(property.getTelephone());
                    }
                    if(property.getYearPlan() != null && !"".equals(property.getYearPlan())){
                        product.setYearPlan(property.getYearPlan());
                    }
                    if(property.getFax() != null && !"".equals(property.getFax())){
                        product.setFax(property.getFax());
                    }
                    if(property.getEmployeeNumber() != null && !"".equals(property.getEmployeeNumber())){
                        product.setEmployeeNumber(Integer.valueOf(property.getEmployeeNumber()));
                    }
                    if(property.getDocumentNo() != null && !"".equals(property.getDocumentNo())){
                        product.setDocumentNo(property.getDocumentNo());
                    }
                    if(property.getCreateDate() != null && !"".equals(property.getCreateDate())){
                        Date date = df.parse(property.getCreateDate());
                        product.setCreateDate(date);
                    }
                    if(property.getContact() != null && !"".equals(property.getContact())){
                        product.setContact(property.getContact());
                    }
                    product.setCustomerId(Long.valueOf(id));
                    if(property.getId() == null){
                        product.setAddtime(new Date());
                        iCustomerDataMaintenanceService.save(product);
                    }else {
                        product.setId(property.getId());
                        iCustomerDataMaintenanceService.updateNotNull(product);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存 联系人 tableview数据
     */
    private void saveTableviewContact(){
        String id = customer_id.getText();
        if(id != null && !"".equals(id)){
            int tableSize = customer_contact_tab.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                CustomerContactsProperty property = null;
                if(customer_contact_tab.getItems().get(i) != null){
                    property = (CustomerContactsProperty)customer_contact_tab.getItems().get(i);
                }
                CustomerContacts product = new CustomerContacts();

                if(property.getDepartment() != null && !"".equals(property.getDepartment())){
                    product.setDepartment(property.getDepartment());
                }
                if(property.getEmail() != null && !"".equals(property.getEmail())){
                    product.setEmail(property.getEmail());
                }
                if(property.getFax() != null && !"".equals(property.getFax())){
                    product.setFax(property.getFax());
                }
                if(property.getMobilePhone() != null && !"".equals(property.getMobilePhone())){
                    product.setMobilePhone(property.getMobilePhone());
                }
                if(property.getName() != null && !"".equals(property.getName())){
                    product.setName(property.getName());
                }
                if(property.getPosition() != null && !"".equals(property.getPosition())){
                    product.setPosition(property.getPosition());
                }
                if(property.getPrimaryContacts() != null && !"".equals(property.getPrimaryContacts())){
                    product.setPrimaryContacts(property.getPrimaryContacts());
                }
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                if(property.getTelephone() != null && !"".equals(property.getTelephone())){
                    product.setTelephone(property.getTelephone());
                }

                product.setCustomerId(Long.valueOf(id));

                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iCustomerContactsService.save(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        product.setId(property.getId());
                        iCustomerContactsService.updateNotNull(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 详细信息tab中的发票明细表格数据

    /**
     * 发票明细 view
     * enter 键盘按下触发
     * @param event
     */
    @FXML
    public void KeyOfInvoiceTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addInviceRow();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfInvice();
        }
    }

    /**
     * 行添加 发票明细
     */
    public void addInviceRow() {

        ObservableList<InvoiceProperty> list = customer_invoice_tab.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new InvoiceProperty("", "", "", "", "", ""));
        customer_invoice_tab.setItems(list);
    }


    /**
     * 行删除 发票明细
     */
    private void deleteRowOfInvice(){
        // 取得当前行的数据
        try {
            TablePosition pos = (TablePosition) customer_invoice_tab.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            InvoiceProperty property = (InvoiceProperty)customer_invoice_tab.getItems().get(index);
            if(property.getId() != null && property.getId() != 0L){
                int rows = iInvoiceService.delete(property.getId());
                returnMsg(rows);
            }
        }catch (Exception e){
            alert_informationDialog("请选择需要删除的行！");
            e.printStackTrace();
        }
    }

    /**
     * 保存 发票明细 tableview数据
     */
    private void saveTableviewInvice(){
        String id = customer_id.getText();
        if(id != null && !"".equals(id)){
            int tableSize = customer_invoice_tab.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                InvoiceProperty property = null;
                if(customer_invoice_tab.getItems().get(i) != null){
                    property = (InvoiceProperty)customer_invoice_tab.getItems().get(i);
                }
                Invoice product = new Invoice();
                if(property.getRemark() != null && !"".equals(property.getRemark())){
                    product.setRemark(property.getRemark());
                }
                if(property.getAddress() != null && !"".equals(property.getAddress())){
                    product.setAddress(property.getAddress());
                }
                if(property.getContact() != null && !"".equals(property.getContact())){
                    product.setContact(property.getContact());
                }
                if(property.getPhone() != null && !"".equals(property.getPhone())){
                    product.setPhone(property.getPhone());
                }
                if(property.getTitle() != null && !"".equals(property.getTitle())){
                    product.setTitle(property.getTitle());
                }
                if(property.getUsual() != null && !"".equals(property.getUsual())){
                    product.setUsual(property.getUsual());
                }
                product.setCustomerId(Long.valueOf(id));
                if(property.getId() == null){
                    try {
                        product.setAddtime(new Date());
                        iInvoiceService.save(product);
                        setCustomerRemarkVal();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        product.setId(property.getId());
                        iInvoiceService.updateNotNull(product);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
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
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将第二个窗口保存到map中
        StageManager.STAGE.put("clientQuery", stage);
        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerBasicInfoController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }



}
