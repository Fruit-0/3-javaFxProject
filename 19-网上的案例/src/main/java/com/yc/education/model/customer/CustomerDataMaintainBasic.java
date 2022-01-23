package com.yc.education.model.customer;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer_data_maintain_basic")
public class CustomerDataMaintainBasic {
    /**
     * 客户基本资料维护-基本信息
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资料维护id
     */
    @Column(name = "maintain_id")
    private Long maintainId;
    /**
     * 负责人
     */
    @Column(name = "leader_people")
    private String leaderPeople;
    /**
     * 联系人
     */
    private String contacts;

    /**
     * 传真
     */
    private String fax;

    /**
     * 送货地址
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;

    /**
     * 发票地址
     */
    @Column(name = "invoice_address")
    private String invoiceAddress;

    /**
     * 采购人
     */
    private String purchase;

    /**
     * 电话
     */
    private String phone;

    /**
     * 创建人
     */
    @Column(name = "create_people")
    private String createPeople;

    /**
     * 创建人后面的文本框
     */
    @Column(name = "create_people_str")
    private String createPeopleStr;

    /**
     * 最后修改人
     */
    @Column(name = "update_people")
    private String updatePeople;

    /**
     * 最后修改人后面的文本框
     */
    @Column(name = "update_people_str")
    private String updatePeopleStr;

    /**
     * 系统添加时间
     */
    private Date addtime;

    /**
     * 获取客户基本资料维护-基本信息
     *
     * @return id - 客户基本资料维护-基本信息
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户基本资料维护-基本信息
     *
     * @param id 客户基本资料维护-基本信息
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取资料维护id
     *
     * @return maintain_id - 资料维护id
     */
    public Long getMaintainId() {
        return maintainId;
    }

    /**
     * 设置资料维护id
     *
     * @param maintainId 资料维护id
     */
    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    /**
     * @return leader_people
     */
    public String getLeaderPeople() {
        return leaderPeople;
    }

    /**
     * @param leaderPeople
     */
    public void setLeaderPeople(String leaderPeople) {
        this.leaderPeople = leaderPeople;
    }

    /**
     * @return contacts
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * @param contacts
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * @return fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取送货地址
     *
     * @return delivery_address - 送货地址
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 设置送货地址
     *
     * @param deliveryAddress 送货地址
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 获取发票地址
     *
     * @return invoice_address - 发票地址
     */
    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    /**
     * 设置发票地址
     *
     * @param invoiceAddress 发票地址
     */
    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    /**
     * 获取采购人
     *
     * @return purchase - 采购人
     */
    public String getPurchase() {
        return purchase;
    }

    /**
     * 设置采购人
     *
     * @param purchase 采购人
     */
    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取创建人
     *
     * @return create_people - 创建人
     */
    public String getCreatePeople() {
        return createPeople;
    }

    /**
     * 设置创建人
     *
     * @param createPeople 创建人
     */
    public void setCreatePeople(String createPeople) {
        this.createPeople = createPeople;
    }

    /**
     * 获取创建人后面的文本框
     *
     * @return create_people_str - 创建人后面的文本框
     */
    public String getCreatePeopleStr() {
        return createPeopleStr;
    }

    /**
     * 设置创建人后面的文本框
     *
     * @param createPeopleStr 创建人后面的文本框
     */
    public void setCreatePeopleStr(String createPeopleStr) {
        this.createPeopleStr = createPeopleStr;
    }

    /**
     * 获取最后修改人
     *
     * @return update_people - 最后修改人
     */
    public String getUpdatePeople() {
        return updatePeople;
    }

    /**
     * 设置最后修改人
     *
     * @param updatePeople 最后修改人
     */
    public void setUpdatePeople(String updatePeople) {
        this.updatePeople = updatePeople;
    }

    /**
     * 获取最后修改人后面的文本框
     *
     * @return update_people_str - 最后修改人后面的文本框
     */
    public String getUpdatePeopleStr() {
        return updatePeopleStr;
    }

    /**
     * 设置最后修改人后面的文本框
     *
     * @param updatePeopleStr 最后修改人后面的文本框
     */
    public void setUpdatePeopleStr(String updatePeopleStr) {
        this.updatePeopleStr = updatePeopleStr;
    }

    /**
     * 获取系统添加时间
     *
     * @return addtime - 系统添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置系统添加时间
     *
     * @param addtime 系统添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}