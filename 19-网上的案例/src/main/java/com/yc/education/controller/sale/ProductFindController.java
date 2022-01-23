package com.yc.education.controller.sale;

import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.basic.ProductBasic;

import com.yc.education.service.IClickItem;
import com.yc.education.service.basic.ProductBasicService;
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
import java.util.*;

/**
 * 销售--产品查询小窗口
 */
@Controller
public class ProductFindController extends BaseController implements Initializable {

    @Autowired
    private ProductBasicService productBasicService; //产品 Service


    Stage stage = new Stage();
    private static SpringFxmlLoader loader = new SpringFxmlLoader();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





}
