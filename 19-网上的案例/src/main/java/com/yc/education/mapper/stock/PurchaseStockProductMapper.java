package com.yc.education.mapper.stock;

import com.yc.education.model.stock.PurchaseStockProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseStockProductMapper extends MyMapper<PurchaseStockProduct> {


    /**
     * 根据采购入库单编号查询采购入库产品
     * @param id
     * @return
     */
    List<PurchaseStockProduct> findStockProductBypurchaseStockId(@Param("id")long id);


}