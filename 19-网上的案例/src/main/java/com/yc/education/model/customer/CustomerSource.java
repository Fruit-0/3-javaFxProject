package com.yc.education.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer_source")
public class CustomerSource {
    /**
     * 客户来源
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户来源名称
     */
    private String title;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date addtime;
    /**
     * 备注说明
     */
    private String remark;

    /**
     * 获取客户来源
     *
     * @return id - 客户来源
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户来源
     *
     * @param id 客户来源
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户来源名称
     *
     * @return title - 客户来源名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置客户来源名称
     *
     * @param title 客户来源名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取添加时间
     *
     * @return addtime - 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置添加时间
     *
     * @param addtime 添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}