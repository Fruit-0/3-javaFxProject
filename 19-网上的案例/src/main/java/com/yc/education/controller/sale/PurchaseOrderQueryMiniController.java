package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SalePurchaseOrder;
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
public class PurchaseOrderQueryMiniController extends BaseController implements Initializable {

    @Autowired
    ISalePurchaseOrderService iSalePurchaseOrderService;

    @FXML
    Button client_sure;

    @FXML TableView tableView;

    @FXML TableColumn id;
    @FXML TableColumn order_no;
    @FXML TableColumn order_date;
    @FXML TableColumn customer_no;
    @FXML TableColumn customer_shortname;
    @FXML TableColumn made_people;
    @FXML TableColumn contact;

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
        List<SalePurchaseOrder> list = iSalePurchaseOrderService.listSalePurchaseOrderAll();
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
            });
        }

        // 查询客户集合
        final ObservableList<SalePurchaseOrder> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory("id"));
        order_no.setCellValueFactory(new PropertyValueFactory("orderNo"));
        order_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        customer_shortname.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        made_people.setCellValueFactory(new PropertyValueFactory("madePeople"));
        contact.setCellValueFactory(new PropertyValueFactory("contact"));

        tableView.setItems(data);

        // 选择行 保存数据
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalePurchaseOrder>() {
            @Override
            public void changed(ObservableValue<? extends SalePurchaseOrder> observableValue, SalePurchaseOrder oldItem, SalePurchaseOrder newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId()))){
                    PurchaseOrderQueryMiniController.orderid = newItem.getId()+"";
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
        StageManager.CONTROLLER.remove("PurchaseOrderControllerNo");
        stage.close();
    }

    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){
        // 报价单
        PurchaseOrderController online = (PurchaseOrderController) StageManager.CONTROLLER.get("PurchaseOrderControllerNo");
        if(online != null && orderid != null && !"".equals(orderid)){
            SalePurchaseOrder purchaseOrder = iSalePurchaseOrderService.selectByKey(Long.valueOf(orderid));
            if(purchaseOrder != null){
                online.setBasicVal(purchaseOrder);
            }
        }
        closeWin();
    }
}
