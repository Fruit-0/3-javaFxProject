package com.yc.education.property;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

/**
 * 报价单导入到订货单
 */
public class SaleQuotationImportProductProperty {
    private SimpleLongProperty id;
    private SimpleStringProperty productNo;
    private SimpleStringProperty productName;
    private SimpleStringProperty num;
    private SimpleStringProperty unit;
    private SimpleStringProperty price;
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

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
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

    public SaleQuotationImportProductProperty() {
    }

    public SaleQuotationImportProductProperty(SimpleStringProperty productNo, SimpleStringProperty productName, SimpleStringProperty num, SimpleStringProperty unit, SimpleStringProperty price, SimpleStringProperty remark, SimpleBooleanProperty checked) {
        this.productNo = productNo;
        this.productName = productName;
        this.num = num;
        this.unit = unit;
        this.price = price;
        this.remark = remark;
        this.checked = checked;
    }

    public SaleQuotationImportProductProperty(Long id, String productNo, String productName, Integer num, String unit, BigDecimal price, String remark, Boolean checked) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(id != null){
            this.productNo = new SimpleStringProperty(productNo);
        }
        if(id != null){
            this.productName = new SimpleStringProperty(productName);
        }
        if(id != null){
            this.num = new SimpleStringProperty(num.toString());
        }
        if(id != null){
            this.unit = new SimpleStringProperty(unit);
        }
        if(id != null){
            this.price = new SimpleStringProperty(price.toString());
        }
        if(id != null){
            this.remark = new SimpleStringProperty(remark);
        }
        if(id != null){
            this.checked = new SimpleBooleanProperty(checked);
        }
    }
}
