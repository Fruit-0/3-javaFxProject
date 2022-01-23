package com.yc.education.service.purchase;

import com.yc.education.model.purchase.PurchaseOrders;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName PurchaseOrdersService
 * @Description TODO 采购订单
 * @Author QuZhangJing
 * @Date 2018/10/9 17:42
 * @Version 1.0
 */
public interface PurchaseOrdersService extends IService<PurchaseOrders> {


    /**
     * 查询最大编号
     * @return
     */
    String selectMaxIdnum(String currentDate);


    //查询采购订单
    List<PurchaseOrders> findPurchaseOrders();

    //查询采购订单
    List<PurchaseOrders> findPurchaseOrders(int pageNum,int pageSize);

    //根据编号查询订单
    PurchaseOrders findPurchaseByOrder(String orders);

}
