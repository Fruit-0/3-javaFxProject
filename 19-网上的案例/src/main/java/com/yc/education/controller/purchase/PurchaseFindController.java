package com.yc.education.controller.purchase;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.purchase.PurchaseFindProperty;
import com.yc.education.model.purchase.PurchaseProduct;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.purchase.PurchaseOrdersService;
import com.yc.education.service.purchase.PurchaseProductService;
import com.yc.education.util.NumberUtil;
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
 * @ClassName PurchaseFindController
 * @Description TODO  最新采购查询
 * @Author QuZhangJing
 * @Date 2018/10/23 13:57
 * @Version 1.0
 */
@Controller
public class PurchaseFindController extends BaseController implements Initializable {

    @Autowired
    private PurchaseProductService purchaseProductService;//采购产品
    @Autowired
    private PurchaseOrdersService purchaseOrdersService; //采购订单
    @Autowired
    private ProductBasicService productBasicService; //产品 Service


    @FXML private TableView findPurchaseProduct;
    @FXML private TableColumn pronum; //产品编号
    @FXML private TableColumn proname; //产品名称
    @FXML private TableColumn protype; //产品大类
    @FXML private TableColumn purchasenum; //采购数量
    @FXML private TableColumn proremark;  //产品备注
    @FXML private TableColumn purchaseorder; //采购单号
    @FXML private TableColumn seeorder;  //参考单号
    @FXML private TableColumn createdate; //制单日期



    @FXML private TextField rightorder;
    @FXML private TextField leftorder;
    @FXML private TextField rightname;
    @FXML private TextField leftname;
    @FXML private ComboBox rightcom;
    @FXML private ComboBox leftcom;
    @FXML private DatePicker rightdate;
    @FXML private DatePicker leftdate;
    @FXML private Button querybutton;



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

    int winStatus =0;

    private static SpringFxmlLoader loader = new SpringFxmlLoader();



    public  void rightButton(){
        winStatus =1;
        moreProductButtonClick();
    }

    public  void leftButton(){
        winStatus =2;
        moreProductButtonClick();
    }


    public void moreProductButtonClick(){

        stage.setTitle("现有产品基本查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/purchase/newproduct_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
        /*stage.initStyle(StageStyle.UNDECORATED);
        DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
        loadMoreProduct();
    }


    /**
     * 现有产品查询
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
            rightorder.setText(productBasic.getIsnum());
        }else if(winStatus == 2){
            leftorder.setText(productBasic.getIsnum());
        }

        coseWin();
    }

    public void coseWin(){
        stage.close();
    }



    private ObservableList<PurchaseFindProperty> purchaseFindProperties = FXCollections.observableArrayList();


    public void loadDate(String sisnum,String eisnum,
                         String sproname,String eproname,
                         String stype,String etype,
                         String sdate,String edate){

        List<PurchaseProduct>  purchaseProducts = purchaseProductService.findPurchaseProductNew(sisnum,eisnum,sproname,eproname,stype,etype,sdate,edate);


        pronum.setCellValueFactory(new PropertyValueFactory("pronum"));
        proname.setCellValueFactory(new PropertyValueFactory("proname"));
        protype.setCellValueFactory(new PropertyValueFactory("protype"));
        purchasenum.setCellValueFactory(new PropertyValueFactory("purchasenum"));
        proremark.setCellValueFactory(new PropertyValueFactory("proremark"));
        purchaseorder.setCellValueFactory(new PropertyValueFactory("purchaseorder"));
        seeorder.setCellValueFactory(new PropertyValueFactory("seeorder"));
        createdate.setCellValueFactory(new PropertyValueFactory("createdate"));


        purchaseFindProperties.clear();

        if(purchaseProducts.size()>0){

            for ( PurchaseProduct purchaseProduct :purchaseProducts){

                PurchaseFindProperty purchaseFindProperty = new PurchaseFindProperty(purchaseProduct.getProorders(),purchaseProduct.getProname(),"",purchaseProduct.getNum()+"",purchaseProduct.getRemarks(),purchaseProduct.getPurchaseOrders().getOrders(),purchaseProduct.getPurchaseOrders().getSeeorders(), new SimpleDateFormat("yyyy-MM-dd").format(purchaseProduct.getPurchaseOrders().getCreatedate()));

                purchaseFindProperties.add(purchaseFindProperty);
            }

        }
        findPurchaseProduct.setItems(purchaseFindProperties);

    }


            //查询
          public  void  submitButton(){

            loadDate(rightorder.getText(),leftorder.getText(),rightname.getText(),leftname.getText(),"","",rightdate.getValue().toString(),leftdate.getValue().toString());

          }

          //清除
          public void clearButton(){
                rightorder.setText(NumberUtil.NULLSTRING);

                leftorder.setText(NumberUtil.NULLSTRING);

                rightname.setText(NumberUtil.NULLSTRING);

                leftname.setText(NumberUtil.NULLSTRING);

                rightcom.getSelectionModel().select(-1);

                leftcom.getSelectionModel().select(-1);

                rightdate.setValue(null);

                leftdate.setValue(null);

          }




    @Override
    public void initialize(URL location, ResourceBundle resources){
        loadDate("","","","","","","","");

        setComboBox(6,rightcom,-1);
        setComboBox(6,leftcom,-1);

    }

}
