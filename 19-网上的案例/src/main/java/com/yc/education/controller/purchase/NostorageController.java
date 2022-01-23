package com.yc.education.controller.purchase;

import com.yc.education.controller.BaseController;
import com.yc.education.model.purchase.NostorageProperty;
import com.yc.education.model.purchase.PurchaseProduct;
import com.yc.education.service.purchase.PurchaseOrdersService;
import com.yc.education.service.purchase.PurchaseProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName NostorageController
 * @Description TODO 业务查询---采购未入库
 * @Author QuZhangJing
 * @Date 2018/10/19 11:22
 * @Version 1.0
 */
@Controller
public class NostorageController extends BaseController implements Initializable {

    @Autowired
    private PurchaseProductService purchaseProductService;//采购产品

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;//采购订单

    @FXML private TableView findPurchaseView;
    @FXML private TableColumn purchaseorder;//采购单号
    @FXML private TableColumn createdate;   //制单日期
    @FXML private TableColumn comedate;     //预计到货日期
    @FXML private TableColumn supplierid;   //供应商编号
    @FXML private TableColumn supplierdes;  //供应商简称
    @FXML private TableColumn proid;        //产品编号
    @FXML private TableColumn proname;      //产品名称
    @FXML private TableColumn ordernum;     //订单数量
    @FXML private TableColumn stocknum;     //已入库数
    @FXML private TableColumn seeorder;     //参考单号
    @FXML private TableColumn overnum;      //结案数量
    @FXML private TableColumn onthewaynum;  //在途数量
    @FXML private TableColumn unpassnum;    //未发数量
    @FXML private TableColumn operation;    //操作


    @FXML private TextField ordersums; //订单数量

    @FXML private TextField stocksums; //已入库数

    @FXML private TextField forcesums; //结案数量

    @FXML private TextField onthewaysums; //在途数量

    @FXML private TextField unpasssums;  //未发数量



    //订单数量、已入库数、结案数量、在途数量、未发数量
    private int ordersum=0,overstock=0,forcesum=0,onthewaysum=0,unpasssum=0;

    //数据绑定
    private ObservableList<NostorageProperty> nostorageProperties = FXCollections.observableArrayList();



    public void LoadData(){

         List<PurchaseProduct> purchaseProducts = purchaseProductService.findPurchaseProductNotStock();


        purchaseorder.setCellValueFactory(new PropertyValueFactory("orders"));
        createdate.setCellValueFactory(new PropertyValueFactory("createdate"));
        comedate.setCellValueFactory(new PropertyValueFactory("comedate"));
        supplierid.setCellValueFactory(new PropertyValueFactory("suppliernum"));
        supplierdes.setCellValueFactory(new PropertyValueFactory("supplierdes"));
        proid.setCellValueFactory(new PropertyValueFactory("proid"));
        proname.setCellValueFactory(new PropertyValueFactory("proname"));
        ordernum.setCellValueFactory(new PropertyValueFactory("ordernum"));
        stocknum.setCellValueFactory(new PropertyValueFactory("stocknum"));
        seeorder.setCellValueFactory(new PropertyValueFactory("seeorder"));
        overnum.setCellValueFactory(new PropertyValueFactory("overnum"));
        onthewaynum.setCellValueFactory(new PropertyValueFactory("onthewaynum"));
        unpassnum.setCellValueFactory(new PropertyValueFactory("unpassnum"));
        operation.setCellValueFactory(new PropertyValueFactory("operation"));


        ordersum = 0;

        overstock = 0;

        forcesum = 0;

        onthewaysum = 0;

        unpasssum = 0;

        nostorageProperties.clear();

        if(purchaseProducts.size()>0){
                for(PurchaseProduct purchaseProduct : purchaseProducts){

                    int unpass =  purchaseProduct.getNum()-purchaseProduct.getStockover()-purchaseProduct.getForcenum()-purchaseProduct.getOntheway();

                    ordersum += purchaseProduct.getNum();

                    overstock += purchaseProduct.getStockover();

                    forcesum += purchaseProduct.getForcenum();

                    onthewaysum += purchaseProduct.getOntheway();

                    unpasssum += unpass;

                    NostorageProperty nostorageProperty = new NostorageProperty(purchaseProduct.getOrders(),
                            new SimpleDateFormat("yyyy-MM-dd").format(purchaseProduct.getPurchaseOrders().getCreatedate()),
                            new SimpleDateFormat("yyyy-MM-dd").format(purchaseProduct.getPurchaseOrders().getComedate()),
                            purchaseProduct.getPurchaseOrders().getSuppliernum(),
                            purchaseProduct.getPurchaseOrders().getSupplierdes(),
                            purchaseProduct.getProorders(),
                            purchaseProduct.getProname(),
                            purchaseProduct.getNum(),
                            purchaseProduct.getStockover(),
                            purchaseProduct.getPurchaseOrders().getSeeorders(),
                            purchaseProduct.getForcenum(),
                            purchaseProduct.getOntheway(),
                            unpass,
                            "操作"
                            );
                    nostorageProperties.add(nostorageProperty);
                }
        }

        ordersums.setText(ordersum+"");
        stocksums.setText(overstock+"");
        forcesums.setText(forcesum+"");
        onthewaysums.setText(onthewaysum+"");
        unpasssums.setText(unpasssum+"");

        findPurchaseView.setItems(nostorageProperties);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadData();
    }

}
