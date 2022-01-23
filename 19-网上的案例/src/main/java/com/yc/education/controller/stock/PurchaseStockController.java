package com.yc.education.controller.stock;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.DepotBasic;
import com.yc.education.model.basic.SupplierBasic;
import com.yc.education.model.purchase.*;
import com.yc.education.model.stock.PurchaseStock;
import com.yc.education.model.stock.PurchaseStockProduct;
import com.yc.education.model.stock.PurchaseStockProductProperty;
import com.yc.education.service.basic.DepotBasicService;
import com.yc.education.service.basic.SupplierBasicService;
import com.yc.education.service.purchase.PurchaseOrdersService;
import com.yc.education.service.purchase.PurchaseProductService;
import com.yc.education.service.stock.PurchaseStockProductService;
import com.yc.education.service.stock.PurchaseStockService;
import com.yc.education.util.NumberUtil;
import com.yc.education.util.StageManager;
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
 * @ClassName PurchaseStockController
 * @Description TODO 采购入库单
 * @Author QuZhangJing
 * @Date 2018/10/24 15:54
 * @Version 1.0
 */
@Controller
public class PurchaseStockController extends BaseController implements Initializable {

    @Autowired
    private PurchaseStockService purchaseStockService;

    @Autowired
    private PurchaseStockProductService purchaseStockProductService;

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;

    @Autowired
    private PurchaseProductService purchaseProductService;

    @Autowired
    private SupplierBasicService supplierBasicService;

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


    @FXML private DatePicker createdate; //制单日期

    @FXML private TextField stockorder; //入库单号

    @FXML private TextField depotnum; //入库仓库编号

    @FXML private TextField depotname; //入库仓库名称

    @FXML private TextField depotfloor;//楼层

    @FXML private TextField suppliernum; //供应商编号

    @FXML private TextField suppliername;//供应商名称

    @FXML private TextField boxnum; //装箱单号

    @FXML private ComboBox inspectnum; //质检人编号

    @FXML private TextField inspectname;  //质检人名称

    @FXML private TextField createname; //制单人

    @FXML private TextField shpeople; //审核人

    @FXML private TextField shdate; //审核时间

    @FXML private TextField updatepeople; //最后修改人

    @FXML private TextField updatedate; //最后修改日期

    @FXML private TextArea remarks; //备注



    @FXML private TableView purchaseStockProductView;//采购入库单产品
    @FXML private TableColumn pro_sourseorder; //来源单据
    @FXML private TableColumn pro_orders;//单号
    @FXML private TableColumn pro_sort;//序号
    @FXML private TableColumn pro_seeorder;//参考单号
    @FXML private TableColumn pro_pronum;//产品编号
    @FXML private TableColumn pro_proname;//产品名称
    @FXML private TableColumn pro_stocknum;//入库数量
    @FXML private TableColumn pro_units;//单位
    @FXML private TableColumn pro_depotnum;//仓库编号
    @FXML private TableColumn pro_depotname;//仓库名称
    @FXML private TableColumn pro_remarks;//备注



    private long changeId = 0;


    private long inquiryOrderId =0;



    //导入--询价单据列表
    @FXML private TableView inquiryView;
    @FXML private TableColumn inquiryid; //单据ID
    @FXML private TableColumn findinquiryorder; //单据编号
    @FXML private TableColumn findinquirydata; //制单日期
    @FXML private TableColumn findsuppliernum; //供应商编号
    @FXML private TableColumn findstatus; //单据状态
    @FXML private TableColumn findcomestock; //到货仓库



    //导入--询价单据产品
    @FXML private TableView inquiryProductView;
    @FXML private TableColumn inquiryproid; //询价单产品ID
    @FXML private TableColumn findproid; //产品编号
    @FXML private TableColumn finprosort; //产品序号
    @FXML private TableColumn findproname; //产品名称
    @FXML private TableColumn finpronum; //数量
    @FXML private TableColumn findproprice; //单价



    //查询更多供应商
    @FXML private TableView tableView3; //供应商查询
    @FXML private TableColumn supid; //id
    @FXML private TableColumn findsupplierid; //供应商编号
    @FXML private TableColumn findsuppliername; //供应商简称

    //采购入库单
    @FXML private TableView purchaseStockView; //采购入库单
    @FXML private TableColumn findid; //  编号
    @FXML private TableColumn findstockorder; //采购入库单
    @FXML private TableColumn findcreatedate; //制单日期
    @FXML private TableColumn findstocksuppliernum; //供应商编号
    @FXML private TableColumn findstocksuppliername; //供应商简称
    @FXML private TableColumn findboxnum; //装箱单号
    @FXML private TableColumn findremarks; //备注

    //仓库库位
    @FXML private TableView depotView; //仓库库位
    @FXML private TableColumn depid; //  编号
    @FXML private TableColumn finddepotid; //  仓库编号
    @FXML private TableColumn finddepotname; //  仓库名称




    //采购入库单产品之TableView数据绑定
    private ObservableList<PurchaseStockProductProperty> purchaseStockProductProperties = FXCollections.observableArrayList();

    //采购订单产品导入
    private ObservableList<InquiryImportProperty> importProperties = FXCollections.observableArrayList();

    private Stage stage = new Stage();

    private static SpringFxmlLoader loader = new SpringFxmlLoader();





    /**
     * 生成询价订单流水号 (A1809280001)
     * 格式:英文字母(A)+日期(180928)+4位流水号(0001)
     * @return
     */
    public String createIsnum(String currentDate){
        String newDateStr=currentDate.substring(2,4)+currentDate.substring(currentDate.indexOf("-")+1,currentDate.indexOf("-")+3)+currentDate.substring(currentDate.lastIndexOf("-")+1,currentDate.lastIndexOf("-")+3);
        String maxIsnum = purchaseStockService.selectMaxIdnum(currentDate);
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
        pane.getChildren().setAll(loader.load("/fxml/stock/purchase_stock_depot_find.fxml"));
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


        /*depid.setCellValueFactory(new PropertyValueFactory("id"));*/
        finddepotid.setCellValueFactory(new PropertyValueFactory("isnum"));
        finddepotname.setCellValueFactory(new PropertyValueFactory("depname"));

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

        depotnum.setText(depotBasic.getIsnum()); //入库仓库编号

        depotname.setText(depotBasic.getDepname()); //入库仓库名称

        depotfloor.setText(depotBasic.getDepfloor()); //楼层

        coseWin();
    }






        /**
         * 查询更多采购入库单
         */
        public void morePurchaseStockClick(){

            stage.setTitle("现有采购入库单");
            Pane pane = new Pane();
            pane.getChildren().setAll(loader.load("/fxml/stock/purchase_stock_find.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
                /*stage.setResizable(false);*/
                /*stage.initStyle(StageStyle.UNDECORATED);
               DragUtil.addDragListener(stage, pane); //拖拽监听*/
            stage.show();
            loadMorePurchaseStock();
        }


        public void loadMorePurchaseStock(){

            List<PurchaseStock> purchaseStocks  =  purchaseStockService.findPurchaseStock();

            ObservableList<PurchaseStock> list =FXCollections.observableArrayList();

            findid.setCellValueFactory(new PropertyValueFactory("id"));
            findstockorder.setCellValueFactory(new PropertyValueFactory("stockorder"));
            findcreatedate.setCellValueFactory(new PropertyValueFactory("createdate"));
            findstocksuppliernum.setCellValueFactory(new PropertyValueFactory("suppliernum"));
            findstocksuppliername.setCellValueFactory(new PropertyValueFactory("suppliername"));
            findboxnum.setCellValueFactory(new PropertyValueFactory("boxnum"));
            findremarks.setCellValueFactory(new PropertyValueFactory("remarks"));

            if(purchaseStocks.size()>0){

                for (PurchaseStock purchaseStock:purchaseStocks) {
                    list.add(purchaseStock);
                }

            }

            purchaseStockView.setItems(list);


            purchaseStockView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseStock>() {
                @Override
                public void changed(ObservableValue<? extends PurchaseStock> observableValue, PurchaseStock oldItem, PurchaseStock newItem) {
                    if(newItem.getStockorder() != null && !("".equals(newItem.getStockorder()))){
                        purchaseStockView.setUserData(newItem.getId());
                    }
                }
            });


            purchaseStockView.setOnMouseClicked((MouseEvent t) -> {
                if (t.getClickCount() == 2) {
                    closePurchaseStockWin();
                }
            });


        }


    public void closePurchaseStockWin(){
            long id =(long)purchaseStockView.getUserData();
            PurchaseStock purchaseStock =  purchaseStockService.selectByKey(id);
            loadDate(purchaseStock);
        coseWin();
    }





    /**
     * 点击弹出 现有供应商查询
     */
    public void moreSupplierClick(){

        stage.setTitle("现有供应商查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/stock/purchase_stock_supplier_find.fxml"));
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

//        supid.setCellValueFactory(new PropertyValueFactory("id"));
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
                    suppliernum.setUserData(newItem.getId());
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
        long id =(long)suppliernum.getUserData();
        SupplierBasic supplierBasic =  supplierBasicService.selectByKey(id);
        suppliernum.setText(supplierBasic.getIdnum());
        suppliername.setText(supplierBasic.getSupdes());
        coseWin();
    }

    public void coseWin(){
        stage.close();
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

        List<PurchaseStock> purchaseStock = purchaseStockService.findPurchaseStock(pageNum,1);

        PageInfo<PurchaseStock> pageInfo = new PageInfo<>(purchaseStock);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页

        purchaseStock.forEach(inquiryOrder ->loadDate(inquiryOrder));


        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


        if(purchaseStock.size()<=0){
         loadPurchaseStockProduct(0);
        }


    }


    /**
     * 向控件加载数据
     * @param purchaseStock
     */
    public void loadDate(PurchaseStock purchaseStock){

        stockorder.setUserData(purchaseStock.getId());

        createdate.setValue(LocalDateTime.ofInstant(purchaseStock.getCreatedate().toInstant(), ZoneId.systemDefault()).toLocalDate());

        stockorder.setText(purchaseStock.getStockorder());

        depotnum.setText(purchaseStock.getDepotnum());

        depotname.setText(purchaseStock.getDepotname());

        depotfloor.setText(purchaseStock.getDepotfloor());

        suppliernum.setText(purchaseStock.getSuppliernum());

        suppliername.setText(purchaseStock.getSuppliername());

        boxnum.setText(purchaseStock.getBoxnum());

        inspectnum.getSelectionModel().select(0);

        inspectname.setText(purchaseStock.getInspectname());

        createname.setText(purchaseStock.getCreatename());

        shpeople.setText(purchaseStock.getShpeople());

        shdate.setText(purchaseStock.getShdate());

        updatepeople.setText(purchaseStock.getUpdatepeople());

        updatedate.setText(purchaseStock.getUpdatedate());

        remarks.setText(purchaseStock.getRemarks());


        int isSh = purchaseStock.getShstatus();

        if(isSh == 0){
            shyes.setDisable(false);
            shno.setDisable(true);
        }else{
            shyes.setDisable(true);
            shno.setDisable(false);
        }


        loadPurchaseStockProduct(purchaseStock.getId());


    }






    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){

        createdate.setDisable(!status);

        stockorder.setDisable(!status);

        depotnum.setDisable(!status);

        depotname.setDisable(!status);

        depotfloor.setDisable(!status);

        suppliernum.setDisable(!status);

        suppliername.setDisable(!status);

        boxnum.setDisable(!status);

        inspectnum.setDisable(!status);

        inspectname.setDisable(!status);

        createname.setDisable(!status);

        shpeople.setDisable(!status);

        shdate.setDisable(!status);

        updatepeople.setDisable(!status);

        updatedate.setDisable(!status);

        remarks.setDisable(!status);

        purchaseStockProductView.setEditable(status);

    }



    /**
     * 清空
     */
    public void clearValue(){

        createdate.setValue(null);

        stockorder.setText(NumberUtil.NULLSTRING);

        depotnum.setText(NumberUtil.NULLSTRING);

        depotname.setText(NumberUtil.NULLSTRING);

        depotfloor.setText(NumberUtil.NULLSTRING);

        suppliernum.setText(NumberUtil.NULLSTRING);

        suppliername.setText(NumberUtil.NULLSTRING);

        boxnum.setText(NumberUtil.NULLSTRING);

        inspectnum.getSelectionModel().select(0);

        inspectname.setText(NumberUtil.NULLSTRING);

        createname.setText(NumberUtil.NULLSTRING);

        shpeople.setText(NumberUtil.NULLSTRING);

        shdate.setText(NumberUtil.NULLSTRING);

        updatepeople.setText(NumberUtil.NULLSTRING);

        updatedate.setText(NumberUtil.NULLSTRING);

        remarks.setText(NumberUtil.NULLSTRING);

    }

    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) stockorder.getUserData();
            List<PurchaseStockProduct> purchaseStockProduct = purchaseStockProductService.findStockProductBypurchaseStockId(id);
            purchaseStockProduct.forEach(purchaseStockProduct1 -> {
                purchaseStockProductService.delete(purchaseStockProduct1.getId());
            });

            int rows = purchaseStockService.delete(id);
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

        submitvbox.setDisable(false);
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);

        lastUpdatePeopel(updatepeople,updatedate); //最后修改人 和最后修改日期
        //联系人空白行
        blankPurchaseStockProduct();


    }


    /**
     *  新增按钮
     */
    public void insert(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.INSERT);//修改为新增状态
        this.changeEditable(true);
        clearValue();//清空控件
        submitvbox.setDisable(false);
        updatevbox.setDisable(true);
        deletevbox.setDisable(true);
        insertvbox.setDisable(true);
        createPeople(createname);//制单人

        purchaseStockProductProperties.clear();
        blankPurchaseStockProduct();

    }


    /**
     * 提交
     */
    public  void submit(){

        int  submitStuts = (int)fxmlStatus.getUserData(); //1、新增 2、修改

        LocalDate orderDate =createdate.getValue();

        String supperIsnum =suppliernum.getText();

        if(supperIsnum == "" || "".equals(supperIsnum)){
            alert_informationDialog("请填写供应商编号!");
            return;
        }
        if(orderDate == null){
            alert_informationDialog("请填写制单日期!");
            return;
        }

            PurchaseStock purchaseStock = new PurchaseStock(
                    new Date(java.sql.Date.valueOf(orderDate).getTime()),
                    stockorder.getText(),
                    depotnum.getText(),
                    depotname.getText(),
                    depotfloor.getText(),
                    suppliernum.getText(),
                    suppliername.getText(),
                    boxnum.getText(),
                    inspectnum.getItems().size()==0?"":inspectnum.getSelectionModel().getSelectedItem().toString(),
                    inspectname.getText(),
                    createname.getText(),
                    shpeople.getText(),
                    shdate.getText(),
                    updatepeople.getText(),
                    updatedate.getText(),
                    remarks.getText(),0
            );


        int rows =0;
        if(submitStuts==1){
            //产生订单编号
            String orderNum = createIsnum(orderDate.toString());
            stockorder.setText(orderNum);
            purchaseStock.setStockorder(orderNum);
            rows = purchaseStockService.save(purchaseStock);
        }else if(submitStuts ==2){
            purchaseStock.setId((long)stockorder.getUserData());
            rows = purchaseStockService.updateNotNull(purchaseStock);
        }

        savePurchaseStockProduct(purchaseStock.getId());
        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);

        this.loadDate(purchaseStock); //重新加载数据

        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);

    }







    public void loadPurchaseStockProduct(long id){

        List<PurchaseStockProduct> purchaseStockProducts = purchaseStockProductService.findStockProductBypurchaseStockId(id);


        pro_sourseorder.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_orders.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_sort.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_seeorder.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_pronum.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_proname.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_stocknum.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_units.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_depotnum.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_depotname.setCellFactory(TextFieldTableCell.forTableColumn());
        pro_remarks.setCellFactory(TextFieldTableCell.forTableColumn());

        pro_sourseorder.setCellValueFactory(new PropertyValueFactory("sourseorder"));
        pro_orders.setCellValueFactory(new PropertyValueFactory("orders"));
        pro_sort.setCellValueFactory(new PropertyValueFactory("sort"));
        pro_seeorder.setCellValueFactory(new PropertyValueFactory("seeorder"));
        pro_pronum.setCellValueFactory(new PropertyValueFactory("pronum"));
        pro_proname.setCellValueFactory(new PropertyValueFactory("proname"));
        pro_stocknum.setCellValueFactory(new PropertyValueFactory("stocknum"));
        pro_units.setCellValueFactory(new PropertyValueFactory("units"));
        pro_depotnum.setCellValueFactory(new PropertyValueFactory("depotnum"));
        pro_depotname.setCellValueFactory(new PropertyValueFactory("depotname"));
        pro_remarks.setCellValueFactory(new PropertyValueFactory("remarks"));



        purchaseStockProductProperties.clear();


        if(purchaseStockProducts.size()>0){

            for (PurchaseStockProduct purchaseStockProduct:purchaseStockProducts) {

                PurchaseStockProductProperty purchaseStockProductProperty = new PurchaseStockProductProperty(purchaseStockProduct.getId(),purchaseStockProduct.getSourseorder(),purchaseStockProduct.getOrders(),purchaseStockProduct.getSort(),purchaseStockProduct.getSeeorder(),purchaseStockProduct.getPronum(),purchaseStockProduct.getProname(),purchaseStockProduct.getStocknum(),purchaseStockProduct.getUnits(),purchaseStockProduct.getDepotnum(),purchaseStockProduct.getDepotname(),purchaseStockProduct.getRemarks());

                purchaseStockProductProperties.add(purchaseStockProductProperty);

            }

        }
        purchaseStockProductView.setItems(purchaseStockProductProperties);


        purchaseStockProductView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseStockProductProperty>() {
            @Override
            public void changed(ObservableValue<? extends PurchaseStockProductProperty> observableValue, PurchaseStockProductProperty oldItem, PurchaseStockProductProperty newItem) {
                changeId=newItem.getId();
            }
        });


    }


    //采购入库单新增修改
    public void savePurchaseStockProduct(long purchaseStockid){
        for (PurchaseStockProductProperty purchaseStockProductProperty :purchaseStockProductProperties) {
            if(purchaseStockProductProperty.getSourseorder()!=null){
                if(purchaseStockProductProperty.getId()>0){
                    //新增
                    PurchaseStockProduct purchaseStockProduct = new PurchaseStockProduct(
                            purchaseStockProductProperty.getId(),
                            purchaseStockProductProperty.getSourseorder(),
                            purchaseStockProductProperty.getOrders(),
                            purchaseStockProductProperty.getSort(),
                            purchaseStockProductProperty.getSeeorder(),
                            purchaseStockProductProperty.getPronum(),
                            purchaseStockProductProperty.getProname(),
                            purchaseStockProductProperty.getStocknum(),
                            purchaseStockProductProperty.getUnits(),
                            purchaseStockProductProperty.getDepotnum(),
                            purchaseStockProductProperty.getDepotname(),
                            purchaseStockProductProperty.getRemarks(),purchaseStockid);

                    purchaseStockProductService.updateNotNull(purchaseStockProduct);
                }else{
                    //修改
                    PurchaseStockProduct purchaseStockProduct = new PurchaseStockProduct(
                            purchaseStockProductProperty.getSourseorder(),
                            purchaseStockProductProperty.getOrders(),
                            purchaseStockProductProperty.getSort(),
                            purchaseStockProductProperty.getSeeorder(),
                            purchaseStockProductProperty.getPronum(),
                            purchaseStockProductProperty.getProname(),
                            purchaseStockProductProperty.getStocknum(),
                            purchaseStockProductProperty.getUnits(),
                            purchaseStockProductProperty.getDepotnum(),
                            purchaseStockProductProperty.getDepotname(),
                            purchaseStockProductProperty.getRemarks(),purchaseStockid);
                    purchaseStockProductService.save(purchaseStockProduct);
                }
            }
        }
    }


    //采购入库单产品空白
    public void blankPurchaseStockProduct(){
        PurchaseStockProductProperty purchaseStockProductProperty = new PurchaseStockProductProperty();
        purchaseStockProductProperties.add(purchaseStockProductProperty);
    }





    //导入--采购订单
    public  void importPurchaseOrder(){

        stage.setTitle("导入-采购订单");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/stock/purchase_import.fxml"));
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
        /*findcomestock.setCellValueFactory(new PropertyValueFactory("purpeople"));*/


        for (PurchaseOrders inquiryOrder1:purchaseOrders) {

            inquiryOrder1.setParemdate(new SimpleDateFormat("yyyy-MM-dd").format(inquiryOrder1.getCreatedate()));

            if(inquiryOrder1.getShstatus()==0){
                inquiryOrder1.setStrstatus("未审核");
            }else{

                inquiryOrder1.setStrstatus("已审核");
            }

            list.add(inquiryOrder1);

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

        importProperties.clear();

        for (PurchaseProduct purchaseProduct:purchaseProducts) {

            InquiryImportProperty inquiryImportProperty = new InquiryImportProperty(false,purchaseProduct.getId(),purchaseProduct.getProorders(),0,purchaseProduct.getProname(),purchaseProduct.getNum(),purchaseProduct.getPrice());

            importProperties.add(inquiryImportProperty);

        }

        inquiryProductView.setItems(importProperties); //tableview添加list

    }




    //导入----根据询价单据编号查询询价产品

    //确定导入产品
    public  void importInquiryProductData(){

        PurchaseOrders purchaseOrders = purchaseOrdersService.selectByKey(inquiryOrderId);

        for(InquiryImportProperty inquiryImportProperty : importProperties){
            //选中导入的产品
            if(inquiryImportProperty.isChecked()){
                PurchaseProduct purchaseProduct = purchaseProductService.selectByKey(inquiryImportProperty.getId());

                PurchaseStockProductProperty purchaseStockProduct = new PurchaseStockProductProperty(0,"采购订单",purchaseOrders.getOrders(),0,purchaseOrders.getSeeorders(),purchaseProduct.getProorders(),purchaseProduct.getProname(),purchaseProduct.getStockover(),purchaseProduct.getUnit(),"","",purchaseProduct.getRemarks());

                purchaseStockProductProperties.add(purchaseStockProduct);
            }
        }
        coseInquiryWin();
    }



    public void coseInquiryWin(){
        stage.close();
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
        long id =  (long)stockorder.getUserData();
        PurchaseStock purchaseStock = purchaseStockService.selectByKey(id);
        purchaseStock.setShstatus(status);
        purchaseStockService.updateNotNull(purchaseStock);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String newStr = location.toString();

        int index = newStr.indexOf("purchase_stock.fxml");

        if(index != -1) {

              PurchaseOrders purchaseOrders = StageManager.purchaseOrders;


              if(purchaseOrders.getId() == null){
                  //正常单据加载
                  findInquiry(1);
                  loadEmployee(inspectnum,0);

              }else {
                  //采购订单转入采购入库单加载

                  //新增状态
                  insert();

                  suppliernum.setText(purchaseOrders.getSuppliernum());
                  suppliername.setText(purchaseOrders.getSuppliername());

                  depotnum.setText(purchaseOrders.getWarehouseid());
                  depotname.setText(purchaseOrders.getWarehousename());
                  //初始化表格数据
                  loadPurchaseStockProduct(0);

                  List<PurchaseProduct> purchaseProducts = StageManager.purchaseProducts;

                  if(purchaseProducts.size() > 0){


                      for (PurchaseProduct purchaseProduct:purchaseProducts) {

                          PurchaseStockProductProperty purchaseStockProductProperty = new PurchaseStockProductProperty(0,"采购订单",purchaseOrders.getOrders(),purchaseProduct.getSort(),"",purchaseProduct.getProorders(),purchaseProduct.getProname(),purchaseProduct.getNum(),purchaseProduct.getUnit(),purchaseOrders.getWarehouseid(),purchaseOrders.getWarehousename(),purchaseProduct.getRemarks());

                          purchaseStockProductProperties.add(purchaseStockProductProperty);

                      }


                  }
              }
        }


    }

}
