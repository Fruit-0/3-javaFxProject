package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SaleGoods;
import com.yc.education.model.sale.SalePurchaseOrder;
import com.yc.education.service.sale.ISaleGoodsProductService;
import com.yc.education.service.sale.ISaleGoodsService;
import com.yc.education.service.sale.ISalePurchaseOrderService;
import com.yc.education.util.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class SaleGoodsQueryMiniController extends BaseController implements Initializable {

    @Autowired
    ISaleGoodsService iSaleGoodsService;

    @FXML
    Button client_sure;

    @FXML TableView tableView;

    @FXML TableColumn id;
    @FXML TableColumn order_no;
    @FXML TableColumn order_date;
    @FXML TableColumn customer_no;
    @FXML TableColumn customer_name;
    @FXML TableColumn contact;
    @FXML TableColumn business_leader;
    @FXML TableColumn tax;
    @FXML TableColumn phone;
    @FXML TableColumn fax;
    @FXML TableColumn status;

    // 订单编号
    private static String  orderid = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    /**
     * 初始化加载数据
     */
    private void loadData(){
        List<SaleGoods> list = iSaleGoodsService.listSaleGoodsAll();
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
            });
        }

        // 查询客户集合
        final ObservableList<SaleGoods> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory("id"));
        order_no.setCellValueFactory(new PropertyValueFactory("saleNo"));
        order_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        customer_name.setCellValueFactory(new PropertyValueFactory("customerName"));
        contact.setCellValueFactory(new PropertyValueFactory("contact"));
        business_leader.setCellValueFactory(new PropertyValueFactory("businessLeader"));
        tax.setCellValueFactory(new PropertyValueFactory("tax"));
        phone.setCellValueFactory(new PropertyValueFactory("phone"));
        fax.setCellValueFactory(new PropertyValueFactory("fax"));
        status.setCellValueFactory(new PropertyValueFactory("orderAudit"));

        tableView.setItems(data);

        // 选择行 保存数据
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleGoods>() {
            @Override
            public void changed(ObservableValue<? extends SaleGoods> observableValue, SaleGoods oldItem, SaleGoods newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId()))){
                    SaleGoodsQueryMiniController.orderid = newItem.getId()+"";
                }
            }
        });

        tableView.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeSureWin();
            }
        });
    }

    //关闭当前窗体
    @FXML
    public void closeWin(){
        Stage stage=(Stage)client_sure.getScene().getWindow();
        StageManager.CONTROLLER.remove("SaleGoodsControllerNo");
        stage.close();
    }

    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){
        // 报价单
        SaleGoodsController controller = (SaleGoodsController) StageManager.CONTROLLER.get("SaleGoodsControllerNo");
        if(controller != null && orderid != null && !"".equals(orderid)){
            SaleGoods order = iSaleGoodsService.selectByKey(Long.valueOf(orderid));
            if(order != null){
                controller.setBasicVal(order);
            }
        }
        closeWin();
    }
}
