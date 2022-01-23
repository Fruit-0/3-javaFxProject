package com.yc.education.model.customer;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class CustomerBusinessLeaderProperty {
    private SimpleLongProperty id;
    private SimpleStringProperty customerId;
    private SimpleStringProperty employeeCode;
    private SimpleStringProperty name;
    private SimpleStringProperty primaryPeople;
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

    public String getCustomerId() {
        return customerId.get();
    }

    public SimpleStringProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public String getEmployeeCode() {
        return employeeCode.get();
    }

    public SimpleStringProperty employeeCodeProperty() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode.set(employeeCode);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrimaryPeople() {
        return primaryPeople.get();
    }

    public SimpleStringProperty primaryPeopleProperty() {
        return primaryPeople;
    }

    public void setPrimaryPeople(String primaryPeople) {
        this.primaryPeople.set(primaryPeople);
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

    public CustomerBusinessLeaderProperty( String customerId, String employeeCode, String name, String primaryPeople, String remark) {
        if(customerId != null){
            this.customerId = new SimpleStringProperty(customerId);
        }
        if(employeeCode != null){
            this.employeeCode = new SimpleStringProperty(employeeCode);
        }
        if(name != null){
            this.name = new SimpleStringProperty(name);
        }
        if(primaryPeople != null){
            this.primaryPeople = new SimpleStringProperty(primaryPeople);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public CustomerBusinessLeaderProperty(Long id, String customerId, String employeeCode, String name, String primaryPeople, String remark) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(customerId != null){
            this.customerId = new SimpleStringProperty(customerId);
        }
        if(employeeCode != null){
            this.employeeCode = new SimpleStringProperty(employeeCode);
        }
        if(name != null){
            this.name = new SimpleStringProperty(name);
        }
        if(primaryPeople != null){
            this.primaryPeople = new SimpleStringProperty(primaryPeople);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public CustomerBusinessLeaderProperty() {
    }
}
