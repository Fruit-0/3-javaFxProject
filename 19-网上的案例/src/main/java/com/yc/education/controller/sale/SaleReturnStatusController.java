package com.yc.education.controller.sale;


import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SaleGoods;
import com.yc.education.service.sale.ISaleGoodsService;
import com.yc.education.util.EditCell;
import com.yc.education.util.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.crypto.Data;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 销货单状态更新
 */
@Controller
public class SaleReturnStatusController extends BaseController implements Initializable {

    @Autowired
    ISaleGoodsService iSaleGoodsService;

    @FXML public TextField customer_no; // 客户编号
    @FXML public TextField customer_short; // 客户简称称
    @FXML ComboBox audit_status;
    @FXML ComboBox delivery_status;
    @FXML ComboBox financial_status;
    @FXML ComboBox rotary_status;
    @FXML DatePicker made_date_ben;
    @FXML DatePicker made_date_end;

    @FXML TableView tab_product;

    @FXML TableColumn col_id;
    @FXML TableColumn col_no;
    @FXML TableColumn col_date;
    @FXML TableColumn col_customer_no;
    @FXML TableColumn col_customer_short;
    @FXML TableColumn col_need_back;
    @FXML TableColumn col_payment;
    @FXML TableColumn col_back;
    @FXML TableColumn col_back_date;
    @FXML TableColumn col_financial;
    @FXML TableColumn col_order_status;
    @FXML TableColumn col_delivery;


    Stage stage = new Stage();
    // 日期格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINA);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime localDate = LocalDateTime.now();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        audit_status.getItems().addAll("已审核","未审核"); // 审核状态
        audit_status.getSelectionModel().select(0);
        financial_status.getItems().addAll("已收款","未收款"); //财务复核状态
        financial_status.getSelectionModel().select(0);
        rotary_status.getItems().addAll("需回传","不回传"); //回传状态
        rotary_status.getSelectionModel().select(0);
        setComboBox(1L,delivery_status,0); // 发货状态
    }

    /**
     * 销货状态查询
     */
    @FXML
    private void selectByWhere(){
        String customerno = customer_no.getText();
        LocalDate bentime = made_date_ben.getValue();
        LocalDate endtime = made_date_end.getValue();
        String bentimeStr = "";
        String endtimeStr = "";
        if(customerno == null){
            customerno = "";
        }
        if(bentime != null){
            bentimeStr = bentime.toString();
        }
        if(endtime != null){
            endtimeStr = endtime.toString();
        }
        String verifystate = audit_status.getSelectionModel().getSelectedItem().toString();
        if("已审核".equals(verifystate)){
            verifystate = "1";
        }else{
            verifystate = "0";
        }
        List<SaleGoods> list = iSaleGoodsService.listSaleGoodsByStates(customerno, bentimeStr, endtimeStr, verifystate, "", "", "");
        tab_product.getItems().clear();
        if(list != null){
            generalProductTab(FXCollections.observableArrayList(list));
        }
    }

    /**
     * 给产品tableview加载数据
     * @param productPropertyList
     */
    private void generalProductTab(ObservableList<SaleGoods> productPropertyList){
        tab_product.setEditable(true);

//        col_no.setCellFactory(column -> EditCell.createStringEditCell());
//        col_date.setCellFactory(column -> EditCell.createStringEditCell());
//        col_customer_no.setCellFactory(column -> EditCell.createStringEditCell());
//        col_customer_short.setCellFactory(column -> EditCell.createStringEditCell());
//        col_need_back.setCellFactory(column -> EditCell.createStringEditCell());
//        col_payment.setCellFactory(column -> EditCell.createStringEditCell());
//        col_back.setCellFactory(column -> EditCell.createStringEditCell());
//        col_back_date.setCellFactory(column -> EditCell.createStringEditCell());
//        col_financial.setCellFactory(column -> EditCell.createStringEditCell());
//        col_order_status.setCellFactory(column -> EditCell.createStringEditCell());
//        col_delivery.setCellFactory(column -> EditCell.createStringEditCell());


        final ObservableList<SaleGoods> dataProperty = FXCollections.observableArrayList(productPropertyList);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_no.setCellValueFactory(new PropertyValueFactory<>("saleNo"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        col_date.setCellFactory(column -> {
            TableCell<SaleGoods, Date> cell = new TableCell<SaleGoods, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        if(item != null)
                            this.setText(format.format(item));
                    }
                }
            };
            return cell;
        });
        col_customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        col_customer_short.setCellValueFactory(new PropertyValueFactory("customerStr"));
        col_need_back.setCellValueFactory(new PropertyValueFactory(""));
        col_payment.setCellValueFactory(new PropertyValueFactory("payment"));
        col_back.setCellValueFactory(new PropertyValueFactory(""));
        col_back_date.setCellValueFactory(new PropertyValueFactory(""));
        col_financial.setCellValueFactory(new PropertyValueFactory(""));
        col_order_status.setCellValueFactory(new PropertyValueFactory("orderAudit"));
        col_delivery.setCellValueFactory(new PropertyValueFactory(""));

        tab_product.setItems(dataProperty);
    }


    /**
     * @return void
     * @Author BlueSky
     * @Description //TODO  打开窗口--现有客户查询
     * @Date 20:22 2018/8/15
     * @Param []
     **/
    @FXML
    public void OpenCurrentClientQuery() {
        SpringFxmlLoader loader = new SpringFxmlLoader();

        Pane pane = new Pane();

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("SaleReturnStatusController", this);

        pane.getChildren().setAll(loader.load("/fxml/customer/current_client_query_mini.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

}
