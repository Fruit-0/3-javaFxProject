package com.yc.education.property;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.util.Date;

public class SaleOnlineOrderProductProperty {

    private SimpleLongProperty id;
    private SimpleLongProperty onlineOrderId;
    private SimpleStringProperty customerNo;
    private SimpleStringProperty productNo;
    private SimpleStringProperty productName;
    private SimpleStringProperty category;
    private SimpleStringProperty num;
    private SimpleStringProperty unit;
    private SimpleStringProperty price;
    private SimpleStringProperty money;
    private SimpleStringProperty usableNum;
    private SimpleStringProperty ifstock;
    private SimpleStringProperty remark;

    public Long getId() {
        if(id == null){
            return null;
        }else{
            return id.get();
        }
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getOnlineOrderId() {
        return onlineOrderId.get();
    }

    public SimpleLongProperty onlineOrderIdProperty() {
        return onlineOrderId;
    }

    public void setOnlineOrderId(long onlineOrderId) {
        this.onlineOrderId.set(onlineOrderId);
    }

    public String getCustomerNo() {
        return customerNo.get();
    }

    public SimpleStringProperty customerNoProperty() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo.set(customerNo);
    }

    public String getProductNo() {
        return productNo.get();
    }

    public SimpleStringProperty productNoProperty() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo.set(productNo);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getNum() {
        return num.get();
    }

    public SimpleStringProperty numProperty() {
        return num;
    }

    public void setNum(String num) {
        this.num.set(num);
    }

    public String getUnit() {
        return unit.get();
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getMoney() {
        return money.get();
    }

    public SimpleStringProperty moneyProperty() {
        return money;
    }

    public void setMoney(String money) {
        this.money.set(money);
    }

    public String getUsableNum() {
        return usableNum.get();
    }

    public SimpleStringProperty usableNumProperty() {
        return usableNum;
    }

    public void setUsableNum(String usableNum) {
        this.usableNum.set(usableNum);
    }

    public String getIfstock() {
        return ifstock.get();
    }

    public SimpleStringProperty ifstockProperty() {
        return ifstock;
    }

    public void setIfstock(String ifstock) {
        this.ifstock.set(ifstock);
    }

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public SaleOnlineOrderProductProperty() {
    }

    public SaleOnlineOrderProductProperty(  String customerNo, String productNo, String productName, String category, Integer num, String unit, BigDecimal price, BigDecimal money, Integer usableNum, String remark) {
        if(customerNo != null){
            this.customerNo = new SimpleStringProperty(customerNo);
        }
        if(productNo != null){
            this.productNo = new SimpleStringProperty(productNo);
        }
        if(productName != null){
            this.productName = new SimpleStringProperty(productName);
        }
        if(category != null){
            this.category = new SimpleStringProperty(category);
        }
        if(num != null){
            this.num = new SimpleStringProperty(num.toString());
        }
        if(unit != null){
            this.unit = new SimpleStringProperty(unit);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(usableNum != null){
            if(usableNum > 0){
                this.ifstock = new SimpleStringProperty("是");
            }else{
                this.ifstock = new SimpleStringProperty("否");
            }
            this.usableNum = new SimpleStringProperty(usableNum.toString());
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        };
    }

    public SaleOnlineOrderProductProperty(Long id, Long onlineOrderId, String customerNo, String productNo, String productName, String category, Integer num, String unit, BigDecimal price, BigDecimal money, Integer usableNum, String remark ) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(onlineOrderId != null){
            this.onlineOrderId = new SimpleLongProperty(onlineOrderId);
        }
        if(customerNo != null){
            this.customerNo = new SimpleStringProperty(customerNo);
        }
        if(productNo != null){
            this.productNo = new SimpleStringProperty(productNo);
        }
        if(productName != null){
            this.productName = new SimpleStringProperty(productName);
        }
        if(category != null){
            this.category = new SimpleStringProperty(category);
        }
        if(num != null){
            this.num = new SimpleStringProperty(num.toString());
        }
        if(unit != null){
            this.unit = new SimpleStringProperty(unit);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(usableNum != null){
            if(usableNum > 0){
                this.ifstock = new SimpleStringProperty("是");
            }else{
                this.ifstock = new SimpleStringProperty("否");
            }
            this.usableNum = new SimpleStringProperty(usableNum.toString());
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }
}
