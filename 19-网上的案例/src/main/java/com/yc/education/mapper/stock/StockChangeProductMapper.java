package com.yc.education.mapper.stock;

import com.yc.education.model.stock.StockChangeProduct;
import com.yc.education.util.MyMapper;

import java.util.List;

/**
 *@Description TODO 库存异动产品
 *@Author QuZhangJing
 *@Date 20:34  2018/11/5
 *@Version 1.0
 */
public interface StockChangeProductMapper extends MyMapper<StockChangeProduct> {


    //根据库存异动id查询库存异动产品
    List<StockChangeProduct> findStockChangeProduct(long stockChangeId);

}