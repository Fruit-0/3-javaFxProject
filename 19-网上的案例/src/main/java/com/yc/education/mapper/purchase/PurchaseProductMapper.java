package com.yc.education.mapper.purchase;

import com.yc.education.model.purchase.PurchaseProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseProductMapper extends MyMapper<PurchaseProduct> {


    //根据采购订单编号查询采购产品
    List<PurchaseProduct> findPurchaseProduct(@Param("purchaseid")long purchaseid);

    //查询采购未入库产品
    List<PurchaseProduct> findPurchaseProductNotStock();

    //根据采购订单编号和产品订单查询采购产品
    PurchaseProduct findPurchaseProductByPronum(@Param("purchaseid")long purchaseid,@Param("pronum")String pronum);


    List<PurchaseProduct> findPurchaseProductNew(@Param("sisnum")String sisnum,@Param("eisnum")String eisnum,
                                                 @Param("sproname")String sproname,@Param("eproname")String eproname,
                                                 @Param("stype")String stype,@Param("etype")String etype,
                                                 @Param("sdate")String sdate,@Param("edate")String edate);


}