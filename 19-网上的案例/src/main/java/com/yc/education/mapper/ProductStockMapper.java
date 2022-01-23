package com.yc.education.mapper;

import com.yc.education.model.ProductStock;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *@Description TODO 产品库存
 *@Author QuZhangJing
 *@Date 16:37  2018/10/22
 *@Version 1.0
 */
public interface ProductStockMapper extends MyMapper<ProductStock> {


    //产评库存查询
    List<ProductStock> findProductStock(@Param("sisnum")String sisnum,@Param("eisnum")String eisnum,
                                        @Param("sproname")String sproname,@Param("eproname")String eproname,
                                        @Param("stype")String stype,@Param("etype")String etype);


    //根据产品名称查询产品库存
    @Select("select * from product_stock where productorder = #{isnum}")
    ProductStock findProductStockByProIsnum(@Param("isnum")String isnum);


}