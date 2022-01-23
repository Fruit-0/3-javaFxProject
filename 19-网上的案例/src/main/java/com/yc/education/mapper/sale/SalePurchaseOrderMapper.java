package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SalePurchaseOrder;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SalePurchaseOrderMapper extends MyMapper<SalePurchaseOrder> {

    /**
     * 查询全部订单
     * @return
     */
    List<SalePurchaseOrder> listSalePurchaseOrderAll();

    /**
     * 根据订货单号查询
     * @param orderno
     * @return
     */
    SalePurchaseOrder getSalePurchaseOrder(@Param("orderno")String orderno);

    /**
     * 最后一条数据
     * @return
     */
    SalePurchaseOrder getLastSalePurchaseOrder();

    /**
     * 第一条数据
     * @return
     */
    SalePurchaseOrder getFirstSalePurchaseOrder();

    /**
     * 获取上下页数据
     * @return
     */
    SalePurchaseOrder getSalePurchaseOrderByPage(@Param("page")int page, @Param("rows")int rows);

    /**
     * 统计订单数量
     * @return
     */
    int getSalePurchaseOrderCount();
}