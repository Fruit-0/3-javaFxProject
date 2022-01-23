package com.yc.education.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

public class Industry {
    /**
     * 行业
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 行业名称
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
     * 获取行业
     *
     * @return id - 行业
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置行业
     *
     * @param id 行业
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取行业名称
     *
     * @return title - 行业名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置行业名称
     *
     * @param title 行业名称
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

    @Override
    public String toString() {
        return title;
    }
}