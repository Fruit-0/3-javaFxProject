package com.yc.education.service.stock;

import com.yc.education.model.stock.CheckStock;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName CheckStockService
 * @Description TODO 盘库作业
 * @Author QuZhangJing
 * @Date 2018/11/8 9:23
 * @Version 1.0
 */
public interface CheckStockService extends IService<CheckStock> {


    /**
     * @param currentDate
     * @return
     */
    String selectMaxIdnum(String currentDate);


    //查询所有盘库作业
    List<CheckStock> findCheckStock();


    List<CheckStock> findCheckStock(int pageNum,int pageSize);

}
