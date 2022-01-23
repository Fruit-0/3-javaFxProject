package com.yc.education.mapper.stock;

import com.yc.education.model.stock.StockOutSaleProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StockOutSaleProductMapper extends MyMapper<StockOutSaleProduct> {

    /**
     * 根据销货出库单id查询出库产品
     * @param orderid
     * @return
     */
    List<StockOutSaleProduct> listStockOutSaleProduct(@Param("orderid") String orderid);
}