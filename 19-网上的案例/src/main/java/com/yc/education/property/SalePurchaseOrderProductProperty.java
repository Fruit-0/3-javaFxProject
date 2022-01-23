package com.yc.education.property;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class SalePurchaseOrderProductProperty {

    private SimpleBooleanProperty checked; // 是否勾选
    private SimpleLongProperty id;
    private SimpleStringProperty purchaseOrderId;
    private SimpleStringProperty productNo;
    private SimpleStringProperty productName;
    private SimpleStringProperty category;
    private SimpleStringProperty num;
    private SimpleStringProperty unit;
    private SimpleStringProperty pricing;
    private SimpleStringProperty discount;
    private SimpleStringProperty price;
    private SimpleStringProperty money;
    private SimpleStringProperty orderNo;
    private SimpleStringProperty productSource;
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

    public String getDiscount() {
        return discount.get();
    }

    public SimpleStringProperty discountProperty() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount.set(discount);
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId.get();
    }

    public SimpleStringProperty purchaseOrderIdProperty() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId.set(purchaseOrderId);
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

    public String getProductSource() {
        return productSource.get();
    }

    public SimpleStringProperty productSourceProperty() {
        return productSource;
    }

    public void setProductSource(String productSource) {
        this.productSource.set(productSource);
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

    public SalePurchaseOrderProductProperty() {
    }

    public SalePurchaseOrderProductProperty( String productNo, String productName, String category, Integer num, String unit,BigDecimal pricing,String discount, BigDecimal price, BigDecimal money, String orderNo, String productSource, String remark) {
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
        if(discount != null){
            this.discount = new SimpleStringProperty(discount);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(orderNo != null){
            this.orderNo = new SimpleStringProperty(orderNo);
        }
        if(productSource != null){
            this.productSource = new SimpleStringProperty(productSource);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public SalePurchaseOrderProductProperty(Long id ,Long purchaseOrderId, String productNo, String productName, String category, Integer num, String unit,BigDecimal pricing,String discount, BigDecimal price, BigDecimal money, String orderNo, String productSource, String remark) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(purchaseOrderId != null){
            this.purchaseOrderId = new SimpleStringProperty(purchaseOrderId.toString());
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
        if(discount != null){
            this.discount = new SimpleStringProperty(discount);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(orderNo != null){
            this.orderNo = new SimpleStringProperty(orderNo);
        }
        if(productSource != null){
            this.productSource = new SimpleStringProperty(productSource);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public SalePurchaseOrderProductProperty(Long id ,Long purchaseOrderId, String productNo, String productName, String category, Integer num, String unit,BigDecimal pricing,String discount, BigDecimal price, BigDecimal money, String orderNo, String productSource, String remark, Boolean checked) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(purchaseOrderId != null){
            this.purchaseOrderId = new SimpleStringProperty(purchaseOrderId.toString());
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
        if(discount != null){
            this.discount = new SimpleStringProperty(discount);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price.toString());
        }
        if(money != null){
            this.money = new SimpleStringProperty(money.toString());
        }
        if(orderNo != null){
            this.orderNo = new SimpleStringProperty(orderNo);
        }
        if(productSource != null){
            this.productSource = new SimpleStringProperty(productSource);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
        if(checked != null){
            this.checked = new SimpleBooleanProperty(checked);
        }
    }

}
