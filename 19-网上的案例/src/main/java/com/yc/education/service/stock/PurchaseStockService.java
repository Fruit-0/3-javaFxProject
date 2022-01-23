package com.yc.education.service.stock;

import com.yc.education.model.stock.PurchaseStock;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName PurchaseStockService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/24 19:47
 * @Version 1.0
 */
public interface PurchaseStockService extends IService<PurchaseStock>{


    /**
     * @param currentDate
     * @return
     */
    String selectMaxIdnum(String currentDate);

    //查询所有采购入库单
    List<PurchaseStock> findPurchaseStock();


    List<PurchaseStock> findPurchaseStock(int pageNum,int pageSize);


}
