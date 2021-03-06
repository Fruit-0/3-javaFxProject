package com.yc.education.controller.sale;


import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;

import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.customer.Customer;
import com.yc.education.model.customer.CustomerContacts;
import com.yc.education.model.customer.Remark;
import com.yc.education.model.sale.ReportRemark;
import com.yc.education.model.sale.SaleOfferProduct;
import com.yc.education.model.sale.SaleQuotation;
import com.yc.education.property.SaleOfferProductProperty;
import com.yc.education.property.RemarkProperty;
import com.yc.education.property.ReportRemarkProperty;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.customer.*;
import com.yc.education.service.sale.IReportRemarkService;
import com.yc.education.service.sale.ISaleOfferProductService;
import com.yc.education.service.sale.ISaleQuotationService;
import com.yc.education.util.DateUtils;
import com.yc.education.util.EditCell;
import com.yc.education.util.NumberUtil;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ??????-?????????
 */
@Controller
public class QuotationController extends BaseController implements Initializable {

    @Autowired
    ISaleQuotationService iSaleQuotationService;
    @Autowired
    ISaleOfferProductService iSaleOfferProductService;
    @Autowired
    IRemarkService iRemarkService;
    @Autowired
    IReportRemarkService iReportRemarkService;
    @Autowired
    ICustomerContactsService iCustomerContactsService;
    @Autowired
    ICustomerService iCustomerService;
    @Autowired
    ICustomerShippingAddressService iCustomerShippingAddressService;


    // ???????????????
    @FXML VBox menu_delete;
    @FXML VBox menu_insert;
    @FXML VBox menu_commit;
    @FXML VBox menu_clearAll;
    @FXML VBox menu_update; //??????
    @FXML VBox shiro_success;
    @FXML VBox shiro_cancel;
    @FXML VBox import_out;
    @FXML Label writestate;// ?????????

    @FXML
    TextField orderid; // ??????id
    @FXML
    DatePicker create_date; // ????????????
    @FXML
    public TextField customer_no; //????????????
    @FXML
    public TextField customer_id; // ??????id
    @FXML
    public TextField offer_no; // ????????????
    @FXML
    public TextField customer_no_str; // ????????????
    @FXML
    DatePicker enquiry_date; // ????????????
    @FXML
    ComboBox business; // ????????????
    @FXML
    TextField business_str; // ??????????????????
    @FXML
    ComboBox tax; // ??????
    @FXML
    TextField discount; // ??????
    @FXML
    CheckBox special_offer;  // ?????????
    //======================= ???????????? =========================
    @FXML
    TextField amount_receivable; // ????????????
    @FXML
    TextField audit; // ?????????
    @FXML
    TextField last_modifier; // ???????????????
    @FXML
    TextField customer_name; // ????????????
    @FXML
    ComboBox currency; // ??????
    @FXML
    TextField audit_str; // ???????????????
    @FXML
    TextField last_modifier_str; // ?????????????????????
    @FXML
    TextField single_people; // ?????????
    @FXML
    DatePicker valid_until; // ????????????
    @FXML
    ComboBox contact; // ?????????
    @FXML
    ComboBox telephone; // ????????????
    @FXML
    ComboBox fax; // ??????
    @FXML
    ComboBox address; // ??????
    @FXML
    TextField customer_category; // ????????????
    // ================= ????????????????????? ======================
    @FXML
    TextField calc_number;
    @FXML
    TextField calc_money;
    @FXML
    TextField calc_loan;
    @FXML
    TextField calc_tax;
    // ====================== tableview??? ==============================
    @FXML
    TableView offer_table;
    @FXML
    TableColumn offer_col_id;
    @FXML
    TableColumn offer_col_product_no;
    @FXML
    TableColumn offer_col_product_name;
    @FXML
    TableColumn offer_col_category;
    @FXML
    TableColumn offer_col_num;
    @FXML
    TableColumn offer_col_unit;
    @FXML
    TableColumn offer_col_pricing;
    @FXML
    TableColumn offer_col_discount;
    @FXML
    TableColumn offer_col_price;
    @FXML
    TableColumn offer_col_money;
    @FXML
    TableColumn offer_col_remark;

    @FXML
    TableView remark_table;
    @FXML
    TableColumn remark_id;
    @FXML
    TableColumn remark_content;
    @FXML
    TableView report_table;
    @FXML
    TableColumn report_id;
    @FXML
    TableColumn report_content;

    /***************************************** ????????????-?????? ********************************************/
    @Autowired
    private ProductBasicService productBasicService; //?????? Service
    @FXML private TableView tableViewProduct; //??????????????????
    @FXML private TableColumn depid; //id
    @FXML private TableColumn findproductid; //??????????????????
    @FXML private TableColumn findproductname; //??????????????????
    @FXML private TableColumn findprotype; //????????????
    @FXML private TableColumn findnormprice; //??????????????????
    @FXML private TableColumn findlowprice; //??????????????????
    @FXML private TableColumn findsafetystock; //????????????
    @FXML private TableColumn findremarks; //??????
    @FXML private Button client_sure; // ????????????
    int tablePosition = 0; // ???????????????????????????

    /***************************************** ????????????-??????-?????? ********************************************/


    private static SpringFxmlLoader loader = new SpringFxmlLoader();
    Stage stage = new Stage();


    // ????????????
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINA);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime localDate = LocalDateTime.now();
    // ?????????
    private static int page = 0;
    // ?????????
    private final static int rows = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tax.getItems().addAll("??????","??????");
        tax.getSelectionModel().select(0);
        setComboBox(7L,currency,0); //??????
        // ??????????????????
        setControlDisable();
        // ????????????????????????????????????
        firstQuotation();
        //????????????????????????
        customerCodeChange();
        // ?????????????????????
        quotationCodeChange();
        business.getItems().clear();
        loadEmployee(business, 0);
        business.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String bus = business.getSelectionModel().getSelectedItem().toString();
                    bus = bus.substring(bus.indexOf(')')+1, bus.length());
                    business_str.setText(bus);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // ????????????????????? ????????????????????????
        BaseController.clickEvent(offer_table, data ->{
            tablePosition = offer_table.getSelectionModel().getSelectedIndex();
            moreProductButtonClick();
        }, 2);
    }
    /**
     * ??????????????????
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

        tableViewProduct.setItems(list); //tableview??????list

        tableViewProduct.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductBasic>() {
            @Override
            public void changed(ObservableValue<? extends ProductBasic> observableValue, ProductBasic oldItem, ProductBasic newItem) {
                if(newItem.getIsnum()!= null && !("".equals(newItem.getIsnum()))){
                    client_sure.setUserData(newItem.getId());
                }
            }
        });


        tableViewProduct.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });
    }

    /**
     * ??????????????????
     */
    public  void moreProductButtonClick(){
        stage.setTitle("????????????????????????");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/sale/product_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        loadMoreProduct();
    }

    public void closeSureWin(){
        long productid =(long)client_sure.getUserData();
        ProductBasic productBasic =  productBasicService.selectByKey(productid);
        // ????????????????????????????????????
        // todo...
        ObservableList<SaleOfferProductProperty> list = offer_table.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        for (int i = 0; i < list.size(); i++) {
            if(tablePosition == i){
                list.get(i).setProductName(productBasic.getProname());
                list.get(i).setProductNo(productBasic.getIsnum());
            }
        }
        offer_table.setItems(FXCollections.observableArrayList(list));
        coseWin();
    }

    public void coseWin(){
        stage.close();
        tablePosition = 0;
    }

    /**
     * ?????????????????????
     */
    @FXML
    public void save(){
        // ?????????
        createPeople(last_modifier,last_modifier_str);

        String id = orderid.getText();

        SaleQuotation quotation = new SaleQuotation();
        if ( create_date.getValue() != null) {
            try {
                Date date = df.parse(create_date.getValue().toString());
                quotation.setCreateDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (customer_no.getText() != null && !"".equals(customer_no.getText())) {
            quotation.setCustomerNo(customer_no.getText());
        }
        if (offer_no.getText() != null && !"".equals(offer_no.getText()) ) {
            quotation.setOfferNo(offer_no.getText());
        }
        if (customer_no_str.getText() != null && !"".equals(customer_no_str.getText())) {
            quotation.setCustomerNoStr(customer_no_str.getText());
        }
        if (enquiry_date.getValue() != null && !"".equals(enquiry_date.getValue())) {
            try {
                Date date = df.parse(enquiry_date.getValue().toString());
                quotation.setEnquiryDate(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (business.getSelectionModel().getSelectedItem() != null && !("".equals(business.getSelectionModel().getSelectedItem()))) {
            quotation.setBusiness(business.getSelectionModel().getSelectedIndex()+"");
        }
        if (business_str.getText() != null && !"".equals(business_str.getText())) {
            quotation.setBusinessStr(business_str.getText());
        }
        if (tax.getSelectionModel().getSelectedItem() != null ) {
            quotation.setTax(tax.getSelectionModel().getSelectedIndex());
        }
        if (discount.getText() != null && !"".equals(discount.getText())) {
            quotation.setDiscount(discount.getText());
        }
        quotation.setSpecialOffer(special_offer.selectedProperty().getValue());

        //======================= ???????????? =========================
        if (amount_receivable.getText() != null && !"".equals(amount_receivable.getText())) {
            quotation.setAmountReceivable(amount_receivable.getText());
        }
        if (audit.getText() != null && !"".equals(audit.getText())) {
            quotation.setAudit(audit.getText());
        }
        if (last_modifier.getText() != null && !"".equals(last_modifier.getText())) {
            quotation.setLastModifier(last_modifier.getText());
        }
        if (customer_name.getText() != null && !"".equals(customer_name.getText())) {
            quotation.setCustomerName(customer_name.getText());
        }
        if (currency.getSelectionModel().getSelectedItem() != null && !"".equals(currency.getSelectionModel().getSelectedItem())) {
            quotation.setCurrency(currency.getSelectionModel().getSelectedIndex()+1);
        }
        if (audit_str.getText() != null && !"".equals(audit_str.getText())) {
            quotation.setAuditStr(audit_str.getText());
        }
        if (last_modifier_str.getText() != null && !"".equals(last_modifier_str.getText())) {
            quotation.setLastModifierStr(last_modifier_str.getText());
        }
        if (single_people.getText() != null && !"".equals(single_people.getText())) {
            quotation.setSinglePeople(single_people.getText());
        }
        if (valid_until.getValue() != null && !"".equals(valid_until.getValue())) {
            try {
                Date date = df.parse(valid_until.getValue().toString());
                quotation.setValidUntil(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (contact.getSelectionModel().getSelectedItem() != null && !"".equals(contact.getSelectionModel().getSelectedItem())) {
            quotation.setContact(contact.getValue().toString());
        }
        if (telephone.getSelectionModel().getSelectedItem() != null && !"".equals(telephone.getSelectionModel().getSelectedItem())) {
            quotation.setTelephone(telephone.getValue().toString());
        }
        if (fax.getSelectionModel().getSelectedItem() != null && !"".equals(fax.getSelectionModel().getSelectedItem())) {
            quotation.setFax(fax.getValue().toString());
        }
        if (address.getSelectionModel().getSelectedItem() != null && !"".equals(address.getSelectionModel().getSelectedItem())) {
            quotation.setAddress(address.getValue().toString());
        }
        if (customer_category.getText() != null && !"".equals(customer_category.getText())) {
            quotation.setCustomerCategory(customer_category.getText());
        }

        if(id == null || "".equals(id)){
            //??????
            quotation.setAddtime(new Date());
            int rows = iSaleQuotationService.save(quotation);
            orderid.setText(quotation.getId().toString());
            returnMsg(rows);
        }else{
            // ??????
            quotation.setId(Long.valueOf(id));
            int rows = iSaleQuotationService.updateNotNull(quotation);
            returnMsg(rows);
        }
        saveOfferProduct();
        saveRemark();
        saveReportRemark();
        setControlDisable();
    }

    /**
     * ??????????????????view??????
     */
    private void saveOfferProduct(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = offer_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                SaleOfferProductProperty property = null;
                if(offer_table.getItems().get(i) != null){
                    property = (SaleOfferProductProperty)offer_table.getItems().get(i);
                }
                SaleOfferProduct offerProduct = new SaleOfferProduct();
                if(property.getProductNo() != null){
                    offerProduct.setProductNo(property.getProductNo());
                }
                if(property.getProductName() != null){
                    offerProduct.setProductName(property.getProductName());
                }
                if(property.getCategory() != null){
                    offerProduct.setCategory(property.getCategory());
                }
                if(property.getNum() != null){
                    offerProduct.setNum(Integer.valueOf(property.getNum()));
                }
                if(property.getUnit() != null){
                    offerProduct.setUnit(property.getUnit());
                }
                if(property.getPricing() != null){
                    offerProduct.setPricing(new BigDecimal(property.getPricing()));
                }
                if(property.getDiscount() != null){
                    offerProduct.setDiscount(property.getDiscount());
                }
                if(property.getPrice() != null){
                    offerProduct.setPrice(new BigDecimal(property.getPrice()));
                }
                if(property.getMoney() != null){
                    offerProduct.setMoney(new BigDecimal(property.getMoney()));
                }
                if(property.getRemark() != null){
                    offerProduct.setRemark(property.getRemark());
                }
                // ??????????????????ID
                offerProduct.setUserid(1L);
                offerProduct.setQuotationId(Long.valueOf(id));
                if(property.getId() == null){
                    offerProduct.setAddtime(new Date());
                    try {
                        iSaleOfferProductService.save(offerProduct);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    offerProduct.setId(property.getId());
                    try {
                        iSaleOfferProductService.updateNotNull(offerProduct);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????view??????
     */
    private void saveRemark(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = remark_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                RemarkProperty property = null;
                if(remark_table.getItems().get(i) != null){
                    property = (RemarkProperty)remark_table.getItems().get(i);
                }
                Remark remark = new Remark();
                remark.setTypeid(2);
                remark.setOtherid(Long.valueOf(id));
                remark.setRemark(property.getRemark());
                if(property.getId() == null){
                    remark.setAddtime(new Date());
                    try {
                        iRemarkService.save(remark);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    remark.setId(property.getId());
                    try {
                        iRemarkService.updateNotNull(remark);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * ??????????????????view??????
     */
    private void saveReportRemark(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int tableSize = report_table.getItems().size();
            for (int i = 0; i < tableSize; i++) {
                ReportRemarkProperty property = null;
                if(report_table.getItems().get(i) != null){
                    property = (ReportRemarkProperty)report_table.getItems().get(i);
                }
                ReportRemark remark = new ReportRemark();
                remark.setTypeid(1);
                remark.setOtherid(Long.valueOf(id));
                remark.setContent(property.getContent());
                if(property.getId() == null){
                    remark.setAddtime(new Date());
                    try {
                        iReportRemarkService.save(remark);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        remark.setId(property.getId());
                        iReportRemarkService.updateNotNull(remark);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * ???????????????
     */
    @FXML
    public void delete(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            int rows = iSaleQuotationService.delete(Long.valueOf(id));
            if(rows > 0){
                // ??????????????????
                // todo ...
                firstQuotation();
                alert_informationDialog("???????????????");
            }else{
                alert_informationDialog("???????????????");
            }
        }
    }

    /**
     * ?????????????????????
     */
    @FXML
    public void nextQuotation() {
        int max = iSaleQuotationService.getSaleQuotationCount();
        if (page < max - 1) {
            page += 1;
        }
        SaleQuotation quotation = iSaleQuotationService.getSaleQuotationByPage(page, rows);
        setBasicValue(quotation);
        setTableviewVal();
        setControlDisable();
    }

    /**
     * ?????????????????????
     */
    @FXML
    public void prevQuotation() {
        if (page > 0) {
            page -= 1;
        }
        SaleQuotation quotation = iSaleQuotationService.getSaleQuotationByPage(page, rows);
        setBasicValue(quotation);
        setTableviewVal();
        setControlDisable();
    }


    /**
     *  ????????????????????????
     **/
    @FXML
    public void lastQuotation() {
        page = iSaleQuotationService.getSaleQuotationCount() - 1;
        SaleQuotation quotation = iSaleQuotationService.getLastSaleQuotation();
        setBasicValue(quotation);
        setTableviewVal();
        setControlDisable();
    }

    /**
     * ?????????????????????
     */
    @FXML
    public void firstQuotation() {
        page = 0;
        SaleQuotation quotation = iSaleQuotationService.getFirstSaleQuotation();
        setBasicValue(quotation);
        setTableviewVal();
        setControlDisable();
    }

    /**
     * ???????????????tableview????????????
     * ??????????????????????????????
     */
    private void setTableviewVal(){

        String id = orderid.getText();

        // ????????????
        offer_table.setEditable(true);


        offer_col_product_no.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_product_name.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_category.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_num.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_unit.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_pricing.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_discount.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_price.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_money.setCellFactory(column -> EditCell.createStringEditCell());
        offer_col_remark.setCellFactory(column -> EditCell.createStringEditCell());

        List<SaleOfferProduct> list = iSaleOfferProductService.listSaleOfferProduct(Long.valueOf(id));
        List<SaleOfferProductProperty> newlist = new ArrayList<>();
        list.forEach(p->{
            SaleOfferProductProperty product = new SaleOfferProductProperty(p.getId(), p.getProductNo(), p.getProductName(), p.getCategory(), p.getNum(), p.getUnit(), p.getPricing(), p.getDiscount(), p.getPrice(), p.getMoney(), p.getRemark(), 0L);
            newlist.add(product);
        });
        final ObservableList<SaleOfferProductProperty> data = FXCollections.observableArrayList(newlist);
        offer_col_id.setCellValueFactory(new PropertyValueFactory<SaleOfferProduct,LongStringConverter>("id"));
        offer_col_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));//??????
        offer_col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
        offer_col_category.setCellValueFactory(new PropertyValueFactory("category"));
        offer_col_num.setCellValueFactory(new PropertyValueFactory("num"));
        offer_col_unit.setCellValueFactory(new PropertyValueFactory("unit"));
        offer_col_pricing.setCellValueFactory(new PropertyValueFactory("pricing"));
        offer_col_discount.setCellValueFactory(new PropertyValueFactory("discount"));
        offer_col_price.setCellValueFactory(new PropertyValueFactory("price"));
        offer_col_money.setCellValueFactory(new PropertyValueFactory("money"));
        offer_col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        offer_table.setItems(data);

        // ???????????????
        remark_table.setEditable(true);

        remark_content.setCellFactory(column -> EditCell.createStringEditCell());

        List<Remark> remarkList = iRemarkService.listRemark(Long.valueOf(id),"2");
        List<RemarkProperty> newRemarkList = new ArrayList<>();
        remarkList.forEach(p->{
             newRemarkList.add(new RemarkProperty(p.getId(),p.getRemark()));
        });
        final ObservableList<RemarkProperty> dataRemark = FXCollections.observableArrayList(newRemarkList);
        remark_id.setCellValueFactory(new PropertyValueFactory<Remark,Long>("id"));
        remark_content.setCellValueFactory(new PropertyValueFactory("remark"));//??????

        remark_table.setItems(dataRemark);

        // ????????????
        report_table.setEditable(true);


        report_content.setCellFactory(column -> EditCell.createStringEditCell());

        List<ReportRemark> reportRemarkList = iReportRemarkService.listReportRemark(Long.valueOf(id),"1");
        List<ReportRemarkProperty> newReportRemarkList = new ArrayList<>();
        reportRemarkList.forEach(p->{
            newReportRemarkList.add(new ReportRemarkProperty(p.getId(),p.getContent()));
        });
        final ObservableList<ReportRemarkProperty> dataReportRemark = FXCollections.observableArrayList(newReportRemarkList);
        report_id.setCellValueFactory(new PropertyValueFactory<ReportRemark,Long>("id"));
        report_content.setCellValueFactory(new PropertyValueFactory("content"));//??????

        report_table.setItems(dataReportRemark);
    }

    /**
     * ????????????view
     * enter ??????????????????
     * @param event
     */
    @FXML
    public void KeyOfOfferPorductTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addOfferProduct();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfOfferProduct();
        }
    }

    /**
     * ???????????????view
     * enter ??????????????????
     * @param event
     */
    @FXML
    public void KeyOfRemarkTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addRemark();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfRemark();
        }
    }

    /**
     * ????????????view
     * enter ??????????????????
     * @param event
     */
    @FXML
    public void KeyOfReportRemarkTableView(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            addReportRemark();
        }
        if (event.getCode() == KeyCode.DELETE) {
            deleteRowOfReportRemark();
        }
    }


    /**
     * ?????????????????????
     */
    public void addOfferProduct() {

        ObservableList<SaleOfferProductProperty> list = offer_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new SaleOfferProductProperty("", "", "", 0, "", new BigDecimal("0.00"), "", new BigDecimal("0.00"), new BigDecimal("0.00"), ""));
        offer_table.setItems(list);
    }

    /**
     * ????????????????????????
     */
    public void addRemark() {

        ObservableList<RemarkProperty> list = remark_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new RemarkProperty(""));
        remark_table.setItems(list);
    }

    /**
     * ?????????????????????
     */
    public void addReportRemark() {

        ObservableList<ReportRemarkProperty> list = report_table.getItems();

        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        list.add(new ReportRemarkProperty(""));
        report_table.setItems(list);
    }

    /**
     * ??????????????????
     */
    private void deleteRowOfOfferProduct(){
        // ????????????????????????
        try {
            TablePosition pos = (TablePosition) offer_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            SaleOfferProduct product = (SaleOfferProduct)offer_table.getItems().get(index);
            if(product.getId() != null && product.getId() != 0L){
                int rows = iSaleOfferProductService.delete(product.getId());
                returnMsg(rows);
            }
            final ObservableList<SaleOfferProduct> dataProperty = offer_table.getItems();
            final ObservableList<SaleOfferProduct> newDataProperty = FXCollections.observableArrayList();
            for (int i = 0; i < dataProperty.size(); i++) {
                if(i != index){
                    newDataProperty.add(dataProperty.get(i));
                }
            }
            offer_table.setItems(newDataProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     */
    private void deleteRowOfRemark(){
        // ????????????????????????
        try {
            TablePosition pos = (TablePosition) remark_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            Remark remark = (Remark)remark_table.getItems().get(index);
            if(remark.getId() != null && remark.getId() != 0L){
                int rows = iRemarkService.delete(remark.getId());
                returnMsg(rows);
            }
        }catch (Exception e){
            alert_informationDialog("??????????????????????????????");
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     */
    private void deleteRowOfReportRemark(){
        // ????????????????????????
        try {
            TablePosition pos = (TablePosition) report_table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            ReportRemark remark = (ReportRemark)report_table.getItems().get(index);
            if(remark.getId() != null && remark.getId() != 0L){
                int rows = iReportRemarkService.delete(remark.getId());
                returnMsg(rows);
            }
        }catch (Exception e){
            alert_informationDialog("??????????????????????????????");
            e.printStackTrace();
        }
    }



    /**
     * ??????
     */
    @FXML
    public void add(){
        clearValue();
        NumberUtil.changeStatus(writestate,1);
        offer_no.setText("BJ"+new Date().getTime()+getRandomone());
        enquiry_date.setValue(localDate.toLocalDate());
        createPeople(single_people);
        setControlUnDisable();
    }


    /**
     * ?????????
     */
    private void setBasicValue(SaleQuotation sale){
        List<CustomerContacts> contactsList = new ArrayList<>();
        List<String> listContact = new ArrayList<>();
        List<String> listPhone = new ArrayList<>();
        List<String> listFax = new ArrayList<>();
        List<String> listAddress = new ArrayList<>();

        if(sale.getCustomerNo() != null && !"".equals(sale.getCustomerNo())){
            Customer customer = iCustomerService.getCustomer(sale.getCustomerNo());
            if(customer != null){
                contactsList = iCustomerContactsService.listCustomerContactsByCustomerId(customer.getId());
                iCustomerShippingAddressService.listAll(customer.getId()).forEach(p->{
                    if(p.getAddress() != null && !"".equals(p.getAddress())){
                        listAddress.add(p.getAddress());
                    }
                });
            }
        }
        contactsList.forEach(p->{
            if(p.getMobilePhone() != null && !"".equals(p.getMobilePhone())){
                listPhone.add(p.getMobilePhone());
            }
            if(p.getPrimaryContacts() != null && !"".equals(p.getPrimaryContacts())){
                listContact.add(p.getPrimaryContacts());
            }
            if(p.getFax() != null && !"".equals(p.getFax())){
                listFax.add(p.getFax());
            }
        });

        if(sale.getId() != null && !"".equals(sale.getId())){
            orderid.setText(sale.getId().toString());
            orderid.setUserData(sale.getOrderAudit());
        }
        if(sale.getCreateDate() != null){
            create_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(sale.getCreateDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(sale.getCustomerNo() != null && !"".equals(sale.getCustomerNo())){
            customer_no.setText(sale.getCustomerNo());
        }
        if(sale.getOfferNo() != null && !"".equals(sale.getOfferNo())){
            offer_no.setText(sale.getOfferNo());
        }
        if(sale.getCustomerNoStr() != null && !"".equals(sale.getCustomerNoStr())){
            customer_no_str.setText(sale.getCustomerNoStr());
        }
        if(sale.getEnquiryDate() != null){
            enquiry_date.setValue(LocalDate.parse(DateUtils.getSpecifyDate(sale.getEnquiryDate(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(sale.getBusiness() != null && !"".equals(sale.getBusiness())){
            business.getSelectionModel().select(Integer.valueOf(sale.getBusiness())+0);
        }
        if(sale.getBusinessStr() != null && !"".equals(sale.getBusinessStr())){
            business_str.setText(sale.getBusinessStr());
        }
        if(sale.getTax() != null && !"".equals(sale.getTax())){
            tax.getSelectionModel().select(Integer.valueOf(sale.getTax())+0);
        }
        if(sale.getDiscount() != null && !"".equals(sale.getDiscount())){
            discount.setText(sale.getDiscount());
        }
        if(sale.getSpecialOffer() != null){
            special_offer.setSelected(sale.getSpecialOffer());
        }
        if(sale.getAmountReceivable() != null && !"".equals(sale.getAmountReceivable())){
            amount_receivable.setText(sale.getAmountReceivable());
        }
        if(sale.getAudit() != null && !"".equals(sale.getAudit())){
            audit.setText(sale.getAudit());
        }
        if(sale.getLastModifier() != null && !"".equals(sale.getLastModifier())){
            last_modifier.setText(sale.getLastModifier());
        }
        if(sale.getCustomerName() != null && !"".equals(sale.getCustomerName())){
            customer_name.setText(sale.getCustomerName());
        }
        if(sale.getCurrency() != null && !"".equals(sale.getCurrency())){
            setComboBox(7L,currency,sale.getCurrency()-1);
        }
        if(sale.getAuditStr() != null && !"".equals(sale.getAuditStr())){
            audit_str.setText(sale.getAuditStr());
        }
        if(sale.getLastModifierStr() != null && !"".equals(sale.getLastModifierStr())){
            last_modifier_str.setText(sale.getLastModifierStr());
        }
        if(sale.getSinglePeople() != null && !"".equals(sale.getSinglePeople())){
            single_people.setText(sale.getSinglePeople());
        }
        if(sale.getValidUntil() != null && !"".equals(sale.getValidUntil())){
            valid_until.setValue(LocalDate.parse(DateUtils.getSpecifyDate(sale.getValidUntil(),DateUtils.FORMAT_YYYY_MM_DD),formatter));
        }
        if(sale.getContact() != null && !"".equals(sale.getContact())){
            if(listContact != null){
                contact.setItems(FXCollections.observableArrayList(listContact.toArray()));
                contact.getSelectionModel().select(sale.getContact());
            }
        }
        if(sale.getTelephone() != null && !"".equals(sale.getTelephone())){
            if(listPhone != null){
                telephone.setItems(FXCollections.observableArrayList(listPhone.toArray()));
                telephone.getSelectionModel().select(sale.getTelephone());
            }
        }
        if(sale.getFax() != null && !"".equals(sale.getFax())){
            if(listFax != null){
                fax.setItems(FXCollections.observableArrayList(listFax.toArray()));
                fax.getSelectionModel().select(sale.getFax());
            }
        }
        if(sale.getAddress() != null && !"".equals(sale.getAddress())){
            if(listAddress != null){
                address.setItems(FXCollections.observableArrayList(listAddress.toArray()));
                address.getSelectionModel().select(sale.getAddress());
            }
        }
        if(sale.getCustomerCategory() != null && !"".equals(sale.getCustomerCategory())){
            customer_category.setText(sale.getCustomerCategory());
        }
        if(sale.getOrderAudit() != null){
            if(sale.getOrderAudit()){
                setShiroControlSuccess();
                menu_update.setDisable(true);
            }else{
                setShiroControlCancel();
            }
        }

        calc_number.setText("");
        calc_money.setText("");
        calc_loan.setText("");
        calc_tax.setText("");
    }

    /**
     * ?????????
     */
    @FXML
    public void clearValue(){
        LocalDateTime localDate = LocalDateTime.now();
        orderid.clear();
        customer_id.clear();
        create_date.setValue(localDate.toLocalDate());
        customer_no.clear();
        offer_no.clear();
        customer_no_str.clear();
        enquiry_date.setValue(localDate.toLocalDate());
        business_str.clear();
        business.getSelectionModel().select(0);
        try {
            String bus = business.getSelectionModel().getSelectedItem().toString();
            bus = bus.substring(bus.indexOf(')')+1, bus.length());
            business_str.setText(bus);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
//        business.getSelectionModel().selectFirst();
        tax.getSelectionModel().selectFirst();
        discount.clear();
        special_offer.setSelected(false);

        amount_receivable.clear();
        audit.clear();
        last_modifier.clear();
        customer_name.clear();
        currency.getSelectionModel().selectFirst();
        audit_str.clear();
        last_modifier_str.clear();
        single_people.clear();
        valid_until.setValue(localDate.toLocalDate());
        contact.getItems().clear();
        telephone.getItems().clear();
        fax.getItems().clear();
        address.getItems().clear();
        customer_category.clear();




        calc_number.clear();
        calc_money.clear();
        calc_loan.clear();
        calc_tax.clear();

        offer_table.setItems(null);
        remark_table.setItems(null);
        report_table.setItems(null);
    }

    /**
     * ??????????????????
     */
    public void setControlDisable(){
        setControlState(true);
        setMenuControlState(true);
    }

    /**
     * ?????????????????????
     */
    @FXML
    public void setControlUnDisable(){
        setControlState(false);
        setMenuControlState(false);
    }


    /**
     * ??????????????????
     * @param bool
     */
    private void setControlState(Boolean bool){
        if(bool){
            NumberUtil.changeStatus(writestate,0);
        }else{
            NumberUtil.changeStatus(writestate,2);
        }
        create_date.setDisable(bool);
        customer_no.setDisable(bool);
        offer_no.setDisable(bool);
        customer_no_str.setDisable(bool);
        enquiry_date.setDisable(bool);
        business.setDisable(bool);
        business_str.setDisable(bool);
        tax.setDisable(bool);
        discount.setDisable(bool);
        special_offer.setDisable(bool);

        amount_receivable.setDisable(bool);
        audit.setDisable(bool);
        last_modifier.setDisable(bool);
        customer_name.setDisable(bool);
        currency.setDisable(bool);
        audit_str.setDisable(bool);
        last_modifier_str.setDisable(bool);
        single_people.setDisable(bool);
        valid_until.setDisable(bool);
        contact.setDisable(bool);
        telephone.setDisable(bool);
        fax.setDisable(bool);
        address.setDisable(bool);
        customer_category.setDisable(bool);
        calc_number.setDisable(bool);
        calc_money.setDisable(bool);
        calc_loan.setDisable(bool);
        calc_tax.setDisable(bool);

        offer_table.setDisable(bool);
        remark_table.setDisable(bool);
        report_table.setDisable(bool);
    }

    /**
     *  ????????????????????????
     * @param bool 
     */
    private void setMenuControlState(Boolean bool){
        menu_clearAll.setDisable(bool);
        menu_commit.setDisable(bool);
        menu_delete.setDisable(bool);
//        menu_insert.setDisable(bool);
    }

    /**
     * ????????????
     */
    @FXML
    public void setShiroControlSuccess(){
        // ??????????????????
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("???????????????????????????????????????");
            return;
        }
        shiro_cancel.setDisable(false);
        shiro_success.setDisable(true);
        import_out.setDisable(false);
        lastUpdatePeopel(audit, audit_str);
        lastUpdatePeopel(last_modifier, last_modifier_str);
        shiroUpdateData(true);

        menu_update.setDisable(true);
    }

    /**
     * ???????????????????????????
     */
    private void shiroUpdateData(Boolean bool){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SaleQuotation saleQuotation = iSaleQuotationService.selectByKey(Long.valueOf(id));
            saleQuotation.setOrderAudit(bool);
            saleQuotation.setLastModifier(last_modifier.getText());
            saleQuotation.setLastModifierStr(last_modifier_str.getText());
            saleQuotation.setAudit(audit.getText());
            saleQuotation.setAuditStr(audit_str.getText());
            iSaleQuotationService.updateNotNull(saleQuotation);

        }
    }

    /**
     * ????????????
     */
    @FXML
    public void setShiroControlCancel(){
        // ??????????????????
        String id = orderid.getText();
        if(id == null && "".equals(id)){
            alert_informationDialog("???????????????????????????????????????");
            return;
        }
        shiro_cancel.setDisable(true);
        shiro_success.setDisable(false);
        import_out.setDisable(true);
        lastUpdatePeopel(audit, audit_str);
        lastUpdatePeopel(last_modifier, last_modifier_str);
        shiroUpdateData(false);

        menu_update.setDisable(false);
    }

    /**
     * ????????????????????????
     */
    public  void customerCodeChange(){
        customer_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String customerid = customer_id.getText();
                if(customerid != null && !("".equals(customerid))){
                    contact.getItems().clear();
                    telephone.getItems().clear();
                    fax.getItems().clear();
                    Customer customer = iCustomerService.selectByKey(Long.valueOf(customerid));
                    if(customer != null){
                        List<CustomerContacts> contactsList = iCustomerContactsService.listCustomerContactsByCustomerId(customer.getId());
                        if(contactsList != null && contactsList.size() >0){
                            for (CustomerContacts c : contactsList) {
                                if(c.getPrimaryContacts() != null && !"".equals(c.getPrimaryContacts())){
                                    contact.getItems().add(c.getPrimaryContacts());
                                }
                                if(c.getTelephone() != null && !"".equals(c.getTelephone())){
                                    telephone.getItems().add(c.getTelephone());
                                }
                                if(c.getFax() != null && !"".equals(c.getFax())){
                                    fax.getItems().add(c.getFax());
                                }
                            }
                            contact.getSelectionModel().selectFirst();
                            telephone.getSelectionModel().selectFirst();
                            fax.getSelectionModel().selectFirst();
                            customer_name.setText(customer.getName());
                        }
                    }
                }
            }
        });
    }

    /**
     * ????????????????????????
     */
    public void quotationCodeChange(){
        orderid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // todo ..
                String id = orderid.getText();
                if(id != null && !"".equals(id)){
                    SaleQuotation quotation = iSaleQuotationService.selectByKey(Long.valueOf(id));
                    if(quotation != null){
                        setBasicValue(quotation);
                    }
                }
            }
        });
    }

    /**
     * ?????? ????????????
     */
    @FXML
    public void importOut(){
        String id = orderid.getText();
        if(id != null && !"".equals(id)){
            SaleQuotation saleQuotation = iSaleQuotationService.selectByKey(Long.valueOf(id));
            StageManager.saleQuotation = saleQuotation;
            if(saleQuotation != null && saleQuotation.getOrderAudit()){
                List<SaleOfferProduct> saleOfferProductList = iSaleOfferProductService.listSaleOfferProduct(saleQuotation.getId());
                if(saleOfferProductList != null){
                    StageManager.PRODUCT_LIST = saleOfferProductList;
                }
                Pane pane1 = StageManager.ORDERCONTENT.get("SalePane");

                //
                pane1.getChildren().setAll(loader.load("/fxml/sale/purchase_order.fxml"));
            }else{
                alert_informationDialog("????????????????????????????????????!");
            }
        }

    }

    /**
     * ??????
     */
    @FXML
    public void importIn(){

    }



    /**
     * ????????????????????????
     */
    @FXML
    public void OpenQuotationMiniQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Pane pane = new Pane();

        //?????????????????????map???
        StageManager.CONTROLLER.put("QuotationControllerNo", this);

        pane.getChildren().setAll(loader.load("/fxml/sale/quotation_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO  ????????????--??????????????????
     * @Date 20:22 2018/8/15
     * @Param []
     **/
    @FXML
    public void OpenCurrentClientQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Pane pane = new Pane();

        //?????????????????????map???
        StageManager.CONTROLLER.put("QuotationController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

}
