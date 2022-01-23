package com.yc.education.controller.stock;

import com.yc.education.controller.BaseController;
import com.yc.education.controller.sale.OnlineOrderController;
import com.yc.education.model.sale.SaleOnlineOrder;
import com.yc.education.model.stock.StockOutSale;
import com.yc.education.service.sale.ISaleOnlineOrderService;
import com.yc.education.service.stock.IStockOutSaleService;
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

/**
 * 销售出库单小窗口
 */
@Controller
public class SaleOutboundMiniController extends BaseController implements Initializable {

    @Autowired
    IStockOutSaleService iStockOutSaleService;

    @FXML
    Button client_sure;

    @FXML TableView tableView;

    @FXML TableColumn id;
    @FXML TableColumn order_no;
    @FXML TableColumn order_date;
    @FXML TableColumn customer_no;
    @FXML TableColumn customer_shortname;
    @FXML TableColumn made_people;
    @FXML TableColumn order_audit;



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
        List<StockOutSale> list = iStockOutSaleService.listStockOutSaleAll();
        if(list != null){
            list.forEach(p->{
                p.setOrderDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                if(p.getOrderAudit() == null){
                    p.setOrderAudit(false);
                }
            });
        }

        // 查询客户集合
        final ObservableList<StockOutSale> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory("id"));
        order_no.setCellValueFactory(new PropertyValueFactory("outboundNo"));
        order_date.setCellValueFactory(new PropertyValueFactory("orderDateStr"));//映射
        customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        customer_shortname.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        made_people.setCellValueFactory(new PropertyValueFactory("madePeople"));
        order_audit.setCellValueFactory(new PropertyValueFactory("orderAudit"));

        tableView.setItems(data);

        // 选择行 保存数据
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockOutSale>() {
            @Override
            public void changed(ObservableValue<? extends StockOutSale> observableValue, StockOutSale oldItem, StockOutSale newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId()))){
                    SaleOutboundMiniController.orderid = newItem.getId()+"";
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
        StageManager.CONTROLLER.remove("SaleOutboundOrderControllerMini");
        stage.close();
    }

    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){
        // 报价单
        SaleOutboundOrderController order = (SaleOutboundOrderController) StageManager.CONTROLLER.get("SaleOutboundOrderControllerMini");
        if(order != null && orderid != null && !"".equals(orderid)){
            StockOutSale stockOutSale = iStockOutSaleService.selectByKey(Long.valueOf(orderid));
            if(stockOutSale != null){
                order.setBasicVal(stockOutSale);
            }
        }
        closeWin();
    }
}
