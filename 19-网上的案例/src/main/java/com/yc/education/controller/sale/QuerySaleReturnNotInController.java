package com.yc.education.controller.sale;

import com.yc.education.controller.BaseController;
import com.yc.education.model.sale.SaleGoods;
import com.yc.education.model.sale.SaleGoodsProduct;
import com.yc.education.service.sale.ISaleGoodsProductService;
import com.yc.education.service.sale.ISaleGoodsService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 业务查询 - 销退未入库
 */
@Controller
public class QuerySaleReturnNotInController extends BaseController implements Initializable {


    @Autowired
    ISaleGoodsService iSaleGoodsService;
    @Autowired
    ISaleGoodsProductService iSaleGoodsProductService;


    @FXML TableView tableview;

    @FXML TableColumn col_id;
    @FXML TableColumn col_order_no; // 订单编号
    @FXML TableColumn col_date; // 退货日期
    @FXML TableColumn col_customer_no; // 客户编号
    @FXML TableColumn col_customer_name; // 客户姓名
    @FXML TableColumn col_reason; // 退货原因
    @FXML TableColumn col_operation; // 操作

    @FXML TableView tableview_product;

    @FXML TableColumn colp_id;
    @FXML TableColumn colp_product_no; // 产品编号
    @FXML TableColumn colp_product_name; // 产品名称
    @FXML TableColumn colp_return_num; // 退货数量
    @FXML TableColumn colp_stock_num;  //入库数量


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

        // Button
        Callback<TableColumn<SaleGoods, String>, TableCell<SaleGoods, String>> buttonFactory
                = new Callback<TableColumn<SaleGoods, String>, TableCell<SaleGoods, String>>() {
            @Override
            public TableCell call(final TableColumn<SaleGoods, String> param) {
                final TableCell<SaleGoods, String> cell = new TableCell<SaleGoods, String>() {

                    final Button btn1 = new Button("导出");

                    @Override
                    public void updateItem(String ite, boolean empty) {
                        super.updateItem(ite, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn1.setOnAction((ActionEvent t)
                                    -> {
                                int selectdIndex = getTableRow().getIndex();
                                System.out.println(selectdIndex);
                                // 参数
                            });
                            btn1.setMaxWidth(40);
                            btn1.setTextFill(Color.WHITE);
                            setGraphic(btn1);
                            setText(null);
                        }
                    }
                };
                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        };
        col_operation.setCellFactory(buttonFactory);

        // 查询客户集合
        final ObservableList<SaleGoods> data = FXCollections.observableArrayList(list);
        col_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_order_no.setCellValueFactory(new PropertyValueFactory("saleNo"));
        col_date.setCellValueFactory(new PropertyValueFactory("createDateStr"));
        col_customer_no.setCellValueFactory(new PropertyValueFactory("customerNo"));
        col_customer_name.setCellValueFactory(new PropertyValueFactory("customerNoStr"));
        col_reason.setCellValueFactory(new PropertyValueFactory("payment"));

        tableview.setItems(data);

        // 选择行 保存数据
        tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleGoods>() {
            @Override
            public void changed(ObservableValue<? extends SaleGoods> observableValue, SaleGoods oldItem, SaleGoods newItem) {
                if(newItem.getId() != null && !("".equals(newItem.getId())) && newItem.getCreateDate() != null && !("".equals(newItem.getCreateDate()))){

                    List<SaleGoodsProduct> listProduct = iSaleGoodsProductService.listSaleGoodsProduct(newItem.getId().toString());
                    // 查询客户集合
                    final ObservableList<SaleGoodsProduct> data = FXCollections.observableArrayList(listProduct);
                    colp_id.setCellValueFactory(new PropertyValueFactory("id"));
                    colp_product_no.setCellValueFactory(new PropertyValueFactory("productNo"));
                    colp_product_name.setCellValueFactory(new PropertyValueFactory("productName"));
                    colp_return_num.setCellValueFactory(new PropertyValueFactory("num"));
                    colp_stock_num.setCellValueFactory(new PropertyValueFactory(""));

                    tableview_product.setItems(data);
                }
            }
        });
    }
}
