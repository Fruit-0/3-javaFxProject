package com.yc.education.property;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class SaleReturnGoodsProductProperty {
    private SimpleLongProperty id;
    private SimpleStringProperty saleReturnId;
    private SimpleStringProperty productNo;
    private SimpleStringProperty productName;
    private SimpleStringProperty category;
    private SimpleStringProperty num;
    private SimpleStringProperty unit;
    private SimpleStringProperty pricing;
    private SimpleStringProperty money;
    private SimpleStringProperty source;
    private SimpleStringProperty orderNo;
    private SimpleStringProperty warehousePosition;
    private SimpleStringProperty floor;
    private SimpleStringProperty remark;
    private SimpleBooleanProperty checked;

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

    public String getSaleReturnId() {
        return saleReturnId.get();
    }

    public SimpleStringProperty saleReturnIdProperty() {
        return saleReturnId;
    }

    public void setSaleReturnId(String saleReturnId) {
        this.saleReturnId.set(saleReturnId);
    }

    public String getSource() {
        return source.get();
    }

    public SimpleStringProperty sourceProperty() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getOrderNo() {
        return orderNo.get();
    }

    public SimpleStringProperty orderNoProperty() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo.set(orderNo);
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

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public String getPricing() {
        return pricing.get();
    }

    public SimpleStringProperty pricingProperty() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing.set(pricing);
    }

    public String getWarehousePosition() {
        if(warehousePosition == null){
            return null;
        }else{
            return warehousePosition.get();
        }

    }

    public SimpleStringProperty warehousePositionProperty() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition.set(warehousePosition);
    }

    public String getFloor() {
        if(floor == null){
            return null;
        }else{
            return floor.get();
        }
    }

    public SimpleStringProperty floorProperty() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor.set(floor);
    }

    public boolean isChecked() {
        return checked.get();
    }

    public SimpleBooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public SaleReturnGoodsProductProperty() {
    }

    public SaleReturnGoodsProductProperty( String productNo, String productName, String category, Integer num, String unit,  BigDecimal pricing, BigDecimal money, String source, String orderNo,String warehousePosition, String floor, String remark) {
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
        if(pricing != null){
            this.pricing = new SimpleStringProperty(pricing.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(warehousePosition != null){
            this.warehousePosition = new SimpleStringProperty(warehousePosition);
        }
        if(floor != null){
            this.floor = new SimpleStringProperty(floor);
        }
        if(source != null){
            this.source = new SimpleStringProperty(source);
        }
        if(orderNo != null){
            this.orderNo = new SimpleStringProperty(orderNo);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public SaleReturnGoodsProductProperty(Long id , Long saleReturnId, String productNo, String productName, String category, Integer num, String unit,  BigDecimal pricing, BigDecimal money, String source, String orderNo,String warehousePosition, String floor, String remark) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(saleReturnId != null){
            this.saleReturnId = new SimpleStringProperty(saleReturnId.toString());
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
        if(pricing != null){
            this.pricing = new SimpleStringProperty(pricing.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(warehousePosition != null){
            this.warehousePosition = new SimpleStringProperty(warehousePosition);
        }
        if(floor != null){
            this.floor = new SimpleStringProperty(floor);
        }
        if(source != null){
            this.source = new SimpleStringProperty(source);
        }
        if(orderNo != null){
            this.orderNo = new SimpleStringProperty(orderNo);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }



}
