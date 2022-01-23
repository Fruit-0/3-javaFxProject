package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SaleOnlineOrderProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleOnlineOrderProductMapper extends MyMapper<SaleOnlineOrderProduct> {

    /**
     * 根据网上订单查询ta订购的产品
     * @param orderno
     * @return
     */
    List<SaleOnlineOrderProduct> listByOnlineOrderAll(@Param("orderno") String orderno);



}