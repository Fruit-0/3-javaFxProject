package com.yc.education.model.customer;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class CustomerDataMaintainCarProperty {
    private SimpleLongProperty id;
    private SimpleLongProperty maintainId;
    private SimpleStringProperty latheType;
    private SimpleStringProperty brand;
    private SimpleStringProperty models;
    private SimpleStringProperty num;

    public CustomerDataMaintainCarProperty() {
    }

    public CustomerDataMaintainCarProperty(Long id, Long maintainId, String latheType, String brand, String models, String num) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(maintainId != null){
            this.maintainId = new SimpleLongProperty(maintainId);
        }
        if(latheType != null){
            this.latheType = new SimpleStringProperty(latheType);
        }
        if(brand != null){
            this.brand = new SimpleStringProperty(brand);
        }
        if(models != null){
            this.models = new SimpleStringProperty(models);
        }
        if(num != null){
            this.num = new SimpleStringProperty(num);
        }
    }

    public CustomerDataMaintainCarProperty(String latheType, String brand, String models, String num) {
        if(latheType != null){
            this.latheType = new SimpleStringProperty(latheType);
        }
        if(brand != null){
            this.brand = new SimpleStringProperty(brand);
        }
        if(models != null){
            this.models = new SimpleStringProperty(models);
        }
        if(num != null){
            this.num = new SimpleStringProperty(num);
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

    public long getMaintainId() {
        return maintainId.get();
    }

    public SimpleLongProperty maintainIdProperty() {
        return maintainId;
    }

    public void setMaintainId(long maintainId) {
        this.maintainId.set(maintainId);
    }

    public String getLatheType() {
        return latheType.get();
    }

    public SimpleStringProperty latheTypeProperty() {
        return latheType;
    }

    public void setLatheType(String latheType) {
        this.latheType.set(latheType);
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

    public String getModels() {
        return models.get();
    }

    public SimpleStringProperty modelsProperty() {
        return models;
    }

    public void setModels(String models) {
        this.models.set(models);
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
