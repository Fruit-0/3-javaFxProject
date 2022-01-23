package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SaleOfferProduct;
import com.yc.education.model.sale.SaleQuotation;
import com.yc.education.service.sale.ISaleOfferProductService;
import javafx.beans.value.ChangeListener;
import com.yc.education.service.sale.ISaleQuotationService;
import com.yc.education.util.StageManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class QuotationQueryMiniController extends BaseController implements Initializable {


    @Autowired
    ISaleQuotationService iSaleQuotationService;
    @Autowired
    ISaleOfferProductService iSaleOfferProductService;

    @FXML
    Button client_sure;

    @FXML TableView tableView;

    @FXML TableColumn id;
    @FXML TableColumn offer_no;
    @FXML TableColumn create_date;
    @FXML TableColumn customer_no;
    @FXML TableColumn customer_type;
    @FXML TableColumn customer_shortname;
    @FXML TableColumn business_leader;
    @FXML TableColumn inquiry_date;
    @FXML TableColumn valid_until;
    @FXML TableColumn contact;
    @FXML TableColumn phone;
    @FXML TableColumn fax;



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
        List<SaleQuotation> list = iSaleQuotationService.listSaleQuotationAll(1, Integer.MAX_VALUE);
        if(list != null){
            list.forEach(p->{
                p.setCreateDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getCreateDate()));
                p.setEnquiryDateStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getEnquiryDate()));
                p.setValidUntilStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getValidUntil()));
            });
        }

        // 查询客户集合
        final ObservableList<SaleQuotation> data = FXCollections.observableArrayList(list);
        id.setCellValueFactory(new PropertyValueFactory("id"));
        offer_no.setCellValueFactory(new PropertyValueFactory("offerNo"));
        create_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));//映射
        customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        customer_type.setCellValueFactory(new PropertyValueFactory("customerCategory"));
        customer_shortname.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        business_leader.setCellValueFactory(new PropertyValueFactory("businessStr"));
        inquiry_date.setCellValueFactory(new PropertyValueFactory("enquiryDateStr"));
        valid_until.setCellValueFactory(new PropertyValueFactory("validUntilStr"));
        contact.setCellValueFactory(new PropertyValueFactory("contact"));
        phone.setCellValueFactory(new PropertyValueFactory("telephone"));
        fax.setCellValueFactory(new PropertyValueFactory("fax"));

        tableView.setItems(data);

        // 选择行 保存数据
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleQuotation>() {
            @Override
            public void changed(ObservableValue<? extends SaleQuotation> observableValue, SaleQuotation oldItem, SaleQuotation newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId()))){
                    QuotationQueryMiniController.orderid = newItem.getId()+"";
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
        StageManager.CONTROLLER.remove("QuotationControllerNo");
        stage.close();
    }

    //确定并关闭当前窗体
    @FXML
    public void closeSureWin(){
        // 报价单
        QuotationController controller = (QuotationController) StageManager.CONTROLLER.get("QuotationControllerNo");
        if(controller != null){
            controller.orderid.setText(orderid);
        }
        closeWin();
    }


}
