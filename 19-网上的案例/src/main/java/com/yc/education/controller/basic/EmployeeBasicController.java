package com.yc.education.controller.basic;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.CompanyBasic;
import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.model.basic.SupplierBasic;
import com.yc.education.service.basic.EmployeeBasicService;
import com.yc.education.util.NumberUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName EmployeeBasicController
 * @Description TODO 员工档案
 * @Author QuZhangJing
 * @Date 2018-08-14 13:47
 * @Version 1.0
 */
@Controller
public class EmployeeBasicController extends BaseController implements Initializable {


    @Autowired
    private EmployeeBasicService employeeBasicService;

    final ToggleGroup toggleGroup = new ToggleGroup();

    @FXML Label fxmlStatus; //窗体状态

    @FXML private VBox first; //第一页

    @FXML private VBox prev; //上一页

    @FXML private VBox next; //下一页

    @FXML private VBox last; //最后一页


    @FXML private VBox submitvbox; //提交

    @FXML private VBox clearvbox; //清除

    @FXML private VBox insertvbox; //新增

    @FXML private VBox updatevbox; //修改

    @FXML private VBox deletevbox; //删除



    /**
     * 员工编号
     */
    @FXML TextField idnum;

    /**
     * 员工工号
     */
    @FXML TextField jobnum;

    /**
     * 员工姓名
     */
    @FXML TextField empname;

    /**
     * 员工性别1、男  2、女
     */
    @FXML RadioButton na; //男

    @FXML RadioButton nv;//女

    /**
     * 员工身份证
     */
    @FXML TextField empcard;

    /**
     * 英文名称
     */
    @FXML TextField englishname;

    /**
     * 国家
     */
    @FXML ComboBox country;

    /**
     * 城市
     */
    @FXML TextField area;

    /**
     * 护照编号
     */
    @FXML TextField passportnum;

    /**
     * 出生地
     */
    @FXML ComboBox birthplace;

    /**
     * 民族
     */
    @FXML ComboBox nation;

    /**
     * 婚姻状态
     */
    @FXML ComboBox marital;


    /**
     * 联系电话
     */
    @FXML TextField phone;

    /**
     * 地址
     */
    @FXML TextField address;

    /**
     * 备注
     */
    @FXML TextField remarks;

    /**
     * 邮箱
     */
    @FXML TextField email;

    /**
     * 建档人
     */
    @FXML TextField addpeople;

    /**
     * 建档日期
     */
    @FXML TextField adddate;

    /**
     * 最后修改人
     */
    @FXML TextField updatepeople;

    /**
     * 最后修改日期
     */
    @FXML TextField updatedate;

    /**
     * 暂停使用
     */
    @FXML CheckBox isstop;

    /**
     * 暂停描述
     */
    @FXML TextField stopdes;
    /**
     * 部门编号
     */
    @FXML ComboBox department;

    /**
     * 职位
     */
    @FXML ComboBox duty;

    /**
     * 使用时长
     */
    @FXML TextField usedate;

    /**
     * 到职日期
     */
    @FXML DatePicker comedate;

    /**
     * 转正日期
     */
    @FXML DatePicker okdate;

    /**
     * 任职状态
     */
    @FXML ComboBox dutystatus;

    /**
     * 停职日期
     */
    @FXML TextField outdate;

    /**
     * 考勤卡号
     */
    @FXML TextField checknum;

    /**
     * 职位津贴
     */
    @FXML TextField subsdiy;

    /**
     * 工资等级
     */
    @FXML ComboBox grade;

    /**
     * 缴纳保险
     */
    @FXML ComboBox insurance;

    @FXML private TableView tableViewEmployee; //供应商查询
    @FXML private TableColumn empid; //id
    @FXML private TableColumn findemployeeid; //员工编号
    @FXML private TableColumn findemployeename; //员工姓名





    private Stage stage = new Stage();


    private static SpringFxmlLoader loader = new SpringFxmlLoader();


/*************************************************表单控件 End *************************************************************/

    /**
     * 生成编号
     * @return
     */
    public String createIsnum(){
        String maxIsnum = employeeBasicService.selectMaxIdnum();
        if(maxIsnum != null){
            String maxAlphabet = maxIsnum.substring(0,1);
            int currenNumber = Integer.parseInt(maxIsnum.substring(1,maxIsnum.length()));
            for (int i = 0; i< NumberUtil.ALPHABET.length; i++){
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
     * 点击弹出 查询员工
     */
    public void moreEmployeeButtonClick(){

        stage.setTitle("现有员工查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/basic/employee_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();

        loadMoreEmployee();
    }


    /**
     * 现有供应商查询
     */
    public void loadMoreEmployee(){

        List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();


        ObservableList<EmployeeBasic> list = FXCollections.observableArrayList();


        tableViewEmployee.setEditable(true);

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
                    idnum.setUserData(newItem.getId());
                }
            }
        });


        tableViewEmployee.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });


    }

    public void closeSureWin(){
        long id =(long)idnum.getUserData();
        EmployeeBasic employeeBasic =  employeeBasicService.selectByKey(id);
        loadData(employeeBasic);
        coseWin();
    }

    public void coseWin(){
        stage.close();
    }














    /**
     * 加载数据
     * @param employeeBasic
     */
    public void  loadData(EmployeeBasic employeeBasic){

        idnum.setUserData(employeeBasic.getId());
        idnum.setText(employeeBasic.getIdnum());
        jobnum.setText(employeeBasic.getJobnum());
        empname.setText(employeeBasic.getEmpname());


         int sex = employeeBasic.getEmpsex();

        /**
         * 员工性别1、男  2、女
         */
        na.setToggleGroup(toggleGroup);
        nv.setToggleGroup(toggleGroup);

        if(sex==1){
            na.setSelected(true);
        }else{
            nv.setSelected(true);
        }

        empcard.setText(employeeBasic.getEmpcard());

        englishname.setText(employeeBasic.getEnglishname());


        int  cout = employeeBasic.getCountry(); //国家
        country.getSelectionModel().select(--cout);

        area.setText(employeeBasic.getArea());

        passportnum.setText(employeeBasic.getPassportnum());

        //出身地
        int  bplce = employeeBasic.getBirthplace();
        /*birthplace.getSelectionModel().select(--bplce);*/
        setComboBox(17,birthplace,--bplce);

        //民族
        int  nation1 = employeeBasic.getNation();
        /*nation.getSelectionModel().select(--nation1);*/
        setComboBox(18,nation,--nation1);

        marital.getItems().clear();
        int  mar = employeeBasic.getMarital();
        marital.getItems().setAll(
                "未婚","已婚"
        );
        marital.getSelectionModel().select(--mar);

        phone.setText(employeeBasic.getPhone());

        address.setText(employeeBasic.getAddress());

        remarks.setText(employeeBasic.getRemarks());

        email.setText(employeeBasic.getEmail());

        addpeople.setText(employeeBasic.getAddpeople());

        adddate.setText(employeeBasic.getAdddate());

        updatepeople.setText(employeeBasic.getUpdatepeople());

        updatedate.setText(employeeBasic.getUpdatedate());

        if(employeeBasic.getIsstop()==1){
            isstop.setSelected(true);
        }else{
            isstop.setSelected(false);
        }

        stopdes.setText(employeeBasic.getStopdes());


        int  dep = employeeBasic.getDepartment().intValue();
       /* department.getSelectionModel().select(--dep);*/
        setComboBox(15,department,--dep);

        int  dut = employeeBasic.getDuty();
        /*duty.getSelectionModel().select(--dut);*/
        setComboBox(16,duty,--dut);

        usedate.setText(employeeBasic.getUsedate().toString());


        comedate.setValue(LocalDateTime.ofInstant(employeeBasic.getComedate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        okdate.setValue(LocalDateTime.ofInstant(employeeBasic.getOkdate().toInstant(), ZoneId.systemDefault()).toLocalDate());


        int  dutys = employeeBasic.getDutystatus();
        dutystatus.getItems().setAll("在职","停职/留职");
        dutystatus.getSelectionModel().select(--dutys);
        outdate.setText(employeeBasic.getOutdate());

        /*outdate.setValue(LocalDateTime.ofInstant(employeeBasic.getOutdate().toInstant(), ZoneId.systemDefault()).toLocalDate());*/
        checknum.setText(employeeBasic.getChecknum());

        subsdiy.setText(employeeBasic.getSubsdiy().toString());

        int  gra = employeeBasic.getGrade();

        grade.getItems().setAll(
                "一","二","三"
        );

        grade.getSelectionModel().select(--gra);


        int  ins = employeeBasic.getInsurance();

        insurance.getItems().setAll("否 ","是");

        insurance.getSelectionModel().select(--ins);


        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


    }


    /**
     * 分页查询供应商查询供应商
     */
    public void findSupplier(int pageNum){

        List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic(pageNum,1);

        PageInfo<EmployeeBasic> pageInfo = new PageInfo<>(employeeBasics);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页

        employeeBasics.forEach(companyBasic1 ->loadData(companyBasic1));

    }



    /**
     * 上下首尾跳页
     * @param event
     */
    public void pages(MouseEvent event){
        Node node =(Node)event.getSource();
        int pageNum =Integer.parseInt(String.valueOf(node.getUserData()));
        findSupplier(pageNum);
        NumberUtil.changeStatus(fxmlStatus,0);
    }




    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){

        idnum.setDisable(!status);
        jobnum.setDisable(!status);
        empname.setDisable(!status);

        na.setDisable(!status);
        nv.setDisable(!status);

        empcard.setDisable(!status);

        englishname.setDisable(!status);

        country.setDisable(!status);

        area.setDisable(!status);

        passportnum.setDisable(!status);

        birthplace.setDisable(!status);

        nation.setDisable(!status);

        marital.setDisable(!status);

        phone.setDisable(!status);

        address.setDisable(!status);

        remarks.setDisable(!status);

        email.setDisable(!status);

        addpeople.setDisable(!status);



        updatepeople.setDisable(!status);

        isstop.setDisable(!status);

        stopdes.setDisable(!status);


        department.setDisable(!status);



        duty.setDisable(!status);


        usedate.setDisable(!status);

        dutystatus.setDisable(!status);

        checknum.setDisable(!status);

        subsdiy.setDisable(!status);

        grade.setDisable(!status);

        insurance.setDisable(!status);

        //日期
        adddate.setDisable(!status);
        updatedate.setDisable(!status);
        comedate.setDisable(!status);
        okdate.setDisable(!status);
        outdate.setDisable(!status);

    }




    /**
     * 清空
     */
    public void clearValue(){



        idnum.setUserData(NumberUtil.NULLSTRING);
        idnum.setText(NumberUtil.NULLSTRING);
        jobnum.setText(NumberUtil.NULLSTRING);
        empname.setText(NumberUtil.NULLSTRING);

        na.setSelected(true);

        empcard.setText(NumberUtil.NULLSTRING);

        englishname.setText(NumberUtil.NULLSTRING);

        country.getSelectionModel().select(0);

        area.setText(NumberUtil.NULLSTRING);

        passportnum.setText(NumberUtil.NULLSTRING);

        birthplace.getSelectionModel().select(0);

        nation.getSelectionModel().select(0);

        marital.getSelectionModel().select(0);

        phone.setText(NumberUtil.NULLSTRING);

        address.setText(NumberUtil.NULLSTRING);

        remarks.setText(NumberUtil.NULLSTRING);

        email.setText(NumberUtil.NULLSTRING);

        addpeople.setText(NumberUtil.NULLSTRING);



        updatepeople.setText(NumberUtil.NULLSTRING);

        isstop.setSelected(false);

        stopdes.setText(NumberUtil.NULLSTRING);
        outdate.setText(NumberUtil.NULLSTRING);

        department.getSelectionModel().select(0);



        duty.getSelectionModel().select(0);


        usedate.setText(NumberUtil.NULLSTRING);

        dutystatus.getSelectionModel().select(0);

        checknum.setText(NumberUtil.NULLSTRING);

        subsdiy.setText(NumberUtil.NULLSTRING);

        grade.getSelectionModel().select(0);

        insurance.getSelectionModel().select(0);

    }



    /**
     * 提交
     */
    public  void submit(){

        int  submitStuts = (int)fxmlStatus.getUserData(); //1、新增 2、修改


        /*    SupplierBasic supplierBasic = new SupplierBasic();*/

        String idnums ="";

        if(submitStuts==2){
            idnums=idnum.getText();
        }

        int istopval=0;
        if(isstop.isSelected()){
            istopval=1;
        }else{
            istopval=0;
        }

        int userd=0;
        if(!"".equals(usedate.getText())){
            userd=Integer.parseInt(usedate.getText().toString());
        }

        Double subsdiys=0.00;
        if(!"".equals(subsdiy.getText())){
            subsdiys=Double.parseDouble(subsdiy.getText().toString());
        }

        Object[] values = {
                0L,
                idnums,
                jobnum.getText(),
                empname.getText(),
                toggleGroup.getSelectedToggle()==na?1:2,
                empcard.getText(),
                englishname.getText(),
                country.getSelectionModel().getSelectedIndex()+1,
                area.getText(),
                passportnum.getText(),
                birthplace.getSelectionModel().getSelectedIndex()+1,
                nation.getSelectionModel().getSelectedIndex()+1,
                marital.getSelectionModel().getSelectedIndex()+1,
                phone.getText(),
                address.getText(),
                remarks.getText(),
                email.getText(),
                addpeople.getText(),
                adddate.getText(),
                updatepeople.getText(),
                updatedate.getText(),
                istopval,
                stopdes.getText(),
                (long)department.getSelectionModel().getSelectedIndex()+1,
                duty.getSelectionModel().getSelectedIndex()+1,
                userd,
                new java.util.Date(Date.valueOf(comedate.getValue()).getTime()),
                new java.util.Date(Date.valueOf(okdate.getValue()).getTime()),
                dutystatus.getSelectionModel().getSelectedIndex()+1,
                outdate.getText(),
                checknum.getText(),
                subsdiys,
                grade.getSelectionModel().getSelectedIndex()+1,
                insurance.getSelectionModel().getSelectedIndex()+1
        };

        EmployeeBasic employeeBasic =(EmployeeBasic)NumberUtil.arrayToObject(values,EmployeeBasic.class);

        int rows =0;
        if(submitStuts==1){
            String isNums = this.createIsnum();
            idnum.setText(isNums);
            employeeBasic.setIdnum(isNums);
            rows = employeeBasicService.save(employeeBasic);
        }else if(submitStuts ==2){
            employeeBasic.setId((long)idnum.getUserData());
            rows = employeeBasicService.updateNotNull(employeeBasic);
        }
        employeeBasic.setIdnum(idnum.getText());
        this.loadData(employeeBasic); //重新加载数据

        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);


    }





    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) idnum.getUserData();
            int rows = employeeBasicService.delete(id);
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

        lastUpdatePeopel(updatepeople,updatedate); //最后修改人 和最后修改日期

        this.changeEditable(true);
        submitvbox.setDisable(false);
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);

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
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);
    }


    /**
     * 回车查询
     * @param event
     */
    public void isNumKey(KeyEvent event){

        if(event.getCode()== KeyCode.ENTER){

            String value = idnum.getText();

            if(!"".equals(value)){

                EmployeeBasic employeeBasic = employeeBasicService.selectEmployeeBasicByIsnum(value);

                if(employeeBasic != null){
                    this.loadData(employeeBasic);
                }

            }

        }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {



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

        dutystatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                    if("停职/留职".equals(newValue)){
                        outdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
                    }else{
                        outdate.setText("");
                    }

            }
        });




        String newStr = location.toString();

        int index = newStr.indexOf("employee_basic");

        if(index != -1){

            findSupplier(1); //加载数据

            NumberUtil.changeStatus(fxmlStatus,0); //查看


            setComboBox(28L,country,0);//国家
        }





    }




}
