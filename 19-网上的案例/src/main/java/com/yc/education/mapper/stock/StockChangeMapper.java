package com.yc.education.mapper.stock;

import com.yc.education.model.stock.StockChange;
import com.yc.education.util.MyMapper;

import java.util.List;

/**
 *@Description TODO 库存异动
 *@Author QuZhangJing
 *@Date 10:36  2018/11/5
 *@Version 1.0
 */
public interface StockChangeMapper extends MyMapper<StockChange> {


    /**
     * @param currentDate
     * @return
     */
    String selectMaxIdnum(String currentDate);


    //查询所有库存异动
    List<StockChange> findStockChange();

}