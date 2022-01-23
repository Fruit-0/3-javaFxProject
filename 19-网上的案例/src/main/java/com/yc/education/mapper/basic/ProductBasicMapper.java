package com.yc.education.mapper.basic;

import com.yc.education.model.basic.ProductBasic;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductBasicMapper extends MyMapper<ProductBasic> {



    /**
     * 查询最大编号
     * @return
     */
    String  selectMaxIdnum();


    /**
     * 查询所有产品
     * @return
     */
    List<ProductBasic> selectProductBasic();


    /**
     * 根据编号查询产品
     * @param idnum
     * @return
     */
    ProductBasic selectProductBasicByIsnum(@Param("idnum") String idnum);


    /**
     * 产品价格设定搜索产品
     * @param isnum  产品编号
     * @param proname 产品名称
     * @param basicunit //基本单位
     * @param protype //产品大类
     * @return
     */
    List<ProductBasic> selectProductBasicSearch(@Param("isnum")String isnum,
                                                @Param("proname")String proname,
                                                @Param("basicunit")long basicunit,
                                                @Param("protype")long protype);






}