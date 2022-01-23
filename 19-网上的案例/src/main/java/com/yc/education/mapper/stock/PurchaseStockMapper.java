package com.yc.education.mapper.stock;

import com.yc.education.model.stock.PurchaseStock;
import com.yc.education.util.MyMapper;

import java.util.List;

public interface PurchaseStockMapper extends MyMapper<PurchaseStock> {

    /**
     * @param currentDate
     * @return
     */
    String selectMaxIdnum(String currentDate);


    //查询所有采购入库单
    List<PurchaseStock> findPurchaseStock();






}