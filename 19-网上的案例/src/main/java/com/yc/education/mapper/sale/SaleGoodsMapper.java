package com.yc.education.mapper.sale;


import com.yc.education.model.sale.SaleGoods;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SaleGoodsMapper extends MyMapper<SaleGoods> {

    /**
     * 销货单状态更新
     * 差 财务复核状态、回传状态 条件
     * @param customerno 客户编号
     * @param begintime 创建时间-开始
     * @param endtime 创建时间-结束
     * @param verifystate 审核状态
     * @param deliverystate 发送状态
     * @param financialstate 财务复核状态
     * @param backstate 回传状态
     * @return
     */
    List<SaleGoods> listSaleGoodsByStates(@Param("customerno") String customerno,@Param("begintime") String begintime,@Param("endtime") String endtime,@Param("verifystate") String verifystate,@Param("deliverystate") String deliverystate,@Param("financialstate") String financialstate,@Param("backstate") String backstate);

    /**
     * 查询全部订单
     * @return
     */
    List<SaleGoods> listSaleGoodsAll();

    /**
     * 根据订货单号查询
     * @param orderno
     * @return
     */
    SaleGoods getSaleGoods(@Param("orderno") String orderno);

    /**
     * 最后一条数据
     * @return
     */
    SaleGoods getLastSaleGoods();

    /**
     * 第一条数据
     * @return
     */
    SaleGoods getFirstSaleGoods();

    /**
     * 获取上下页数据
     * @return
     */
    SaleGoods getSaleGoodsByPage(@Param("page") int page, @Param("rows")int rows);
}