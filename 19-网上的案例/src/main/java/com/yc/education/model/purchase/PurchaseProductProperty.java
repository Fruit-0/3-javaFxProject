package com.yc.education.model.purchase;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @ClassName PurchaseProductProperty
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/10 17:52
 * @Version 1.0
 */
public class PurchaseProductProperty {


    private final SimpleLongProperty id = new SimpleLongProperty();

    private final SimpleStringProperty sourseorder = new SimpleStringProperty();

    private final SimpleStringProperty inquiryorders = new SimpleStringProperty();

    private final SimpleStringProperty sort = new SimpleStringProperty();

    private final SimpleStringProperty proorders = new SimpleStringProperty();

    private final SimpleStringProperty proname = new SimpleStringProperty();

    private final SimpleStringProperty prosupname = new SimpleStringProperty();

    private final SimpleStringProperty pronum = new SimpleStringProperty();

    private final SimpleStringProperty prounit = new SimpleStringProperty();

    private final SimpleStringProperty proprice = new SimpleStringProperty();

    private final SimpleStringProperty totalprice = new SimpleStringProperty();

    private final SimpleStringProperty remarks = new SimpleStringProperty();


    public PurchaseProductProperty(long id,String sourseorder,String inquiryorders,String sort,String proorders,String proname,String prosupname,String pronum,String prounit,String proprice,String totalprice,String remarks) {
        setId(id);
        setSourseorder(sourseorder);
        setInquiryorders(inquiryorders);
        setSort(sort);
        setProorders(proorders);
        setProname(proname);
        setProsupname(prosupname);
        setPronum(pronum);
        setProunit(prounit);
        setProprice(proprice);
        setTotalprice(totalprice);
        setRemarks(remarks);
    }
    public PurchaseProductProperty() {

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

    public String getSourseorder() {
        return sourseorder.get();
    }

    public SimpleStringProperty sourseorderProperty() {
        return sourseorder;
    }

    public void setSourseorder(String sourseorder) {
        this.sourseorder.set(sourseorder);
    }

    public String getInquiryorders() {
        return inquiryorders.get();
    }

    public SimpleStringProperty inquiryordersProperty() {
        return inquiryorders;
    }

    public void setInquiryorders(String inquiryorders) {
        this.inquiryorders.set(inquiryorders);
    }

    public String getSort() {
        return sort.get();
    }

    public SimpleStringProperty sortProperty() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort.set(sort);
    }

    public String getProorders() {
        return proorders.get();
    }

    public SimpleStringProperty proordersProperty() {
        return proorders;
    }

    public void setProorders(String proorders) {
        this.proorders.set(proorders);
    }

    public String getProname() {
        return proname.get();
    }

    public SimpleStringProperty pronameProperty() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname.set(proname);
    }

    public String getProsupname() {
        return prosupname.get();
    }

    public SimpleStringProperty prosupnameProperty() {
        return prosupname;
    }

    public void setProsupname(String prosupname) {
        this.prosupname.set(prosupname);
    }

    public String getPronum() {
        return pronum.get();
    }

    public SimpleStringProperty pronumProperty() {
        return pronum;
    }

    public void setPronum(String pronum) {
        this.pronum.set(pronum);
    }

    public String getProunit() {
        return prounit.get();
    }

    public SimpleStringProperty prounitProperty() {
        return prounit;
    }

    public void setProunit(String prounit) {
        this.prounit.set(prounit);
    }

    public String getProprice() {
        return proprice.get();
    }

    public SimpleStringProperty propriceProperty() {
        return proprice;
    }

    public void setProprice(String proprice) {
        this.proprice.set(proprice);
    }

    public String getTotalprice() {
        return totalprice.get();
    }

    public SimpleStringProperty totalpriceProperty() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice.set(totalprice);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public SimpleStringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }
}
