package com.yc.education.model.customer;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 问题及意见
 */
public class CustomerDataMaintainQuestionProperty {
    /**
     *  问题及意见
     */
    private SimpleLongProperty id;

    /**
     * 问题类型（1现有问题、2其他问题）
     */
    private SimpleStringProperty type;


    /**
     * 正文
     */
    private SimpleStringProperty content;

    public CustomerDataMaintainQuestionProperty(String content) {
        if(content != null ){
            this.content = new SimpleStringProperty(content);
        }
    }

    public CustomerDataMaintainQuestionProperty(Long id, String type, String content) {
        if(id != null ){
            this.id = new SimpleLongProperty(id);
        }
        if(type != null ){
            this.type = new SimpleStringProperty(type);
        }
        if(content != null ){
            this.content = new SimpleStringProperty(content);
        }
    }

    public CustomerDataMaintainQuestionProperty() {

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

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
