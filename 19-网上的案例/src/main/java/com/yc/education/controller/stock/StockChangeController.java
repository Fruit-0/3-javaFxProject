package com.yc.education.controller.stock;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.DepotBasic;
import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.model.stock.StockChange;
import com.yc.education.model.stock.StockChangeProduct;
import com.yc.education.model.stock.StockChangeProductProperty;
import com.yc.education.service.basic.DepotBasicService;
import com.yc.education.service.basic.EmployeeBasicService;
import com.yc.education.service.stock.StockChangeProductService;
import com.yc.education.service.stock.StockChangeService;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName StockChangeController
 * @Description TODO  库存异动作业
 * @Author QuZhangJing
 * @Date 2018/11/2 18:16
 * @Version 1.0
 */
@Controller
public class StockChangeController extends BaseController implements Initializable {

    @Autowired
    private StockChangeService stockChangeService; //库存异动

    @Autowired
    private StockChangeProductService stockChangeProductService;//库存异动产品

    @Autowired
    private EmployeeBasicService employeeBasicService;//员工Service  ---异动申请人

    @Autowired
    private DepotBasicService depotBasicService;




    @FXML
    private Label fxmlStatus; //状态

    @FXML private VBox first; //第一页

    @FXML private VBox prev; //上一页

    @FXML private VBox next; //下一页

    @FXML private VBox last; //最后一页


    @FXML private VBox clearvbox; //清除

    @FXML private VBox submitvbox; //提交

    @FXML private VBox insertvbox; //新增

    @FXML private VBox updatevbox; //修改

    @FXML private VBox deletevbox; //删除

    @FXML private VBox shyes; //审核

    @FXML private VBox shno; //取消审核



    @FXML private DatePicker changedate; //异动日期

    @FXML private TextField changeorder; //异动单号

    @FXML private TextField depotorder; //异动仓库编号

    @FXML private TextField depotname; //异动仓库名称

    @FXML private ComboBox changetype; //异动类型

    @FXML private ComboBox peopleorder; //异动申请人编号

    @FXML private TextField peoplename; //异动申请人名称

    @FXML private ComboBox changecurrency; //币率

    @FXML private TextField changesourse; //异动来源

    @FXML private TextField createpeople; //制单人

    @FXML private TextField shpeople; //审核人

    @FXML private TextField shdate; //审核日期

    @FXML private TextField updatepeople; //最后修改人

    @FXML private TextField updatedate; //最后修改说明

    @FXML private TextArea remarks; //备注




    //库存异动产品
    @FXML private TableView stockChangeProductView; //库存异动产品TableView
    @FXML private TableColumn findchangesourse; //来源单据
    @FXML private TableColumn findchangeorder; //单号
    @FXML private TableColumn findchangesort; //序号
    @FXML private TableColumn findproductorder; //产品编号
    @FXML private TableColumn findproductname; //产品名称
    @FXML private TableColumn findproductnum; //数量
    @FXML private TableColumn findproductunit; //单位
    @FXML private TableColumn finddepotorder; //库位编号
    @FXML private TableColumn finddepotname; //库位名称
    @FXML private TableColumn findproductremarks; //备注


    //仓库库位
    @FXML private TableView depotView; //仓库库位
    @FXML private TableColumn depid; //  编号
    @FXML private TableColumn finddepotid; //  仓库编号
    @FXML private TableColumn finddepotnames; //  仓库名称

    //库存异动
    @FXML private TableView stockChangeView; //库存异动
    @FXML private TableColumn findstockchangeid; //  编号
    @FXML private TableColumn findstockchangeorder; //  异动单号
    @FXML private TableColumn findstockchangedate; //  制单日期
    @FXML private TableColumn findstockchangedepotorder; //  仓库编号
    @FXML private TableColumn findstockchangedepotname; //  仓库名称
    @FXML private TableColumn findstockchangeremarks; //  备注


    private long changeId=0;

    private Stage stage = new Stage();

    private static SpringFxmlLoader loader = new SpringFxmlLoader();

    //库存异动产品之TableView
    private ObservableList<StockChangeProductProperty> stockChangeProductProperties = FXCollections.observableArrayList();


    /**
     * 生成询价订单流水号 (A1809280001)
     * 格式:英文字母(A)+日期(180928)+4位流水号(0001)
     * @return
     */
    public String createIsnum(String currentDate){
        String newDateStr=currentDate.substring(2,4)+currentDate.substring(currentDate.indexOf("-")+1,currentDate.indexOf("-")+3)+currentDate.substring(currentDate.lastIndexOf("-")+1,currentDate.lastIndexOf("-")+3);
        String maxIsnum = stockChangeService.selectMaxIdnum(currentDate);
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

    public void morePurchaseStockDepotClick(){

        stage.setTitle("现有库位查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/stock/change_stock_depot_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
                /*stage.initStyle(StageStyle.UNDECORATED);
               DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
        loadMoreDepartment();
    }


    /**
     * 现有库位查询
     */
    public void loadMoreDepartment(){

        List<DepotBasic> departmentBasics = depotBasicService.selectDepotBasic();


        ObservableList<DepotBasic> list = FXCollections.observableArrayList();


//        depid.setCellValueFactory(new PropertyValueFactory("id"));
        finddepotid.setCellValueFactory(new PropertyValueFactory("isnum"));
        finddepotnames.setCellValueFactory(new PropertyValueFactory("depname"));

        for (DepotBasic depotBasic:departmentBasics) {

            list.add(depotBasic);

        }

        depotView.setItems(list); //tableview添加list

        depotView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DepotBasic>() {
            @Override
            public void changed(ObservableValue<? extends DepotBasic> observableValue, DepotBasic oldItem, DepotBasic newItem) {
                if(newItem.getIsnum()!= null && !("".equals(newItem.getIsnum()))){
                    depotView.setUserData(newItem.getId());
                }
            }
        });


        depotView.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeDepotWin();
            }
        });


    }

    public void closeDepotWin(){
        long id =(long)depotView.getUserData();
        DepotBasic depotBasic =  depotBasicService.selectByKey(id);

        depotorder.setText(depotBasic.getIsnum()); //入库仓库编号


        depotname.setText(depotBasic.getDepname()); //入库仓库名称


        coseWin();
    }

    public void coseWin(){
        stage.close();
    }




    //现有库存异动
    public void moreStockChangeClick(){

        stage.setTitle("现有库存异动");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/stock/stock_change_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
        loadStockChange();
    }




    public void loadStockChange(){

        List<StockChange> stockChanges = stockChangeService.findStockChange();


        ObservableList<StockChange> list = FXCollections.observableArrayList();



//        findstockchangeid.setCellValueFactory(new PropertyValueFactory("isnum"));
        findstockchangeorder.setCellValueFactory(new PropertyValueFactory("changeorder"));
        findstockchangedate.setCellValueFactory(new PropertyValueFactory("flgTime"));
        findstockchangedepotorder.setCellValueFactory(new PropertyValueFactory("depotorder"));
        findstockchangedepotname.setCellValueFactory(new PropertyValueFactory("depotname"));
        findstockchangeremarks.setCellValueFactory(new PropertyValueFactory("remarks"));

        for (StockChange stockChange:stockChanges) {

            stockChange.setFlgTime(new SimpleDateFormat("yyyy-MM-dd").format(stockChange.getChangedate()));

            list.add(stockChange);

        }

        stockChangeView.setItems(list); //tableview添加list

        stockChangeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockChange>() {
            @Override
            public void changed(ObservableValue<? extends StockChange> observableValue, StockChange oldItem, StockChange newItem) {
                if(newItem.getId()!= null && !("".equals(newItem.getId()))){
                    stockChangeView.setUserData(newItem.getId());
                }
            }
        });

        stockChangeView.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeStockChangeWin();
            }
        });
    }


    public void closeStockChangeWin(){

        long id =  (long)stockChangeView.getUserData();

        StockChange stockChange  = stockChangeService.selectByKey(id);

        loadDate(stockChange);

        coseWin();

    }





    /**
     * 根据库存异动编号查询库存异动产品
     * @param stockChangeId 库存异动编号
     *
     */
    public void loadStockChangeProduct(long stockChangeId){

        List<StockChangeProduct> stockChangeProducts = stockChangeProductService.findStockChangeProduct(stockChangeId);

        findchangesourse.setCellFactory(TextFieldTableCell.forTableColumn());
        findchangeorder.setCellFactory(TextFieldTableCell.forTableColumn());
        findchangesort.setCellFactory(TextFieldTableCell.forTableColumn());
        findproductorder.setCellFactory(TextFieldTableCell.forTableColumn());
        findproductname.setCellFactory(TextFieldTableCell.forTableColumn());
        findproductnum.setCellFactory(TextFieldTableCell.forTableColumn());
        findproductunit.setCellFactory(TextFieldTableCell.forTableColumn());
        finddepotorder.setCellFactory(TextFieldTableCell.forTableColumn());
        finddepotname.setCellFactory(TextFieldTableCell.forTableColumn());
        findproductremarks.setCellFactory(TextFieldTableCell.forTableColumn());

        findchangesourse.setCellValueFactory(new PropertyValueFactory("soursebill"));
        findchangeorder.setCellValueFactory(new PropertyValueFactory("sourseorder"));
        findchangesort.setCellValueFactory(new PropertyValueFactory("sort"));
        findproductorder.setCellValueFactory(new PropertyValueFactory("productorder"));
        findproductname.setCellValueFactory(new PropertyValueFactory("productname"));
        findproductnum.setCellValueFactory(new PropertyValueFactory("productnum"));
        findproductunit.setCellValueFactory(new PropertyValueFactory("unit"));
        finddepotorder.setCellValueFactory(new PropertyValueFactory("depotorder"));
        finddepotname.setCellValueFactory(new PropertyValueFactory("depotname"));
        findproductremarks.setCellValueFactory(new PropertyValueFactory("remarks"));

        stockChangeProductProperties.clear();

        if(stockChangeProducts.size()>0){

            for (StockChangeProduct stockChangeProduct :stockChangeProducts) {

                StockChangeProductProperty stockChangeProductProperty = new StockChangeProductProperty(stockChangeProduct.getId(),stockChangeProduct.getSoursebill(),stockChangeProduct.getSourseorder(),stockChangeProduct.getSort(),stockChangeProduct.getProductorder(),stockChangeProduct.getProductname(),stockChangeProduct.getProductnum(),stockChangeProduct.getUnit(),stockChangeProduct.getDepotorder(),stockChangeProduct.getDepotname(),stockChangeProduct.getRemarks());

                stockChangeProductProperties.add(stockChangeProductProperty);
            }
        }
        stockChangeProductView.setItems(stockChangeProductProperties);



        stockChangeProductView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockChangeProductProperty>() {
            @Override
            public void changed(ObservableValue<? extends StockChangeProductProperty> observableValue, StockChangeProductProperty oldItem, StockChangeProductProperty newItem) {
                changeId=newItem.getId();
            }
        });


    }


    public void saveStockChangeProduct(long stockChangeProductId){
        for (StockChangeProductProperty stockChangeProductProperty :stockChangeProductProperties) {
            if(stockChangeProductProperty.getSourseorder()!=null){
                if(stockChangeProductProperty.getId()>0){
                    //修改
                    StockChangeProduct stockChangeProduct = new StockChangeProduct(stockChangeProductProperty.getId(),stockChangeProductProperty.getSoursebill(),stockChangeProductProperty.getSourseorder(),stockChangeProductProperty.getSort(),stockChangeProductProperty.getProductorder(),stockChangeProductProperty.getProductname(),stockChangeProductProperty.getProductnum(),stockChangeProductProperty.getUnit(),stockChangeProductProperty.getDepotorder(),stockChangeProductProperty.getDepotname(),stockChangeProductProperty.getRemarks(),stockChangeProductId);
                    stockChangeProductService.updateNotNull(stockChangeProduct);
                }else{
                    //新增
                    StockChangeProduct stockChangeProduct = new StockChangeProduct(stockChangeProductProperty.getSoursebill(),stockChangeProductProperty.getSourseorder(),stockChangeProductProperty.getSort(),stockChangeProductProperty.getProductorder(),stockChangeProductProperty.getProductname(),stockChangeProductProperty.getProductnum(),stockChangeProductProperty.getUnit(),stockChangeProductProperty.getDepotorder(),stockChangeProductProperty.getDepotname(),stockChangeProductProperty.getRemarks(),stockChangeProductId);
                    stockChangeProductService.save(stockChangeProduct);
                }
            }
        }
    }

    public void blankStockChangeProduct(){
        StockChangeProductProperty stockChangeProductProperty = new StockChangeProductProperty();
        stockChangeProductProperties.add(stockChangeProductProperty);
    }




    public void pages(MouseEvent event){
        Node node =(Node)event.getSource();
        int pageNum =Integer.parseInt(String.valueOf(node.getUserData()));
        findInquiry(pageNum);
        NumberUtil.changeStatus(fxmlStatus,0);//修改为修改状态
    }




    /**
     * 分页查询供应商查询供应商
     */
    public void findInquiry(int pageNum){

        List<StockChange> stockChange = stockChangeService.findStockChange(pageNum,1);

        PageInfo<StockChange> pageInfo = new PageInfo<>(stockChange);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页





        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


       if(stockChange != null){
           stockChange.forEach(inquiryOrder ->loadDate(inquiryOrder));
       }

    if(stockChange.size()<0){
        loadStockChangeProduct(0);
    }



    }


    /**
     * 向控件加载数据
     * @param stockChange
     */
    public void loadDate(StockChange stockChange){

        changeorder.setUserData(stockChange.getId());

        changedate.setValue(LocalDateTime.ofInstant(stockChange.getChangedate().toInstant(), ZoneId.systemDefault()).toLocalDate());

        changeorder.setText(stockChange.getChangeorder());

        depotname.setText(stockChange.getDepotname());

        peoplename.setText(stockChange.getPeoplename());

        int changecny =  stockChange.getChangecurrency();

        setComboBox(7,changecurrency,--changecny);


        changesourse.setText(stockChange.getChangesourse());

        createpeople.setText(stockChange.getCreatepeople());

        shpeople.setText(stockChange.getShpeople());

        shdate.setText(stockChange.getShdate());

        updatepeople.setText(stockChange.getUpdatepeople());

        updatedate.setText(stockChange.getUpdatedate());

        remarks.setText(stockChange.getRemarks());

        int isSh = stockChange.getShstatus();

        if(isSh == 0){
            shyes.setDisable(false);
            shno.setDisable(true);
        }else{
            shyes.setDisable(true);
            shno.setDisable(false);
        }

        loadStockChangeProduct(stockChange.getId());

    }






    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){
                 changedate.setDisable(!status);
                  changeorder.setDisable(!status);
                depotorder.setDisable(!status);
                   depotname.setDisable(!status);
                changetype.setDisable(!status);
                 peopleorder.setDisable(!status);
                peoplename.setDisable(!status);
                 changecurrency.setDisable(!status);
                changesourse.setDisable(!status);
                  createpeople.setDisable(!status);
                shpeople.setDisable(!status);
                shdate.setDisable(!status);
                updatepeople.setDisable(!status);
                updatedate.setDisable(!status);
                 remarks.setDisable(!status);

                 //库存异动表格
        stockChangeProductView.setEditable(status);

    }



    /**
     * 清空
     */
    public void clearValue(){



        changedate.setValue(null);
        changeorder.setText(NumberUtil.NULLSTRING);
        depotorder.setText(NumberUtil.NULLSTRING);
        depotname.setText(NumberUtil.NULLSTRING);
        changetype.getSelectionModel().select(0);
        peopleorder.getSelectionModel().select(0);
        peoplename.setText(NumberUtil.NULLSTRING);
        changecurrency.getSelectionModel().select(0);
        changesourse.setText(NumberUtil.NULLSTRING);
        createpeople.setText(NumberUtil.NULLSTRING);
        shpeople.setText(NumberUtil.NULLSTRING);
        updatepeople.setText(NumberUtil.NULLSTRING);
        updatedate.setText(NumberUtil.NULLSTRING);
        remarks.setText(NumberUtil.NULLSTRING);
    }

    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) changeorder.getUserData();

            int rows = stockChangeService.delete(id);
            if (rows > 0) {
                findInquiry(1); //初始化第一条数据
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
        blankStockChangeProduct();
        submitvbox.setDisable(false);
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);

        lastUpdatePeopel(updatepeople,updatedate); //最后修改人 和最后修改日期
        //联系人空白行


    }


    /**
     *  新增按钮
     */
    public void insert(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.INSERT);//修改为新增状态
        this.changeEditable(true);
        clearValue();//清空控件
        blankStockChangeProduct();
        submitvbox.setDisable(false);
        updatevbox.setDisable(true);

        deletevbox.setDisable(true);
        insertvbox.setDisable(true);
        createPeople(createpeople);//制单人



    }


    /**
     * 提交
     */
    public  void submit(){

    int  submitStuts = (int)fxmlStatus.getUserData(); //1、新增 2、修改

    LocalDate orderDate =changedate.getValue();

        if(orderDate == null){
            alert_informationDialog("请填写制单日期!");
             return;
          }

            StockChange stockChange = new StockChange(
                    new Date(java.sql.Date.valueOf(orderDate).getTime()),
                    changeorder.getText(),
                    depotorder.getText(),
                    depotname.getText(),
                    changetype.getSelectionModel().getSelectedIndex()+1,
                    peopleorder.getItems().size()==0?"":peopleorder.getSelectionModel().getSelectedItem().toString(),
                    peoplename.getText(),
                    changecurrency.getSelectionModel().getSelectedIndex()+1,
                    changesourse.getText(),
                    createpeople.getText(),
                    shpeople.getText(),
                    shdate.getText(),
                    updatepeople.getText(),
                    updatedate.getText(),
                    remarks.getText(),
                    0
            );



    int rows =0;
        if(submitStuts==1){
        //产生订单编号
        String orderNum = createIsnum(orderDate.toString());
        changeorder.setText(orderNum);
            stockChange.setChangeorder(orderNum);
        rows = stockChangeService.save(stockChange);
    }else if(submitStuts ==2){
            stockChange.setId((long)changeorder.getUserData());
        rows = stockChangeService.updateNotNull(stockChange);
    }

        saveStockChangeProduct(stockChange.getId());

        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);



        this.loadDate(stockChange); //重新加载数据


        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);

}




    /**
     * 审核通过
     *
     * 修改审核人、审核日期
     *
     */
    public void shButtonSuccess(){
        shno.setDisable(false);
        shyes.setDisable(true);
        lastUpdatePeopel(shpeople,shdate);
        updateOrderStatus(1);
    }

    /**
     * 取消审核
     *
     * 需查看 单据是否被其他地方调用
     *
     */
    public void shButtonCancel(){
        shno.setDisable(true);
        shyes.setDisable(false);
        updateOrderStatus(0);
    }

    /**
     * 修改审核状态
     * @param status  0、未审核    1、审核通过
     */
    public void updateOrderStatus(int status){
        long id =  (long)changeorder.getUserData();
        StockChange stockChange = stockChangeService.selectByKey(id);
        stockChange.setShstatus(status);
        stockChangeService.updateNotNull(stockChange);
    }


    private int prveIndex = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {






        String newStr = location.toString();

        int index = newStr.indexOf("stock_change.fxml");

        if(index != -1) {

            peopleorder.getItems().clear();

            List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();

            for(int i=0,len=employeeBasics.size();i<len;i++){
                peopleorder.getItems().add(employeeBasics.get(i).getIdnum());
            }
            peopleorder.getSelectionModel().select(0);

            findInquiry(1);



            peopleorder.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                    if(!"".equals(newValue) && newValue != null ){


                    EmployeeBasic employeeBasic =  employeeBasicService.selectEmployeeBasicByIsnum(newValue.toString());


                        peoplename.setText(employeeBasic.getEmpname());

//                        List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();
//
//
//                        for(int i=0,len=employeeBasics.size();i<len;i++){
//                            peopleorder.getItems().set(i,"("+employeeBasics.get(i).getIdnum()+")"+employeeBasics.get(i).getEmpname());
//                        }
//
//                        peopleorder.getSelectionModel().select(0);
//
//
//                        prveIndex = peopleorder.getSelectionModel().getSelectedIndex();
//
//                        String str = newValue.toString();
//
//                        String empName = str.substring(str.indexOf(")")+1,str.length());
//
//                        String empOrder =  str.substring(str.indexOf("(")+1,str.indexOf(")"));
//
//
//                        peopleorder.getItems().set(prveIndex,empOrder);

//                        peoplename.setText(empName);

                    }

                }
            });


        }

    }

}
