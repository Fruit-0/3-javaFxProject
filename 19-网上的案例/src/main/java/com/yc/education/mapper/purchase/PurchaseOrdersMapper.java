package com.yc.education.mapper.purchase;

import com.yc.education.model.purchase.PurchaseOrders;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description TODO   采购订单
 *@Author QuZhangJing
 *@Date 17:37  2018/10/9
 *@Version 1.0
 */
public interface PurchaseOrdersMapper extends MyMapper<PurchaseOrders> {

    /**
     * 查询最大编号
     * @return
     */
    String selectMaxIdnum(String currentDate);


    //查询采购订单
    List<PurchaseOrders> findPurchaseOrders();


    //根据编号查询订单
    PurchaseOrders findPurchaseByOrder(@Param("orders")String orders);


}