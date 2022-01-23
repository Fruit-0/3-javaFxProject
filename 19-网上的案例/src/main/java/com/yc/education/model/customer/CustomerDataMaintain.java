package com.yc.education.model.customer;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer_data_maintain")
public class CustomerDataMaintain {

    /******************************************************************* 临时数据存放开始 ************************************************************/

    /**
     * 客户编号
     */
    @Transient
    private String tempCustomerNo;
    /**
     * 客户姓名
     */
    @Transient
    private String tempCustomerName;

    /**
     * 客户类别
     */
    @Transient
    private String tempCustomerCategory;

    /**
     * 客户姓名
     */
    @Transient
    private String tempCustomerRemark;

    /******************************************************************* 临时数据存放结束 ************************************************************/

    /**
     * 客户资料维护
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 客户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 客户编号后面的文本框
     */
    @Column(name = "customer_no_str")
    private String customerNoStr;

    /**
     * 建档编号
     */
    @Column(name = "create_no")
    private String createNo;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 建档日期
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 系统添加时间
     */
    private Date addtime;

    /**
     * 获取客户资料维护
     *
     * @return id - 客户资料维护
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户资料维护
     *
     * @param id 客户资料维护
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户id
     *
     * @return customer_id - 客户id
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户id
     *
     * @param customerId 客户id
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取客户编号
     *
     * @return customer_no - 客户编号
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 设置客户编号
     *
     * @param customerNo 客户编号
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 获取客户编号后面的文本框
     *
     * @return customer_no_str - 客户编号后面的文本框
     */
    public String getCustomerNoStr() {
        return customerNoStr;
    }

    /**
     * 设置客户编号后面的文本框
     *
     * @param customerNoStr 客户编号后面的文本框
     */
    public void setCustomerNoStr(String customerNoStr) {
        this.customerNoStr = customerNoStr;
    }

    /**
     * 获取建档编号
     *
     * @return create_no - 建档编号
     */
    public String getCreateNo() {
        return createNo;
    }

    /**
     * 设置建档编号
     *
     * @param createNo 建档编号
     */
    public void setCreateNo(String createNo) {
        this.createNo = createNo;
    }

    /**
     * 获取客户名称
     *
     * @return customer_name - 客户名称
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户名称
     *
     * @param customerName 客户名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 获取建档日期
     *
     * @return create_date - 建档日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置建档日期
     *
     * @param createDate 建档日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getTempCustomerNo() {
        return tempCustomerNo;
    }

    public void setTempCustomerNo(String tempCustomerNo) {
        this.tempCustomerNo = tempCustomerNo;
    }

    public String getTempCustomerName() {
        return tempCustomerName;
    }

    public void setTempCustomerName(String tempCustomerName) {
        this.tempCustomerName = tempCustomerName;
    }

    public String getTempCustomerCategory() {
        return tempCustomerCategory;
    }

    public void setTempCustomerCategory(String tempCustomerCategory) {
        this.tempCustomerCategory = tempCustomerCategory;
    }

    public String getTempCustomerRemark() {
        return tempCustomerRemark;
    }

    public void setTempCustomerRemark(String tempCustomerRemark) {
        this.tempCustomerRemark = tempCustomerRemark;
    }
}