package com.yc.education.model.customer;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer_data_maintain_info")
public class CustomerDataMaintainInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户资料维护id
     */
    @Column(name = "maintain_id")
    private Long maintainId;

    /**
     * 创业年度
     */
    @Column(name = "start_year")
    private String startYear;

    /**
     * 年营业额
     */
    @Column(name = "annual_turnover")
    private String annualTurnover;

    /**
     * 传真
     */
    private String fax;

    /**
     * 员工数量
     */
    @Column(name = "employee_num")
    private Integer employeeNum;

    /**
     * 今年预算
     */
    private String budget;

    /**
     * 工业形态
     */
    private String industry;

    /**
     * 系统添加时间
     */
    private Date addtime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取创业年度
     *
     * @return start_year - 创业年度
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * 设置创业年度
     *
     * @param startYear 创业年度
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    /**
     * 获取年营业额
     *
     * @return annual_turnover - 年营业额
     */
    public String getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * 设置年营业额
     *
     * @param annualTurnover 年营业额
     */
    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * 获取传真
     *
     * @return fax - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取员工数量
     *
     * @return employee_num - 员工数量
     */
    public Integer getEmployeeNum() {
        return employeeNum;
    }

    /**
     * 设置员工数量
     *
     * @param employeeNum 员工数量
     */
    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    /**
     * 获取今年预算
     *
     * @return budget - 今年预算
     */
    public String getBudget() {
        return budget;
    }

    /**
     * 设置今年预算
     *
     * @param budget 今年预算
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    /**
     * 获取工业形态
     *
     * @return industry - 工业形态
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置工业形态
     *
     * @param industry 工业形态
     */
    public void setIndustry(String industry) {
        this.industry = industry;
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

    /**
     * 客户资料id
     * @return
     */
    public Long getMaintainId() {
        return maintainId;
    }
    /**
     * 客户资料id
     * @return
     */
    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }
}