package com.yc.education.model.customer;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class CustomerDemandGoodsInfoProperty {
    private SimpleLongProperty id;
    private SimpleStringProperty productNo;
    private SimpleStringProperty productName;
    private SimpleStringProperty brand;
    private SimpleStringProperty quantityDemand;
    private SimpleStringProperty price;
    private SimpleStringProperty productCategories;
    private SimpleStringProperty productNature;
    private SimpleStringProperty manufactureMethod;
    private SimpleStringProperty processMethod;
    private SimpleStringProperty continuity;
    private SimpleStringProperty cutOil;
    private SimpleStringProperty useAmount;
    private SimpleStringProperty cutSpeed;


    public CustomerDemandGoodsInfoProperty() {
    }

    public CustomerDemandGoodsInfoProperty(Long id, String productNo, String productName, String brand, String quantityDemand, String price, String productCategories, String productNature, String manufactureMethod, String processMethod, String continuity, String cutOil, String useAmount, String cutSpeed) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(productNo != null){
            this.productNo = new SimpleStringProperty(productNo);
        }
        if(productName != null){
            this.productName = new SimpleStringProperty(productName);
        }
        if(brand != null){
            this.brand = new SimpleStringProperty(brand);
        }
        if(quantityDemand != null){
            this.quantityDemand = new SimpleStringProperty(quantityDemand);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price);
        }
        if(productCategories != null){
            this.productCategories = new SimpleStringProperty(productCategories);
        }
        if(productNature != null){
            this.productNature = new SimpleStringProperty(productNature);
        }
        if(manufactureMethod != null){
            this.manufactureMethod = new SimpleStringProperty(manufactureMethod);
        }
        if(processMethod != null){
            this.processMethod = new SimpleStringProperty(processMethod);
        }
        if(continuity != null){
            this.continuity = new SimpleStringProperty(continuity);
        }
        if(cutOil != null){
            this.cutOil = new SimpleStringProperty(cutOil);
        }
        if(useAmount != null){
            this.useAmount = new SimpleStringProperty(useAmount);
        }
        if(cutSpeed != null){
            this.cutSpeed = new SimpleStringProperty(cutSpeed);
        }
    }

    public CustomerDemandGoodsInfoProperty(String productNo, String productName, String brand, String quantityDemand, String price, String productCategories, String productNature, String manufactureMethod, String processMethod, String continuity, String cutOil, String useAmount, String cutSpeed) {

        if(productNo != null){
            this.productNo = new SimpleStringProperty(productNo);
        }
        if(productName != null){
            this.productName = new SimpleStringProperty(productName);
        }
        if(brand != null){
            this.brand = new SimpleStringProperty(brand);
        }
        if(quantityDemand != null){
            this.quantityDemand = new SimpleStringProperty(quantityDemand);
        }
        if(price != null){
            this.price = new SimpleStringProperty(price);
        }
        if(productCategories != null){
            this.productCategories = new SimpleStringProperty(productCategories);
        }
        if(productNature != null){
            this.productNature = new SimpleStringProperty(productNature);
        }
        if(manufactureMethod != null){
            this.manufactureMethod = new SimpleStringProperty(manufactureMethod);
        }
        if(processMethod != null){
            this.processMethod = new SimpleStringProperty(processMethod);
        }
        if(continuity != null){
            this.continuity = new SimpleStringProperty(continuity);
        }
        if(cutOil != null){
            this.cutOil = new SimpleStringProperty(cutOil);
        }
        if(useAmount != null){
            this.useAmount = new SimpleStringProperty(useAmount);
        }
        if(cutSpeed != null){
            this.cutSpeed = new SimpleStringProperty(cutSpeed);
        }
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

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getQuantityDemand() {
        return quantityDemand.get();
    }

    public SimpleStringProperty quantityDemandProperty() {
        return quantityDemand;
    }

    public void setQuantityDemand(String quantityDemand) {
        this.quantityDemand.set(quantityDemand);
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

    public String getProductCategories() {
        return productCategories.get();
    }

    public SimpleStringProperty productCategoriesProperty() {
        return productCategories;
    }

    public void setProductCategories(String productCategories) {
        this.productCategories.set(productCategories);
    }

    public String getProductNature() {
        return productNature.get();
    }

    public SimpleStringProperty productNatureProperty() {
        return productNature;
    }

    public void setProductNature(String productNature) {
        this.productNature.set(productNature);
    }

    public String getManufactureMethod() {
        return manufactureMethod.get();
    }

    public SimpleStringProperty manufactureMethodProperty() {
        return manufactureMethod;
    }

    public void setManufactureMethod(String manufactureMethod) {
        this.manufactureMethod.set(manufactureMethod);
    }

    public String getProcessMethod() {
        return processMethod.get();
    }

    public SimpleStringProperty processMethodProperty() {
        return processMethod;
    }

    public void setProcessMethod(String processMethod) {
        this.processMethod.set(processMethod);
    }

    public String getContinuity() {
        return continuity.get();
    }

    public SimpleStringProperty continuityProperty() {
        return continuity;
    }

    public void setContinuity(String continuity) {
        this.continuity.set(continuity);
    }

    public String getCutOil() {
        return cutOil.get();
    }

    public SimpleStringProperty cutOilProperty() {
        return cutOil;
    }

    public void setCutOil(String cutOil) {
        this.cutOil.set(cutOil);
    }

    public String getUseAmount() {
        return useAmount.get();
    }

    public SimpleStringProperty useAmountProperty() {
        return useAmount;
    }

    public void setUseAmount(String useAmount) {
        this.useAmount.set(useAmount);
    }

    public String getCutSpeed() {
        return cutSpeed.get();
    }

    public SimpleStringProperty cutSpeedProperty() {
        return cutSpeed;
    }

    public void setCutSpeed(String cutSpeed) {
        this.cutSpeed.set(cutSpeed);
    }
}
