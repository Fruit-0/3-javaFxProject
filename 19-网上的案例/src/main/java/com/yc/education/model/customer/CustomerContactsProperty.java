package com.yc.education.model.customer;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class CustomerContactsProperty {
    private SimpleLongProperty id;
    private SimpleStringProperty customerId;
    private SimpleStringProperty primaryContacts;
    private SimpleStringProperty name;
    private SimpleStringProperty department;
    private SimpleStringProperty position;
    private SimpleStringProperty telephone;
    private SimpleStringProperty fax;
    private SimpleStringProperty mobilePhone;
    private SimpleStringProperty email;
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

    public String getPrimaryContacts() {
        return primaryContacts.get();
    }

    public SimpleStringProperty primaryContactsProperty() {
        return primaryContacts;
    }

    public void setPrimaryContacts(String primaryContacts) {
        this.primaryContacts.set(primaryContacts);
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

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getFax() {
        return fax.get();
    }

    public SimpleStringProperty faxProperty() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    public String getMobilePhone() {
        return mobilePhone.get();
    }

    public SimpleStringProperty mobilePhoneProperty() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone.set(mobilePhone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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

    public CustomerContactsProperty(Long id, String customerId, String primaryContacts, String name, String department, String position, String telephone, String fax, String mobilePhone, String email, String remark) {
        if(id != null){
            this.id = new SimpleLongProperty(id);
        }
        if(customerId != null){
            this.customerId = new SimpleStringProperty(customerId);
        }
        if(primaryContacts != null){
            this.primaryContacts = new SimpleStringProperty(primaryContacts);
        }
        if(name != null){
            this.name = new SimpleStringProperty(name);
        }
        if(department != null){
            this.department = new SimpleStringProperty(department);
        }
        if(position != null){
            this.position = new SimpleStringProperty(position);
        }
        if(telephone != null){
            this.telephone = new SimpleStringProperty(telephone);
        }
        if(fax != null){
            this.fax = new SimpleStringProperty(fax);
        }
        if(mobilePhone != null){
            this.mobilePhone = new SimpleStringProperty(mobilePhone);
        }
        if(email != null){
            this.email = new SimpleStringProperty(email);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public CustomerContactsProperty(String customerId, String primaryContacts, String name, String department, String position, String telephone, String fax, String mobilePhone, String email, String remark) {
        if(customerId != null){
            this.customerId = new SimpleStringProperty(customerId);
        }
        if(primaryContacts != null){
            this.primaryContacts = new SimpleStringProperty(primaryContacts);
        }
        if(name != null){
            this.name = new SimpleStringProperty(name);
        }
        if(department != null){
            this.department = new SimpleStringProperty(department);
        }
        if(position != null){
            this.position = new SimpleStringProperty(position);
        }
        if(telephone != null){
            this.telephone = new SimpleStringProperty(telephone);
        }
        if(fax != null){
            this.fax = new SimpleStringProperty(fax);
        }
        if(mobilePhone != null){
            this.mobilePhone = new SimpleStringProperty(mobilePhone);
        }
        if(email != null){
            this.email = new SimpleStringProperty(email);
        }
        if(remark != null){
            this.remark = new SimpleStringProperty(remark);
        }
    }


}
