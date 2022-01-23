package com.yc.education.service.stock;

import com.yc.education.model.stock.PurchaseStockProduct;
import com.yc.education.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName PurchaseStockProductService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/25 15:28
 * @Version 1.0
 */
public interface PurchaseStockProductService extends IService<PurchaseStockProduct> {


    /**
     * 根据采购入库单编号查询采购入库产品
     * @param id
     * @return
     */
    List<PurchaseStockProduct> findStockProductBypurchaseStockId(long id);



}
