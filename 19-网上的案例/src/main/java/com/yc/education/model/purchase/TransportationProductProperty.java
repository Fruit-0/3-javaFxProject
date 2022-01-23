package com.yc.education.model.purchase;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @ClassName TransportationProductProperty
 * @Description TODO 在途产品之TabelView数据绑定
 * @Author QuZhangJing
 * @Date 2018/10/17 16:48
 * @Version 1.0
 */
public class TransportationProductProperty {

    private final SimpleLongProperty id = new SimpleLongProperty();

    private final SimpleStringProperty purchaseorder = new SimpleStringProperty();

    private final SimpleStringProperty sort = new SimpleStringProperty();

    private final SimpleStringProperty pronum = new SimpleStringProperty();

    private final SimpleStringProperty stocknum = new SimpleStringProperty();

    private final SimpleStringProperty boxnum = new SimpleStringProperty();

    private final SimpleStringProperty thistimeontheway = new SimpleStringProperty();

    private final SimpleStringProperty totalnum = new SimpleStringProperty();

    private final SimpleStringProperty stockover = new SimpleStringProperty();

    private final SimpleStringProperty forcenum = new SimpleStringProperty();

    private final SimpleStringProperty ontheway = new SimpleStringProperty();


    public TransportationProductProperty() {
    }

    public TransportationProductProperty(long id,String purchaseorder,int sort,String pronum,String socknum,String boxnum,int thistimeontheway,int totalnum,int stockover,int forcenum,int ontheway) {
        setId(id);
        setPurchaseorder(purchaseorder);
        setSort(String.valueOf(sort));
        setPronum(pronum);
        setStocknum(socknum);
        setBoxnum(boxnum);
        setThistimeontheway(String.valueOf(thistimeontheway));
        setTotalnum(String.valueOf(totalnum));
        setStockover(String.valueOf(stockover));
        setForcenum(String.valueOf(forcenum));
        setOntheway(String.valueOf(ontheway));
    }

    public TransportationProductProperty(String purchaseorder,int sort,String pronum,String socknum,String boxnum,int thistimeontheway,int totalnum,int stockover,int forcenum,int ontheway) {
        setPurchaseorder(purchaseorder);
        setSort(String.valueOf(sort));
        setPronum(pronum);
        setStocknum(socknum);
        setBoxnum(boxnum);
        setThistimeontheway(String.valueOf(thistimeontheway));
        setTotalnum(String.valueOf(totalnum));
        setStockover(String.valueOf(stockover));
        setForcenum(String.valueOf(forcenum));
        setOntheway(String.valueOf(ontheway));
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

    public String getPurchaseorder() {
        return purchaseorder.get();
    }

    public SimpleStringProperty purchaseorderProperty() {
        return purchaseorder;
    }

    public void setPurchaseorder(String purchaseorder) {
        this.purchaseorder.set(purchaseorder);
    }

    public int getSort() {

        if("".equals(sort.get())|| sort.get() == null ){
            return 0;
        }else{
            return Integer.parseInt(sort.get());
        }
    }

    public SimpleStringProperty sortProperty() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort.set(sort);
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

    public String getStocknum() {
        return stocknum.get();
    }

    public SimpleStringProperty stocknumProperty() {
        return stocknum;
    }

    public void setStocknum(String stocknum) {
        this.stocknum.set(stocknum);
    }

    public String getBoxnum() {
        return boxnum.get();
    }

    public SimpleStringProperty boxnumProperty() {
        return boxnum;
    }

    public void setBoxnum(String boxnum) {
        this.boxnum.set(boxnum);
    }

    public int getThistimeontheway() {

        if("".equals(thistimeontheway.get())|| thistimeontheway.get() == null ){
            return 0;
        }else{
            return Integer.parseInt(thistimeontheway.get());
        }
    }

    public SimpleStringProperty thistimeonthewayProperty() {
        return thistimeontheway;
    }

    public void setThistimeontheway(String thistimeontheway) {
        this.thistimeontheway.set(thistimeontheway);
    }

    public int  getTotalnum() {

        if("".equals(totalnum.get())|| totalnum.get() == null ){
            return 0;
        }else{
            return Integer.parseInt(totalnum.get());
        }
    }

    public SimpleStringProperty totalnumProperty() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum.set(totalnum);
    }

    public int getStockover() {

        if("".equals(stockover.get())|| stockover.get() == null ){
            return 0;
        }else{
            return Integer.valueOf(stockover.get());
        }
    }

    public SimpleStringProperty stockoverProperty() {
        return stockover;
    }

    public void setStockover(String stockover) {
        this.stockover.set(stockover);
    }

    public int getForcenum() {


        if("".equals(forcenum.get())|| forcenum.get() == null ){
            return 0;
        }else{
            return Integer.parseInt(forcenum.get());
        }

    }

    public SimpleStringProperty forcenumProperty() {
        return forcenum;
    }

    public void setForcenum(String forcenum) {
        this.forcenum.set(forcenum);
    }

    public int getOntheway() {
        if("".equals(ontheway.get())|| ontheway.get() == null ){
            return 0;
        }else{
            return Integer.parseInt(ontheway.get());
        }
    }

    public SimpleStringProperty onthewayProperty() {
        return ontheway;
    }

    public void setOntheway(String ontheway) {
        this.ontheway.set(ontheway);
    }
}
