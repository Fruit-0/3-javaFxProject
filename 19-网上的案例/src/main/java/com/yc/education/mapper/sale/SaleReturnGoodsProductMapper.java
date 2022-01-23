package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SaleReturnGoodsProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SaleReturnGoodsProductMapper extends MyMapper<SaleReturnGoodsProduct> {

    /**
     * 根据销售退货单id查询销退产品
     * @param orderid
     * @return
     */
    List<SaleReturnGoodsProduct> listSaleReturnGoodsProduct(@Param("orderid") String orderid);
}