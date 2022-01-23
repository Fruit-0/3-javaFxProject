package com.yc.education.model.customer;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 客户基本资料-发票明细
 */
public class InvoiceProperty {

    private SimpleLongProperty id;
    private SimpleStringProperty usual;
    private SimpleStringProperty title;
    private SimpleStringProperty address;
    private SimpleStringProperty phone;
    private SimpleStringProperty contact;
    private SimpleStringProperty remark;


    public InvoiceProperty(Long id, String usual, String title, String address, String phone, String contact, String remark) {
        if(id != null ){
            this.id = new SimpleLongProperty(id);
        }
        if(usual != null ){
            this.usual = new SimpleStringProperty(usual);
        }
        if(title != null ){
            this.title = new SimpleStringProperty(title);
        }
        if(address != null ){
            this.address = new SimpleStringProperty(address);
        }
        if(phone != null ){
            this.phone = new SimpleStringProperty(phone);
        }
        if(contact != null ){
            this.contact = new SimpleStringProperty(contact);
        }
        if(remark != null ){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public InvoiceProperty( String usual, String title, String address, String phone, String contact, String remark) {
        if(usual != null ){
            this.usual = new SimpleStringProperty(usual);
        }
        if(title != null ){
            this.title = new SimpleStringProperty(title);
        }
        if(address != null ){
            this.address = new SimpleStringProperty(address);
        }
        if(phone != null ){
            this.phone = new SimpleStringProperty(phone);
        }
        if(contact != null ){
            this.contact = new SimpleStringProperty(contact);
        }
        if(remark != null ){
            this.remark = new SimpleStringProperty(remark);
        }
    }

    public InvoiceProperty() {

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

    public String getUsual() {
        return usual.get();
    }

    public SimpleStringProperty usualProperty() {
        return usual;
    }

    public void setUsual(String usual) {
        this.usual.set(usual);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
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
}
