package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.DataSetting;
import com.yc.education.model.sale.SaleGoods;

import com.yc.education.model.sale.SaleReturnGoods;
import com.yc.education.service.DataSettingService;
import com.yc.education.service.sale.ISaleReturnGoodsService;
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
public class SaleReturnQueryMiniController extends BaseController implements Initializable {

    @Autowired
    ISaleReturnGoodsService iSaleReturnGoodsService;

    @Autowired
    private DataSettingService dataSettingService;

    @FXML
    Button client_sure;

    @FXML TableView tableView;

    @FXML TableColumn id;
    @FXML TableColumn order_no;
    @FXML TableColumn order_date;
    @FXML TableColumn customer_no;
    @FXML TableColumn customer_name;
    @FXML TableColumn business_leader;
    @FXML TableColumn tax;
    @FXML TableColumn reason;
    @FXML TableColumn remark;

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
        List<SaleReturnGoods> list = iSaleReturnGoodsService.listSaleReturnGoodsAll();
        List<DataSetting> settings = dataSettingService.findDataSetting(19L);
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                if(p.getReturnReason() != null && p.getReturnReason() != 0 && settings != null){
                    settings.forEach(k->{
                        if(k.getSort() == p.getReturnReason()){
                            p.setReasonStr(k.getTitle());
                            return;
                        }
                    });
                }
                if(p.getTax() != null && p.getTax() != 0){
                    if(p.getTax() == 1){
                        p.setTaxStr("已税");
                    }else{
                        p.setTaxStr("未税");
                    }
                }
            });
        }

        // 查询客户集合
        final ObservableList<SaleReturnGoods> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory("id"));
        order_no.setCellValueFactory(new PropertyValueFactory("pinbackNo"));
        order_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        customer_name.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        business_leader.setCellValueFactory(new PropertyValueFactory("businessLeaderStr"));
        tax.setCellValueFactory(new PropertyValueFactory("taxStr"));
        reason.setCellValueFactory(new PropertyValueFactory("reasonStr"));
        remark.setCellValueFactory(new PropertyValueFactory("remark"));

        tableView.setItems(data);

        // 选择行 保存数据
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleReturnGoods>() {
            @Override
            public void changed(ObservableValue<? extends SaleReturnGoods> observableValue, SaleReturnGoods oldItem, SaleReturnGoods newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId()))){
                    SaleReturnQueryMiniController.orderid = newItem.getId()+"";
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
        StageManager.CONTROLLER.remove("SaleReturnControllerNo");
        stage.close();
    }

    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){
        // 报价单
        SaleReturnController controller = (SaleReturnController) StageManager.CONTROLLER.get("SaleReturnControllerNo");
        if(controller != null && orderid != null && !"".equals(orderid)){
            SaleReturnGoods order = iSaleReturnGoodsService.selectByKey(Long.valueOf(orderid));
            if(order != null){
                controller.setBasicVal(order);
            }
        }
        closeWin();
    }
}
