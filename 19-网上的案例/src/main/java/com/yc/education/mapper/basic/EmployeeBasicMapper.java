package com.yc.education.mapper.basic;

import com.yc.education.model.basic.CompanyBasic;
import com.yc.education.model.basic.EmployeeBasic;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description TODO 员工档案
 *@Author QuZhangJing
 *@Date 15:58  2018/8/31
 *@Version 1.0
 */

public interface EmployeeBasicMapper extends MyMapper<EmployeeBasic> {



    /**
     * 查询最大编号
     * @return
     */
    String  selectMaxIdnum();


    /**
     * 查询所有员工
     * @return
     */
    List<EmployeeBasic> selectEmployeeBasic();


    /**
     * 根据编号查询员工
     * @param idnum
     * @return
     */
    EmployeeBasic selectEmployeeBasicByIsnum(@Param("idnum") String idnum);




}