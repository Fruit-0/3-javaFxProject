package com.yc.education.controller.sale;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.customer.CustomerDemandGoodsInfo;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.customer.ICustomerDemandGoodsInfoService;
import com.yc.education.service.customer.ICustomerDemandGoodsService;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 客户需求商品查询
 */
@Controller
public class CustomerDemandGoodsWhereController extends BaseController implements Initializable {


    @Autowired
    ICustomerDemandGoodsService iCustomerDemandGoodsService;
    @Autowired
    ICustomerDemandGoodsInfoService iCustomerDemandGoodsInfoService;
    @Autowired
    private ProductBasicService productBasicService; //产品 Service


    @FXML public TextField cusotmer_no;
    @FXML public TextField cusotmer_no_end;
    @FXML ComboBox material;
    @FXML ComboBox process;
    @FXML ComboBox manufacture;
    @FXML public TextField create_no;
    @FXML public TextField create_no_end;
    @FXML TextField product_no;
    @FXML TextField product_no_end;
    @FXML DatePicker create_date;
    @FXML DatePicker create_date_end;

    @FXML TableView tab_product_info;

    @FXML TableColumn col_id;
    @FXML TableColumn col_product_no;
    @FXML TableColumn col_product_name;
    @FXML TableColumn col_demand_num;
    @FXML TableColumn col_price;
    @FXML TableColumn col_unit;
    @FXML TableColumn col_material;
    @FXML TableColumn col_manufacture;
    @FXML TableColumn col_material_pro;
    @FXML TableColumn col_customer_no;
    @FXML TableColumn col_create_no;
    @FXML TableColumn col_create_date;
    @FXML TableColumn col_remark;

//=============================== 产品 ================================
    @FXML private TableView tableViewProduct; //产品基本查询
    @FXML private TableColumn depid; //id
    @FXML private TableColumn findproductid; //产品基本编号
    @FXML private TableColumn findproductname; //产品基本名称
    @FXML private TableColumn findprotype; //产品类型
    @FXML private TableColumn findnormprice; //产品标准售价
    @FXML private TableColumn findlowprice; //产品最低售价
    @FXML private TableColumn findsafetystock; //安全存量
    @FXML private TableColumn findremarks; //备注
    private Stage stage = new Stage();
    private static SpringFxmlLoader loader = new SpringFxmlLoader();
    int winStatus =0;

    @FXML private TextField rightorder;
    @FXML private TextField leftorder;


    // 订单编号
    private static String  orderid = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        loadData();
    }



    /**
     * 清除
     */
    @FXML
     public void clear(){
         tab_product_info.setItems(null);
         create_no.clear();
         cusotmer_no.clear();
         product_no.clear();
         material.getItems().clear();
         process.getItems().clear();
         manufacture.getItems().clear();
         create_no_end.clear();
         cusotmer_no_end.clear();
         product_no_end.clear();
         create_date.setValue(null);
         create_date_end.setValue(null);
     }

    /**
     * 查找
     */
    public void find(){
        String createNoEnd = "";
        String createNo = "";
        String customerNo = "";
        String customerNoEnd = "";
        String materialStr = "";
        String processStr = "";
        String manufactureStr = "";
        String createDate = "";
        String createDateEnd = "";
        String productNo = "";
        String productNoEnd = "";
        if(create_no_end.getText() != null){
            createNoEnd = create_no_end.getText();
        }
        if(create_no.getText() != null){
            createNo = create_no.getText();
        }
        if(cusotmer_no.getText() != null){
            customerNo = cusotmer_no.getText();
        }
        if(cusotmer_no_end.getText() != null){
            customerNoEnd = cusotmer_no_end.getText();
        }
        if(material.getSelectionModel().getSelectedItem() != null){
            materialStr = material.getSelectionModel().getSelectedItem().toString();
        }
        if(material.getSelectionModel().getSelectedItem() != null){
            processStr = process.getSelectionModel().getSelectedItem().toString();
        }
        if(material.getSelectionModel().getSelectedItem() != null){
            manufactureStr = manufacture.getSelectionModel().getSelectedItem().toString();
        }
        if(create_date.getValue() != null){
            createDate = create_date.getValue().toString();
        }
        if(create_date_end.getValue() != null){
            createDateEnd = create_date_end.getValue().toString();
        }
        if(product_no.getText() != null){
            productNo = product_no.getText();
        }
        if(product_no_end.getText() != null){
            productNoEnd = product_no_end.getText();
        }
        List<CustomerDemandGoodsInfo> list = iCustomerDemandGoodsInfoService.listCustomerDemandGoodsWhere(createNo, createNoEnd, createDate, createDateEnd, customerNo, customerNoEnd, productNo, productNoEnd, materialStr, manufactureStr, processStr);
        if(list != null){
            //todo.. 渲染数据
            loadData(list);
        }
    }

    /**
     * 初始化加载数据
     */
    private void loadData(List<CustomerDemandGoodsInfo> list){
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
            });
        }

        // 查询客户集合
        final ObservableList<CustomerDemandGoodsInfo> data = FXCollections.observableArrayList(list);
        col_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));
        col_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
        col_customer_no.setCellValueFactory(new PropertyValueFactory("customerShortCall"));
        col_demand_num.setCellValueFactory(new PropertyValueFactory("quantityDemand"));
        col_price.setCellValueFactory(new PropertyValueFactory("price"));
        col_unit.setCellValueFactory(new PropertyValueFactory("unit"));
        col_material.setCellValueFactory(new PropertyValueFactory("productNature"));
        col_manufacture.setCellValueFactory(new PropertyValueFactory("manufactureMethod"));
        col_material_pro.setCellValueFactory(new PropertyValueFactory("processMethod"));
        col_create_no.setCellValueFactory(new PropertyValueFactory("createNo"));
        col_create_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        col_remark.setCellValueFactory(new PropertyValueFactory("remark"));

        tab_product_info.setItems(data);

    }

    /**
     * 打开现有客户窗口 开始
     */
    @FXML
    public void OpenCurrentClientQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsWhereController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * 打开现有客户窗口 结束
     */
    @FXML
    public void OpenCurrentClientQueryEnd() {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsWhereControllerEnd", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 打开现有产品窗口
     */
    @FXML
    public void moreProductButtonClick(){

        stage.setTitle("现有产品基本查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/sale/product_customer_demand_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        loadMoreProduct();
    }

    /**
     * 右边按钮
     */
    public  void rightButton(){
        winStatus =1;
        moreProductButtonClick();
    }

    /**
     * 左边按钮
     */
    public  void leftButton(){
        winStatus =2;
        moreProductButtonClick();
    }

    /**
     * 现有产品查询
     */
    public void loadMoreProduct(){

        List<ProductBasic> productBasics = productBasicService.selectProductBasic();

        ObservableList<ProductBasic> list = FXCollections.observableArrayList();

        tableViewProduct.setEditable(true);

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

        tableViewProduct.setItems(list); //tableview添加list

        tableViewProduct.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductBasic>() {
            @Override
            public void changed(ObservableValue<? extends ProductBasic> observableValue, ProductBasic oldItem, ProductBasic newItem) {
                if(newItem.getIsnum()!= null && !("".equals(newItem.getIsnum()))){
                    tableViewProduct.setUserData(newItem.getId());
                }
            }
        });


        tableViewProduct.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });
    }

    public void closeSureWin(){
        long id =(long)tableViewProduct.getUserData();
        ProductBasic productBasic =  productBasicService.selectByKey(id);
        if(winStatus == 1 ){
            product_no_end.setText(productBasic.getIsnum());
        }else if(winStatus == 2){
            product_no.setText(productBasic.getIsnum());
        }

        coseWin();
    }

    public void coseWin(){
        stage.close();
    }

    /**
     * @Author BlueSky
     * @Description //建档编号（客户需求商品查询）开始
     * @Date 20:22 2018/8/15
     * @Param []
     * @return void
     **/
    @FXML
    public void CustomerDemandGoodsQueryBen(){
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsWhereController_discount_ben", this);
        pane.getChildren().setAll(loader.load("/fxml/customer/customer_demand_goods_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @Author BlueSky
     * @Description //建档编号（客户需求商品查询）结束
     * @Date 20:22 2018/8/15
     * @Param []
     * @return void
     **/
    @FXML
    public void CustomerDemandGoodsQueryEnd(){
        Stage stage = new Stage();
        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("CustomerDemandGoodsWhereController_discount_end", this);
        pane.getChildren().setAll(loader.load("/fxml/customer/customer_demand_goods_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
