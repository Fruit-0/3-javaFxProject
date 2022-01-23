package com.yc.education.controller.basic;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.*;
import com.yc.education.model.purchase.InquiryProductProperty;
import com.yc.education.service.basic.EmployeeBasicService;
import com.yc.education.service.basic.SupplierBasicService;
import com.yc.education.service.basic.SupplierContactService;
import com.yc.education.service.basic.SupplierPurchaserService;
import com.yc.education.util.NumberUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @ClassName SupplierBasicController
 * @Description TODO  供应商
 * @Author QuZhangJing
 * @Date 2018-08-13 17:01
 * @Version 1.0
 */
@Controller
public class SupplierBasicController extends BaseController implements Initializable {

    @Autowired
    private SupplierBasicService supplierBasicService; //供应商基本资料
    @Autowired
    private SupplierContactService supplierContactService;//联系人
    @Autowired
    private SupplierPurchaserService supplierPurchaserService;//采购负责人
    @Autowired
    private EmployeeBasicService employeeBasicService;//员工Service



    /**
     *  联系人TabelView绑定数据
     */
    private ObservableList<SupplierContactProperty> supplierContactProperties = FXCollections.observableArrayList();
    /**
     *  采购负责人TabelView绑定数据
     */
    private ObservableList<SupplierPurchaserProperty> supplierPurchaserProperties = FXCollections.observableArrayList();


    @FXML private  Label fxmlStatus; //状态


    @FXML private VBox first; //第一页

    @FXML private VBox prev; //上一页

    @FXML private VBox next; //下一页

    @FXML private VBox last; //最后一页


    @FXML private VBox clearvbox; //清除

    @FXML private VBox submitvbox; //提交

    @FXML private VBox insertvbox; //新增

    @FXML private VBox updatevbox; //修改

    @FXML private VBox deletevbox; //删除



    @FXML private TextField isNum;//编号
    @FXML private TextField supdes; //供应商简称
    @FXML private TextField supname; //供应商名称
    @FXML private TextField regadd; //注册地址


    @FXML private ComboBox country; //国家
    @FXML private TextField area; //地区
    @FXML private TextField postalcode; //邮政编码
    @FXML private ComboBox phonetype; //电话类型
    @FXML private TextField phone; //电话
    @FXML private ComboBox faxtype; //传真类型
    @FXML private TextField fax; //传真
    @FXML private TextField remarks; //备注
    @FXML private TextField addpeople; //建档人
    @FXML private TextField adddate; //建档日期
    @FXML private TextField updatepeople; //最后修改人
    @FXML private TextField updatedate; //最后修改日期
    @FXML private CheckBox isstop; //是否暂停使用 默认0 1、暂停使用
    @FXML private TextField stopdes; //暂停说明



    @FXML private TableView tableView1; //联系人
    @FXML private TableColumn contactId;  //编号
    @FXML private TableColumn mainContact;  //主要联系人
    @FXML private TableColumn username; //姓名
    @FXML private TableColumn department; //部门
    @FXML private TableColumn position; //职务
    @FXML private TableColumn phones; //电话
    @FXML private TableColumn faxs; //传真
    @FXML private TableColumn iphone; //移动电话
    @FXML private TableColumn email; //E-mail
    @FXML private TableColumn remarkss; //备注




    @FXML private TableView tableView2; //采购负责人
    @FXML private TableColumn staffid; //员工编码
    @FXML private TableColumn staffcode; //员工编码
    @FXML private TableColumn staffname; //姓名
    @FXML private TableColumn keycontent; //主要负责人
    @FXML private TableColumn staffremarks; //备注


    @FXML private Button moreSupplierButton; //点击弹出 查询供应商


    @FXML private TableView tableView3; //供应商查询
    @FXML private TableColumn supid; //id
    @FXML private TableColumn findsupplierid; //供应商编号
    @FXML private TableColumn findsuppliername; //供应商简称

    @FXML private TableView tableViewEmployee; //员工TableView
    @FXML private TableColumn empid; //id
    @FXML private TableColumn findemployeeid; //员工编号
    @FXML private TableColumn findemployeename; //员工姓名


    private long contactNum=0;
    private long purchaserNum=0;


    private Stage stage = new Stage();


    private static SpringFxmlLoader loader = new SpringFxmlLoader();
    /**
     * 生成供应商基本资料编号
     * @return
     */
    public String createIsnum(){

        String maxIsnum = supplierBasicService.selectMaxIdnum();

        if(maxIsnum != null){

            String maxAlphabet = maxIsnum.substring(0,1);

            int currenNumber = Integer.parseInt(maxIsnum.substring(1,maxIsnum.length()));

            for (int i=0;i< NumberUtil.ALPHABET.length;i++){

            if(currenNumber == NumberUtil.MAXNUMBER){
                if( maxAlphabet.equals(NumberUtil.ALPHABET[i])  ){
                    return NumberUtil.ALPHABET[i+1]+"001";
                }
            }
            }

            if(currenNumber>0 && currenNumber < 9){
                return maxAlphabet +"00"+(currenNumber+1);
            }else if(currenNumber >= 9 && currenNumber< 99){
                return maxAlphabet +"0"+(currenNumber+1);
            }else{
                return maxAlphabet +(currenNumber+1);
            }
        }
        return "A001";
    }


    /**
     * 点击弹出 查询供应商
     */
    public void moreSupplierButtonClick(){

        stage.setTitle("现有供应商查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/basic/supplier_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();

        loadMoreSupplier();
    }


    /**
     * 现有供应商查询
     */
    public void loadMoreSupplier(){

        List<SupplierBasic> supplierBasics = supplierBasicService.selectSupplierBasic();


        ObservableList<SupplierBasic> list =FXCollections.observableArrayList();


        tableView3.setEditable(true);

        /*staffcode.setCellFactory((TableColumn<Object,Object> a ) -> new EditingCell<>());*/

        supid.setCellValueFactory(new PropertyValueFactory("id"));
        findsupplierid.setCellValueFactory(new PropertyValueFactory("idnum"));
        findsuppliername.setCellValueFactory(new PropertyValueFactory("supdes"));

        for (SupplierBasic supplierBasics1:supplierBasics) {

            list.add(supplierBasics1);

        }

        tableView3.setItems(list); //tableview添加list

        tableView3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierBasic>() {
            @Override
            public void changed(ObservableValue<? extends SupplierBasic> observableValue, SupplierBasic oldItem, SupplierBasic newItem) {
                if(newItem.getIdnum() != null && !("".equals(newItem.getIdnum()))){
                    isNum.setUserData(newItem.getId());
                }
            }
        });


        tableView3.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });


    }

    public void closeSureWin(){
        long id =(long)isNum.getUserData();
        SupplierBasic supplierBasic =  supplierBasicService.selectByKey(id);
        loadDate(supplierBasic);
        coseWin();
    }

    public void coseWin(){
        stage.close();
    }






    /**
     * 上下首尾跳页
     * @param event
     */
    public void pages(MouseEvent event){
        Node node =(Node)event.getSource();
        int pageNum =Integer.parseInt(String.valueOf(node.getUserData()));
        findSupplier(pageNum);
        NumberUtil.changeStatus(fxmlStatus,0);//修改为修改状态
    }


    /**
     * 分页查询供应商查询供应商
     */
    public void findSupplier(int pageNum){

        List<SupplierBasic> supplierBasics = supplierBasicService.selectSupplierBasic(pageNum,1);

        PageInfo<SupplierBasic> pageInfo = new PageInfo<>(supplierBasics);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页

        supplierBasics.forEach(supplierBasic ->loadDate(supplierBasic));

    }


    /**
     * 向控件加载数据
     * @param supplierBasic
     */
    public void loadDate(SupplierBasic supplierBasic){


        isNum.setUserData(supplierBasic.getId()); //供应商自增id 用户 删 改

        isNum.setText(supplierBasic.getIdnum());

        supdes.setText(supplierBasic.getSupdes());



        supname.setText(supplierBasic.getSupname());


        regadd.setText(supplierBasic.getRegadd());


       int  cout = supplierBasic.getCountry(); //国家

       country.getSelectionModel().select(--cout);


       area.setText(supplierBasic.getArea());


       postalcode.setText(supplierBasic.getPostalcode());


        int phty =supplierBasic.getPhonetype();

        phonetype.getSelectionModel().select(--phty);


        phone.setText(supplierBasic.getPhone());


        int faxint = supplierBasic.getFaxtype();

        faxtype.getSelectionModel().select(--faxint);


        fax.setText(supplierBasic.getFax());


        remarks.setText(supplierBasic.getRemarks());


        addpeople.setText(supplierBasic.getAddpeople());


         /*
          * Date 类型转换 LocalDate
          * Instant instant = date.toInstant();
          *  ZoneId zone = ZoneId.systemDefault();
          *  LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
          *  LocalDate localDate = localDateTime.toLocalDate();
          */

        adddate.setText(supplierBasic.getAdddate());

        updatepeople.setText(supplierBasic.getUpdatepeople());

        updatedate.setText(supplierBasic.getUpdatedate());

        if(supplierBasic.getIsstop()==1){
            isstop.setSelected(true);
        }else{
            isstop.setSelected(false);
        }


        stopdes.setText(supplierBasic.getStopdes());

        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);

        contact();//加载联系人

        purchaser();//加载采购负责人

    }



    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){
        supdes.setDisable(!status);
        supname.setDisable(!status);
        regadd.setDisable(!status);
        country.setDisable(!status);
        area.setDisable(!status);
        postalcode.setDisable(!status);
        phonetype.setDisable(!status);
        phone.setDisable(!status);
        faxtype.setDisable(!status);
        fax.setDisable(!status);
        remarks.setDisable(!status);
        addpeople.setDisable(!status);
        adddate.setDisable(!status);
        updatepeople.setDisable(!status);
        updatedate.setDisable(!status);
        isstop.setDisable(!status);
        stopdes.setDisable(!status);

        tableView1.setEditable(status);
        tableView2.setEditable(status);

    }



    /**
     * 清空
     */
    public void clearValue(){


        isNum.setText(NumberUtil.NULLSTRING);

        supdes.setText(NumberUtil.NULLSTRING);

        supname.setText(NumberUtil.NULLSTRING);

        regadd.setText(NumberUtil.NULLSTRING);

        country.getSelectionModel().select(0);

        area.setText(NumberUtil.NULLSTRING);

        postalcode.setText(NumberUtil.NULLSTRING);

        phonetype.getSelectionModel().select(0);

        phone.setText(NumberUtil.NULLSTRING);

        faxtype.getSelectionModel().select(0);

        fax.setText(NumberUtil.NULLSTRING);

        remarks.setText(NumberUtil.NULLSTRING);

        addpeople.setText(NumberUtil.NULLSTRING);

        LocalDateTime localDateTime = LocalDateTime.now();

        adddate.setText(NumberUtil.NULLSTRING);

        updatepeople.setText(NumberUtil.NULLSTRING);

        updatedate.setText(NumberUtil.NULLSTRING);

            isstop.setSelected(false);

        stopdes.setText(NumberUtil.NULLSTRING);

        supplierContactProperties.clear();
        supplierPurchaserProperties.clear();

    }

    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) isNum.getUserData();
            int rows = supplierBasicService.delete(id);
            if (rows > 0) {
                findSupplier((int) prev.getUserData()); //初始化第一条数据
            }
            NumberUtil.changeStatus(fxmlStatus, 0); //修改为修改状态
            this.changeEditable(false);
        }
    }



    /**
     * 修改
     */
    public void update(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.UPDATE);//修改为修改状态
        this.changeEditable(true);


        lastUpdatePeopel(updatepeople,updatedate); //最后修改人 和最后修改日期

        submitvbox.setDisable(false);
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);

        //联系人空白行
        blankContact();
        blankPurchaser();


    }


    /**
     *  新增按钮
     */
    public void insert(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.INSERT);//修改为新增状态
        this.changeEditable(true);
        clearValue();//清空控件

        createPeople(addpeople,adddate);//建档人、建档日期

        submitvbox.setDisable(false);
        updatevbox.setDisable(true);
        deletevbox.setDisable(true);
        insertvbox.setDisable(true);

        //联系人空白行
        blankContact();
        blankPurchaser();

    }


    /**
     * 提交
     */
    public  void submit(){

        int  submitStuts = (int)fxmlStatus.getUserData(); //1、新增 2、修改


    /*    SupplierBasic supplierBasic = new SupplierBasic();*/

    String idnumss=isNum.getText();
        if(submitStuts==2){
            idnumss=isNum.getText();
        }

        int istopval=0;
        if(isstop.isSelected()){
            istopval=1;
        }else{
            istopval=0;
        }
        /*if(idnumss==""||"".equals(idnumss)){

            alert_informationDialog("请填写编号!");
            return;
        }*/
        if(supdes.getText()=="" ||"".equals(supdes.getText()) ){
            alert_informationDialog("请填写供应商简称!");
            return;
        }

        if(supname.getText()=="" ||"".equals(supname.getText())){
            alert_informationDialog("请填写供应商名称!");
            return;
        }
        if(regadd.getText()=="" ||"".equals(regadd.getText()) ){
            alert_informationDialog("请填写注册地址!");
            return;
        }
        Object[] values = {
                0L,
                idnumss,
                supdes.getText(),
                supname.getText(),
                regadd.getText(),
                country.getSelectionModel().getSelectedIndex()+1,
                area.getText(),
                postalcode.getText(),
                phonetype.getSelectionModel().getSelectedIndex()+1,
                phone.getText(),
                faxtype.getSelectionModel().getSelectedIndex()+1,
                fax.getText(),
                remarks.getText(),
                addpeople.getText(),
                adddate.getText(),
                updatepeople.getText(),
                updatedate.getText(),
                istopval,
                stopdes.getText(),
                0
        };

        SupplierBasic supplierBasic =(SupplierBasic)NumberUtil.arrayToObject(values,SupplierBasic.class);

        int rows =0;
        if(submitStuts==1){
            String isNums = this.createIsnum();
            isNum.setText(isNums);
            supplierBasic.setIdnum(isNums);
            rows = supplierBasicService.save(supplierBasic);
        }else if(submitStuts ==2){
            supplierBasic.setId((long)isNum.getUserData());
            rows = supplierBasicService.updateNotNull(supplierBasic);
        }
        supplierBasic.setIdnum(isNum.getText());

        saveContact(supplierBasic.getId());//联系人
        savePurchaser(supplierBasic.getId()); //采购负责人

        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);

        this.loadDate(supplierBasic); //重新加载数据

    }







    /**
     * tableView1 键盘按下触发
     * @param event
     */
    public void tableView1Key(KeyEvent event){

        if (event.getCode() == KeyCode.DELETE) {
            if(f_alert_confirmDialog(" ","是否删除!")) {
                supplierContactService.delete(contactNum);


                ObservableList<SupplierContactProperty> supplierContactPropertiesNew = FXCollections.observableArrayList();

                for (SupplierContactProperty supplierContactProperty : supplierContactProperties) {
                    if (supplierContactProperty.getId() != contactNum) {
                        supplierContactPropertiesNew.add(supplierContactProperty);
                    }
                }

                supplierContactProperties.clear();
                supplierContactProperties.setAll(supplierContactPropertiesNew);
            }
             /*loadDate(supplierBasic);*/

        }

        if (event.getCode() == KeyCode.INSERT) {

            //联系人空白行
            blankContact();

        }




    }



    /**
     * tableView2 键盘按下触发
     * @param event
     */
    public void tableView2Key(KeyEvent event){

        if (event.getCode() == KeyCode.DELETE) {
            if(f_alert_confirmDialog(" ","是否删除!")) {
                supplierPurchaserService.delete(purchaserNum);

                long supid = (long) isNum.getUserData();


                ObservableList<SupplierPurchaserProperty> supplierPurchaserPropertiesNew = FXCollections.observableArrayList();

                for (SupplierPurchaserProperty supplierPurchaserProperty : supplierPurchaserProperties) {
                    if (supplierPurchaserProperty.getId() != purchaserNum) {
                        supplierPurchaserPropertiesNew.add(supplierPurchaserProperty);
                    }
                }
                supplierPurchaserProperties.clear();
                supplierPurchaserProperties.setAll(supplierPurchaserPropertiesNew);
            }
        }
        if (event.getCode() == KeyCode.INSERT) {
            //联系人空白行
          blankPurchaser();
        }

        if(event.getCode() == KeyCode.F4){
            moreSupplierEmployeeButtonClick();
        }

    }


    /**
     * 回车查询
     * @param event
     */
    public void isNumKey(KeyEvent event){


        if(event.getCode()==KeyCode.ENTER){

            String value = isNum.getText();

            if(!"".equals(value)){

                 SupplierBasic supplierBasic = supplierBasicService.selectSupplierBasicByIsnum(value);

                 if(supplierBasic != null){
                     this.loadDate(supplierBasic);
                 }

            }

        }

    }



    public void moreSupplierEmployeeButtonClick(){

        stage.setTitle("现有员工查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/basic/supplier_employee_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();

        loadMoreSupplierEmployee();
    }


    /**
     * 现有员工查询
     */
    public void loadMoreSupplierEmployee(){

        List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();


        ObservableList<EmployeeBasic> list =FXCollections.observableArrayList();


        /*staffcode.setCellFactory((TableColumn<Object,Object> a ) -> new EditingCell<>());*/
        empid.setCellValueFactory(new PropertyValueFactory("id"));
        findemployeeid.setCellValueFactory(new PropertyValueFactory("idnum"));
        findemployeename.setCellValueFactory(new PropertyValueFactory("empname"));

        for (EmployeeBasic employeeBasic:employeeBasics) {

            list.add(employeeBasic);

        }

        tableViewEmployee.setItems(list); //tableview添加list

        tableViewEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeBasic>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeBasic> observableValue, EmployeeBasic oldItem, EmployeeBasic newItem) {
                if(newItem.getIdnum() != null && !("".equals(newItem.getIdnum()))){
                    tableViewEmployee.setUserData(newItem.getId());
                }
            }
        });


        tableViewEmployee.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closePurchaseWin();
            }
        });


    }

    public void closePurchaseWin(){
        long id =(long)tableViewEmployee.getUserData();
       EmployeeBasic employeeBasic =  employeeBasicService.selectByKey(id);

       for(int i=0,len=supplierPurchaserProperties.size();i<len;i++){

           if(supplierPurchaserProperties.get(i).getId()==purchaserNum){
              SupplierPurchaserProperty supplierPurchaserProperty =supplierPurchaserProperties.get(i);
               supplierPurchaserProperty.setStaffcode(employeeBasic.getIdnum());
               supplierPurchaserProperty.setStaffname(employeeBasic.getEmpname());
               supplierPurchaserProperty.setStaffremarks(employeeBasic.getRemarks());
           }
       }

        coseWin();
    }




    /**
     * 选项卡---采购负责人 TableView
     *
     * TableColumn staffcode; //员工编码
     * TableColumn staffname; //姓名
     * TableColumn keycontent; //主要负责人
     * TableColumn staffremarks; //备注
     */
    public void purchaser(){


   /*     ObservableList<SupplierPurchaser> list = tableView2.getItems();*/


        List<SupplierPurchaser> supplierPurchasers =supplierPurchaserService.selectSupplierPurchaseBySupplierid((long)isNum.getUserData());



        /*staffcode.setCellFactory((TableColumn<Object,Object> a ) -> new EditingCell<>());*/


            staffcode.setCellFactory(TextFieldTableCell.forTableColumn());
            staffname.setCellFactory(TextFieldTableCell.forTableColumn());
            keycontent.setCellFactory(TextFieldTableCell.forTableColumn());
            staffremarks.setCellFactory(TextFieldTableCell.forTableColumn());


            staffid.setCellValueFactory(new PropertyValueFactory("id"));
            staffcode.setCellValueFactory(new PropertyValueFactory("staffcode"));
            staffname.setCellValueFactory(new PropertyValueFactory("staffname"));
            keycontent.setCellValueFactory(new PropertyValueFactory("keycontent"));
            staffremarks.setCellValueFactory(new PropertyValueFactory("staffremarks"));

            supplierPurchaserProperties.clear();

           if(supplierPurchasers.size()>0){
               for (SupplierPurchaser supplierPurchaser:supplierPurchasers) {

                   SupplierPurchaserProperty supplierPurchaserProperty = new SupplierPurchaserProperty(supplierPurchaser.getId(),supplierPurchaser.getStaffcode(),supplierPurchaser.getStaffname(),supplierPurchaser.getKeycontent(),supplierPurchaser.getStaffremarks());

                   supplierPurchaserProperties.add(supplierPurchaserProperty);

               }
           }
        tableView2.setItems(supplierPurchaserProperties); //tableview添加list

        tableView2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierPurchaserProperty>() {
            @Override
            public void changed(ObservableValue<? extends SupplierPurchaserProperty> observableValue, SupplierPurchaserProperty oldItem, SupplierPurchaserProperty newItem) {
                if(newItem.getId() >0){
                    purchaserNum=newItem.getId();
                }else{
                    purchaserNum=0;
                }
            }



        });
    }





    /**
     * 选项卡---联系人 TableView
     */
    public void contact(){



        List<SupplierContact> supplierContact = supplierContactService.selectSupplierContactBySupplierid((long)isNum.getUserData());



        /* contactId.setCellFactory(TextFieldTableCell.forTableColumn());*/
           mainContact.setCellFactory(TextFieldTableCell.forTableColumn());
        username.setCellFactory(TextFieldTableCell.forTableColumn());
        department.setCellFactory(TextFieldTableCell.forTableColumn());
        position.setCellFactory(TextFieldTableCell.forTableColumn());
        phones.setCellFactory(TextFieldTableCell.forTableColumn());
        faxs.setCellFactory(TextFieldTableCell.forTableColumn());
        iphone.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        remarkss.setCellFactory(TextFieldTableCell.forTableColumn());



         contactId.setCellValueFactory(new PropertyValueFactory("id"));
        mainContact.setCellValueFactory(new PropertyValueFactory("keycontact"));
        username.setCellValueFactory(new PropertyValueFactory("uname"));
        department.setCellValueFactory(new PropertyValueFactory("udepartment"));
        position.setCellValueFactory(new PropertyValueFactory("ujob"));
        phones.setCellValueFactory(new PropertyValueFactory("uphone"));
        faxs.setCellValueFactory(new PropertyValueFactory("ufax"));
        iphone.setCellValueFactory(new PropertyValueFactory("umobile"));
        email.setCellValueFactory(new PropertyValueFactory("uemail"));
        remarkss.setCellValueFactory(new PropertyValueFactory("uremarks"));

        supplierContactProperties.clear();


            if(supplierContact.size()>0){
                for (SupplierContact supplierContact1:supplierContact) {

                    SupplierContactProperty supplierContactProperty = new SupplierContactProperty(supplierContact1.getId(),supplierContact1.getKeycontact(),supplierContact1.getUname(),supplierContact1.getUdepartment(),supplierContact1.getUjob(),supplierContact1.getUphone(), supplierContact1.getUfax(),supplierContact1.getUmobile(),supplierContact1.getUemail(),supplierContact1.getUremarks());

                    supplierContactProperties.add(supplierContactProperty);
                }
            }

        tableView1.setItems(supplierContactProperties); //tableview添加list

        tableView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierContactProperty>() {
            @Override
            public void changed(ObservableValue<? extends SupplierContactProperty> observableValue, SupplierContactProperty oldItem, SupplierContactProperty newItem) {
                if(newItem.getId() >0){
                    contactNum=newItem.getId();
                }
            }
        });

    }

    /**
     * 选项卡---联系人 TableView 新增修改
     */
    public void saveContact(long supplierid){
        for (SupplierContactProperty supplierContactProperty :supplierContactProperties) {
                if(supplierContactProperty.getUname()!=null){
                    if(supplierContactProperty.getId()>0){
                        //修改联系人
                        SupplierContact supplierContact = new SupplierContact(supplierContactProperty.getId(),supplierContactProperty.getKeycontact(),supplierContactProperty.getUname(),supplierContactProperty.getUdepartment(),supplierContactProperty.getUjob(),supplierContactProperty.getUphone(),supplierContactProperty.getUfax(),supplierContactProperty.getUmobile(),supplierContactProperty.getUemail(),supplierContactProperty.getUremarks());
                        supplierContactService.updateNotNull(supplierContact);
                    }else{
                        //新增联系人
                        SupplierContact supplierContact = new SupplierContact(supplierContactProperty.getId(),supplierContactProperty.getKeycontact(),supplierContactProperty.getUname(),supplierContactProperty.getUdepartment(),supplierContactProperty.getUjob(),supplierContactProperty.getUphone(),supplierContactProperty.getUfax(),supplierContactProperty.getUmobile(),supplierContactProperty.getUemail(),supplierContactProperty.getUremarks(),0,supplierid);
                        supplierContactService.save(supplierContact);
                    }
                }
        }
    }

    /**
     * 选项卡---采购负责人TabelView 新增修改
     * @param supplierid
     */
    public void savePurchaser(long supplierid){
        for (SupplierPurchaserProperty supplierPurchaserProperty :supplierPurchaserProperties) {
            if(supplierPurchaserProperty.getStaffcode()!=null){
                if(supplierPurchaserProperty.getId()>0){
                    //修改采购负责人
                    SupplierPurchaser supplierPurchaser = new SupplierPurchaser(supplierPurchaserProperty.getId(),supplierPurchaserProperty.getStaffcode(),supplierPurchaserProperty.getStaffname(),supplierPurchaserProperty.getKeycontent(),supplierPurchaserProperty.getStaffremarks());
                    supplierPurchaserService.updateNotNull(supplierPurchaser);
                }else{
                    //新增采购负责人
                    SupplierPurchaser supplierPurchaser = new SupplierPurchaser(supplierPurchaserProperty.getId(),1L,supplierPurchaserProperty.getStaffcode(),supplierPurchaserProperty.getStaffname(),supplierPurchaserProperty.getKeycontent(),supplierPurchaserProperty.getStaffremarks(),0,supplierid);
                    supplierPurchaserService.save(supplierPurchaser);
                }
            }
        }
    }


    //联系人空白行
    public void blankContact(){
        SupplierContactProperty supplierContactProperty = new SupplierContactProperty();
        supplierContactProperties.add(supplierContactProperty);
    }

    //采购负责人空白行
    public void blankPurchaser(){
        SupplierPurchaserProperty supplierPurchaserProperty = new SupplierPurchaserProperty();
        supplierPurchaserProperties.add(supplierPurchaserProperty);
    }



      private Map<String,String> phoneArray = new HashMap<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {



        phonetype.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.err.println("oldValue:"+oldValue);
                System.err.println("newValue:"+newValue);


              if(!"新增".equals(newValue)){



                  if(phone.getText() != null && !"".equals(phone.getText())){

                      if(phoneArray.get(oldValue)==null || "".equals(phoneArray.get(oldValue))){
                          phoneArray.put(oldValue.toString(),phone.getText());
                      }

                  }



                  for (Map.Entry<String,String> map:phoneArray.entrySet()) {

                      String mapKey = map.getKey();
                      String mapValue = map.getValue();


                      if(mapKey == newValue){
                          phone.setText(mapValue);
                      }else{
                          phone.setText("");
                      }

                  }

              }

            }
        });






        isstop.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                boolean isbool =(boolean)newValue;

                if(isbool){
                    stopdes.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
                }else{
                    stopdes.setText("");
                }


            }
        });



        String newStr = location.toString();

        int index = newStr.indexOf("supplier_basic");

        if(index != -1){

            findSupplier(0); //初始化第一条数据

            NumberUtil.changeStatus(fxmlStatus,0); //查看

            setComboBox(28L,country,0);//国家

            setComboBox(21,phonetype,0); //电话

            setComboBox(22,faxtype,0);//传真

            phonetype.getItems().add("新增");

            faxtype.getItems().add("新增");

        }


      /*  contact();*/

    }
}
