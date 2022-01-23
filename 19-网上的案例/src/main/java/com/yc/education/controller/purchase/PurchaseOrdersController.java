package com.yc.education.controller.purchase;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.DepotBasic;
import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.model.basic.SupplierBasic;
import com.yc.education.model.basic.SupplierContact;
import com.yc.education.model.purchase.*;
import com.yc.education.service.basic.DepotBasicService;
import com.yc.education.service.basic.EmployeeBasicService;
import com.yc.education.service.basic.SupplierBasicService;
import com.yc.education.service.basic.SupplierContactService;
import com.yc.education.service.purchase.InquiryOrderService;
import com.yc.education.service.purchase.InquiryProductService;
import com.yc.education.service.purchase.PurchaseOrdersService;
import com.yc.education.service.purchase.PurchaseProductService;
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
 * @ClassName PurchaseOrdersController
 * @Description TODO 采购订单
 * @Author QuZhangJing
 * @Date 2018/10/9 17:47
 * @Version 1.0
 */
@Controller
public class PurchaseOrdersController extends BaseController implements Initializable {

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;  //采购订单 Service

    @Autowired
    private SupplierContactService supplierContactService;//供应商联系人

    @Autowired
    private SupplierBasicService supplierBasicService; //供应商 Service

    @Autowired
    private DepotBasicService depotBasicService;//仓库库位

    @Autowired
    private PurchaseProductService purchaseProductService; //采购产品Service

    @Autowired
    private InquiryOrderService inquiryOrderService;//询价单Service

    @Autowired
    private InquiryProductService inquiryProductService;//询价产品 Service

    @Autowired
    private EmployeeBasicService employeeBasicService;//员工Service



    long changeId =0;

    @FXML private Label fxmlStatus; //状态

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

    @FXML private VBox importData;//导入




    @FXML private DatePicker createdate; //制单日期

    @FXML private TextField orders; //询价订单

    @FXML private TextField suppliernum; //供应商编号

    @FXML private TextField suppliername; //供应商简称

    @FXML private ComboBox warehouseid; //到货入库

    @FXML private TextField warehousename; //到货入库

    @FXML private TextField seeorders; //参考单号

    @FXML private ComboBox tax; //税别

    @FXML private ComboBox currency; //币别

   @FXML private DatePicker replydate; //回复日期

    /*  @FXML private DatePicker validdate; //有效期至*/

    @FXML private ComboBox purpeopletype; //采购负责类型

    @FXML private TextField purpeople; //采购负责人

    @FXML private TextField createpeople; //制单人

    @FXML private TextField shpeople; //审核人

    @FXML private TextField shdate; //审核日期

    @FXML private TextField updatepeople; //最后更新人

    @FXML private TextField updatedate; //最后更新日期


    //供应商基本资料


    @FXML private TextField supname;//供应商名称

    @FXML private ComboBox supcontacts; //供应商联系人

    @FXML private ComboBox supohone; //供应商 联系电话

    @FXML private ComboBox suptax; //供应商 传真

    @FXML private ComboBox supaddress; //供应商 地址


    //查询更多供应商
    @FXML private TableView tableView3; //供应商查询
    @FXML private TableColumn supid; //id
    @FXML private TableColumn findsupplierid; //供应商编号
    @FXML private TableColumn findsuppliername; //供应商简称


    //查询更多采购订单
    @FXML private TableView tableView4; //采购订单
    @FXML private TableColumn purchaseid; //id
    @FXML private TableColumn findpurchaseorder; //采购订单号
    @FXML private TableColumn findpurchasedata; //制单日期
    @FXML private TableColumn findpurchasenum;//供应商编号
    @FXML private TableColumn findpurchasedes;//供应商简称
    @FXML private TableColumn findpurchasepeo;//业务负责
    @FXML private TableColumn findpurchasecontact;//联系人
    @FXML private TableColumn findphone; //电话
    @FXML private TableColumn findfax;//传真


    //查询采购产品
    @FXML private TableView tableView1; //采购产品
    @FXML private TableColumn proid; //来源单据
    @FXML private TableColumn sourseorder; //来源单据
    @FXML private TableColumn inquiryorders; //单据编号
    @FXML private TableColumn sort; //序号
    @FXML private TableColumn proorders; //产品编号
    @FXML private TableColumn proname; //产品名称
    @FXML private TableColumn prosupname; //供应商名称
    @FXML private TableColumn pronum; //数量
    @FXML private TableColumn prounit; //单位
    @FXML private TableColumn proprice; //单价
    @FXML private TableColumn totalprice; //金额
    @FXML private TableColumn remarks; //备注


    //导入--询价单据列表
    @FXML private TableView inquiryView;
    @FXML private TableColumn inquiryid; //单据ID
    @FXML private TableColumn findinquiryorder; //单据编号
    @FXML private TableColumn findinquirydata; //制单日期
    @FXML private TableColumn findsuppliernum; //供应商编号
    @FXML private TableColumn findstatus; //单据状态
    @FXML private TableColumn findcomestock; //到货仓库

    private long inquiryOrderId =0;

    //导入--询价单据产品
    @FXML private TableView inquiryProductView;
    @FXML private TableColumn inquiryproid; //询价单产品ID
    @FXML private TableColumn findproid; //产品编号
    @FXML private TableColumn finprosort; //产品序号
    @FXML private TableColumn findproname; //产品名称
    @FXML private TableColumn finpronum; //数量
    @FXML private TableColumn findproprice; //单价




    @FXML private Button supplierBtn;


    //采购产品——之数据绑定TabelView
    private ObservableList<PurchaseProductProperty> purchaseProductProperties = FXCollections.observableArrayList();
    //导入--询价单  询价产品导入数据TabelView
    private ObservableList<InquiryImportProperty> inquiryImportProperties = FXCollections.observableArrayList();


    private Stage stage = new Stage();

    private static SpringFxmlLoader loader = new SpringFxmlLoader();



    /**
     * 生成询价订单流水号 (A1809280001)
     * 格式:英文字母(A)+日期(180928)+4位流水号(0001)
     * @return
     */
    public String createIsnum(String currentDate){
        String newDateStr=currentDate.substring(2,4)+currentDate.substring(currentDate.indexOf("-")+1,currentDate.indexOf("-")+3)+currentDate.substring(currentDate.lastIndexOf("-")+1,currentDate.lastIndexOf("-")+3);
        String maxIsnum = purchaseOrdersService.selectMaxIdnum(currentDate);
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



    //点击弹出 现有询价单查询
    public void moreInquiryClick(){

        stage.setTitle("现有采购订单查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/purchase_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();

        loadMoreInquiry();
    }


    public void loadMoreInquiry(){

        List<PurchaseOrders> purchaseOrders = purchaseOrdersService.findPurchaseOrders();

        ObservableList<PurchaseOrders> list = FXCollections.observableArrayList();


        /*tableView4.setEditable(true);*/


        purchaseid.setCellValueFactory(new PropertyValueFactory("id"));
        findpurchaseorder.setCellValueFactory(new PropertyValueFactory("orders"));
        findpurchasedata.setCellValueFactory(new PropertyValueFactory("paremdate"));
        findpurchasenum.setCellValueFactory(new PropertyValueFactory("suppliernum"));
        findpurchasedes.setCellValueFactory(new PropertyValueFactory("suppliername"));
        findpurchasepeo.setCellValueFactory(new PropertyValueFactory("purpeople"));
        findpurchasecontact.setCellValueFactory(new PropertyValueFactory("supplierconcat"));
        findphone.setCellValueFactory(new PropertyValueFactory("supplierphone"));
        findfax.setCellValueFactory(new PropertyValueFactory("supplierfax"));


        for (PurchaseOrders purchaseOrders1:purchaseOrders) {

            /*purchaseOrders1.setParemdate(new SimpleDateFormat("yyyy-MM-dd").format(supplierBasics1.getCreatedate()));*/

            list.add(purchaseOrders1);

        }

        tableView4.setItems(list); //tableview添加list

        tableView4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseOrders>() {
            @Override
            public void changed(ObservableValue<? extends PurchaseOrders> observableValue, PurchaseOrders oldItem, PurchaseOrders newItem) {
                if(newItem.getOrders() != null && !("".equals(newItem.getOrders()))){
                    findpurchaseorder.setUserData(newItem.getId());
                }
            }
        });


        tableView4.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeInquiryWin();
            }
        });


    }

    public void closeInquiryWin(){
        long id =(long)findpurchaseorder.getUserData();
        PurchaseOrders purchaseOrders =  purchaseOrdersService.selectByKey(id);
        orders.setText(purchaseOrders.getOrders());
        loadDate(purchaseOrders);
        loadSupplier(purchaseOrders.getSuppliernum(),purchaseOrders);
        /*loadDate(supplierBasic);*/
        coseInquiryWin();
    }

    public void coseInquiryWin(){
        stage.close();
    }

    /**
     * 点击弹出 现有供应商查询
     */
    public void moreSupplierClick(){

        stage.setTitle("现有供应商查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/purchase_supplier_find.fxml"));
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
        long orderId =(long)orders.getUserData();
        SupplierBasic supplierBasic =  supplierBasicService.selectByKey(id);
        PurchaseOrders purchaseOrders =  purchaseOrdersService.selectByKey(orderId);
        suppliernum.setText(supplierBasic.getIdnum());
        suppliername.setText(supplierBasic.getSupdes());
        loadSupplier(supplierBasic.getIdnum(),purchaseOrders);
        /*loadDate(supplierBasic);*/
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
        findInquiry(pageNum);
        NumberUtil.changeStatus(fxmlStatus,0);//修改为修改状态
    }


    /**
     * 分页查询供应商查询供应商
     */
    public void findInquiry(int pageNum){

        List<PurchaseOrders> purchaseOrders = purchaseOrdersService.findPurchaseOrders(pageNum,1);

        PageInfo<PurchaseOrders> pageInfo = new PageInfo<>(purchaseOrders);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页

        purchaseOrders.forEach(inquiryOrder ->loadDate(inquiryOrder));


        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


        if(purchaseOrders.size()<=0){
            orders.setUserData(0L);
            inquiryProduct();
        }


    }


    /**
     * 向控件加载数据
     * @param purchaseOrders
     */
    public void loadDate(PurchaseOrders purchaseOrders){

        loadSupplier(purchaseOrders.getSuppliernum(),purchaseOrders); //加载供应商信息

        createdate.setValue(LocalDateTime.ofInstant(purchaseOrders.getCreatedate().toInstant(), ZoneId.systemDefault()).toLocalDate()); //制单日期

        orders.setUserData(purchaseOrders.getId());

        orders.setText(purchaseOrders.getOrders());//询价订单

        suppliernum.setText(purchaseOrders.getSuppliernum()); //供应商编号

        suppliername.setText(purchaseOrders.getSuppliername());//供应商名称

         List<DepotBasic> depotBasicList = depotBasicService.selectDepotBasic();



         for(int i=0,len=depotBasicList.size();i<len;i++){

             warehouseid.getItems().add(depotBasicList.get(i).getIsnum());
             if(depotBasicList.get(i).getIsnum().equals(purchaseOrders.getWarehouseid())){
                 warehouseid.getSelectionModel().select(i);
                 warehousename.setText(purchaseOrders.getWarehousename());
             }
         }

        tax.getItems().clear();
        int taxs = purchaseOrders.getPtax();
        tax.getItems().addAll("外加","内含","零税","免税");
        tax.getSelectionModel().select(--taxs); //税别


        int currentcyInt = purchaseOrders.getPcurrency();
        currency.getItems().clear();
        setComboBox(7L,currency,--currentcyInt); //币别

        replydate.setValue(LocalDateTime.ofInstant(purchaseOrders.getComedate().toInstant(), ZoneId.systemDefault()).toLocalDate()); //回复日期*/

        /*validdate.setValue(LocalDateTime.ofInstant(purchaseOrders.getValiddate().toInstant(), ZoneId.systemDefault()).toLocalDate()); //有效期至*/
        int purpeopletypes = purchaseOrders.getPurpeopletype();

        purpeopletype.getSelectionModel().select(--purpeopletypes); //采购负责类型


        purpeople.setText(purchaseOrders.getPurpeople()); //采购负责人

        createpeople.setText(purchaseOrders.getCreatepeople());//制单人

        shpeople.setText(purchaseOrders.getShpeople());//审核人


        shdate.setText(purchaseOrders.getShdate()); //审核日期


        updatepeople.setText(purchaseOrders.getUpdatepeople()); //最后更新人

        updatedate.setText(purchaseOrders.getUpdatedate()); //最后更新日期


        int isSh = purchaseOrders.getShstatus();

        if(isSh == 0){
            shyes.setDisable(false);
            shno.setDisable(true);
        }else{
            shyes.setDisable(true);
            shno.setDisable(false);
        }

        inquiryProduct();


    }

    /**
     * 根据供应商编号加载供应商
     * @param isNum
     */
    public void loadSupplier(String isNum,PurchaseOrders purchaseOrders){

        //供应商
        SupplierBasic supplierBasic =  supplierBasicService.selectSupplierBasicByIsnum(isNum);

        if(supplierBasic !=  null){

            supname.setText(supplierBasic.getSupname());

            //查询供应商联系人
            List<SupplierContact> supplierContacts = supplierContactService.selectSupplierContactBySupplierid(supplierBasic.getId());

            supcontacts.getItems().clear();
            supohone.getItems().clear();
            suptax.getItems().clear();
            supaddress.getItems().clear();

            for(int i=0,len=supplierContacts.size();i<len;i++){
                supcontacts.getItems().add(supplierContacts.get(i).getUname());
                supohone.getItems().add(supplierContacts.get(i).getUphone());
                suptax.getItems().add(supplierContacts.get(i).getUfax());
                if(purchaseOrders != null){
                    if(supplierContacts.get(i).getUname().equals(purchaseOrders.getSupplierconcat())){
                        supcontacts.getSelectionModel().select(i);
                    }
                if(supplierContacts.get(i).getUphone().equals(purchaseOrders.getSupplierphone())){
                    supohone.getSelectionModel().select(i);
                }
                if(supplierContacts.get(i).getUfax().equals(purchaseOrders.getSupplierfax())){
                    suptax.getSelectionModel().select(i);
                }
                }else{
                    supcontacts.getSelectionModel().select(0);
                    supohone.getSelectionModel().select(0);
                    suptax.getSelectionModel().select(0);
                }
            }
            supaddress.getItems().add(supplierBasic.getRegadd());
            supaddress.getSelectionModel().select(0);
        }

    }




    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){

        orders.setEditable(false);//询价订单

        suppliernum.setDisable(!status); //供应商编号

        suppliername.setDisable(!status); //供应商名称

        warehouseid.setDisable(!status);

        warehousename.setDisable(!status);

        seeorders.setDisable(!status);

        tax.setDisable(!status); //税别

        currency.setDisable(!status); //币别

        purpeopletype.setDisable(!status); //采购负责类型

        purpeople.setDisable(!status); //采购负责人

        createpeople.setDisable(!status); //制单人

        shpeople.setDisable(!status); //审核人

        shdate.setDisable(!status); //审核日期

        updatepeople.setDisable(!status); //最后更新人

        updatedate.setDisable(!status); //最后更新日期

        supname.setDisable(!status);

        supcontacts.setDisable(!status);
        supohone.setDisable(!status);
        suptax.setDisable(!status);
        supaddress.setDisable(!status);



        tableView1.setEditable(status);

      /*  tableViewDes.setEditable(status);
        tableViewRem.setEditable(status);*/


        createdate.setDisable(!status);
        replydate.setDisable(!status);
        /*validdate.setDisable(!status);*/


        supplierBtn.setDisable(!status); //查询更多供应商窗体

        importData.setDisable(!status);

    }



    /**
     * 清空
     */
    public void clearValue(){

        orders.setText(NumberUtil.NULLSTRING);//询价订单

        suppliernum.setText(NumberUtil.NULLSTRING); //供应商编号

        suppliername.setText(NumberUtil.NULLSTRING); //供应商名称

        tax.getSelectionModel().select(0); //税别

        currency.getSelectionModel().select(0); //币别

        purpeopletype.getSelectionModel().select(0); //采购负责类型

        purpeople.setText(NumberUtil.NULLSTRING); //采购负责人

        createpeople.setText(NumberUtil.NULLSTRING); //制单人

        shpeople.setText(NumberUtil.NULLSTRING); //审核人

        shdate.setText(NumberUtil.NULLSTRING); //审核日期

        updatepeople.setText(NumberUtil.NULLSTRING); //最后更新人

        updatedate.setText(NumberUtil.NULLSTRING); //最后更新日期

    }

    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) orders.getUserData();
            int rows = purchaseOrdersService.delete(id);
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
       /* blankContact();
        blankPurchaser();*/

        blankInquiryProduct();
//        blankInquiryDescription();
//        blankInquiryRemarks();


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
        createPeople(createpeople);//制单人
        purchaseProductProperties.clear();
        blankInquiryProduct();
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

        PurchaseOrders purchaseOrders = new PurchaseOrders(new Date(java.sql.Date.valueOf(orderDate).getTime()),
                orders.getText(),
                suppliernum.getText(),
                suppliername.getText(),
                warehouseid.getItems().size()==0?"":warehouseid.getSelectionModel().getSelectedItem().toString(),
                warehousename.getText(),
                seeorders.getText(),
                new Date(java.sql.Date.valueOf(replydate.getValue()).getTime()),
                tax.getSelectionModel().getSelectedIndex()+1 ,
                currency.getSelectionModel().getSelectedIndex()+1,
                purpeopletype.getSelectionModel().getSelectedIndex()+1,
                purpeople.getText(),
                createpeople.getText(),
                shpeople.getText(),
                shdate.getText(),
                updatepeople.getText(),
                updatedate.getText(),
                supname.getText(),
                supcontacts.getItems().size()==0?"":supcontacts.getSelectionModel().getSelectedItem().toString(),
                supohone.getItems().size()==0?"":supohone.getSelectionModel().getSelectedItem().toString(),
                suptax.getItems().size()==0?"":suptax.getSelectionModel().getSelectedItem().toString(),
                supaddress.getItems().size()==0?"":supaddress.getSelectionModel().getSelectedItem().toString(),
                0,
                0);


        int rows =0;
        if(submitStuts==1){
            //产生订单编号
            String orderNum = createIsnum(orderDate.toString());
            orders.setText(orderNum);
            purchaseOrders.setOrders(orderNum);
            rows = purchaseOrdersService.save(purchaseOrders);
        }else if(submitStuts ==2){
            purchaseOrders.setId((long)orders.getUserData());
            rows = purchaseOrdersService.updateNotNull(purchaseOrders);
        }

                            /*saveContact(supplierBasic.getId());//联系人
                            savePurchaser(supplierBasic.getId()); //采购负责人*/
        saveInquiryProduct(purchaseOrders.getId());
//        saveInquiryDescription(purchaseOrders.getId());
//        saveInquiryRemarks(purchaseOrders.getId());
        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);

        this.loadDate(purchaseOrders); //重新加载数据

        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


    }


    //采购产品
    public void inquiryProduct(){

        List<PurchaseProduct> purchaseProducts =  purchaseProductService.findPurchaseProduct((long)orders.getUserData());

        sourseorder.setCellFactory(TextFieldTableCell.forTableColumn());
        inquiryorders.setCellFactory(TextFieldTableCell.forTableColumn());
        sort.setCellFactory(TextFieldTableCell.forTableColumn());
        proorders.setCellFactory(TextFieldTableCell.forTableColumn());
        proname.setCellFactory(TextFieldTableCell.forTableColumn());
        prosupname.setCellFactory(TextFieldTableCell.forTableColumn());
        pronum.setCellFactory(TextFieldTableCell.forTableColumn());
        prounit.setCellFactory(TextFieldTableCell.forTableColumn());
        proprice.setCellFactory(TextFieldTableCell.forTableColumn());
        totalprice.setCellFactory(TextFieldTableCell.forTableColumn());
        remarks.setCellFactory(TextFieldTableCell.forTableColumn());


        /*proid.setCellValueFactory(new PropertyValueFactory("id"));*/
        sourseorder.setCellValueFactory(new PropertyValueFactory("sourseorder"));
        inquiryorders.setCellValueFactory(new PropertyValueFactory("inquiryorders"));
        sort.setCellValueFactory(new PropertyValueFactory("sort"));
        proorders.setCellValueFactory(new PropertyValueFactory("proorders"));
        proname.setCellValueFactory(new PropertyValueFactory("proname"));
        prosupname.setCellValueFactory(new PropertyValueFactory("prosupname"));
        pronum.setCellValueFactory(new PropertyValueFactory("pronum"));
        prounit.setCellValueFactory(new PropertyValueFactory("prounit"));
        proprice.setCellValueFactory(new PropertyValueFactory("proprice"));
        totalprice.setCellValueFactory(new PropertyValueFactory("totalprice"));
        remarks.setCellValueFactory(new PropertyValueFactory("remarks"));


        purchaseProductProperties.clear();

      /*  totalnum.setText("0");
        totalmoney.setText("0.00");*/
        if(purchaseProducts.size()>0){
            for (PurchaseProduct purchaseProduct:purchaseProducts) {

                /*totalCost(purchaseProduct.getNum(),purchaseProduct.getTotalprice());*/

                PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty(purchaseProduct.getId(),purchaseProduct.getSourseorder(),purchaseProduct.getOrders(),purchaseProduct.getSort().toString(),purchaseProduct.getProorders(),purchaseProduct.getProname(),purchaseProduct.getSupname(),purchaseProduct.getNum().toString(),purchaseProduct.getUnit(),purchaseProduct.getPrice().toString(),purchaseProduct.getTotalprice().toString(),purchaseProduct.getRemarks());

                purchaseProductProperties.add(purchaseProductProperty);
            }
        }
        tableView1.setItems(purchaseProductProperties); //tableview添加list w



        tableView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseProductProperty>() {
            @Override
            public void changed(ObservableValue<? extends PurchaseProductProperty> observableValue, PurchaseProductProperty oldItem, PurchaseProductProperty newItem) {
                changeId=newItem.getId();
            }
        });

    }
    //
    public void saveInquiryProduct(long purchase){
        for (PurchaseProductProperty purchaseProductProperty :purchaseProductProperties) {
            if(purchaseProductProperty.getSourseorder()!=null){
                if(purchaseProductProperty.getId()>0){
                    //修改采购负责人
                    PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseProductProperty.getId(),purchaseProductProperty.getSourseorder(),purchaseProductProperty.getInquiryorders(),Integer.parseInt(purchaseProductProperty.getSort()),purchaseProductProperty.getProorders(),purchaseProductProperty.getProname(),purchaseProductProperty.getProsupname(),Integer.parseInt(purchaseProductProperty.getPronum()),purchaseProductProperty.getProunit(),Double.valueOf(purchaseProductProperty.getProprice()),Double.valueOf(purchaseProductProperty.getTotalprice()),purchaseProductProperty.getRemarks(),purchase);
                    purchaseProductService.updateNotNull(purchaseProduct);
                }else{
                    //新增采购负责人
                    PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseProductProperty.getSourseorder(),purchaseProductProperty.getInquiryorders(),Integer.parseInt(purchaseProductProperty.getSort()),purchaseProductProperty.getProorders(),purchaseProductProperty.getProname(),purchaseProductProperty.getProsupname(),Integer.parseInt(purchaseProductProperty.getPronum()),purchaseProductProperty.getProunit(),Double.valueOf(purchaseProductProperty.getProprice()),Double.valueOf(purchaseProductProperty.getTotalprice()),purchaseProductProperty.getRemarks(),purchase);
                    purchaseProduct.setOntheway(0);
                    purchaseProduct.setForcenum(0);
                    purchaseProduct.setStockover(0);
                    purchaseProductService.save(purchaseProduct);
                }
            }
        }
    }


    //询价单产品空白
    public void blankInquiryProduct(){
        PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty();
        purchaseProductProperties.add(purchaseProductProperty);
    }







    //采购产品键盘按下操作
    public void tableView1Key(KeyEvent event){

        if (event.getCode() == KeyCode.DELETE) {


            purchaseProductService.delete(changeId);
            ObservableList<PurchaseProductProperty> purchaseProductPropertiesNew = FXCollections.observableArrayList();

            for (PurchaseProductProperty purchaseProductProperty : purchaseProductProperties){
                if(purchaseProductProperty.getId() != changeId){
                    purchaseProductPropertiesNew .add(purchaseProductProperty);
                }
            }
            purchaseProductProperties.clear();
            purchaseProductProperties.setAll(purchaseProductPropertiesNew);

        }


        if (event.getCode() == KeyCode.INSERT) {
            blankInquiryProduct();
        }

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
        long id =  (long)orders.getUserData();
        PurchaseOrders purchaseOrders = purchaseOrdersService.selectByKey(id);
        purchaseOrders.setShstatus(status);
        purchaseOrdersService.updateNotNull(purchaseOrders);
    }



    //导入---询价单
    public void importButtonInquiry(){

        stage.setTitle("导入-询价单");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/inquiry_import.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
        loadMoreInquiryImport();

    }




    public void loadMoreInquiryImport(){

        List<InquiryOrder> inquiryOrder = inquiryOrderService.findInquiryOrder();

        ObservableList<InquiryOrder> list = FXCollections.observableArrayList();


        inquiryid.setCellValueFactory(new PropertyValueFactory("id"));
        findinquiryorder.setCellValueFactory(new PropertyValueFactory("orders"));
        findinquirydata.setCellValueFactory(new PropertyValueFactory("paremdate"));
        findsuppliernum.setCellValueFactory(new PropertyValueFactory("suppliernum"));
        findstatus.setCellValueFactory(new PropertyValueFactory("strstatus"));
        /*findcomestock.setCellValueFactory(new PropertyValueFactory("purpeople"));*/


        for (InquiryOrder inquiryOrder1:inquiryOrder) {

            inquiryOrder1.setParemdate(new SimpleDateFormat("yyyy-MM-dd").format(inquiryOrder1.getCreatedate()));

            if(inquiryOrder1.getShstatus()==0){
                inquiryOrder1.setStrstatus("未审核");
            }else{

                inquiryOrder1.setStrstatus("已审核");
            }

            list.add(inquiryOrder1);

        }

        inquiryView.setItems(list); //tableview添加list

        inquiryView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InquiryOrder>() {
            @Override
            public void changed(ObservableValue<? extends InquiryOrder> observableValue, InquiryOrder oldItem, InquiryOrder newItem) {
                if(newItem != null && newItem.getId()>0){
                    loadMoreInquiryProductImport(newItem.getId());
                }
            }
        });
    }


public void loadMoreInquiryProductImport(long id){
        inquiryOrderId=id;
         List<InquiryProduct> inquiryProducts = inquiryProductService.findInquiryProductByInquiryid(id);
        inquiryProductView.setEditable(true);
         inquiryproid.setCellFactory(CheckBoxTableCell.forTableColumn(inquiryproid));

        inquiryproid.setCellValueFactory(new PropertyValueFactory("checked"));
        findproid.setCellValueFactory(new PropertyValueFactory("proisnum"));
        finprosort.setCellValueFactory(new PropertyValueFactory("sort"));
        findproname.setCellValueFactory(new PropertyValueFactory("proname"));
        finpronum.setCellValueFactory(new PropertyValueFactory("pronum"));
        findproprice.setCellValueFactory(new PropertyValueFactory("proprice"));

        inquiryImportProperties.clear();

        for (InquiryProduct inquiryProduct:inquiryProducts) {

            InquiryImportProperty inquiryImportProperty = new InquiryImportProperty(false,inquiryProduct.getId(),inquiryProduct.getProisnum(),inquiryProduct.getSort(),inquiryProduct.getProname(),inquiryProduct.getPronum(),inquiryProduct.getProprice());

            inquiryImportProperties.add(inquiryImportProperty);

        }

    inquiryProductView.setItems(inquiryImportProperties); //tableview添加list

}




    //导入----根据询价单据编号查询询价产品

    //确定导入产品
    public  void importInquiryProductData(){

            InquiryOrder inquiryOrder = inquiryOrderService.selectByKey(inquiryOrderId);


            for(InquiryImportProperty inquiryImportProperty : inquiryImportProperties){

                if(inquiryOrder.getShstatus()==0){
                    alert_informationDialog("未审核订单产品导入失败!");
                }else{
                    //选中导入的产品
                    if(inquiryImportProperty.isChecked()){
                        InquiryProduct inquiryProduct = inquiryProductService.selectByKey(inquiryImportProperty.getId());
                        PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty(0,"询价单",inquiryOrder.getOrders(),"0",inquiryProduct.getProisnum(),inquiryProduct.getProname(),inquiryOrder.getSuppliername(),inquiryProduct.getPronum().toString(),inquiryProduct.getProunit(),inquiryProduct.getProprice().toString(),inquiryProduct.getTotalprice().toString(),inquiryProduct.getProremark());
                        purchaseProductProperties.add(purchaseProductProperty);
                    }
                }
            }

            inquiryImportProperties.clear();

        coseInquiryWin();
    }





    //导出采购入库单
    public void importPurchaseStock(){

        PurchaseOrders purchaseOrders =  purchaseOrdersService.selectByKey((long)orders.getUserData());

        if(purchaseOrders.getShstatus() == 1){
            StageManager.purchaseOrders =purchaseOrders;

            StageManager.purchaseProducts = purchaseProductService.findPurchaseProduct((long)orders.getUserData());

            Pane pane1 = StageManager.ORDERCONTENT.get("PurchasePane");

            pane1.getChildren().setAll(loader.load("/fxml/stock/purchase_stock.fxml"));
        }else{
            alert_informationDialog("该单据未审核无法进行导出!");
        }


    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //选中入库仓库
        warehouseid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                DepotBasic depotBasic =  depotBasicService.selectDepotBasicByIsnum(newValue.toString());
                warehousename.setText(depotBasic.getDepname());
            }
        });



        /*setComboBox(7L,tax,0);*/

        String newStr = location.toString();

        int index = newStr.indexOf("purchase_order.fxml");

        if(index != -1){

            insert();

            InquiryOrder inquiryOrder =  StageManager.inquiryOrderInfo;



            List<EmployeeBasic> employeeBasics = employeeBasicService.selectEmployeeBasic();

            for(int i=0,len=employeeBasics.size();i<len;i++){
                purpeopletype.getItems().add(employeeBasics.get(i).getIdnum());
            }
            purpeopletype.getSelectionModel().select(0);



            purpeopletype.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                    if(!"".equals(newValue) && newValue != null ){


                        EmployeeBasic employeeBasic =  employeeBasicService.selectEmployeeBasicByIsnum(newValue.toString());


                        purpeople.setText(employeeBasic.getEmpname());


                    }

                }
            });


            if(inquiryOrder.getId() == null){

                findInquiry(1);

                setComboBox(7L,currency,0); //币别
                tax.getItems().addAll("外加","内含","零税","免税");
                tax.getSelectionModel().select(0); //税别




            }else{

            suppliernum.setText(inquiryOrder.getSuppliernum()); //供应商编号
            suppliername.setText(inquiryOrder.getSuppliername());//供应商名称

            PurchaseOrders purchaseOrders = new PurchaseOrders();
                purchaseOrders.setSupplieraddress(inquiryOrder.getSupplieraddress());
                purchaseOrders.setSupplierfax(inquiryOrder.getSupplierfax());
                purchaseOrders.setSupplierphone(inquiryOrder.getSupplierphone());
                purchaseOrders.setSupplierconcat(inquiryOrder.getSupplierconcat());
                loadSupplier(inquiryOrder.getSuppliernum(),purchaseOrders);

                int taxFinal = inquiryOrder.getTaxs();//税别
                int currencysFinal = inquiryOrder.getCurrencys();//币别
                tax.getItems().addAll("外加","内含","零税","免税");
                tax.getSelectionModel().select(--taxFinal); //税别
                setComboBox(7L,currency,--currencysFinal); //币别

                orders.setUserData(0L);

                inquiryProduct();

                List<InquiryProduct> inquiryProducts = StageManager.inquiryProductsInfo;

                for(InquiryProduct inquiryProduct : inquiryProducts){

                    PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty(0,"询价单",inquiryOrder.getOrders(),"0",inquiryProduct.getProisnum(),inquiryProduct.getProname(),inquiryOrder.getSuppliername(),inquiryProduct.getPronum().toString(),inquiryProduct.getProunit(),inquiryProduct.getProprice().toString(),inquiryProduct.getTotalprice().toString(),inquiryProduct.getProremark());
                    purchaseProductProperties.add(purchaseProductProperty);
                }
                tableView1.setEditable(true);
            }

            List<DepotBasic> depotBasicList = depotBasicService.selectDepotBasic();
            for(int i=0,len=depotBasicList.size();i<len;i++){
                warehouseid.getItems().add(depotBasicList.get(i).getIsnum());
                warehouseid.getSelectionModel().select(0);
            }


        }

    }



}
