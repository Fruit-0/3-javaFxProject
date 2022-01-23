package com.yc.education.controller.basic;

import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.CodingProperty;
import com.yc.education.model.basic.ProductBasic;
import com.yc.education.model.basic.SupplierBasic;
import com.yc.education.model.customer.Customer;
import com.yc.education.service.basic.ProductBasicService;
import com.yc.education.service.basic.SupplierBasicService;
import com.yc.education.service.customer.ICustomerBasicService;
import com.yc.education.service.customer.ICustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LongStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @ClassName CodingBasicController
 * @Description TODO 资料编码维护   维护范围(供应商、运输商、客户、产品)
 * @Author QuZhangJing
 * @Date 2018-08-14 13:47
 * @Version 1.0
 */
@Controller
public class CodingBasicController extends BaseController implements Initializable {

    @Autowired
    private SupplierBasicService supplierBasicService; //供应商0
    @Autowired
    private ICustomerBasicService iCustomerBasicService;//客户基本信息
    @Autowired
    private ICustomerService iCustomerService; //客户信息


    @Autowired
    private ProductBasicService productBasicService; //产品3



    @FXML private ComboBox codeType;

    @FXML private TextField isNum; //源编码

    @FXML private TextField newIsNum; //新编码



    @FXML private TableView itemsView;

    @FXML private TableColumn ck;

    @FXML private TableColumn code;

    @FXML private TableColumn newCode;

    @FXML private TableColumn name;

    @FXML private TableColumn addDate;

    @FXML private TableColumn addPeople;

    @FXML private TableColumn stopDate;

    @FXML private TableColumn remarks;


    /**
     * 编码设置-TabelView数据绑定
     */
    private ObservableList<CodingProperty> codingProperties = FXCollections.observableArrayList();



    /**
     * 搜索
     */
    public void search(){

        int type = codeType.getSelectionModel().getSelectedIndex();

        /*System.err.println("type:"+type);*/
        if(type==0){
            searchSupplier(isNum.getText());
        }else if(type==1){

        }else if(type==2){
            searchCustomer(isNum.getText());
        }else if(type==3){
            searchProduct(isNum.getText());
        }






    }


    public void searchSupplier(String isNum){

        SupplierBasic supplierBasic = supplierBasicService.selectSupplierBasicByIsnum(isNum);

        itemsView.setEditable(false);

            /*ck.setCellFactory(CheckBoxTableCell.forTableColumn(ck));*/
            code.setCellFactory(TextFieldTableCell.forTableColumn());
            newCode.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            addDate.setCellFactory(TextFieldTableCell.forTableColumn());
            addPeople.setCellFactory(TextFieldTableCell.forTableColumn());
            stopDate.setCellFactory(TextFieldTableCell.forTableColumn());
            remarks.setCellFactory(TextFieldTableCell.forTableColumn());

            ck.setCellValueFactory(new PropertyValueFactory("id"));
            code.setCellValueFactory(new PropertyValueFactory("idnum"));
             newCode.setCellValueFactory(new PropertyValueFactory("newcode"));
            name.setCellValueFactory(new PropertyValueFactory("supname"));
            addDate.setCellValueFactory(new PropertyValueFactory("adddate"));
            addPeople.setCellValueFactory(new PropertyValueFactory("addpeople"));
            stopDate.setCellValueFactory(new PropertyValueFactory("stopdes"));
            remarks.setCellValueFactory(new PropertyValueFactory("remarks"));
        codingProperties.clear();
        if(supplierBasic != null) {

            CodingProperty codingProperty = new CodingProperty(supplierBasic.getId(),supplierBasic.getIdnum(),"",supplierBasic.getSupname(),supplierBasic.getAdddate(),supplierBasic.getAddpeople(),supplierBasic.getStopdes(),supplierBasic.getRemarks());

            codingProperties.add(codingProperty);
        }
        itemsView.setItems(codingProperties);


    }


    public void searchCustomer(String isNum){
          Customer customer = iCustomerService.getCustomer(isNum);

    }

    public void searchProduct(String isNum){

        ProductBasic productBasic = productBasicService.selectProductBasicByIsnum(isNum);

        itemsView.setEditable(false);

            /*ck.setCellFactory(CheckBoxTableCell.forTableColumn(ck));*/
            code.setCellFactory(TextFieldTableCell.forTableColumn());
            newCode.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            addDate.setCellFactory(TextFieldTableCell.forTableColumn());
            addPeople.setCellFactory(TextFieldTableCell.forTableColumn());
            stopDate.setCellFactory(TextFieldTableCell.forTableColumn());
            remarks.setCellFactory(TextFieldTableCell.forTableColumn());

            ck.setCellValueFactory(new PropertyValueFactory("id"));
            code.setCellValueFactory(new PropertyValueFactory("idnum"));
            newCode.setCellValueFactory(new PropertyValueFactory("newcode"));
            name.setCellValueFactory(new PropertyValueFactory("supname"));
            addDate.setCellValueFactory(new PropertyValueFactory("adddate"));
            addPeople.setCellValueFactory(new PropertyValueFactory("addpeople"));
            stopDate.setCellValueFactory(new PropertyValueFactory("stopdes"));
            remarks.setCellValueFactory(new PropertyValueFactory("remarks"));
        codingProperties.clear();
            if(productBasic != null) {
                CodingProperty codingProperty = new CodingProperty(productBasic.getId(),productBasic.getIsnum(),"",productBasic.getProname(),productBasic.getAdddate(),productBasic.getAddpeople(),productBasic.getStopdes(),productBasic.getRemarks());
            codingProperties.add(codingProperty);
        }
        itemsView.setItems(codingProperties);
    }


    /**
     * 清除
     */
    public void clear(){



    }


    /**
     * 替换
     */
    public void replace(){

       String nisnum = newIsNum.getText();

       if(nisnum != null && !"".equals(nisnum)){

           int type = codeType.getSelectionModel().getSelectedIndex();

           if(type==0){

               SupplierBasic supplierBasic = supplierBasicService.selectSupplierBasicByIsnum(nisnum);

               if(supplierBasic == null){

                   codingProperties.forEach(codingProperty -> {

                       codingProperty.setNewcode(nisnum);

                       SupplierBasic supplierBasic1 = new SupplierBasic(codingProperty.getId(),nisnum);

                       supplierBasicService.updateNotNull(supplierBasic1);

                   });
               }else {
                   alert_informationDialog("编码已存在!");
               }



           }else if(type==1){

           }else if(type==2){
               searchCustomer(isNum.getText());
           }else if(type==3){

              ProductBasic productBasic =  productBasicService.selectProductBasicByIsnum(nisnum);

              if( productBasic == null){

                  codingProperties.forEach(codingProperty -> {

                      codingProperty.setNewcode(nisnum);

                      ProductBasic productBasic1 = new ProductBasic(codingProperty.getId(),nisnum);

                      productBasicService.updateNotNull(productBasic1);

                  });

              }else {
                  alert_informationDialog("编码已存在!");
              }


           }

       }


    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String newStr = location.toString();

        int index = newStr.indexOf("coding_basic");

        if(index != -1){

            codeType.getItems().addAll(
                    "供应商","运输商","客户","产品"
            );
            codeType.getSelectionModel().select(0);

        }


    }





}
