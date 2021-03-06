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
 * @Description TODO ????????????
 * @Author QuZhangJing
 * @Date 2018/10/9 17:47
 * @Version 1.0
 */
@Controller
public class PurchaseOrdersController extends BaseController implements Initializable {

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;  //???????????? Service

    @Autowired
    private SupplierContactService supplierContactService;//??????????????????

    @Autowired
    private SupplierBasicService supplierBasicService; //????????? Service

    @Autowired
    private DepotBasicService depotBasicService;//????????????

    @Autowired
    private PurchaseProductService purchaseProductService; //????????????Service

    @Autowired
    private InquiryOrderService inquiryOrderService;//?????????Service

    @Autowired
    private InquiryProductService inquiryProductService;//???????????? Service

    @Autowired
    private EmployeeBasicService employeeBasicService;//??????Service



    long changeId =0;

    @FXML private Label fxmlStatus; //??????

    @FXML private VBox first; //?????????

    @FXML private VBox prev; //?????????

    @FXML private VBox next; //?????????

    @FXML private VBox last; //????????????


    @FXML private VBox clearvbox; //??????

    @FXML private VBox submitvbox; //??????

    @FXML private VBox insertvbox; //??????

    @FXML private VBox updatevbox; //??????

    @FXML private VBox deletevbox; //??????

    @FXML private VBox shyes; //??????

    @FXML private VBox shno; //????????????

    @FXML private VBox importData;//??????




    @FXML private DatePicker createdate; //????????????

    @FXML private TextField orders; //????????????

    @FXML private TextField suppliernum; //???????????????

    @FXML private TextField suppliername; //???????????????

    @FXML private ComboBox warehouseid; //????????????

    @FXML private TextField warehousename; //????????????

    @FXML private TextField seeorders; //????????????

    @FXML private ComboBox tax; //??????

    @FXML private ComboBox currency; //??????

   @FXML private DatePicker replydate; //????????????

    /*  @FXML private DatePicker validdate; //????????????*/

    @FXML private ComboBox purpeopletype; //??????????????????

    @FXML private TextField purpeople; //???????????????

    @FXML private TextField createpeople; //?????????

    @FXML private TextField shpeople; //?????????

    @FXML private TextField shdate; //????????????

    @FXML private TextField updatepeople; //???????????????

    @FXML private TextField updatedate; //??????????????????


    //?????????????????????


    @FXML private TextField supname;//???????????????

    @FXML private ComboBox supcontacts; //??????????????????

    @FXML private ComboBox supohone; //????????? ????????????

    @FXML private ComboBox suptax; //????????? ??????

    @FXML private ComboBox supaddress; //????????? ??????


    //?????????????????????
    @FXML private TableView tableView3; //???????????????
    @FXML private TableColumn supid; //id
    @FXML private TableColumn findsupplierid; //???????????????
    @FXML private TableColumn findsuppliername; //???????????????


    //????????????????????????
    @FXML private TableView tableView4; //????????????
    @FXML private TableColumn purchaseid; //id
    @FXML private TableColumn findpurchaseorder; //???????????????
    @FXML private TableColumn findpurchasedata; //????????????
    @FXML private TableColumn findpurchasenum;//???????????????
    @FXML private TableColumn findpurchasedes;//???????????????
    @FXML private TableColumn findpurchasepeo;//????????????
    @FXML private TableColumn findpurchasecontact;//?????????
    @FXML private TableColumn findphone; //??????
    @FXML private TableColumn findfax;//??????


    //??????????????????
    @FXML private TableView tableView1; //????????????
    @FXML private TableColumn proid; //????????????
    @FXML private TableColumn sourseorder; //????????????
    @FXML private TableColumn inquiryorders; //????????????
    @FXML private TableColumn sort; //??????
    @FXML private TableColumn proorders; //????????????
    @FXML private TableColumn proname; //????????????
    @FXML private TableColumn prosupname; //???????????????
    @FXML private TableColumn pronum; //??????
    @FXML private TableColumn prounit; //??????
    @FXML private TableColumn proprice; //??????
    @FXML private TableColumn totalprice; //??????
    @FXML private TableColumn remarks; //??????


    //??????--??????????????????
    @FXML private TableView inquiryView;
    @FXML private TableColumn inquiryid; //??????ID
    @FXML private TableColumn findinquiryorder; //????????????
    @FXML private TableColumn findinquirydata; //????????????
    @FXML private TableColumn findsuppliernum; //???????????????
    @FXML private TableColumn findstatus; //????????????
    @FXML private TableColumn findcomestock; //????????????

    private long inquiryOrderId =0;

    //??????--??????????????????
    @FXML private TableView inquiryProductView;
    @FXML private TableColumn inquiryproid; //???????????????ID
    @FXML private TableColumn findproid; //????????????
    @FXML private TableColumn finprosort; //????????????
    @FXML private TableColumn findproname; //????????????
    @FXML private TableColumn finpronum; //??????
    @FXML private TableColumn findproprice; //??????




    @FXML private Button supplierBtn;


    //?????????????????????????????????TabelView
    private ObservableList<PurchaseProductProperty> purchaseProductProperties = FXCollections.observableArrayList();
    //??????--?????????  ????????????????????????TabelView
    private ObservableList<InquiryImportProperty> inquiryImportProperties = FXCollections.observableArrayList();


    private Stage stage = new Stage();

    private static SpringFxmlLoader loader = new SpringFxmlLoader();



    /**
     * ??????????????????????????? (A1809280001)
     * ??????:????????????(A)+??????(180928)+4????????????(0001)
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



    //???????????? ?????????????????????
    public void moreInquiryClick(){

        stage.setTitle("????????????????????????");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/purchase_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //????????????*/
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

        tableView4.setItems(list); //tableview??????list

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
     * ???????????? ?????????????????????
     */
    public void moreSupplierClick(){

        stage.setTitle("?????????????????????");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/purchase_supplier_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //????????????*/
        stage.show();

        loadMoreSupplier();
    }


    /**
     * ?????????????????????
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

        tableView3.setItems(list); //tableview??????list

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
     * ??????????????????
     * @param event
     */
    public void pages(MouseEvent event){
        Node node =(Node)event.getSource();
        int pageNum =Integer.parseInt(String.valueOf(node.getUserData()));
        findInquiry(pageNum);
        NumberUtil.changeStatus(fxmlStatus,0);//?????????????????????
    }


    /**
     * ????????????????????????????????????
     */
    public void findInquiry(int pageNum){

        List<PurchaseOrders> purchaseOrders = purchaseOrdersService.findPurchaseOrders(pageNum,1);

        PageInfo<PurchaseOrders> pageInfo = new PageInfo<>(purchaseOrders);

        first.setUserData(1); //??????

        prev.setUserData(pageInfo.getPrePage()); //?????????

        next.setUserData(pageInfo.getNextPage());//?????????

        last.setUserData(pageInfo.getPages());//??????

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
     * ?????????????????????
     * @param purchaseOrders
     */
    public void loadDate(PurchaseOrders purchaseOrders){

        loadSupplier(purchaseOrders.getSuppliernum(),purchaseOrders); //?????????????????????

        createdate.setValue(LocalDateTime.ofInstant(purchaseOrders.getCreatedate().toInstant(), ZoneId.systemDefault()).toLocalDate()); //????????????

        orders.setUserData(purchaseOrders.getId());

        orders.setText(purchaseOrders.getOrders());//????????????

        suppliernum.setText(purchaseOrders.getSuppliernum()); //???????????????

        suppliername.setText(purchaseOrders.getSuppliername());//???????????????

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
        tax.getItems().addAll("??????","??????","??????","??????");
        tax.getSelectionModel().select(--taxs); //??????


        int currentcyInt = purchaseOrders.getPcurrency();
        currency.getItems().clear();
        setComboBox(7L,currency,--currentcyInt); //??????

        replydate.setValue(LocalDateTime.ofInstant(purchaseOrders.getComedate().toInstant(), ZoneId.systemDefault()).toLocalDate()); //????????????*/

        /*validdate.setValue(LocalDateTime.ofInstant(purchaseOrders.getValiddate().toInstant(), ZoneId.systemDefault()).toLocalDate()); //????????????*/
        int purpeopletypes = purchaseOrders.getPurpeopletype();

        purpeopletype.getSelectionModel().select(--purpeopletypes); //??????????????????


        purpeople.setText(purchaseOrders.getPurpeople()); //???????????????

        createpeople.setText(purchaseOrders.getCreatepeople());//?????????

        shpeople.setText(purchaseOrders.getShpeople());//?????????


        shdate.setText(purchaseOrders.getShdate()); //????????????


        updatepeople.setText(purchaseOrders.getUpdatepeople()); //???????????????

        updatedate.setText(purchaseOrders.getUpdatedate()); //??????????????????


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
     * ????????????????????????????????????
     * @param isNum
     */
    public void loadSupplier(String isNum,PurchaseOrders purchaseOrders){

        //?????????
        SupplierBasic supplierBasic =  supplierBasicService.selectSupplierBasicByIsnum(isNum);

        if(supplierBasic !=  null){

            supname.setText(supplierBasic.getSupname());

            //????????????????????????
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
     * ????????????
     * @param status
     */
    public void changeEditable(boolean status){

        orders.setEditable(false);//????????????

        suppliernum.setDisable(!status); //???????????????

        suppliername.setDisable(!status); //???????????????

        warehouseid.setDisable(!status);

        warehousename.setDisable(!status);

        seeorders.setDisable(!status);

        tax.setDisable(!status); //??????

        currency.setDisable(!status); //??????

        purpeopletype.setDisable(!status); //??????????????????

        purpeople.setDisable(!status); //???????????????

        createpeople.setDisable(!status); //?????????

        shpeople.setDisable(!status); //?????????

        shdate.setDisable(!status); //????????????

        updatepeople.setDisable(!status); //???????????????

        updatedate.setDisable(!status); //??????????????????

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


        supplierBtn.setDisable(!status); //???????????????????????????

        importData.setDisable(!status);

    }



    /**
     * ??????
     */
    public void clearValue(){

        orders.setText(NumberUtil.NULLSTRING);//????????????

        suppliernum.setText(NumberUtil.NULLSTRING); //???????????????

        suppliername.setText(NumberUtil.NULLSTRING); //???????????????

        tax.getSelectionModel().select(0); //??????

        currency.getSelectionModel().select(0); //??????

        purpeopletype.getSelectionModel().select(0); //??????????????????

        purpeople.setText(NumberUtil.NULLSTRING); //???????????????

        createpeople.setText(NumberUtil.NULLSTRING); //?????????

        shpeople.setText(NumberUtil.NULLSTRING); //?????????

        shdate.setText(NumberUtil.NULLSTRING); //????????????

        updatepeople.setText(NumberUtil.NULLSTRING); //???????????????

        updatedate.setText(NumberUtil.NULLSTRING); //??????????????????

    }

    /**
     * ??????
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","????????????!")) {
            long id = (long) orders.getUserData();
            int rows = purchaseOrdersService.delete(id);
            if (rows > 0) {
                findInquiry(1); //????????????????????????
            }
            NumberUtil.changeStatus(fxmlStatus, 0); //?????????????????????
            this.changeEditable(false);
        }
    }



    /**
     * ??????
     */
    public void update(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.UPDATE);//?????????????????????
        this.changeEditable(true);

        submitvbox.setDisable(false);
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);

        lastUpdatePeopel(updatepeople,updatedate); //??????????????? ?????????????????????
        //??????????????????
       /* blankContact();
        blankPurchaser();*/

        blankInquiryProduct();
//        blankInquiryDescription();
//        blankInquiryRemarks();


    }


    /**
     *  ????????????
     */
    public void insert(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.INSERT);//?????????????????????
        this.changeEditable(true);
        clearValue();//????????????
        submitvbox.setDisable(false);
        updatevbox.setDisable(true);
        deletevbox.setDisable(true);
        insertvbox.setDisable(true);
        createPeople(createpeople);//?????????
        purchaseProductProperties.clear();
        blankInquiryProduct();
    }


    /**
     * ??????
     */
    public  void submit(){

        int  submitStuts = (int)fxmlStatus.getUserData(); //1????????? 2?????????

        LocalDate orderDate =createdate.getValue();

        String supperIsnum =suppliernum.getText();

        if(supperIsnum == "" || "".equals(supperIsnum)){
            alert_informationDialog("????????????????????????!");
            return;
        }
        if(orderDate == null){
            alert_informationDialog("?????????????????????!");
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
            //??????????????????
            String orderNum = createIsnum(orderDate.toString());
            orders.setText(orderNum);
            purchaseOrders.setOrders(orderNum);
            rows = purchaseOrdersService.save(purchaseOrders);
        }else if(submitStuts ==2){
            purchaseOrders.setId((long)orders.getUserData());
            rows = purchaseOrdersService.updateNotNull(purchaseOrders);
        }

                            /*saveContact(supplierBasic.getId());//?????????
                            savePurchaser(supplierBasic.getId()); //???????????????*/
        saveInquiryProduct(purchaseOrders.getId());
//        saveInquiryDescription(purchaseOrders.getId());
//        saveInquiryRemarks(purchaseOrders.getId());
        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);

        this.loadDate(purchaseOrders); //??????????????????

        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


    }


    //????????????
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
        tableView1.setItems(purchaseProductProperties); //tableview??????list w



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
                    //?????????????????????
                    PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseProductProperty.getId(),purchaseProductProperty.getSourseorder(),purchaseProductProperty.getInquiryorders(),Integer.parseInt(purchaseProductProperty.getSort()),purchaseProductProperty.getProorders(),purchaseProductProperty.getProname(),purchaseProductProperty.getProsupname(),Integer.parseInt(purchaseProductProperty.getPronum()),purchaseProductProperty.getProunit(),Double.valueOf(purchaseProductProperty.getProprice()),Double.valueOf(purchaseProductProperty.getTotalprice()),purchaseProductProperty.getRemarks(),purchase);
                    purchaseProductService.updateNotNull(purchaseProduct);
                }else{
                    //?????????????????????
                    PurchaseProduct purchaseProduct = new PurchaseProduct(purchaseProductProperty.getSourseorder(),purchaseProductProperty.getInquiryorders(),Integer.parseInt(purchaseProductProperty.getSort()),purchaseProductProperty.getProorders(),purchaseProductProperty.getProname(),purchaseProductProperty.getProsupname(),Integer.parseInt(purchaseProductProperty.getPronum()),purchaseProductProperty.getProunit(),Double.valueOf(purchaseProductProperty.getProprice()),Double.valueOf(purchaseProductProperty.getTotalprice()),purchaseProductProperty.getRemarks(),purchase);
                    purchaseProduct.setOntheway(0);
                    purchaseProduct.setForcenum(0);
                    purchaseProduct.setStockover(0);
                    purchaseProductService.save(purchaseProduct);
                }
            }
        }
    }


    //?????????????????????
    public void blankInquiryProduct(){
        PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty();
        purchaseProductProperties.add(purchaseProductProperty);
    }







    //??????????????????????????????
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
     * ????????????
     *
     * ??????????????????????????????
     *
     */
    public void shButtonSuccess(){
        shno.setDisable(false);
        shyes.setDisable(true);
        lastUpdatePeopel(shpeople,shdate);
        updateOrderStatus(1);
    }

    /**
     * ????????????
     *
     * ????????? ?????????????????????????????????
     *
     */
    public void shButtonCancel(){
        shno.setDisable(true);
        shyes.setDisable(false);
        updateOrderStatus(0);
    }

    /**
     * ??????????????????
     * @param status  0????????????    1???????????????
     */
    public void updateOrderStatus(int status){
        long id =  (long)orders.getUserData();
        PurchaseOrders purchaseOrders = purchaseOrdersService.selectByKey(id);
        purchaseOrders.setShstatus(status);
        purchaseOrdersService.updateNotNull(purchaseOrders);
    }



    //??????---?????????
    public void importButtonInquiry(){

        stage.setTitle("??????-?????????");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/inquiry_import.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //????????????*/
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
                inquiryOrder1.setStrstatus("?????????");
            }else{

                inquiryOrder1.setStrstatus("?????????");
            }

            list.add(inquiryOrder1);

        }

        inquiryView.setItems(list); //tableview??????list

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

    inquiryProductView.setItems(inquiryImportProperties); //tableview??????list

}




    //??????----??????????????????????????????????????????

    //??????????????????
    public  void importInquiryProductData(){

            InquiryOrder inquiryOrder = inquiryOrderService.selectByKey(inquiryOrderId);


            for(InquiryImportProperty inquiryImportProperty : inquiryImportProperties){

                if(inquiryOrder.getShstatus()==0){
                    alert_informationDialog("?????????????????????????????????!");
                }else{
                    //?????????????????????
                    if(inquiryImportProperty.isChecked()){
                        InquiryProduct inquiryProduct = inquiryProductService.selectByKey(inquiryImportProperty.getId());
                        PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty(0,"?????????",inquiryOrder.getOrders(),"0",inquiryProduct.getProisnum(),inquiryProduct.getProname(),inquiryOrder.getSuppliername(),inquiryProduct.getPronum().toString(),inquiryProduct.getProunit(),inquiryProduct.getProprice().toString(),inquiryProduct.getTotalprice().toString(),inquiryProduct.getProremark());
                        purchaseProductProperties.add(purchaseProductProperty);
                    }
                }
            }

            inquiryImportProperties.clear();

        coseInquiryWin();
    }





    //?????????????????????
    public void importPurchaseStock(){

        PurchaseOrders purchaseOrders =  purchaseOrdersService.selectByKey((long)orders.getUserData());

        if(purchaseOrders.getShstatus() == 1){
            StageManager.purchaseOrders =purchaseOrders;

            StageManager.purchaseProducts = purchaseProductService.findPurchaseProduct((long)orders.getUserData());

            Pane pane1 = StageManager.ORDERCONTENT.get("PurchasePane");

            pane1.getChildren().setAll(loader.load("/fxml/stock/purchase_stock.fxml"));
        }else{
            alert_informationDialog("????????????????????????????????????!");
        }


    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //??????????????????
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

                setComboBox(7L,currency,0); //??????
                tax.getItems().addAll("??????","??????","??????","??????");
                tax.getSelectionModel().select(0); //??????




            }else{

            suppliernum.setText(inquiryOrder.getSuppliernum()); //???????????????
            suppliername.setText(inquiryOrder.getSuppliername());//???????????????

            PurchaseOrders purchaseOrders = new PurchaseOrders();
                purchaseOrders.setSupplieraddress(inquiryOrder.getSupplieraddress());
                purchaseOrders.setSupplierfax(inquiryOrder.getSupplierfax());
                purchaseOrders.setSupplierphone(inquiryOrder.getSupplierphone());
                purchaseOrders.setSupplierconcat(inquiryOrder.getSupplierconcat());
                loadSupplier(inquiryOrder.getSuppliernum(),purchaseOrders);

                int taxFinal = inquiryOrder.getTaxs();//??????
                int currencysFinal = inquiryOrder.getCurrencys();//??????
                tax.getItems().addAll("??????","??????","??????","??????");
                tax.getSelectionModel().select(--taxFinal); //??????
                setComboBox(7L,currency,--currencysFinal); //??????

                orders.setUserData(0L);

                inquiryProduct();

                List<InquiryProduct> inquiryProducts = StageManager.inquiryProductsInfo;

                for(InquiryProduct inquiryProduct : inquiryProducts){

                    PurchaseProductProperty purchaseProductProperty = new PurchaseProductProperty(0,"?????????",inquiryOrder.getOrders(),"0",inquiryProduct.getProisnum(),inquiryProduct.getProname(),inquiryOrder.getSuppliername(),inquiryProduct.getPronum().toString(),inquiryProduct.getProunit(),inquiryProduct.getProprice().toString(),inquiryProduct.getTotalprice().toString(),inquiryProduct.getProremark());
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
