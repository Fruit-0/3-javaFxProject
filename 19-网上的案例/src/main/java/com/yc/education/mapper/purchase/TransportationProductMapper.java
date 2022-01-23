package com.yc.education.mapper.purchase;

import com.yc.education.model.purchase.TransportationProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransportationProductMapper extends MyMapper<TransportationProduct> {



    //根据在途库存编号查询在途产品
    List<TransportationProduct> findTransportationProductByParentid(@Param("parentid")long parentid);



}