package com.yc.education.service.basic;

import com.yc.education.model.basic.SupplierBasic;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName SupplierBasicService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/8/22 14:20
 * @Version 1.0
 */
public interface SupplierBasicService extends IService<SupplierBasic> {


    /**
     * 查询最大编号
     * @return
     */
    String selectMaxIdnum();

    /**
     * 查询所有供应商
     * @return
     */
    List<SupplierBasic> selectSupplierBasic(int pageNum,int pageSize);

    /**
     * 查询所有供应商
     * @return
     */
    List<SupplierBasic> selectSupplierBasic();


    /**
     * 根据编号查询供应商
     * @param idnum
     * @return
     */
    SupplierBasic selectSupplierBasicByIsnum(String idnum);




}
