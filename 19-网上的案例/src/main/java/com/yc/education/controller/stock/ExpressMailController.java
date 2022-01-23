package com.yc.education.controller.stock;

import com.github.pagehelper.PageInfo;
import com.yc.education.constants.SpringFxmlLoader;
import com.yc.education.controller.BaseController;
import com.yc.education.model.stock.ExpressMail;
import com.yc.education.service.stock.ExpressMailService;
import com.yc.education.util.NumberUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName ExpressMailCOntroller
 * @Description TODO 快递寄件
 * @Author QuZhangJing
 * @Date 2018/11/12 16:06
 * @Version 1.0
 */
@Controller
public class ExpressMailController extends BaseController implements Initializable {

    @Autowired
    private ExpressMailService expressMailService;


    @FXML private Label fxmlStatus; //状态

    @FXML private VBox first; //第一页

    @FXML private VBox prev; //上一页

    @FXML private VBox next; //下一页

    @FXML private VBox last; //最后一页

    @FXML private VBox clearvbox; //清除

    @FXML private VBox submitvbox; //提交

    @FXML private VBox insertvbox; //新增

    @FXML private VBox updatevbox; //修改

    @FXML private VBox deletevbox; //删除

    @FXML private VBox shyes; //审核

    @FXML private VBox shno; //取消审核




    @FXML private DatePicker maildate;//寄件日期
    @FXML private TextField mailorder;//快递单号
    @FXML private ComboBox company;//快递公司

    /**
     *          快递信息
     */
    @FXML private TextField mailnum;//数量(件数
    @FXML private TextField mailweight;//重量(KG)
    @FXML private ComboBox contenttype;//托寄内容类型
    @FXML private TextField content;//托寄内容
    @FXML private ComboBox paymethodtype;//支付方式类型
    @FXML private TextField paymethod;//支付方式
    @FXML private ComboBox freighttype;//本次运费类型
    @FXML private TextField freightprice;//本次运费
    @FXML private CheckBox ispay;//是否制单时已付
    @FXML private CheckBox ismonth;//是否月付
    @FXML private TextField account;//账号
    @FXML private TextField ensure;//保介费
    @FXML private TextField ensurepoint;//账号
    @FXML private TextArea remarks;//备注
    @FXML private TextField createpeople;//建档人
    @FXML private TextField createdate;//建档日期
    @FXML private TextField updatepeople;//最后更新人
    @FXML private TextField updatedate;//最后更新日期
    @FXML private TextField shpeople;//审核人
    @FXML private TextField shdate;//审核日期

    /**
     *  寄件公司
     */

    @FXML private TextField mailid;//寄件公司编号
    @FXML private TextField mailcompany;//寄件公司
    @FXML private ComboBox mailprovince;//省
    @FXML private ComboBox mailcity;//市
    @FXML private ComboBox mailarea;//区
    @FXML private TextField mailaddress;//详细地址
    @FXML private ComboBox mailpeople;//寄件人
    @FXML private ComboBox mailphone;//联络方式



    /**
     *  收件公司
     */

    @FXML private TextField collectid;//收件公司编号
    @FXML private TextField collectdes;//收件公司名称
    @FXML private TextField collectcompany;//收件公司
    @FXML private ComboBox collectprovince;//省
    @FXML private ComboBox collectcity;//市
    @FXML private ComboBox collectarea;//区
    @FXML private TextField collectaddress;//详细地址
    @FXML private ComboBox collectpeople;//收件人
    @FXML private ComboBox collectphone;//联络方式


//     <TableView fx:id="expressMailView" prefHeight="309.0" prefWidth="600.0" VBox.vgrow="ALWAYS">
//                    <columns>
//                        <TableColumn fx:id="findmailid" maxWidth="1500.0" prefWidth="75.0" text="  " />
//                        <TableColumn fx:id="findmailorder" prefWidth="100.0" text="快递单号" />
//                        <TableColumn fx:id="findmaildate" prefWidth="100.0" text="寄件日期" />
//                        <TableColumn fx:id="findmailcompany" prefWidth="100.0" text="快递公司" />
//                        <TableColumn fx:id="findmailaddress" prefWidth="100.0" text="寄件地址" />
//                        <TableColumn fx:id="findmailremarks" prefWidth="100.0" text="备注" />



    //快递寄件订单
    @FXML private TableView expressMailView;

    @FXML private TableColumn  findmailid; //编号

    @FXML private TableColumn  findmailorder;//快递单号

    @FXML private TableColumn  findmaildate;//寄件日期

    @FXML private TableColumn  findmailcompany;//快递公司

    @FXML private TableColumn  findmailaddress;//寄件地址

    @FXML private TableColumn  findmailremarks;//备注






    private Stage stage = new Stage();

    private static SpringFxmlLoader loader = new SpringFxmlLoader();



    /**
     * 生成询价订单流水号 (A1809280001)
     * 格式:英文字母(A)+日期(180928)+4位流水号(0001)
     * @return
     */
    public String createIsnum(String currentDate){
        String newDateStr=currentDate.substring(2,4)+currentDate.substring(currentDate.indexOf("-")+1,currentDate.indexOf("-")+3)+currentDate.substring(currentDate.lastIndexOf("-")+1,currentDate.lastIndexOf("-")+3);
        String maxIsnum = expressMailService.selectMaxIdnum(currentDate);
        if(maxIsnum != null){
            String maxAlphabet = maxIsnum.substring(0,1);
            //180928 0001
            int currenNumber = Integer.parseInt(maxIsnum.substring(maxIsnum.length()-4,maxIsnum.length()));
            for (int i = 0; i< NumberUtil.ALPHABET.length; i++){
                if(currenNumber == 9999 ){
                    if( maxAlphabet.equals(NumberUtil.ALPHABET[i])  ){
                        return NumberUtil.ALPHABET[i+1]+"0001";
                    }
                }
            }
            if(currenNumber>0 && currenNumber < 9){
                return maxAlphabet +newDateStr+"000"+(currenNumber+1);
            }else if(currenNumber >= 9 && currenNumber< 99){
                return maxAlphabet+newDateStr +"00"+(currenNumber+1);
            }else if(currenNumber >= 99 && currenNumber< 999){
                return maxAlphabet+newDateStr +"0"+(currenNumber+1);
            }else{
                return maxAlphabet+newDateStr+(currenNumber+1);
            }
        }
        return "A"+newDateStr+"0001";
    }



    public void moreExpressMailClick(){


        stage.setTitle("快递寄件查询");
        Pane pane = new Pane();
        pane.getChildren().setAll(loader.load("/fxml/stock/express_mail_find.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        /*stage.setResizable(false);*/
                /*stage.initStyle(StageStyle.UNDECORATED);
               DragUtil.addDragListener(stage, pane); //拖拽监听*/
        stage.show();
        loadExpressMail();
    }

    /**
     * 现有库位查询
     */
    public void loadExpressMail(){

        List<ExpressMail> expressMails = expressMailService.findExpressMail();


        ObservableList<ExpressMail> list = FXCollections.observableArrayList();


        findmailorder.setCellValueFactory(new PropertyValueFactory("mailorder"));
        findmaildate.setCellValueFactory(new PropertyValueFactory("maildatetime"));
        findmailcompany.setCellValueFactory(new PropertyValueFactory("company"));
        findmailaddress.setCellValueFactory(new PropertyValueFactory("mailaddress"));
        findmailremarks.setCellValueFactory(new PropertyValueFactory("remarks"));

        for (ExpressMail expressMail:expressMails) {

            expressMail.setMaildatetime(new SimpleDateFormat("yyyy-MM-dd").format(expressMail.getMaildate()));

            list.add(expressMail);

        }

        expressMailView.setItems(list); //tableview添加list

        expressMailView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExpressMail>() {
            @Override
            public void changed(ObservableValue<? extends ExpressMail> observableValue, ExpressMail oldItem, ExpressMail newItem) {
                if(newItem.getMailorder()!= null && !("".equals(newItem.getMailorder()))){
                    expressMailView.setUserData(newItem.getId());
                }
            }
        });


        expressMailView.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                closeExpressMailWin();
            }
        });


    }

    public void closeExpressMailWin(){
        long id =(long)expressMailView.getUserData();
        ExpressMail expressMail =  expressMailService.selectByKey(id);
        loadDate(expressMail);
        coseWin();
    }

    public void coseWin(){
        stage.close();
    }













    public void pages(MouseEvent event){
        Node node =(Node)event.getSource();
        int pageNum =Integer.parseInt(String.valueOf(node.getUserData()));
        findInquiry(pageNum);
        NumberUtil.changeStatus(fxmlStatus,0);//修改为修改状态
    }




    /**
     * 分页查询盘点订单
     */
    public void findInquiry(int pageNum){


        List<ExpressMail> expressMails = expressMailService.findExpressMail(pageNum,1);

        PageInfo<ExpressMail> pageInfo = new PageInfo<>(expressMails);

        first.setUserData(1); //首页

        prev.setUserData(pageInfo.getPrePage()); //上一页

        next.setUserData(pageInfo.getNextPage());//下一页

        last.setUserData(pageInfo.getPages());//尾页

        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);


        if(expressMails != null){
            expressMails.forEach(inquiryOrder ->loadDate(inquiryOrder));
        }





    }


    /**
     * 向控件加载数据
     * @param expressMail
     */
    public void loadDate(ExpressMail expressMail){


        maildate.setValue(LocalDateTime.ofInstant(expressMail.getMaildate().toInstant(), ZoneId.systemDefault()).toLocalDate());


        mailorder.setUserData(expressMail.getId());

        mailorder.setText(expressMail.getMailorder());

        company.getItems().setAll("上海君之道信息有限公司","上海杰冠信息有限公司");


        for(int i=0,len=company.getItems().size();i<len;i++){
            if(company.getItems().get(i).equals(expressMail.getCompany())){
                company.getSelectionModel().select(i);
            }else{
                company.getSelectionModel().select(0);
            }
        }

        mailnum.setText(expressMail.getMailnum().toString());

        mailweight.setText(expressMail.getMailweight().toString());

        content.setText(expressMail.getContent());

        paymethod.setText(expressMail.getPaymethod());

        freightprice.setText(expressMail.getFreightprice().toString());


        if(expressMail.getIspay() == 1)ispay.setSelected(true);

        if(expressMail.getIsmonth() == 1)ismonth.setSelected(true);

        account.setText(expressMail.getAccount());

        ensure.setText(expressMail.getEnsure().toString());

        ensurepoint.setText(expressMail.getEnsurepoint().toString());

        remarks.setText(expressMail.getRemarks());

        createpeople.setText(expressMail.getCreatepeople());

        createdate.setText(expressMail.getCreatedate());

        updatepeople.setText(expressMail.getUpdatepeople());

        updatedate.setText(expressMail.getUpdatedate());

        shpeople.setText(expressMail.getShpeople());

        shdate.setText(expressMail.getShdate());

//        @FXML private ComboBox contenttype;//托寄内容类型
//        @FXML private ComboBox paymethodtype;//支付方式类型
//        @FXML private ComboBox freighttype;//本次运费类型


        mailid.setText(expressMail.getMailid());
        mailcompany.setText(expressMail.getMailcompany());

        selectedComboBox(mailprovince,expressMail.getMailprovince());
        selectedComboBox(mailcity,expressMail.getMailcity());
        selectedComboBox(mailarea,expressMail.getMailarea());

        mailaddress.setText(expressMail.getMailaddress());
//        mailpeople.setDisable(!status);
//        mailphone.setDisable(!status);

        collectid.setText(expressMail.getCollectid());
        collectdes.setText(expressMail.getCollectdes());
        collectcompany.setText(expressMail.getCollectcompany());

        selectedComboBox(collectprovince,expressMail.getCollectprovince());
        selectedComboBox(collectcity,expressMail.getCollectcity());
        selectedComboBox(collectarea,expressMail.getCollectarea());


        collectaddress.setText(expressMail.getCollectaddress());
//        collectpeople.setDisable(!status);
//        collectphone.setDisable(!status);
    }






    /**
     * 不可编辑
     * @param status
     */
    public void changeEditable(boolean status){

        maildate.setDisable(!status);

        mailorder.setDisable(!status);

        mailorder.setDisable(!status);

        company.setDisable(!status);

        mailnum.setDisable(!status);

        mailweight.setDisable(!status);

        content.setDisable(!status);

        paymethod.setDisable(!status);

        freightprice.setDisable(!status);

        ispay.setDisable(!status);

        ismonth.setDisable(!status);

        account.setDisable(!status);

        ensure.setDisable(!status);

        ensurepoint.setDisable(!status);

        remarks.setDisable(!status);

        createpeople.setDisable(!status);

        createdate.setDisable(!status);

        updatepeople.setDisable(!status);

        updatedate.setDisable(!status);

        shpeople.setDisable(!status);

        shdate.setDisable(!status);

        contenttype.setDisable(!status);

        paymethodtype.setDisable(!status);

        freighttype.setDisable(!status);

        mailid.setDisable(!status);
        mailcompany.setDisable(!status);
        mailprovince.setDisable(!status);
        mailcity.setDisable(!status);
        mailarea.setDisable(!status);
        mailaddress.setDisable(!status);
        mailpeople.setDisable(!status);
        mailphone.setDisable(!status);

        collectid.setDisable(!status);
        collectdes.setDisable(!status);
        collectcompany.setDisable(!status);
        collectprovince.setDisable(!status);
        collectcity.setDisable(!status);
        collectarea.setDisable(!status);
        collectaddress.setDisable(!status);
        collectpeople.setDisable(!status);
        collectphone.setDisable(!status);

    }



    /**
     * 清空
     */
    public void clearValue(){



        maildate.setValue(null);

        mailorder.setText(NumberUtil.NULLSTRING);

        mailorder.setText(NumberUtil.NULLSTRING);

        company.getSelectionModel().select(0);

        mailnum.setText(NumberUtil.NULLSTRING);

        mailweight.setText(NumberUtil.NULLSTRING);

        content.setText(NumberUtil.NULLSTRING);

        paymethod.setText(NumberUtil.NULLSTRING);

        freightprice.setText(NumberUtil.NULLSTRING);

        ispay.setText(NumberUtil.NULLSTRING);

        ismonth.setText(NumberUtil.NULLSTRING);

        account.setText(NumberUtil.NULLSTRING);

        ensure.setText(NumberUtil.NULLSTRING);

        ensurepoint.setText(NumberUtil.NULLSTRING);

        remarks.setText(NumberUtil.NULLSTRING);

        createpeople.setText(NumberUtil.NULLSTRING);

        createdate.setText(NumberUtil.NULLSTRING);

        updatepeople.setText(NumberUtil.NULLSTRING);

        updatedate.setText(NumberUtil.NULLSTRING);

        shpeople.setText(NumberUtil.NULLSTRING);

        shdate.setText(NumberUtil.NULLSTRING);

        contenttype.getSelectionModel().select(0);

        paymethodtype.getSelectionModel().select(0);

        freighttype.getSelectionModel().select(0);


        mailid.setText(NumberUtil.NULLSTRING);
        mailcompany.setText(NumberUtil.NULLSTRING);
        mailprovince.getSelectionModel().select(0);
        mailcity.getSelectionModel().select(0);
        mailarea.getSelectionModel().select(0);
        mailaddress.setText(NumberUtil.NULLSTRING);
        mailpeople.getSelectionModel().select(0);
        mailphone.getSelectionModel().select(0);

        collectid.setText(NumberUtil.NULLSTRING);
        collectdes.setText(NumberUtil.NULLSTRING);
        collectcompany.setText(NumberUtil.NULLSTRING);
        collectprovince.getSelectionModel().select(0);
        collectcity.getSelectionModel().select(0);
        collectarea.getSelectionModel().select(0);
        collectaddress.setText(NumberUtil.NULLSTRING);
        collectpeople.getSelectionModel().select(0);
        collectphone.getSelectionModel().select(0);

    }

    /**
     * 删除
     */
    public void delete(){
        if(f_alert_confirmDialog(" ","是否删除!")) {
            long id = (long) mailorder.getUserData();

            int rows = expressMailService.delete(id);
            if (rows > 0) {
                findInquiry(1); //初始化第一条数据
            }
            NumberUtil.changeStatus(fxmlStatus, 0); //修改为修改状态
            this.changeEditable(false);
        }
    }



    /**
     * 修改
     */
    public void update(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.UPDATE);//修改为修改状态
        this.changeEditable(true);
        submitvbox.setDisable(false);
        insertvbox.setDisable(true);
        deletevbox.setDisable(true);
        updatevbox.setDisable(true);
        lastUpdatePeopel(updatepeople,updatedate);
        //联系人空白行
    }

    /**
     *  新增按钮
     */
    public void insert(){
        NumberUtil.changeStatus(fxmlStatus,NumberUtil.INSERT);//修改为新增状态
        this.changeEditable(true);
        clearValue();//清空控件
        submitvbox.setDisable(false);
        updatevbox.setDisable(true);
        deletevbox.setDisable(true);
        insertvbox.setDisable(true);
        createPeople(createpeople,createdate);//制单人
    }





    /**
     * 提交
     */
    public  void submit(){

        int  submitStuts = (int)fxmlStatus.getUserData(); //1、新增 2、修改

        LocalDate orderDate =maildate.getValue();

        if(orderDate == null){
            alert_informationDialog("请填写制单日期!");
            return;
        }


        ExpressMail expressMail = new ExpressMail(
                new Date(java.sql.Date.valueOf(orderDate).getTime()),
                mailorder.getText(),
                company.getItems().size()==0?"":company.getSelectionModel().getSelectedItem().toString(),
                "".equals(mailnum.getText())?0:Integer.parseInt(mailnum.getText()),
                "".equals(mailweight.getText())?0.00:Integer.parseInt(mailnum.getText()),
                contenttype.getSelectionModel().getSelectedIndex()+1,
                content.getText(),
                paymethodtype.getSelectionModel().getSelectedIndex()+1,
                paymethod.getText(),
                freighttype.getSelectionModel().getSelectedIndex()+1,
                "".equals(freightprice.getText())?0:Double.parseDouble(freightprice.getText()),
                ispay.isSelected()==true?1:0,
                ismonth.isSelected()==true?1:0,
                account.getText(),
                "".equals(ensure.getText())?0:Double.parseDouble(ensure.getText()),
                "".equals(ensurepoint.getText())?0:Double.parseDouble(ensurepoint.getText()),
                remarks.getText(),
                createpeople.getText(),
                createdate.getText(),
                updatepeople.getText(),
                updatedate.getText(),
                shpeople.getText(),
                shdate.getText(),
                0,
                mailid.getText(),
                mailcompany.getText(),
                mailprovince.getItems().size()==0?"":mailprovince.getSelectionModel().getSelectedItem().toString(),
                mailcity.getItems().size()==0?"":mailcity.getSelectionModel().getSelectedItem().toString(),
                mailarea.getItems().size()==0?"":mailarea.getSelectionModel().getSelectedItem().toString(),
                mailaddress.getText(),
                mailpeople.getItems().size()==0?"":mailphone.getSelectionModel().getSelectedItem().toString(),
                mailarea.getItems().size()==0?"":mailarea.getSelectionModel().getSelectedItem().toString(),
                collectid.getText(),
                collectdes.getText(),
                collectcompany.getText(),
                collectprovince.getItems().size()==0?"":collectprovince.getSelectionModel().getSelectedItem().toString(),
                collectcity.getItems().size()==0?"":collectcity.getSelectionModel().getSelectedItem().toString(),
                collectarea.getItems().size()==0?"":collectarea.getSelectionModel().getSelectedItem().toString(),
                collectaddress.getText(),
                collectpeople.getItems().size()==0?"":collectpeople.getSelectionModel().getSelectedItem().toString(),
                collectphone.getItems().size()==0?"":collectphone.getSelectionModel().getSelectedItem().toString()
        );




        int rows =0;
        if(submitStuts==1){
            //产生订单编号
            String orderNum = createIsnum(orderDate.toString());
            mailorder.setText(orderNum);
            expressMail.setMailorder(orderNum);
            rows = expressMailService.save(expressMail);
        }else if(submitStuts ==2){
            expressMail.setId((long)mailorder.getUserData());
            rows = expressMailService.updateNotNull(expressMail);
        }

        NumberUtil.changeStatus(fxmlStatus,0);
        submitvbox.setDisable(true);



        this.loadDate(expressMail); //重新加载数据


        changeEditable(false);

        submitvbox.setDisable(true);

        insertvbox.setDisable(false);

        updatevbox.setDisable(false);

        deletevbox.setDisable(false);

    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {


        String newStr = location.toString();

        int index = newStr.indexOf("express_mail.fxml");

        if(index != -1) {

            bindCityListen(mailprovince,mailcity,mailarea);
            bindCityListen(collectprovince,collectcity,collectarea);

            findInquiry(1);
        }


    }
}
