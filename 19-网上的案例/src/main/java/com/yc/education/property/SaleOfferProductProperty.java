package com.yc.education.property;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

/**
 * 报价产品
 */
public class SaleOfferProductProperty {

    /**
     *
     */
    private SimpleLongProperty id;

    /**
     *
     */
    private SimpleStringProperty productNo;

    private SimpleStringProperty productName;
    private SimpleStringProperty category;
    private SimpleStringProperty num;
    private SimpleStringProperty unit;
    private SimpleStringProperty pricing;
    private SimpleStringProperty discount;
    private SimpleStringProperty price;
    private SimpleStringProperty money;
    private SimpleStringProperty remark;
    private SimpleStringProperty userid;

    public SaleOfferProductProperty(Long id, String productNo, String productName, String category, Integer num, String unit, BigDecimal pricing, String discount, BigDecimal price, BigDecimal money, String remark, Long userid) {
        this.id = new SimpleLongProperty(id);
        this.productNo = new SimpleStringProperty(productNo);
        this.productName = new SimpleStringProperty(productName);
        this.category = new SimpleStringProperty(category);
        this.num = new SimpleStringProperty(num.toString());
        this.unit = new SimpleStringProperty(unit);
        this.pricing = new SimpleStringProperty(pricing.toString());
        this.discount = new SimpleStringProperty(discount);
        this.price = new SimpleStringProperty(price.toString());
        this.money = new SimpleStringProperty(money.toString());
        this.remark = new SimpleStringProperty(remark);
        this.userid = new SimpleStringProperty(userid.toString());
    }

    public SaleOfferProductProperty(String productNo, String productName, String category, Integer num, String unit, BigDecimal pricing, String discount, BigDecimal price, BigDecimal money, String remark) {
        this.productNo = new SimpleStringProperty(productNo);
        this.productName = new SimpleStringProperty(productName);
        this.category = new SimpleStringProperty(category);
        this.num = new SimpleStringProperty(num.toString());
        this.unit = new SimpleStringProperty(unit);
        this.pricing = new SimpleStringProperty(pricing.toString());
        this.discount = new SimpleStringProperty(discount);
        this.price = new SimpleStringProperty(price.toString());
        this.money = new SimpleStringProperty(money.toString());
        this.remark = new SimpleStringProperty(remark);
    }

    public SaleOfferProductProperty() {
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

    public String getUnit() {
        return unit.get();
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
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

    public String getUserid() {
        return userid.get();
    }

    public SimpleStringProperty useridProperty() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid.set(userid);
    }

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

    public String getNum() {
        return num.get();
    }

    public SimpleStringProperty numProperty() {
        return num;
    }

    public void setNum(String num) {
        this.num.set(num);
    }
}
