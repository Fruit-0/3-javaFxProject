package com.yc.education.controller;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.model.Citys;
import com.yc.education.model.DataSetting;
import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.service.CitysService;
import com.yc.education.service.DataSettingService;
import com.yc.education.service.IClickItem;
import com.yc.education.service.basic.EmployeeBasicService;
import com.yc.education.util.NumberUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName BaseController
 * @Description TODO 基础控制类
 * @Author QuZhangJing
 * @Date 2018-08-15 11:04
 * @Version 1.0
 */
public class BaseController {


    @Autowired
    private DataSettingService dataSettingService;

    @Autowired
    private EmployeeBasicService employeeBasicService;

    @Autowired
    private CitysService citysService;//省市区



    private String uname ="Admin";


    private Stage stage = new Stage();


    private static SpringFxmlLoader loader = new SpringFxmlLoader();


    // // T指代表实体
    // private T entity;

    // 提示：子类应该在自己的构造方法中自动去实现各个表格的初始化，而不是显示调用
    public BaseController() {

    }



    /**
     * 绑定多个表格的列
     *
     * @param
     */
    public <T> void bindCellValueByTable(T entity, TableView<T> table) {
        try {
            List<TableColumn<T, ?>> columns = table.getColumns();
            bindCellValues(entity, columns);
        } catch (Exception e) {
            throw new RuntimeException("小林：绑定列值失败");
        }
    }

    /**
     * 绑定数据域
     *
     * @param colums TableColumn 可变参数
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Entity> void bindCellValue(T entity, TableColumn<T, ?>... colums) {
        for (TableColumn column : colums) {
            bindSingleCellValues(entity, column);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> void bindCellValues(T entity, List<TableColumn<T, ?>> colums) {
        for (TableColumn column : colums) {
            bindSingleCellValues(entity, column);
        }
    }

    public <T> void bindSingleCellValues(T entity, TableColumn<T, String> column) {
        String fxId = column.getId();
        column.setCellValueFactory(new PropertyValueFactory<T, String>(fxId));
        column.setStyle("-fx-alignment: CENTER;");
        column.setSortable(false);// 禁止排序
        if (entity != null) {
            column.setCellFactory(getColorCellFactory(entity));
        }
    }


    public <T> Callback<TableColumn<T, String>, TableCell<T, String>> getColorCellFactory(
            T t) {
        return new Callback<TableColumn<T, String>, TableCell<T, String>>() {
            public TableCell<T, String> call(TableColumn<T, String> param) {
                TableCell<T, String> cell = new TableCell<T, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setTextFill(null);
                        if (!isEmpty() && item != null) {
                            if (item.contains("-")) {
                                this.setTextFill(Color.RED);
                            } else {
                                this.setTextFill(Color.BLACK);
                            }
                            setText(item);
                        }
                    }
                };
                cell.setEditable(false);// 不让其可编辑
                return cell;
            }
        };
    }



    /**
     * 父类获取子类Class
     * @author linzt
     */
    public Class<?> getSubClass(){ return null;}

    /**
     * @return java.lang.String
     * @Author BlueSky
     * @Description //TODO 返回四位随机数
     * @Date 10:48 2018/8/20
     * @Param []
     **/
    protected String getRandomone() {
        //存放产生的随机数
        String sms = "";
        //生成四位数的随机数
        StringBuffer buf = new StringBuffer();
        Random rand = new Random();
        String[] str = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < 4; i++) {
            buf.append(str[rand.nextInt(10)]);
        }
        sms = buf.toString();
        System.err.println(sms);
        return sms;
    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO 弹出一个信息对话框
     * @Date 13:56 2018/8/20
     * @Param [p_header, p_message]
     **/
    public void alert_informationDialog(String p_message) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("信息");
        _alert.setHeaderText(null);
        _alert.setContentText(p_message);
        _alert.show();
    }


    public boolean f_alert_confirmDialog(String p_header,String p_message){
//        按钮部分可以使用预设的也可以像这样自己 new 一个
        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION,p_message,new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
//        设置窗口的标题
        _alert.setTitle("确认");
        _alert.setHeaderText(p_header);
//        设置对话框的 icon 图标，参数是主窗口的 stage
//        _alert.initOwner(d_stage);
//        showAndWait() 将在对话框消失以前不会执行之后的代码
        Optional<ButtonType> _buttonType = _alert.showAndWait();
//        根据点击结果返回
        if(_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            return true;
        }
        else {
            return false;
        }
    }



    /**
     * 资料查询根据type查询
     * @param type
     * @param comboBox 控件
     * @param index 选中下标
     * type   说明
     * 1	发货状态
     * 2	业务地区
     * 3	货运方式
     * 4	开户银行
     * 5	基本单位
     * 6	产品大类
     * 7	货币单位
     * 8	产品性质
     * 9	产品来源
     * 10	客户类别
     * 11	行业
     * 12	地区
     * 13	客户来源
     * 14	客户等级
     * 15	部门
     * 16	职务
     * 17	出生地
     * 18	民族
     * 19	退货原因
     * 20	收款方式
     * 21	电话
     * 22	传真
     * 23	备注及说明
     * 24	发货状态
     * 25	销退备注
     * 26	收款备注
     * 27	所属公司
     * 28	国家
     * 29   出货仓库
     * 30   运输方式
     */
    public void setComboBox(long type,ComboBox comboBox,int index){

        ObservableList<String> comboBoxList = FXCollections.observableArrayList();

        List<DataSetting> comboBoxType = dataSettingService.findDataSetting(type);

        for (DataSetting combox:comboBoxType) {
            comboBoxList.add(combox.getTitle());
        }

        comboBox.getItems().setAll(comboBoxList);

        comboBox.getSelectionModel().select(index);

    }




    public void showDataSetting(){
        stage.setTitle("资料设定");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/data_setting.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
    }


    /**
     * 赋值制单人
     * @param textField
     */
    public void createPeople(TextField textField){

        textField.setText(uname);

    }

    public void createPeople(TextField textField,TextField textFiel2){

        textField.setText(uname);
        textFiel2.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

    }


    /**
     * 赋值 建档人、建档日期
     * @param textField
     * @param textFieldDate
     */
    public void createPeople(TextField textField,DatePicker textFieldDate){

        textField.setText(uname);

        LocalDateTime localDateTime = LocalDateTime.now();

        textFieldDate.setValue(localDateTime.toLocalDate());

    }



    public void lastUpdatePeopel(TextField textField,TextField textFieldDate){

        textField.setText(uname);

        textFieldDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

    }

    public void lastUpdatePeopel(TextField textField,DatePicker textFieldDate){

        textField.setText(uname);

        LocalDateTime localDateTime = LocalDateTime.now();

        textFieldDate.setValue(localDateTime.toLocalDate());

    }

    /**
     * 返回执行结果
     * @param rows
     */
    public void returnMsg(int rows){
        if(rows > 0){
            alert_informationDialog("操作成功！");
        }else{
            alert_informationDialog("操作失败！");
        }
    }


    /**
     * 加载员工数据
     */
    public void loadEmployee(ComboBox comboBox,int index){

        List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();

        for(int i=0,len =employeeBasics.size();i<len;i++){
            comboBox.getItems().add("("+employeeBasics.get(i).getIdnum()+")"+employeeBasics.get(i).getEmpname());
        }
        comboBox.getSelectionModel().select(index);

    }



    public  void bindEmployee(ComboBox comboBox,TextField textField){

        List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();

        for(int i=0,len=employeeBasics.size();i<len;i++){
            comboBox.getItems().add(employeeBasics.get(i).getIdnum());
        }
        comboBox.getSelectionModel().select(0);



        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if(!"".equals(newValue) && newValue != null ){


                    EmployeeBasic employeeBasic =  employeeBasicService.selectEmployeeBasicByIsnum(newValue.toString());


                    textField.setText(employeeBasic.getEmpname());


                }

            }
        });

    }







    /**
     * 生成订单流水号 (A1809280001)
     * 格式:英文字母(A)+日期(180928)+4位流水号(0001)
     * @param currentDate 当前日期
     * @param maxIsnum 最大订单编号
     * @return
     */
    public String createIsnum(String currentDate, String maxIsnum ){
        String newDateStr=currentDate.substring(2,4)+currentDate.substring(currentDate.indexOf("-")+1,currentDate.indexOf("-")+3)+currentDate.substring(currentDate.lastIndexOf("-")+1,currentDate.lastIndexOf("-")+3);
        if(maxIsnum != null){
            String maxAlphabet = maxIsnum.substring(0,1);
            //180928 0001
            int currenNumber = Integer.parseInt(maxIsnum.substring(maxIsnum.length()-4,maxIsnum.length()));
            for (int i = 0; i< NumberUtil.ALPHABET.length; i++){
                if(currenNumber == 9999 ){
                    if( maxAlphabet.equals(NumberUtil.ALPHABET[i])  ){
                        return NumberUtil.ALPHABET[i+1]+"0001";
                    }
                }
            }
            if(currenNumber>0 && currenNumber < 9){
                return maxAlphabet +newDateStr+"000"+(currenNumber+1);
            }else if(currenNumber >= 9 && currenNumber< 99){
                return maxAlphabet+newDateStr +"00"+(currenNumber+1);
            }else if(currenNumber >= 99 && currenNumber< 999){
                return maxAlphabet+newDateStr +"0"+(currenNumber+1);
            }else{
                return maxAlphabet+newDateStr+(currenNumber+1);
            }
        }
        return "A"+newDateStr+"0001";
    }





  /**
   *@Description TODO  绑定省市区监听
   *@Author QuZhangJing
   *@Date 11:28  2018/11/14
   *@Version 1.0
   * @param province 省 ComboBox控件
   * @param city     市 ComboBox控件
   * @param area     区 ComboBox控件
   */
    public void bindCityListen(ComboBox province,ComboBox city,ComboBox area){

            List<Citys> provinceList = citysService.selectCitysByFatherCode("0000");

            foreachComboBox(province,provinceList);

            province.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue){

                Citys citys = citysService.selectCitysByAddrName(newValue.toString(),"01");

                if(citys != null){

                    List<Citys> cityList = citysService.selectCitysByFatherCode(citys.getAddrCode());

                       foreachComboBox(city,cityList);

                         city.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observable, Object oldValue, Object newValue){

                            Citys citysare = citysService.selectCitysByAddrName(newValue.toString(),"02");

                            if(citysare != null){
                                List<Citys> cityList = citysService.selectCitysByFatherCode(citysare.getAddrCode());

                                foreachComboBox(area,cityList);

                                area.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                                    @Override
                                    public void changed(ObservableValue observable, Object oldValue, Object newValue){


                                    }
                                });

                            }
                        }
                    });
                }
            }
          });
    }

    /**
     * 绑定省市区
     * @param bindParem
     * @param list
     */
    public void foreachComboBox(ComboBox bindParem,List<Citys> list){
        bindParem.getItems().clear();
        for(int i=0,len =list.size();i<len;i++){
            bindParem.getItems().add(list.get(i).getAddrName());
        }
        bindParem.getSelectionModel().select(0);
    }

    /**
     * 设置默认值
     * @param comboBox
     * @param area
     */
    public void selectedComboBox(ComboBox comboBox,String area){

            for(int i=0,len=comboBox.getItems().size();i<len;i++){

                if(comboBox.getItems().get(i).equals(area)){
                    comboBox.getSelectionModel().select(i);
                }

            }

    }


    public static <T> void clickEvent(TableView<T> table, IClickItem<T> item, int times) {

        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                // 双击执行操作
                if(event.getClickCount() == times ){
                    T t = row.getItem();
                    item.click(t);
                }
            });
            return row;
        });
    }






}
