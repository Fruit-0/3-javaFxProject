package com.yc.education.model.basic;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @ClassName SupplierContactProperty
 * @Description TODO  供应商-联系人 TabelView数据绑定之类
 * @Author QuZhangJing
 * @Date 2018/9/19 11:54
 * @Version 1.0
 */
public final class SupplierContactProperty {



    private final SimpleLongProperty id = new SimpleLongProperty();


    private  final SimpleStringProperty keycontact = new SimpleStringProperty();

    private  final SimpleStringProperty uname = new SimpleStringProperty();

    private  final SimpleStringProperty udepartment = new SimpleStringProperty();

    private  final SimpleStringProperty ujob = new SimpleStringProperty();

    private  final SimpleStringProperty uphone = new SimpleStringProperty();

    private  final SimpleStringProperty ufax = new SimpleStringProperty();

    private  final SimpleStringProperty umobile = new SimpleStringProperty();

    private  final SimpleStringProperty uemail = new SimpleStringProperty();

    private  final SimpleStringProperty uremarks = new SimpleStringProperty();


    public SupplierContactProperty() {
    }

    public SupplierContactProperty(long id,String keycontact, String uname, String udepartment, String ujob, String uphone, String ufax, String umobile, String uemail, String uremarks) {
        setId(id);
        setKeycontact(keycontact);
        setUname(uname);
        setUdepartment(udepartment);
        setUjob(ujob);
        setUphone(uphone);
        setUfax(ufax);
        setUmobile(umobile);
        setUemail(uemail);
        setUremarks(uremarks);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getUname() {
        return uname.get();
    }

    public SimpleStringProperty unameProperty() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname.set(uname);
    }

    public String getUdepartment() {
        return udepartment.get();
    }

    public SimpleStringProperty udepartmentProperty() {
        return udepartment;
    }

    public void setUdepartment(String udepartment) {
        this.udepartment.set(udepartment);
    }

    public String getUjob() {
        return ujob.get();
    }

    public SimpleStringProperty ujobProperty() {
        return ujob;
    }

    public void setUjob(String ujob) {
        this.ujob.set(ujob);
    }

    public String getUphone() {
        return uphone.get();
    }

    public SimpleStringProperty uphoneProperty() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone.set(uphone);
    }

    public String getUfax() {
        return ufax.get();
    }

    public SimpleStringProperty ufaxProperty() {
        return ufax;
    }

    public void setUfax(String ufax) {
        this.ufax.set(ufax);
    }

    public String getUmobile() {
        return umobile.get();
    }

    public SimpleStringProperty umobileProperty() {
        return umobile;
    }

    public void setUmobile(String umobile) {
        this.umobile.set(umobile);
    }

    public String getUemail() {
        return uemail.get();
    }

    public SimpleStringProperty uemailProperty() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail.set(uemail);
    }

    public String getUremarks() {
        return uremarks.get();
    }

    public SimpleStringProperty uremarksProperty() {
        return uremarks;
    }

    public void setUremarks(String uremarks) {
        this.uremarks.set(uremarks);
    }

    public String getKeycontact() {
        return keycontact.get();
    }

    public SimpleStringProperty keycontactProperty() {
        return keycontact;
    }

    public void setKeycontact(String keycontact) {
        this.keycontact.set(keycontact);
    }
}
