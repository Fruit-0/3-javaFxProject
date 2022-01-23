package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SalePurchaseOrderProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalePurchaseOrderProductMapper extends MyMapper<SalePurchaseOrderProduct> {

    /**
     * 根据订货单id查询订货产品
     * @param orderid
     * @return
     */
    List<SalePurchaseOrderProduct> listPurchaseOrderProduct(@Param("orderid") String orderid);
}