package com.yc.education.mapper.stock;


import com.yc.education.model.stock.StockOutSale;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StockOutSaleMapper extends MyMapper<StockOutSale> {

    /**
     * 查询全部订单
     * @return
     */
    List<StockOutSale> listStockOutSaleAll();

    /**
     * 根据订货单号查询
     * @param orderno
     * @return
     */
    StockOutSale getStockOutSale(@Param("orderno") String orderno);

    /**
     * 最后一条数据
     * @return
     */
    StockOutSale getLastStockOutSale();

    /**
     * 第一条数据
     * @return
     */
    StockOutSale getFirstStockOutSale();

    /**
     * 获取上下页数据
     * @return
     */
    StockOutSale getStockOutSaleByPage(@Param("page") int page, @Param("rows")int rows);

    /**
     * 统计订单数量
     * @return
     */
    int getStockOutSaleCount();
}