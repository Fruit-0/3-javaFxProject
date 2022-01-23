package com.yc.education.service.basic;

import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName EmployeeBasicService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/8/31 16:23
 * @Version 1.0
 */
public interface EmployeeBasicService extends IService<EmployeeBasic> {


    /**
     * 查询最大编号
     * @return
     */
    String  selectMaxIdnum();


    /**
     * 查询所有员工
     * @return
     */
    List<EmployeeBasic> selectEmployeeBasic(int pageNum,int pageSize);


    List<EmployeeBasic> selectEmployeeBasic();


    /**
     * 根据编号查询员工
     * @param idnum
     * @return
     */
    EmployeeBasic selectEmployeeBasicByIsnum(String idnum);






}
