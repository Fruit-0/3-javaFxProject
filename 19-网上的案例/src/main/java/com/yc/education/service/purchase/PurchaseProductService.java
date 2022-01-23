package com.yc.education.service.purchase;

import com.yc.education.model.purchase.PurchaseProduct;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName PurchaseProducctService
 * @Description TODO
 * @Author QuZhangJing
 * @Date 2018/10/10 16:49
 * @Version 1.0
 */
public interface PurchaseProductService extends IService<PurchaseProduct> {


    //根据采购订单编号查询采购产品
    List<PurchaseProduct> findPurchaseProduct(long purchaseid);

    //查询采购未入库产品
    List<PurchaseProduct> findPurchaseProductNotStock();


    //根据采购订单编号和产品订单查询采购产品
    PurchaseProduct findPurchaseProductByPronum(long purchaseid,String pronum);


    List<PurchaseProduct> findPurchaseProductNew(String sisnum,String eisnum,
                                                 String sproname,String eproname,
                                                 String stype,String etype,
                                                 String sdate,String edate);

}
