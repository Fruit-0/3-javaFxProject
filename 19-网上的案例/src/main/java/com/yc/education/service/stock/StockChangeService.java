package com.yc.education.service.stock;

import com.yc.education.model.stock.StockChange;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName StockChangeService
 * @Description TODO 库存异动
 * @Author QuZhangJing
 * @Date 2018/11/5 10:52
 * @Version 1.0
 */
public interface StockChangeService extends IService<StockChange> {

    /**
     * @param currentDate
     * @return
     */
    String selectMaxIdnum(String currentDate);

    //查询所有库存异动
    List<StockChange> findStockChange();

    //查询所有库存异动
    List<StockChange> findStockChange(int pageNum,int pageSize);

}
