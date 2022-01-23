package com.yc.education.controller.purchase;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.purchase.*;
import com.yc.education.service.purchase.PurchaseOrdersService;
import com.yc.education.service.purchase.PurchaseProductService;
import com.yc.education.service.purchase.TransportationInventoryService;
import com.yc.education.service.purchase.TransportationProductService;
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
import javafx.scene.control.cell.CheckBoxTableCell;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName TransportationInventoryController
 * @Description TODO 在途库存
 * @Author QuZhangJing
 * @Date 2018/10/17 10:21
 * @Version 1.0
 */
@Controller
public class TransportationInventoryController extends BaseController implements Initializable {

    @Autowired
    private TransportationInventoryService transportationInventoryService; //在途库存
    @Autowired
    private TransportationProductService transportationProductService;//在途产品
    @Autowired
    private PurchaseOrdersService purchaseOrdersService;//采购订单
    @Autowired
    private PurchaseProductService purchaseProductService;//采购产品





    @FXML
    private Label fxmlStatus; //状态

    @FXML private VBox first; //第一页

    @FXML private VBox prev; //上一页

    @FXML private VBox next; //下一页

    @FXML private VBox last; //最后一页

    long changeId=0;

    @FXML private VBox clearvbox; //清除

    @FXML private VBox submitvbox; //提交

    @FXML private VBox insertvbox; //新增

    @FXML private VBox updatevbox; //修改

    @FXML private VBox deletevbox; //删除

    @FXML private VBox importData;//导入


    @FXML private TextField orders;  //装箱单号

    @FXML private DatePicker senddate;  //发货日期

    @FXML private TextField invoicenum; //发票号码

    @FXML private DatePicker comedate;  //预计到货日期


    @FXML private TableView onthewayfind; //在途库存

    @FXML private TableColumn onthewayid; //编号

    @FXML private TableColumn findorders; //装箱单号

    @FXML private TableColumn findsenddate; //发货日期


    @FXML private TableView transportationProduct;
    @FXML private TableColumn productid; //
    @FXML private TableColumn purchaseorder; //
    @FXML private TableColumn sort; //
    @FXML private TableColumn pronum; //
    @FXML private TableColumn stocknum; //
    @FXML private TableColumn boxnum; //
    @FXML private TableColumn thistimeontheway; //
    @FXML private TableColumn totalnum; //
    @FXML private TableColumn stockover; //
    @FXML private TableColumn forcenum; //
    @FXML private TableColumn ontheway; //




    //导入--询价单据列表
    @FXML private TableView inquiryView;
    @FXML private TableColumn inquiryid; //单据ID
    @FXML private TableColumn findinquiryorder; //单据编号
    @FXML private TableColumn findinquirydata; //制单日期
    @FXML private TableColumn findsuppliernum; //供应商编号
    @FXML private TableColumn findstatus; //单据状态
    @FXML private TableColumn findcomestock; //到货仓库

    private long inquiryOrderId =0;

    //导入--采购订单产品
    @FXML private TableView inquiryProductView;
    @FXML private TableColumn inquiryproid; //询价单产品ID
    @FXML private TableColumn findproid; //产品编号
    @FXML private TableColumn finprosort; //产品序号
    @FXML private TableColumn findproname; //产品名称
    @FXML private TableColumn finpronum; //数量
    @FXML private TableColumn findproprice; //单价



    //在途产品之TabelView
    private ObservableList<TransportationProductProperty> transportationProductProperties = FXCollections.observableArrayList();
    //导入--询价单  采购订单产品导入数据TabelView
    private ObservableList<InquiryImportProperty> inquiryImportProperties = FXCollections.observableArrayList();


    private Stage stage = new Stage();

    private static SpringFxmlLoader loader = new SpringFxmlLoader();



    public void moreTransportationInventoryClick(){

        stage.setTitle("在途库存查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/ontheway_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();

        loadMoreInquiry();
    }


    public void loadMoreInquiry(){

        List<TransportationInventory> transportationInventory = transportationInventoryService.findTransportationInventory();

        ObservableList<TransportationInventory> list = FXCollections.observableArrayList();


        /*tableView4.setEditable(true);*/


        onthewayid.setCellValueFactory(new PropertyValueFactory("id"));
        findorders.setCellValueFactory(new PropertyValueFactory("orders"));
        findsenddate.setCellValueFactory(new PropertyValueFactory("senddates"));


        for (TransportationInventory transportationInventory1:transportationInventory) {
            transportationInventory1.setSenddates(new SimpleDateFormat("yyyy-MM-dd").format(transportationInventory1.getSenddate()));
            list.add(transportationInventory1);

        }

        onthewayfind.setItems(list); //tableview添加list

        onthewayfind.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TransportationInventory>() {
            @Override
            public void changed(ObservableValue<? extends TransportationInventory> observableValue, TransportationInventory oldItem, TransportationInventory newItem) {
                if(newItem.getOrders() != null && !("".equals(newItem.getOrders()))){
                    onthewayid.setUserData(newItem.getId());
                }
            }
        });


        onthewayfind.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeInquiryWin();
            }
        });
    }

    public void closeInquiryWin(){
        long id =(long)onthewayid.getUserData();
        TransportationInventory transportationInventory =  transportationInventoryService.selectByKey(id);
        orders.setText(transportationInventory.getOrders());
        invoicenum.setText(transportationInventory.getInvoicenum());
        senddate.setValue(LocalDateTime.ofInstant(transportationInventory.getSenddate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        comedate.setValue(LocalDateTime.ofInstant(transportationInventory.getComedate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        coseInquiryWin();
    }

    public void coseInquiryWin(){
        stage.close();
    }






    public void inquiryProduct(){


       List<TransportationProduct> transportationProducts =  transportationProductService.findTransportationProductByParentid((long)orders.getUserData());


        purchaseorder.setCellFactory(TextFieldTableCell.forTableColumn());
        sort.setCellFactory(TextFieldTableCell.forTableColumn());
        pronum.setCellFactory(TextFieldTableCell.forTableColumn());
        forcenum.setCellFactory(TextFieldTableCell.forTableColumn());
        stocknum.setCellFactory(TextFieldTableCell.forTableColumn());
        boxnum.setCellFactory(TextFieldTableCell.forTableColumn());
        thistimeontheway.setCellFactory(TextFieldTableCell.forTableColumn());
        totalnum.setCellFactory(TextFieldTableCell.forTableColumn());
        stockover.setCellFactory(TextFieldTableCell.forTableColumn());
        forcenum.setCellFactory(TextFieldTableCell.forTableColumn());
        ontheway.setCellFactory(TextFieldTableCell.forTableColumn());


        productid.setCellValueFactory(new PropertyValueFactory("id"));
        purchaseorder.setCellValueFactory(new PropertyValueFactory("purchaseorder"));
        sort.setCellValueFactory(new PropertyValueFactory("sort"));
        pronum.setCellValueFactory(new PropertyValueFactory("pronum"));
        forcenum.setCellValueFactory(new PropertyValueFactory("forcenum"));
        stocknum.setCellValueFactory(new PropertyValueFactory("stocknum"));
        boxnum.setCellValueFactory(new PropertyValueFactory("boxnum"));
        thistimeontheway.setCellValueFactory(new PropertyValueFactory("thistimeontheway"));
        totalnum.setCellValueFactory(new PropertyValueFactory("totalnum"));
        stockover.setCellValueFactory(new PropertyValueFactory("stockover"));
        forcenum.setCellValueFactory(new PropertyValueFactory("forcenum"));
        ontheway.setCellValueFactory(new PropertyValueFactory("ontheway"));



        transportationProductProperties.clear();

        if(transportationProducts.size()>0){
            for (TransportationProduct transportationProduct:transportationProducts) {

                TransportationProductProperty transportationProductProperty = new TransportationProductProperty(transportationProduct.getId(),transportationProduct.getPurchaseorder(),transportationProduct.getSort(),transportationProduct.getPronum(),transportationProduct.getStocknum(),transportationProduct.getBoxnum(),transportationProduct.getThistimeontheway(),transportationProduct.getTotalnum(),transportationProduct.getStockover(),transportationProduct.getForcenum(),transportationProduct.getOntheway());

                transportationProductProperties.add(transportationProductProperty);
            }
        }
        transportationProduct.setItems(transportationProductProperties); //tableview添加list



        transportationProduct.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TransportationProductProperty>() {
            @Override
            public void changed(ObservableValue<? extends TransportationProductProperty> observableValue, TransportationProductProperty oldItem, TransportationProductProperty newItem) {
                changeId=newItem.getId();
            }
        });


    }
    //保存在途产品
    public void saveTransportationProduct(long forceid){
        for (TransportationProductProperty transportationProductProperty :transportationProductProperties) {
            if(transportationProductProperty.getPurchaseorder()!=null){

                //计算最新在途数量
                int onthewaynum =transportationProductProperty.getOntheway()+transportationProductProperty.getThistimeontheway();
                //修改在途数量
                transportationProductProperty.setOntheway(onthewaynum+"");

                    PurchaseOrders purchaseOrders =  purchaseOrdersService.findPurchaseByOrder(transportationProductProperty.getPurchaseorder());

                    if(purchaseOrders !=null){
                          PurchaseProduct purchaseProduct =  purchaseProductService.findPurchaseProductByPronum(purchaseOrders.getId(),transportationProductProperty.getPronum());

                            if(purchaseProduct != null){
                                purchaseProduct.setOntheway(onthewaynum);
                                purchaseProductService.updateNotNull(purchaseProduct);
                            }
                    }

                transportationProductProperty.setThistimeontheway(0+"");
                if(transportationProductProperty.getId()>0){
                    TransportationProduct transportationProduct = new TransportationProduct(transportationProductProperty.getId(),forceid,transportationProductProperty.getPurchaseorder(),transportationProductProperty.getSort(),transportationProductProperty.getPronum(),transportationProductProperty.getStocknum(),transportationProductProperty.getBoxnum(),transportationProductProperty.getThistimeontheway(),transportationProductProperty.getTotalnum(),transportationProductProperty.getStockover(),transportationProductProperty.getForcenum(),transportationProductProperty.getOntheway());
                    transportationProductService.updateNotNull(transportationProduct);
                }else{
                    TransportationProduct transportationProduct = new TransportationProduct(forceid,transportationProductProperty.getPurchaseorder(),transportationProductProperty.getSort(),transportationProductProperty.getPronum(),transportationProductProperty.getStocknum(),transportationProductProperty.getBoxnum(),transportationProductProperty.getThistimeontheway(),transportationProductProperty.getTotalnum(),transportationProductProperty.getStockover(),transportationProductProperty.getForcenum(),transportationProductProperty.getOntheway());
                    transportationProductService.save(transportationProduct);
                }
            }
        }
    }

    //在途产品空白
    public void blankTransportationProduct(){
        TransportationProductProperty transportationProductProperty = new TransportationProductProperty();
        transportationProductProperties.add(transportationProductProperty);
    }














    /**
     * 上下首尾跳页
     * @param event
     */
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

        List<TransportationInventory> transportationInventories = transportationInventoryService.findTransportationInventory(pageNum,1);

        PageInfo<TransportationInventory> pageInfo = new PageInfo<>(transportationInventories);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页

        transportationInventories.forEach(forceOrder1 ->loadDate(forceOrder1));

        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);

        if(transportationInventories.size()<=0){
            orders.setUserData(0L);
            inquiryProduct();
        }



    }


    /**
     * 向控件加载数据
     * @param transportationInventory
     */
    public void loadDate(TransportationInventory transportationInventory){

        orders.setUserData(transportationInventory.getId());
        //装箱单号
        orders.setText(transportationInventory.getOrders());
        //发货日期
        senddate.setValue(LocalDateTime.ofInstant(transportationInventory.getSenddate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        //发票号码
        invoicenum.setText(transportationInventory.getInvoicenum());
        //预计到货日期
        comedate.setValue(LocalDateTime.ofInstant(transportationInventory.getComedate().toInstant(), ZoneId.systemDefault()).toLocalDate());


        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);

        inquiryProduct();

    }


    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){

        orders.setDisable(!status);

        senddate.setDisable(!status);

        invoicenum.setDisable(!status);

        comedate.setDisable(!status);

        transportationProduct.setEditable(status);

        importData.setDisable(!status);

    }



    /**
     * 清空
     */
    public void clearValue(){

        orders.setText(NumberUtil.NULLSTRING);

        invoicenum.setText(NumberUtil.NULLSTRING);

    }

    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) orders.getUserData();
            int rows = transportationInventoryService.delete(id);
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
        blankTransportationProduct();
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
        blankTransportationProduct();
        submitvbox.setDisable(false);
        updatevbox.setDisable(true);
        deletevbox.setDisable(true);
        insertvbox.setDisable(true);

    }


    /**
     * 提交
     */
    public  void submit(){

        int  submitStuts = (int)fxmlStatus.getUserData(); //1、新增 2、修改

        LocalDate orderDate =senddate.getValue();

        String supperIsnum =orders.getText();

        if(supperIsnum == "" || "".equals(supperIsnum)){
            alert_informationDialog("请填写装箱单号!");
            return;
        }
        if(orderDate == null){
            alert_informationDialog("请填写发货日期!");
            return;
        }

        TransportationInventory transportationInventory = new TransportationInventory(supperIsnum,
                new Date(java.sql.Date.valueOf(orderDate).getTime()),
                invoicenum.getText(),
                new Date(java.sql.Date.valueOf(comedate.getValue()).getTime())
                );

        int rows =0;
        if(submitStuts==1){
            rows = transportationInventoryService.save(transportationInventory);
        }else if(submitStuts ==2){
            transportationInventory.setId((long)orders.getUserData());
            rows = transportationInventoryService.updateNotNull(transportationInventory);
        }

        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);




        saveTransportationProduct(transportationInventory.getId());

        this.loadDate(transportationInventory); //重新加载数据

    }


    //在途产品表格处理
    public void transportationProductKey(KeyEvent event){

        if (event.getCode() == KeyCode.DELETE) {


            transportationProductService.delete(changeId);



            ObservableList<TransportationProductProperty> transportationProductPropertiesNew = FXCollections.observableArrayList();

            for (TransportationProductProperty transportationProductProperty : transportationProductProperties){
                if(transportationProductProperty.getId() != changeId){
                    transportationProductPropertiesNew .add(transportationProductProperty);
                }
            }

            transportationProductProperties.clear();
            transportationProductProperties.setAll(transportationProductPropertiesNew);

        }


        if (event.getCode() == KeyCode.INSERT) {
            //联系人空白行
            /* blankContact();*/
            blankTransportationProduct();
        }

    }




    public void importButtonInquiry(){

        stage.setTitle("导入-采购订单");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/purchase_import.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
        loadMoreInquiryImport();
    }

    public void loadMoreInquiryImport(){

        List<PurchaseOrders> purchaseOrders = purchaseOrdersService.findPurchaseOrders();

        ObservableList<PurchaseOrders> list = FXCollections.observableArrayList();


        inquiryid.setCellValueFactory(new PropertyValueFactory("id"));
        findinquiryorder.setCellValueFactory(new PropertyValueFactory("orders"));
        findinquirydata.setCellValueFactory(new PropertyValueFactory("paremdate"));
        findsuppliernum.setCellValueFactory(new PropertyValueFactory("suppliernum"));
        findstatus.setCellValueFactory(new PropertyValueFactory("strstatus"));
        findcomestock.setCellValueFactory(new PropertyValueFactory("warehousename"));


        for (PurchaseOrders purchaseOrders1:purchaseOrders) {

            purchaseOrders1.setParemdate(new SimpleDateFormat("yyyy-MM-dd").format(purchaseOrders1.getCreatedate()));

            if(purchaseOrders1.getShstatus()==0){
                purchaseOrders1.setStrstatus("未审核");
            }else{
                purchaseOrders1.setStrstatus("已审核");
            }

            list.add(purchaseOrders1);

        }

        inquiryView.setItems(list); //tableview添加list

        inquiryView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseOrders>() {
            @Override
            public void changed(ObservableValue<? extends PurchaseOrders> observableValue, PurchaseOrders oldItem, PurchaseOrders newItem) {
                if(newItem != null && newItem.getId()>0){
                    loadMoreInquiryProductImport(newItem.getId());
                }
            }
        });
    }



    public void loadMoreInquiryProductImport(long id){
        inquiryOrderId=id;
        List<PurchaseProduct> purchaseProducts = purchaseProductService.findPurchaseProduct(id);
        inquiryProductView.setEditable(true);
        inquiryproid.setCellFactory(CheckBoxTableCell.forTableColumn(inquiryproid));

        inquiryproid.setCellValueFactory(new PropertyValueFactory("checked"));
        findproid.setCellValueFactory(new PropertyValueFactory("proisnum"));
        finprosort.setCellValueFactory(new PropertyValueFactory("sort"));
        findproname.setCellValueFactory(new PropertyValueFactory("proname"));
        finpronum.setCellValueFactory(new PropertyValueFactory("pronum"));
        findproprice.setCellValueFactory(new PropertyValueFactory("proprice"));

        inquiryImportProperties.clear();

        for (PurchaseProduct inquiryProduct:purchaseProducts) {

            InquiryImportProperty inquiryImportProperty = new InquiryImportProperty(false,inquiryProduct.getId(),inquiryProduct.getProorders(),inquiryProduct.getSort(),inquiryProduct.getProname(),inquiryProduct.getNum(),inquiryProduct.getPrice());

            inquiryImportProperties.add(inquiryImportProperty);

        }

        inquiryProductView.setItems(inquiryImportProperties); //tableview添加list

    }



    //确定导入产品
    public  void importInquiryProductData(){

        PurchaseOrders purchaseOrders = purchaseOrdersService.selectByKey(inquiryOrderId);

        for(InquiryImportProperty inquiryImportProperty : inquiryImportProperties){
            //选中导入的产品
            if(inquiryImportProperty.isChecked()){
                PurchaseProduct purchaseProduct = purchaseProductService.selectByKey(inquiryImportProperty.getId());
                TransportationProductProperty purchaseProductProperty = new TransportationProductProperty(0,purchaseOrders.getOrders(),purchaseProduct.getSort(),purchaseProduct.getProorders(),purchaseOrders.getWarehousename(),"",0,purchaseProduct.getNum(),purchaseProduct.getStockover(),purchaseProduct.getForcenum(),purchaseProduct.getOntheway());
                transportationProductProperties.add(purchaseProductProperty);
            }
        }
        coseInquiryWin();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化

        String newStr = location.toString();

        int index = newStr.indexOf("ontheway.fxml");

        if(index != -1){
            findInquiry(1);
        }

    }
}
